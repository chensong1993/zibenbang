package com.zhiyi.chinaipo.util;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 2017/9/6.
 */
public class EditAction {

    public static void EditEnter(EditText e){
        //回车不换行
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
    }
}
