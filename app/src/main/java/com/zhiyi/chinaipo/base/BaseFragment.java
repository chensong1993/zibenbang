package com.zhiyi.chinaipo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.injections.components.DaggerFragmentComponent;
import com.zhiyi.chinaipo.injections.components.FragmentComponent;
import com.zhiyi.chinaipo.injections.modules.FragmentModule;

import javax.inject.Inject;

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {

    @Inject
    protected T mPresenter;

    private boolean isVisible;
    private boolean isInitialized;
    //private NetBroadcastReceiver mNetBroadcastReceiver;

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
     //   initEventAndData();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void showErrorMsg(String msg) {
      //  SnackbarUtil.show(((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0), msg);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            if (!isInitialized) {
                isInitialized = true;
                initEventAndData();
            }
        } else {
            isVisible = false;
        }
      // initEventAndData();
    }


    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    //注册和销毁网络变化广播监听
    @Override
    public void onResume() {
        super.onResume();
//        if (mNetBroadcastReceiver == null) {
//            mNetBroadcastReceiver = new NetBroadcastReceiver();
//        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        mContext.registerReceiver(mNetBroadcastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
       // mContext.unregisterReceiver(mNetBroadcastReceiver);
    }

    protected abstract void initInject();
}