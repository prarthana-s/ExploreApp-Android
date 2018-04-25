package com.example.prarthana.travelapp;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import static com.example.prarthana.travelapp.ShowAllDetails.selectedCategory;

public class categorySpinner extends ShowAllDetails implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> adapter, View view,
                               int pos, long id) {

        selectedCategory =  adapter.getItemAtPosition(pos).toString();
        Log.d("SELECTED CAT:", selectedCategory.toString());
//        if (selectedCategory == "Amusement Park") {
//            selectedCategory = "amusement_park";
//        }
//        else if (selectedCategory == "Art Gallery") {
//            selectedCategory = "art_gallery";
//        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}