package com.zhiyi.chinaipo.base.connectors.datas;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.HomeIndex;

import java.util.List;

public interface DataConnector {

    interface View extends BaseView {

        void showMarketTops(List<IndexSCEntity> marketTops);
        void showAreas(StasAreaEntity topArea);
        void showIndustry(List<StasIndustryEntity> topIndustry);
        void showNewStocks(List<StockPriceEntity> newStocks);
        void showDealers(List<StasOrgsEntity> topDealers);
        void showInvestors(List<StasOrgsEntity> topInvestors);
        void showLawyers(List<StasOrgsEntity> topLawyers);
        void showAccountants(List<StasOrgsEntity> topAccountant);
        void iconRefresh(HomeIndex homeIndex);
    }

    interface Presenter extends BasePresenter<View> {

        void getMarketTops();
        void getTotalAreas();
        void getTotalIndustries();
        void getNewStocks();
        void getStatsByDealer();
        void getStatsByInvestor();
        void getStatsByLawyer();
        void getStatsByAccountant();

    }
}
