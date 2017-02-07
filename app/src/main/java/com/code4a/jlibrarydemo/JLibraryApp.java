package com.code4a.jlibrarydemo;

import android.app.Application;
import android.util.Log;

import com.code4a.jlibrary.bugly.JBuglyManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

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
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return client;
    }

    private static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.i("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    public static JLibraryApp getInstance(){
        return mApp;
    }
}
