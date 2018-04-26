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
import android.widget.AdapterView;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TabFragment4 extends Fragment {

    static public String selectedCategory = "Google Reviews";
    static public String selectedSort = "Default Sort";

    public static Boolean googleFlag = false;
    public static Boolean yelpFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View reviewsView = inflater.inflate(R.layout.fragment_tab_fragment4, container, false);

        final String selectedPlace = getArguments().getString("data");
        Log.d("data", selectedPlace);

        final List<Review> googleReviews = new ArrayList<>();
        final List<Review> yelpReviews = new ArrayList<>();

        final List<Review> sortedGoogleReviews = new ArrayList<>();
        final List<Review> sortedYelpReviews = new ArrayList<>();

        JSONObject reader = null;
        JSONArray allGoogleReviews = null;
        final JSONArray allYelpReviews = null;

        // Categories of Reviews
        List<String> categorySpinnerArray =  new ArrayList<String>();
        categorySpinnerArray.add("Google Reviews");
        categorySpinnerArray.add("Yelp Reviews");

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, categorySpinnerArray);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner categoryItems = (Spinner) reviewsView.findViewById(R.id.categorySpinner);
        categoryItems.setAdapter(categorySpinnerAdapter);

        categoryItems.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory =  parent.getItemAtPosition(position).toString();
                if (selectedCategory.equals("Google Reviews") == true) {
                    if (googleFlag == true) {
                        if (selectedSort == "Default Sort") {
                            callAdapter(reviewsView, googleReviews);
                        }
                        else{
                            callAdapter(reviewsView, sortedGoogleReviews);
                        }
                    }
                }
                else {
                    if (yelpFlag == true) {
                        if (selectedSort == "Default Sort") {
                            callAdapter(reviewsView, yelpReviews);
                        }
                        else{
                            callAdapter(reviewsView, sortedYelpReviews);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        sortItems.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSort=  parent.getItemAtPosition(position).toString();
//                callAdapter(reviewsView,reviews);
                Log.d("SELECTED SORT:", selectedSort.toString());

                if (selectedSort.equals("Default Order")) {
                    if (selectedCategory == "Google Reviews") {
                        callAdapter(reviewsView, googleReviews);
                    }
                    else {
                        callAdapter(reviewsView, yelpReviews);
                    }
                }
                else if (selectedSort.equals("Highest Rating")) {
                    Collections.sort(sortedGoogleReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj2.getRating().compareTo(obj1.getRating());
                        }
                    });
                    Collections.sort(sortedYelpReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj2.getRating().compareTo(obj1.getRating());
                        }
                    });

                    if (selectedCategory == "Google Reviews") {
                        callAdapter(reviewsView, sortedGoogleReviews);
                    }
                    else {
                        callAdapter(reviewsView, sortedYelpReviews);
                    }
                }
                else if (selectedSort.equals("Lowest Rating")) {
                    Collections.sort(sortedGoogleReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj1.getRating().compareTo(obj2.getRating());
                        }
                    });
                    Collections.sort(sortedYelpReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj1.getRating().compareTo(obj2.getRating());
                        }
                    });

                    if (selectedCategory == "Google Reviews") {
                        callAdapter(reviewsView, sortedGoogleReviews);
                    }
                    else {
                        callAdapter(reviewsView, sortedYelpReviews);
                    }
                }
                else if (selectedSort.equals("Most Recent")) {
                    Collections.sort(sortedGoogleReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj2.getTimestamp().compareTo(obj1.getTimestamp());
                        }
                    });
                    Collections.sort(sortedYelpReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj2.getTimestamp().compareTo(obj1.getTimestamp());
                        }
                    });

                    if (selectedCategory == "Google Reviews") {
                        callAdapter(reviewsView, sortedGoogleReviews);
                    }
                    else {
                        callAdapter(reviewsView, sortedYelpReviews);
                    }
                }
                else if (selectedSort.equals("Least Recent")) {
                    Collections.sort(sortedGoogleReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj1.getTimestamp().compareTo(obj2.getTimestamp());
                        }
                    });
                    Collections.sort(sortedYelpReviews, new Comparator<Review>() {
                        @Override
                        public int compare(Review obj1, Review obj2) {
                            return obj1.getTimestamp().compareTo(obj2.getTimestamp());
                        }
                    });

                    if (selectedCategory == "Google Reviews") {
                        callAdapter(reviewsView, sortedGoogleReviews);
                    }
                    else {
                        callAdapter(reviewsView, sortedYelpReviews);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");

            allGoogleReviews = results.getJSONArray("reviews");
            for (int i = 0; i < allGoogleReviews.length(); i++) {
                JSONObject review = allGoogleReviews.getJSONObject(i);
                String name = review.getString("author_name");
                String userIcon = review.getString("profile_photo_url");
                String rating = review.getString("rating");
                String timestamp = review.getString("time");
                String reviewBody = review.getString("text");
                String url = review.getString("author_url");

                googleReviews.add(new Review(userIcon, name, rating, timestamp, reviewBody, url));
                sortedGoogleReviews.add(new Review(userIcon, name, rating, timestamp, reviewBody, url));
            }

            googleFlag = true;

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
                        }
                        else if (types.getString(j).equals("locality")) {
                            city = singleAddComp.getString("short_name");
                            try {
                                city = URLEncoder.encode(city, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (types.getString(j).equals("administrative_area_level_1")) {
                            state = singleAddComp.getString("short_name");
                        }
                        else if (types.getString(j).equals("country")) {
                            country = singleAddComp.getString("short_name");
                        }
                        else if (types.getString(j).equals("postal_code")) {
                            postal_code = singleAddComp.getString("short_name");
                        }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//                // Make volley call to Yelp Reviews
            final RequestQueue queue = Volley.newRequestQueue(getActivity());
            String url ="http://travelyellowpages.us-east-2.elasticbeanstalk.com/yelpReviews?name=" + name + "&address1=" + address1 + "&city=" + city + "&state=" + state + "&country=" + country + "&postal_code=" + postal_code;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray allYelpReviews = null;
                            try {
                                allYelpReviews = new JSONArray(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < allYelpReviews.length(); i++) {
                                JSONObject review = null;
                                try {
                                    review = allYelpReviews.getJSONObject(i);
                                    JSONObject userInfo = (JSONObject) review.get("user");
                                    String name = userInfo.getString("name");
                                    String userIcon = userInfo.getString("image_url");
                                    String rating = review.getString("rating");
                                    String timestamp = review.getString("time_created");
                                    String reviewBody = review.getString("text");
                                    String url = review.getString("url");

                                    yelpReviews.add(new Review(userIcon, name, rating, timestamp, reviewBody, url));
                                    sortedYelpReviews.add(new Review(userIcon, name, rating, timestamp, reviewBody, url));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            yelpFlag = true;

                            if (selectedCategory.equals("Yelp Reviews") == true) {
                                callAdapter(reviewsView, yelpReviews);
                            }
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
        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        if (selectedCategory.equals("Google Reviews") == true) {
        callAdapter(reviewsView, googleReviews);
    }

        return reviewsView;
}

    private void callAdapter(View reviewsView, List<Review> reviews) {
        Log.d("ADAPTER:", "Called");
        RecyclerView rv = (RecyclerView) reviewsView.findViewById(R.id.reviewsRV);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        reviewsRVAdapter adapter = new reviewsRVAdapter(reviews, getActivity(), selectedCategory);
        rv.setAdapter(adapter);
    }

}