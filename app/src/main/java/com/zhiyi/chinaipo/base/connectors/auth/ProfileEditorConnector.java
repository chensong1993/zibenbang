package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;

public interface ProfileEditorConnector {

    interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<View> {

        void doLogout();

    }
}
