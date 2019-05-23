package com.zhiyi.chinaipo.base.connectors.stocks;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.TimeLineEntity;
import com.zhiyi.chinaipo.models.entity.details.Top5Entity;

import java.util.List;

public interface TimelineConnector {

    interface View extends BaseView {
        void setupPrePrice(String preClosePrice);
        void showTimeline(List<TimeLineEntity> mList);
        void showTops(Top5Entity mTops);
        void error();
    }

    interface Presenter extends BasePresenter<View> {
        void getTimeline(String stockId);
        void getTopDeals(String stockId);
    }
}
