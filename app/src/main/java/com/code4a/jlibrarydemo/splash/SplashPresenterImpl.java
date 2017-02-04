package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrarydemo.JLibraryApp;
import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.utils.Constants;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashPresenterImpl extends SplashPresenter {

    private GankCommonInteractor gankCommonInteractor;

    protected SplashPresenterImpl(SplashView view) {
        super(view);
        this.gankCommonInteractor = new GankCommonInteractorImpl();
    }

    @Override
    public void start() {
        gankCommonInteractor.getRes(Constants.FULI, new GankCommonInteractor.LoadSplashResListener() {
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
        gankCommonInteractor.cancel();
    }

    @Override
    public void releaseRes() {
        release();
        gankCommonInteractor = null;
    }

}
