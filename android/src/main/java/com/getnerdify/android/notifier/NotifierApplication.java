package com.getnerdify.android.notifier;

import android.app.Application;

import com.getnerdify.android.notifier.model.RetrofitUser;
import com.getnerdify.android.notifier.provider.NotifierContract.InstallationsColumns;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class NotifierApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
            "psIYAPrPYQg5hBeuG52SUv6OSdFMTn0rHoRf9RCQ",
            "OKkfqi53kOPFKI9SWo0GY7FavdbzwkwsSJZp9sB3");

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void updateParseInstallation(RetrofitUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(InstallationsColumns.INSTALLATION_USER_ID, user.getId());
        installation.put(InstallationsColumns.INSTALLATION_USER_EMAIL, user.getEmail());
        installation.put(InstallationsColumns.INSTALLATION_USER_CELLPHONE, user.getCelular());
        installation.saveInBackground();
    }

}
