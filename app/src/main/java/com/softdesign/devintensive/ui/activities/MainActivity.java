package com.softdesign.devintensive.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.CircleTransform;
import com.softdesign.devintensive.utils.ConstantManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX + "Main Activity";

    private DataManager mDataManager;

    private boolean mCurrentEditMode = false;


//    private ImageView mCallMsgImg;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private ImageView mImageViewUrerPhoto;
    private FloatingActionButton mFloatingActionButton;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserAbout;
    private RelativeLayout mProfilePlaceholder;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private AppBarLayout mAppBarLayout;
    private ImageView mProfileImageView;

    private ImageView mPhoneCall;
    private ImageView mPhoneSms;
    private ImageView mSendEmail;
    private ImageView mSendEmail_2;
    private ImageView mViewVk;
    private ImageView mViewVk_2;
    private ImageView mViewGit;
    private ImageView mViewGit_2;

    private List<EditText> mUserInfoViews;

    private AppBarLayout.LayoutParams mAppBarParam = null;

    private File mPhotoFile = null;
    private Uri mSelectedImage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Log.d(TAG, "onCreate");

        mDataManager            = DataManager.getINSTANCE();

//        mCallMsgImg             = (ImageView)findViewById(R.id.phone_sms);
        mCoordinatorLayout      = (CoordinatorLayout) findViewById(R.id.main_coordinator_content);
        mToolbar                = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer       = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mImageViewUrerPhoto     = (ImageView)findViewById(R.id.user_photo_drawer_img_nav);
        mFloatingActionButton   = (FloatingActionButton) findViewById(R.id.fab);
        mProfilePlaceholder     = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar      = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mAppBarLayout           = (AppBarLayout) findViewById(R.id.app_bar_layout);


        mUserPhone = (EditText) findViewById(R.id.phoneNumber);
        mUserMail  = (EditText) findViewById(R.id.email);
        mUserVk    = (EditText) findViewById(R.id.vk);
        mUserGit   = (EditText) findViewById(R.id.repository);
        mUserAbout = (EditText) findViewById(R.id.about);
        mProfileImageView = (ImageView) findViewById(R.id.user_photo_img);

        mPhoneCall = (ImageView) findViewById(R.id.phone_call);
        mPhoneSms = (ImageView) findViewById(R.id.phone_sms);
        mSendEmail = (ImageView) findViewById(R.id.send_mail);
        mSendEmail_2 = (ImageView) findViewById(R.id.send_mail_2);
        mViewVk = (ImageView) findViewById(R.id.view_vk);
        mViewVk_2 = (ImageView) findViewById(R.id.view_vk_2);
        mViewGit = (ImageView) findViewById(R.id.view_git);
        mViewGit_2 = (ImageView) findViewById(R.id.view_git_2);


        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
        mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserAbout);



        mFloatingActionButton.setOnClickListener(this);
        mProfilePlaceholder.setOnClickListener(this);

        mPhoneCall.setOnClickListener(this);
        mPhoneSms.setOnClickListener(this);
        mSendEmail.setOnClickListener(this);
        mSendEmail_2.setOnClickListener(this);
        mViewVk.setOnClickListener(this);
        mViewVk_2.setOnClickListener(this);
        mViewGit.setOnClickListener(this);
        mViewGit_2.setOnClickListener(this);


        setupToolbar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this)
                .load(mDataManager.getPreferenceManager().loadUserPhoto())
                .into(mProfileImageView);



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
//        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause");
        saveUserInfoValue();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab :
                mCurrentEditMode = !mCurrentEditMode;
                changeEditMode(mCurrentEditMode);
                break;
            
            case R.id.profile_placeholder:
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
            case R.id.phone_call:
                callPhoneNumber(mUserPhone.getText().toString());
                break;
            case R.id.phone_sms:
                sendSms(mUserPhone.getText().toString());
                break;
            case R.id.send_mail:
            case R.id.send_mail_2:
                sendEmail(mUserMail.getText().toString());
                break;
            case R.id.view_vk:
            case R.id.view_vk_2:
                openWebPage(mUserVk.getText().toString());
                break;
            case R.id.view_git:
            case R.id.view_git_2:
                openWebPage(mUserGit.getText().toString());
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
        mAppBarParam = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        uploadAvatar();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackBar(item.getTitle().toString());
                if (item.getItemId() == R.id.user_profile_login) {
                    startLoginActivity();
                }
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }


    /**
     * Получение результата из другой Activity (фото из камеры или галлереи)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    mSelectedImage = data.getData();
                    insertProfileImage (mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null) {
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_LOGIN_CODE:
                // TODO: 08.07.16 Обработка реквеста логин активити
                showSnackBar(data.getStringExtra(ConstantManager.LOGIN_KEY) + " : " + resultCode);
                break;
        }
    }



    /**
     *
     * переключает режим редактирования
     * @param mode если true режим редактирования, если false режим просмотра
     */
    private void changeEditMode(boolean mode) {
        mUserPhone.requestFocus();
        for (EditText userValue : mUserInfoViews) {
            userValue.setEnabled(mode);
            userValue.setFocusable(mode);
            userValue.setFocusableInTouchMode(mode);
        }
        if (!mCurrentEditMode) {
            mFloatingActionButton.setImageResource(R.drawable.ic_create_black_24dp);
            mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.color_white));
            unlockToolbar();
            hideProfilePlaceholder();
            saveUserInfoValue();
            uploadAvatar();
        } else {
            mUserPhone.requestFocus();
            mFloatingActionButton.setImageResource(R.drawable.ic_done_black_24dp);
            mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            lockToolbar();
            showProfilePlaceholder();
        }
        mCurrentEditMode = mode;
    }

    /**
     * Извлечение данных из PreferenceManager
     */
    private void loadUserInfoValue() {
        List<String> userData = mDataManager.getPreferenceManager().loadUserProfileData();
        if (userData == null) {
            return;
        }
        for (int i = 0; i < userData.size(); i++) {
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    /**
     * Сохранение данных в PreferenceManager
     */
    private void saveUserInfoValue() {
        List<String> userDate = new ArrayList<>();
        for (EditText userFieldView : mUserInfoViews) {
            userDate.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferenceManager().saveUserProfileData(userDate);
    }

    private void loadPhotoFromGallery() {
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_chose_message)), ConstantManager.REQUEST_GALLERY_PICTURE);
    }

    private void loadPhotoFromCamera() {

        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                mPhotoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: 07.07.16 Обработать ошибку
            }
            if (mPhotoFile != null) {
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        } else {
            askPermissionCamera();
        }
    }

    /**
     *  Вызывается Snakebar с кнопкой setting
     */
    private void viewSnakebarPermission() {
        Snackbar.make(mCoordinatorLayout, R.string.ask_permission_snakebar, Snackbar.LENGTH_LONG)
                .setAction(R.string.allow, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSetting();
                    }
                }).show();
    }

    /**
     *  Cоздание запроса permission для камеры
     *   Manifest.permission.CAMERA
     *   Manifest.permission.WRITE_EXTERNAL_STORAGE
     *   или одно из них в зависимости от уже разрешенных
     */
    private void askPermissionCamera() {
        ArrayList<String> listPermission = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            listPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        String[] stringArray = listPermission.toArray(new String[listPermission.size()]);
        ActivityCompat.requestPermissions(this, stringArray, ConstantManager.CAMERA_REQUEST_PERMITION_CODE);
    }

    /**
     *  Обработка запросов permission
     *  Если запрещен показ запросов, то показывается Snakebar
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMITION_CODE) {
            boolean permissionDenie = false;
            boolean permissionBlocked = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionDenie = true;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        permissionBlocked = true;
                    }
                }
            }
            if (permissionBlocked) {
                viewSnakebarPermission();
            } else {
                if (!permissionDenie) {
                    loadPhotoFromCamera();
                }
            }

        }   // end if CAMERA_REQUEST_PERMITION_CODE

        if (requestCode == ConstantManager.SEND_SMS_REQUEST_PERMITION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms(mUserPhone.getText().toString());
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    viewSnakebarPermission();
                }
            }
        }

        if (requestCode == ConstantManager.CALL_PHONE_REQUEST_PERMITION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber(mUserPhone.getText().toString());
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                    viewSnakebarPermission();
                }
            }
        }
    }

    private void hideProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder() {
        mProfilePlaceholder.setVisibility(View.VISIBLE);
    }

    /**
     * Блокировка Toolbar при редактировании
     */
    private void lockToolbar() {
        mAppBarLayout.setExpanded(true, true);
        mAppBarParam.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParam);
    }

    /**
     *  Разблокировка Toolbar
     */
    private void unlockToolbar() {
        mAppBarParam.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL|
                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParam);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItem = {
                        getString(R.string.user_profile_dialog_gallery),
                        getString(R.string.user_profile_dialog_camera),
                        getString(R.string.user_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_title));
                builder.setItems(selectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                loadPhotoFromGallery();
                                break;
                            case 1:
                                loadPhotoFromCamera();
                                break;
                            case 2:
                                dialog.cancel();
                                break;
                        }
                    }
                });
                return builder.create();
            default:
                return null;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return image;
    }

    /**
     * Установка битмап в профайл вью
     * @param selectedImage
     */
    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this)
                .load(selectedImage)
                .into(mProfileImageView);
        mDataManager.getPreferenceManager().saveUserPhoto(selectedImage);
    }

    /**
     *  установка битмап в аватар уменьшение размеров и скругление
     */
    private void uploadAvatar() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        ImageView navImgView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_photo_drawer_img_nav);
        Picasso.with(this)
                .load(mDataManager.getPreferenceManager().loadUserPhoto())
                .resize(400,400)
                .centerCrop()
                .transform(new CircleTransform())
                .into(navImgView);
    }

    /**
     *  Вызов системных установок приложения
     */
    public void openApplicationSetting() {
        Intent appSettingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingIntent, ConstantManager.PERMITION_REQUEST_SETTING_CODE);
    }

    /**
     *  Открытие веб страницы
     * @param url
     */
    private void openWebPage(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.contains("http://") ? url : ("http://" + url)));
        startActivity(browserIntent);
    }

    /**
     * Отправка почты
     * @param email
     */
    private void sendEmail(String email) {
        Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
        sendEmailIntent.setType("text/plain");
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Тема письма");
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT, "Текст письма");
        startActivity(Intent.createChooser(sendEmailIntent, "Отправка письма..."));
    }

    /**
     *  Вызов по номеру телефона
     * @param phoneNumber
     */
    private void callPhoneNumber(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + correctPhoneNumberForSendSmsOrCall(phoneNumber)));
            startActivity(dialIntent);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE} , ConstantManager.CALL_PHONE_REQUEST_PERMITION_CODE);
        }
    }

    /**
     * Отправка СМС на номер телефона
     * @param phoneNumber
     */
    private void sendSms(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Intent sendSmsIntent = new Intent(Intent.ACTION_VIEW);
            sendSmsIntent.putExtra("address", correctPhoneNumberForSendSmsOrCall(phoneNumber));
            sendSmsIntent.putExtra("sms_body", "Текст сообщения");
            sendSmsIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendSmsIntent);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS} , ConstantManager.SEND_SMS_REQUEST_PERMITION_CODE);
        }
    }

    /**
     * Убирает из номера телефона пробелы и скобки
     * @param phoneNum
     * @return
     */
    private String correctPhoneNumberForSendSmsOrCall(String phoneNum) {
        return phoneNum.replace(" ", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");
    }

    /**
     *  Вызов логин активити
     */
    private void startLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(loginIntent, ConstantManager.REQUEST_LOGIN_CODE);
    }
}
