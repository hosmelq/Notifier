package com.getnerdify.android.notifier.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

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

        TextView labelView = (TextView) findViewById(R.id.label);
        TextView codeView = (TextView) findViewById(R.id.code);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.containsKey("com.parse.Data")) {
                try {
                    JSONObject pushData = new JSONObject(extras.getString("com.parse.Data"));
                    String code = pushData.getString("code");
                    String company = pushData.getString("company");

                    labelView.setText(String.format(getString(R.string.company_code), company));
                    codeView.setText(code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
