package com.example.appdemo_1704.home_screen.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.AddMemberChatActivity;
import com.example.appdemo_1704.home_screen.MessageByGroupActivity;
import com.example.appdemo_1704.home_screen.adapter.GroupChatAdapter;
import com.example.appdemo_1704.interf.OnItemGroupChatClickListener;
import com.example.appdemo_1704.json_models.response.GroupChat;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagerFragment extends Fragment implements OnItemGroupChatClickListener {
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    FloatingActionButton btnFloating;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    RetrofitService retrofitService;
    SwipeRefreshLayout refreshLayout;
    UserInfo userInfo;
    GroupChatAdapter groupChatAdapter;
    ArrayList<GroupChat> groupChatArrayList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messager, container, false);
        userInfo = RealmContext.getInstance().getUser();
        init(view);
        getGroup();
        addListener();
        return view;
    }


    private void init(View view) {
        btnFloating = view.findViewById(R.id.btn_float);
        btnFloating = view.findViewById(R.id.btn_float);
        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.rv_groupchat);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);

        groupChatArrayList = new ArrayList<>();
        groupChatAdapter = new GroupChatAdapter(groupChatArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(groupChatAdapter);
    }

    private void addListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGroup();
            }
        });

        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMemberChatActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void viewConservationByGroup(GroupChat groupChat) {
        Intent intent = new Intent(getActivity(), MessageByGroupActivity.class);
        intent.putExtra("GetGroupId", groupChat.getGroupId());
        intent.putExtra("GetGroupName", groupChat.getGroupName());
        startActivity(intent);
    }
    private void getGroup() {
        retrofitService.getGroup(userInfo.getUserID()).enqueue(new Callback<ArrayList<GroupChat>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupChat>> call, Response<ArrayList<GroupChat>> response) {
                ArrayList<GroupChat> groupChats = response.body();
                if (response.code() == 200 && groupChats != null) {
                    groupChatArrayList.clear();
                    groupChatArrayList.addAll(groupChats);
                    groupChatAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<ArrayList<GroupChat>> call, Throwable t) {
                Utils.showToast(getActivity(),"khong co internet");
                refreshLayout.setRefreshing(false);
            }
        });

    }

}
