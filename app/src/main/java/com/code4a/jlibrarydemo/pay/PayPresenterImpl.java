package com.code4a.jlibrarydemo.pay;

import com.code4a.jlibrarydemo.data.AlipayBean;

/**
 * Created by code4a on 2017/2/6.
 */

public class PayPresenterImpl extends PayPresenter implements PayInteractor.OnGetFinishedListener {

    private PayInteractor payInteractor;

    protected PayPresenterImpl(PayView view) {
        super(view);
        this.payInteractor = new PayInteractorImpl();
    }

    @Override
    public void onGetPayInfo(String key) {
        if (getView() != null) {
            getView().showProgress();
        }
        this.payInteractor.getPayInfo(key, this);
    }

    @Override
    public void onCancel() {
        this.payInteractor.cancel();
    }

    @Override
    public void releaseRes() {
        release();
        this.payInteractor = null;
    }

    @Override
    public void onFailed() {
        if (getView() != null) {
            getView().hideProgress();
        }
    }

    @Override
    public void onSuccess(AlipayBean alipayBean) {
        if (getView() != null) {
            getView().hideProgress();
            if (alipayBean != null) {
                getView().doNext(alipayBean);
            }
        }
    }
}
