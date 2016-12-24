package com.code4a.jlibrarydemo.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.code4a.jlibrary.base.AppActivity;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.MainActivity;
import com.code4a.jlibrarydemo.R;
import com.dd.CircularProgressButton;

/**
 * Created by code4a on 2016/12/23.
 */

public class LoginActivity extends AppActivity implements LoginView {


    private EditText username;
    private EditText password;
    private LoginPresenter presenter;
    private CircularProgressButton circularButton;

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
        username = $(R.id.username);
        password = $(R.id.password);
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
        openActivity(MainActivity.class);
        resetButtonState();
    }
}
