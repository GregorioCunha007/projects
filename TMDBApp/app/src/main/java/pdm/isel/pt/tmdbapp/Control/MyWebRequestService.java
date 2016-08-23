package pdm.isel.pt.tmdbapp.Control;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.ResponseDto;
import pdm.isel.pt.tmdbapp.Model.TMDBService;

/**
 * Created by Pedro on 08/12/2015.
 */
public class MyWebRequestService extends IntentService implements ServiceInterface {
    RequestedOrder request;

    public MyWebRequestService() {
        super("MyWebRequestService");
    }

    @Override
    protected void onHandleIntent(Intent workerIntent) {

        TMDBService service = new TMDBService();
        request = workerIntent.getParcelableExtra("TypeOfRequest");
        String language = request.language;

        service.setServiceListener(this);
        request.setService(service);
        request.setLanguage(language);
        ResponseDto response = request.makeRequest(); // Sync request

        request.setResults(response.getMovieList());
        Intent broadcastIntent = new Intent();

        if (request.getCurrentPage() > 1) {
            broadcastIntent.setAction(MyWebRequestReceiver.ACTION_SAME_REQUEST);
            broadcastIntent.putExtra("Reference", request);
        } else {
            broadcastIntent.setAction(MyWebRequestReceiver.ACTION_NEW_REQUEST);
            broadcastIntent.putExtra("Reference", request);
        }

        getApplicationContext().sendBroadcast(broadcastIntent);

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
}