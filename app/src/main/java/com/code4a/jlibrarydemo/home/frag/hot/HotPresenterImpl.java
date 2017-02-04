package com.code4a.jlibrarydemo.home.frag.hot;

import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.utils.Constants;

/**
 * Created by code4a on 2017/1/12.
 */

public class HotPresenterImpl extends HotPresenter {

    private GankCommonInteractor gankCommonInteractor;

    protected HotPresenterImpl(HotView view) {
        super(view);
        this.gankCommonInteractor = new GankCommonInteractorImpl();
    }

    @Override
    public void start(int page, int size) {
        getView().showLoadingView();
        gankCommonInteractor.getRes(Constants.FULI, page, size, new GankCommonInteractor.LoadSplashResListener() {
            @Override
            public void onResLoaded(GirlsBean girlsBean) {
                getView().showGirl(girlsBean.getResults());
                getView().hideLoadingView();
            }

            @Override
            public void onDataNotAvailable() {
                getView().showGirl();
                getView().hideLoadingView();
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
