package com.code4a.jlibrarydemo.commoninteractor;

import com.code4a.jlibrarydemo.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public interface GankCommonInteractor {

    interface LoadSplashResListener {

        void onResLoaded(GirlsBean girlsBean);

        void onDataNotAvailable();
    }

    void getRes(String type, int page, int count, LoadSplashResListener listener);

    void getRes(String type, LoadSplashResListener listener);

    void cancel();
}
