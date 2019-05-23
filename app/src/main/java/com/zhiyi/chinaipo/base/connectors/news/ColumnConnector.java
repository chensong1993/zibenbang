package com.zhiyi.chinaipo.base.connectors.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;

import java.util.List;


public interface ColumnConnector {

    interface View extends BaseView {
        // void showColumn(List<ColumnEntity> list);
        void showColumns(List<TopicEntity> list);

        void homeIndex(int index);

        void bannerIndex(int index);

        void err();
    }

    interface Presenter extends BasePresenter<View> {
        void checkPermissions(RxPermissions rxPermissions);

        //void getColumn(int page);
        void getColumns(int page);

    }
}
