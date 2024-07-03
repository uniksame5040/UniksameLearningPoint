package com.uniksame.uniksamelearningpoint.unikservicesutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    public PreferenceUtils() {
    }

    public static boolean saveUsername(String username, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(UnikConstants.KEY_USERNAME, username);
        prefEditor.apply();
        return true;
    }

    public static String getUsernamePref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(UnikConstants.KEY_USERNAME, null);
    }

    public static boolean saveFullName(String fullName, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(UnikConstants.KEY_FULLNAME, fullName);
        prefEditor.apply();
        return true;
    }

    public static String getFullNamePref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(UnikConstants.KEY_FULLNAME, null);
    }

    public static boolean saveImgUrl(String imgUrl, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(UnikConstants.KEY_IMAGE_URL, imgUrl);
        prefEditor.apply();
        return true;
    }

    public static String getImgUrlPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(UnikConstants.KEY_IMAGE_URL, null);
    }

    public static boolean saveDatePref(String dateString, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(UnikConstants.KEY_DATE, dateString);
        prefEditor.apply();
        return true;
    }

    public static String getDatePref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(UnikConstants.KEY_DATE, "");
    }

    public static boolean saveProgressPref(int progress, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putInt(UnikConstants.KEY_PROGRESS, progress);
        prefEditor.apply();
        return true;
    }

    public static int getProgressPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(UnikConstants.KEY_PROGRESS, 0);
    }

    public static boolean savePostProgressPref(int progressPost, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putInt(UnikConstants.KEY_POST_PROGRESS, progressPost);
        prefEditor.apply();
        return true;
    }

    public static int getPostProgressPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(UnikConstants.KEY_POST_PROGRESS, 0);
    }
}
