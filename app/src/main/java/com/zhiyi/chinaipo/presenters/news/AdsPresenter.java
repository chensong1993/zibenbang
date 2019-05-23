package com.zhiyi.chinaipo.presenters.news;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.AdsConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.WeatherEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import javax.inject.Inject;

public class AdsPresenter extends RxPresenter<AdsConnector.View> implements AdsConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public AdsPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getAds() {
        addSubscribe(mDataManager.getAds()
                .compose(RxUtils.<WeatherEntity<AdverticeEntity>>rxSchedulerHelper())
                .compose(RxUtils.<AdverticeEntity>handleResultss())
                .subscribeWith(new CommonSubscriber<AdverticeEntity>(mView) {
                    @Override
                    public void onNext(AdverticeEntity AdsItem) {
                        mView.Ads(AdsItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.err();
                        e.printStackTrace();
                    }
                })
        );
    }
}
