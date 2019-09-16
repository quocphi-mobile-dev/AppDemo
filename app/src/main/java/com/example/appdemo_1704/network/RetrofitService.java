package com.example.appdemo_1704.network;

import com.example.appdemo_1704.json_models.request.CommentStatuSendForm;
import com.example.appdemo_1704.json_models.request.CreateStatusSendForm;
import com.example.appdemo_1704.json_models.request.LikeStatustSendForm;
import com.example.appdemo_1704.json_models.request.LoginSendForm;
import com.example.appdemo_1704.json_models.request.UpdateAvatarUri;
import com.example.appdemo_1704.json_models.response.Avatar;
import com.example.appdemo_1704.json_models.response.CommentResponse;
import com.example.appdemo_1704.json_models.response.GroupChat;
import com.example.appdemo_1704.json_models.response.Message;
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
    Call<List<Status>> getAllPost(@Query("userId") String userId);

    @POST(API.CREATE_POST)
    @Headers({API.HEADER})

    Call<Status> createPost(@Body CreateStatusSendForm sendForm);
    // sau đó gọi ở fragment

    @POST(API.LIKE_POST)
    @Headers({API.HEADER})
    Call<Void> likePost(@Body LikeStatustSendForm sendForm);

    @GET(API.GET_ALL_FRIEND)
    Call<ArrayList<Friend>> getAllFriend(@Query("userId") String userID);
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
    // update avatar
    @PUT(API.UPDATE_AVATAR)
    @Headers({API.HEADER})
    Call<Avatar> upDateAvatar (@Query("userId")String userId, @Body UpdateAvatarUri sendAvatarUri);


    // create commmet
    @POST(API.CREATE_COMMENT)
    @Headers({API.HEADER})
    Call<CommentResponse>  commentStatus(@Body CommentStatuSendForm sendForm);

    // get all comment
    @GET(API.GET_ALL_COMMENT)
    @Headers({API.HEADER})
    Call<List<Comment>> getAllComment(@Query("postId") String postId);
    // get group
    @GET(API.GET_GROUP)
    Call<ArrayList<GroupChat>> getGroup(@Query("userId") String userId);
    // get all messager
    @GET(API.GET_ALL_MESSAGE)
    Call<List<Message>> getAllMessage(@Query("groupId") String groupId);
    // update avatar

}
