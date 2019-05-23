package com.zhiyi.chinaipo.presenters.main;

import android.Manifest;

import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.main.MainConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.util.RxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MainPresenter extends RxPresenter<MainConnector.View> implements MainConnector.Presenter{

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void checkVersion(final String currentVersion) {
//        addSubscribe(mDataManager.fetchVersionInfo()
//                .compose(RxUtil.<MyHttpResponse<VersionBean>>rxSchedulerHelper())
//                .compose(RxUtil.<VersionBean>handleMyResult())
//                .filter(new Predicate<VersionBean>() {
//                    @Override
//                    public boolean test(@NonNull VersionBean versionBean) throws Exception {
//                        return Integer.valueOf(currentVersion.replace(".", "")) < Integer.valueOf(versionBean.getCode().replace(".", ""));
//                    }
//                })
//                .map(new Function<VersionBean, String>() {
//                    @Override
//                    public String apply(VersionBean bean) {
//                        StringBuilder content = new StringBuilder("版本号: v");
//                        content.append(bean.getCode());
//                        content.append("\r\n");
//                        content.append("版本大小: ");
//                        content.append(bean.getSize());
//                        content.append("\r\n");
//                        content.append("更新内容:\r\n");
//                        content.append(bean.getDes().replace("\\r\\n","\r\n"));
//                        return content.toString();
//                    }
//                })
//                .subscribeWith(new CommonSubscriber<String>(mView) {
//                    @Override
//                    public void onNext(String s) {
//                        mView.showUpdateDialog(s);
//                    }
//                })
//        );
    }

    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        if (granted) {
                            mView.startDownloadService();
                        } else {
                            mView.showErrorMsg("下载应用需要文件写入权限");
                        }
                    }
                })
        );
    }

}
