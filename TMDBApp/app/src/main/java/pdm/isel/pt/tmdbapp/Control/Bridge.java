package pdm.isel.pt.tmdbapp.Control;

import pdm.isel.pt.tmdbapp.Model.TMDBService;
import pdm.isel.pt.tmdbapp.View.MovieDetailsFragment;
import pdm.isel.pt.tmdbapp.View.MovieLinksFragments;

/**
 * Created by Pedro on 29/10/2015.
 */
public interface Bridge {

    TMDBService callModelConstructor();
    boolean Online();
}
