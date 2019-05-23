package com.zhiyi.chinaipo.injections.modules;

import android.app.Activity;

import com.zhiyi.chinaipo.injections.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chinaipo on 16/8/7.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
