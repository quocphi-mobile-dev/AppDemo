package com.example.appdemo_1704.json_models.response;

import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("username")
    private String username;

    @SerializedName("avatarUrl")
    private String avatar;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("isYou")
    private boolean isYou;

    public Friend(String username, String avatar, String fullName, boolean isYou) {
        this.username = username;
        this.avatar = avatar;
        this.fullName = fullName;
        this.isYou = isYou;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isYou() {
        return isYou;
    }

    public void setYou(boolean you) {
        isYou = you;
    }
}
