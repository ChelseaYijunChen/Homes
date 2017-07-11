package com.example.yijunchen.homes.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.fragments.CategoryTabFragment;
import com.example.yijunchen.homes.fragments.MapFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapFragment mapFragment = new MapFragment();
        final CategoryTabFragment categoryTabFragment = new CategoryTabFragment();

        /* default fragment */
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
                        fragment = mapFragment;
                        break;
//                    case R.id.action_save:
//                        fragment = addPropertyFragment;
//                        break;
                }
                if (fragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, categoryTabFragment).commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, fragment).commit();
                }
                return true;
            }
        });
    }
}
