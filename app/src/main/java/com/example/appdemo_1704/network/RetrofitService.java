package com.example.appdemo_1704.network;

import com.example.appdemo_1704.json_models.request.LoginSendForm;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST(API.LOGIN)
    @Headers({API.HEADER})
    Call<UserInfo> login(@Body LoginSendForm sendForm);

    @POST(API.REGISTER)
    @Headers({API.HEADER})
    Call<UserInfo> register (@Body RegisterSendForm registerSendForm);

    @GET(API.GET_ALL_POST)
    @Headers({API.HEADER})
    Call<List<Status>> getAllPost(@Query("userId") String userID);




}
