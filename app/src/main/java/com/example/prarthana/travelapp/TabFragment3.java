package com.example.prarthana.travelapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String selectedPlace = getArguments().getString("data");
        return inflater.inflate(R.layout.fragment_tab_fragment3, container, false);
    }
}