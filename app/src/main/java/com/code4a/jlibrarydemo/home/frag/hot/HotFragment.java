package com.code4a.jlibrarydemo.home.frag.hot;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.frag.HomeBaseFragment;
import com.dalong.library.listener.OnItemSelectedListener;
import com.dalong.library.view.LoopRotarySwitchView;

import butterknife.BindView;


public class HotFragment extends HomeBaseFragment {

    @BindView(R.id.mLoopRotarySwitchView)
    LoopRotarySwitchView mLoopRotarySwitchView;
    @BindView(R.id.current_hot_name)
    TextView hotName;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(R.string.hot_groom);
        mLoopRotarySwitchView.setR(700);
        mLoopRotarySwitchView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int item, View view) {
                hotName.setText("当前第" + item + "页");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }
}
