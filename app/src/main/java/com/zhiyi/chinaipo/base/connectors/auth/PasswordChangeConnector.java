package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;

/**
 * @author chensong
 * @date 2018/3/27 11:51
 */
public interface PasswordChangeConnector {

    interface View extends BaseView{
        void changeSuccess();
        void changeErr();
    }

    interface Presenter extends BasePresenter<View>{
        void passwordChenge(String token,String newpassword1, String newpassword2);

    }
}
