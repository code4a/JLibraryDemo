package com.code4a.jlibrarydemo.login;

public class LoginPresenterImpl extends LoginPresenter implements LoginInteractor.OnLoginFinishedListener {

    private LoginInteractor loginInteractor;

    protected LoginPresenterImpl(LoginView view) {
        super(view);
        this.loginInteractor = new LoginInteractorImpl();
    }


    @Override
    public void validateCredentials(String username, String password) {
        if (getView() != null) {
            getView().setIndeterminateProgressMode(true);
            getView().setCircleButtonProgress(20);
        }

        loginInteractor.login(username, password, this);
    }

    @Override
    public void onDestroy() {
        release();
    }

    @Override
    public void onUsernameError() {
        if (getView() != null) {
            getView().setCircleButtonProgress(-1);
            getView().setUsernameError();
        }
    }

    @Override
    public void onPasswordError() {
        if (getView() != null) {
            getView().setCircleButtonProgress(-1);
            getView().setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if (getView() != null) {
            getView().setCircleButtonProgress(100);
            getView().navigateToHome();
        }
    }
}
