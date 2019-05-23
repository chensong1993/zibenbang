package com.zhiyi.chinaipo.base.connectors.news;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;

import java.util.List;


public interface TopicConnector {

    interface View extends BaseView {
        void showTopic(List<TopicEntity> list);

        void showTopicMore(List<TopicEntity> list);

        void homeIndex(int index);

        void bannerIndex(int index);

        void err();

        void moreErr();
    }

    interface Presenter extends BasePresenter<View> {
        void checkPermissions(RxPermissions rxPermissions);

        void getTopic(int page);

        void getTopicMore(int page);
    }
}
