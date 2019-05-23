package com.zhiyi.chinaipo.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;

/**
 * @author chensong
 * @date 2017/12/1 17:42
 */
public class ShowDiglog {

    public static void Diglog( Context context){
        final Dialog mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
        //将布局设置给Dialog
        mDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window window = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        window.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = -20;//设置Dialog距离底部的距离
        lp.x = 0;
        //获取显示器的宽度
        lp.width = context.getResources().getDisplayMetrics().widthPixels;
        //    将属性设置给窗体
        window.setAttributes(lp);
        mDialog.show();//显示对话框

        //留言的文字
        final TextView mE;
        mE = (TextView) inflate.findViewById(R.id.et_text);
        //发送
        inflate.findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mDialog.dismiss();
            }
        });
    }

}
