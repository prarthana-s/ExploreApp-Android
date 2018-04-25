package com.example.prarthana.travelapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ResultsTable extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results_table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String nearby_places_str = intent.getStringExtra(MainActivity.NEARBY_PLACES);

        JSONObject reader = null;
        List<nearbyPlace> places = new ArrayList<>();

        try {
            reader = new JSONObject(nearby_places_str);
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

        RVAdapter adapter = new RVAdapter(places, this);
        rv.setAdapter(adapter);
    }

}

