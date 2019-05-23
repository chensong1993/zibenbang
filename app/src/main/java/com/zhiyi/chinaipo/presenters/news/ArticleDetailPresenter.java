package com.zhiyi.chinaipo.presenters.news;

import com.zhiyi.chinaipo.base.RxPresenter;
import com.zhiyi.chinaipo.base.connectors.news.ArticleDetailConnector;
import com.zhiyi.chinaipo.models.DataManager;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.ui.widget.CommonSubscriber;
import com.zhiyi.chinaipo.util.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class ArticleDetailPresenter extends RxPresenter<ArticleDetailConnector.View> implements ArticleDetailConnector.Presenter {

    private DataManager mDataManager;

    public static final int NUM_OF_PAGE = 20;

    private int newsId = 1;
    private int originalId = 1;

    @Inject
    public ArticleDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(ArticleDetailConnector.View view) {
        super.attachView(view);
    }


    //    @Override
//    public void getContent(String type) {
//        addSubscribe(Flowable
//                .just(VtexApis.TAB_HOST + type)
//                .subscribeOn(Schedulers.io())
//                .map(new Function<String, Document>() {
//                    @Override
//                    public Document apply(String s) {
//                        try {
//                            return Jsoup.connect(s).timeout(10000).get();
//                        } catch (IOException e) {
//                            LogUtil.d(e.toString());
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                })
//                .filter(new Predicate<Document>() {
//                    @Override
//                    public boolean test(@NonNull Document document) throws Exception {
//                        return document != null;
//                    }
//                })
//                .map(new Function<Document, List<TopicListBean>>() {
//                    @Override
//                    public List<TopicListBean> apply(Document doc) {
//                        List<TopicListBean> mList = new ArrayList<>();
//                        Elements itemElements = doc.select("div.cell.item");    //item根节点
//                        int count = itemElements.size();
//                        for (int i = 0; i < count; i++) {
//                            Elements titleElements = itemElements.get(i).select("div.cell.item table tr td span.item_title > a");   //标题
//                            Elements imgElements = itemElements.get(i).select("div.cell.item table tr td img.avatar");              //头像
//                            Elements nodeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade a.node");    //节点
//                            Elements commentElements = itemElements.get(i).select("div.cell.item table tr a.count_livid");          //评论数
//                            Elements nameElements = itemElements.get(i).select("div.cell.item table tr span.small.fade strong a");  //作者 & 最后回复
//                            Elements timeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade");           //更新时间
//
//                            TopicListBean bean = new TopicListBean();
//
//                            if (titleElements.size() > 0) {
//                                bean.setTitle(titleElements.get(0).text());
//                                bean.setTopicId(parseId(titleElements.get(0).attr("href")));
//                            }
//                            if (imgElements.size() > 0) {
//                                bean.setImgUrl(parseImg(imgElements.get(0).attr("src")));
//                            }
//                            if (nodeElements.size() > 0) {
//                                bean.setNode(nodeElements.get(0).text());
//                            }
//                            if (nameElements.size() > 0) {
//                                bean.setName(nameElements.get(0).text());
//                            }
//                            //存在没有 最后回复者、评论数、更新时间的情况
//                            if (nameElements.size() > 1) {
//                                bean.setLastUser(nameElements.get(1).text());
//                            }
//                            if (commentElements.size() > 0) {
//                                bean.setCommentNum(Integer.valueOf(commentElements.get(0).text()));
//                            }
//                            if (timeElements.size() > 1) {
//                                bean.setUpdateTime(parseTime(timeElements.get(1).text()));
//                            }
//
//                            mList.add(bean);
//                        }
//                        return mList;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new CommonSubscriber<List<TopicListBean>>(mView) {
//                    @Override
//                    public void onNext(List<TopicListBean> mList) {
//                        mView.showContent(mList);
//                    }
//                })
//        );
//    }


    @Override
    public void getNewsDetail(int newsId, int originalId) {
        addSubscribe(mDataManager.getArticle(newsId, originalId)
                        .compose(RxUtils.<ApiResponse<List<ArticleDetailEntity>>>rxSchedulerHelper())
                        .compose(RxUtils.<List<ArticleDetailEntity>>handleResults())
                        .subscribeWith(new CommonSubscriber<List<ArticleDetailEntity>>(mView, false) {
                            @Override
                            public void onNext(List<ArticleDetailEntity> articlesItem) {
                                if (articlesItem.size() > 0) {
                                    mView.showNewsDetail(articlesItem.get(0));
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                mView.err();
                                e.printStackTrace();
//                        ToastUtils.showLongToast("Can not load issues");
                            }
                        })
        );
    }

}
