package com.zhiyi.chinaipo.base.connectors.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;

import java.util.List;


public interface CategoryConnector {

    interface View extends BaseView {
        void updateCategories(List<CategoryEntity> list);

        void updateCategoriesTwo(List<CategoryEntity> list);

        void updateTableTitle(int titleId);

        void indexNews(int index);

        void bannerImg(String img);
    }

    interface Presenter extends BasePresenter<View> {
        void checkPermissions(RxPermissions rxPermissions);

        void getCategories(int page);

        void getCategoriesTwo(int page);
    }
}
