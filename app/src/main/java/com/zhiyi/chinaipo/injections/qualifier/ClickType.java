package com.zhiyi.chinaipo.injections.qualifier;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({
        ClickType.CLICK_TYPE_USER_CLICKED,
        ClickType.CLICK_TYPE_TOPIC_CLICKED
})

public @interface ClickType {
    int CLICK_TYPE_USER_CLICKED = 1000;

    int CLICK_TYPE_TOPIC_CLICKED = 1001;
}