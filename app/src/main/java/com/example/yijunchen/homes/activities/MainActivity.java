package com.example.yijunchen.homes.activities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.fragments.AddPropertyFragment;
import com.example.yijunchen.homes.fragments.AllPropertyFragment;
import com.example.yijunchen.homes.fragments.CategoryTabFragment;
import com.example.yijunchen.homes.fragments.Frag_Category;
import com.example.yijunchen.homes.fragments.MyMapFragment;
import com.example.yijunchen.homes.models.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AllPropertyFragment allPropertyFragment = new AllPropertyFragment();
        //final Frag_Category frag_category = new Frag_Category();

        final MyMapFragment myMapFragment = new MyMapFragment();

        final AddPropertyFragment addPropertyFragment = new AddPropertyFragment();

        final CategoryTabFragment categoryTabFragment = new CategoryTabFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, categoryTabFragment).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_homepage:
                        fragment = categoryTabFragment;
                        break;
                    case R.id.action_search:
                        fragment = myMapFragment;
                        break;
//                    case R.id.action_save:
//                        fragment = addPropertyFragment;
//                        break;
                }
                if(fragment == null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, categoryTabFragment).commit();
                           // .replace(R.id.main_fragment_container, allPropertyFragment).commit();
                } else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, fragment).commit();
                }
                return true;
            }
        });

    }
}
