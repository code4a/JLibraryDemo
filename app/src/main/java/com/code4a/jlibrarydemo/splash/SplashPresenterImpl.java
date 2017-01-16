package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrarydemo.JLibraryApp;
import com.code4a.jlibrarydemo.splash.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashPresenterImpl extends SplashPresenter {

    private SplashInteractor splashInteractor;

    protected SplashPresenterImpl(SplashView view) {
        super(view);
        this.splashInteractor = new SplashInteractorImpl();
    }

    @Override
    public void start() {
        splashInteractor.getRes(new SplashInteractor.LoadSplashResListener() {
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
}
