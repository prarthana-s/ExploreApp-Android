package com.example.prarthana.travelapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TabFragment2 extends Fragment {

    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View photosView =  inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        String place_id = null;
        String selectedPlace = getArguments().getString("data");

        JSONObject reader = null;
        try {
            reader = new JSONObject(selectedPlace);
            JSONObject results = (JSONObject) reader.get("result");
            place_id = results.getString("place_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(getActivity());

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity());

        getPhotos(photosView, place_id);


        return photosView;
    }

    // Request photos and metadata for the specified place.
    private void getPhotos(final View photosViewRef, String place_id) {
        final ArrayList<Photo> photosContainer = new ArrayList<>();

        final String placeId = place_id;
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                final Integer numPhotos = photoMetadataBuffer.getCount();
                Log.d("numPhotos", numPhotos.toString());
                for (int i = 0; i < numPhotos; i++) {
                    PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(i);
                    // Get the attribution text.
                    CharSequence attribution = photoMetadata.getAttributions();
                    // Get a full-size bitmap for the photo.
                    Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            Log.d("finished", "done!");
                            photosContainer.add(new Photo(bitmap));

                            if (photosContainer.size() == numPhotos) {
                                RecyclerView rv = (RecyclerView) photosViewRef.findViewById(R.id.photosRV);

                                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                rv.setLayoutManager(layoutManager);

                                photosRVAdapter adapter = new photosRVAdapter(photosContainer);
                                rv.setAdapter(adapter);
                            }

                        }
                    });
                }
                photoMetadataBuffer.release();

            }
        });
    }

}