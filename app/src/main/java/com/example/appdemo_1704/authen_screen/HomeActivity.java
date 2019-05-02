package com.example.appdemo_1704.authen_screen;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.adapter.ViewPageAdapterHome;
import com.example.appdemo_1704.authen_screen.adapter.ViewPagerAdapter;
import com.example.appdemo_1704.network.RetrofitService;


public class HomeActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapterHome viewPageAdapterHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPageAdapterHome = new ViewPageAdapterHome(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapterHome);
        tabLayout.setupWithViewPager(viewPager);
    }

}



