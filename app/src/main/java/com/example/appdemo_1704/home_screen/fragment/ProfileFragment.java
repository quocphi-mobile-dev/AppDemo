package com.example.appdemo_1704.home_screen.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.home_screen.CommentActivity;
import com.example.appdemo_1704.home_screen.adapter.StatusAdapter;
import com.example.appdemo_1704.interf.OnItemStatusClickListener;
import com.example.appdemo_1704.json_models.request.UpdateAvatarUri;
import com.example.appdemo_1704.json_models.response.Avatar;
import com.example.appdemo_1704.json_models.response.ProfileUser;
import com.example.appdemo_1704.json_models.response.Status;
import com.example.appdemo_1704.json_models.response.UserInfo;
import com.example.appdemo_1704.network.RetrofitService;
import com.example.appdemo_1704.network.RetrofitUtils;
import com.example.appdemo_1704.utils.Utils;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements OnItemStatusClickListener {
    private RetrofitService retrofitService;
    UserInfo userInfor;
    SliderLayout sliderLayout;
    TextView tvUsername, tvAddress, tvDoB, tvPhone;
    ViewFlipper viewFlipper;
    final int MODE_NODATA = 1;
    final int MODE_RECYCLEVIEW = 2;
    ArrayList<Status> statusArrayList;
    RecyclerView recyclerView;
    private StorageReference storageReference;
    String address, DoB, phone;
    SwipeRefreshLayout refreshLayout;

    StatusAdapter statusAdapter;
    ImageView ivAva, newfeedAva, ivCamera, ivUpdateCover;
    CircleImageView dialogAvatar;
    Status currentStatus;
    TextView tvMenu, tvPost;
    LinearLayout itemAddress, itemPhone;
    String[] listPermissions = null;
    EditText edtPost;
    public static final int REQUEST_PERMISSION_CODE = 1;
    public static final int REQUEST_GET_IMAGE_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        storageReference = FirebaseStorage.getInstance().getReference();
        addListener();
        getProfile(userInfor.getUserName(),userInfor.getUserID());
        return view;

    }

    private void init(View view) {
        userInfor = RealmContext.getInstance().getUser();
        retrofitService = RetrofitUtils.getInstance().createService(RetrofitService.class);
        tvPost = view.findViewById(R.id.tv_post);
        sliderLayout = view.findViewById(R.id.slider);
        tvUsername = view.findViewById(R.id.tv_username);
        tvAddress = view.findViewById(R.id.tv_address);
        tvDoB = view.findViewById(R.id.tv_birthday);
        tvPhone = view.findViewById(R.id.tv_phone);
        viewFlipper = view.findViewById(R.id.flipper_status);
        recyclerView = view.findViewById(R.id.rv_status);
        ivAva = view.findViewById(R.id.iv_ava);
        newfeedAva = view.findViewById(R.id.newfeeds_ava);
        tvMenu = view.findViewById(R.id.tv_menu);
        itemAddress = view.findViewById(R.id.item_address);
        itemPhone = view.findViewById(R.id.item_phone);
        ivCamera = view.findViewById(R.id.iv_camera);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        ivUpdateCover = view.findViewById(R.id.iv_update_cover);
        statusArrayList = new ArrayList<>();

        statusAdapter = new StatusAdapter(this, statusArrayList);
        recyclerView.setAdapter(statusAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getProfile(String username, String userId) {
        retrofitService.getProfile(username, userId).enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                ProfileUser profileUser = response.body();
                if (response.code() == 200 && profileUser != null) {
                    // lấy ảnh
                    tvUsername.setText(profileUser.getFullName());
                    tvAddress.setText(profileUser.getAddress());
                    tvDoB.setText(profileUser.getBirthday());
                    tvPhone.setText(profileUser.getPhone());
                    Glide.with(getActivity()).load(userInfor.getAvatarUrL()).into(ivAva);
                    Glide.with(getActivity()).load(userInfor.getAvatarUrL()).into(newfeedAva);
                    address = profileUser.getAddress();
                    DoB = profileUser.getBirthday();
                    phone = profileUser.getPhone();

                    ArrayList<Status> statuses = profileUser.getPostList();
                    if(statuses !=null){
                    statusArrayList.addAll(statuses);
                    statusAdapter.notifyDataSetChanged();
                        viewFlipper.setDisplayedChild(MODE_RECYCLEVIEW);
                    } else {
                        viewFlipper.setDisplayedChild(MODE_NODATA);
                    }

            //        String[] coverPhoto = profileUser.getCoverPhoto();
                    showSlider(profileUser.getCoverPhoto());
                    Log.d("phi", profileUser.toString());
                } else {
                    Utils.showToast(getContext(), "Có lỗi");
                }
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Utils.showToast(getActivity(), "No internet");
            }
        });
    }


    private void showSlider(String[] coverPhoto) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        for (String url : coverPhoto) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // DefaultSliderView textSliderView = new DefaultSliderView(getActivity());

            textSliderView
                    .image(url)
                    .description("DEMO")
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .bundle(new Bundle());
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setDuration(4000);

    }

    private void addListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProfile(userInfor.getUserName(), userInfor.getUserID());
            }
        });
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensurePermission();
            }
        });

        

    }
    private void ensurePermission(){
        listPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if(checkPermission(getActivity(), listPermissions)){
            openGalary();
        }else {
            requestPermissions(listPermissions, REQUEST_PERMISSION_CODE);
        }
    }
    private boolean checkPermission(Context context, String[] listPermission) {
        if (context != null && listPermission != null) {
            for (String permission : listPermission) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_GET_IMAGE_CODE){
            if(checkPermission(getActivity(), listPermissions)){
                openGalary();
            }
        } else{
            Utils.showToast(getActivity(), "Request is denied!");
        }
    }
    private void openGalary() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE && data != null) {
            Uri uri = data.getData();
            ivAva.setImageURI(uri);
            upLoadImage(uri);
        }
    }

    private void upLoadImage(Uri uri) {
        final StorageReference ref = storageReference.child("avatar/" + uri.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                return ref.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Utils.showToast(getActivity(),"Up load thành công");
                    // thành công
                    // thực hiện update avata.
                    updateAvatar(task.getResult().toString());
                    Log.d("phi", task.getResult().toString());
                } else {
                    // thất bại
                    Utils.showToast(getActivity(),"Update thất bại");
                }
            }
        });
    }
    private  void  updateAvatar(String avatarUrl){
        UpdateAvatarUri avatarSend = new UpdateAvatarUri(avatarUrl);
        retrofitService.upDateAvatar(userInfor.getUserID(), avatarSend).enqueue(new Callback<Avatar>() {
            @Override
            public void onResponse(Call<Avatar> call, Response<Avatar> response) {
                Avatar avatarRes = response.body();
                if(response.code() == 200 && avatarRes != null){
                    RealmContext.getInstance().upDateAvatar(avatarRes.getAvatarUrl());
                } else {
                    Utils.showToast(getActivity(), "This is fail while getting image!");
                }
            }

            @Override
            public void onFailure(Call<Avatar> call, Throwable t) {
                Utils.showToast(getActivity(), "No Internet!");
            }
        });

        
    }



    @Override
    public void onDeleteStatus(Status status) {

    }

    @Override
    public void onDetailImage(int positionAdapter, int positionImage, Status status) {

    }

    @Override
    public void onLikeClick(int position, Status status) {

    }

    @Override
    public void onCommentClick(int position, Status status) {

    }

    @Override
    public void onEditStatus(Status status) {

    }


}
