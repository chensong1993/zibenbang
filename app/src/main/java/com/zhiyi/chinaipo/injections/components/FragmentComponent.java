package com.zhiyi.chinaipo.injections.components;

import android.app.Activity;

import com.zhiyi.chinaipo.injections.modules.FragmentModule;
import com.zhiyi.chinaipo.injections.scope.FragmentScope;
import com.zhiyi.chinaipo.ui.fragment.BrandFragment;
import com.zhiyi.chinaipo.ui.fragment.DataFragment;
import com.zhiyi.chinaipo.ui.fragment.HomeFragment;
import com.zhiyi.chinaipo.ui.fragment.MarketFragment;
import com.zhiyi.chinaipo.ui.fragment.ProfileFragment;
import com.zhiyi.chinaipo.ui.fragment.kchart.KLineFragment;
import com.zhiyi.chinaipo.ui.fragment.kchart.TimelineFragment;
import com.zhiyi.chinaipo.ui.fragment.news.FirstNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.news.LatterNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.news.SpecialColumnFragment;
import com.zhiyi.chinaipo.ui.fragment.news.SpecialsFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.AnnouncementsFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.FinanceFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.RelatedNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.ShareHoldersFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.SummaryFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(FirstNewsFragment promoteFragment);

    void inject(LatterNewsFragment normalFragment);

    void inject(DataFragment dataFragment);

    void inject(MarketFragment marketFragment);

    void inject(AnnouncementsFragment announcementsFragment);

    void inject(FinanceFragment financeFragment);

    void inject(RelatedNewsFragment relatedNewsFragment);

    void inject(ShareHoldersFragment shareholderFragment);

    void inject(SummaryFragment summaryFragment);

    void inject(TimelineFragment timelineFragment);

    void inject(KLineFragment kLineFragment);

    void inject(ProfileFragment profileFragment);

    void inject(SpecialColumnFragment columnFragment);

    void inject(SpecialsFragment specialsFragment);

    void inject(BrandFragment brandFragment);
//
//    void inject(ThemeFragment themeFragment);
//
//    void inject(SettingFragment settingFragment);

}
