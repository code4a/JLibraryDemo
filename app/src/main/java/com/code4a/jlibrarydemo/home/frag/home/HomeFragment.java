package com.code4a.jlibrarydemo.home.frag.home;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.frag.home.four.FourFragment;
import com.code4a.jlibrarydemo.home.frag.home.one.OneFragment;
import com.code4a.jlibrarydemo.widget.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.vp)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;

    private FragmentPagerAdapter mAdapter;
    private List<BaseFragment> mList;
    private List<String> mDatas;
    private final int itemCount = 4;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mList = new ArrayList<>(itemCount);
        mList.add(OneFragment.getInstance());
        mList.add(FourFragment.getInstance());
        mList.add(FourFragment.getInstance());
        mList.add(FourFragment.getInstance());
        mDatas = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            mDatas.add("i=" + i);
        }

        mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public BaseFragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
}
