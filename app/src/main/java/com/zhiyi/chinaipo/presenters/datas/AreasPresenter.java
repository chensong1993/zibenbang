package com.zhiyi.chinaipo.presenters.datas;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.datas.AreaConnector;
import com.zhiyi.chinaipo.base.connectors.stocks.IndexConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class AreasPresenter extends RxPresenter<AreaConnector.View> implements AreaConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public AreasPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getTotalAreas(int pageOffset) {
        addSubscribe(mDataManager.getStatisticByArea(pageOffset)
                        .compose(RxUtils.<ApiResponse<List<StasAreaEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<StasAreaEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<StasAreaEntity>>(mView) {
                            @Override
                            public void onNext(List<StasAreaEntity> items) {
                                mView.showAreas(items);
                            }
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.noContent();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }
}
