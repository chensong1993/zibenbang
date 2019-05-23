package com.zhiyi.chinaipo.presenters.search;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.search.SearchConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.SearchEvent;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;
import com.zhiyi.chinaipo.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchPresenter extends RxPresenter<SearchConnector.View> implements SearchConnector.Presenter {

    private DataManager mDataManager;
    private int currentType = Constants.SEARCHING_TYPE_ALL;
    private int currentPage = 1;

    @Inject
    public SearchPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(SearchConnector.View view) {
        super.attachView(view);
        registerEvent();
        registerTokenEvent();
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

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(SearchEvent.class)
                        .compose(RxUtils.<SearchEvent>rxSchedulerHelper())
//                .filter(new Predicate<SearchEvent>() {
//                    @Override
//                    public boolean test(@NonNull SearchEvent searchEvent) throws Exception {
//                        return searchEvent.getType() == currentType;
//                    }
//                })
                        .subscribeWith(new CommonSubscriber<SearchEvent>(mView, "搜索失败") {
                            @Override
                            public void onNext(SearchEvent s) {
                                currentType = s.getType();
                                mView.updateSearchContext(currentType, s.getQuery());
//                        if (currentType == Constants.SEARCHING_TYPE_ALL) {
//                            searchByTitle(s.getQuery(), currentPage);
//                            searchStockByKey(s.getQuery(), currentPage);
//                        } else if (currentType == Constants.SEARCHING_TYPE_AUTHOR ) {
//                            searchByAuthor(s.getQuery(), currentPage);
//                        } else if (currentType == Constants.SEARCHING_TYPE_AUTHOR ) {
//                            searchByTitle(s.getQuery(), currentPage);
//                        } else {
//                            searchStockByKey(s.getQuery(), currentPage);
//                        }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                registerEvent();
                            }
                        })
        );
    }

    @Override
    public void searchStockByKey(String stockCodeOrName, int pageOffset) {
        addSubscribe(Flowable.zip(mDataManager.getStockByCode(stockCodeOrName, pageOffset), mDataManager.getStockByName(stockCodeOrName, pageOffset),
                new BiFunction<ApiResponse<List<StockPriceEntity>>, ApiResponse<List<StockPriceEntity>>, List<StockPriceEntity>>() {
                    @Override
                    public List<StockPriceEntity> apply(ApiResponse<List<StockPriceEntity>> t1, ApiResponse<List<StockPriceEntity>> t2) throws Exception {
                        List<StockPriceEntity> allStocks = new ArrayList<>();
                        allStocks.addAll(t1.getResults());
                        allStocks.addAll(t2.getResults());
                        return allStocks;
                    }
                })
                .compose(RxUtils.<List<StockPriceEntity>>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> itemStock) {
                        mView.showStocks(itemStock);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err("已加载全部行情(｀・ω・´)");
                        e.printStackTrace();
                    }
                })

        );

    }

    @Override
    public void searchByTitle(String searchKey, int pageOffset) {
        addSubscribe(mDataManager.articlesByKey(searchKey, pageOffset)
                        .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                            @Override
                            public void onNext(List<ArticlesEntity> articlesItem) {
                                List<ArticlesEntity> mResults = articlesItem;
                                Timber.w("Got articles " + mResults);
                                mView.showArticles(articlesItem);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.err("已加载全部资讯(｀・ω・´)");
                                e.printStackTrace();

//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }

    @Override
    public void searchByAuthor(String authorName, int pageOffset) {
        addSubscribe(mDataManager.articlesByAuthor(authorName, pageOffset)
                        .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                            @Override
                            public void onNext(List<ArticlesEntity> articlesItem) {
                                List<ArticlesEntity> mResults = articlesItem;
                                Timber.w("Got articles " + mResults);
                                mView.showArticles(articlesItem);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.err("已加载全部作者(｀・ω・´) ");
                                e.printStackTrace();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }
}
