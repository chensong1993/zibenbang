package com.zhiyi.chinaipo.injections.modules;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.db.DBHelper;
import com.zhiyi.chinaipo.models.db.RealmHelper;
import com.zhiyi.chinaipo.models.http.HttpHelper;
import com.zhiyi.chinaipo.models.http.RetrofitHelper;
import com.zhiyi.chinaipo.models.prefs.ImplPreferencesHelper;
import com.zhiyi.chinaipo.models.prefs.PreferencesHelper;
import com.zhiyi.chinaipo.models.prefs.UserPreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chinaipo on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    UserPreferencesHelper provideUserHelper(ImplPreferencesHelper userHelper) {
        return userHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferencesHelper preferencesHelper, UserPreferencesHelper userHelper) {
        return new DataManager(httpHelper, DBHelper, preferencesHelper, userHelper);
    }
}
