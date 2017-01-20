package com.code4a.jlibrarydemo.home.frag.home.three;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.AbsListView;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrary.loading.LoadingView;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.code4a.jlibrarydemo.home.frag.home.three.adapter.VideoRecyclerViewAdapter;
import com.code4a.jlibrarydemo.home.frag.home.three.items.BaseVideoItem;
import com.code4a.jlibrarydemo.home.frag.home.three.items.ItemFactory;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by code4a on 2017/1/20.
 */

public class ThreeFragment extends BaseFragment implements ThreeView {

    private static final String TAG = ThreeFragment.class.getSimpleName();

    private final ArrayList<BaseVideoItem> mList = new ArrayList<>();

    /**
     * Only the one (most visible) view should be active (and playing).
     * To calculate visibility of views we use {@link SingleListViewItemActiveCalculator}
     */
    private final ListItemsVisibilityCalculator mVideoVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.network_error_layout)
    ViewStub mNetworkErrorLayout;
    @BindView(R.id.waterbottle_loadingview)
    LoadingView mLoadingView;
    private View networkErrorView;
    private LinearLayoutManager mLayoutManager;
    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private ThreePresenter mThreePresenter;

    /**
     * ItemsPositionGetter is used by {@link ListItemsVisibilityCalculator} for getting information about
     * items position in the RecyclerView and LayoutManager
     */
    private ItemsPositionGetter mItemsPositionGetter;

    /**
     * Here we use {@link SingleVideoPlayerManager}, which means that only one video playback is possible.
     */
    private final VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {

        }
    });

    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

    public static ThreeFragment getInstance() {
        ThreeFragment threeFragment = new ThreeFragment();
        return threeFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        initItemData();
        mThreePresenter = new ThreePresenterImpl(this);
        networkErrorView = mNetworkErrorLayout.inflate();
        showNormal();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getHoldingActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(mVideoPlayerManager, getHoldingActivity(), mList);

        mRecyclerView.setAdapter(videoRecyclerViewAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()) {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mList.isEmpty()) {
                    mVideoVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);

    }

    @Override
    public void onResume() {
        super.onResume();
        mThreePresenter.start();
        if (!mList.isEmpty()) {
            // need to call this method from list view handler in order to have filled list
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    mVideoVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // we have to stop any playback in onStop
        mVideoPlayerManager.resetMediaPlayer();
        mThreePresenter.cancel();
    }

    private void initItemData() {
//        try {
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video1), "video1.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video2), "video2.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video3), "video3.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video4), "video4.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video5), "video5.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video6), "video6.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video7), "video7.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));

//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video1), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video2), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video3), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video4), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video5), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video6), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video7), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromAsset(getString(R.string.video8), "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));

//            String moviesDir = SDCardUtil.getSDCardPath() + "Movies/";
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video1), moviesDir + "video1.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video2), moviesDir + "video2.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video3), moviesDir + "video3.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video4), moviesDir + "video4.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video5), moviesDir + "video5.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video6), moviesDir + "video6.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video7), moviesDir + "video7.flv", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));
//            mList.add(ItemFactory.createItemFromUri(getString(R.string.video8), moviesDir + "video8.m4v", R.mipmap.daimajia, getHoldingActivity(), mVideoPlayerManager));

//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_three_recycler;
    }

    @Override
    public void load(List<GirlsBean.ResultsEntity> datas) {
        try {
            for (GirlsBean.ResultsEntity data : datas) {
                mList.add(ItemFactory.createItemFromUri(data.getDesc(), data.getUrl(), R.drawable.default_error, getHoldingActivity(), mVideoPlayerManager));
                videoRecyclerViewAdapter.updateData(mList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }
}