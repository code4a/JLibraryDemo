package com.code4a.jlibrarydemo.commoninteractor;

import com.code4a.jlibrarydemo.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public interface GirlsCommonInteractor {

    interface LoadSplashResListener {

        void onResLoaded(GirlsBean girlsBean);

        void onDataNotAvailable();
    }

    void getRes(int page, int size, LoadSplashResListener listener);

    void getRes(LoadSplashResListener listener);

    void cancel();
}
