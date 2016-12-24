package com.code4a.jlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

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
