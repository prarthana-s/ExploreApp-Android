package com.example.prarthana.travelapp;

import android.animation.FloatArrayEvaluator;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.prarthana.travelapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View infoView =  inflater.inflate(R.layout.fragment_tab_fragment1, container, false);
        String selectedPlace = getArguments().getString("data");
        Log.d("data", selectedPlace);

        JSONObject reader = null;

        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");

            String address = results.getString("formatted_address");
            TextView addressValue = (TextView) infoView.findViewById(R.id.infoAddressData);
            addressValue.setText(address);

            String phone = results.getString("formatted_phone_number");
            TextView phoneValue = (TextView) infoView.findViewById(R.id.infoPhoneData);
            phoneValue.setText(phone);

            String price = results.getString("price_level");
            String priceView = convertPriceToDollars(price);
            TextView priceValue = (TextView) infoView.findViewById(R.id.infoPriceData);
            priceValue.setText(priceView);

            String rating = results.getString("rating");
            float ratingFloat = Float.parseFloat(rating);
            RatingBar ratingBar = (RatingBar) infoView.findViewById(R.id.infoRatingBar);
            ratingBar.setRating(ratingFloat);

            String google = results.getString("url");
            TextView googleValue = (TextView) infoView.findViewById(R.id.infoGoogleData);
            googleValue.setText(google);

            String website = results.getString("website");
            TextView websiteValue = (TextView) infoView.findViewById(R.id.infoWebsiteData);
            websiteValue.setText(website);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return infoView;
    }

    private String convertPriceToDollars(String price) {
        Integer intPrice = Integer.parseInt(price);

        if (intPrice >= 0 && intPrice < 1) {
            return "0";
        }
        else if (intPrice >= 1 && intPrice < 2) {
            return "$";
        }
        else if (intPrice >= 2 && intPrice < 3) {
            return "$$";
        }
        else if (intPrice >= 3 && intPrice < 4) {
            return "$$$";
        }
        else {
            return "$$$$";
        }

    }
}