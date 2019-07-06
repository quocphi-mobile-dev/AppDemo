package com.example.appdemo_1704.json_models.request;

import com.google.gson.annotations.SerializedName;

public class LikeStatustSendForm {
    @SerializedName("userId")
    private String userID;
    @SerializedName("postId")
    private String postId;

    public LikeStatustSendForm(String userID, String postId) {
        this.userID = userID;
        this.postId = postId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
