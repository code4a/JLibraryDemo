package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.utils.Constants;

/**
 * Created by code4a on 2017/1/12.
 */

public class GirlsPresenterImpl extends GirlsPresenter {

    private GankCommonInteractor gankCommonInteractor;

    protected GirlsPresenterImpl(GirlsView view) {
        super(view);
        this.gankCommonInteractor = new GankCommonInteractorImpl();
    }

    @Override
    public void start() {
        getGirls(1, 20, true);
    }

    @Override
    public void getGirls(int page, int count, final boolean isRefresh) {
        gankCommonInteractor.getRes(Constants.FULI, page, count, new GankCommonInteractor.LoadSplashResListener() {
            @Override
            public void onResLoaded(GirlsBean girlsBean) {
                if (isRefresh) {
                    getView().refresh(girlsBean.getResults());
                } else {
                    getView().load(girlsBean.getResults());
                }
                getView().showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                if (isRefresh) {
                    getView().showError();
                }
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
