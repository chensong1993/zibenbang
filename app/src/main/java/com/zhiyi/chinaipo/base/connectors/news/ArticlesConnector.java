package com.zhiyi.chinaipo.base.connectors.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;

import java.util.List;


public interface ArticlesConnector {

    interface View extends BaseView {

        void showBanner(List<BannerEntity> banners);

        void showMarketTops(List<IndexSCEntity> topMarkets);

        void showContent(List<ArticlesEntity> list);


        void showMoreContent(List<ArticlesEntity> list);

        void showStockIndex(List<MarketIndexEntity> stockIndexes);

        void showColumn(List<ColumnEntity> columnEntities);

        void showTopic(List<TopicEntity> mTopicEntityList);

        void homeIndex(int index);

        void bannerIndex(int index);

        void showErr();

        void moreErr();

    }

    interface Presenter extends BasePresenter<View> {

        void getArticles(int categoryId);

        void getMoreArticles(int categoryId, int pageOffset);

        void getBanner();

        void getIndexSC();

        void getStockIndex();

        void checkPermissions(RxPermissions rxPermissions);

        void getNewsColumn(String name, int id);

        void getNewsColumnTitle(int page);

        void getSpecialTopic(int topic, int page);

        void getTopic(int page);
    }
}
