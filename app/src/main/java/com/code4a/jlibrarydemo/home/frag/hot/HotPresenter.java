package com.code4a.jlibrarydemo.home.frag.hot;

import com.code4a.jlibrary.base.presenter.BasePresenter;

/**
 * Created by code4a on 2017/1/12.
 */

public abstract class HotPresenter extends BasePresenter<HotView> {

    protected HotPresenter(HotView view) {
        super(view);
    }

    public abstract void start(int page, int size);

    public abstract void cancel();
}
