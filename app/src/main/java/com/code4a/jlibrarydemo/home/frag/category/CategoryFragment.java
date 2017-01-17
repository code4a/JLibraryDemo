package com.code4a.jlibrarydemo.home.frag.category;

import android.os.Bundle;
import android.view.View;

import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.frag.HomeBaseFragment;

public class CategoryFragment extends HomeBaseFragment {

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(R.string.catagory);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }


}



