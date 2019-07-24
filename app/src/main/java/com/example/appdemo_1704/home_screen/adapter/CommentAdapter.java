package com.example.appdemo_1704.home_screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.json_models.response.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
  ArrayList<Comment> commentList;
  Context context;

    public CommentAdapter(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_comment, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.bindView(commentList.get(i));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAva;
        TextView tvFullname;
        TextView tvComment;
        Comment comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            init(itemView);
        }
        private void init(View itemView) {
            ivAva = itemView.findViewById(R.id.iv_ava);
            tvFullname = itemView.findViewById(R.id.tv_username);
            tvComment = itemView.findViewById(R.id.tv_content);
        }
        private void bindView(Comment comment){
            this.comment = comment;
            Glide.with(context).load(comment.getUserAvatar()).into(ivAva);
            tvFullname.setText(comment.getFullName());
            tvComment.setText(comment.getContent());

        }
    }



}
