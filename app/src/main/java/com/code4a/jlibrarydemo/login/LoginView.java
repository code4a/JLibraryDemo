package com.code4a.jlibrarydemo.login;

import com.code4a.jlibrary.base.presenter.view.BasePresenterView;

public interface LoginView extends BasePresenterView {

    void setIndeterminateProgressMode(boolean indeterminateProgressMode);

    void setCircleButtonProgress(int progress);

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}
