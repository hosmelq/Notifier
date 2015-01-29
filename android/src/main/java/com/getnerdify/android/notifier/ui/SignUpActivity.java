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

public class SignUpActivity extends Activity {

    public static final String TAG = SignUpActivity.class.getSimpleName();
    protected TextView mNameText;
    protected TextView mCellphoneText;
    protected TextView mEmailText;
    protected TextView mPasswordText;
    protected Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        mNameText = (TextView) findViewById(R.id.name_text);
        mCellphoneText = (TextView) findViewById(R.id.username_cellphone);
        mEmailText = (TextView) findViewById(R.id.email_text);
        mPasswordText = (TextView) findViewById(R.id.password_text);
        mSignupButton = (Button) findViewById(R.id.signup);

        mPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    proccessSignUp();
                }

                return false;
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proccessSignUp();
            }
        });
    }

    private void proccessSignUp() {
        String name = mNameText.getText().toString().trim();
        String cellphone = mCellphoneText.getText().toString().trim();
        String email = mEmailText.getText().toString().trim();
        String password = mPasswordText.getText().toString().trim();

        if (name.isEmpty() || cellphone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
            builder.setMessage(getString(R.string.signup_failed_text))
                .setTitle("Opss!")
                .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage(getString(R.string.signup_signingup));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();

            // create the new user
            final RetrofitUser user = new RetrofitUser(name, cellphone, email, password);
            NotifierService service = new NotifierService();

            service.signup(user, new Callback<RetrofitUser>() {
                @Override
                public void success(RetrofitUser retrofitUser, Response response) {
                    progressDialog.dismiss();

                    if (retrofitUser.getCodigo() == 0) {
                        NotifierApplication.updateParseInstallation(user);
                        PrefUtils.markLoginDone(SignUpActivity.this, user);

                        Intent intent = new Intent(SignUpActivity.this, BrowseNotificationsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                        builder.setMessage(retrofitUser.getDescripcion())
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
