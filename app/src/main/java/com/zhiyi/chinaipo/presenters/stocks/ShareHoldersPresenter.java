package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.ShareHoldersConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.details.ShareHoldersEntity;
import com.zhiyi.chinaipo.models.entity.details.ShareListEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class ShareHoldersPresenter extends RxPresenter<ShareHoldersConnector.View> implements ShareHoldersConnector.Presenter {

    private DataManager mDataManager;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public ShareHoldersPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getShareList(String stockCode) {
                addSubscribe(mDataManager.getShareList(stockCode)
                .compose(RxUtils.<ApiResponse<List<ShareListEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ShareListEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ShareListEntity>>(mView) {
                    @Override
                    public void onNext(List<ShareListEntity> shareList) {
                        mView.showShareList(shareList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.err();
                    }
                })
        );
    }

    @Override
    public void getTopShareHolders(String stockCode) {
        addSubscribe(mDataManager.getShareHolders(stockCode, 1)
                .compose(RxUtils.<ApiResponse<List<ShareHoldersEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ShareHoldersEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ShareHoldersEntity>>(mView) {
                    @Override
                    public void onNext(List<ShareHoldersEntity> shareList) {
                        mView.showShareHolders(shareList);
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
