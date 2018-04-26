package com.example.prarthana.travelapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.AutocompleteResultType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    static List<String> spinnerOptions = new ArrayList<>(asList("Default", "Airport", "Amusement Park", "Aquarium", "Art Gallery", "Bakery", "Bar", "Beauty Salon", "Bowling Alley", "Bus Station", "Cafe", "Campground", "Car Rental", "Casino" , "Lodging", "Movie Theater", "Museum", "Night Club", "Parking", "Restaurant", "Shopping Mall", "Stadium", "Subway Station", "Taxi Station", "Train Station", "Transit Station","Travel Agency", "Zoo"));
    static public String selectedCategory = "default";

    public static final String NEARBY_PLACES = "com.example.prarthana.NEARBYPLACES";

    public static com.seatgeek.placesautocomplete.PlacesAutocompleteTextView searchFromLoc;

    public static String lat = null;
    public static String lng = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
//            Button clearButton = (Button) rootView.findViewById(R.id.clearButton);
            final EditText keywordValue = (EditText) rootView.findViewById(R.id.keywordValue);
//            Spinner categoryValue = (Spinner) rootView.findViewById(R.id.categoryValue);
            final EditText distanceValue = (EditText) rootView.findViewById(R.id.distanceValue);
//            RadioGroup locationRadio = (RadioGroup) rootView.findViewById(R.id.locRadioGroup);
//            EditText customLocText = (EditText) rootView.findViewById(R.id.autocompLoc);

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
                    String keyword = keywordValue.getText().toString();
                    String distance = distanceValue.getText().toString();
                    String url ="http://travelyellowpages.us-east-2.elasticbeanstalk.com/nearbyPlaces?keyword=" + keyword + "&category=" + selectedCategory + "&distance=" + distance + "&hereLatitude=" + lat +  "&hereLongitude=" + lng;
                    Log.d("request : ", url);
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
//                                    mTextView.setText("Response is: "+ response.substring(0,500));
                                    Log.d("VolleyResponse", response);
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


                    // Code here executes on main thread after user presses button
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
}
