package pdm.isel.pt.tmdbapp.Control;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import pdm.isel.pt.tmdbapp.Model.DB;
import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.MyContentProvider;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.TMDBService;
import pdm.isel.pt.tmdbapp.Model.requestTypes.ByNameSearch;
import pdm.isel.pt.tmdbapp.Model.requestTypes.InTheatre;
import pdm.isel.pt.tmdbapp.Model.requestTypes.Popular;
import pdm.isel.pt.tmdbapp.Model.requestTypes.Upcoming;
import pdm.isel.pt.tmdbapp.R;
import pdm.isel.pt.tmdbapp.View.ImageLoader;
import pdm.isel.pt.tmdbapp.View.MovieActivity;

public class MainActivity extends AppCompatActivity implements Bridge, IMainActivityReceiver, IQueryListener {

    SearchView searchView;
    TMDBService tmdb;
    private boolean queryOnProcess;
    private String language;
    Intent detailIntent;
    public ArrayList<Movie> moviesProvided;
    protected RequestedOrder typeOfRequest;
    protected MyWebRequestReceiver receiver;
    protected IntentFilter filter;
    public static final int REFRESHER_SERVICE_CODE = 1;
    private UpdateDataBaseServiceConfig intervalServiceConfig;
    private DB dB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tmdb = callModelConstructor();
        language = Locale.getDefault().getLanguage();
        new MoviesFollowedFile(this);
        dB = new DB(this);

        setNoImageCache();

        /* Setting up the broadcaster */
        receiver = new MyWebRequestReceiver();
        receiver.setMainActivityListener(this);
        filter = new IntentFilter();
        filter.addAction(MyWebRequestReceiver.ACTION_SAME_REQUEST);
        filter.addAction(MyWebRequestReceiver.ACTION_NEW_REQUEST);
        registerReceiver(receiver, filter);

        /* Setting up Notification Update Movie Alarm */

        intervalServiceConfig = new UpdateDataBaseServiceConfig(getApplicationContext());

