package com.zhiyi.chinaipo.base.connectors.auth;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.H3;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.event.EventLogin;

import java.util.List;

public interface ProfileConnector {

    interface View extends BaseView {
        void updateUserInfo(UserEntity userInfo);
        void updateThirdPartyUser(EventLogin user);
        void clearUserInfo();
        void userToken(String token);
        void UserFollowed(List<StockPriceEntity> userInfo);
        void weaherData(H3 weatherEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void userCode(String stockCode);
        void updateProfile ();
        void weaherData(String area);
    }
}
