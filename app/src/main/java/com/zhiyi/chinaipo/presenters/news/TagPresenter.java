package com.zhiyi.chinaipo.presenters.news;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.CategoryConnector;
import com.zhiyi.chinaipo.base.connectors.news.TagConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.event.SearchEvent;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import timber.log.Timber;

public class TagPresenter extends RxPresenter<TagConnector.View> implements TagConnector.Presenter {

    private DataManager mDataManager;


    @Inject
    public TagPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public void getArticlesByTag(String tag, int pageOffset) {
        addSubscribe(mDataManager.articlesByTag(tag, pageOffset)
                        .compose(RxUtils.<ApiResponse<List<ArticlesEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<ArticlesEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<ArticlesEntity>>(mView) {
                            @Override
                            public void onNext(List<ArticlesEntity> articlesItem) {
                                List<ArticlesEntity> mResults = articlesItem;
                                Timber.w("Got articles " + mResults);
                                mView.showContent(articlesItem);
                            }
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                mView.showErr();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }
}
