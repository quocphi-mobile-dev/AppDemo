package com.example.appdemo_1704.authen_screen;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.adapter.AuthenViewPagerAdapter;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.HomeActivity;
import com.example.appdemo_1704.json_models.response.UserInfo;

public class LoginActivity extends AppCompatActivity  {
    TabLayout tabLayout;
    ViewPager viewPager;
    AuthenViewPagerAdapter authenViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkLogin();
        init();
    }
    private void checkLogin() {
        UserInfo userInfo = RealmContext.getInstance().getUser();
        if(userInfo != null){
            goToHome();
        }
    }

    private void goToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    private void init() {
    tabLayout = findViewById(R.id.tab_layout);
    viewPager = findViewById(R.id.view_pager);
    authenViewPagerAdapter = new AuthenViewPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(authenViewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    }

}
