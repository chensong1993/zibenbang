package com.zhiyi.chinaipo.base.connectors.stocks;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;

import java.util.List;

public interface IndexConnector {

    interface View extends BaseView {

        void showStockIndexes(List<MarketIndexEntity> stockIndexes);

        void showPriceByChange(List<StockPriceEntity> priceList);

        void showPriceByVolume(List<StockPriceEntity> priceList);

        void showPriceByAmount(List<StockPriceEntity> priceList);

        void homeIndex(int index);

        void err();
    }

    interface Presenter extends BasePresenter<View> {

        void getStockIndex();

        void getPriceByChange(String index);

        void getPriceByVolume(String index);

        void getPriceByAmount(String index);
    }
}
