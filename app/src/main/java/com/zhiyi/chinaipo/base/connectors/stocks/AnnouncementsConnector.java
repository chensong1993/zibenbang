package com.zhiyi.chinaipo.base.connectors.stocks;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.AnnouncementEntity;

import java.util.List;

public interface AnnouncementsConnector {

    interface View extends BaseView {
        void showAnnouncements(List<AnnouncementEntity> announcements);
    }

    interface Presenter extends BasePresenter<View> {
        void getAnnouncements(String stockCode, int pageOffset);
    }
}
