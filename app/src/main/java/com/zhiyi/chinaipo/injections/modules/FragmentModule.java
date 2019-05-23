package com.zhiyi.chinaipo.injections.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.zhiyi.chinaipo.injections.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chinaipo on 16/8/7.
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
