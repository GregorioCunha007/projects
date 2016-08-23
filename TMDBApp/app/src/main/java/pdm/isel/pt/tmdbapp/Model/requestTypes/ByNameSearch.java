package pdm.isel.pt.tmdbapp.Model.requestTypes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Model.Movie;
import pdm.isel.pt.tmdbapp.Model.RequestedOrder;
import pdm.isel.pt.tmdbapp.Model.ResponseDto;
import pdm.isel.pt.tmdbapp.Model.TMDBService;

/**
 * Created by Pedro on 08/12/2015.
 */
public class ByNameSearch extends RequestedOrder implements Parcelable {

    String movie;

    public ByNameSearch(String movie){
        setPageInit();
        this.movie = movie;
        results = new ArrayList<Movie>();
    }

    protected ByNameSearch(Parcel in) {
        movie = in.readString();
        page = in.readInt();
        service = in.readParcelable(TMDBService.class.getClassLoader());
        language = in.readString();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<ByNameSearch> CREATOR = new Creator<ByNameSearch>() {
        @Override
        public ByNameSearch createFromParcel(Parcel in) {
            return new ByNameSearch(in);
        }

        @Override
        public ByNameSearch[] newArray(int size) {
            return new ByNameSearch[size];
        }
    };

    @Override
    public ResponseDto makeRequest() {
        return service.searchMovieByName(movie, language, getCurrentPage());
    }

    @Override
    public void setInDB(ArrayList<Movie> results) {

    }

    @Override
    public String getDBTableString() {
        return "name_search";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie);
        dest.writeInt(page);
        dest.writeParcelable(service, flags);
        dest.writeString(language);
        dest.writeTypedList(results);
    }
}
