package com.zhiyi.chinaipo.base.connectors.main;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;

public interface MainConnector {

    interface View extends BaseView{

        void showUpdateDialog(String versionContent);

        void startDownloadService();
    }

    interface Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

        void checkPermissions(RxPermissions rxPermissions);

    }
}
