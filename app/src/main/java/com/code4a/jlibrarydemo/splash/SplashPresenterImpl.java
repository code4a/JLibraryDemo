package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrarydemo.JLibraryApp;
import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashPresenterImpl extends SplashPresenter {

    private GirlsCommonInteractor girlsCommonInteractor;

    protected SplashPresenterImpl(SplashView view) {
        super(view);
        this.girlsCommonInteractor = new GirlsCommonInteractorImpl();
    }

    @Override
    public void start() {
        girlsCommonInteractor.getRes(new GirlsCommonInteractor.LoadSplashResListener() {
            @Override
            public void onResLoaded(GirlsBean girlsBean) {
                getView().showGirl(girlsBean.getResults().get(0).getUrl());
                JLibraryApp.currentGirl = girlsBean.getResults().get(0).getUrl();
            }

            @Override
            public void onDataNotAvailable() {
                getView().showGirl();
            }
        });
    }

    @Override
    public void cancel() {
        girlsCommonInteractor.cancel();
        release();
    }
}
