package com.getnerdify.android.notifier.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.getnerdify.android.notifier.model.RetrofitUser;

public class PrefUtils {

    public static final String TAG = PrefUtils.class.getSimpleName();

    /** Boolean indicating is the user is log in */
    public static final String PREF_LOGIN_DONE = "pref_login_done";

    /** Integer reference the user id */
    public static final String PREF_USER_ID = "pref_user_id";

    /** String reference the user email */
    public static final String PREF_USER_EMAIL = "pref_user_email";

    public static boolean isLoginDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_LOGIN_DONE, false);
    }

    public static void markLoginDone(final Context context, RetrofitUser  user) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit()
            .putBoolean(PREF_LOGIN_DONE, true)
            .putInt(PREF_USER_ID, user.getId())
            .putString(PREF_USER_EMAIL, user.getEmail())
            .apply();
    }

    public static void markLoginOutDone(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_LOGIN_DONE, false).apply();
    }

}
