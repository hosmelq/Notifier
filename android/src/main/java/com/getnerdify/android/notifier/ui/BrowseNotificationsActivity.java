package com.getnerdify.android.notifier.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.model.RetrofitNotification;
import com.getnerdify.android.notifier.util.PrefUtils;
import com.getnerdify.android.notifier.util.UIUtils;

public class BrowseNotificationsActivity extends BaseActivity
    implements NotificationsFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.activity_browse_notifications);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setTitle(R.string.title_explore);
        }

//        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateFragContentTopClearance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browse_notifications, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            PrefUtils.markLoginOutDone(this);
            navigateToWelcome();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNotificationSelected(RetrofitNotification notificationId) {
        Intent intent = new Intent(this, NotificationDetailsActivity.class);
        intent.putExtra("notification", notificationId);
        startActivity(intent);
    }

    private void updateFragContentTopClearance() {
        NotificationsFragment frag = (NotificationsFragment) getSupportFragmentManager().findFragmentById(
            R.id.notifications_fragment);

        if (frag == null) {
            return;
        }

        int actionBarClearance = UIUtils.calculateActionBarSize(this);

        frag.setContentTopClearance(actionBarClearance);
    }

}
