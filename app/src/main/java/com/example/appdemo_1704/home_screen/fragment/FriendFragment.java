package com.example.appdemo_1704.home_screen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.LoginActivity;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.FriendAdapter;
import com.example.appdemo_1704.home_screen.adapter.StatusAdapter;
import com.example.appdemo_1704.interf.OnItemFriendClickListener;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment implements OnItemFriendClickListener {

    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ArrayList<Friend> friendList;
    Button btnLogOut;
    RetrofitService retrofitService;
    FriendAdapter friendAdapter;
    UserInfo user;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getAllFriend();
        addListener();
    }

    private void addListener() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmContext.getInstance().deleteAllUser();
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();

    }

    private void init(View view) {
        btnLogOut = view.findViewById(R.id.btn_logout);
        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.recycler_view);

        friendList = new ArrayList<>();
        friendAdapter = new FriendAdapter(friendList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(friendAdapter);
        user = RealmContext.getInstance().getUser();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void getAllFriend() {
        retrofitService.getAllFriend(user.getUserID()).enqueue(new Callback<ArrayList<Friend>>() {
            @Override
            public void onResponse(Call<ArrayList<Friend>> call, Response<ArrayList<Friend>> response) {
                ArrayList<Friend> friends = response.body();
                if (response.code() == 200 && !friends.isEmpty() && friends != null) {

                    friendList.clear();
                    friendList.addAll(friends);
                    friendAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(3);

                } else {
                    viewFlipper.setDisplayedChild(1);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Friend>> call, Throwable t) {
                viewFlipper.setDisplayedChild(2);

            }
        });
    }

    @Override
    public void viewProfileFriend(UserInfo userInfo) {
        // trùng nên chưa làm
        // cần 1 activity mới hay k ?
        Intent intent = new Intent(getActivity(),ProfileFragment.class);
        intent.putExtra("userId", userInfo.getUserID());
        intent.putExtra("userName",userInfo.getUserName());
        intent.putExtra("AvatarUrl",userInfo.getAvatarUrL());
        startActivity(intent);
    }
}
