package pdm.isel.pt.tmdbapp.View;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Control.IMovieActivityReceiver;
import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.R;

/**
 * Created by Pedro on 21/10/2015.
 */
public class MovieLinksFragments extends ListFragment {

    boolean dualFrame;
    int curPosition = 0;
    private ArrayList<Movie> movieArrayList;
    private int currentPage;
    IMovieActivityReceiver listener;
    MovieListAdapter movieListAdapter;
    Button nextPage;
    Button prevPage;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        RequestedOrder requestedOrder = getActivity().getIntent().getParcelableExtra("MovieArrayList");
        movieArrayList = requestedOrder.getResults();
        setPageListener((MovieActivity) this.getActivity());
        // Create list of movies obtained
        setNewListAdapter();
        // Check the existence of frame for the details page
        nextPage = (Button) getActivity().findViewById(R.id.buttonNext);
        prevPage = (Button) getActivity().findViewById(R.id.buttonPrev);

        View detailsFrame = getActivity().findViewById(R.id.details);
        getListView().setOnScrollListener(movieListAdapter);
        dualFrame = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        currentPage = 1;
        if(savedInstanceState != null){
            // Reclaims the last state for the marked position
            curPosition = savedInstanceState.getInt("curPosition",0);
        }

        if( dualFrame ){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(curPosition);
        }
    }

    private void setPageListener(IMovieActivityReceiver activity) {
        this.listener = activity;
    }

    private void setNewListAdapter() {
        movieListAdapter = new MovieListAdapter(getActivity(), movieArrayList);
        setListAdapter(movieListAdapter);
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("curPosition", curPosition);
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        showDetails(position);
    }

    //Shows the details of the selected item by showing off the fragment or initiating an activity

    private void showDetails(int index){
        curPosition = index;
        if( dualFrame ) {
            getListView().setItemChecked(index, true);
            // Checks if the fragment is exhibited and alters him if necessary
            MovieDetailsFragment details = (MovieDetailsFragment) getFragmentManager().findFragmentById(R.id.details);
            if (details == null || details.getShownIndex() != index) {
                // Creates new fragment
                details = MovieDetailsFragment.newInstance(index, movieArrayList);
                // Executes a transition, subbing any fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
                // Launches new Activity
                Intent intent = new Intent();
                intent.setClass(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("movieList", movieArrayList);
                startActivity(intent);
            }
    }

    public class MovieListAdapter extends ArrayAdapter<Movie> implements AbsListView.OnScrollListener{

        private ImageView thumbnail;
        private TextView title;
        private RatingBar ratingBar;
        private int currentFirstVisibleItem;
        private int currentVisibleItemCount;
        private int currentScrollState;
        private int totalItemCount;

        public MovieListAdapter(Context context, ArrayList<Movie> movies) {
            super(context, 0, movies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Movie movie = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list, parent, false);
            }
            // Lookup view
            thumbnail = (ImageView) convertView.findViewById(R.id.film_cover);
            title = (TextView) convertView.findViewById(R.id.movieTitle);
            ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            // Populate the views
            new ImageLoader(movie.getPoster_path(), thumbnail, movie.getId(), getContext()).execute();
            title.setText(movie.getTitle());
            ratingBar.setRating((float)movie.getVote_average());

            // Return the completed view to render on screen
            return convertView;
        }

        private void updateData(){

            this.clear();

            for(Movie m : movieArrayList){
                this.insert(m,this.getCount());
            }
            notifyDataSetChanged();
        }

        public void sendNewData(ArrayList<Movie> movieList) {
            movieArrayList = movieList;
            updateData();
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if( firstVisibleItem + visibleItemCount == totalItemCount ){
                nextPage.setVisibility(View.VISIBLE);
                nextPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.nextPage(movieListAdapter);
                        ++currentPage;
                    }
                });
            }else{
                nextPage.setVisibility(View.INVISIBLE);
            }

            if( firstVisibleItem == 0 && currentPage > 1){
                prevPage.setVisibility(View.VISIBLE);
                prevPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.prevPage(movieListAdapter);
                        --currentPage;
                    }
                });
            }else{
                prevPage.setVisibility(View.INVISIBLE);
            }
        }
    }
}


