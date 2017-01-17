package com.code4a.jlibrarydemo.home.frag;

import android.support.annotation.StringRes;
import android.widget.TextView;

import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.R;

import butterknife.BindView;


public abstract class HomeBaseFragment extends BaseFragment {

    @BindView(R.id.toolbar_title)
    TextView title;

    protected void setTitle(@StringRes int strId){
        if(title != null){
            title.setText(strId);
        }
    }
}
