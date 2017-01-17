package com.code4a.jlibrarydemo.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.code4a.jlibrary.base.AppActivity;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.HomeActivity;
import com.dd.CircularProgressButton;

import butterknife.BindView;

/**
 * Created by code4a on 2016/12/23.
 */

public class LoginActivity extends AppActivity implements LoginView {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    private CircularProgressButton circularButton;
    private LoginPresenter presenter;

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(android.R.color.transparent, false);
//        username = $(R.id.username);
//        password = $(R.id.password);
        circularButton = $(R.id.login_btn);
        circularButton.setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);
    }


    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                presenter.validateCredentials(username.getText().toString(), password.getText().toString());
                break;
        }
    }

    private void resetButtonState(){
        circularButton.setProgress(0);
    }

    @Override
    public void setIndeterminateProgressMode(boolean indeterminateProgressMode) {
        circularButton.setIndeterminateProgressMode(indeterminateProgressMode);
    }

    @Override
    public void setCircleButtonProgress(int progress) {
        circularButton.setProgress(progress);
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
        resetButtonState();
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
        resetButtonState();
    }

    @Override
    public void navigateToHome() {
        openActivity(HomeActivity.class);
        resetButtonState();
    }
}
