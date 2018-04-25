
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


public class photosRVAdapter extends RecyclerView.Adapter<photosRVAdapter.photosViewHolder> {

    List<Photo> photosList;

    photosRVAdapter(ArrayList<Photo> allPhotos) {
        this.photosList = allPhotos;
    }

    public static class photosViewHolder extends RecyclerView.ViewHolder {
        public static ImageView placePhoto;
        CardView cv;

        photosViewHolder(View itemView) {
            super(itemView);
            placePhoto = (ImageView) itemView.findViewById(R.id.place_photo);
        }

    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    @Override
    public photosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photocard, viewGroup, false);
        photosViewHolder pvh = new photosViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(photosViewHolder personViewHolder, int i) {
        photosViewHolder.placePhoto.setImageBitmap(photosList.get(i).getPhoto());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


