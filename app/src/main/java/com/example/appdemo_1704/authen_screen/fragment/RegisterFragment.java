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
import com.example.appdemo_1704.home_screen.HomeActivity;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    private final int MODE_LOADING = 0;
    private final int MODE_NORMAL = 1;
    EditText edtUsername;
    EditText edtPassword;
    EditText edtRePassword;
    EditText edtFullname;
    EditText edtAddress;
    EditText edtPhone;
    Button btnSignUp;
    ViewFlipper viewFlipper;
    RetrofitService retrofitService;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        init(view);
        addListener();
        return view;

    }

    public void addListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String repassword = edtRePassword.getText().toString();
                String fullname = edtFullname.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();
                if (username.isEmpty()) {
                    Utils.showToast(getContext(), "Username must be  no empty");
                } else if (password.isEmpty()) {
                    Utils.showToast(getContext(), "Password  must be  no empty ");
                } else if (!repassword.equals(password)) {
                    Utils.showToast(getContext(), "Password does not match ");
                } else if (fullname.isEmpty()) {
                    Utils.showToast(getContext(), "Fullname must be  no empty ");
                } else {
                    register(username, password, repassword, fullname, address, phone);
                }
            }
        });

    }

    private void init(View view) {
        edtUsername = view.findViewById(R.id.edt_SignUpUsername);
        edtPassword = view.findViewById(R.id.edt_SignUpPassword);
        edtRePassword = view.findViewById(R.id.edt_Re_EnterPassword);
        edtFullname = view.findViewById(R.id.edt_Fullname);
        edtAddress = view.findViewById(R.id.edt_Address);
        edtPhone = view.findViewById(R.id.edt_Phone);
        btnSignUp = view.findViewById(R.id.btn_signUp);
        viewFlipper = view.findViewById(R.id.view_flipper);

        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
    }
    // goi api login

    private void register(String username, String password, String repassword, String fullname, String address, String phone) {
        RegisterSendForm registerSendForm = new RegisterSendForm(username, password, fullname, address, phone);
        viewFlipper.setDisplayedChild(MODE_LOADING);
        retrofitService.register(registerSendForm).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                UserInfo userInfo = response.body();
                if (response.code() == 200 && userInfo != null) {
                    // Dung
                    Utils.showToast(getActivity(), "Register  Successfully !!");
                    RealmContext.getInstance().addUser(userInfo);
                    goToHome();
                } else {
                    // sai
                    Utils.showToast(getActivity(), "Acount already exists !!");
                }
                viewFlipper.setDisplayedChild(MODE_NORMAL);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

                Utils.showToast(getActivity(), "Kiem tra Lai");
                viewFlipper.setDisplayedChild(MODE_NORMAL);
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
