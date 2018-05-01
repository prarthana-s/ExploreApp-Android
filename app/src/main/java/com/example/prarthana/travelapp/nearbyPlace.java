package com.example.prarthana.travelapp;

import org.json.JSONObject;

public class nearbyPlace {
    String icon;
    String name;
    String vicinity;
    String placeid;
    JSONObject allDetails;
    Boolean isFav;

    nearbyPlace(String icon, String name, String vicinity, String placeid, JSONObject allDetails, Boolean isFav) {
        this.icon = icon;
        this.name = name;
        this.vicinity = vicinity;
        this.placeid = placeid;
        this.allDetails = allDetails;
        this.isFav = isFav;
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getAddress() {
        return this.vicinity;
    }

    public String getPlaceid() {
        return this.placeid;
    }

    public JSONObject getAllDetails() {
        return this.allDetails;
    }

    public Boolean getIsFav() {
        return this.isFav;
    }

    public void setIsFav(Boolean val) { this.isFav = val; }
}
