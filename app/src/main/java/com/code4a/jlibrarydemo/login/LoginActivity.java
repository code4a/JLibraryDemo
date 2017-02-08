package com.code4a.jlibrarydemo.login;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
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
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    private AnimationDrawable anim;
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
        RelativeLayout container = $(R.id.id_login_layout);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(2000);
        circularButton = $(R.id.login_btn);
        circularButton.setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        animationView.setProgress(0f);
        animationView.playAnimation();
    }

    @Override
    public void onStop() {
        super.onStop();
        animationView.cancelAnimation();
    }

    // Starting animation:- start the animation on onResume.
    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    // Stopping animation:- stop the animation on onPause.
    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
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
