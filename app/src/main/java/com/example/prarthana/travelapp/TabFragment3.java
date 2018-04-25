package com.example.prarthana.travelapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class TabFragment3 extends Fragment implements OnMapReadyCallback {

    Double lat = null;
    Double lng = null;
    String name = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapsView = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);

        String selectedPlace = getArguments().getString("data");

        JSONObject reader = null;
        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");
            name = results.getString("name");

            JSONObject geometry = (JSONObject) results.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            lat = Double.parseDouble(location.getString("lat"));
            lng = Double.parseDouble(location.getString("lng"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return mapsView;
    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng destCoords = new LatLng(lat, lng);
        Marker marker = googleMap.addMarker(new MarkerOptions().position(destCoords)
                .title(name));
        marker.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destCoords, 15.0f));
    }
}