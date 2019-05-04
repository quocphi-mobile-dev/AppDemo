package com.example.appdemo_1704.json_models.request;

import com.google.gson.annotations.SerializedName;

public class CreateStatusSendForm {
    @SerializedName("userId")
    private String userID;
    @SerializedName("content")
    private String content;

    public CreateStatusSendForm(String userID, String content) {
        this.userID = userID;
        this.content = content;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
