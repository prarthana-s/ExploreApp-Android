package com.example.prarthana.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.prarthana.travelapp.MainActivity.NEARBY_PLACES;
import static com.example.prarthana.travelapp.MainActivity.selectedCategory;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.nearbyPlaceViewHolder> {

    List<nearbyPlace> resultsList;
    Context fromContext;

    public static final String SELECTED_PLACE = "com.example.prarthana.SELECTEDPLACE";


    RVAdapter(List<nearbyPlace> allPlaces, Context context) {
        this.resultsList = allPlaces;
        this.fromContext = context;
    }

    public static class nearbyPlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView placeName;
        TextView placeAddress;
        ImageView placePhoto;
        Context innerFromContext;
        String placeId;

        nearbyPlaceViewHolder(View itemView, Context fromContext, String placeid) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            placeName = (TextView) itemView.findViewById(R.id.place_name);
            placeAddress = (TextView) itemView.findViewById(R.id.place_address);
            placePhoto = (ImageView) itemView.findViewById(R.id.place_icon);
            innerFromContext = fromContext;
            placeId = placeid;
        }

        @Override
        public void onClick(View view)
        {
            Integer pos = getAdapterPosition();
            String placeid = placeId;
            final RequestQueue queue = Volley.newRequestQueue(innerFromContext);

            String url ="http://travelyellowpages.us-east-2.elasticbeanstalk.com/placeDetails?placeid=" + placeid;
            Log.d("request : ", url);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("VolleyResponse", response);
                            Intent intent = new Intent(innerFromContext, ShowAllDetails.class);
                            intent.putExtra(SELECTED_PLACE, response);
                            innerFromContext.startActivity(intent);
                        }
                        }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("VolleyError", "error");
                        }
                    });

                    // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    @Override
    public nearbyPlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_card, viewGroup, false);
        nearbyPlaceViewHolder pvh = new nearbyPlaceViewHolder(v, fromContext, resultsList.get(i).getPlaceid());
        return pvh;
    }

    @Override
    public void onBindViewHolder(nearbyPlaceViewHolder personViewHolder, int i) {
        personViewHolder.placeName.setText(resultsList.get(i).getName());
        personViewHolder.placeAddress.setText(resultsList.get(i).getAddress());
        Picasso.get().load(resultsList.get(i).getIcon().toString()).into(personViewHolder.placePhoto);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


