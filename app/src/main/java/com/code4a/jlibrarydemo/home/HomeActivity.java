package com.code4a.jlibrarydemo.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.code4a.jlibrary.base.BaseActivity;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.HomeBottomTab;
import com.code4a.jlibrarydemo.home.frag.category.CategoryFragment;
import com.code4a.jlibrarydemo.home.frag.hot.HotFragment;
import com.code4a.jlibrarydemo.home.frag.mine.MineFragment;
import com.code4a.jlibrarydemo.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private LayoutInflater mInflater;

    private FragmentTabHost mTabhost;

    private List<HomeBottomTab> mTabs = new ArrayList<>(4);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        setStatusBarColor(R.color.colorToolBar);
        initTab();
    }

    private void initTab() {
        HomeBottomTab tab_home = new HomeBottomTab(MineFragment.class, R.string.home, R.drawable.selector_icon_home);
        HomeBottomTab tab_hot = new HomeBottomTab(HotFragment.class, R.string.hot, R.drawable.selector_icon_hot);
        HomeBottomTab tab_category = new HomeBottomTab(CategoryFragment.class, R.string.catagory, R.drawable.selector_icon_category);
        HomeBottomTab tab_mine = new HomeBottomTab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine);

        mTabs.add(tab_home);
        mTabs.add(tab_hot);
        mTabs.add(tab_category);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabhost = $(android.R.id.tabhost);
        mTabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (HomeBottomTab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec, tab.getFragment(), null);
        }

        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == getString(R.string.home)) {
//                    refData();
                }
            }
        });

        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }

    private View buildIndicator(HomeBottomTab tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
