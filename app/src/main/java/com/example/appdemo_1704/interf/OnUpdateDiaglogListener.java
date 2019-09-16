package com.example.appdemo_1704.interf;

import com.example.appdemo_1704.json_models.response.Status;

public interface OnUpdateDiaglogListener {
    void onLikeClick(int position, Status status);

//    void onCommentClick(Status status);

    void onCommentClick(int position, Status status);

    void onEditStatus(Status status);

    void onDeleteStatus(Status status);

    void onDetailImage(int positionAdapter, int positionImage, Status status);
    void onSaveClick(String newContent);
}