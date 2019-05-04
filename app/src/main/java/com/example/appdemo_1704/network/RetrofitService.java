package com.example.appdemo_1704.network;

import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
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

    @POST(API.CREATE_POST)
    @Headers({API.HEADER})
    // phương thức gọi "call" Trả về cái gì  ?  trả về 1 cái Status " tạo rồi k tạo nữa"
    //đặt tên là createPost = > truyền vào cái gì ? cái gì trên API có thì làm : sau khi tryền vào thì kiểu dữ liệu cuẩ nó là gì
    Call<Status> createPost(@Body CreateStatusSendForm sendForm);
     // sau đó gọi ở fragment




}
