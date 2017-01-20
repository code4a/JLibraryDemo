package com.code4a.jlibrarydemo.commoninteractor;

import android.util.Log;

import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.http.GirlsService;
import com.code4a.jlibrarydemo.http.SplashRetrofit;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by code4a on 2017/1/12.
 */

public class GankCommonInteractorImpl implements GankCommonInteractor {

    private static final String TAG = "InteractorImpl";
    private boolean isCanceled = false;

    @Override
    public void getRes(String type, int page, int count, final LoadSplashResListener listener) {
        isCanceled = false;
        SplashRetrofit.getRetrofit()
                .create(GirlsService.class)
                .getGirls(type, count, page)
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
    public void getRes(String type, LoadSplashResListener listener) {
        getRes(type, 1, 1, listener);
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }
}
