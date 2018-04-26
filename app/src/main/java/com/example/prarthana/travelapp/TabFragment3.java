package com.example.prarthana.travelapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.prarthana.travelapp.TabFragment4.googleFlag;

public class TabFragment3 extends Fragment implements OnMapReadyCallback {

    Double lat = null;
    Double lng = null;
    String name = null;
    LatLng origin = null;
    LatLng destination = null;
    com.seatgeek.placesautocomplete.PlacesAutocompleteTextView fromLoc;
    GoogleMap copyOfGoogleMap;
    String selectedTravelMode = TransportMode.DRIVING;
    Boolean directionsMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mapsView = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
        // Categories of Reviews
        List<String> travelModes =  new ArrayList<String>();
        travelModes.add("Driving");
        travelModes.add("Bicycling");
        travelModes.add("Transit");
        travelModes.add("Walking");

        ArrayAdapter<String> travelModesAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, travelModes);

        travelModesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner travelModesSpinner = (Spinner) mapsView.findViewById(R.id.travelMode);
        travelModesSpinner.setAdapter(travelModesAdapter);

        travelModesSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTravelMode = parent.getItemAtPosition(position).toString();
                if (selectedTravelMode.equals("Driving")) {
                    selectedTravelMode = TransportMode.DRIVING ;
                }
                else if (selectedTravelMode.equals("Bicycling")) {
                    selectedTravelMode = TransportMode.BICYCLING;
                }
                else if (selectedTravelMode.equals("Transit")) {
                    selectedTravelMode = TransportMode.TRANSIT;
                }
                else if (selectedTravelMode.equals("Walking")) {
                    selectedTravelMode = TransportMode.WALKING;
                }
                Log.d("selected mode:", selectedTravelMode);
                if (directionsMode == true) {
                    generateDirections();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        String selectedPlace = getArguments().getString("data");


        fromLoc = mapsView.findViewById(R.id.fromLocAutocomplete);
        fromLoc.setResultType(AutocompleteResultType.GEOCODE) ;
        fromLoc.setLocationBiasEnabled(true);
        fromLoc.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        Log.d("PLACE", place.toString());
                        fromLoc.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(PlaceDetails placeDetails) {
                                origin = new LatLng(placeDetails.geometry.location.lat, placeDetails.geometry.location.lng);
                                name = placeDetails.name;
                                generateDirections();
                            }

                            @Override
                            public void onFailure(Throwable throwable) {

                            }
                        });
                        // do something awesome with the selected place
                    }
                }
        );

        JSONObject reader = null;
        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");
            name = results.getString("name");

            JSONObject geometry = (JSONObject) results.get("geometry");
            JSONObject location = (JSONObject) geometry.get("location");
            lat = Double.parseDouble(location.getString("lat"));
            lng = Double.parseDouble(location.getString("lng"));
            destination = new LatLng(lat,lng);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return mapsView;
    }

    private void generateDirections() {
        directionsMode = true;
        GoogleDirection.withServerKey("AIzaSyANFqTMYqnjkhbs9BQ5BYdd8zc2RtvzcFc")
                .from(destination)
                .to(origin)
                .transportMode(selectedTravelMode)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if(status.equals(RequestResult.OK)) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(getActivity(), directionPositionList, 5, Color.RED);
                            copyOfGoogleMap.clear();
                            LatLng originCoords = origin;
                            LatLng destCoords = destination;
                            Marker marker = copyOfGoogleMap.addMarker(new MarkerOptions().position(originCoords)
                                    .title(name));
                            Marker marker2 = copyOfGoogleMap.addMarker(new MarkerOptions().position(destCoords));
                            marker.showInfoWindow();

                            copyOfGoogleMap.addPolyline(polylineOptions);
                        } else if(status.equals(RequestResult.NOT_FOUND)) {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });
    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        copyOfGoogleMap = googleMap;
        LatLng destCoords = new LatLng(lat, lng);
        Marker marker = googleMap.addMarker(new MarkerOptions().position(destCoords)
                .title(name));
        marker.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destCoords, 15.0f));
    }




}