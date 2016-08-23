package pdm.isel.pt.tmdbapp.Model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import pdm.isel.pt.tmdbapp.Control.IQueryListener;

/**
 * Created by francisco on 21/10/2015.
 */
public abstract class RequestedOrder implements Parcelable{

    private static final int ORDER_TO_SUM = 19;
    protected int page;
    public TMDBService service;
    public String language;
    protected ArrayList<Movie> results;
    protected List<ArrayList<Movie>> tempMemory;

    public RequestedOrder(){
        tempMemory = null;
    }

    public void setService(TMDBService service){
        this.service = service;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public abstract ResponseDto makeRequest();

    public int getCurrentPage() {
        return page;
    }

    public void setPageInit(){
        page = 1;
    }

    public void prevPage() {
        --page;
    }

    public void nextPage(){
        ++page;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public abstract void setInDB(ArrayList<Movie> results);

    public  ArrayList<Movie> getPreviousPage(String table, IQueryListener ctx){

       return ctx.callQuery(table, page);

    }

    public abstract String getDBTableString();
}

