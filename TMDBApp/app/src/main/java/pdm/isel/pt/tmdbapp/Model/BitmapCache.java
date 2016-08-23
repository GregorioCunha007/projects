package pdm.isel.pt.tmdbapp.Model;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.util.ArrayList;

import pdm.isel.pt.tmdbapp.Control.MainActivity;
import pdm.isel.pt.tmdbapp.View.ImageLoader;

/**
 * Created by  on 25/11/2015.
 */
public class BitmapCache {

    private LruCache<String, Bitmap> mMemoryCache;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;

    public BitmapCache(){
        createLruCache();
    }

    private void createLruCache() {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
