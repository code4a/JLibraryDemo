package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import com.code4a.jlibrary.base.presenter.view.BasePresenterView;
import com.code4a.jlibrarydemo.data.GirlsBean;

import java.util.List;

/**
 * Created by code4a on 2017/1/19.
 */

public interface GirlsView extends BasePresenterView {

    void refresh(List<GirlsBean.ResultsEntity> datas);

    void load(List<GirlsBean.ResultsEntity> datas);

    void showError();

    void showNormal();
}
