package pdm.isel.pt.tmdbapp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by  on 12/01/2016.
 */
public class DBCore extends SQLiteOpenHelper{

    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "TMDB_DB";

    public DBCore(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table upcoming(pos integer primary key, id_ integer , title text not null, rating integer not null, overview text not null, releaseDate text not null, imgUrl text not null)");
        db.execSQL("create table inTheatre(pos integer primary key, id_ integer , title text not null, rating integer not null, overview text not null, releaseDate text not null, imgUrl text not null)");
        db.execSQL("create table popular(pos integer primary key, id_ integer , title text not null, rating integer not null, overview text not null, releaseDate text not null, imgUrl text not null)");
        db.execSQL("create table name_search(pos integer primary key, id_ integer , title text not null, rating integer not null, overview text not null, releaseDate text not null, imgUrl text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists upcoming");
        db.execSQL("drop table if exists inTheatre");
        db.execSQL("drop table if exists popular");
        db.execSQL("drop table if exists name_search");
        onCreate(db);
    }
}
