package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Precious on 10/25/2017.
 */

public class EarthQuakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private String url;


    public String ActivityName = "EarthQuakeLoader";

    public EarthQuakeLoader(Context context, String strings){
        super(context);
        url = strings;
    }




    @Override
    protected void onStartLoading() {
       forceLoad();
        Log.v(ActivityName, "The loader just started running");
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {

        Log.v(ActivityName, "The loader is doing some work in the background");

        ArrayList<Earthquake> result = null;

        if(url == null){
            return result;
        }

        else {

            result = QueryUtils.fetchData(url);
        }



        return result;

    }



}
