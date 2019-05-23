package com.zhiyi.chinaipo.base.connectors.stocks;

import com.zhiyi.chinaipo.base.BasePresenter;
import com.zhiyi.chinaipo.base.BaseView;
import com.zhiyi.chinaipo.models.entity.details.ShareHoldersEntity;
import com.zhiyi.chinaipo.models.entity.details.ShareListEntity;

import java.util.List;

public interface ShareHoldersConnector {

    interface View extends BaseView {

        void showShareHolders(List<ShareHoldersEntity> mList);
        void showShareList(List<ShareListEntity> sList);
        void err();
    }

    interface Presenter extends BasePresenter<View> {

        void getShareList(String stockCode);
        void getTopShareHolders(String stockCode);

    }
}
