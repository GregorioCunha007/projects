package pdm.isel.pt.tmdbapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pdm.isel.pt.tmdbapp.Control.MoviesFollowedFile;
import pdm.isel.pt.tmdbapp.R;

/**
 * Created by Pedro on 25/10/2015.
 */
public class MovieDetailView extends LinearLayout {

    private ImageView cover;
    private TextView overview;
    private TextView movieTitle;
    private TextView summary;
    private TextView releaseDate;
    private Button follow;
    private final Context context;

    public MovieDetailView(Context context){
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.movie_detail,this); // movie_detail vai ser o nome do xml
        cover = (ImageView) findViewById(R.id.image);
        movieTitle = (TextView) findViewById(R.id.movieTitle);
        overview = (TextView) findViewById(R.id.overview);
        summary = (TextView) findViewById(R.id.summary);
        releaseDate = (TextView) findViewById(R.id.airdate);
        follow = (Button)findViewById(R.id.followme);
    }

    public void setCover(String url, int id){ new ImageLoader(url,cover, id, context).execute(); }

    public void setOverview(String string){
        overview.setText(string);
    }

    public void setSummary(String string){
        summary.setText(string);
    }

    public void setReleaseDate(String string){
        releaseDate.setText(string);
    }

    public void setMovieTitle(String string){
        movieTitle.setText(string);
    }

    public void setFollowButton(final int movieId) {
        follow.setVisibility(View.VISIBLE);
        final MoviesFollowedFile mff = new MoviesFollowedFile(context);
        if(mff.contains(movieId))
            follow.setText("Unfollow");

        follow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mff.contains(movieId) ){
                    mff.unfollow(movieId);
                    follow.setText("Follow");
                }else{
                    mff.follow(movieId);
                    follow.setText("Unfollow");
                }
            }
        });
    }
}
