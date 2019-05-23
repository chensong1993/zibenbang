package com.zhiyi.chinaipo.util;

/**
 * @author chensong
 * @date 2018/9/14 14:51
 */
public class RepeatCllickUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD>0 && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
