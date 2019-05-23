package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.SummaryConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.details.ManagementEntity;
import com.zhiyi.chinaipo.models.entity.details.ProfileEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class SummaryPresenter extends RxPresenter<SummaryConnector.View> implements SummaryConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private String queryStr = null;

    @Inject
    public SummaryPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getManagement(String stockCode, int pageOffset) {
        addSubscribe(mDataManager.getManagement(stockCode, pageOffset)
                .compose(RxUtils.<ApiResponse<List<ManagementEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ManagementEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ManagementEntity>>(mView) {
                    @Override
                    public void onNext(List<ManagementEntity> items) {
                        mView.showManagement(items);
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.errM();
                    }
                })
        );
    }

    @Override
    public void getProfile(String stockCode) {
        addSubscribe(mDataManager.getProfile(stockCode)
                .compose(RxUtils.<ApiResponse<List<ProfileEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ProfileEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ProfileEntity>>(mView) {
                    @Override
                    public void onNext(List<ProfileEntity> items) {
                        mView.showProfile(items.get(0));
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.errP();
                    }
                })
        );
    }
}
