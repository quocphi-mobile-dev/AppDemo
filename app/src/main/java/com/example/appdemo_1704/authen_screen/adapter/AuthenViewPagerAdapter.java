package com.example.appdemo_1704.authen_screen.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.appdemo_1704.authen_screen.fragment.LoginFragment;
import com.example.appdemo_1704.authen_screen.fragment.RegisterFragment;
// tạo một class để căng các viewpager : cần phải kế thừa FragmentPagerAdapter
public class AuthenViewPagerAdapter extends FragmentPagerAdapter {
    private final int NUMBER_PAGE = 2;

    public AuthenViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
        }
        return null;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Resigter";
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_PAGE;
    }
}
