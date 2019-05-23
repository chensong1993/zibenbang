package com.zhiyi.chinaipo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NetUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class SimpleFragment extends SupportFragment {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnBinder;
    protected boolean isInited = false;
     //网络类型
    private int netMobile;

    //public static NetBroadcastReceiver.NetEvevt evevt;
    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
      //  evevt =this;
        isInited = true;
        inspectNet();

        //initEventAndData();
    }

    protected void onPostResume() {
        onPostResume();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(getActivity());
        if (netMobile == 1) {
            LogUtil.i("inspectNet","wifi:");
        } else if (netMobile == 0) {
            LogUtil.i("inspectNet","yidong:");
        } else if (netMobile == -1) {
            LogUtil.i("inspectNet","wu:");

        }
        return isNetConnect();

    }

    /**
     * 网络变化之后的类型
     */
//    @Override
//    public void onNetChange(int netMobile) {
//        // TODO Auto-generated method stub
//        this.netMobile = netMobile;
//        isNetConnect();
//
//    }

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

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();
}
