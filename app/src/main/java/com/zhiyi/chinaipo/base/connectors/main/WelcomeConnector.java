package com.zhiyi.chinaipo.base.connectors.main;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;

public interface WelcomeConnector {

    interface View extends BaseView {

        void showContent(AdverticeEntity welcomeData);

        void jumpToMain();

    }

    interface  Presenter extends BasePresenter<View> {

        void getWelcomeData();
        void checkPermissions(RxPermissions rxPermissions);

    }
}
