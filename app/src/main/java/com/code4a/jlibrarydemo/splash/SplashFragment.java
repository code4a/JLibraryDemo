package com.code4a.jlibrarydemo.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code4a.jlibrary.base.ActivityManager;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.login.LoginActivity;

import butterknife.BindView;

/**
 * Created by code4a on 2016/9/27.
 */

public class SplashFragment extends BaseFragment implements SplashView {

    @BindView(R.id.splash)
    ImageView mSplashImg;

    private ScaleAnimation scaleAnimation;


    private SplashPresenter mPresenter;

    public static SplashFragment getInstance() {
        SplashFragment splashFragment = new SplashFragment();
        return splashFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        mSplashImg = $(view, R.id.splash);
        mPresenter = new SplashPresenterImpl(SplashFragment.this);

        initAnim();
    }

    private void initAnim() {
        scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2500);
        mSplashImg.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ActivityManager.getInstance().finishActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public void showGirl(String girlUrl) {
        Glide.with(getActivity())
                .load(girlUrl)
                .animate(scaleAnimation)
                .into(mSplashImg);
    }

    @Override
    public void showGirl() {
        Glide.with(getActivity())
                .load(R.drawable.login_bg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(scaleAnimation)
                .into(mSplashImg);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

}
