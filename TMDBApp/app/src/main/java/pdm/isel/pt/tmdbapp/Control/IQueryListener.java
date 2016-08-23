package pdm.isel.pt.tmdbapp.Control;


import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Model.Movie;

/**
 * Created by francisco on 14/01/2016.
 */
public interface IQueryListener {

    ArrayList<Movie> callQuery(String tableName, int page);



}
