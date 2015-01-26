package com.getnerdify.android.notifier.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.getnerdify.android.notifier.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isFinishing()) {
            return;
        }

        setContentView(R.layout.activity_code);

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setBackgroundResource(R.color.theme_primary_dark);
        toolbar.setNavigationIcon(R.drawable.ic_up);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

}
