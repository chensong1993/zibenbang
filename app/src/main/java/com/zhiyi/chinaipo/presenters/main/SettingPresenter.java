package com.zhiyi.chinaipo.presenters.main;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.main.SettingConnector;
import com.zhiyi.chinaipo.models.DataManager;

import javax.inject.Inject;


public class SettingPresenter extends RxPresenter<SettingConnector.View> implements SettingConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public SettingPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void checkVersion(final String currentVersion) {
//        addSubscribe(mDataManager.fetchVersionInfo()
//                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
//                .compose(RxUtil.<VersionBean>handleMyResult())
//                .subscribeWith(new CommonSubscriber<VersionBean>(mView, "获取版本信息失败 T T") {
//                    @Override
//                    public void onNext(VersionBean versionBean) {
//                        if (Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""))) {
//                            mView.showUpdateDialog(versionBean);
//                        } else {
//                            mView.showErrorMsg("已经是最新版本~");
//                        }
//                    }
//                })
//        );
    }

    @Override
    public void setNoImageState(boolean b) {
        mDataManager.setNoImageState(b);
    }

    @Override
    public void setAutoCacheState(boolean b) {
        mDataManager.setAutoCacheState(b);
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }
}
