package com.code4a.jlibrarydemo.pay;

import com.code4a.jlibrary.base.presenter.view.BasePresenterView;
import com.code4a.jlibrarydemo.data.AlipayBean;

/**
 * Created by code4a on 2017/2/6.
 */

public interface PayView extends BasePresenterView {

    void showLoadingView();

    void hideLoadingView();

    void doNext(AlipayBean alipayBean);
}
