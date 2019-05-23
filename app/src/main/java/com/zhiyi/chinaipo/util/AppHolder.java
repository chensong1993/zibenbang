package com.zhiyi.chinaipo.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;

/**
 * 这个是SharedPreferences的工具类
 */
public final class AppHolder {

    private static Context mContext;

    public final static boolean IS_DEBUG = true;

    private static long mUIThreadID;
    private static Handler mUIThreadHandler;

    public static void init(Context context) {
        mContext = context;
        mUIThreadID = Thread.currentThread().getId();
        mUIThreadHandler = new Handler();
    }

    public static Context getContext() {
        return mContext;
    }

    public static long getUIThreadID() {
        return mUIThreadID;
    }

    public static Handler getUIThreadHandler() {
        return mUIThreadHandler;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static Resources getResource() {
        return mContext.getResources();
    }

}
