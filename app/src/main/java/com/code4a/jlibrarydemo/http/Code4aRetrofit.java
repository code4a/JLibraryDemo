package com.code4a.jlibrarydemo.http;

import com.code4a.jlibrarydemo.JLibraryApp;
import com.code4a.jlibrarydemo.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by code4a on 2017/1/12.
 */

public class Code4aRetrofit {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (Code4aRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.CODE4A_API)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(JLibraryApp.defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
