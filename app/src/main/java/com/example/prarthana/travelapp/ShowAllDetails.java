package com.example.prarthana.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class ShowAllDetails extends AppCompatActivity {

    static public String selectedCategory = "Google Reviews";
    static public String selectedSort = "Default Sort";
    public static Menu copyMenu;
    public MenuItem favIcon;
    Button twitterShare;

    public String name = "";
    public String website = "";
    public String address = "";
    public String placeid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_details);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        String selected_place_str = intent.getStringExtra(RVAdapter.SELECTED_PLACE);

        JSONObject reader = null;
        try {
            reader = new JSONObject(selected_place_str);
            JSONObject results = (JSONObject) reader.get("result");

            name = results.getString("name");

            address = results.getString("formatted_address");

            placeid = results.getString("place_id");

            if (results.has("website")) {
                website = results.getString("website");
            } else {
                if (results.has("url")) {
                    website = results.getString("url");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        getSupportActionBar().setTitle(name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // In `OnCreate();`

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.addTab(tabLayout.newTab().setText("PHOTOS"));
        tabLayout.addTab(tabLayout.newTab().setText("MAPS"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEWS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), selected_place_str);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        favIcon = menu.findItem(R.id.detailsFavIcon);
        copyMenu = menu;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String isInSharedPref = preferences.getString(placeid, "");

        if(isInSharedPref != null && !isInSharedPref.isEmpty()) {
            ImageView imageView = new ImageView(this);
            imageView.setMaxHeight(18);
            imageView.setMaxWidth(18);
            imageView.setImageResource(R.drawable.heart_fill_white);
            favIcon.setActionView(imageView);
        }
        else {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.heart_outline_white);
            favIcon.setActionView(imageView);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.twitterShare:
                String text = "Check out " + name + " located at " + address + ". Website: " + website;
                String tweetURL = "";
                try {
                    tweetURL = "https://twitter.com/intent/tweet?text=" + URLEncoder.encode(text, "UTF-8") + "&hashtags=TravelAndEntertainmentSearch"  ;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("TWITTER","TWITTER CLICKED");
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(tweetURL));
                startActivity(intent);
                return true;

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}