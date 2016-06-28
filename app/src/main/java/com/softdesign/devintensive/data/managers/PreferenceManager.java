package com.softdesign.devintensive.data.managers;

import android.content.SharedPreferences;

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
            ConstantManager.USER_ABOUT_KEY};

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

    public List<String> loadUserProfileData() {
        List<String> userField = new ArrayList<>();
        for (int i = 0; i < USER_FIELDS.length; i+) {
            userField.add(mSharedPreferences.getString(USER_FIELDS[i], "null"));
        }
        return userField;
    }
}
