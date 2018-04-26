package com.example.prarthana.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class reviewsRVAdapter extends RecyclerView.Adapter<reviewsRVAdapter.reviewsViewHolder> {

    List<Review> reviewsList;
    Context fromContext;
    String selectedCategory;

    reviewsRVAdapter(List<Review> allReviews, Context context, String selectedCategory) {
        this.reviewsList = allReviews;
        this.fromContext = context;
        this.selectedCategory = selectedCategory;
    }

    public static class reviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        ImageView reviewUserIcon;
        TextView reviewUserName;
        RatingBar reviewUserRating;
        TextView reviewTime;
        TextView reviewBody;
        Context fromInnerContext;
        String url;

        reviewsViewHolder(View itemView, Context fromContext) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.reviewscv);
            cv.setOnClickListener(this);
            reviewUserIcon = (ImageView) itemView.findViewById(R.id.review_icon);
            reviewUserName = (TextView) itemView.findViewById(R.id.review_name);
            reviewUserRating = (RatingBar) itemView.findViewById(R.id.review_rating);
            reviewTime = (TextView) itemView.findViewById(R.id.review_time);
            reviewBody = (TextView) itemView.findViewById(R.id.review_body);
            fromInnerContext = fromContext;
        }

        @Override
        public void onClick(View v) {
            Integer pos = getAdapterPosition();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            fromInnerContext.startActivity(browserIntent);
        }
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    @Override
    public reviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reviewcard, viewGroup, false);
        reviewsViewHolder pvh = new reviewsViewHolder(v, fromContext);
        return pvh;
    }

    @Override
    public void onBindViewHolder(reviewsViewHolder reviewsViewHolder, int i) {
        reviewsViewHolder.reviewUserName.setText(reviewsList.get(i).getName());
        reviewsViewHolder.reviewUserRating.setRating(Float.parseFloat(reviewsList.get(i).getRating()));

        String timestamp = reviewsList.get(i).getTimestamp();
        if (selectedCategory == "Google Reviews") {
            Date date = new Date(Long.parseLong(timestamp) * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            String formattedDate = sdf.format(date);
            reviewsViewHolder.reviewTime.setText(formattedDate);
        }
        else {
            reviewsViewHolder.reviewTime.setText(timestamp);
        }

        reviewsViewHolder.reviewBody.setText(reviewsList.get(i).getReviewBody());
        reviewsViewHolder.url = reviewsList.get(i).getURL();
        Picasso.get().load(reviewsList.get(i).getUserIcon().toString()).into(reviewsViewHolder.reviewUserIcon);


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


