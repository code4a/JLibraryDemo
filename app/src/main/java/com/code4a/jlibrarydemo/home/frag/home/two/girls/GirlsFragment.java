package com.code4a.jlibrarydemo.home.frag.home.two.girls;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrary.utils.LogUtil;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.GirlsBean;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by oracleen on 2016/6/21.
 */
public class GirlsFragment extends BaseFragment implements GirlsView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    private static final String TAG = "GirlsFragment";

    @BindView(R.id.girls_recycler_view)
    EasyRecyclerView mGirlsRecyclerView;
    @BindView(R.id.network_error_layout)
    ViewStub mNetworkErrorLayout;

    private View networkErrorView;

    private ArrayList<GirlsBean.ResultsEntity> data;
    private GirlsAdapter mAdapter;

    private GirlsPresenter mPresenter;
    private int page = 20;
    private int size = 20;

//    private Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two_tab;
    }

    public static GirlsFragment getInstance() {
        GirlsFragment mainFragment = new GirlsFragment();
        return mainFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
//        unbinder = ButterKnife.bind(this, view);

        mPresenter = new GirlsPresenterImpl(this);

        initRecyclerView();

        //初始化数据
        mPresenter.start();
    }

    private void initRecyclerView() {
        data = new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGirlsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new GirlsAdapter(getContext());

        mGirlsRecyclerView.setAdapterWithProgress(mAdapter);

        mAdapter.setMore(R.layout.load_more_layout, this);
        mAdapter.setNoMore(R.layout.no_more_layout);
        mAdapter.setError(R.layout.network_error);
//        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(mActivity, GirlActivity.class);
//                intent.putParcelableArrayListExtra("girls", data);
//                intent.putExtra("current", position);
//                startActivity(intent);
//            }
//        });

        mAdapter.setOnMyItemClickListener(new GirlsAdapter.OnMyItemClickListener() {
            @Override
            public void onItemClick(int position, BaseViewHolder holder) {
                // TODO
//                Intent intent = new Intent(mActivity, GirlActivity.class);
//                intent.putParcelableArrayListExtra("girls", data);
//                intent.putExtra("current", position);
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(holder.itemView, holder.itemView.getWidth() / 2, holder.itemView.getHeight() / 2, 0, 0);
//                startActivity(intent, options.toBundle());
            }
        });

        mGirlsRecyclerView.setRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.getGirls(1, size, true);
        page = 1;
    }

    @Override
    public void onLoadMore() {
        if (data.size() % 20 == 0) {
            LogUtil.d(TAG, "onloadmore");
            page++;
            mPresenter.getGirls(page, size, false);
        }
    }

    @Override
    public void refresh(List<GirlsBean.ResultsEntity> datas) {
        data.clear();
        data.addAll(datas);
        mAdapter.clear();
        mAdapter.addAll(datas);
    }

    @Override
    public void load(List<GirlsBean.ResultsEntity> datas) {
        data.addAll(datas);
        mAdapter.addAll(datas);
    }

    @Override
    public void showError() {
        mGirlsRecyclerView.showError();

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }

        networkErrorView = mNetworkErrorLayout.inflate();
    }

    public void showNormal() {
        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.cancel();
//        unbinder.unbind();
    }
}