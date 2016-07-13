package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.data.network.req.UserLoginReq;
import com.softdesign.devintensive.data.network.res.UserModelRes;
import com.softdesign.devintensive.utils.NetworkStatusChecker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private CoordinatorLayout mCoordinatorLayout;
    private Button mButtonLogin;
    private TextView mRememberPassord;
    private EditText mLogin, mPassword;

    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDataManager = DataManager.getInstance();

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_login_coordinator_layout);
        mButtonLogin = (Button) findViewById(R.id.login_btn);
        mLogin = (EditText) findViewById(R.id.email_logon);
        mPassword = (EditText) findViewById(R.id.password_logon);
        mRememberPassord =  (TextView) findViewById(R.id.were_password);

        mButtonLogin.setOnClickListener(this);
        mRememberPassord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                signIn();
                break;
            case R.id.were_password:
                rememberPassword();
                break;
        }
    }

    private void showSnackBar (String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void rememberPassword() {
        Intent rememberIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(UserModelRes userModel) {
        mDataManager.getPreferenceManager().saveAuthToken(userModel.getData().getToken());
        mDataManager.getPreferenceManager().saveUserID(userModel.getData().getUser().getId());
        saveUserValues(userModel);

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void signIn() {
        if (NetworkStatusChecker.isNetworkAvalible(this)) {
            Call<UserModelRes> call = mDataManager
                    .loginUser(new UserLoginReq(
                            mLogin.getText().toString(),
                            mPassword.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {
                    if (response.code() == 200) {
                        loginSuccess(response.body());
                    } else if (response.code() == 404) {
                        showSnackBar("Неверный логин или пароль.");
                    } else {
                        showSnackBar("Лелик, все пропало.");
                    }
                }

                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t) {
                    // TODO: 12.07.16  обработать ошибки ретрофита
                }
            });
        } else {
            showSnackBar("Нет сети, попробуйте попозже.");
        }
    }

    private void saveUserValues(UserModelRes userModel) {
        int[] userValues = {
                userModel.getData().getUser().getProfileValues().getRaiting(),
                userModel.getData().getUser().getProfileValues().getLinesCode(),
                userModel.getData().getUser().getProfileValues().getProjects()
        };
        mDataManager.getPreferenceManager().saveUserProfileValues(userValues);

        List<String> userDate = new ArrayList<>();
        userDate.add(userModel.getData().getUser().getContacts().getPhone());
        userDate.add(userModel.getData().getUser().getContacts().getEmail());
        String vk = userModel.getData().getUser().getContacts().getVk();
        if (vk.contains("https://")) {
            vk = vk.replaceAll("https://", "");
        }
        if (vk.contains("http://")) {
            vk = vk.replaceAll("http://", "");
        }
        userDate.add(vk);
        String  git = userModel.getData().getUser().getRepositories().getRepo().get(0).getGit();
        if (git.contains("https://")) {
            git = git.replaceAll("https://", "");
        }
        if (git.contains("http://")) {
            git = git.replaceAll("http://", "");
        }
        userDate.add(git);
        userDate.add(userModel.getData().getUser().getPublicInfo().getBio());
        mDataManager.getPreferenceManager().saveUserProfileData(userDate);

        String photoStr = userModel.getData().getUser().getPublicInfo().getPhoto();
        if (photoStr.length() != 0) {
            mDataManager.getPreferenceManager().saveUserPhoto(Uri.parse(photoStr));
        }
        String avatarStr = userModel.getData().getUser().getPublicInfo().getAvatar();
        if (avatarStr.length() != 0) {
            mDataManager.getPreferenceManager().saveAvatarPhoto(Uri.parse(avatarStr));
        }
        String name = userModel.getData().getUser().getFirstName() + userModel.getData().getUser().getSecondName();
        mDataManager.getPreferenceManager().saveUserName(name);
    }


}
