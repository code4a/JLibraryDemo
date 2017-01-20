package com.code4a.jlibrarydemo.home.frag.home.three;

import com.code4a.jlibrary.base.presenter.BasePresenter;

/**
 * Created by code4a on 2017/1/12.
 */

public abstract class ThreePresenter extends BasePresenter<ThreeView> {

    protected ThreePresenter(ThreeView view) {
        super(view);
    }

    public abstract void start();

    public abstract void getVideos(int page, int size);

    public abstract void cancel();
}
