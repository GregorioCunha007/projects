package pdm.isel.pt.tmdbapp.Model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.net.URI;

/**
 * Created by on 12/01/2016.
 */
public class MyContentProvider extends ContentProvider {

    static final int UPCOMING = 1;
    static final int IN_THEATRE = 2;
    static final int POPULAR = 3;
    static final int SEARCH_BY_NAME = 4;

    static final String AUTHORITY = "pdm.isel.pt.tmdbapp.Model.MyContentProvider";
    public static String URL = "content://" + AUTHORITY;
    public static final Uri content = Uri.parse(URL);

    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "/upcoming", UPCOMING);
        uriMatcher.addURI(AUTHORITY, "/inTheatre", IN_THEATRE);
        uriMatcher.addURI(AUTHORITY, "/popular", POPULAR);
        uriMatcher.addURI(AUTHORITY, "/name_search", SEARCH_BY_NAME);
    }

    private DBCore db;

    @Override
    public boolean onCreate() {
        db = new DBCore(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int uriType = uriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriType) {
            case UPCOMING: {
                queryBuilder.setTables("upcoming");


                break;
            }


            case IN_THEATRE: {
                queryBuilder.setTables("inTheatre");

                break;
            }
            case POPULAR: {
                queryBuilder.setTables("popular");

                break;
            }
            case SEARCH_BY_NAME: {
                queryBuilder.setTables("name_search");

                break;
            }

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db.getWritableDatabase(), projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        long id;
        String table;
        switch (uriType) {
            case UPCOMING: {
                id = db.getWritableDatabase().insert("upcoming", null, values);
                table = "upcoming";
                break;
            }
            case IN_THEATRE: {
                id = db.getWritableDatabase().insert("inTheatre", null, values);
                table = "inTheatre";
                break;
            }
            case POPULAR: {
                id = db.getWritableDatabase().insert("popular", null, values);
                table = "popular";
                break;
            }
            case SEARCH_BY_NAME: {
                id = db.getWritableDatabase().insert("name_search", null, values);
                table = "name_search";
                break;
            }

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(URL + "/" + table);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        String tableName="";
        switch (uriType) {
            case POPULAR: {
                tableName = "popular";
                break;
            }
            case SEARCH_BY_NAME: {
                tableName = "name_search";
                break;
            }
        }
        if(tableName != "")
            return db.getWritableDatabase().delete(tableName, null, null);
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
