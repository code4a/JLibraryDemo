package com.code4a.jlibrarydemo.home.frag.hot;

import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public class HotPresenterImpl extends HotPresenter {

    private GirlsCommonInteractor girlsCommonInteractor;

    protected HotPresenterImpl(HotView view) {
        super(view);
        this.girlsCommonInteractor = new GirlsCommonInteractorImpl();
    }

    @Override
    public void start(int page, int size) {
        getView().showLoadingView();
        girlsCommonInteractor.getRes(page, size, new GirlsCommonInteractor.LoadSplashResListener() {
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
        girlsCommonInteractor.cancel();
        release();
    }
}
