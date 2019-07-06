package com.example.appdemo_1704.home_screen.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.common.UpdateStatusDialog;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.StatusAdapter;
import com.example.appdemo_1704.interf.OnItemStatusClickListener;
import com.example.appdemo_1704.interf.OnUpdateDiaglogListener;
import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
import com.example.appdemo_1704.json_models.request.LikeStatustSendForm;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
import com.example.appdemo_1704.json_models.request.UpdateStatusSendForm;
import com.example.appdemo_1704.json_models.response.Comment;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnItemStatusClickListener, OnUpdateDiaglogListener {
    CircleImageView imvAvata;
    EditText edtContent;

    ImageView imvSend;
    SwipeRefreshLayout refreshLayout;
    Status status;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    // Truyên vào ?
    ArrayList<Status> statusList;
    StatusAdapter statusAdapter;
    UserInfo user;
    RetrofitService retrofitService;
    private Status currentStatus;

    public HomeFragment() {
        // Required empty public constructor
    }

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
            Glide.with(getActivity()).load(user.getAvatarUrL()).into(imvAvata);
            getAllpost(user.getUserID());
        }

    }

    private void addListener() {
        imvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtContent.getText().toString();
                if (content.isEmpty()) {
                    Utils.showToast(getActivity(), "Bạn chưa nhập nội Dung");
                } else {
                    createPost(content);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllpost(user.getUserID());
            }
        });
    }

    private void init(View view) {

        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.recycler_view);

        imvAvata = view.findViewById(R.id.imv_avatar);
        imvSend = view.findViewById(R.id.imv_post);
        edtContent = view.findViewById(R.id.edt_content);
        refreshLayout = view.findViewById(R.id.refresh_layout);


// truyền cái list vào adapter  ===  5 dòng thần thánh
        statusList = new ArrayList<>();

        // constructor chỉ mới có cái list thôi => cái Adapter chỉ mới có cái list => chưa có sự kiện click => tạo
        statusAdapter = new StatusAdapter(this, statusList);
        // truyền vào cái layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(statusAdapter);

        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void getAllpost(String userId) {

        retrofitService.getAllPost(userId).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {

                ArrayList<Status> statuses = (ArrayList<Status>) response.body();
                if (response.code() == 200 && statuses != null) {
                    if (statuses.isEmpty()) {
                        viewFlipper.setDisplayedChild(1);

                    } else {
                        statusList.clear();
                        statusList.addAll(statuses);
                        statusAdapter.notifyDataSetChanged();
                        viewFlipper.setDisplayedChild(3);
                    }
                } else {
                    viewFlipper.setDisplayedChild(2);
                }
                // khi get post về xong thì tắt đi
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                viewFlipper.setDisplayedChild(2);
                // khi getPost về xong thi tắt đi
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void createPost(String content) {
        CreateStatusSendForm sendForm = new CreateStatusSendForm(user.getUserID(), content);
        retrofitService.createPost(sendForm).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                Status status = response.body();
                if (response.code() == 200 && status != null) {
                    // status này sẽ add vào cái vị trí đầu tiên của statusList
                    // chuyển dữ liệu bằng adapter
                    // đưa cái ô text về rỗng
                    statusList.add(0, status);
                    statusAdapter.notifyDataSetChanged();
                    edtContent.setText("");
                    Utils.showToast(getActivity(), " Đăng bài thành công");
                } else {
                    Utils.showToast(getActivity(), " Đăng bài thất bại");
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Utils.showToast(getActivity(), " Đăng bài thất bại");
            }
        });
    }

    private void commentPost(Status status) {
        // lấy các cmt về ?  =>
        retrofitService.getAllComment(status.getPostId()).enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                // trong này nếu lấy được thì đẩy vào trong 1 cái list
                ArrayList<Comment> comments = response.body();
                if (response.code() == 200 && !comments.isEmpty()) {
                    Log.d("phi", comments.toString());
                    Utils.showToast(getActivity(), "đúng rồi nhưng chưa làm chức năng này =))");
                } else {
                    Utils.showToast(getActivity(), "bài viết k có cmt ");
                }
                // nếu đúng =>
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                Utils.showToast(getActivity(), "Lỗi k lấy được cmt   ");
            }
        });
    }

    private void likePost(Status status) {
        // tạo một cái sendForm truyên vào 2 gia trị của nó
        LikeStatustSendForm sendForm = new LikeStatustSendForm(user.getUserID(), status.getPostId());
        // gọi API của nó
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
                    // đổ dữ liệu vào
                    statusAdapter.notifyDataSetChanged();
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

    private void updateStatus(String userID, String newContent, String postID) {
        UpdateStatusSendForm sendForm = new UpdateStatusSendForm(userID, newContent);
        retrofitService.updateStatus(postID, sendForm).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status res = response.body();
                if (response.code() != 200 & res != null) {
                    currentStatus.setContent(res.getContent());
                    // báo cho adapter biết dữ liệu đã thay đổi, cần kiến thiết lại
                    statusAdapter.notifyDataSetChanged();
                    Utils.showToast(getContext(), "Chỉnh sửa thành công !");
                } else {
                    Utils.showToast(getContext(), "Chỉnh sửa thất bại !");
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Utils.showToast(getContext(), "Chỉnh sửa thất bại !");
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
    public void onLikeClick(Status status) {
        // click vào thì nhảy sang adapter = > để làm gì đấy
        likePost(status);
    }

    @Override
    public void onCommentClick(Status status) {
        commentPost(status);
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
    public void onEditStatus(Status status) {
        currentStatus = status;
        UpdateStatusDialog statusDialog = new UpdateStatusDialog(getContext(), this);
        statusDialog.getContent(status.getContent());
        statusDialog.show();
    }

    @Override
    public void onSaveClick(String newContent) {
        updateStatus(user.getUserID(), newContent, currentStatus.getPostId());
    }
}
