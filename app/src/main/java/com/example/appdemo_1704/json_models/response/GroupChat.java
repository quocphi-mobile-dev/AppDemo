package com.example.appdemo_1704.json_models.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupChat {
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("groupName")
    private String groupName;
    @SerializedName("lastMessage")
    private String lastMessage;
    @SerializedName("users")
    private ArrayList<UserInfo> users;

    public GroupChat(String groupId, String groupName, String lastMessage, ArrayList<UserInfo> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.lastMessage = lastMessage;
        this.users = users;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ArrayList<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserInfo> users) {
        this.users = users;
    }
}
