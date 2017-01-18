package com.code4a.jlibrarydemo.splash;

import android.util.Log;

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

    private static final String TAG = "SplashInteractorImpl";
    private boolean isCanceled = false;

    @Override
    public void getRes(int page, int size, final LoadSplashResListener listener) {
        isCanceled = false;
        SplashRetrofit.getRetrofit()
                .create(GirlsService.class)
                .getGirls("福利", size, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted ---> : xxxxxxxxxxxxxx ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError ---> : " + e);
                        if(!isCanceled){
                            listener.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        if(!isCanceled){
                            listener.onResLoaded(girlsBean);
                        }
                    }
                });
    }

    @Override
    public void getRes(LoadSplashResListener listener) {
        getRes(1, 1, listener);
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}
