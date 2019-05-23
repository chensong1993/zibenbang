package com.zhiyi.chinaipo.ui.activity.misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
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
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.ui.activity.MainActivity;
import com.zhiyi.chinaipo.ui.widget.ShareBottomDialog;
import com.zhiyi.chinaipo.ui.widget.WebLayout;
import com.zhiyi.chinaipo.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WebAdActivity extends SimpleActivity {

    protected AgentWeb mAgentWeb;
    @BindView(R.id.container)
    LinearLayout mLinearLayout;
    private String mWebTitle;
    private String mWebURL;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.img_share)
    ImageView mImgShare;
    @BindView(R.id.tv_title)
    TextView mTvActivityTitle;
    ShareBottomDialog dialog;


    @Override
    protected int getLayout() {
        return R.layout.activity_ad_web;
    }

    @Override
    protected void initEventAndData() {
        initData();
    }

    private void initData() {
        setSwipeBackEnable(false);
        mWebTitle = getIntent().getStringExtra(Constants.GOTO_WEB_TITLE);
        mWebURL = getIntent().getStringExtra(Constants.GOTO_WEB_URL);
        //  LogUtil.i("GOTO_WEB_URL", mWebURL);
        mTvActivityTitle.setText(mWebTitle);
        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator(ContextCompat.getColor(this, R.color.blue), 2)
                //               .setIndicatorColorWithHeight(-1, 3)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()//
                .ready()
                .go(mWebURL);


    }

    @OnClick(R.id.rl_back)
    void RlFinish() {
        startActivity(new Intent(WebAdActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        startActivity(new Intent(WebAdActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.img_share)
    void IShare() {
        dialog = ShareBottomDialog.newInstance(mWebTitle, mWebURL, -1,2);
        dialog.show(getSupportFragmentManager());
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtil.i("Info", "result:" + requestCode + " result:" + resultCode);
        //   mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }


    public static class Builder {

        private String mTitle;
        private String mWebUrl;
        private Context mContext;
        private Activity mActivity;

        public Builder() {

        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setUrl(String accessUrl) {
            this.mWebUrl = accessUrl;
            return this;
        }
    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, WebAdActivity.class);
        intent.putExtra(Constants.GOTO_WEB_TITLE, builder.mTitle);
        intent.putExtra(Constants.GOTO_WEB_URL, builder.mWebUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }
}
