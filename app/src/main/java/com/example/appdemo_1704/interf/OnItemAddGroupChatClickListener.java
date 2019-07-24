package com.example.appdemo_1704.interf;

import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.List;

public interface OnItemAddGroupChatClickListener {
    void createMemberChat(List<UserInfo> userInforList);
}
