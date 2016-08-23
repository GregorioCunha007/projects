package pdm.isel.pt.tmdbapp.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Control.Bridge;
import pdm.isel.pt.tmdbapp.Control.MainActivity;
import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.R;

/**
 * Created by Pedro on 21/10/2015.
 */
public class MovieDetailsFragment extends Fragment {

    // Create new instance of Details Fragment
    public static MovieDetailsFragment newInstance(int index, ArrayList<Movie> movie){
        MovieDetailsFragment mdf = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index",index);
        args.putSerializable("movieList",movie);
        mdf.setArguments(args);
        return mdf;
    }

    public int getShownIndex(){
        return getArguments().getInt("index",0);
    }

    public ArrayList<Movie> getMovies(){
        return (ArrayList<Movie>)getArguments().getSerializable("movieList");
    }

    // Returns an object Scroll view with ...

    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ArrayList<Movie> movies = getMovies();

        MovieDetailView mdv = new MovieDetailView(getActivity());

        mdv.setMovieTitle(movies.get(getShownIndex()).getTitle());
        mdv.setReleaseDate(movies.get(getShownIndex()).getRelease_date());
        mdv.setOverview("Plot summary");
        mdv.setSummary(movies.get(getShownIndex()).getOverview());
        mdv.setCover(movies.get(getShownIndex()).getPoster_path(), movies.get(getShownIndex()).getId());

        if(movies.get(getShownIndex()).canBeFollowed)
           mdv.setFollowButton(movies.get(getShownIndex()).getId());

        ScrollView scroller = new ScrollView(getActivity());
        scroller.addView(mdv);

        return scroller;
    }
}

