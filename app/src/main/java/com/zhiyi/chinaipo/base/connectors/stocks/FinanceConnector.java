package com.zhiyi.chinaipo.base.connectors.stocks;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;

import java.util.List;

public interface FinanceConnector {

    interface View extends BaseView {
        void showProfit(List<BalanceEntity> balanceEntities);
        void showCashFlow(List<CashFlowEntity> cList);
        void showFinance(List<FinanceEntity> financeEntities);
        void err();
    }

    interface Presenter extends BasePresenter<View> {
        void getProfit(String stockCode);
        void getFinance(String stockCode);
        void getCashFlow(String stockCode);
    }
}
