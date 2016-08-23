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
public class Upcoming extends RequestedOrder implements Parcelable{

    public Upcoming() {
        setPageInit();
        results = new ArrayList<Movie>();
    }

    protected Upcoming(Parcel in) {
        page = in.readInt();
        service = in.readParcelable(TMDBService.class.getClassLoader());
        language = in.readString();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Creator<Upcoming> CREATOR = new Creator<Upcoming>() {
        @Override
        public Upcoming createFromParcel(Parcel in) {
            return new Upcoming(in);
        }

        @Override
        public Upcoming[] newArray(int size) {
            return new Upcoming[size];
        }
    };

    @Override
    public ResponseDto makeRequest() {
        return service.getUpcomingMovies(language, getCurrentPage());
    }

    @Override
    public void setInDB(ArrayList<Movie> results) {
        
    }

    @Override
    public String getDBTableString() {
        return "upcoming";
    }

    public void setUpcoming(ArrayList<Movie> finalResults) {
        for( Movie m : finalResults){
            m.canBeFollowed = true;
        }
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
