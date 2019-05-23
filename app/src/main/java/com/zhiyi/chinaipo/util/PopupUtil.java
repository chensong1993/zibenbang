package com.zhiyi.chinaipo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author chensong
 * @date 2018/5/7 16:56
 */
public class PopupUtil {

    public static void Dialog(Context context, View view,Dialog dialog) {
        //Dialog dialog = new Dialog(context, R.style.DialogStyle);
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window window = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        window.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.x = 0;
        //获取显示器的宽度
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        //    将属性设置给窗体
        window.setAttributes(lp);
    }


}
