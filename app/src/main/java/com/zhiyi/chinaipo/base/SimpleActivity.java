package com.zhiyi.chinaipo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.broadcastreceiver.NetBroadcastReceiver;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NetUtil;
import com.zhiyi.chinaipo.util.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class SimpleActivity extends SwipeBackActivity implements NetBroadcastReceiver.NetEvevt {

    protected Activity mContext;
    private Unbinder mUnBinder;
    //网络类型
    private int netMobile;
    public static NetBroadcastReceiver.NetEvevt evevt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        getSwipeBackLayout().setParallaxOffset(0.1f);
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        App.getInstance().addActivity(this);
        initEventAndData();
        //状态栏字体变黑
        StatusBarUtil.StatusBarLightMode(this,true);
        evevt = this;
        inspectNet();
      /*  IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.NET_WORK);
        registerReceiver(receiver, filter);*/
        //启动网络检测
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 在横向退出
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //  initEventAndData();
    }

    protected void onViewCreated() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnBinder.unbind();
        //  unregisterReceiver(receiver);
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(this);
        if (netMobile == 1) {
            LogUtil.i("inspectNet", "wifi:");
        } else if (netMobile == 0) {
            LogUtil.i("inspectNet", "yidong:");
        } else if (netMobile == -1) {
            LogUtil.i("inspectNet", "wu:");

        }
        return isNetConnect();


    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();

    }


    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}
