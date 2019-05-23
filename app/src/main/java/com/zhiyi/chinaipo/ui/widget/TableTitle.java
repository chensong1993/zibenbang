package com.zhiyi.chinaipo.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;

/**
 * @author chensong
 * @date 2017/12/1 11:46
 *
 * 横竖滑动的表格 颜色字体统一变化 还有变换的箭头
 */
public class TableTitle {

    public static void setTextColor(TextView view, Context context){
        view.setTextColor(ContextCompat.getColor(context, R.color.black));
    }

    public static void setSelected(TextView view, String s){
        if(view.isSelected()){
            view.setText(Html.fromHtml("<font color='#d71820'>▲</font>"+s));
            view.setSelected(false);

        }else {
            view.setText(Html.fromHtml("<font color='#0b9d0b'>▼</font>"+s));
            view.setSelected(true);
        }
    }
}
