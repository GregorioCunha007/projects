package pdm.isel.pt.tmdbapp.View;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Control.IMovieActivityReceiver;
import pdm.isel.pt.tmdbapp.Control.MainActivity;
import pdm.isel.pt.tmdbapp.Control.MyWebRequestService;
import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.requestTypes.InTheatre;
import pdm.isel.pt.tmdbapp.Model.requestTypes.Upcoming;
import pdm.isel.pt.tmdbapp.R;

// Activity that hold the fragment
public class MovieActivity extends MainActivity implements IMovieActivityReceiver {

    private RequestedOrder typeOfRequestLocal;
    MovieLinksFragments.MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        this.typeOfRequestLocal = getIntent().getParcelableExtra("TypeOfRequest");
        getIntent().putExtra("MovieArrayList", typeOfRequestLocal);
        receiver.setMovieActivityListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!(typeOfRequestLocal instanceof Upcoming || typeOfRequestLocal instanceof InTheatre))
            myDelete(typeOfRequestLocal.getDBTableString());
    }

    @Override
    public void nextPage(MovieLinksFragments.MovieListAdapter movieListAdapter) {
        this.movieListAdapter = movieListAdapter;
        typeOfRequestLocal.nextPage();
        if(!Online()){
            moviesProvided = myQuery(typeOfRequestLocal.getDBTableString(),typeOfRequestLocal.getCurrentPage());
            if( typeOfRequestLocal instanceof Upcoming)
                canBeFollowedTrue();
            movieListAdapter.sendNewData(moviesProvided);
        }else {
            if(typeOfRequestLocal instanceof Upcoming || typeOfRequestLocal instanceof InTheatre){
                moviesProvided = myQuery(typeOfRequestLocal.getDBTableString(),typeOfRequestLocal.getCurrentPage());
                if( moviesProvided.isEmpty()){
                    Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
                    msgIntent.putExtra("TypeOfRequest", typeOfRequestLocal);
                    startService(msgIntent);
                    return;
                }
                movieListAdapter.sendNewData(moviesProvided);
            }else {
                Intent msgIntent = new Intent(getApplicationContext(), MyWebRequestService.class);
                msgIntent.putExtra("TypeOfRequest", typeOfRequestLocal);
                startService(msgIntent);
            }
        }
    }

    @Override
    public void prevPage(MovieLinksFragments.MovieListAdapter movieListAdapter) {
        this.movieListAdapter = movieListAdapter;
        typeOfRequestLocal.prevPage();
        ArrayList<Movie> data = typeOfRequestLocal.getPreviousPage(typeOfRequestLocal.getDBTableString(), this);
        movieListAdapter.sendNewData(data);
    }

    @Override
    public void serviceReceivedToMovieActivity(RequestedOrder request) {
        typeOfRequestLocal = request;
        int i = getTableSize(typeOfRequestLocal.getDBTableString());
        if( i / 20 < typeOfRequestLocal.getCurrentPage())
            myInsert(request.getResults(), request.getDBTableString());
        movieListAdapter.sendNewData(request.getResults());
    }

}
