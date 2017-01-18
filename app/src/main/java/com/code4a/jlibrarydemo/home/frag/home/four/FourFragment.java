package com.code4a.jlibrarydemo.home.frag.home.four;

import android.os.Bundle;
import android.view.View;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;


public class FourFragment extends BaseFragment {

    public static FourFragment getInstance() {
        FourFragment fourFragment = new FourFragment();
        return fourFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
