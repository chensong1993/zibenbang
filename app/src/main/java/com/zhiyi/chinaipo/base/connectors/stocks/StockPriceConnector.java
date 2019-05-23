package com.zhiyi.chinaipo.base.connectors.stocks;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;

import java.util.List;

public interface StockPriceConnector {

    interface View extends BaseView {

        void showPrice(List<StockPriceEntity> priceList);
        void err(String s);
    }

    interface Presenter extends BasePresenter<View> {

        void getPrices(String index, String sortBy, String sortOrder, int pageOffset);
        void getPricesWithExtra(String baseIndex, String sortBy,String order, String extraKey, String extraValue, int pageOffset);
    }
}
