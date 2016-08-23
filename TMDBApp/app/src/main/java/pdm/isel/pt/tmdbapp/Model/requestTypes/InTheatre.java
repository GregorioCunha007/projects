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
public class InTheatre extends RequestedOrder implements Parcelable{

    public InTheatre() {
        setPageInit();
        results = new ArrayList<Movie>();
    }

    protected InTheatre(Parcel in) {
        page = in.readInt();
        service = in.readParcelable(TMDBService.class.getClassLoader());
        language = in.readString();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<InTheatre> CREATOR = new Creator<InTheatre>() {
        @Override
        public InTheatre createFromParcel(Parcel in) {
            return new InTheatre(in);
        }

        @Override
        public InTheatre[] newArray(int size) {
            return new InTheatre[size];
        }
    };

    @Override
    public ResponseDto makeRequest() {
        return service.getMoviesInTheatres(language, getCurrentPage());
    }

    @Override
    public void setInDB(ArrayList<Movie> results) {

    }

    @Override
    public String getDBTableString() {
        return "inTheatre";
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
