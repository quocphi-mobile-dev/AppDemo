package com.example.appdemo_1704.home_screen.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.interf.OnItemStatusClickListener;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder>{
    private OnItemStatusClickListener listener;
      UserInfo userInfo;
    // bước 1: tạo 1 list : chứa 1 list các statust => contrustor cho nó
    ArrayList<Status> statusList;
    // tạo
// vì tạo bên HomeFragment nên cái listener này => chính là sự kiên click bên đó
    public StatusAdapter(OnItemStatusClickListener listener, ArrayList<Status> statusList ) {
        this.listener = listener;
        this.statusList = statusList;
        userInfo = RealmContext.getInstance().getUser();
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
        Status status;
        LinearLayout itemlike;
        LinearLayout itemCmt;
        Context context;
        TextView tvContent;
        CircleImageView imageView;
        TextView tvFullName;
        TextView tvCreateDate;
        TextView tvNumberLike;
        TextView tvNumberCmt;
        ImageView imvLike;
        TextView tvLike;
        TextView tvMenu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            init(itemView);
             addListener();
        }
        private void  addListener(){
            itemlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLikeClick(status);
                }
            });

            itemCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(status);
                }
            });
            tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,tvMenu);
                    popupMenu.inflate(R.menu.status_option__menu);

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.option_edit:
                                   listener.onEditStatus(status);
                                    break;
                                case R.id.option_delete:
                                    // xóa  chưa làm
                                    listener.onDeleteStatus(status);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show(); //showing popup menu
                }
            });
        }

        private  void  init(View itemview){
            tvContent = itemView.findViewById(R.id.tv_content);
            imageView = itemView.findViewById(R.id.imv_profile);
            tvCreateDate = itemView.findViewById(R.id.tv_createDate);
            tvFullName = itemView.findViewById(R.id.tv_fullName);

            tvNumberLike = itemView.findViewById(R.id.tv_number_like);
            tvNumberCmt = itemView.findViewById(R.id.tv_number_comment);
            imvLike = itemView.findViewById(R.id.imv_like);
            tvLike = itemView.findViewById(R.id.tv_like);
            // ánh xạ cho sự kiện like
            itemlike = itemView.findViewById(R.id.item_like);
            itemCmt = itemView.findViewById(R.id.item_comment);
             tvMenu = itemview.findViewById(R.id.tv_menu);

        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

        private void bindView(Status status){
            // giữ lại status để dùng
            this.status = status;

            tvContent.setText(status.getContent());
            tvFullName.setText(status.getAuthorName());
            tvCreateDate.setText(status.getCreateDate());
            tvNumberLike.setText(String.valueOf(status.getNumberLike()));
            tvNumberCmt.setText(String.format("%s Bình luận",status.getNumberComment()));
            // set Avarta
            Glide.with(context).load(status.getAuthorAvatar()).into(imageView);

            if(status.isLike()){
                // set baground cần context  => set cái ảnh màu hồng
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.colorRed400));
            }
            else {
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_dont_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.colorGray900));

            }
            // getInstance trả về thể hiện
            if(status.getAuthor().equals(userInfo.getUserName())){
                tvMenu.setVisibility(View.VISIBLE);
            }else {
                tvMenu.setVisibility(View.GONE);
            }
        }

    }
}
