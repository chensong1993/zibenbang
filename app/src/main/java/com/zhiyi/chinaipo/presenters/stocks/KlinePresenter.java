package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.KlineConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.details.KLineEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class KlinePresenter extends RxPresenter<KlineConnector.View> implements KlineConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public KlinePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getKlineData(String stockCode, String klineType, int pageOffset) {

        if (klineType.equals("day")) {
            addSubscribe(mDataManager.getKDay(stockCode, pageOffset)
                    .compose(RxUtils.<ApiResponse<List<KLineEntity>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<KLineEntity>>handleResults())
                    .subscribeWith(new CommonSubscriber<List<KLineEntity>>(mView, false) {
                        @Override
                        public void onNext(List<KLineEntity> klines) {
                            mView.showKline(klines);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mView.err();
                        }
                    })
            );
        } else if (klineType.equals("week")) {
            addSubscribe(mDataManager.getKWeek(stockCode, pageOffset)
                    .compose(RxUtils.<ApiResponse<List<KLineEntity>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<KLineEntity>>handleResults())
                    .subscribeWith(new CommonSubscriber<List<KLineEntity>>(mView, false) {
                        @Override
                        public void onNext(List<KLineEntity> klines) {
                            mView.showKline(klines);
                        }
                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mView.err();
                        }
                    })
            );
        } else {
            addSubscribe(mDataManager.getKMonth(stockCode, pageOffset)
                    .compose(RxUtils.<ApiResponse<List<KLineEntity>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<KLineEntity>>handleResults())
                    .subscribeWith(new CommonSubscriber<List<KLineEntity>>(mView, false) {
                        @Override
                        public void onNext(List<KLineEntity> klines) {
                            mView.showKline(klines);
                        }
                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mView.err();
                        }
                    })
            );
        }

    }
}
