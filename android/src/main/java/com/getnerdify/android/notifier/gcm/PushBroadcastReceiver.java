package com.getnerdify.android.notifier.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.ui.CodeActivity;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

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

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            json.put("alert", String.format(context.getString(R.string.notification_code), json.getString("code")));
            intent.putExtra("com.parse.Data", json.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return;
        }

        super.onPushReceive(context, intent);
    }

}
