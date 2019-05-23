package com.zhiyi.chinaipo.presenters.stocks;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.stocks.RelatedConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

public class RelatedPresenter extends RxPresenter<RelatedConnector.View> implements RelatedConnector.Presenter {

    private DataManager mDataManager;
    private static final int NUM_OF_PAGE = 20;

    private String queryStr = null;

    @Inject
    public RelatedPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    private void getSearchResultData() {
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
    public void getNewsFor(String stockcode, String stockname, int pageOffset) {

        addSubscribe(
                Flowable.zip(mDataManager.articlesByKey(stockcode, pageOffset), mDataManager.articlesByKey(stockname, pageOffset),
                        new BiFunction<ApiResponse<List<ArticlesEntity>>, ApiResponse<List<ArticlesEntity>>, List<ArticlesEntity>>() {
                            @Override
                            public List<ArticlesEntity> apply(ApiResponse<List<ArticlesEntity>> t1, ApiResponse<List<ArticlesEntity>> t2) throws Exception {
                                List<ArticlesEntity> allNews = new ArrayList<>();
                                allNews.addAll(t1.getResults());
                                allNews.addAll(t2.getResults());
                                return allNews;
                            }
                        })
                        .compose(RxUtils.<List<ArticlesEntity>>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                            @Override
                            public void onNext(List<ArticlesEntity> itemCategory) {
                                mView.showNews(itemCategory);
                            }
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.showErrorMsg("");
                            }
                        })

        );

    }
}
