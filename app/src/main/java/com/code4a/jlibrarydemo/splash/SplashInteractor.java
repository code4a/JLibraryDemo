package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrarydemo.splash.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public interface SplashInteractor {

    interface LoadSplashResListener {

        void onResLoaded(GirlsBean girlsBean);

        void onDataNotAvailable();
    }

    void getRes(int page, int size, LoadSplashResListener listener);

    void getRes(LoadSplashResListener listener);
}
