package com.zhiyi.chinaipo.base.connectors.stocks;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;

import java.util.List;

public interface RelatedConnector {

    interface View extends BaseView {

        void showNews(List<ArticlesEntity> mList);
    }

    interface Presenter extends BasePresenter<View> {

        void getNewsFor( String stockcode, String stockname, int pageOffset);
    }
}
