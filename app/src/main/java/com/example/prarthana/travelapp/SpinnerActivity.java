package com.example.prarthana.travelapp;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.prarthana.travelapp.MainActivity;
public class SpinnerActivity extends MainActivity implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> adapter, View view,
                               int pos, long id) {

        selectedCategory =  adapter.getItemAtPosition(pos).toString();
        if (selectedCategory == "Amusement Park") {
            selectedCategory = "amusement_park";
        }
        else if (selectedCategory == "Art Gallery") {
            selectedCategory = "art_gallery";
        }
        else if (selectedCategory == "Beauty Salon") {
            selectedCategory = "beauty_salon";
        }
        else if (selectedCategory == "Bowling Alley") {
            selectedCategory = "bowling_alley";
        }
        else if (selectedCategory == "Bus Station") {
            selectedCategory = "bus_station";
        }
        else if (selectedCategory == "Car Rental") {
            selectedCategory = "car_rental";
        }
        else if (selectedCategory == "Movie Theatre") {
            selectedCategory = "movie_theatre";
        }
        else if (selectedCategory == "Night Club") {
            selectedCategory = "night_club";
        }
        else if (selectedCategory == "Shopping Mall") {
            selectedCategory = "shopping_ma;;";
        }
        else if (selectedCategory == "Subway Station") {
            selectedCategory = "subway_station";
        }
        else if (selectedCategory == "Taxi Station") {
            selectedCategory = "taxi_station";
        }
        else if (selectedCategory == "Train Station") {
            selectedCategory = "train_station";
        }
        else if (selectedCategory == "Transit Station") {
            selectedCategory = "transit_station";
        }
        else if (selectedCategory == "Travel Agency") {
            selectedCategory = "travel_agency";
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}