package pdm.isel.pt.tmdbapp;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import pdm.isel.pt.tmdbapp.Control.MainActivity;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.TMDBService;
import retrofit.Call;

/**
 * Created by Pedro on 01/11/2015.
 */
public class TMDBApiTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public TMDBApiTest(String pkg, Class<MainActivity> activityClass) {
        super(pkg, activityClass);
    }
    /*
    String language = "en";
    private MainActivity MainActivityTest;
    public final String API_KEY = "0e69855944ab60754969006017082dac";

    public TMDBApiTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        MainActivityTest = getActivity();
    }

    public void tearDown() throws Exception {

    }

    public void testRetrofitTest_getMovieListByName() throws Exception {
        String movieName = "Lord of War";
        TMDBService tmdbapi = new TMDBService();
        Call<RequestedOrder> call = tmdbapi.apiService.searchMovieList(movieName, API_KEY,0, language);
        RequestedOrder res = call.execute().body();
        Assert.assertNotNull(res);

    }

    public void testRetrofitTest_getMovieListInTheaters() throws Exception {
        TMDBService tmdbapi = new TMDBService();
        Call<RequestedOrder> call = tmdbapi.apiService.getMoviesInTheatres(API_KEY,0,language);
        RequestedOrder res = call.execute().body(); //sync call
        Assert.assertNotNull(res);
    }

    public void testRetrofitTest_getMovieListPopular() throws Exception {
        TMDBService tmdbapi = new TMDBService();
        Call<RequestedOrder> call = tmdbapi.apiService.getPopularMovies(API_KEY,0,language);
        RequestedOrder res = call.execute().body(); //sync call
        Assert.assertNotNull(res);
    }

    public void testRetrofitTest_getMovieListUpcoming() throws Exception {
        TMDBService tmdbapi = new TMDBService();
        Call<RequestedOrder> call = tmdbapi.apiService.getUpcomingMovies(API_KEY,0,language);
        RequestedOrder res = call.execute().body(); //sync call
        Assert.assertNotNull(res);
    }
*/
}