package com.example.appdemo_1704.network;

import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
import com.example.appdemo_1704.json_models.request.LikeStatustSendForm;
import com.example.appdemo_1704.json_models.request.LoginSendForm;
import com.example.appdemo_1704.json_models.request.UpdateAvatarUri;
import com.example.appdemo_1704.json_models.response.ProfileUser;
import com.example.appdemo_1704.json_models.request.RegisterSendForm;
import com.example.appdemo_1704.json_models.request.UpdateStatusSendForm;
import com.example.appdemo_1704.json_models.response.Comment;
import com.example.appdemo_1704.json_models.response.Friend;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST(API.LOGIN)
    @Headers({API.HEADER})
    Call<UserInfo> login(@Body LoginSendForm sendForm);

    @POST(API.REGISTER)
    @Headers({API.HEADER})
    Call<UserInfo> register(@Body RegisterSendForm registerSendForm);

    @GET(API.GET_ALL_POST)
    @Headers({API.HEADER})
    Call<List<Status>> getAllPost(@Query("userId") String userID);

    @POST(API.CREATE_POST)
    @Headers({API.HEADER})
        // phương thức gọi "call" Trả về cái gì  ?  trả về 1 cái Status " tạo rồi k tạo nữa"
        //đặt tên là createPost = > truyền vào cái gì ? cái gì trên API có thì làm : sau khi tryền vào thì kiểu dữ liệu cuẩ nó là gì
    Call<Status> createPost(@Body CreateStatusSendForm sendForm);
    // sau đó gọi ở fragment

    @POST(API.LIKE_POST)
    @Headers({API.HEADER})
        // trả về => truyền lên >
    Call<Void> likePost(@Body LikeStatustSendForm sendForm);

    @GET(API.GET_ALL_FRIEND)
    Call<ArrayList<Friend>> getAllFriend(@Query("userId") String userID);

    // lấy cmt về
    @GET(API.GET_ALL_COMMENT)
    @Headers({API.HEADER})
    Call<ArrayList<Comment>> getAllComment(@Query("postId") String postID);

    // updata post
    @PUT(API.UPDATE_STATUS)
    @Headers({API.HEADER})
    Call<Status> updateStatus(@Path("postID") String postID, @Body UpdateStatusSendForm sendForm);
    // Xóa status
    @DELETE(API.DELET_STATUS)
    Call<Void> deletStatus (@Path("postID")String postID, @Header("userId") String userID);
     // cập nhật ảnh bìa
    @GET(API.GET_PROFILE)
    Call<ProfileUser> getProfile (@Query("username") String username , @Header("userId") String userID);

    @PUT(API.UPDATE_AVATAR)
    @Headers({API.HEADER})
    Call<ProfileUser> upDateAvatar (@Body UpdateAvatarUri sendAvatarUri);


}
