package com.example.yijunchen.homes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.yijunchen.homes.fragments.All_Property_Test;

import java.util.ArrayList;

/**
 * Created by zhangwenpurdue on 7/7/2017.
 */

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;
    private ArrayList<All_Property_Test> viewPagerFragments;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setTitles(String[] titles) {
        this.titles = titles;
    }
    public void setFragments(ArrayList<All_Property_Test> fragments) {
        viewPagerFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return viewPagerFragments.get(position);
    }

    @Override
    public int getCount() {
        return viewPagerFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
