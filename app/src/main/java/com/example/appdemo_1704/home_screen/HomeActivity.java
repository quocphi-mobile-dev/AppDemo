package com.example.appdemo_1704.home_screen;

import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.home_screen.adapter.ViewPageAdapterHome;


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

        tabLayout.getTabAt(0).setIcon(R.drawable.news_feeds);
        tabLayout.getTabAt(1).setIcon(R.drawable.friends);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_IN);


       tabLayout.addOnTabSelectedListener(
               new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            super.onTabSelected(tab);

            tab.getIcon().setColorFilter(getResources().getColor(R.color.colorBlue), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            super.onTabUnselected(tab);

            tab.getIcon().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            super.onTabReselected(tab);
        }
    }
    );

}}



