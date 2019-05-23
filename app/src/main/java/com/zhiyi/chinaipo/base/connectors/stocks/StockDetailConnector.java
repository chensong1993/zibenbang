package com.zhiyi.chinaipo.base.connectors.stocks;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.EventLogin;

import java.util.List;

public interface StockDetailConnector {

    interface View extends BaseView {

        void showPrice(StockPriceEntity stockDetail);
        void showFollow(EventLogin login);
        void userToken(String userToken);
    }

    interface Presenter extends BasePresenter<View> {

        void getPrice(String stockCode);

    }
}
