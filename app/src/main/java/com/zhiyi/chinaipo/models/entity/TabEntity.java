package com.zhiyi.chinaipo.models.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @author chensong
 * @date 2018/12/17 10:30
 */
public class TabEntity implements CustomTabEntity{
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    public TabEntity(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}