package com.code4a.jlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.githang.statusbar.StatusBarCompat;

/**
 * Created by renlei on 2016/5/23.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    //布局文件ID
    protected abstract int getContentViewId();

    //布局中Fragment的ID
    protected abstract int getFragmentContentId();

    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void setStatusBarColor(@ColorRes int color){
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(color));
    }

    /**
     * 可将颜色设置为透明，背景为图片时，可平铺到状态栏
     * @param color 颜色
     * @param fitSystemWindows 是否预留出系统 View 的空间
     */
    public void setStatusBarColor(int color, boolean fitSystemWindows){
        Window window = this.getWindow();
        if (/*Build.VERSION.SDK_INT < Build.VERSION_CODES.M && */Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置透明状态栏,使 ContentView 内容覆盖状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        StatusBarCompat.setFitsSystemWindows(window, fitSystemWindows);
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void openActivity(Class<?> clazz) {
        openActivity(clazz, null);
    }

    protected void openActivity(Class<?> clazz, Bundle pBundle) {
        Intent startIntent = new Intent(this, clazz);
        if (pBundle != null) {
            startIntent.putExtras(pBundle);
        }
        startActivity(startIntent);
    }

    /**
     * Shared Elements Transition
     * @param activity 当前界面
     * @param clazz 要启动的Activity
     * @param pBundle 传递的参数
     * @param sharedElements 共享元素
     */
    protected void openActivityMakeTransition(Activity activity, Class<?> clazz, Bundle pBundle, Pair<View, String>... sharedElements) {
        Intent startIntent = new Intent(activity, clazz);
        if (pBundle != null) {
            startIntent.putExtras(pBundle);
        }
        ActivityCompat.startActivity(activity, startIntent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sharedElements).toBundle());
    }
}
