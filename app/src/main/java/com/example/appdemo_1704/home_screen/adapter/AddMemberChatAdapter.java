package com.example.appdemo_1704.home_screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.interf.OnItemAddGroupChatClickListener;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddMemberChatAdapter extends RecyclerView.Adapter<AddMemberChatAdapter.MyViewHolder> {
    private ArrayList<Friend> friends;
    UserInfo user;
    final int MODE_ADD = 0;
    final int MODE_TICK = 1;
    List<UserInfo> userInfosList;
    OnItemAddGroupChatClickListener listener;

    public AddMemberChatAdapter(ArrayList<Friend> userInfos) {
        this.friends = userInfos;
    }

    public AddMemberChatAdapter(ArrayList<Friend> friends, OnItemAddGroupChatClickListener listener) {
        this.friends = friends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddMemberChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_add_friend_chat, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddMemberChatAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(friends.get(i));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        Context context;
        CircleImageView imvAvatar;
        TextView tvFullName;
        ImageView ivAdd;
        ViewFlipper viewFlipper;
        RelativeLayout itemAdd;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);;
            user = RealmContext.getInstance().getUser();
            context = itemView.getContext();
            imvAvatar = itemView.findViewById(R.id.iv_ava);
            tvFullName = itemView.findViewById(R.id.tv_full_name);
            ivAdd = itemView.findViewById(R.id.iv_add);
            itemAdd = itemView.findViewById(R.id.item_add_conservation);
            viewFlipper = itemView.findViewById(R.id.view_flipper);
            userInfosList = new ArrayList<>();
            userInfosList.add(user);

        }
        private  void bindView(Friend friendInfor){

            Glide.with(context).load(friendInfor.getAvatar()).into(imvAvatar);
            tvFullName.setText(friendInfor.getFullName());

           // userInfo.setAdded(false);
//            userInfor.setAdded(!userInfor.isAdded());
//
////                    viewFlipper.setDisplayedChild(userInfor.isAdded() ? MODE_TICK : MODE_ADD);
//
//            if (userInfor.isAdded()) {
//                viewFlipper.setDisplayedChild(MODE_TICK);
//                userInforList.add(userInfor);
//            } else {
//                viewFlipper.setDisplayedChild(MODE_ADD);
//                if (userInforList.contains(userInfor)) {
//                    userInforList.remove(userInfor);

                    itemAdd.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.createMemberChat(userInfosList);

                 }
             });
        }

    }
}
