package com.code4a.jlibrary.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code4a.jlibrary.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by renlei on 2016/5/23.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    private Unbinder unbinder;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }

    protected <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected int getColor(@ColorRes int color) {
        return getResources().getColor(color);
    }

    protected void openActivity(Class<?> clazz, Bundle pBundle) {
        Intent startIntent = new Intent(mActivity, clazz);
        if (pBundle != null) {
            startIntent.putExtras(pBundle);
        }
        mActivity.startActivity(startIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        LogUtil.i("this.class = " + this.getClass());
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
