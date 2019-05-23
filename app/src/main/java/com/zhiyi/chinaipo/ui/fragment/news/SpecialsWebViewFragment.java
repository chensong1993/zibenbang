package com.zhiyi.chinaipo.ui.fragment.news;


import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.SimpleFragment;
import com.zhiyi.chinaipo.ui.widget.WebLayout;
import com.zhiyi.chinaipo.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 专题界面
 */
public class SpecialsWebViewFragment extends SimpleFragment {

    @BindView(R.id.ll_special)
    LinearLayout mLinearLayout;
    AgentWeb mAgentWeb;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    String url;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    int netType;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview_zhuanti;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        url = "http://3g.chinaipo.com/zhuanti/";
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()
//                .setIndicatorColorWithHeight(-1, 3)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(getActivity()))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()//
                .ready()
                .go(url);

        if ("http://3g.chinaipo.com/zhuanti/".equals(url)) {
            mRlBack.setVisibility(View.GONE);
        } else {
            mRlBack.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.img_back)
    void imgBack(){
        mAgentWeb.back();
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            LogUtil.i("Info","progress:"+newProgress);
        }
    };

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
