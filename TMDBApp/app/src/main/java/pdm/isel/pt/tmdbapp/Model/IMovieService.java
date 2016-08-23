package pdm.isel.pt.tmdbapp.Model;

/**
 * Created by francisco on 20/10/2015.
 */
public interface IMovieService {

    ResponseDto searchMovieByName(String name, String language,int page);
    ResponseDto getMoviesInTheatres(String language, int page);
    ResponseDto getPopularMovies(String language, int page);
    ResponseDto getUpcomingMovies(String language, int page);
}
