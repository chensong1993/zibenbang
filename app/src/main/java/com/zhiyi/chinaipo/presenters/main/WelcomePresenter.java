package com.zhiyi.chinaipo.presenters.main;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.main.WelcomeConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.WelcomeEntity;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class WelcomePresenter extends RxPresenter<WelcomeConnector.View> implements WelcomeConnector.Presenter{

//    private static final String RES = "1080*1776";
    private static final int FRONT_AD_TYPE = 1;
    private static final int COUNT_DOWN_TIME = 2200;

    private DataManager mDataManager;

    @Inject
    public WelcomePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getWelcomeData() {
        addSubscribe(mDataManager.getWelcomeData()
                .compose(RxUtils.<ApiResponse<List<AdverticeEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<AdverticeEntity>>handleResults())
                .subscribe(new Consumer<List<AdverticeEntity>>() {
                    @Override
                    public void accept(List<AdverticeEntity> welcomeData) {
                        mView.showContent(welcomeData.get(0));
                        startCountDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                        mView.jumpToMain();
                    }
                })
        );
    }

    private void startCountDown() {
        addSubscribe(Flowable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.jumpToMain();
                    }
                })
        );
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        if (granted) {
                        } else {
                            mView.showErrorMsg("需要网络权限");
                        }
                    }
                })
        );
    }
}
