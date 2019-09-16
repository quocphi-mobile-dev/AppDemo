package com.example.appdemo_1704.home_screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.json_models.response.Message;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagerByGroupAdapter extends RecyclerView.Adapter<MessagerByGroupAdapter.ViewHolder> {
    ArrayList<Message> messageArrayList;
    UserInfo userInfor;
    public MessagerByGroupAdapter(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
        userInfor = RealmContext.getInstance().getUser();
    }
    @NonNull
    @Override
    public MessagerByGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagerByGroupAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindView(messageArrayList.get(i));
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewFlipper viewFlipper;
        CircleImageView ivAvaFriend, ivAvaMe;
        TextView tvMessFriend, tvMessMe;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
        }

        private void init(View itemView) {
            ivAvaFriend = itemView.findViewById(R.id.iv_ava_friend);
            ivAvaMe = itemView.findViewById(R.id.iv_ava_me);
            tvMessFriend = itemView.findViewById(R.id.tv_mess_friend);
            tvMessMe = itemView.findViewById(R.id.tv_mess_me);
            viewFlipper = itemView.findViewById(R.id.view_flipper);
        }
        private  void  bindView(Message message){
            if (!message.getUserId().equals(userInfor.getUserID())){
                Glide.with(context).load(message.getAvatarUrl()).into(ivAvaFriend);
                tvMessFriend.setText(message.getContent());
                viewFlipper.setDisplayedChild(0);
            }else {
                viewFlipper.setDisplayedChild(1);
                Glide.with(context).load(message.getAvatarUrl()).into(ivAvaMe);
                tvMessMe.setText(message.getContent());
            }

        }
    }

}
