package com.zhiyi.chinaipo.presenters.auth;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.auth.AvatarConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AvatarPresenter extends RxPresenter<AvatarConnector.View> implements AvatarConnector.Presenter {
    private DataManager mDataManager;

    @Inject
    public AvatarPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


  /*  @Override
    public void attachView(AvatarConnector.View view) {
        super.attachView(view);
        registerThirdPartyEvent();
    }*/

   /* private void registerThirdPartyEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(AvatarFile.class)
                .compose(RxUtils.<AvatarFile>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<AvatarFile>(mView, "认证操作失败") {
                    @Override
                    public void onNext(AvatarFile s) {
                       mView.avatarFile(s.getmFile());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }*/
    @Override
    public void getAvatar(String token,MultipartBody.Part file) {
        addSubscribe(mDataManager.getAvatar(token,file)
                .compose(RxUtils.<HashKeyEntity>rxSchedulerHelper())
                .subscribe(new Consumer<HashKeyEntity>() {
                    @Override
                    public void accept(HashKeyEntity stringResult) {
                        mView.loginSuccess(stringResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtil.i("cuwucu", throwable.getLocalizedMessage());
                    }
                })
        );
    }

    @Override
    public void getAvatarUpload(String token, RequestBody file) {
        addSubscribe(mDataManager.avatarUpload(token,file)
                .compose(RxUtils.<HashKeyEntity>rxSchedulerHelper())
                .subscribe(new Consumer<HashKeyEntity>() {
                    @Override
                    public void accept(HashKeyEntity stringResult) {
                        mView.loginSuccess(stringResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        LogUtil.i("cuwucu", throwable.getLocalizedMessage());
                    }
                })
        );
    }

}
