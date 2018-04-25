package com.example.prarthana.travelapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.prarthana.travelapp.TabFragment1;
import com.example.prarthana.travelapp.TabFragment2;
import com.example.prarthana.travelapp.TabFragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String selectedPlace;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, String selectedPlaceDetails) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.selectedPlace = selectedPlaceDetails;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("data", selectedPlace);
                TabFragment1 tab1 = new TabFragment1();
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putString("data", selectedPlace);
                TabFragment2 tab2 = new TabFragment2();
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putString("data", selectedPlace);
                TabFragment3 tab3 = new TabFragment3();
                tab3.setArguments(bundle3);
                return tab3;
            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putString("data", selectedPlace);
                TabFragment4 tab4 = new TabFragment4();
                tab4.setArguments(bundle4);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}