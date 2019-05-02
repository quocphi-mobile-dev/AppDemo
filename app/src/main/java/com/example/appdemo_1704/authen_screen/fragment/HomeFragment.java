package com.example.appdemo_1704.authen_screen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ImageView imageView;
    EditText edtContent;
    ImageView ivPost;
    RecyclerView recyclerView;
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
        UserInfo user = RealmContext.getInstance().getUser();
        if (user != null){
            getAllpost(user.getUserID());
        }

    }

    private void init(View view) {
        imageView = view.findViewById(R.id.iv_profile);
        edtContent = view.findViewById(R.id.edt_yourmind);
        ivPost = view.findViewById(R.id.iv_post);
        recyclerView = view.findViewById(R.id.rv_status);



        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }

    private void getAllpost(String userId ) {

        retrofitService.getAllPost(userId).enqueue(new Callback<List<Status>>() {
            @Override
            public void onResponse(Call<List<Status>> call, Response<List<Status>> response) {
                List<Status> statusList = response.body();
                if (response.code()==200 && statusList != null) {
                    for (Status status : statusList) {
                        Log.d("st",status.toString());
                    }
                }
            else Log.d("st","loiiiiiiii");
            }
            @Override
            public void onFailure(Call<List<Status>> call, Throwable t) {
                Log.d("st","loiiiiiiiiiiiii");

            }
        });
    }
}
