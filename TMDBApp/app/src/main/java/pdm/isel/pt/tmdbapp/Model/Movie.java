package pdm.isel.pt.tmdbapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


/**
 * Created by francisco on 14/10/2015.
 */
public class Movie implements Parcelable {

    private boolean adult;
    private String backdrop_path;
    private int[] genre_ids;
    private int id; //?
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date; // ou String
    private String poster_path;
    private double popularity;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;
    public boolean canBeFollowed;


    public Movie(String s) {
        this.original_title = s;
    }


    public Movie(boolean adult, String backdropPath, int[] genreIds, int id, String originalLanguage, String originalTitle, String overview, String releaseDate, String posterPath, double popularity, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdrop_path = backdropPath;
        this.genre_ids = genreIds;
        this.id = id;
        this.original_language = originalLanguage;
        this.original_title = originalTitle;
        this.overview = overview;
        this.release_date = releaseDate;
        this.poster_path = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.vote_average = voteAverage;
        this.vote_count = voteCount;
    }

    public Movie() {

    }


    @Override
    public String toString() {
        return title;
    }


    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdrop_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeByte((byte) (canBeFollowed ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(poster_path);
     //   dest.writeIntArray(genre_ids);
        dest.writeInt(id);
        dest.writeInt(vote_count);
        dest.writeDouble(vote_average);
        dest.writeString(release_date);
        dest.writeDouble(popularity);
    }

    public Movie(Parcel in) {
        this.backdrop_path = in.readString();
        this.adult = in.readByte() != 0 ? true : false;
        this.video = in.readByte() != 0 ? true : false;
        this.canBeFollowed = in.readByte() != 0 ? true : false;
        this.overview = in.readString();
        this.title = in.readString();
        this.poster_path = in.readString();
    //    genre_ids = in.readIntArray(genre_ids);
        this.id = in.readInt();
        this.vote_count = in.readInt();
        this.vote_average = in.readDouble();
        this.release_date = in.readString();
        this.popularity = in.readDouble();
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}