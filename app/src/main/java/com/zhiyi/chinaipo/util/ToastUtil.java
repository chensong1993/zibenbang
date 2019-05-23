package com.zhiyi.chinaipo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author chensong
 * @date 2018/4/23 11:00
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
