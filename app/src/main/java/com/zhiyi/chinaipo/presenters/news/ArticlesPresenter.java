package com.zhiyi.chinaipo.presenters.news;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.ArticlesConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.event.BannerIndexEvent;
import com.zhiyi.chinaipo.models.event.NewsIndex;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class ArticlesPresenter extends RxPresenter<ArticlesConnector.View> implements ArticlesConnector.Presenter {

    private DataManager mDataManager;

    public static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;

    @Inject
    public ArticlesPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(ArticlesConnector.View view) {
        super.attachView(view);
        getHomeIndex();
       // bannerIndex();
    }

    @Override
    public void getArticles(int categoryId) {
        currentPage = 1;
        addSubscribe(mDataManager.getArticles(categoryId)
                        .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                            @Override
                            public void onNext(List<ArticlesEntity> articlesItem) {
                                mView.showContent(articlesItem);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.showErr();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }

    public void getHomeIndex() {
        addSubscribe(RxBus.getDefault().toFlowable(NewsIndex.class)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<NewsIndex>(mView, "") {
                    @Override
                    public void onNext(NewsIndex homeIndex) {
                        mView.homeIndex(homeIndex.getIndex());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.i("err", "onError123: ");
                        getHomeIndex();
                    }
                }));
    }

    public void bannerIndex() {
        addSubscribe(RxBus.getDefault().toFlowable(BannerIndexEvent.class)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BannerIndexEvent>(mView, "") {
                    @Override
                    public void onNext(BannerIndexEvent homeIndex) {
                        mView.bannerIndex(homeIndex.getIndex());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

    private String parseId(String str) {
        int idEnd = str.indexOf("#");
        return str.substring(3, idEnd);
    }

    private String parseTime(String str) {
        int timeEnd = str.indexOf("  •");
        if (timeEnd == -1) {
            return str;
        }
        return str.substring(0, timeEnd);
    }

    public static String parseImg(String str) {
        return "http:" + str;
    }

    @Override
    public void getMoreArticles(int categoryId, int pageOffset) {
        addSubscribe(mDataManager.getMoreArticles(categoryId, ++currentPage)
                .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView, false) {
                    @Override
                    public void onNext(List<ArticlesEntity> articlesItem) {
                        mView.showMoreContent(articlesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.moreErr();
                    }
                })
        );
    }

    @Override
    public void getBanner() {
        addSubscribe(mDataManager.getBanners()
                .compose(RxUtils.<ApiResponse<List<BannerEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BannerEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<BannerEntity>>(mView) {
                    @Override
                    public void onNext(List<BannerEntity> bannersItem) {
                        mView.showBanner(bannersItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getIndexSC() {
        addSubscribe(mDataManager.getIndexSC()
                .compose(RxUtils.<ApiResponse<List<IndexSCEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<IndexSCEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<IndexSCEntity>>(mView) {
                    @Override
                    public void onNext(List<IndexSCEntity> indexesItem) {
                        mView.showMarketTops(indexesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }


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
                            public void onNext(List<MarketIndexEntity> stockIndexes) {
                                mView.showStockIndex(stockIndexes);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.showErr();
                                e.printStackTrace();
                            }
                        })

        );
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.INTERNET)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) {
                                if (granted) {
//                            mView.startDownloadService();
                                } else {
                                    mView.showErrorMsg("下载应用需要联网权限");
                                }
                            }
                        })
        );
    }

    @Override
    public void getNewsColumn(String name, int id) {
        addSubscribe(mDataManager.getColumns(name, id)
                .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView, false) {
                    @Override
                    public void onNext(List<ArticlesEntity> articlesItem) {
                        mView.showContent(articlesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getNewsColumnTitle(int page) {
        addSubscribe(mDataManager.getColumns(page)
                .compose(RxUtils.<ApiResponse<List<ColumnEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ColumnEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ColumnEntity>>(mView) {
                    @Override
                    public void onNext(List<ColumnEntity> articlesItem) {
                        mView.showColumn(articlesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getSpecialTopic(int topic, int page) {
        addSubscribe(mDataManager.articlesByTopic(topic, page)
                .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                    @Override
                    public void onNext(List<ArticlesEntity> articlesItem) {

                        mView.showContent(articlesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void getTopic(int page) {
        addSubscribe(mDataManager.getTopics(page)
                .compose(RxUtils.<ApiResponse<List<TopicEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<TopicEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<TopicEntity>>(mView) {
                    @Override
                    public void onNext(List<TopicEntity> articlesItem) {
                       /* List<TopicEntity> mResults = articlesItem;
                        Timber.w("Got articles " + mResults);*/
                        mView.showTopic(articlesItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErr();
                        e.printStackTrace();
                    }
                })
        );
    }
}
