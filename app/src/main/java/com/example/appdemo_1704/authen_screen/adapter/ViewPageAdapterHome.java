package com.example.appdemo_1704.authen_screen.adapter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.fragment.HomeFragment;
import com.example.appdemo_1704.authen_screen.fragment.HomeYourFragment;
import com.example.appdemo_1704.authen_screen.fragment.LoginFragment;
import com.example.appdemo_1704.authen_screen.fragment.RegisterFragment;

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
                return new HomeYourFragment();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "News Feed";
            case 1:
                return "Friends";
        }
        return null;
    }


    @Override
    public int getCount() {
        return NUMBER_PAGE;
    }
}
