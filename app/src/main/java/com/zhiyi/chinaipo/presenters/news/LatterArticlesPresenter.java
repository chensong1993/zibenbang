package com.zhiyi.chinaipo.presenters.news;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.LatterArticlesConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.event.BannerIndexEvent;
import com.zhiyi.chinaipo.models.event.NewsIndex;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class LatterArticlesPresenter extends RxPresenter<LatterArticlesConnector.View> implements LatterArticlesConnector.Presenter {

    private DataManager mDataManager;
    private int currentPage = 1;
    @Inject
    public LatterArticlesPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(LatterArticlesConnector.View view) {
        super.attachView(view);
        getHomeIndex();
        bannerIndex();
    }

    @Override
    public void getArticles(int categoryId) {
        addSubscribe(mDataManager.getArticles(categoryId)
                .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView, false) {
                    @Override
                    public void onNext(List<ArticlesEntity> articlesItem) {
                        mView.showContent(articlesItem);
                        LogUtil.i("6666", "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showErr();
                        LogUtil.i("6666", "onNext123: ");
                    }
                })
        );
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
                      //  getHomeIndex();
                    }
                }));
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

}
