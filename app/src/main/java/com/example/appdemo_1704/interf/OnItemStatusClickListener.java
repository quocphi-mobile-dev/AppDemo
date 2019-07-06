package com.example.appdemo_1704.interf;

import com.example.appdemo_1704.json_models.response.Status;

public interface OnItemStatusClickListener {
    // tạo 1 interf chứa rất nhiều kiểu click

    // truyền vào cái Status chúng ta like
  //  public abstract void onLikeClick (Status status);
    void onLikeClick (Status status);
  //  public abstract void onCommentClick(Status status);
    void onCommentClick(Status status);
    void onDeleteStatus(Status status);
    void onEditStatus(Status status);

}
