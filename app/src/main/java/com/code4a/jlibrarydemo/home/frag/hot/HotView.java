package com.code4a.jlibrarydemo.home.frag.hot;

import com.code4a.jlibrary.base.presenter.view.BasePresenterView;
import com.code4a.jlibrarydemo.data.GirlsBean;

import java.util.List;

/**
 * Created by code4a on 2017/1/12.
 */

public interface HotView extends BasePresenterView {

    void showGirl(List<GirlsBean.ResultsEntity> datas);

    void showGirl();
}
