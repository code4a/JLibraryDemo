package com.code4a.jlibrarydemo.home.frag.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code4a.jlibrary.utils.ToastUtil;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.frag.HomeBaseFragment;

import butterknife.BindView;


/**
 * Created by Ivan on 15/9/22.
 */
public class MineFragment extends HomeBaseFragment implements View.OnClickListener{

    @BindView(R.id.feedback)
    View feedBack;

    @BindView(R.id.user)
    View user;

    @BindView(R.id.cache)
    View cache;

    @BindView(R.id.about)
    View about;

    TextView feedBackTv;
    TextView userTv;
    TextView cacheTv;
    TextView aboutTv;
    ImageView feedBackIv;
    ImageView userIv;
    ImageView cacheIv;
    ImageView aboutIv;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(R.string.mine);

        initViewData();
    }

    private void initViewData() {
        feedBack.setOnClickListener(this);
        user.setOnClickListener(this);
        cache.setOnClickListener(this);
        about.setOnClickListener(this);

        feedBackTv = $(feedBack, R.id.feedback_tv);
        feedBackTv.setText(R.string.feedback);
        userTv = $(user, R.id.feedback_tv);
        userTv.setText(R.string.user);
        cacheTv = $(cache, R.id.feedback_tv);
        cacheTv.setText(R.string.cache);
        aboutTv = $(about, R.id.feedback_tv);
        aboutTv.setText(R.string.about_us);

        feedBackIv = $(feedBack, R.id.feedback_icon);
        feedBackIv.setImageResource(R.mipmap.mine_feedback);
        userIv = $(user, R.id.feedback_icon);
        userIv.setImageResource(R.mipmap.mine_user);
        cacheIv = $(cache, R.id.feedback_icon);
        cacheIv.setImageResource(R.mipmap.mine_cache);
        aboutIv = $(about, R.id.feedback_icon);
        aboutIv.setImageResource(R.mipmap.mine_about);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View v) {
        ToastUtil.showShort(getHoldingActivity(), "用户点击了");
    }
}
