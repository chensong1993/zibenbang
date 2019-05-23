package com.zhiyi.chinaipo.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.polok.localify.LocalifyClient;
import com.github.polok.localify.module.LocalifyModule;
import com.kennyc.view.MultiStateView;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.app.Navigator;

import java.util.HashMap;


import butterknife.BindView;
import nucleus5.presenter.Presenter;

import static com.kennyc.view.MultiStateView.VIEW_STATE_CONTENT;
import static com.kennyc.view.MultiStateView.VIEW_STATE_LOADING;


public abstract class BaseWebViewActivity<PresenterType extends Presenter> extends BaseStatefulActivity<PresenterType> {

   /* @BindView(R.id.wv_content)
    public WebView contentView;*/

    protected WebSettings settings;

    protected final static String PLATFORM = "Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  settings = contentView.getSettings();

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    protected static class WebAppClient extends WebViewClient {
        private Context context;

        private Navigator navigator;

        private MultiStateView multiStateView;

        private WebView contentView;

        public WebAppClient(Context context,
                            Navigator navigator,
                            MultiStateView multiStateView,
                            WebView contentView) {
            this.context = context;
            this.navigator = navigator;
            this.multiStateView = multiStateView;
            this.contentView = contentView;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getHost().contains(Constants.CHINAIPO_API_HOST)) {
                HashMap<String, String> segments = new HashMap<>();
                String key = null;
                for (String segment : uri.getPathSegments()) {
                    if (key == null) {
                        key = segment;
                    } else {
                        segments.put(key, segment);
                        key = null;
                    }
                }
//                if (segments.size() > 0) {
//                    if (segments.containsKey(Constants.CHINAIPO_NEWS_PATH) && !TextUtils.isEmpty(segments.get(CHINAIPO_NEWS_PATH))) {
//                        url = String.format("%s%s?id=%s", DEEP_LINK_PREFIX, CHINAIPO_NEWS_PATH, segments.get(CHINAIPO_NEWS_PATH));
//                    } else if (segments.containsKey(CHINAIPO_STOCK_PATH) && !TextUtils.isEmpty(segments.get(CHINAIPO_STOCK_PATH))) {
//                        url = String.format("%s%s?id=%s", DEEP_LINK_PREFIX, PHPHUB_USER_PATH, segments.get(CHINAIPO_STOCK_PATH));
//                    }
//                }
            }

//            if (url.contains(DEEP_LINK_PREFIX)) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(url));
//                if (intent.resolveActivity(context.getPackageManager()) != null) {
//                    context.startActivity(intent);
//                } else {
//                    context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
//                }
//            } else {
//                navigator.navigateToWebView(context, url);
//            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (multiStateView != null) {
                multiStateView.setViewState(VIEW_STATE_LOADING);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (multiStateView != null) {
                multiStateView.setViewState(VIEW_STATE_CONTENT);
            }
        }

    }

    protected static class WebAppInterface {
        private Context context;

        private Navigator navigator;

        public WebAppInterface(Context context, Navigator navigator) {
            this.context = context;
            this.navigator = navigator;
        }

//        @JavascriptInterface
//        public void openImage(String url) {
//            navigator.navigateToGallery(context, url);
//        }
    }
}
