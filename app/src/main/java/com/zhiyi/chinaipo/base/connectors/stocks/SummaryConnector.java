package com.zhiyi.chinaipo.base.connectors.stocks;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.ManagementEntity;
import com.zhiyi.chinaipo.models.entity.details.ProfileEntity;

import java.util.List;

public interface SummaryConnector {

    interface View extends BaseView {

        void showProfile(ProfileEntity profileEntity);

        void showManagement(List<ManagementEntity> managers);
        void errM();
        void errP();
    }

    interface Presenter extends BasePresenter<View> {

        void getManagement(String stockCode, int pageOffset);

        void getProfile(String stockCode);

    }
}
