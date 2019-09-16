package com.example.appdemo_1704.home_screen.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.CommentActivity;
import com.example.appdemo_1704.home_screen.CreatePostActivity;
import com.example.appdemo_1704.home_screen.UpdatePostActivity;
import com.example.appdemo_1704.home_screen.ViewMyProfileActivity;
import com.example.appdemo_1704.home_screen.adapter.StatusAdapter;
import com.example.appdemo_1704.interf.OnItemStatusClickListener;
import com.example.appdemo_1704.interf.OnUpdateDiaglogListener;
import com.example.appdemo_1704.json_models.request.LikeStatustSendForm;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemStatusClickListener, OnUpdateDiaglogListener {
    private RetrofitService retrofitService;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    TextView tvPost;
    UserInfo user;
    StatusAdapter statusAdapter;
    CircleImageView newfeesAvatar;
    ArrayList<Status> statusList;


    final int MODE_NO_DATA = 1;
    final int MODE_RECYCYCLEVIEW = 2;
    SwipeRefreshLayout refreshLayout;
    int position = 0;
    int positionDetail = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addListener();

        user = RealmContext.getInstance().getUser();

        if (user != null) {
            Glide.with(getActivity()).load(user.getAvatarUrL()).into(newfeesAvatar);
            getAllpost(user.getUserID());
        }

    }

    private void addListener() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllpost(user.getUserID());
            }
        });
        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            }
        });
        newfeesAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewMyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(View view) {
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        viewFlipper = view.findViewById(R.id.flipper_status);
        newfeesAvatar = view.findViewById(R.id.newfeeds_ava);
        recyclerView = view.findViewById(R.id.rv_status);
        tvPost = view.findViewById(R.id.tv_post);
        newfeesAvatar = view.findViewById(R.id.newfeeds_ava);
        refreshLayout = view.findViewById(R.id.refresh_layout);

        statusList = new ArrayList<>();
        statusAdapter = new StatusAdapter(this, statusList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(statusAdapter);
        // scroll cuộn hay ho
        recyclerView.setAdapter(new ScaleInAnimationAdapter(statusAdapter));

        // căn lề xuất hiện đều ở giữa
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(recyclerView);

    }

    private void getAllpost(String userId) {
        retrofitService.getAllPost(userId).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {

                ArrayList<Status> statuses = (ArrayList<Status>) response.body();
                if (response.code() == 200 && statuses != null) {
                    if (response.code() == 200 && statusList != null) {
                        statusList.clear();
                        statusList.addAll(statuses);
                        statusAdapter.notifyDataSetChanged();
                        viewFlipper.setDisplayedChild(MODE_RECYCYCLEVIEW);
                    } else {
                        viewFlipper.setDisplayedChild(MODE_NO_DATA);
                    }
                }
                // khi get post về xong thì tắt đi
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                viewFlipper.setDisplayedChild(2);

                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void likePost(int position, Status status) {
        LikeStatustSendForm sendForm = new LikeStatustSendForm(user.getUserID(), status.getPostId());
        retrofitService.likePost(sendForm).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    // set ngược cái statust này
                    status.setLike(!status.isLike());
                    // sau đó tăng hoặc giảm tương ứng

                    if (status.isLike()) {
                        status.setNumberLike(status.getNumberLike() + 1);
                    } else {
                        status.setNumberLike(status.getNumberLike() - 1);
                    }
                    statusAdapter.notifyItemChanged(position);
                } else {
                    Utils.showToast(getActivity(), "Có Lỗi xảy ra");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Utils.showToast(getActivity(), " Vui lòng kiểm tra lại kết nối");
            }
        });
    }

    public void deleteStatus(String userId, Status status) {
        retrofitService.deletStatus(status.getPostId(), userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    statusList.remove(status);
                    statusAdapter.notifyDataSetChanged();
                    Utils.showToast(getContext(), "Xóa Thành công  !");
                } else {
                    Utils.showToast(getContext(), "Xóa thất bại !");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    @Override
    public void onLikeClick(int position, Status status) {
        likePost(position, status);
    }

    @Override
    public void onDeleteStatus(Status status) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa bài viết")
                .setMessage("Bạn có chắc chắn muốn xóa bài viết")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delletePost();
                        deleteStatus(user.getUserID(), status);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onDetailImage(int positionAdapter, int positionImage, Status status) {
        // chưa cần làm
    }

    @Override
    public void onSaveClick(String newContent) {

    }

    @Override
    public void onCommentClick(int position, Status status) {
        this.position = position;
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        // intent.putExtra(Constant.GetStatusForDetail, status);
        startActivityForResult(intent, CommentActivity.REQUEST_CODE);
        //ĐOẠN NÀY NÊN DÙNG ACTIVITY FOR RESULT ĐỂ TỐI ƯU VÀ GIẢM TẢI LÊN API

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onEditStatus(Status status) {
        ArrayList<String> images = new ArrayList<>();
        images.addAll(status.getImages());
        Intent intent = new Intent(getActivity(), UpdatePostActivity.class);
        // intent.putExtra(Constant.GetStatusForDetail, (Serializable) status);
        startActivity(intent);
    }

}
