package com.zhiyi.chinaipo.base.connectors.main;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;

public interface LikeConnector {

    interface View extends BaseView {

//        void showContent(List<RealmLikeBean> mList);
    }

    interface Presenter extends BasePresenter<View> {

        void getLikeData();

        void deleteLikeData(String id);

        void changeLikeTime(String id, long time, boolean isPlus);

        boolean getLikePoint();

        void setLikePoint(boolean b);
    }
}
