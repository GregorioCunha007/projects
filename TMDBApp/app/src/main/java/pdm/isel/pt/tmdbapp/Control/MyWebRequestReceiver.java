package pdm.isel.pt.tmdbapp.Control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.requestTypes.*;

/**
 * Created by Pedro on 08/12/2015.
 */
public class MyWebRequestReceiver extends BroadcastReceiver {

    public static final String ACTION_NEW_REQUEST = "NEW REQUEST";
    public static final String ACTION_SAME_REQUEST = "SAME REQUEST";
    public static final String UPDATE_DATA_BASE = "UPDATE IN THEATRE";

    IMainActivityReceiver listenerMain;
    IMovieActivityReceiver listenerMovie;

    public MyWebRequestReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        RequestedOrder reference = intent.getParcelableExtra("Reference");

        if (action.equals(ACTION_NEW_REQUEST)) {
           if (reference instanceof Upcoming)
                ((Upcoming) reference).setUpcoming(reference.getResults());
            listenerMain.serviceReceivedToMainActivity(reference);
        } else if (action.equals(ACTION_SAME_REQUEST)) {
            listenerMovie.serviceReceivedToMovieActivity(reference);
        }
    }

    public void setMainActivityListener(IMainActivityReceiver listener) {
        this.listenerMain = listener;
    }

    public void setMovieActivityListener(IMovieActivityReceiver listener) {
        this.listenerMovie = listener;
    }

}