package com.code4a.jlibrarydemo.splash;

import android.view.View;

import com.code4a.jlibrary.base.AppActivity;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;

/**
 * Created by code4a on 2017/1/12.
 */

public class SplashActivity extends AppActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return SplashFragment.getInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.splash_fragment;
    }

    @Override
    public void onClick(View view) {

    }
}
