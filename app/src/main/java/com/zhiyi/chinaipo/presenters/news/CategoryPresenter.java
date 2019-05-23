package com.zhiyi.chinaipo.presenters.news;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.CategoryConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;
import com.zhiyi.chinaipo.models.event.BannerColorEvent;
import com.zhiyi.chinaipo.models.event.HomeIndex;
import com.zhiyi.chinaipo.models.event.TabTitle;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class CategoryPresenter extends RxPresenter<CategoryConnector.View> implements CategoryConnector.Presenter {

    private DataManager mDataManager;

    @Inject
    public CategoryPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(CategoryConnector.View view) {
        super.attachView(view);
        registerEvent();
        getHomeIndex();
       // bannerImg();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(TabTitle.class)
                .compose(RxUtils.<TabTitle>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TabTitle>(mView, "跳转失败") {
                    @Override
                    public void onNext(TabTitle s) {
                        mView.updateTableTitle(s.path);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent();
                    }
                })
        );
    }

    private void bannerImg() {
        addSubscribe(RxBus.getDefault().toFlowable(BannerColorEvent.class)
                .compose(RxUtils.<BannerColorEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BannerColorEvent>(mView, "") {
                    @Override
                    public void onNext(BannerColorEvent s) {
                        mView.bannerImg(s.getImg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        bannerImg();
                    }
                })
        );
    }

    public void getHomeIndex() {
        addSubscribe(RxBus.getDefault().toFlowable(HomeIndex.class)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HomeIndex>(mView, "") {
                    @Override
                    public void onNext(HomeIndex homeIndex) {
                        mView.indexNews(homeIndex.position);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtil.i("err", "onError123: ");
                        getHomeIndex();
                    }
                }));
    }

    @Override
    public void getCategories(int page) {
        addSubscribe(mDataManager.getCategorys(page)
                .compose(RxUtils.<ApiResponse<List<CategoryEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<CategoryEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<CategoryEntity>>(mView) {
                    @Override
                    public void onNext(List<CategoryEntity> indexesItem) {
                        CategoryEntity column = new CategoryEntity();
                        column.setId(0);
                        column.setName("推荐");
                        List<CategoryEntity> mResults = indexesItem;
                        mResults.add(0, column);
                        mView.updateCategories(mResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showErrorMsg("");
                    }
                })
        );
    }

    @Override
    public void getCategoriesTwo(int page) {
        addSubscribe(mDataManager.getCategorys(page)
                .compose(RxUtils.<ApiResponse<List<CategoryEntity>>>rxSchedulerHelper())
                .compose(RxUtils.<List<CategoryEntity>>handleResults())
                .subscribeWith(new CommonSubscriber<List<CategoryEntity>>(mView) {
                    @Override
                    public void onNext(List<CategoryEntity> indexesItem) {
                        List<CategoryEntity> mResults = indexesItem;
                        mView.updateCategoriesTwo(mResults);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }


    @Override
    public void checkPermissions(RxPermissions rxPermissions) {
        addSubscribe(rxPermissions.request(Manifest.permission.INTERNET)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean granted) {
                                if (granted) {
//                            mView.startDownloadService();
                                } else {
                                    mView.showErrorMsg("下载应用需要联网权限");
                                }
                            }
                        })
        );
    }


}
