package pdm.isel.pt.tmdbapp.Model;

import android.os.Parcelable;

import java.io.Serializable;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by francisco on 20/10/2015.
 */
public interface TMDBAPI {

    @GET("search/movie")
    Call<ResponseDto> searchMovieList(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page, @Query("language") String language);

    @GET("movie/now_playing")
    Call<ResponseDto> getMoviesInTheatres(@Query("api_key") String api_key,@Query("page") int page, @Query("language") String language);

    @GET("movie/upcoming")
    Call<ResponseDto> getUpcomingMovies( @Query("api_key") String api_key,@Query("page") int page, @Query("language") String language);

    @GET("movie/popular")
    Call<ResponseDto> getPopularMovies( @Query("api_key") String api_key,@Query("page") int page, @Query("language") String language);

    @GET("movie/{id}")
    Call<ResponseDto> getSpecificMovie(@Path("id")String  movieId, @Query("api_key") String api_key, @Query("language") String language);

}
