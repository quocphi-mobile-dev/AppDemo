package com.example.appdemo_1704.json_models.response;

import com.google.gson.annotations.SerializedName;

public class PostInfor {

    @SerializedName("postId")
    private String postId;
    @SerializedName("author")
    private String author;
    @SerializedName("authorName")
    private String authorName;
    @SerializedName("authorAvatar")
    private String authorAvatar;
    @SerializedName("content")
    private String content;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("numberLike")
    private String numberLike;
    @SerializedName("numberComment")
    private String numberComment;
    @SerializedName("isLike")
    private String isLike;

    public PostInfor(String postId, String author, String authorName, String authorAvatar,
                     String content, String createDate, String numberLike, String numberComment, String isLike) {
        this.postId = postId;
        this.author = author;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
        this.content = content;
        this.createDate = createDate;
        this.numberLike = numberLike;
        this.numberComment = numberComment;
        this.isLike = isLike;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNumberLike() {
        return numberLike;
    }

    public void setNumberLike(String numberLike) {
        this.numberLike = numberLike;
    }

    public String getNumberComment() {
        return numberComment;
    }

    public void setNumberComment(String numberComment) {
        this.numberComment = numberComment;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}
