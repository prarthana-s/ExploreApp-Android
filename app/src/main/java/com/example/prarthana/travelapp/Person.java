package com.example.prarthana.travelapp;

import java.util.ArrayList;
import java.util.List;

class Person {
    String icon;
    String name;
    String vicinity;
    String placeid;

    Person(String icon, String name, String vicinity, String placeid) {
        this.icon = icon;
        this.name = name;
        this.vicinity = vicinity;
        this.placeid = placeid;
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
}
