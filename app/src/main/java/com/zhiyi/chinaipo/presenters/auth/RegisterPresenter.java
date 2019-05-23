package com.zhiyi.chinaipo.presenters.auth;

import android.util.Log;

import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.RegisterConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.models.entity.auth.TokenEntity;
import com.zhiyi.chinaipo.models.entity.auth.captchaAuthEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * @author chensong
 * @date 2018/3/27 11:50
 */
public class RegisterPresenter extends RxPresenter<RegisterConnector.View> implements RegisterConnector.Presenter {
    private DataManager mDataManager;

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void goRegister(String userName, String Pwd, String confirmPwd) {
        //  startCountDown();
        addSubscribe(mDataManager.tryRegister(userName, Pwd, confirmPwd)
                .compose(RxUtils.<TokenEntity>rxSchedulerHelper())
                .subscribe(new Consumer<TokenEntity>() {
                    @Override
                    public void accept(TokenEntity tokenEntity) throws Exception {
                        mDataManager.updateLogin(userName, tokenEntity.getUserToken());
                        mView.inProcessing("开始注册");
                        updateUserInfo();
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthenticatedEvent.AuthEventType.LOGIN_SUCCEED, "登录成功"));
                        RxBus.getDefault().post(new AuthenticatedEvent("ok", AuthenticatedEvent.AuthEventType.USER_TOKEN, tokenEntity.getUserToken()));
                        mView.registerSuccess(tokenEntity.getUserToken());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.registerErr();
                    }
                }));
    }


    //图形验证码
    @Override
    public void getCaptcha() {
        addSubscribe(mDataManager.getCaptcha()
                .compose(RxUtils.<CaptchaEntity>rxSchedulerHelper())
                .subscribe(new Consumer<CaptchaEntity>() {
                    @Override
                    public void accept(CaptchaEntity captchaEntity) {
                        mView.getCaptcha(captchaEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.getCaptchaErr();
                    }
                })
        );
    }


    //图形验证码验证
    @Override
    public void captchaAuth(String response, String hashkey) {
        addSubscribe(mDataManager.captchaAuth(response, hashkey)
                .compose(RxUtils.<captchaAuthEntity>rxSchedulerHelper())
                .subscribe(new Consumer<captchaAuthEntity>() {
                    @Override
                    public void accept(captchaAuthEntity s) {
                        mView.captchaAuth(s.getStatus());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.captchaAuth("图形验证无法获取");
                    }
                }));
    }

    //验证短信验证码
    @Override
    public void smsCaptchaAuth(String response, String hashkey) {
        addSubscribe(mDataManager.smsCaptchaAuth(response, hashkey)
                .compose(RxUtils.<captchaAuthEntity>rxSchedulerHelper())
                .subscribe(new Consumer<captchaAuthEntity>() {
                    @Override
                    public void accept(captchaAuthEntity s) {
                        mView.smsCaptchaAuth(s.getStatus());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.smsCaptchaAuth("短信验证码输入错误");
                    }
                }));
    }

    //获取短信验证
    @Override
    public void smsCaptcha(String sms) {
        addSubscribe(mDataManager.smsCaptcha(sms)
                .compose(RxUtils.<HashKeyEntity>rxSchedulerHelper())
                .subscribe(new Consumer<HashKeyEntity>() {
                    @Override
                    public void accept(HashKeyEntity s) {
                        mView.smsCaptcha(s.getHashkey());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.smsCaptchaErr("短信验证无法获取");
                    }
                }));
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
                        Log.d("LoginPresenter", "获取用户信息");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("LoginPresenter", "获取用户信息失败: " + throwable.getMessage());
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
                        for (int it = 0; it < aLong / 1000; it++) {
                            stringBuilder.append(".");
                            mView.inProcessing(stringBuilder.toString());
                            Log.d("LoginPresenter", "尝试登录中" + aLong);
                        }

                    }
                })
        );
    }
}
