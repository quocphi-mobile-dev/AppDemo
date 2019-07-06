package com.example.appdemo_1704;

import android.app.Application;

import io.realm.Realm;
// 2 class cần thiết để tạo Realm
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
