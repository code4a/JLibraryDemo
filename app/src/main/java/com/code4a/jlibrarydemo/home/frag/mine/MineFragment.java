package com.code4a.jlibrarydemo.home.frag.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.code4a.jlibrary.loading.LoadingView;
import com.code4a.jlibrary.tasks.BackgroundWork;
import com.code4a.jlibrary.tasks.Completion;
import com.code4a.jlibrary.tasks.Tasks;
import com.code4a.jlibrary.utils.DataCleanUtil;
import com.code4a.jlibrary.utils.ToastUtil;
import com.code4a.jlibrarydemo.pay.PayActivity;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.webview.WebViewActivity;
import com.code4a.jlibrarydemo.home.frag.HomeBaseFragment;
import com.code4a.jlibrarydemo.utils.Constants;

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
    @BindView(R.id.cache_size_tv)
    TextView cacheSizeTv;
    TextView aboutTv;
    ImageView feedBackIv;
    ImageView userIv;
    ImageView aboutIv;
    @BindView(R.id.gear_loadingview)
    LoadingView mLoadingView;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle(R.string.mine);

        initViewData();
        getCacheSize();
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
        aboutTv = $(about, R.id.feedback_tv);
        aboutTv.setText(R.string.about_us);

        feedBackIv = $(feedBack, R.id.feedback_icon);
        feedBackIv.setImageResource(R.mipmap.mine_feedback);
        userIv = $(user, R.id.feedback_icon);
        userIv.setImageResource(R.mipmap.mine_user);
        aboutIv = $(about, R.id.feedback_icon);
        aboutIv.setImageResource(R.mipmap.mine_about);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                break;
            case R.id.user:
                openActivity(PayActivity.class);
                break;
            case R.id.cache:
                cleanCache();
                break;
            case R.id.about:
                gotoAboutMeWebView();
                break;
        }
//        ToastUtil.showShort(getHoldingActivity(), "用户点击了");
    }

    private void gotoAboutMeWebView() {
        Bundle pBundle = new Bundle();
        pBundle.putString(Constants.BUNDLE_WV_TITLE, getString(R.string.about_us));
        pBundle.putString(Constants.BUNDLE_WV_URL, Constants.ABOUT_ME);
        openActivity(WebViewActivity.class, pBundle);
    }

    private void getCacheSize(){
        showLoadingView();
        Tasks.executeInBackground(getHoldingActivity(), new BackgroundWork<String>() {
            @Override
            public String doInBackground() throws Exception {
                return DataCleanUtil.getCacheSize(getHoldingActivity());
            }
        }, new Completion<String>() {
            @Override
            public void onSuccess(Context context, String result) {
                if(!TextUtils.isEmpty(result)){
                    cacheSizeTv.setText(result);
                    hideLoadingView();
                }
            }

            @Override
            public void onError(Context context, Exception e) {
                cacheSizeTv.setText("0MB");
                hideLoadingView();
            }
        });
    }

    private void cleanCache() {
        showLoadingView();
        Tasks.executeInBackground(getHoldingActivity(), new BackgroundWork<String>() {
            @Override
            public String doInBackground() throws Exception {
                DataCleanUtil.cleanApplicationData(getHoldingActivity());
                return null;
            }
        }, new Completion<String>() {
            @Override
            public void onSuccess(Context context, String result) {
                ToastUtil.showShort(getHoldingActivity(), "数据清除完成！");
                cacheSizeTv.setText("0MB");
                hideLoadingView();
            }

            @Override
            public void onError(Context context, Exception e) {
                cacheSizeTv.setText("0MB");
                hideLoadingView();
            }
        });
    }

    public void showLoadingView() {
        mLoadingView.setVisibility(View.VISIBLE);
        cacheSizeTv.setVisibility(View.GONE);
    }

    public void hideLoadingView() {
        mLoadingView.setVisibility(View.GONE);
        cacheSizeTv.setVisibility(View.VISIBLE);
    }
}
