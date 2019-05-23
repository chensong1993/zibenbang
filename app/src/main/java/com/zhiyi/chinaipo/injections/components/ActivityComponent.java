package com.zhiyi.chinaipo.injections.components;

import android.app.Activity;

import com.zhiyi.chinaipo.injections.modules.ActivityModule;
import com.zhiyi.chinaipo.injections.scope.ActivityScope;
import com.zhiyi.chinaipo.ui.activity.AdActivity;
import com.zhiyi.chinaipo.ui.activity.MainActivity;
import com.zhiyi.chinaipo.ui.activity.MoreNewsActivity;
import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.ui.activity.WelcomeActivity;
import com.zhiyi.chinaipo.ui.activity.datas.AreaListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.IndustryAreaListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.IndustryListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.NewStockActivity;
import com.zhiyi.chinaipo.ui.activity.datas.OrganizationListActivity;
import com.zhiyi.chinaipo.ui.activity.login.FindPwdActivity;
import com.zhiyi.chinaipo.ui.activity.login.LoginActivity;
import com.zhiyi.chinaipo.ui.activity.login.RegisterActivity;
import com.zhiyi.chinaipo.ui.activity.login.ResetPwdActivity;
import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;
import com.zhiyi.chinaipo.ui.activity.news.SpecialColumnActivity;
import com.zhiyi.chinaipo.ui.activity.news.SpecialTopicActivity;
import com.zhiyi.chinaipo.ui.activity.profile.ProfileSettingActivity;
import com.zhiyi.chinaipo.ui.activity.search.SearchActivity;
import com.zhiyi.chinaipo.ui.activity.search.SearchDetailsActivity;
import com.zhiyi.chinaipo.ui.activity.stocks.MoreFinanceActivity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.ui.activity.stocks.PdfViewActivity;
import com.zhiyi.chinaipo.ui.activity.tables.TableMultiActivity;
import com.zhiyi.chinaipo.ui.category.ColumnActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(MainActivity mainActivity);

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(ColumnActivity columnActivity);

    void inject(SearchActivity searchActivity);

    void inject(SearchDetailsActivity searchDetailsActivity);

    void inject(TableMultiActivity tableMultiActivity);

    void inject(PdfViewActivity pdfViewActivity);

    void inject(StockDetailActivity stockDetailActivity);

    void inject(NewStockActivity newStockActivity);

    void inject(AreaListActivity areaListActivity);

    void inject(IndustryListActivity industryListActivity);

    void inject(OrganizationListActivity organizationListActivity);

    void inject(MoreFinanceActivity moreFinanceActivity);

    void inject(MoreNewsActivity moreNewsActivity);

    void inject(WebActivity newWebActivity);

    void inject(LoginActivity mLoginActivity);

    void inject(SpecialColumnActivity mColumnActivity);

    void inject(SpecialTopicActivity mSpecialTopicActivity);

    void inject(IndustryAreaListActivity mIndustryAreaListActivity);

    void inject(RegisterActivity mRegisterActivity);

    void inject(FindPwdActivity mFindPwdActivity);

    void inject(ResetPwdActivity mPwdActivity);

    void inject(ProfileSettingActivity mSettingActivity);

    void inject(AdActivity mAdActivity);
}
