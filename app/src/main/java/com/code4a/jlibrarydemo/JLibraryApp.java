package com.code4a.jlibrarydemo;

import android.app.Application;

import com.code4a.jlibrary.bugly.JBuglyManager;

/**
 * Created by code4a on 2016/12/24.
 */

public class JLibraryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        AndroidCrash.initCrashHandler(this, R.string.app_crash, LoginActivity.class);
        JBuglyManager.initCrashReportAndUpdate(this, true);
    }
}
