package com.zhiyi.chinaipo.ui.fragment;


import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebChromeClient;
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
import com.zhiyi.chinaipo.base.SimpleFragment;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.StatusBarUtil;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrandFragment extends SimpleFragment {

    @BindView(R.id.ll_brand)
    LinearLayout mLlWeb;
    AgentWeb mAgentWeb;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.fillStatusBarView)
    TextView mStatusBarView;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;


    public void visitUrl() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLlWeb, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(ContextCompat.getColor(getActivity(), R.color.blue), 2)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(Constants.CHINAIPO_BRAND);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_brand;
    }

    @Override
    protected void initEventAndData() {
      //  mRlProgress.setVisibility(View.VISIBLE);
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView,0);
        visitUrl();

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
     /*   @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            if (Constants.CHINAIPO_BRAND.equals(request)) {
                mImgBack.setVisibility(View.GONE);
            } else {
                mImgBack.setVisibility(View.VISIBLE);
                mImgBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mAgentWeb.back()) {
                            BrandFragment.this.getActivity().finish();
                        }
                    }
                });
            }
            LogUtil.i("Info", request);
            return super.shouldOverrideUrlLoading(view, request);

        }*/

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (Constants.CHINAIPO_BRAND.equals(url)) {
                mImgBack.setVisibility(View.GONE);
            } else {
                mImgBack.setVisibility(View.VISIBLE);
                mImgBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mAgentWeb.back()) {
                            BrandFragment.this.getActivity().finish();
                        }
                    }
                });
            }
            LogUtil.i("Info", url);
        }
    };


    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            LogUtil.i("Info","progress:"+newProgress);
//            if(newProgress==100){
//                mRlProgress.setVisibility(View.GONE);
//            }
        }
    };


}
