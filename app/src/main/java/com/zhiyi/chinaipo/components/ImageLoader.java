package com.zhiyi.chinaipo.components;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.ui.widget.GlideRoundTransform;
import com.zhiyi.chinaipo.util.LogUtil;

public class ImageLoader {


    public static void load(Context context, String url, ImageView iv) {
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).transform(new GlideRoundTransform(context, 5)).error(R.drawable.ic_placeholder).into(iv);
        } else {
            LogUtil.i("glide", "glide");
        }
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public static void load(Activity activity, String url, ImageView iv) {
//        if (!activity.isDestroyed()) {
//            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_placeholder).into(iv);
//        }
//    }

    public static void load(Fragment context, String url, ImageView iv) {
        if (context != null && context.getActivity() != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_placeholder).into(iv);
        }
    }

    public static void load(android.support.v4.app.Fragment context, String url, ImageView iv) {
        if (context != null && context.getActivity() != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_placeholder).into(iv);
        }
    }

    /*public static void load(Context context, String url, int placeholder, ImageView iv) {
        if (!App.getAppComponent().preferencesHelper().getNoImageState()) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, String url, int placeholder, ImageView iv) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).into(iv);
        }
    }
    public static void load(Context context, int url, int placeholder, ImageView iv) {
        if (!App.getAppComponent().preferencesHelper().getNoImageState()) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, int url, int placeholder, ImageView iv) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).into(iv);
        }
    }*/
    public static void load(Context context, String url, int err, ImageView iv) {
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(err).into(iv);
        }
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public static void load(Activity activity, String url, int err, ImageView iv) {
//        if (!activity.isDestroyed()) {
//            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(err).into(iv);
//        }
//    }

    public static void loadBitmap(Context context, String url, ImageView iv, int err) {
        if (context != null) {
            Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(err).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadBitmap(Activity activity, String url, ImageView iv, int err) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).error(err).into(iv);
        }
    }

    public static void load(Context context, String url, ImageView iv, int width, int height) {    //设置图片大小
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().override(width, height).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, String url, ImageView iv, int width, int height) {    //设置图片大小
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).centerCrop().override(width, height).into(iv);
        }
    }

    public static void load(Context context, String url, int placeholder, ImageView iv, int width, int height) {    //设置图片大小
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).centerCrop().override(width, height).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void load(Activity activity, String url, int placeholder, ImageView iv, int width, int height) {    //设置图片大小
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeholder).centerCrop().override(width, height).into(iv);
        }
    }

    public static void loadAll(Context context, String url, ImageView iv) {  //不从缓存获取
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_placeholder).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadAll(Activity activity, String url, ImageView iv) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_placeholder).into(iv);
        }
    }

    public static void loadAll(Context context, String url, ImageView iv, int width, int height) {    //设置图片大小
        if (context != null) {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_placeholder).centerCrop().override(width, height).into(iv);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void loadAll(Activity activity, String url, ImageView iv, int width, int height) {    //设置图片大小
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).placeholder(R.drawable.ic_placeholder).centerCrop().override(width, height).into(iv);
        }
    }
}
