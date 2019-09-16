package com.example.appdemo_1704.home_screen.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.interf.OnItemStatusClickListener;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.String.valueOf;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
    private OnItemStatusClickListener listener;
    UserInfo userInfo;
    ArrayList<Status> statusList;

    public StatusAdapter(OnItemStatusClickListener listener, ArrayList<Status> statusList) {
        this.listener = listener;
        this.statusList = statusList;
        userInfo = RealmContext.getInstance().getUser();
    }

    @NonNull
    @Override
    public StatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // căng lên
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_status, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(statusList.get(i));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Status status;
        Context context;
        CircleImageView imageView;
        TextView tvFullName;
        TextView tvCreateDate;
        TextView tvContent;
        TextView tvNumberLike;
        TextView tvNumberCmt;
        ImageView imvLike;
        TextView tvLike;
        LinearLayout itemLike, itemComment;
        TextView tv_menu;
        ViewFlipper viewFlipper;
        List<String> imageList;
        ImageView ivOne, ivTwo1, ivTwo2, ivThree1, ivThree2, ivThree3;
        ImageView ivFour1, ivFour2, ivFour3, ivFour4, ivFive1, ivFive2, ivFive3, ivFive4, ivFive5;
        View viewGrey;
        TextView tvNumberImage;
        RelativeLayout layoutIv5;
        LinearLayout layoutCmt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            init(itemView);
            addListener();
        }

        private void addListener() {
            itemLike.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    listener.onLikeClick(getAdapterPosition(), status);
                    if (status.isLike()) {
                        imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_like));
                        tvNumberLike.setText(valueOf(Integer.parseInt(tvNumberLike.getText().toString()) - 1));
                        tvLike.setTextColor(context.getResources().getColor(R.color.black));

                    } else {
                        imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_like));
                        tvNumberLike.setText(valueOf(Integer.parseInt(tvNumberLike.getText().toString()) + 1));
                        tvLike.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
            });

            tv_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(context, tv_menu);
                    popupMenu.inflate(R.menu.status_option__menu);
                    popupMenu.show();

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.option_edit:
                                    listener.onEditStatus(status);
                                    break;
                                case R.id.option_delete:
                                    listener.onDeleteStatus(status);
                                    break;
                            }
                            return false;
                        }
                    });
                }
            });

            layoutCmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(getAdapterPosition(), status);
                }
            });

            itemComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCommentClick(getAdapterPosition(), status);
                }
            });
        }

        private void init(View itemview) {
            imageView = itemView.findViewById(R.id.iv_ava);
            tvFullName = itemView.findViewById(R.id.tv_username);
            tvCreateDate = itemView.findViewById(R.id.tv_datetime);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvNumberLike = itemView.findViewById(R.id.tv_numberLike);
            tvNumberCmt = itemView.findViewById(R.id.tv_countComment);
            imvLike = itemView.findViewById(R.id.iv_like);
            tvLike = itemView.findViewById(R.id.tv_like);
            itemLike = itemView.findViewById(R.id.itemLike);
            tv_menu = itemView.findViewById(R.id.tv_menu);
            itemComment = itemView.findViewById(R.id.itemComment);
            viewFlipper = itemView.findViewById(R.id.view_flipper);
            ivOne = itemView.findViewById(R.id.iv_one);
            ivTwo1 = itemView.findViewById(R.id.iv_two_1);
            ivTwo2 = itemView.findViewById(R.id.iv_two_2);
            ivThree1 = itemView.findViewById(R.id.iv_three_1);
            ivThree2 = itemView.findViewById(R.id.iv_three_2);
            ivThree3 = itemView.findViewById(R.id.iv_three_3);
            ivFour1 = itemView.findViewById(R.id.iv_four_1);
            ivFour2 = itemView.findViewById(R.id.iv_four_2);
            ivFour3 = itemView.findViewById(R.id.iv_four_3);
            ivFour4 = itemView.findViewById(R.id.iv_four_4);
            ivFive1 = itemView.findViewById(R.id.iv_five_1);
            ivFive2 = itemView.findViewById(R.id.iv_five_2);
            ivFive3 = itemView.findViewById(R.id.iv_five_3);
            ivFive4 = itemView.findViewById(R.id.iv_five_4);
            ivFive5 = itemView.findViewById(R.id.iv_five_5);
            layoutIv5 = itemView.findViewById(R.id.layout_iv5);
            layoutCmt = itemView.findViewById(R.id.layout_cmt);

            viewGrey = itemView.findViewById(R.id.view_grey);
            tvNumberImage = itemView.findViewById(R.id.tv_numberImage);

        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

        private void bindView(Status status) {
            // giữ lại status để dùng
            this.status = status;
            if (status.getAuthorAvatar() != null) {
                Glide.with(context).load(status.getAuthorAvatar()).into(imageView);
            } else {
                imageView.setBackgroundResource(R.drawable.anh1);
            }

            tvFullName.setText(status.getAuthorName());
            tvCreateDate.setText(status.getCreateDate());
            tvContent.setText(status.getContent());
            tvNumberLike.setText(valueOf(status.getNumberLike()));
            tvNumberCmt.setText(String.format("%s", status.getNumberComment()));


            if (!status.getAuthor().equals(userInfo.getUserName())) {
                tv_menu.setVisibility(View.INVISIBLE);
            } else {
                tv_menu.setVisibility(View.VISIBLE);
            }

            if (status.isLike()) {
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                imvLike.setBackground(context.getResources().getDrawable(R.drawable.ic_dont_like));
                tvLike.setTextColor(context.getResources().getColor(R.color.black));
            }

            imageList = new ArrayList<>();
            imageList = status.getImages();
            if (imageList != null) {
                if (imageList.size() > 0) {
                    viewFlipper.setVisibility(View.VISIBLE);

                    if (imageList.size() == 1) {
                        viewFlipper.setDisplayedChild(0);
                        Glide.with(context).load(imageList.get(0)).into(ivOne);
                    } else if (imageList.size() == 2) {
                        viewFlipper.setDisplayedChild(1);
                        Glide.with(context).load(imageList.get(0)).into(ivTwo1);
                        Glide.with(context).load(imageList.get(1)).into(ivTwo2);
                    } else if (imageList.size() == 3) {
                        viewFlipper.setDisplayedChild(2);
                        Glide.with(context).load(imageList.get(0)).into(ivThree1);
                        Glide.with(context).load(imageList.get(1)).into(ivThree2);
                        Glide.with(context).load(imageList.get(2)).into(ivThree3);
                    } else if (imageList.size() == 4) {
                        viewFlipper.setDisplayedChild(3);
                        Glide.with(context).load(imageList.get(0)).into(ivFour1);
                        Glide.with(context).load(imageList.get(1)).into(ivFour2);
                        Glide.with(context).load(imageList.get(2)).into(ivFour3);
                        Glide.with(context).load(imageList.get(3)).into(ivFour4);
                    } else if (imageList.size() == 5) {
                        viewFlipper.setDisplayedChild(4);
                        Glide.with(context).load(imageList.get(0)).into(ivFive1);
                        Glide.with(context).load(imageList.get(1)).into(ivFive2);
                        Glide.with(context).load(imageList.get(2)).into(ivFive3);
                        Glide.with(context).load(imageList.get(3)).into(ivFive4);
                        Glide.with(context).load(imageList.get(4)).into(ivFive5);
                    } else {
                        viewFlipper.setDisplayedChild(4);
                        Glide.with(context).load(imageList.get(0)).into(ivFive1);
                        Glide.with(context).load(imageList.get(1)).into(ivFive2);
                        Glide.with(context).load(imageList.get(2)).into(ivFive3);
                        Glide.with(context).load(imageList.get(3)).into(ivFive4);
                        Glide.with(context).load(imageList.get(4)).into(ivFive5);

                        viewGrey.setVisibility(View.VISIBLE);
                        tvNumberImage.setVisibility(View.VISIBLE);
                        tvNumberImage.setText("+" + (imageList.size() - 4));
                    }

                } else {
                    viewFlipper.setVisibility(View.GONE);
                }
            } else {
                viewFlipper.setVisibility(View.GONE);

        }
    }
    }
}
