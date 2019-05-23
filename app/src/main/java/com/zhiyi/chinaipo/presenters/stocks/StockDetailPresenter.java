package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.StockDetailConnector;
import com.zhiyi.chinaipo.base.connectors.stocks.StockPriceConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.models.event.TabTitle;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class StockDetailPresenter extends RxPresenter<StockDetailConnector.View> implements StockDetailConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    @Override
    public void attachView(StockDetailConnector.View view) {
        super.attachView(view);
        registerEvent();
        registerTokenEvent();
    }

    @Inject
    public StockDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(EventLogin.class)
                .compose(RxUtils.<EventLogin>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<EventLogin>(mView, "不能获取信息") {
                    @Override
                    public void onNext(EventLogin s) {
                        mView.showFollow(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }
    private void registerTokenEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(AuthenticatedEvent.class)
                .compose(RxUtils.<AuthenticatedEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<AuthenticatedEvent>(mView, "认证操作失败") {
                    @Override
                    public void onNext(AuthenticatedEvent s) {
                        AuthenticatedEvent.AuthEventType currentType = s.getAuthEvent();
                        switch (s.getAuthEvent()) {
                            case USER_TOKEN:
                                mView.userToken(s.getUserToken());
                                break;
                            default:
                                break;
                        }
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

//    private Observable<HashMap<Integer, ProductModel>> getProductsMapFromFeedsModelObservable(
//            FeedsModel feedsModel, int shopId, int langId, int currency) {
//        return Observable.fromIterable(feedsModel.getFeed())
//                .filter(feed -> feed.getProducts() != null)
//                .flatMapIterable(feed -> feed.getProducts())
//                .flatMap(productId -> productsRepository.fetchProduct(productId.intValue(), shopId, langId, currency))
//                .reduce(new HashMap<Integer, ProductModel>(),
//                        (productsMap, productModel) -> {
//                            productsMap.put(productModel.getIdProduct(), productModel);
//                            return productsMap;
//                        })
//                .toObservable();
//    }

//    listArticles(int pageIdx, int pageMax)
//.flatMapIterable(list->list)
//            .flatMap(article ->
//    getUserByUserIds(article.userId)
//     .subscribeOn(Schedulers.io())
//            .flatMapIterable(list->list),
//    Pair::of
//)
//        .toList();

//    public Single<Submission> getSubmission(final String threadId, final CommentSort sort) {
//        return Single.create(new SingleOnSubscribe<Submission>() {
//            @Override
//            public void subscribe(SingleEmitter<Submission> e) throws Exception {
//                // some irrelevant code
//
//                try {
//                    submission = reddit.getSubmission(sr); // Can throw RuntimeException
//                    e.onSuccess(submission);
//                } catch (Exception ex) {
//                    e.onError(ex);
//                }
//            }
//        });
//    }

//    public Single<List<MarketIndexEntity>> getMarketIndex(String stockIndex) {
//        return Single.just(mDataManager.getStockIndex(stockIndex)) // (1)
//                .map(stockIndexes -> {
//                    if (stockIndex.isEmpty()) {
//                        throw Exceptions.propagate(new NullPointerException("List is empty"));
//                    }
//                    return stockIndexes.toObservable();
//                }).doOnSuccess(stockIndexList -> {
//                    stockIndexList.collect(get(0));
//                });
//    }
//
//    public Single<List<Restaurant>> getRestaurants(int userId) {
//        return ddApi.getUserInfo(userId).flapMap(user -> {
//            return ddApi.getAvailableRestaurants(user.defaultAddress.lat, user.defaultAddress.lng);
//        });
//    }

//        addSubscribe( Observable.flatMapIterable()
//                .flatMapMaybe(index -> mDataManager.getStockIndex(index))
//                .flatMap(new Function<Single<ApiResponse<List<MarketIndexEntity>>>, Single<List<MarketIndexEntity>>>() {
//                    @Override
//                    public Single<List<MarketIndexEntity>> apply(Single<ApiResponse<List<MarketIndexEntity>>> s) throws Exception {
//                        return null;
//                    }
//                })
//        });
//                .compose(RxUtils.<MarketIndexEntity>rxSchedulerHelper())
//                .compose(RxUtils.<List<MarketIndexEntity>>handleResults())
//                .elementAt()
//                .subscribeWith(new CommonSubscriber<List<MarketIndexEntity>>(mView) {
//                    @Override
//                    public void onNext(List<MarketIndexEntity> itemCategory) {
//                        mView.showStockIndexes(itemCategory);
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
////                        ToastUtils.showLongToast("Can not load issues");
//                    }
//                })
//        );
//
//    }

    @Override
    public void getPrice(String stockCode) {
        addSubscribe(mDataManager.getStock(stockCode)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.showPrice(items.get(0));
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

}
