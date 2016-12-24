package com.code4a.jlibrary.base.presenter;


import com.code4a.jlibrary.base.presenter.view.BasePresenterView;

/**
 * Created by code4a on 2016/8/18.
 */

public abstract class BasePresenter<T extends BasePresenterView> {

    protected T view;

    protected BasePresenter(T view){
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void release(){
        view = null;
    }
}
