package com.example.prarthana.travelapp;

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


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    List<Person> resultsList;

    RVAdapter(List<Person> allPlaces) {
        this.resultsList = allPlaces;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            cv.setOnClickListener(this);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }

        @Override
        public void onClick(View view)
        {
//            final RequestQueue queue = Volley.newRequestQueue(this);
            Log.d("clicked:", "clicked");
            Log.d("clicked on:", String.valueOf(getAdapterPosition()));
//            String url ="http://travelyellowpages.us-east-2.elasticbeanstalk.com/nearbyPlaces?keyword=" + keyword + "&category=" + selectedCategory + "&distance=" + distance + "&hereLatitude=34.0266&hereLongitude=-118.2831";
//            Log.d("request : ", url);
//            // Request a string response from the provided URL.
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Display the first 500 characters of the response string.
////                                    mTextView.setText("Response is: "+ response.substring(0,500));
//                            Log.d("VolleyResponseDETAILS", response);
////                            Intent intent = new Intent(getActivity(), ResultsTable.class);
////                            intent.putExtra(NEARBY_PLACES, response);
////                            startActivity(intent);
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
////                            mTextView.setText("That didn't work!");
//                    Log.d("VolleyError", "error");
//                }
//            });
//
//            // Add the request to the RequestQueue.
//            queue.add(stringRequest);


        }
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_card, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(resultsList.get(i).getName());
        personViewHolder.personAge.setText(resultsList.get(i).getAddress());
        Picasso.get().load(resultsList.get(i).getIcon().toString()).into(personViewHolder.personPhoto);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


