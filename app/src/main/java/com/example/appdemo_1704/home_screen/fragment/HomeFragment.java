package com.example.appdemo_1704.home_screen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.adapter.StatusAdapter;
import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
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

public class HomeFragment extends Fragment {
    CircleImageView imvAvata;
    EditText edtContent;
    ImageView imvSend;


    RecyclerView recyclerView;

    ViewFlipper viewFlipper;
    // Truyên vào
    ArrayList<Status> statusList;
    StatusAdapter statusAdapter;


    UserInfo user;
    RetrofitService retrofitService;

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
                if(content.isEmpty()){
                    Utils.showToast(getActivity(),"Bạn chưa nhập nội Dung");
                }else {
                    createPost(content);
                }
            }
        });
    }

    private void init(View view) {

        viewFlipper = view.findViewById(R.id.view_flipper);
        recyclerView = view.findViewById(R.id.recycler_view);

        imvAvata = view.findViewById(R.id.imv_avatar);
        imvSend = view.findViewById(R.id.imv_post);
        edtContent = view.findViewById(R.id.edt_content);

// truyền cái list vào adapter  ===  5 dòng thần thánh
        statusList = new ArrayList<>();
        statusAdapter = new StatusAdapter(statusList);
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
            }

            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                viewFlipper.setDisplayedChild(2);

            }
        });
    }
    private void createPost(String content){
        CreateStatusSendForm sendForm = new CreateStatusSendForm(user.getUserID(),content);
        retrofitService.createPost(sendForm).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {

                Status status = response.body();
                if(response.code()==200  && status  != null){
                    // status này sẽ add vào cái vị trí đầu tiên của statusList
                    // chuyển dữ liệu bằng adapter
                    // đưa cái ô text về rỗng

                    statusList.add(0,status);
                    statusAdapter.notifyDataSetChanged();
                    edtContent.setText("");
                    Utils.showToast(getActivity()," Đăng bài thành công");


                } else {
                    Utils.showToast(getActivity()," Đăng bài thất bại");
                }

            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
            Utils.showToast(getActivity()," Đăng bài thất bại");
            }
        });

    }
}
