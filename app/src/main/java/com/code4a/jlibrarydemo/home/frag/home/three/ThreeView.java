package com.code4a.jlibrarydemo.home.frag.home.three;

import com.code4a.jlibrary.base.presenter.view.BasePresenterView;
import com.code4a.jlibrarydemo.data.GirlsBean;

import java.util.List;

/**
 * Created by code4a on 2017/1/19.
 */

public interface ThreeView extends BasePresenterView {

    void load(List<GirlsBean.ResultsEntity> datas);

    void showLoadingView();

    void hideLoadingView();

    void showError();

    void showNormal();
}
