package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;

public interface LoginConnector {

    interface View extends BaseView {
//        void authProcessing(AuthenticatedEvent.AuthEventType event, String additionalMsg);
        void inProcessing(String statusMsg);
        void loginSuccess();
        void loginFailed();
//        void updateUserInfo();
//        void clearUserInfo();
    }

    interface Presenter extends BasePresenter<View> {

        void doLogin(String username, String password);

    }
}
