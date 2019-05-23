package com.zhiyi.chinaipo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.util.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class AuthenticateActivity extends SwipeBackActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    protected DataManager mDataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        mDataManager = App.getAppComponent().getDataManager();
        onViewCreated();
        App.getInstance().addActivity(this);
        //状态栏字体变黑
        StatusBarUtil.StatusBarLightMode(this,true);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initEventAndData();
    }

    protected void onViewCreated() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
