package pdm.isel.pt.tmdbapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 12/01/2016.
 */
public class DB {
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_ID = "id_";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_RATING = "rating";
    public static final String MOVIE_RELEASE_DATE = "releaseDate";
    public static final String MOVIE_IMG_URL = "imgUrl";

    private static final int ID_KEY = 1;
    private static final int TITLE_KEY = 2;
    private static final int OVERVIEW_KEY = 3;
    private static final int RATING_KEY = 4;
    private static final int RELEASE_DATE_KEY = 5;


    public SQLiteDatabase db;
    public DBCore db_core;

    public DB(Context context) {
        db_core = new DBCore(context);
        db = db_core.getWritableDatabase();
    }

    public static ContentValues putValues(Movie m){
        ContentValues values = new ContentValues();
        values.put(MOVIE_TITLE, m.getTitle());
        values.put(MOVIE_IMG_URL, m.getPoster_path());
        values.put(MOVIE_ID, m.getId());
        values.put(MOVIE_OVERVIEW, m.getOverview());
        values.put(MOVIE_RELEASE_DATE, m.getRelease_date());
        values.put(MOVIE_RATING, m.getVote_average());
        return values;
    }

    public static ArrayList<Movie> parseCursor(Cursor cursor){
        ArrayList<Movie> l = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                Movie m = new Movie();
                m.setId(cursor.getInt(cursor.getColumnIndex("id_")));
                m.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                m.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                m.setRelease_date(cursor.getString(cursor.getColumnIndex("releaseDate")));
                m.setVote_average(cursor.getDouble(cursor.getColumnIndex("rating")));
                m.setPoster_path(cursor.getString(cursor.getColumnIndex("imgUrl")));
                l.add(m);
            } while(cursor.moveToNext());
        }
        return l;
    }



}
