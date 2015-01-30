package com.getnerdify.android.notifier.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.model.RetrofitNotification;

public class NotificationDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.activity_notification_details);

        RetrofitNotification notification = (RetrofitNotification) getIntent().getSerializableExtra("notification");

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setBackgroundResource(R.color.theme_primary);
        toolbar.setNavigationIcon(R.drawable.ic_up);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(notification.getCompany());
        }

        TextView titleView = (TextView) findViewById(R.id.notification_title);
        TextView messageView = (TextView) findViewById(R.id.notification_message);

        titleView.setText(notification.getTitle());
        messageView.setText(notification.getMessage());
    }

}
