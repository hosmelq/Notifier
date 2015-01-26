package com.getnerdify.android.notifier;

import android.app.Application;

import com.getnerdify.android.notifier.ui.CodeActivity;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

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

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("userId", user.getObjectId());
        installation.put("userEmail", user.getEmail());
        installation.saveInBackground();
    }

}
