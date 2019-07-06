package com.example.appdemo_1704.home_screen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.LoginActivity;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.FriendAdapter;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ArrayList<Friend> friendList;
    Button btnLogOut;
    RetrofitService retrofitService;
    //friendList thì luôn đi kèm friendAdapter rồi :V
    FriendAdapter friendAdapter;

    UserInfo user;


    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        // trong hàm này lấy ra AllFriend luôn = >
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
        // tạo xong => kéo api về đã

        friendList = new ArrayList<>();
        friendAdapter = new FriendAdapter(friendList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        // điều cuối cùng là cái recycleview của mình => phải có 2 thuộc tính > cách căng ntn ?
        // và dữ liệu truyền vào như thế nào
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(friendAdapter);
        user = RealmContext.getInstance().getUser();
        // tạo cái interface
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void getAllFriend() {
        // hàm lấy ra bạn này thì cần biêt gì : cần biết user id ?
        //=> user id nằm ở đâu ? ở trong UserInfor => lại nằm ở trong
        // ReamlContext
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
}
