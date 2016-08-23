package pdm.isel.pt.tmdbapp.Control;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.View.MovieLinksFragments;

/**
 * Created by Pedro on 08/12/2015.
 */
public interface IMovieActivityReceiver {

    void nextPage(MovieLinksFragments.MovieListAdapter movieListAdapter);
    void prevPage(MovieLinksFragments.MovieListAdapter movieListAdapter);
    void serviceReceivedToMovieActivity(RequestedOrder request);
}
