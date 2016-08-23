package pdm.isel.pt.tmdbapp.Model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pedro on 10/12/2015.
 */
public class ResponseDto {

    public int page;
    public ArrayList<Movie> results;
    public int total_pages;
    public int total_results;

    public ArrayList<Movie> getMovieList() {
        return results;
    }
    public void setResults(ArrayList<Movie> movies ){
        int index = 0;
        for( Movie m : movies){
            results.add(index++,m);
        }
    }

}
