package pdm.isel.pt.tmdbapp.Control;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Model.DB;
import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.MyContentProvider;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.ResponseDto;
import pdm.isel.pt.tmdbapp.Model.TMDBService;
import pdm.isel.pt.tmdbapp.Model.requestTypes.InTheatre;
import pdm.isel.pt.tmdbapp.Model.requestTypes.Upcoming;
import pdm.isel.pt.tmdbapp.R;

/**
 * Created by g on 13/12/2015.
 */
public class UpdateDataBaseService extends IntentService {

    public static final int ID = 123;
    public static final String LANGUAGE_KEY = "language";


    public UpdateDataBaseService() {
        super("UpdateDataBaseService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String language = intent.getStringExtra(LANGUAGE_KEY);
        Log.d("TAG", "Intent Started");
        if(!checkWirelessConnection()) return;
        updateMoviesInTheatres(language);
        updateUpcoming(language);
        checkDbToNotify();
        Log.d("TAG", "Intent Stopped");
    }

    private void checkDbToNotify() {
        Uri uri = Uri.parse(MyContentProvider.URL + "/upcoming");
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        ArrayList<Movie> upcomingMovieList = DB.parseCursor(c);
        MoviesFollowedFile mff = new MoviesFollowedFile(getApplicationContext());
        for (Movie m : upcomingMovieList) {
            if (mff.contains(m.getId()))
                showNotification(m.getTitle() + " is now in theatres");
        }
    }

    private void showNotification(String text) {
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

            final Notification notification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.mipmap.ic_action_ghostbusters)
                    .setContentTitle(getResources().getString(R.string.notificationTitle))
                    .setContentText(text)
                    .build();

            notificationManager.notify(ID, notification);
        }
    }

    private void updateUpcoming(String language) {
        TMDBService service = new TMDBService();
        RequestedOrder request = new Upcoming();
        request.setService(service);
        request.setLanguage(language);
        if(!checkWirelessConnection()) return;
        ResponseDto response = request.makeRequest();


        getContentResolver().delete(Uri.parse(MyContentProvider.URL + "/upcoming"), null, null);
        for (Movie m : request.getResults())
            getContentResolver().insert(Uri.parse(MyContentProvider.URL + "/upcoming"), DB.putValues(m));

        int totalPages = response.total_pages;

        for (int i = 1; i < totalPages; ++i) {
            request.nextPage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("TAG", e.getMessage());
            }
            if(!checkWirelessConnection()) return;
            ResponseDto r = request.makeRequest();
            for (Movie m : request.getResults())
                getContentResolver().insert(Uri.parse(MyContentProvider.URL + "/upcoming"), DB.putValues(m));
        }
        Log.d("TAG", "updated upcoming movies");
    }

    private void updateMoviesInTheatres(String language) {
        TMDBService service = new TMDBService();
        RequestedOrder request = new InTheatre();
        request.setService(service);
        request.setLanguage(language);
        if(!checkWirelessConnection()) return;
        ResponseDto response = request.makeRequest();

        getContentResolver().delete(Uri.parse(MyContentProvider.URL + "/inTheatre"), null, null);
        for (Movie m : request.getResults())
            getContentResolver().insert(Uri.parse(MyContentProvider.URL + "/inTheatre"), DB.putValues(m));

        int totalPages = response.total_pages;

        for (int i = 1; i < totalPages; ++i) {
            request.nextPage();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("TAG", e.getMessage());
            }
            if(!checkWirelessConnection()) return;
            ResponseDto r = request.makeRequest();
            for (Movie m : request.getResults())
                getContentResolver().insert(Uri.parse(MyContentProvider.URL + "/inTheatre"), DB.putValues(m));
        }
        Log.d("TAG", "updated movies in theatres");
    }


    public static Intent makeIntent(Context ctx, String language) {
        return new Intent(ctx, UpdateDataBaseService.class)
                .putExtra(LANGUAGE_KEY, language);
    }

    public boolean checkWirelessConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnected();
    }

}