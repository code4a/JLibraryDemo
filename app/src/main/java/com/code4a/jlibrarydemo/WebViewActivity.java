package com.code4a.jlibrarydemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.code4a.jlibrary.base.AppActivity;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrarydemo.utils.Constants;

import butterknife.BindView;

public class WebViewActivity extends AppActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView titleTv;
    @BindView(R.id.webview)
    WebView mWebView;

    String mLoadUrl;
    String mTitle;

    public void setmTitle(String tit) {
        if (titleTv != null) {
            titleTv.setText(tit);
        }
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void handleIntent(Intent intent) {
        mTitle = intent.getStringExtra(Constants.BUNDLE_WV_TITLE);
        setmTitle(mTitle);
        mLoadUrl = intent.getStringExtra(Constants.BUNDLE_WV_URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.colorToolBar);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.loadUrl(mLoadUrl);
        //加载数据
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    WebViewActivity.this.setmTitle(mTitle);
                } else {
                    WebViewActivity.this.setmTitle("加载中.......");

                }
            }
        });
        //这个是当网页上的连接被点击的时候
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

//    // goBack()表示返回webView的上一页面
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e(TAG, " ------- onKeyDown ----- ");
//        if (mWebView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
//            Log.e(TAG, " ------- onKeyDown ----- can go back ++++ ");
//            mWebView.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, " ------- onBackPressed ----- ");
        if (mWebView.canGoBack()) {
            Log.i(TAG, " ------- onBackPressed ----- can go back ++++ ");
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    public void onClick(View v) {

    }
}
