package com.example.yijunchen.homes.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.adapters.CategoryPagerAdapter;

/**
 * Created by yijunchen on 7/9/17.
 */

public class CategoryTabFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category_tablayout, container, false);

        tabLayout = (TabLayout) v.findViewById(R.id.category_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Rent"));
        tabLayout.addTab(tabLayout.newTab().setText("Outright Purchase"));
        tabLayout.addTab(tabLayout.newTab().setText("Mortgage"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);

        //Initializing viewPager
        viewPager = (ViewPager) v.findViewById(R.id.category_viewpager);

        //Creating our pager adapter
        CategoryPagerAdapter adapter = new CategoryPagerAdapter(fm, tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

        return v;
    }
}
