package com.zhiyi.chinaipo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhiyi.chinaipo.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import nucleus5.presenter.Presenter;
import nucleus5.view.NucleusAppCompatActivity;

public abstract class BaseStatefulActivity<PresenterType extends Presenter> extends NucleusAppCompatActivity<PresenterType> {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        App.getInstance().addActivity(this);

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
