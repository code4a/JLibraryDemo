package com.code4a.jlibrarydemo.http;

import com.code4a.jlibrarydemo.JLibraryApp;
import com.code4a.jlibrarydemo.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashRetrofit {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (SplashRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.GANHUO_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(JLibraryApp.defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
