package com.example.appdemo_1704.json_models.request;

import com.google.gson.annotations.SerializedName;

public class CreatePostSendForm {
    @SerializedName("userId")
    private String userId;
    @SerializedName("content")
    private String content;

    public CreatePostSendForm(String userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
