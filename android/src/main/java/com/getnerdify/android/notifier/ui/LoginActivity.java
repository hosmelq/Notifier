package com.getnerdify.android.notifier.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.getnerdify.android.notifier.NotifierApplication;
import com.getnerdify.android.notifier.R;
import com.getnerdify.android.notifier.model.RetrofitUser;
import com.getnerdify.android.notifier.sync.NotifierService;
import com.getnerdify.android.notifier.util.PrefUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    protected TextView mUsernameText;
    protected TextView mPasswordText;
    protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mUsernameText = (TextView) findViewById(R.id.email_text);
        mPasswordText = (TextView) findViewById(R.id.password_text);
        mLoginButton = (Button) findViewById(R.id.login);

        mPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    proccessLogin();
                }

                return false;
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessLogin();
            }
        });
    }

    private void proccessLogin() {
        String username = mUsernameText.getText().toString().trim().toLowerCase();
        String password = mPasswordText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage(getString(R.string.login_failed_text))
                .setTitle("Opss!")
                .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            // Login
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(getString(R.string.login_logging));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            RetrofitUser user = new RetrofitUser(username, password);
            NotifierService service = new NotifierService();

            service.login(user, new Callback<RetrofitUser>() {
                @Override
                public void success(RetrofitUser user, Response response) {
                    progressDialog.dismiss();

                    if (user.getCodigo() == 0) {
                        NotifierApplication.updateParseInstallation(user);
                        PrefUtils.markLoginDone(LoginActivity.this, user);

                        Intent intent = new Intent(LoginActivity.this, BrowseNotificationsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(user.getDescripcion())
                            .setTitle("Opss!")
                            .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG, error.getMessage());
                }
            });
        }
    }

}