        setUpdateDataBaseServiceAlarm();

    }

    public Context getContext() {
        return getContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dB.db_core.closeDB();
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Already closed", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().length() <= 0) return false;
                if (!queryOnProcess)   // If the retrofit response was successful don't call the callSearch anymore
                {
                    callSearch(query);
                    queryOnProcess = true;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            public void callSearch(String query) {
                try {
                    getMovieList(query);
                } catch (NoNetworkException e) {
                    Toast.makeText(getApplicationContext(), " No network connection ", Toast.LENGTH_LONG).show();
                }
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int item_touched = item.getItemId();
        try {
            switch (item_touched) {
                case R.id.action_in_theatre:
                    getMovieListInTheatre();
                    return true;
                case R.id.action_popular_movies:
                    getPopularMovies();
                    return true;
                case R.id.action_incoming:
                    getUpcomingMovies();
                    return true;
                case R.id.config:
                    configClick();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (NoNetworkException e) {
            Toast.makeText(getApplicationContext(), " No network connection ", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void configClick() {
        Intent i = new Intent(this, ConfigActivity.class);
        startActivity(i);
    }

    protected void getPopularMovies() {
       if (!Online()) throw new NoNetworkException();
        Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
        typeOfRequest = new Popular();
        typeOfRequest.setLanguage(language);
        msgIntent.putExtra("TypeOfRequest", typeOfRequest);
        startService(msgIntent);
    }

    protected void getUpcomingMovies() throws NoDataAvailableException {
        typeOfRequest = new Upcoming();
        moviesProvided = myQuery("upcoming", typeOfRequest.getCurrentPage());
        if (!Online()) {
            if (moviesProvided.isEmpty())
                throw new NoDataAvailableException();
            canBeFollowedTrue();
            detailIntent = new Intent(MainActivity.this, MovieActivity.class);
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            typeOfRequest.setResults(moviesProvided);
            detailIntent.putExtra("TypeOfRequest", typeOfRequest);
            startActivity(detailIntent);
        } else {
            if (moviesProvided.isEmpty()) {
                Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
                typeOfRequest.setLanguage(language);
                msgIntent.putExtra("TypeOfRequest", typeOfRequest);
                startService(msgIntent);
            } else {
                canBeFollowedTrue();
                typeOfRequest.setResults(moviesProvided);
                detailIntent = new Intent(MainActivity.this, MovieActivity.class);
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detailIntent.putExtra("TypeOfRequest", typeOfRequest);
                startActivity(detailIntent);
            }
        }
    }

    protected void canBeFollowedTrue() {
        for (Movie m : moviesProvided)
            m.canBeFollowed = true;
    }

    protected void getMovieListInTheatre() throws NoDataAvailableException {
        typeOfRequest = new InTheatre();
        moviesProvided = myQuery("inTheatre", typeOfRequest.getCurrentPage());
        if (!Online()) {
            if (moviesProvided.isEmpty()){
                throw new NoDataAvailableException();
            }
            detailIntent = new Intent(MainActivity.this, MovieActivity.class);
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            typeOfRequest.setResults(moviesProvided);
            detailIntent.putExtra("TypeOfRequest", typeOfRequest);

            startActivity(detailIntent);
        } else {
            if (moviesProvided.isEmpty()) {
                Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
                typeOfRequest.setLanguage(language);
                msgIntent.putExtra("TypeOfRequest", typeOfRequest);
                startService(msgIntent);
            } else {
                typeOfRequest.setResults(moviesProvided);
                detailIntent = new Intent(MainActivity.this, MovieActivity.class);
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                detailIntent.putExtra("TypeOfRequest", typeOfRequest);
                startActivity(detailIntent);
            }
        }
    }

    protected void getMovieList(String text) {
        if (!Online()) throw new NoNetworkException();
        Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
        typeOfRequest = new ByNameSearch(text);
        typeOfRequest.setLanguage(language);
        msgIntent.putExtra("TypeOfRequest", typeOfRequest);
        startService(msgIntent);
    }

    @Override
    public TMDBService callModelConstructor() {
        return new TMDBService();
    }

    @Override
    public boolean Online() {
        return checkWirelessConnection();
    }

    public boolean checkWirelessConnection() {
            ConnectivityManager manager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            boolean isAvailable = false;
            if (networkInfo != null && networkInfo.isConnected()) {
                // Network is present and connected
                isAvailable = true;
            }
            return isAvailable;
    }

    @Override
    public boolean serviceReceivedToMainActivity(RequestedOrder typeOfRequestProvided) {
        moviesProvided = typeOfRequestProvided.getResults();
        myInsert(moviesProvided, typeOfRequestProvided.getDBTableString());
        detailIntent = new Intent(MainActivity.this, MovieActivity.class);
        detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        detailIntent.putExtra("TypeOfRequest", typeOfRequestProvided);
        startActivity(detailIntent);
        return true;
    }

    private void setUpdateDataBaseServiceAlarm() {
        Log.d("TAG", "Setting alarm");
        final AlarmManager alarmManager = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        final PendingIntent pendingIntent = PendingIntent.getService(
                this, REFRESHER_SERVICE_CODE,
                UpdateDataBaseService.makeIntent(this, language),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                0, intervalServiceConfig.getTimeInterval(), pendingIntent);
    }


    public ArrayList<Movie> myQuery(String tableName, int page) {
        Uri uri = Uri.parse(MyContentProvider.URL + "/" + tableName);
        int startOrder = (page - 1) * 20 + 1;
        int final_ = startOrder + 19;
        String[] array = {Integer.toString(startOrder), Integer.toString(final_)};
        Cursor c = getContentResolver().query(uri, null, "pos >= ? AND pos <= ?", array, null);
        return DB.parseCursor(c);
    }

    protected int getTableSize(String tableName) {
        Uri uri = Uri.parse(MyContentProvider.URL + "/" + tableName);
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        return c.getCount();
    }

    public void myInsert(ArrayList<Movie> list, String tableName) {
        for (Movie m : list)
            getContentResolver().insert(Uri.parse(MyContentProvider.URL + "/" + tableName), DB.putValues(m));
    }

    public void myDelete(String tableName) {
        getContentResolver().delete(Uri.parse(MyContentProvider.URL + "/" + tableName), null, null);
    }

    @Override
    public ArrayList<Movie> callQuery(String tableName, int page) {
        return myQuery(tableName, page);
    }


    private void setNoImageCache() {
        Bitmap noImg = BitmapFactory.decodeResource(getResources(), R.drawable.no_img);
        ImageLoader.cache.addBitmapToMemoryCache(Integer.toString(-1), noImg);
    }

    private class NoNetworkException extends RuntimeException {
        public NoNetworkException() {

        }
    }

    private class NoDataAvailableException extends RuntimeException {
        public NoDataAvailableException() {
            Toast.makeText(getApplicationContext(), " No data available ", Toast.LENGTH_LONG).show();
        }
    }
}
