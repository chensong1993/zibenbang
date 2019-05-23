package com.zhiyi.chinaipo.ui.activity.misc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
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
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.ui.activity.MainActivity;
import com.zhiyi.chinaipo.ui.widget.WebLayout;
import com.zhiyi.chinaipo.util.ActivityCollector;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.Set;

import butterknife.BindView;
import me.yokeyword.fragmentation.SwipeBackLayout;

public class WebActivity extends SimpleActivity {

    protected AgentWeb mAgentWeb;
    @BindView(R.id.container)
    LinearLayout mLinearLayout;
    private String mWebTitle;
    private String mWebURL, PushURL = null;
    private String URL;
    @BindView(R.id.img_back)
    ImageView mImgbcak;
    @BindView(R.id.tv_title)
    TextView mTvActivityTitle;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initEventAndData() {
        initData();
    }

    private void initData() {
        siwpeBack();
        //获取推送的参数
        if (getIntent().getExtras() != null) {
            Bundle bun = getIntent().getExtras();

            Set<String> keySet = bun.keySet();
            LogUtil.i("pushSize", keySet.size() + "");
            for (String key : keySet) {
                String value = bun.getString(key);
                if (value != null) {
                    PushURL = value;
                }
            }

        }
        mWebURL = getIntent().getStringExtra(Constants.GOTO_WEB_URL);
        mRlProgress.setVisibility(View.VISIBLE);
        mWebTitle = getIntent().getStringExtra(Constants.GOTO_WEB_TITLE);
        //  LogUtil.i("GOTO_WEB_URL", mWebURL);
        mTvActivityTitle.setText(mWebTitle);
        mImgbcak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                if (activity != null) {
                    finish();
                } else {
                    startActivity(new Intent(WebActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
        if (mWebURL != null) {
            URL = mWebURL;
        } else {
            URL = PushURL;
        }
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
                .go(URL);

        //mAgentWeb.getLoader().loadUrl(getUrl());


    }

    /* @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         //NavigationBar.getNavigation(this);
         mWebTitle = getIntent().getStringExtra(Constants.GOTO_WEB_TITLE);
         mWebURL = getIntent().getStringExtra(Constants.GOTO_WEB_URL);
         LogUtil.i("GOTO_WEB_URL", mWebURL);
         setContentView(R.layout.activity_web);
         mLinearLayout =  this.findViewById(R.id.container);

         mTvActivityTitle =  this.findViewById(R.id.tv_title);
         mImgbcak =  this.findViewById(R.id.img_back);

         mImgbcak.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
         long p = System.currentTimeMillis();

         mAgentWeb = AgentWeb.with(this)//
                 .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                 .useDefaultIndicator()//
                 .setIndicatorColor(R.color.blue)
 //                .setIndicatorColorWithHeight(-1, 3)
                 .setReceivedTitleCallback(mCallback)
                 .setWebChromeClient(mWebChromeClient)
                 .setWebViewClient(mWebViewClient)
                 .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                 .setSecurityType(AgentWeb.SecurityType.strict)
                 .setWebLayout(new WebLayout(this))
                 .openParallelDownload()//打开并行下载 , 默认串行下载
                 .setNotifyIcon(R.drawable.ic_file_download_black_24dp) //下载图标
                 .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                 .interceptUnkownScheme() //拦截找不到相关页面的Scheme
                 .createAgentWeb()//
                 .ready()
                 .go(mWebURL);

         //mAgentWeb.getLoader().loadUrl(getUrl());

         long n = System.currentTimeMillis();
         LogUtil.i("Info", "init used time:" + (n - p));

     }
 */
    public void siwpeBack() {
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {
            @Override
            public void onDragStateChange(int state) {
                // Drag state
                if (state == 3) {
                    MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                    if (activity != null) {
                        finish();
                    } else {
                        startActivity(new Intent(WebActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                // 触摸的边缘flag
            }

            @Override
            public void onDragScrolled(float scrollPercent) {
                // 滑动百分比

            }
        });
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mRlProgress.setVisibility(View.GONE);
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

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
        if (activity != null) {
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
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
        intent.setClass(builder.mContext, WebActivity.class);
        intent.putExtra(Constants.GOTO_WEB_TITLE, builder.mTitle);
        intent.putExtra(Constants.GOTO_WEB_URL, builder.mWebUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }
}
