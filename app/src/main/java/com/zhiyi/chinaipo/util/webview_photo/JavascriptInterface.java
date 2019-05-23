package com.zhiyi.chinaipo.util.webview_photo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zhiyi.chinaipo.ui.activity.news.NewsPhotoActivity;

/**
 * @author chensong
 * @date 2018/10/19 14:54
 */
public class JavascriptInterface {
    private Context context;
    String [] imageUrls;
    public JavascriptInterface(Context context) {
        this.context = context;
    }

    public JavascriptInterface(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent();
        intent.putExtra("imageUrls", imageUrls);
        intent.putExtra("curImageUrl", img);
        intent.setClass(context, NewsPhotoActivity.class);
        context.startActivity(intent);
        for (int i = 0; i < imageUrls.length; i++) {
            Log.e("图片地址"+i,imageUrls[i].toString());
        }
    }

}
