package com.zhiyi.chinaipo.presenters.auth;

import android.util.Log;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.ProfileConnector;
import com.zhiyi.chinaipo.base.connectors.auth.ProfileEditorConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.auth.TokenEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent.AuthEventType;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ProfileEditorPresenter extends RxPresenter<ProfileEditorConnector.View> implements ProfileEditorConnector.Presenter {
    private DataManager mDataManager;

    @Inject
    public ProfileEditorPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(ProfileEditorConnector.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(AuthenticatedEvent.class)
                        .compose(RxUtils.<AuthenticatedEvent>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<AuthenticatedEvent>(mView, "认证操作失败") {
                            @Override
                            public void onNext(AuthenticatedEvent s) {
                                AuthEventType currentType = s.getAuthEvent();
                                switch (s.getAuthEvent()) {
                                    case LOGIN_SUCCEED:
//                                        mView.updateUserInfo();
                                        break;
                                    case LOGIN_FAILED:
                                    case LOGOUT:
//                                        mView.clearUserInfo();
                                        break;
                                    default:
                                        break;
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                registerEvent();
                            }
                        })
        );
    }


    @Override
    public void doLogout() {
        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.LOGOUT, "退出登录"));

//        addSubscribe(mDataManager.tryLogin()
//                .compose(RxUtils.<TokenEntity>rxSchedulerHelper())
//                .subscribe(new Consumer<TokenEntity>() {
//                    @Override
//                    public void accept(TokenEntity welcomeBean) {
//                        mView.inProcessing("开始登录");
//                        Log.d("LoginPresenter", "开始登录");
//                        startCountDown();
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) {
//                        mView.loginSuccess();
//                        Log.d("LoginPresenter", "尝试成功");
//                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.LOGIN_SUCCEED, "登录成功"));
//                    }
//                })
//        );
    }
}
