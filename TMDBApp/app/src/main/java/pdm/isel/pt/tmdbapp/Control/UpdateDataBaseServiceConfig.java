package pdm.isel.pt.tmdbapp.Control;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by  on 13/12/2015.
 */
public class UpdateDataBaseServiceConfig {

    public static final int DAILY = 86400000;
    public static final int WEEKLY = 604800000;


    private final Context context;
    private static final String UPDATE_DATA_BASE_CONFIG = "updateDBConfig";
    private int DEFAULT_INTERVAL = 604800000;
    private String Key = "interval";

    public UpdateDataBaseServiceConfig(Context ctx) {
        this.context = ctx;
    }

    public int getTimeInterval(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(UPDATE_DATA_BASE_CONFIG, Context.MODE_PRIVATE);
        Log.d("TAG", sharedPreferences.getInt(Key, DEFAULT_INTERVAL)+"");
        return sharedPreferences.getInt(Key, DEFAULT_INTERVAL);
    }

    public void setTimeInterval(int value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(UPDATE_DATA_BASE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Key, value);
        editor.apply(); // or commit();
    }



}