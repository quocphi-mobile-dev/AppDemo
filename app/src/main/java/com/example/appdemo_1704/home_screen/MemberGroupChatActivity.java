package com.example.appdemo_1704.home_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.AddMemberChatAdapter;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;

import java.util.ArrayList;

public class MemberGroupChatActivity extends AppCompatActivity {
    RetrofitService retrofitService;
    AddMemberChatAdapter messagerFriendAdapter;
    UserInfo userInfo;
    ArrayList<UserInfo> userInfos;
    RealmContext realmContext;
    RecyclerView recyclerView;
    ViewFlipper  viewFlipper;
    TextView tvNameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_group_chat);

    }
}
