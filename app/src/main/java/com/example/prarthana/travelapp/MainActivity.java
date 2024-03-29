package com.example.prarthana.travelapp;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String provider;

    static List<String> spinnerOptions = new ArrayList<>(asList("Default", "Airport", "Amusement Park", "Aquarium", "Art Gallery", "Bakery", "Bar", "Beauty Salon", "Bowling Alley", "Bus Station", "Cafe", "Campground", "Car Rental", "Casino" , "Lodging", "Movie Theater", "Museum", "Night Club", "Parking", "Restaurant", "Shopping Mall", "Stadium", "Subway Station", "Taxi Station", "Train Station", "Transit Station","Travel Agency", "Zoo"));
    static public String selectedCategory = "default";

    public static final String NEARBY_PLACES = "com.example.prarthana.NEARBYPLACES";

    public static com.seatgeek.placesautocomplete.PlacesAutocompleteTextView searchFromLoc;

    public static String lat = null;
    public static String lng = null;

    public static String deviceLat = null;
    public static String deviceLng = null;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    static final Integer GPS_SETTINGS = 0x7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        checkLocationPermission();
        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .build();




    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public void nearbyPlacesSearch(View view) {
//
//    }

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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Spinner spinner = (Spinner) rootView.findViewById(R.id.categoryValue);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,spinnerOptions );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new SpinnerActivity());

            Button searchButton = (Button) rootView.findViewById(R.id.searchButton);
            Button clearButton = (Button) rootView.findViewById(R.id.clearButton);
            final EditText keywordValue = (EditText) rootView.findViewById(R.id.keywordValue);
            final Spinner categoryValue = (Spinner) rootView.findViewById(R.id.categoryValue);
            final EditText distanceValue = (EditText) rootView.findViewById(R.id.distanceValue);
            final RadioGroup locationRadio = (RadioGroup) rootView.findViewById(R.id.locRadioGroup);
            final EditText customLocText = (EditText) rootView.findViewById(R.id.autocompLoc);

            final RadioButton otherLocRadio = (RadioButton) rootView.findViewById(R.id.otherLocRadio);

            final TextView keywordError = (TextView) rootView.findViewById(R.id.keywordError);
            final TextView otherLocError = (TextView) rootView.findViewById(R.id.locError);

            searchFromLoc = rootView.findViewById(R.id.autocompLoc);
            searchFromLoc.setResultType(AutocompleteResultType.GEOCODE) ;
            searchFromLoc.setLocationBiasEnabled(true);
            searchFromLoc.setOnPlaceSelectedListener(
                    new OnPlaceSelectedListener() {
                        @Override
                        public void onPlaceSelected(final Place place) {
                            Log.d("PLACE", place.toString());
                            searchFromLoc.getDetailsFor(place, new DetailsCallback() {
                                @Override
                                public void onSuccess(PlaceDetails placeDetails) {
                                    lat = String.valueOf(placeDetails.geometry.location.lat);
                                    lng = String.valueOf(placeDetails.geometry.location.lng);
                                }

                                @Override
                                public void onFailure(Throwable throwable) {

                                }
                            });
                        }
                    }
            );


            final RequestQueue queue = Volley.newRequestQueue(getActivity());
            System.out.println(searchButton);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean validForm = false;

                    String keyword = keywordValue.getText().toString();
                    if (keyword.trim().length() > 0) {
                        validForm = true;
                        keywordError.setVisibility(View.GONE);
                    } else {
                        validForm = false;
                        keywordError.setTextColor(Color.parseColor("#FF0000"));
                        keywordError.setVisibility(View.VISIBLE);

                    }

                    if (otherLocRadio.isChecked()) {
                        String enteredLoc = customLocText.getText().toString();
                        if (enteredLoc.trim().length() > 0) {
                            validForm = true;
                            otherLocError.setVisibility(View.GONE);
                        } else {
                            validForm = false;
                            otherLocError.setTextColor(Color.parseColor("#FF0000"));
                            otherLocError.setVisibility(View.VISIBLE);
                        }
                    }

                    if (validForm) {
                        final ProgressDialog pd = new ProgressDialog(getActivity());

                        // Set progress dialog style spinner
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                        // Set the progress dialog title and message
                        pd.setMessage("Fetching results");

                        // Set the progress dialog background color
                        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

                        pd.setIndeterminate(false);

                        // Finally, show the progress dialog
                        pd.show();

                        String finalLat = "";
                        String finalLng = "";

                        if (otherLocRadio.isChecked()) {
                            finalLat = lat;
                            finalLng = lng;
                        }
                        else {
                            finalLat = deviceLat;
                            finalLng = deviceLng;
                        }

                        String distance = distanceValue.getText().toString();
                        String url = "http://travelyellowpages.us-east-2.elasticbeanstalk.com/nearbyPlaces?keyword=" + keyword + "&category=" + selectedCategory + "&distance=" + distance + "&hereLatitude=" + finalLat + "&hereLongitude=" + finalLng;
                        Log.d("request : ", url);
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
//                                    mTextView.setText("Response is: "+ response.substring(0,500));
                                        Log.d("VolleyResponse", response);
                                        pd.dismiss();
                                        Intent intent = new Intent(getActivity(), ResultsTable.class);
                                        intent.putExtra(NEARBY_PLACES, response);
                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                            mTextView.setText("That didn't work!");
                                Log.d("VolleyError", "error");
                            }
                        });

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                    }
                    else {
                        Context context = getActivity();
                        CharSequence text = "Please fix all fields with errors.";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            });

            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keywordValue.setText("");
                    categoryValue.setSelection(0);
                    distanceValue.setText("");
                    customLocText.setText("");
                    customLocText.clearFocus();
                    locationRadio.clearCheck();
                    locationRadio.check(R.id.currLocRadio);

                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(), false);
            //Request location updates:
            locationManager.requestLocationUpdates(provider, 400, 1, mLocationListener);
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    askForGPS();


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        provider = locationManager.getBestProvider(new Criteria(), false);

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, mLocationListener);

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    private void askForGPS(){
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MainActivity.this, GPS_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        client.disconnect();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            deviceLat = String.valueOf(location.getLatitude());
            deviceLng = String.valueOf(location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
