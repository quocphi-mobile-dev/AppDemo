package com.example.appdemo_1704.home_screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.interf.OnItemGroupChatClickListener;
import com.example.appdemo_1704.json_models.response.GroupChat;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {
    ArrayList<GroupChat> groupChatArrayList;
    OnItemGroupChatClickListener listener;

    public GroupChatAdapter(ArrayList<GroupChat> groupChatArrayList, OnItemGroupChatClickListener listener) {
        this.groupChatArrayList = groupChatArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_groupchat, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupChatAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindView(groupChatArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return groupChatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewFlipper viewFlipper;
        CircleImageView ivAvaOne, ivAvaMore1, ivAvaMore2;
        TextView tvNameGroup, tvLassMess;
        Context context;
        RelativeLayout relativeLayout;
        GroupChat groupChat;
        ArrayList<UserInfo> userInfors;
        String[] avas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
            addListener();
        }
        public void   bindView(GroupChat groupChat) {
            this.groupChat = groupChat;
            userInfors = groupChat.getUsers();
            avas = new String[userInfors.size()];

//            if(groupChat.getLastMessage() == null){
//                tvLassMess.setVisibility(View.INVISIBLE);
//            } else {
//                tvLassMess.setVisibility(View.VISIBLE);
//                tvLassMess.setText(groupChat.getLastMessage());
//            }


            for (int i = 0;i<userInfors.size();i++){
                avas[i] = userInfors.get(i).getAvatarUrL();
            }
            if (avas == null || avas.length ==0) {
                viewFlipper.setDisplayedChild(0);
                Glide.with(context).load(R.drawable.anh1).into(ivAvaOne);
            }else if (avas.length ==1){
                viewFlipper.setDisplayedChild(0);
                Glide.with(context).load(avas[0]).into(ivAvaOne);
            }else {
                viewFlipper.setDisplayedChild(1);
                Glide.with(context).load(avas[0]).into(ivAvaMore1);
                Glide.with(context).load(avas[1]).into(ivAvaMore2);
            }
            tvNameGroup.setText(groupChat.getGroupName());

        }
        private void init(View itemView){
            viewFlipper = itemView.findViewById(R.id.view_flipper);
            ivAvaOne = itemView.findViewById(R.id.iv_ava_one);
            ivAvaMore1 = itemView.findViewById(R.id.iv_ava_more1);
            ivAvaMore2 = itemView.findViewById(R.id.iv_ava_more2);
            tvNameGroup = itemView.findViewById(R.id.tv_namegroup);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            tvLassMess = itemView.findViewById(R.id.tv_lass_mess);
        }
        private void addListener() {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.viewConservationByGroup(groupChat);
                }
            });
        }
    }
}
