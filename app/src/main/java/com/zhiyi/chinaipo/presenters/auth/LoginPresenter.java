package com.zhiyi.chinaipo.presenters.auth;

import android.util.Log;

import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.LoginConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.auth.TokenEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent.AuthEventType;

import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import org.apache.http.auth.AUTH;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

public class LoginPresenter extends RxPresenter<LoginConnector.View> implements LoginConnector.Presenter {
    private DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void doLogin(String username, String password) {
        startCountDown();
        addSubscribe(mDataManager.tryLogin(username, password)
                .compose(RxUtils.<TokenEntity>rxSchedulerHelper())
                .subscribe(new Consumer<TokenEntity>() {
                    @Override
                    public void accept(TokenEntity gotToken) {
                        mDataManager.updateLogin(username, gotToken.getUserToken());
                        mView.inProcessing("开始登录");
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.LOGIN_SUCCEED, "登录成功"));
                        RxBus.getDefault().post(new AuthenticatedEvent("ok",AuthEventType.USER_TOKEN,gotToken.getUserToken()));
                        updateUserInfo();
                        mView.loginSuccess();
                        Log.d("LoginPresenter", "开始登录");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.loginFailed();
                        Log.d("LoginPresenter", "尝试失败: " + throwable.getMessage());
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.LOGIN_FAILED, "登录成功"));
                    }
                })
        );
    }

    private void updateUserInfo() {
        addSubscribe(mDataManager.getUserInfo()
                .compose(RxUtils.<UserEntity>rxSchedulerHelper())
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity gotUser) {
                        UserEntity userInfo = gotUser;
                        mDataManager.updateUserPrefs(userInfo);
                        mView.inProcessing("获取用户信息成功");
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.USER_INFO_SUCCEED, "登录成功"));
                        Log.d("LoginPresenter", "获取用户信息");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("LoginPresenter", "获取用户信息失败: " + throwable.getMessage());
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.USER_INFO_FAILED, "登录成功"));
                    }
                })
        );
    }

    private void startCountDown() {
        addSubscribe(Flowable.timer(Constants.AUTH_COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        StringBuilder stringBuilder = new StringBuilder().append("尝试登录中");
                        for (int it = 0; it < aLong / 1000; it ++ )
                          stringBuilder.append(".");
                        mView.inProcessing(stringBuilder.toString());
                        Log.d("LoginPresenter", "尝试登录中" + aLong);
                    }
                })
        );
    }
}
