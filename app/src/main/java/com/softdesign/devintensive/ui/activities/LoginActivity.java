package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout mFrameLayout;
    private Button mButtonLogin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = (Button) findViewById(R.id.login_btn);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout_login);

        mButtonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                showSnackBar("Click button");
                intent = new Intent();
                intent.putExtra(ConstantManager.LOGIN_KEY, "return from login activity");
                setResult(RESULT_OK, intent);
                super.onBackPressed();
                break;
        }
    }

    private void showSnackBar (String message) {
        Snackbar.make(mFrameLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
