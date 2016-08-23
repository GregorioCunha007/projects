package pdm.isel.pt.tmdbapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import pdm.isel.pt.tmdbapp.Control.IDataBaseBridge;
import pdm.isel.pt.tmdbapp.Control.ServiceInterface;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by francisco on 20/10/2015.
 */
public class TMDBService implements IMovieService,Parcelable {

    public String ENDPOINT = "https://api.themoviedb.org/3/";
    public String API_KEY = "0e69855944ab60754969006017082dac"; // mudar para onde?
    public TMDBAPI apiService;
    ServiceInterface listener;
    
    private RequestedOrder requestResponse;
    private IDataBaseBridge dataBaseListener;

    public TMDBService() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = adapter.create(TMDBAPI.class);
    }

    protected TMDBService(Parcel in) {
        ENDPOINT = in.readString();
        API_KEY = in.readString();
        requestResponse = in.readParcelable(RequestedOrder.class.getClassLoader());
    }

    public static final Creator<TMDBService> CREATOR = new Creator<TMDBService>() {
        @Override
        public TMDBService createFromParcel(Parcel in) {
            return new TMDBService(in);
        }

        @Override
        public TMDBService[] newArray(int size) {
            return new TMDBService[size];
        }
    };

    @Override
    public ResponseDto searchMovieByName(String movieName, String language, int page) {

        Call<ResponseDto> call = apiService.searchMovieList(API_KEY, movieName, page, language);
        try {
            return call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDto getMoviesInTheatres(String language, int page) {
        Call<ResponseDto> call = apiService.getMoviesInTheatres(API_KEY, page, language);
        try {
            return call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDto getPopularMovies(String language, int page) {
        if (!listener.Online()) throw new MException();

        Call<ResponseDto> call = apiService.getPopularMovies(API_KEY, page, language);
        try {
            return call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDto getUpcomingMovies(String language, int page) {
        Call<ResponseDto> call = apiService.getUpcomingMovies(API_KEY, page, language);
        try {
            return call.execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   public void setServiceListener(ServiceInterface serviceListener){
        this.listener = serviceListener;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ENDPOINT);
        dest.writeString(API_KEY);
        dest.writeParcelable(requestResponse, flags);
    }

    public void setDataBaseListener(IDataBaseBridge dataBaseListener) {
        this.dataBaseListener =  dataBaseListener;
    }


    private class MException extends RuntimeException {
    }

}
