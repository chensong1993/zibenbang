package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.TimelineConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.details.TimeLineEntity;
import com.zhiyi.chinaipo.models.entity.details.Top5Entity;
import com.zhiyi.chinaipo.models.event.UpdateStockEvent;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

public class TimelinePresenter extends RxPresenter<TimelineConnector.View> implements TimelineConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String mStockCode = null;

    @Override
    public void attachView(TimelineConnector.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(UpdateStockEvent.class)
                .compose(RxUtils.<UpdateStockEvent>rxSchedulerHelper())
                .filter(new Predicate<UpdateStockEvent>() {
                    @Override
                    public boolean test(@NonNull UpdateStockEvent priceEvent) throws Exception {
                        if (mStockCode != null) {
                            return priceEvent.getStockCode() == mStockCode;
                        } else {
                            return false;
                        }

                    }
                })
                .subscribeWith(new CommonSubscriber<UpdateStockEvent>(mView, "获取股票价格失败") {
                    @Override
                    public void onNext(UpdateStockEvent s) {
                        mView.setupPrePrice(s.getStockPrice().getLast_close());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent();
                    }
                })
        );
    }


    @Inject
    public TimelinePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    public void setStockCode(String stockCode) {
        this.mStockCode = stockCode;
    }

    @Override
    public void getTimeline(String stockId) {
        addSubscribe(mDataManager.getTimeLine(stockId, currentPage++)
                .compose(RxUtils.<ApiResponse<List<TimeLineEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<TimeLineEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<TimeLineEntity>>(mView, false) {
                    @Override
                    public void onNext(List<TimeLineEntity> times) {
                        mView.showTimeline(times);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    @Override
    public void getTopDeals(String stockId) {
        addSubscribe(mDataManager.getTopDeals(stockId)
                .compose(RxUtils.<ApiResponse<List<Top5Entity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<Top5Entity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<Top5Entity>>(mView, false) {
                    @Override
                    public void onNext(List<Top5Entity> tops) {
                        mView.showTops(tops.get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.error();
                    }
                })
        );
    }
}
