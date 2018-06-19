/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;





        import android.content.AsyncTaskLoader;
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;


        import android.app.LoaderManager;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.app.LoaderManager;
        import android.app.LoaderManager.LoaderCallbacks;
        import android.content.Loader;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import java.util.ArrayList;



public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>>{

//    public static final String LOG_TAG = EarthquakeActivity.class.getName();
private static final String SAMPLE_JSON_RESPONSES = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    public String activityName = "EarthquakeActivity";


    private TextView emptyView;

    private ListView earthquakeListView;

    public ProgressBar mainProgress;

    public boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mainProgress = (ProgressBar) findViewById(R.id.loading_spinner);
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(emptyView);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        Log.v(activityName, "This is the initLoader running");


        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
         isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();



    }




    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL

        Log.v(activityName, "This is the onCreateLoaderMethod");



        return new EarthQuakeLoader(this, SAMPLE_JSON_RESPONSES);

    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {

        final ArrayList<Earthquake> quakes = earthquakes;


        if(earthquakes != null && !earthquakes.isEmpty()){

            mainProgress.setVisibility(View.GONE);

            // Create a new {@link ArrayAdapter} of earthquakes
            Fragmenter family = new Fragmenter(EarthquakeActivity.this, quakes);

            // Find a reference to the {@link ListView} in the layout


            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(family);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Earthquake newObject = quakes.get(i);

                    Intent redirect = new Intent(Intent.ACTION_VIEW, Uri.parse(newObject.getUrl()));
                    startActivity(redirect);
                }
            });
        }


        else if(!isConnected) {

             mainProgress.setVisibility(View.GONE);

            emptyView.setText("No Network Connection");
        }


        else if(isConnected) {

             mainProgress.setVisibility(View.GONE);

            emptyView.setText("No EarthQuake data found");
        }





        Log.v(activityName, "This piece of code will execute when the loader has finished fetching the data");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {

    }




}
