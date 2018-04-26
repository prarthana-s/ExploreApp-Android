package com.example.prarthana.travelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.prarthana.travelapp.MainActivity.NEARBY_PLACES;

public class ResultsTable extends AppCompatActivity {

    private RecyclerView rv;
    String nextToken;
    Button nextButton;
    Button prevButton;
    String[] resultsHistory = {"","",""};
    Integer pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results_table);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Search results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nextButton = (Button) findViewById(R.id.nextButton);
        prevButton = (Button) findViewById(R.id.prevButton);

        nextButton.setEnabled(false);
        prevButton.setEnabled(false);

        final RequestQueue queue = Volley.newRequestQueue(this);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final ProgressDialog pd = new ProgressDialog();
//
//                // Set progress dialog style spinner
//                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//
//                // Set the progress dialog title and message
//                pd.setMessage("Fetching results");
//
//                // Set the progress dialog background color
//                pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));
//
//                pd.setIndeterminate(false);
//
//                // Finally, show the progress dialog
//                pd.show();

                pageNum = pageNum + 1;
                String url = "http://travelyellowpages.us-east-2.elasticbeanstalk.com/nextPage?nextPageToken=" + nextToken;
                Log.d("request : ", url);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("VolleyResponse", response);
                                if (pageNum < 3) {
                                    resultsHistory[pageNum] = response;
                                    Log.d("ADDED", "NEXT ADDED");
                                }
                                constructResultsTable(response, pageNum, true);
//                                pd.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                            mTextView.setText("That didn't work!");
                        Log.d("VolleyError", "error");
                    }
                });

//                 Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum = pageNum - 1;
                constructResultsTable("", pageNum, false);
            }
        });

        Intent intent = getIntent();
        String nearby_places_str = intent.getStringExtra(NEARBY_PLACES);
        resultsHistory[0] = nearby_places_str;
        constructResultsTable(nearby_places_str, pageNum, true);
    }

    private void constructResultsTable(String nearby_places_str, Integer pageNum, Boolean fromNext) {

        if (fromNext == false) {
            nearby_places_str = resultsHistory[pageNum];
            Log.d("PREV:", nearby_places_str);
        }

        JSONObject reader = null;
        List<nearbyPlace> places = new ArrayList<>();

        try {
            reader = new JSONObject(nearby_places_str);
            if (reader.has("next_page_token")) {
                nextToken = reader.getString("next_page_token");
                nextButton.setEnabled(true);
            }
            else {
                nextButton.setEnabled(false);
            }


            if (pageNum > 0) {
                prevButton.setEnabled(true);
            }
            else {
                prevButton.setEnabled(false);
            }

            JSONArray results = reader.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject c = results.getJSONObject(i);
                String icon = c.getString("icon");
                String name = c.getString("name");
                String vicinity = c.getString("vicinity");
                String placeid = c.getString("place_id");

                places.add(new nearbyPlace(icon, name, vicinity, placeid));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        RVAdapter adapter = new RVAdapter(places, this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

