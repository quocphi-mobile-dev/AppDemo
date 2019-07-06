package com.example.appdemo_1704.home_screen.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.appdemo_1704.R;
import com.example.appdemo_1704.dbcontext.RealmContext;
import com.example.appdemo_1704.json_models.response.ProfileUser;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final int REQUEST_CODE_INTENT = 1;
    RetrofitService retrofitService ;
    UserInfo userInfo;
    SliderLayout sliderLayout;
    CircleImageView imvChageAvatar;
    CircleImageView imvAvatar;
    StorageReference storageReference;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        storageReference = FirebaseStorage.getInstance().getReference();
        addListener();
        getProfile();
    }

    private void init() {
        sliderLayout = getView().findViewById(R.id.slider);
        userInfo = RealmContext.getInstance().getUser();
        retrofitService= RetrofitUtils.getInstance().createService(RetrofitService.class);
        imvChageAvatar = getView().findViewById(R.id.imv_chage_avatar);
        imvAvatar = getView().findViewById(R.id.imv_avatar1);
    }
    private void getProfile(){
        retrofitService.getProfile(userInfo.getUserName(),userInfo.getUserID()).enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                ProfileUser profileUser = response.body();
                if (response.code() == 200&& profileUser!= null){
                    // lấy ảnh
                    String[] coverPhoto = profileUser.getCoverPhoto();

                    showSlider(coverPhoto);
                    Log.d("phi",profileUser.toString());
                }else {
                    Utils.showToast(getContext(),"Có lỗi");
                }
            }
            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {

            }
        });
    }
    private void showSlider(String[] coverPhoto){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        for(String url : coverPhoto){
            TextSliderView textSliderView = new TextSliderView(getContext());
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
        imvChageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalary();;
            }
        });
    }
    private void openGalary() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_INTENT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_INTENT  && data!= null){
            Uri uri = data.getData();
            imvAvatar.setImageURI(uri);
            upLoadImage(uri);
        }
    }
    private void upLoadImage(Uri uri) {
        final StorageReference ref = storageReference.child("avatar/"+ uri.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                return  ref.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    // thành công
                    Log.d("phi",task.getResult().toString());
                }else{
                    // thất bại
                    Toast.makeText(getContext(),"Thất bại ",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}
