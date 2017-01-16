package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrary.base.presenter.BasePresenter;

/**
 * Created by code4a on 2017/1/12.
 */

public abstract class SplashPresenter extends BasePresenter<SplashView> {

    protected SplashPresenter(SplashView view) {
        super(view);
    }

    public abstract void start();
}
