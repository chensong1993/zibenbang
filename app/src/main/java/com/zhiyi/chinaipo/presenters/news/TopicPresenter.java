package com.zhiyi.chinaipo.presenters.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.TopicConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.event.BannerIndexEvent;
import com.zhiyi.chinaipo.models.event.NewsIndex;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class TopicPresenter extends RxPresenter<TopicConnector.View> implements TopicConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public TopicPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(TopicConnector.View view) {
        super.attachView(view);
        getHomeIndex();
        bannerIndex();
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {

    }

    @Override
    public void getTopic(int page) {
        addSubscribe(mDataManager.getTopics(page)
                .compose(RxUtils.<ApiResponse<List<TopicEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<TopicEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<TopicEntity>>(mView) {
                    @Override
                    public void onNext(List<TopicEntity> articlesItem) {
                        List<TopicEntity> mResults = articlesItem;
                        Timber.w("Got articles " + mResults);
                        mView.showTopic(mResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err();
                        LogUtil.i("top", "err");
                        e.printStackTrace();
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

    @Override
    public void getTopicMore(int page) {
        addSubscribe(mDataManager.getTopics(page)
                .compose(RxUtils.<ApiResponse<List<TopicEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<TopicEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<TopicEntity>>(mView) {
                    @Override
                    public void onNext(List<TopicEntity> articlesItem) {
                        List<TopicEntity> mResults = articlesItem;
                        Timber.w("Got articles " + mResults);
                        mView.showTopicMore(mResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.moreErr();
                        LogUtil.i("top", "err");
                        e.printStackTrace();
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

}
