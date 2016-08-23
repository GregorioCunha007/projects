package pdm.isel.pt.tmdbapp.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Pedro on 05/12/2015.
 */
public class MoviesFollowedFile {

    private static final String FILE_NAME = "Followed";
    private Context ctx;

    public MoviesFollowedFile(Context ctx) {
        this.ctx = ctx;
    }

    public void unfollow(int movieId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(movieId + "");
        editor.commit();
    }

    public boolean contains(int movieId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        boolean ret = sharedPreferences.contains(movieId + "");
        return ret;
    }

    public void follow(int movieId) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(movieId + "", movieId);
        editor.commit();
    }

}
