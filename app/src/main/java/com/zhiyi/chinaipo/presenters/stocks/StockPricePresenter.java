package com.zhiyi.chinaipo.presenters.stocks;

import android.util.Log;

import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.IndexConnector;
import com.zhiyi.chinaipo.base.connectors.stocks.StockPriceConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class StockPricePresenter extends RxPresenter<StockPriceConnector.View> implements StockPriceConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

//    private int currentPage = 1;
//    private String sortBy = Constants.STOCK_SORT_BY_CHNG_PCT;
//    private String sortOrder = Constants.STOCK_SORT_ORDER_ASC;

    @Inject
    public StockPricePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
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
    public void getPrices(String index, String sortBy, String sortOrder, int pageOffset) {
        addSubscribe(mDataManager.getStock(index, sortBy, sortOrder, pageOffset)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.showPrice(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getPricesWithExtra(String baseIndex, String sortBy, String order, String extraKey, String extraValue, int pageOffset) {
        Flowable<ApiResponse<List<StockPriceEntity>>> stockObserver;
        switch (extraKey) {
            case "investor":
                stockObserver = mDataManager.getStocksByInvestor(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            case "dealer":
                stockObserver = mDataManager.getStocksByDealer(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            case "lawyer":
                stockObserver = mDataManager.getStocksByLawyer(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            case "accountant":
                stockObserver = mDataManager.getStocksByAccountant(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            case "area":
                stockObserver = mDataManager.getStocksByArea(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            case "industry":
                stockObserver = mDataManager.getStocksByIndustry(baseIndex, sortBy, order, extraValue, pageOffset);
                break;
            default:
                return;
        }

        addSubscribe(stockObserver
                        .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                            @Override
                            public void onNext(List<StockPriceEntity> items) {
                                mView.showPrice(items);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                LogUtil.i("onError", "onError: ");
                                mView.err("暂无数据");
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }
}
