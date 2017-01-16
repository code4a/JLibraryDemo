package com.code4a.jlibrarydemo.splash;

import com.code4a.jlibrarydemo.http.GirlsService;
import com.code4a.jlibrarydemo.http.SplashRetrofit;
import com.code4a.jlibrarydemo.splash.data.GirlsBean;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashInteractorImpl implements SplashInteractor {

    @Override
    public void getRes(int page, int size, final LoadSplashResListener listener) {
        SplashRetrofit.getRetrofit()
                .create(GirlsService.class)
                .getGirls("", size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onDataNotAvailable();
                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        listener.onResLoaded(girlsBean);
                    }
                });
    }

    @Override
    public void getRes(LoadSplashResListener listener) {
        getRes(1, 1, listener);
    }
}
