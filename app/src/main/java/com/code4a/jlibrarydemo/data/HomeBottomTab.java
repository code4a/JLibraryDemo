package com.code4a.jlibrarydemo.data;

/**
 * Created by code4a on 2017/1/16.
 */

public class HomeBottomTab {

    private  int title;
    private  int icon;
    private Class fragment;

    public HomeBottomTab(Class fragment,int title, int icon) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
