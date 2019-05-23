package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;

/**
 * @author chensong
 * @date 2018/3/27 11:51
 */
public interface RegisterConnector {

    interface View extends BaseView {
        void inProcessing(String statusMsg);

        void registerSuccess(String tokenEntity);

        void registerErr();

        void getCaptcha(CaptchaEntity captchaEntity);

        void captchaAuth(String status);

        void smsCaptcha(String hashkey);

        void smsCaptchaAuth(String s);

        void getCaptchaErr();

        void smsCaptchaErr(String s);
    }

    interface Presenter extends BasePresenter<View> {
        void goRegister(String userName, String Pwd, String confirmPwd);

        void getCaptcha();

        void captchaAuth(String response, String hashkey);

        void smsCaptchaAuth(String response, String hashkey);

        void smsCaptcha(String sms);
    }
}
