package com.example.appdemo_1704.authen_screen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.authen_screen.HomeActivity;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.json_models.request.LoginSendForm;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private final int MODE_NORMAL = 0;
    private final  int MODE_LOADING =1 ;
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    RetrofitService retrofitService;
    ViewFlipper viewFlipper;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);

       edtUsername = view.findViewById(R.id.edt_SignInUsername);
       edtPassword = view.findViewById(R.id.edt_SignInpassword);
       btnLogin = view.findViewById(R.id.btn_signIn);
       viewFlipper = view.findViewById(R.id.view_flipper);
       retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
       addListener();

        return view;

    }

    private void addListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if(username.isEmpty()||password.isEmpty()){
                    Utils.showToast(getContext(),"Username or Password  must be  no empty  ");
                } else {
                    login(username, password);
                }
            }
        });
    }
    private void login(String username, String password){
        // goi api login
        LoginSendForm loginSendForm = new LoginSendForm(username,password);
        viewFlipper.setDisplayedChild(MODE_LOADING);
        retrofitService.login(loginSendForm).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo userInfo = response.body();
                if (response.code() == 200&& userInfo != null){
                    // Truoc khi vao
                    RealmContext.getInstance().addUser(userInfo);
                    // Dung

                    goToHome();
                }else {
                    // sai
                    Utils.showToast(getActivity(),"Username or Password is incorrect");
                }
                viewFlipper.setDisplayedChild(MODE_NORMAL);
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                // fail
                Utils.showToast(getActivity(),"No Internet!");
                viewFlipper.setDisplayedChild(MODE_NORMAL);
            }
        });
    }
    private  void  goToHome(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
       // để khi out ra thì k quay lại cái Login Activity
        getActivity().finish();
    }



}
