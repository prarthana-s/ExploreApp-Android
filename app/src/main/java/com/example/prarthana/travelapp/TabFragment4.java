package com.example.prarthana.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TabFragment4 extends Fragment {

    static public String selectedCategory = "Google Reviews";
    static public String selectedSort = "Default Sort";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View reviewsView = inflater.inflate(R.layout.fragment_tab_fragment4, container, false);

        // Categories of Reviews
        List<String> categorySpinnerArray =  new ArrayList<String>();
        categorySpinnerArray.add("Google Reviews");
        categorySpinnerArray.add("Yelp Reviews");

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, categorySpinnerArray);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner categoryItems = (Spinner) reviewsView.findViewById(R.id.categorySpinner);
        categoryItems.setAdapter(categorySpinnerAdapter);
        categoryItems.setOnItemSelectedListener(new categorySpinner());

        // Sort options of reviews
        // you need to have a list of data that you want the spinner to display
        List<String> sortSpinnerArray =  new ArrayList<String>();
        sortSpinnerArray.add("Default Order");
        sortSpinnerArray.add("Highest Rating");
        sortSpinnerArray.add("Lowest Rating");
        sortSpinnerArray.add("Most Recent");
        sortSpinnerArray.add("Least Recent");

        ArrayAdapter<String> sortSpinnerAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, sortSpinnerArray);

        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sortItems = (Spinner) reviewsView.findViewById(R.id.sortSpinner);
        sortItems.setAdapter(sortSpinnerAdapter);
        sortItems.setOnItemSelectedListener(new sortSpinner());

        String selectedPlace = getArguments().getString("data");
        Log.d("data", selectedPlace);

        final List<Review> reviews = new ArrayList<>();

        JSONObject reader = null;
        JSONArray allGoogleReviews = null;
        final JSONArray allYelpReviews = null;

        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");

            if (ShowAllDetails.selectedCategory.equals("Google Reviews")) {
                allGoogleReviews = results.getJSONArray("reviews");
                for (int i = 0; i < allGoogleReviews.length(); i++) {
                    JSONObject review = allGoogleReviews.getJSONObject(i);
                    String name = review.getString("author_name");
                    String userIcon = review.getString("profile_photo_url");
                    String rating = review.getString("rating");
                    String timestamp = review.getString("time");
                    String reviewBody = review.getString("text");

                    reviews.add(new Review(userIcon, name, rating, timestamp, reviewBody));
                }
            }
            else {
                String name = "";
                String address1 = "";
                String city = "";
                String state = "";
                String country = "";
                String postal_code = "";

                try {

                    name = results.get("name").toString();
                    try {
                        name = URLEncoder.encode(name, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    JSONArray address_components = results.getJSONArray("address_components");
                    for (int i = 0; i < address_components.length(); i++) {

                        JSONObject singleAddComp = address_components.getJSONObject(i);
                        JSONArray types = singleAddComp.getJSONArray("types");
                        for (int j = 0; j < types.length(); j++)
                            if (types.getString(j).equals("route")) {
                                address1 = singleAddComp.getString("short_name");
                                try {
                                    address1 = URLEncoder.encode(address1, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.d("found ROUTE:", "FOUND");
                            }
                            else if (types.getString(j).equals("locality")) {
                                city = singleAddComp.getString("short_name");
                                try {
                                    city = URLEncoder.encode(city, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.d("found CITY:", "FOUND");
                            }
                            else if (types.getString(j).equals("administrative_area_level_1")) {
                                state = singleAddComp.getString("short_name");
                                Log.d("found STATE:", "FOUND");
                            }
                            else if (types.getString(j).equals("country")) {
                                country = singleAddComp.getString("short_name");
                                Log.d("found COUNTRY:", "FOUND");
                            }
                            else if (types.getString(j).equals("postal_code")) {
                                postal_code = singleAddComp.getString("short_name");
                                Log.d("found CODE:", "FOUND");
                            }
                    }
                } catch (JSONException e) {
                e.printStackTrace();
            }

//                // Make volley call to Yelp Reviews
                final RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url ="http://travelyellowpages.us-east-2.elasticbeanstalk.com/yelpReviews?name=" + name + "&address1=" + address1 + "&city=" + city + "&state=" + state + "&country=" + country + "&postal_code=" + postal_code;
                Log.d("request : ", url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONArray allYelpReviews = null;
                                        try {
                                            Log.d("RESPONSE", response);
                                            allYelpReviews = new JSONArray(response);
                                            Log.d("YELP:", allYelpReviews.toString());
//                                            JSONObject yelpResults = (JSONObject) yelpReader.get("reviews");
//                                        allYelpReviews = results.getJSONArray("reviews");
//                                            Log.d("VolleyResponse", yelpReader.toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        for (int i = 0; i < allYelpReviews.length(); i++) {
                                            JSONObject review = null;
                                            try {
                                                review = allYelpReviews.getJSONObject(i);
                                                Log.d("REIVEW OBJ:", review.toString());
                                                JSONObject userInfo = (JSONObject) review.get("user");
                                                String name = userInfo.getString("name");
                                                String userIcon = userInfo.getString("image_url");
                                                String rating = review.getString("rating");
                                                String timestamp = review.getString("time_created");
                                                String reviewBody = review.getString("text");

                                                reviews.add(new Review(userIcon, name, rating, timestamp, reviewBody));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                        RecyclerView rv = (RecyclerView) reviewsView.findViewById(R.id.reviewsRV);

                                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        rv.setLayoutManager(layoutManager);

                                        reviewsRVAdapter adapter = new reviewsRVAdapter(reviews, getActivity());
                                        rv.setAdapter(adapter);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewsView;
    }
}