package com.code4a.jlibrarydemo;

import android.app.Application;

import com.code4a.jlibrary.bugly.JBuglyManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by code4a on 2016/12/24.
 */

public class JLibraryApp extends Application {

    private static JLibraryApp mApp;

    public static String currentGirl = "http://ww4.sinaimg.cn/large/610dc034jw1fbeerrs7aqj20u011htec.jpg";

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        AndroidCrash.initCrashHandler(this, R.string.app_crash, LoginActivity.class);
        JBuglyManager.initCrashReportAndUpdate(this, true);
    }

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static JLibraryApp getInstance(){
        return mApp;
    }
}
