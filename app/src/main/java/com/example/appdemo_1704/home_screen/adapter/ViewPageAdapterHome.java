package com.example.appdemo_1704.home_screen.adapter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.appdemo_1704.home_screen.fragment.HomeFragment;
import com.example.appdemo_1704.home_screen.fragment.FriendFragment;

public class ViewPageAdapterHome extends FragmentPagerAdapter {
    private final int NUMBER_PAGE = 2;

    public ViewPageAdapterHome(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new HomeFragment();
            case 1:
                return new FriendFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_PAGE;
    }
}
