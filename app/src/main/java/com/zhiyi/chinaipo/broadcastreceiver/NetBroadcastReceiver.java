package com.zhiyi.chinaipo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.util.NetUtil;

/**
 * @author chensong
 * @date 2018/8/8 13:44
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

   // public NetEvevt evevt = SimpleFragment.evevt;
    public NetEvevt evevt1 = SimpleActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            //evevt.onNetChange(netWorkState);
            evevt1.onNetChange(netWorkState);
        }

    }

    // 自定义接口
    public interface NetEvevt {
        void onNetChange(int netMobile);
    }

}
