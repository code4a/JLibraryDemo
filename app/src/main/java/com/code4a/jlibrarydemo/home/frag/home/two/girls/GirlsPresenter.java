package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import com.code4a.jlibrary.base.presenter.BasePresenter;

/**
 * Created by code4a on 2017/1/12.
 */

public abstract class GirlsPresenter extends BasePresenter<GirlsView> {

    protected GirlsPresenter(GirlsView view) {
        super(view);
    }

    public abstract void start();

    public abstract void getGirls(int page, int size, boolean isRefresh);

    public abstract void cancel();

    public abstract void releaseRes();
}
