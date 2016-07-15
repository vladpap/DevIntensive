package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;

import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintenciveApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladimirpapin on 28.06.16.
 */
public class PreferenceManager {

    private SharedPreferences mSharedPreferences;

    private static final String[] USER_FIELDS = {
            ConstantManager.USER_PHONE_KEY,
            ConstantManager.USER_MAIL_KEY,
            ConstantManager.USER_VK_KEY,
            ConstantManager.USER_GIT_KEY,
            ConstantManager.USER_ABOUT_KEY
    };

    private static final String[] USER_VALUES = {
            ConstantManager.USER_RAITING_VALUE,
            ConstantManager.USER_CODE_LINES_VALUE,
            ConstantManager.USER_PROJECT_VALUE
    };

    public PreferenceManager() {
        this.mSharedPreferences = DevintenciveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userField) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            editor.putString(USER_FIELDS[i], userField.get(i));
        }
        editor.apply();
    }

    public String getFieldFromKey(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public List<String> loadUserProfileData() {
        List<String> userField = new ArrayList<>();
        for (int i = 0; i < USER_FIELDS.length; i++) {
            userField.add(mSharedPreferences.getString(USER_FIELDS[i], ConstantManager.NULL_SHARE_PREFERENCES));
        }
        if (userField.get(0).equals(ConstantManager.NULL_SHARE_PREFERENCES)) {
            return null;
        } else {
            return userField;
        }
    }

    public void saveUserName(String name) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_NAME_KEY, name);
        editor.apply();
    }

    public String loadUserName() {
        return mSharedPreferences.getString(ConstantManager.USER_NAME_KEY, "User name");
    }

    public void saveUserPhoto(Uri uri) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public void saveAvatarPhoto(Uri uri) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_AVATAR_KEY, uri.toString());
        editor.apply();
    }

    public Uri loadUserPhoto() {
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY, "android.resource://com.softdesign.devintensive/drawable/user_bg"));
    }

    public Uri loadAvatarPhoto() {
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_AVATAR_KEY, loadUserPhoto().toString()));
    }

    public void saveAuthToken(String authToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN_KEY, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN_KEY, "null");
    }

    public void saveUserID(String userId) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY, userId);
        editor.apply();
    }

    public String getUserId() {
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY, "null");
    }

    public List<String> loadUserProfileValues() {
        List<String> userValues = new ArrayList<>();
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_RAITING_VALUE, "0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_CODE_LINES_VALUE, "0"));
        userValues.add(mSharedPreferences.getString(ConstantManager.USER_PROJECT_VALUE, "0"));
        return userValues;
    }

    public void saveUserProfileValues(int[] userValues) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (int i = 0; i < USER_VALUES.length; i++) {
            editor.putString(USER_VALUES[i], String.valueOf(userValues[i]));
        }
        editor.apply();
    }
}
