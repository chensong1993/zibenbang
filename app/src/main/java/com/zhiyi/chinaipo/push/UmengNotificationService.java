package com.zhiyi.chinaipo.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.umeng.message.UmengMessageService;
import com.zhiyi.chinaipo.util.LogUtil;

import org.android.agoo.common.AgooConstants;

public class UmengNotificationService extends UmengMessageService {
    @SuppressLint("LongLogTag")
    @Override
    public void onMessage(Context context, Intent intent) {
        LogUtil.i("UmengNotificationService", "onMessage");
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Intent intent1 = new Intent();
        intent1.setClass(context, MyNotificationService.class);
        intent1.putExtra("UmengMsg", message);
        context.startService(intent1);
    }
}
