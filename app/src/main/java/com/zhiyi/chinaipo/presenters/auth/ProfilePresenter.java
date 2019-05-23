package com.zhiyi.chinaipo.presenters.auth;

import android.util.Log;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.ProfileConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.H3;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.WeatherEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent.AuthEventType;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ProfilePresenter extends RxPresenter<ProfileConnector.View> implements ProfileConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public ProfilePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(ProfileConnector.View view) {
        super.attachView(view);
        registerEvent();
        registerThirdPartyEvent();
    }

    private void registerThirdPartyEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(EventLogin.class)
                .compose(RxUtils.<EventLogin>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<EventLogin>(mView, "认证操作失败") {
                    @Override
                    public void onNext(EventLogin s) {
                        mView.updateThirdPartyUser(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(AuthenticatedEvent.class)
                .compose(RxUtils.<AuthenticatedEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<AuthenticatedEvent>(mView, "认证操作失败") {
                    @Override
                    public void onNext(AuthenticatedEvent s) {
                        AuthEventType currentType = s.getAuthEvent();
                        switch (currentType) {
                            case LOGIN_SUCCEED:
                                updateProfile();
                                break;
                            case LOGIN_FAILED:
                                mDataManager.logout();
                            case LOGOUT:
                                mView.clearUserInfo();
                                break;
                            case USER_TOKEN:
                                mView.userToken(s.getUserToken());
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                })
        );
    }


    @Override
    public void userCode(String stockCode) {
        addSubscribe(mDataManager.getStock(stockCode)
                .compose(RxUtils.<ApiResponse<List<StockPriceEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<StockPriceEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<StockPriceEntity>>(mView) {
                    @Override
                    public void onNext(List<StockPriceEntity> items) {
                        mView.UserFollowed(items);
                        LogUtil.i("eeeeeeee", stockCode);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorMsg("err");
                        e.printStackTrace();
                    }
                })
        );
    }

    @Override
    public void updateProfile() {
        addSubscribe(mDataManager.getUserInfo()
                .compose(RxUtils.<UserEntity>rxSchedulerHelper())
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity gotUser) {
                        UserEntity userInfo = gotUser;
                        mView.updateUserInfo(userInfo);
                        mDataManager.updateUserPrefs(userInfo);
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.USER_INFO_SUCCEED, "登录成功"));
                        Log.d("LoginPresenter", "获取用户信息");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d("LoginPresenter", "获取用户信息失败: " + throwable.getMessage());
                        RxBus.getDefault().post(new AuthenticatedEvent(AuthEventType.USER_INFO_FAILED, "登录失败"));
                    }
                })
        );
    }

    @Override
    public void weaherData(String area) {
        addSubscribe(mDataManager.weatherData("dd56f549bf22478498843967e8083884",area)
                .compose(RxUtils.<WeatherEntity<H3>>rxSchedulerHelper())
                .compose(RxUtils.<H3>handleResultss())
                .subscribeWith(new CommonSubscriber<H3>(mView) {
                    @Override
                    public void onNext(H3 items) {
                      mView.weaherData(items);
                        LogUtil.i("WeatherEntity", "sdasdasdasdasdsadsa");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorMsg("werr");
                        LogUtil.i("WeatherEntity", "errrrrrrrrrr");
                    }
                })
        );
    }


}
