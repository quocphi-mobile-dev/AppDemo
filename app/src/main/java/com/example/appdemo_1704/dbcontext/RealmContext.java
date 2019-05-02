package com.example.appdemo_1704.dbcontext;

import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmContext {
    private Realm realm;
    private static RealmContext instance;
    private RealmContext(){
        realm = Realm.getDefaultInstance();
    }

    public  static RealmContext getInstance(){
        if (instance == null){
            instance = new RealmContext();
        }return instance;
    }
    public void  addUser (UserInfo userInfo){
        deleteAllUser();
        realm.beginTransaction();

        UserInfo user = realm.createObject(UserInfo.class);
        user.setUserID(userInfo.getUserID());
        user.setAvatarUrL(userInfo.getAvatarUrL());
        user.setFullname(userInfo.getFullname());

        realm.copyFromRealm(user);
        realm.commitTransaction();

    }

    public UserInfo getUser(){
        return realm.where(UserInfo.class).findFirst();
    }
    public void deleteAllUser(){

        RealmResults<UserInfo> userInfos= realm.where(UserInfo.class).findAll();
        realm.beginTransaction();

        userInfos.deleteAllFromRealm();
        realm.commitTransaction();
    }
}
