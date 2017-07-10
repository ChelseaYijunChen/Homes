package com.example.yijunchen.homes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.yijunchen.homes.fragments.TabOneFragment;
import com.example.yijunchen.homes.fragments.TabThreeFragment;
import com.example.yijunchen.homes.fragments.TabTwoFragment;

/**
 * Created by yijunchen on 7/9/17.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public CategoryPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabOneFragment tab1 = new TabOneFragment();
                return tab1;
            case 1:
                TabTwoFragment tab2 = new TabTwoFragment();
                return tab2;
            case 2:
                TabThreeFragment tab3 = new TabThreeFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
