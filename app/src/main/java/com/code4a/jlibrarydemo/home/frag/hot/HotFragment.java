package com.code4a.jlibrarydemo.home.frag.hot;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code4a.jlibrary.loading.LoadingView;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.home.frag.HomeBaseFragment;
import com.dalong.library.listener.OnItemSelectedListener;
import com.dalong.library.view.LoopRotarySwitchView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.BindViews;


public class HotFragment extends HomeBaseFragment implements HotView {

    @BindView(R.id.mLoopRotarySwitchView)
    LoopRotarySwitchView mLoopRotarySwitchView;
    @BindView(R.id.current_hot_name)
    TextView hotName;

    @BindView(R.id.collision_loadingview)
    LoadingView mLoadView;

    @BindViews({R.id.hot_movie1, R.id.hot_movie2, R.id.hot_movie3, R.id.hot_movie4, R.id.hot_movie5, R.id.hot_movie6})
    List<ImageView> imageViews;

    private HotPresenter mHotPresenter;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(R.string.hot_groom);

        mHotPresenter = new HotPresenterImpl(this);

        mLoopRotarySwitchView.setR(650);
        mLoopRotarySwitchView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void selected(int item, View view) {
                hotName.setText("当前第" + item + "页");
            }
        });

        loadDefaultImg();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHotPresenter.start(new Random().nextInt(20), imageViews.size());
    }

    @Override
    public void onPause() {
        super.onPause();
        mHotPresenter.cancel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    public void showLoadingView() {
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mLoadView.setVisibility(View.GONE);
    }

    @Override
    public void showGirl(List<GirlsBean.ResultsEntity> datas) {
        for (int i = 0; i < imageViews.size(); i++) {
            Glide.with(getActivity())
                    .load(datas.get(i).getUrl())
                    .into(imageViews.get(i));
        }
    }

    @Override
    public void showGirl() {
        loadDefaultImg();
    }

    private void loadDefaultImg() {
        for (int i = 0; i < imageViews.size(); i++) {
            Glide.with(getActivity())
                    .load(R.drawable.default_error)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageViews.get(i));
        }
    }
}
