package com.zhiyi.chinaipo.presenters.datas;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.datas.DataConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.HomeIndex;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class DataPresenter extends RxPresenter<DataConnector.View> implements DataConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public DataPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(DataConnector.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(HomeIndex.class)
                .compose(RxUtils.<HomeIndex>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomeIndex>(mView, "跳转失败") {
                    @Override
                    public void onNext(HomeIndex s) {
                        mView.iconRefresh(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent();
                    }
                })
        );
    }

    private void getSearchResultData() {
        currentPage = 1;
//        addSubscribe(mDataManager.fetchCnBlogSearchList(queryStr, currentTech, NUM_OF_PAGE, currentPage)
//                .compose(RxUtil.<CnBlogHttpResponse<List<CnBlogSearchItemBean>>>rxSchedulerHelper())
//                .compose(RxUtil.<List<CnBlogSearchItemBean>>handleResult())
//                .map(new Function<List<CnBlogSearchItemBean>, List<CnBlogItemBean>>() {
//                    @Override
//                    public List<CnBlogItemBean> apply(List<CnBlogSearchItemBean> CnBlogSearchItemBeen) {
//                        List<CnBlogItemBean> newList = new ArrayList<>();
//                        for (CnBlogSearchItemBean item : CnBlogSearchItemBeen) {
//                            CnBlogItemBean bean = new CnBlogItemBean();
//                            bean.set_id(item.getGanhuo_id());
//                            bean.setDesc(item.getDesc());
//                            bean.setPublishedAt(item.getPublishedAt());
//                            bean.setWho(item.getWho());
//                            bean.setUrl(item.getUrl());
//                            newList.add(bean);
//                        }
//                        return newList;
//                    }
//                })
//                .subscribeWith(new CommonSubscriber<List<CnBlogItemBean>>(mView) {
//                    @Override
//                    public void onNext(List<CnBlogItemBean> CnBlogItemBeen) {
//                        mView.showContent(CnBlogItemBeen);
//                    }
//                })
//        );
    }

    @Override
    public void getMarketTops() {
        addSubscribe(mDataManager.getIndexSC()
                        .compose(RxUtils.<ApiResponse<List<IndexSCEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<IndexSCEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<IndexSCEntity>>(mView) {
                            @Override
                            public void onNext(List<IndexSCEntity> itemCategory) {
                                mView.showMarketTops(itemCategory);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }

    @Override
    public void getTotalAreas() {
        addSubscribe(mDataManager.getStatisticByArea()
                .compose(RxUtils.<ApiResponse<List<StasAreaEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasAreaEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasAreaEntity>>(mView) {
                    @Override
                    public void onNext(List<StasAreaEntity> itemAreas) {
                        mView.showAreas(itemAreas.get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getTotalIndustries() {
        addSubscribe(mDataManager.getStatisticByIndustry()
                .compose(RxUtils.<ApiResponse<List<StasIndustryEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasIndustryEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasIndustryEntity>>(mView) {
                    @Override
                    public void onNext(List<StasIndustryEntity> itemIndus) {
                        mView.showIndustry(itemIndus);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getNewStocks() {
        addSubscribe(mDataManager.getNewStocks("month", 1)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> itemOrgs) {
                        mView.showNewStocks(itemOrgs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getStatsByDealer() {
        addSubscribe(mDataManager.getStatisticByDealer()
                .compose(RxUtils.<ApiResponse<List<StasOrgsEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasOrgsEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasOrgsEntity>>(mView) {
                    @Override
                    public void onNext(List<StasOrgsEntity> itemOrgs) {
                        mView.showDealers(itemOrgs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getStatsByInvestor() {
        addSubscribe(mDataManager.getStatisticByInvestor()
                .compose(RxUtils.<ApiResponse<List<StasOrgsEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasOrgsEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasOrgsEntity>>(mView) {
                    @Override
                    public void onNext(List<StasOrgsEntity> itemOrgs) {
                        mView.showInvestors(itemOrgs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getStatsByLawyer() {
        addSubscribe(mDataManager.getStatisticByLawyer()
                .compose(RxUtils.<ApiResponse<List<StasOrgsEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasOrgsEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasOrgsEntity>>(mView) {
                    @Override
                    public void onNext(List<StasOrgsEntity> itemOrgs) {
                        mView.showLawyers(itemOrgs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getStatsByAccountant() {
        addSubscribe(mDataManager.getStatisticByAccountant()
                .compose(RxUtils.<ApiResponse<List<StasOrgsEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StasOrgsEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StasOrgsEntity>>(mView) {
                    @Override
                    public void onNext(List<StasOrgsEntity> itemOrgs) {
                        mView.showAccountants(itemOrgs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }
}
