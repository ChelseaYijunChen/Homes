package com.example.yijunchen.homes.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.fragments.AddPropertyFragment;
import com.example.yijunchen.homes.fragments.AllPropertyFragment;
import com.example.yijunchen.homes.fragments.CategoryTabFragment;
import com.example.yijunchen.homes.fragments.Frag_Category;
import com.example.yijunchen.homes.fragments.Frag_Filter;
import com.example.yijunchen.homes.fragments.MyMapFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AllPropertyFragment allPropertyFragment = new AllPropertyFragment();

        final Frag_Category frag_category = new Frag_Category();

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
