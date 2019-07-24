package com.example.appdemo_1704.home_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.CommentAdapter;
import com.example.appdemo_1704.json_models.request.CommentStatuSendForm;
import com.example.appdemo_1704.json_models.response.Comment;
import com.example.appdemo_1704.json_models.response.CommentResponse;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    RetrofitService retrofitService;
    EditText edtComment;
    ImageView ivSend, ivAva;
    UserInfo userInfo;
    CommentAdapter commentAdapter;
    ArrayList<Comment> commentList;
    final int MODE_NO_DATA = 1;
    final int MODE_RECYCYCLEVIEW = 2;
    String postId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        userInfo = RealmContext.getInstance().getUser();
        init();
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatarUrL()).into(ivAva);
            // can Inten o day ?
            Intent intent = getIntent();
            postId = intent.getStringExtra("GetPostId");
            userId = intent.getStringExtra("GetUserId");
            getAllComment(postId);
        }

        addListener();

    }

    private void addListener() {
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edtComment.getText().toString();
                if (!comment.isEmpty()) {
                    commentStatus(comment);
                } else {
                    Utils.showToast(CommentActivity.this, "Khong duoc de cmt trong!");
                }

            }
        });
    }

    private void commentStatus(String comment) {
        CommentStatuSendForm commentStatuSendForm = new CommentStatuSendForm(userId, postId, comment);
        retrofitService.commentStatus(commentStatuSendForm).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                CommentResponse response1 = response.body();
                if (response.code() == 200 && response != null) {
                    Utils.showToast(CommentActivity.this, "thanh cong");
                    getAllComment(postId);
                    edtComment.setText("");
                } else {
                    Utils.showToast(CommentActivity.this, "that bai");
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Utils.showToast(CommentActivity.this, "No Internet!");
            }
        });

    }

    private void getAllComment(String postId) {
        retrofitService.getAllComment(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                ArrayList<Comment> comments = (ArrayList<Comment>) response.body();
                if (response.code() == 200 && comments != null) {
                    commentList.clear();
                    commentList.addAll(comments);
                    commentAdapter.notifyDataSetChanged();
                    viewFlipper.setDisplayedChild(MODE_RECYCYCLEVIEW);
                } else {
                    viewFlipper.setDisplayedChild(MODE_NO_DATA);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Utils.showToast(CommentActivity.this, " khong co mang");

            }
        });
    }

    private void init() {
        viewFlipper = findViewById(R.id.view_flipper);
        recyclerView = findViewById(R.id.rv_cmt);
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        ivAva = findViewById(R.id.iv_ava);
        ivSend = findViewById(R.id.iv_send);
        edtComment = findViewById(R.id.edt_comment);
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(commentAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
