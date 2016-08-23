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
public class Popular extends RequestedOrder implements Parcelable{

    public Popular(){
        setPageInit();
        results = new ArrayList<Movie>();
    }

    protected Popular(Parcel in) {
        page = in.readInt();
        service = in.readParcelable(TMDBService.class.getClassLoader());
        language = in.readString();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<Popular> CREATOR = new Creator<Popular>() {
        @Override
        public Popular createFromParcel(Parcel in) {
            return new Popular(in);
        }

        @Override
        public Popular[] newArray(int size) {
            return new Popular[size];
        }
    };

    @Override
    public ResponseDto makeRequest() {
        return service.getPopularMovies(language, getCurrentPage());
    }

    @Override
    public void setInDB(ArrayList<Movie> results) {

    }

    @Override
    public String getDBTableString() {
        return "popular";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeParcelable(service, flags);
        dest.writeString(language);
        dest.writeTypedList(results);
    }
}
