package com.zhiyi.chinaipo.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {
    @Inject
    public Navigator() {}


    public void navigateToWebView(@NonNull Context context, String webUrl) {
        WebActivity.launch(new WebActivity.Builder()
                .setContext(context)
                .setUrl(webUrl)
        );
    }
}