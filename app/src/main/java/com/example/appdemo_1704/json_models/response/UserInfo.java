package com.example.appdemo_1704.json_models.response;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
// extent 1 RealmObject  + 1 constructor TRONG!
public class UserInfo  extends RealmObject {
    @SerializedName("userId")
   private String userID;
    @SerializedName("username")
    private String userName;
    @SerializedName("fullName")
    private String fullname;
    @SerializedName("avatarUrl")
    private String avatarUrL;

    public UserInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo(String userID, String userName, String fullname, String avatarUrL) {
        this.userID = userID;
        this.userName = userName;
        this.fullname = fullname;
        this.avatarUrL = avatarUrL;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatarUrL() {
        return avatarUrL;
    }

    public void setAvatarUrL(String avatarUrL) {
        this.avatarUrL = avatarUrL;
    }


}
