package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractor;
import com.code4a.jlibrarydemo.commoninteractor.GirlsCommonInteractorImpl;
import com.code4a.jlibrarydemo.data.GirlsBean;

/**
 * Created by code4a on 2017/1/12.
 */

public class GirlsPresenterImpl extends GirlsPresenter {

    private GirlsCommonInteractor girlsCommonInteractor;

    protected GirlsPresenterImpl(GirlsView view) {
        super(view);
        this.girlsCommonInteractor = new GirlsCommonInteractorImpl();
    }

    @Override
    public void start(){
        getGirls(1, 20, true);
    }

    @Override
    public void getGirls(int page, int size, final boolean isRefresh) {
        girlsCommonInteractor.getRes(page, size, new GirlsCommonInteractor.LoadSplashResListener() {
            @Override
            public void onResLoaded(GirlsBean girlsBean) {
                if(isRefresh){
                    getView().refresh(girlsBean.getResults());
                }else{
                    getView().load(girlsBean.getResults());
                }
                getView().showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                if(isRefresh) {
                    getView().showError();
                }
            }
        });
    }

    @Override
    public void cancel() {
        girlsCommonInteractor.cancel();
        release();
    }
}
