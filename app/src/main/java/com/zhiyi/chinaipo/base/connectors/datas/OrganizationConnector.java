package com.zhiyi.chinaipo.base.connectors.datas;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;

import java.util.List;

public interface OrganizationConnector {

    interface View extends BaseView {

        void showOrganizations(List<StasOrgsEntity> topIndustry);
        void err(String err);
    }

    interface Presenter extends BasePresenter<View> {

        void getOrganizations(String type, int pageOffset);

    }
}
