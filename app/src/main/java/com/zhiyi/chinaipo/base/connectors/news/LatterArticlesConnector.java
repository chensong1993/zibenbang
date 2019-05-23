package com.zhiyi.chinaipo.base.connectors.news;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;

import java.util.List;


public interface LatterArticlesConnector {

    interface View extends BaseView {

        void showContent(List<ArticlesEntity> list);

        void showMoreContent(List<ArticlesEntity> list);

        void homeIndex(int index);

        void bannerIndex(int index);

        void showErr();

        void moreErr();

    }

    interface Presenter extends BasePresenter<View> {

        void getArticles(int categoryId);

        void getMoreArticles(int categoryId, int pageOffset);


    }
}
