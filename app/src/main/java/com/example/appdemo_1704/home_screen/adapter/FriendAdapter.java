package com.example.appdemo_1704.home_screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.interf.OnItemFriendClickListener;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    // trong này chưa 1 cái array list Friend // khai báo xong khởi tạo constructor cho nó

    private ArrayList<Friend> friendList;
    OnItemFriendClickListener listener;

    public FriendAdapter(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    public FriendAdapter(ArrayList<Friend> friendList, OnItemFriendClickListener listener) {
        this.friendList = friendList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // phương thức chính => trả về cái gì
        View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.layout_item_friend, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(friendList.get(i));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Context context;
        CircleImageView imvAvatar;
        TextView tvFullName;
        TextView tvMine;
        RelativeLayout relativeLayout;
        UserInfo userInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.imv_avatar);

            tvFullName = itemView.findViewById(R.id.tv_full_name);
            tvMine = itemView.findViewById(R.id.tv_mine);
            relativeLayout = itemView.findViewById(R.id.itemFriend);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.viewProfileFriend(userInfo);
                }
            });


        }

        private void bindView(Friend friend) {

            Glide.with(context).load(friend.getAvatar()).into(imvAvatar);
            tvFullName.setText(friend.getFullName());
            Log.d("phi", tvFullName.toString());

            if (friend.isYou()) {

                tvMine.setVisibility(View.VISIBLE);
            } else {
                tvMine.setVisibility(View.GONE);
            }
        }
    }
}
