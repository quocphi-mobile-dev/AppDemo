package com.example.appdemo_1704.home_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.AddMemberChatAdapter;
import com.example.appdemo_1704.interf.OnItemAddGroupChatClickListener;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberChatActivity extends AppCompatActivity implements OnItemAddGroupChatClickListener {
    RetrofitService retrofitService;
    AddMemberChatAdapter messagerFriendAdapter;
    UserInfo userInfo;
    ArrayList<Friend> friendList;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    TextView tvNameGroup;
    ImageView ivOk, ivBack;
    EditText edtNameGroup;
    String nameGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member_chat);
        init();
        getAllFriend();
        addListener();
    }

    private void addListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_list_friend);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        ivOk = findViewById(R.id.iv_ok);
        ivBack = findViewById(R.id.iv_back);
        userInfo = RealmContext.getInstance().getUser();

        friendList = new ArrayList<>();
        messagerFriendAdapter = new AddMemberChatAdapter(friendList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddMemberChatActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messagerFriendAdapter);


    }

    private void getAllFriend() {
        retrofitService.getAllFriend(userInfo.getUserID()).enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(Call<ArrayList<Friend>> call, Response<ArrayList<Friend>> response) {
                ArrayList<Friend> friends = response.body();
                if (response.code() == 200 && friends != null && !friends.isEmpty()) {
                    friendList.clear();
                    friendList.addAll(friends);
                    messagerFriendAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(2);
                } else {
                    viewFlipper.setDisplayedChild(1);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Friend>> call, Throwable t) {
                Utils.showToast(AddMemberChatActivity.this, " no internet");
            }
        });

    }

    @Override
    public void createMemberChat(List<UserInfo> userInforList) {

    }

}
