package com.zhiyi.chinaipo.presenters.datas;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.datas.OrganizationConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class OrganizationPresenter extends RxPresenter<OrganizationConnector.View> implements OrganizationConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public OrganizationPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getOrganizations(String type, int pageOffset) {
        Flowable<ApiResponse<List<StasOrgsEntity>>> orgObserver ;
        if (type.equals("investor")) {
            orgObserver = mDataManager.getStatisticByInvestor(pageOffset);
        } else if (type.equals("dealer")) {
            orgObserver = mDataManager.getStatisticByDealer(pageOffset);
        } else if (type.equals("lawyer")) {
            orgObserver = mDataManager.getStatisticByLawyer(pageOffset);
        } else {
            orgObserver = mDataManager.getStatisticByAccountant(pageOffset);
        }

        addSubscribe(orgObserver
                .compose(RxUtils.<ApiResponse<List<StasOrgsEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasOrgsEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasOrgsEntity>>(mView) {
                    @Override
                    public void onNext(List<StasOrgsEntity> items) {
                        mView.showOrganizations(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.err("");
//                        ToastUtils.showLongToast("Can not load issues");
                    }
                })
            );
    }
}
