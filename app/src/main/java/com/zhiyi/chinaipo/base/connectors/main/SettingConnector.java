package com.zhiyi.chinaipo.base.connectors.main;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;

public interface SettingConnector {

    interface View extends BaseView {

//        void showUpdateDialog(VersionBean bean);

    }

    interface  Presenter extends BasePresenter<View> {

        void checkVersion(String currentVersion);

        void setNoImageState(boolean b);

        void setAutoCacheState(boolean b);

        boolean getNoImageState();

        boolean getAutoCacheState();
    }
}
