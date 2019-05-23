package com.zhiyi.chinaipo.injections.components;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Navigator;
import com.zhiyi.chinaipo.injections.modules.AppModule;
import com.zhiyi.chinaipo.injections.modules.HttpModule;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.db.RealmHelper;
import com.zhiyi.chinaipo.models.http.RetrofitHelper;
import com.zhiyi.chinaipo.models.prefs.ImplPreferencesHelper;
import com.zhiyi.chinaipo.models.prefs.UserPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();

    DataManager getDataManager();

    RetrofitHelper retrofitHelper();

    RealmHelper realmHelper();

    ImplPreferencesHelper preferencesHelper();

    ImplPreferencesHelper userHelper();

//    UserPreferencesHelper userHelper();

    Navigator navigator();

}
