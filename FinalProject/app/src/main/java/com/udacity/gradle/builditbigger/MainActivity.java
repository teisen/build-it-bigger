package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.steelgirderdev.builditbigger.jokebackend.myApi.MyApi;
import com.steelgirderdev.jokedisplaylibrary.DisplayActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * called by the view to tell a joke.
     * Calls the endpoint async
     * @param view
     */
    public void tellJoke(View view){
        EndpointsAsyncTask async = new EndpointsAsyncTask();
        Pair param = new Pair<Context, View>(this, view);
        async.execute(param);
    }

    /**
     * Launched an Intent with the Text of a Joke to display.
     * Called by the async Task onPostExecute
     * @param view
     * @param joke
     */
    public void launchLibraryActivity(View view, String joke){
        Intent myIntent = new Intent(this, DisplayActivity.class);
        myIntent.putExtra(DisplayActivity.JOKE_KEY, joke);
        startActivity(myIntent);
    }

    /**
     * Inner class that performs the async task to get the Joke from the GCE
     * Source/Tutorial: https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
     */
    class EndpointsAsyncTask extends AsyncTask<Pair<Context, View>, Void, String> {
        private MyApi myApiService = null;
        private Context context; //for future use
        private View view;

        @Override
        protected String doInBackground(Pair<Context, View>... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            view = params[0].second;

            try {
                return myApiService.sayHi("tellMeAJoke").execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            launchLibraryActivity(view, result);
        }
    }


}
