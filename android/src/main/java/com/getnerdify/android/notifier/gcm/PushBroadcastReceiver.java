package com.getnerdify.android.notifier.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.getnerdify.android.notifier.ui.CodeActivity;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

public class PushBroadcastReceiver extends ParsePushBroadcastReceiver {

    final public static String TAG = PushBroadcastReceiver.class.getSimpleName();

    @Override
    protected Class<? extends Activity> getActivity(Context context, Intent intent) {
        return CodeActivity.class;
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        if (ParseUser.getCurrentUser() == null) {
            return;
        }

        super.onPushReceive(context, intent);
    }

}
