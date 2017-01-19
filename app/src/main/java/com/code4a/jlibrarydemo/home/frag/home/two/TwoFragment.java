package com.code4a.jlibrarydemo.home.frag.home.two;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrary.utils.DensityUtil;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.home.frag.home.two.girls.GirlsFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;


public class TwoFragment extends BaseFragment {

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private String[] names = {"福利", "DONUT", "FROYO", "GINGERBREAD", "HONEYCOMB", "ICE CREAM SANDWICH", "JELLY BEAN", "KITKAT"};
    private ScrollIndicatorView scrollIndicatorView;
    private int unSelectTextColor;

    private boolean isPinnedTab = false;

    public static TwoFragment getInstance(){
        TwoFragment twoFragment = new TwoFragment();
        return twoFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ViewPager viewPager = $(view, R.id.home_two_viewPager);
        scrollIndicatorView = $(view, R.id.home_two_indicator);

        scrollIndicatorView.setBackgroundColor(getResources().getColor(R.color.colorToolBar));

        // 设置是否自动布局
        scrollIndicatorView.setSplitAuto(false);

        scrollIndicatorView.setPinnedTabView(isPinnedTab);
        if(isPinnedTab){
            // 设置固定tab的shadow，这里不设置的话会使用默认的shadow绘制
            scrollIndicatorView.setPinnedShadow(R.mipmap.tabshadow, DensityUtil.dp2px(getHoldingActivity(), 4));
            scrollIndicatorView.setPinnedTabBgColor(Color.RED);
        }

        scrollIndicatorView.setScrollBar(new DrawableBar(getHoldingActivity(), R.drawable.round_border_white_selector, ScrollBar.Gravity.CENTENT_BACKGROUND) {
            @Override
            public int getHeight(int tabHeight) {
                return tabHeight - DensityUtil.dp2px(getHoldingActivity(), 12);
            }

            @Override
            public int getWidth(int tabWidth) {
                return tabWidth - DensityUtil.dp2px(getHoldingActivity(), 12);
            }
        });

        unSelectTextColor = Color.WHITE;
        // 设置滚动监听
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, unSelectTextColor));

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        inflate = LayoutInflater.from(getHoldingActivity());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_two;
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.home_two_tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position % names.length]);
            int padding = DensityUtil.dp2px(getHoldingActivity(), 10);
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public BaseFragment getFragmentForPage(int position) {
//            MoreFragment fragment = new MoreFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt(MoreFragment.INTENT_INT_INDEX, position);
//            fragment.setArguments(bundle);
            return GirlsFragment.getInstance();
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

    }

}
