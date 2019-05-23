package com.zhiyi.chinaipo.presenters.datas;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.datas.IndustryConnector;
import com.zhiyi.chinaipo.base.connectors.stocks.IndexConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class IndustryPresenter extends RxPresenter<IndustryConnector.View> implements IndustryConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;
    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public IndustryPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getIndustries(int pageOffset) {
        addSubscribe(mDataManager.getStatisticByIndustry(pageOffset)
                        .compose(RxUtils.<ApiResponse<List<StasIndustryEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<StasIndustryEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<StasIndustryEntity>>(mView) {
                            @Override
                            public void onNext(List<StasIndustryEntity> items) {
                                mView.showIndustry(items);
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