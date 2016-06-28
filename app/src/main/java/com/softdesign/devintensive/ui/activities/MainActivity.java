package com.softdesign.devintensive.ui.activities;

import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private boolean mCurrentEditMode = false;

    private ImageView mCallMsgImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFloatingActionButton;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserAbout;

    private List<EditText> mUserInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        mCallMsgImg = (ImageView)findViewById(R.id.call_msg_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mUserPhone = (EditText) findViewById(R.id.phoneNumber);
        mUserMail  = (EditText) findViewById(R.id.email);
        mUserVk    = (EditText) findViewById(R.id.vk);
        mUserGit   = (EditText) findViewById(R.id.repository);
        mUserAbout = (EditText) findViewById(R.id.about);

        mUserInfo = new ArrayList<>();
        mUserInfo.add(mUserPhone);
        mUserInfo.add(mUserMail);
        mUserInfo.add(mUserVk);
        mUserInfo.add(mUserGit);
        mUserInfo.add(mUserAbout);

        mFloatingActionButton.setOnClickListener(this);
        setupToolbar();
        setupDrawer();



        if (savedInstanceState == null) {
//            активити запускается впервые
        } else {
            mCurrentEditMode = savedInstanceState.getBoolean(ConstantManager.EDIT_MODE_KEY);
        }

        changeEditMode(mCurrentEditMode);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab :
                mCurrentEditMode = !mCurrentEditMode;
                changeEditMode(mCurrentEditMode);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    private void showSnackBar (String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackBar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    /**
     *
     * переключает режим редактирования
     * @param mode если true режим редактирования, если false режим просмотра
     */
    private void changeEditMode(boolean mode) {
        for (EditText userValue : mUserInfo) {
            userValue.setEnabled(mode);
            userValue.setFocusable(mode);
            userValue.setFocusableInTouchMode(mode);
        }
        if (!mCurrentEditMode) {
            mFloatingActionButton.setImageResource(R.drawable.ic_create_black_24dp);
        } else {
            mFloatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
        }
        mCurrentEditMode = mode;
    }

    private void loadUserInfoValue() {

    }

    private void saveUserInfoValue() {

    }
}
