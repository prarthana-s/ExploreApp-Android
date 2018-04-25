package com.example.prarthana.travelapp;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import static com.example.prarthana.travelapp.ShowAllDetails.selectedSort;

public class sortSpinner extends ShowAllDetails implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> adapter, View view,
                               int pos, long id) {

        selectedSort =  adapter.getItemAtPosition(pos).toString();
//        Log.d("SELECTED SORT:", selectedSort.toString());
////        if (selectedCategory == "Amusement Park") {
////            selectedCategory = "amusement_park";
////        }
////        else if (selectedCategory == "Art Gallery") {
////            selectedCategory = "art_gallery";
////        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}