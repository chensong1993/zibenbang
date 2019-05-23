package com.zhiyi.chinaipo.base.connectors.stocks;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.KLineEntity;

import java.util.List;

public interface KlineConnector {

    interface View extends BaseView {

        void showKline(List<KLineEntity> mKLineData);
        void err();
    }

    interface Presenter extends BasePresenter<View> {
        void getKlineData(String stockCode, String klineType, int pageOffset);
    }
}
