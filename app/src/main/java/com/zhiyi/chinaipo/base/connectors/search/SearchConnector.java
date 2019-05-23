package com.zhiyi.chinaipo.base.connectors.search;


import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;

import java.util.List;

public interface SearchConnector {

    interface View extends BaseView {
        void showStocks(List<StockPriceEntity> stockIndexes);
        void showArticles(List<ArticlesEntity> newsList);
        void updateSearchContext(int searchType, String searchString);
        void userToken(String userToken);
        void err(String err);
    }

    interface Presenter extends BasePresenter<View> {

        void searchStockByKey(String stockCodeOrName, int pageOffset);
        void searchByTitle(String searchKey, int pageOffset);
        void searchByAuthor(String searchKey, int pageOffset);

    }
}
