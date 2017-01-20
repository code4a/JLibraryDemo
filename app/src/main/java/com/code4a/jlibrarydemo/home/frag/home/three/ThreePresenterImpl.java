package com.code4a.jlibrarydemo.home.frag.home.three;

import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GankCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.utils.Constants;

/**
 * Created by code4a on 2017/1/12.
 */

public class ThreePresenterImpl extends ThreePresenter {

    private GankCommonInteractor gankCommonInteractor;

    protected ThreePresenterImpl(ThreeView view) {
        super(view);
        this.gankCommonInteractor = new GankCommonInteractorImpl();
    }

    @Override
    public void start(){
        getVideos(1, 20);
    }

    @Override
    public void getVideos(int page, int count) {
        getView().showLoadingView();
        gankCommonInteractor.getRes(Constants.VIDEO, page, count, new GankCommonInteractor.LoadSplashResListener() {
            @Override
            public void onResLoaded(GirlsBean girlsBean) {
                getView().load(girlsBean.getResults());
                getView().showNormal();
                getView().hideLoadingView();
            }

            @Override
            public void onDataNotAvailable() {
                getView().showError();
                getView().hideLoadingView();
            }
        });
    }

    @Override
    public void cancel() {
        gankCommonInteractor.cancel();
        release();
    }
}
