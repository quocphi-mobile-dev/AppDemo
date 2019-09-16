package com.example.appdemo_1704.json_models.request;

import com.google.gson.annotations.SerializedName;

public class UpdateAvatarUri {
    @SerializedName("avatarUrl")
    public  String avatarUrl ;

    public UpdateAvatarUri(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
