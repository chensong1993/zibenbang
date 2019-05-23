package com.zhiyi.chinaipo.presenters.auth;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.PasswordChangeConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.util.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author chensong
 * @date 2018/3/27 11:50
 */
public class PasswordChangePresenter extends RxPresenter<PasswordChangeConnector.View> implements PasswordChangeConnector.Presenter {
    private DataManager mDataManager;

    @Inject
    public PasswordChangePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void passwordChenge(String token, String newpassword1, String newpassword2) {
        addSubscribe(mDataManager.passwordChange(token, newpassword1, newpassword2)
                .compose(RxUtils.<HashKeyEntity>rxSchedulerHelper())
                .subscribe(new Consumer<HashKeyEntity>() {
                    @Override
                    public void accept(HashKeyEntity captchaAuthEntity) {
                        mView.changeSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.changeErr();
                    }
                }));
    }

}