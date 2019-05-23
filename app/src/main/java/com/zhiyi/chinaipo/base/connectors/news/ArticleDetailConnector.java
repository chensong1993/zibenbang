package com.zhiyi.chinaipo.base.connectors.news;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;

public interface ArticleDetailConnector {

    interface View extends BaseView {
        void showNewsDetail(ArticleDetailEntity newsDetail);
        void err();
    }

    interface Presenter extends BasePresenter<View> {
        void getNewsDetail(int newsId, int originalId);
    }
}
