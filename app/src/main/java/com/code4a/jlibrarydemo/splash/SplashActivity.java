package com.code4a.jlibrarydemo.splash;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.Pair;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(Color.TRANSPARENT, false);
    }

    public void openActivityMakeTransition(Class<?> openClazz, Pair<View, String>... sharedElements){
        openActivityMakeTransition(openClazz, null, sharedElements);
    }

    public void openActivityMakeTransition(Class<?> openClazz, Bundle pBundle, Pair<View, String>... sharedElements){
        openActivityMakeTransition(this, openClazz, pBundle, sharedElements);
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
