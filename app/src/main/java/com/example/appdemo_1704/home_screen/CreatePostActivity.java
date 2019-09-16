package com.example.appdemo_1704.home_screen;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.utils.Utils;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.view_grey)
    View viewGrey;
    @BindView(R.id.tv_numberImage)
    TextView tvNumberImage;
    @BindView(R.id.iv_ava)
    CircleImageView ivAva;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.edt_content)
    EditText edtContent;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.btn_float)
    FloatingActionButton btnAdd;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two_1)
    ImageView ivTwo1;
    @BindView(R.id.iv_two_2)
    ImageView ivTwo2;
    @BindView(R.id.iv_three_1)
    ImageView ivThree1;
    @BindView(R.id.iv_three_2)
    ImageView ivThree2;
    @BindView(R.id.iv_three_3)
    ImageView ivThree3;
    @BindView(R.id.iv_four_1)
    ImageView ivFour1;
    @BindView(R.id.iv_four_2)
    ImageView ivFour2;
    @BindView(R.id.iv_four_3)
    ImageView ivFour3;
    @BindView(R.id.iv_four_4)
    ImageView ivFour4;
    @BindView(R.id.iv_five_1)
    ImageView ivFive1;
    @BindView(R.id.iv_five_2)
    ImageView ivFive2;
    @BindView(R.id.iv_five_3)
    ImageView ivFive3;
    @BindView(R.id.iv_five_4)
    ImageView ivFive4;
    @BindView(R.id.iv_five_5)
    ImageView ivFive5;
    UserInfo userInfor;
    String[] uriList;
    int countImage;
    final int MODE_ONE = 0;
    final int MODE_TWO = 1;
    final int MODE_THREE = 2;
    final int MODE_FOUR = 3;
    final int MODE_FIVE = 4;
    private StorageReference storageReference;
    List<String> uriResponseList;
    private RetrofitService retrofitService;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }

//    private void createPost(String content) {
//        CreateStatusSendForm sendForm = new CreateStatusSendForm(user.getUserID(), content);
//        retrofitService.createPost(sendForm).enqueue(new Callback<Status>() {
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//
//                Status status = response.body();
//                if (response.code() == 200 && status != null) {
//                    // status này sẽ add vào cái vị trí đầu tiên của statusList
//                    // chuyển dữ liệu bằng adapter
//                    // đưa cái ô text về rỗng
//                    statusList.add(0, status);
//                    statusAdapter.notifyDataSetChanged();
//                    edtContent.setText("");
//                    Utils.showToast(getActivity(), " Đăng bài thành công");
//                } else {
//                    Utils.showToast(getActivity(), " Đăng bài thất bại");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Utils.showToast(getActivity(), " Đăng bài thất bại");
//            }
//        });
//    }
}
