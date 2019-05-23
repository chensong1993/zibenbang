package com.zhiyi.chinaipo.base.connectors.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;

import java.util.List;


public interface TagConnector {

    interface View extends BaseView {

        void showContent(List<ArticlesEntity> list);
        void showErr();
    }

    interface Presenter extends BasePresenter<View> {

        void getArticlesByTag(String tag, int pageOffset);

    }
}
