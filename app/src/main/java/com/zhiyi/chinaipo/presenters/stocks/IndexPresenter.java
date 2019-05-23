package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.IndexConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.HomeIndex;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class IndexPresenter extends RxPresenter<IndexConnector.View> implements IndexConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;
    private String queryStr = null;

    @Inject
    public IndexPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(IndexConnector.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(HomeIndex.class)
                .compose(RxUtils.<HomeIndex>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomeIndex>(mView, "跳转失败") {
                    @Override
                    public void onNext(HomeIndex s) {
                        mView.homeIndex(s.getPosition());
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

    @Override
    public void getStockIndex() {


        List<String> indexes = Arrays.asList(
                "三板成指",
                "三板做市");

        addSubscribe(
                Single.zip(mDataManager.getStockIndex("三板成指"), mDataManager.getStockIndex("三板做市"),
                        new BiFunction<ApiResponse<List<MarketIndexEntity>>, ApiResponse<List<MarketIndexEntity>>, List<MarketIndexEntity>>() {
                            @Override
                            public List<MarketIndexEntity> apply(ApiResponse<List<MarketIndexEntity>> t1, ApiResponse<List<MarketIndexEntity>> t2) throws Exception {
                                List<MarketIndexEntity> topIndex = new ArrayList<>();
                                topIndex.add(t1.getResults().get(0));
                                topIndex.add(t2.getResults().get(0));
                                return topIndex;
                            }
                        })
                        .toFlowable()
                        .compose(RxUtils.<List<MarketIndexEntity>>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<List<MarketIndexEntity>>(mView) {
                            @Override
                            public void onNext(List<MarketIndexEntity> itemCategory) {
                                mView.showStockIndexes(itemCategory);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.err();
                                e.printStackTrace();
                            }
                        })

        );

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

    }

    @Override
    public void getPriceByChange(String index) {
        addSubscribe(mDataManager.getStock(index, Constants.STOCK_SORT_BY_CHNG_PCT, 1)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.showPriceByChange(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getPriceByVolume(String index) {
        addSubscribe(mDataManager.getStock(index, Constants.STOCK_SORT_BY_LATEST_VOLUME, 1)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.showPriceByVolume(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getPriceByAmount(String index) {
        addSubscribe(mDataManager.getStock(index, Constants.STOCK_SORT_BY_LATEST_TURNOVER, 1)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.showPriceByAmount(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err();
                        e.printStackTrace();
                    }
                })
        );
    }
}
