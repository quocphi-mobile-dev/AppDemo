package com.example.appdemo_1704.home_screen.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.json_models.response.Status;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder>{
    // bước 1: tạo 1 list : chứa 1 list các statust => contrustor cho nó
    ArrayList<Status> statusList;

    public StatusAdapter(ArrayList<Status> arrayList) {
        this.statusList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // căng lên
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_status,viewGroup,false);
        return new MyViewHolder(view);

    }

    @Override
    public int getItemCount() {
        // cout  = số lượng
        return statusList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        // bind  view
        myViewHolder.bindView(statusList.get(i));

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Context context;

        TextView tvContent;
        CircleImageView imageView;
        TextView tvFullName;
        TextView tvCreateDate;
        TextView tvNumberLike;
        TextView tvNumberCmt;
        ImageView imvLike;
        TextView tvLike;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tvContent = itemView.findViewById(R.id.tv_content);
            imageView = itemView.findViewById(R.id.imv_profile);
            tvCreateDate = itemView.findViewById(R.id.tv_createDate);
            tvFullName = itemView.findViewById(R.id.tv_fullName);

            tvNumberLike = itemView.findViewById(R.id.tv_number_like);
            tvNumberCmt = itemView.findViewById(R.id.tv_number_comment);
            imvLike = itemView.findViewById(R.id.imv_like);
            tvLike = itemView.findViewById(R.id.tv_like);




        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

        private void bindView(Status status){

            tvContent.setText(status.getContent());
            tvFullName.setText(status.getAuthorName());
            tvCreateDate.setText(status.getCreateDate());
            tvNumberLike.setText(String.valueOf(status.getNumberLike()));
            tvNumberCmt.setText(String.format("%s Bình luận",status.getNumberComment()));

            Glide.with(context).load(status.getAuthorAvatar()).into(imageView);



            if(status.getLike()){
                // set baground cần context  => set cái ảnh màu hồng
                imageView.setBackground(context.getResources().getDrawable(R.drawable.ic_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.colorRed400));
            }
            else {
                imageView.setBackground(context.getResources().getDrawable(R.drawable.ic_dont_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.colorGray900));

            }
        }

    }
}
