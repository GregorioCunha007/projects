package pdm.isel.pt.tmdbapp.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pdm.isel.pt.tmdbapp.Control.MainActivity;
import pdm.isel.pt.tmdbapp.R;

/**
 * Created by  on 21/10/2015.
 */
public class MovieDetailsActivity extends MainActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){

            // If the screen is in landscape mode its possible to show both fragments on the same activity
            // so there's no need in this activity
            finish();
            return;
        }
        if( savedInstanceState == null){
            // During the initial configuration connects the detail fragment
            MovieDetailsFragment details = new MovieDetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content,details).commit();
        }
    }

}
