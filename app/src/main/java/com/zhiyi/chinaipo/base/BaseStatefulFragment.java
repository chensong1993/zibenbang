package com.zhiyi.chinaipo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Navigator;
import com.zhiyi.chinaipo.injections.components.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import nucleus5.presenter.Presenter;
import nucleus5.view.NucleusSupportFragment;


public abstract class BaseStatefulFragment<PresenterType extends Presenter> extends NucleusSupportFragment<PresenterType> {
//    @Nullable
//    @BindView(R.id.toolbar)
//    public Toolbar toolbarView;
//
//    @Nullable
//    @BindView(R.id.toolbar_title)
//    public TextView toolbarTitleView;

    private Unbinder mUnBinder;
    public Navigator navigator;

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Icepick.saveInstanceState(this, bundle);
    }

    @Override
    public void onCreate(Bundle bundle) {
        injectorPresenter();
        super.onCreate(bundle);
        navigator = getAppComponent().navigator();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);

    }

    protected String getTitle() {
        return "";
    }

    protected AppComponent getAppComponent() {
        return ((App) getActivity().getApplication()).getAppComponent();
    }

    protected void injectorPresenter() {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

}