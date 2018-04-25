package com.example.prarthana.travelapp;

import android.graphics.Bitmap;

class Photo {
    Bitmap photo;

    Photo(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return this.photo;
    }

}

