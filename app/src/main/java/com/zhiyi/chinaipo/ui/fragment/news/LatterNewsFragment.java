package com.zhiyi.chinaipo.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.news.LatterArticlesConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.news.NewsSave;
import com.zhiyi.chinaipo.models.event.BannerColorEvent;
import com.zhiyi.chinaipo.presenters.news.LatterArticlesPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 没有轮播图图片的新闻业页
 */
public class LatterNewsFragment extends BaseFragment<LatterArticlesPresenter> implements LatterArticlesConnector.View {

    //下拉刷新
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    @BindView(R.id.tv_news_content)
    TextView mTvNewsContent;
    private NewAdapter mAdapter;
    private List<ArticlesEntity> mList;
    private int categoryId;
    private int pageOffset = 1;
   // int netType;
    @BindView(R.id.tv_again_loading)
    TextView mTvAgainLoad;
    int indexs;
    LinearLayoutManager manager;
    //横向分割线
    private RecycleViewDivider recycleViewDivider;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEventAndData() {
        mRlProgress.setVisibility(View.VISIBLE);
        categoryId = getArguments().getInt(Constants.NEWS_CATEGORY_ID);
        mPresenter.getArticles(categoryId);
        mList = new ArrayList<>();
        mAdapter = new NewAdapter(getActivity(), mList);
        mRecyclerView.removeItemDecoration(recycleViewDivider);
        mRecyclerView.setAdapter(mAdapter);
        manager = new LinearLayoutManager(getActivity());
        recycleViewDivider = new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui));
        mRecyclerView.addItemDecoration(recycleViewDivider);
        mRecyclerView.setLayoutManager(manager);
        // autoLoadMore();
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getArticles(categoryId);
//                if (netType < 0) {
//                    ToastUtil.showToast(mContext, "当前网络不可用");
//                }
               // LogUtil.i("categoryId", categoryId + "");
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                mPresenter.getMoreArticles(categoryId, ++pageOffset);


            }
        });
    }


    @Override
    public void homeIndex(int index) {
        LogUtil.i("latterindex", "" + index);
        // if (index != 0 && index != 7 && index != 8) {
      //  mRecyclerView.scrollToPosition(0);

        //  }
    }

    @Override
    public void bannerIndex(int index) {
        Log.i("bannerIndex", "bannerIndex: " + index);
        if (index != 0) {
            RxBus.getDefault().post(new BannerColorEvent(null));
        }
    }

    private void changeReadState(ArticlesEntity newsEntity) {
        for (Iterator iter = App.getInstance().getNewsSaveDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
            NewsSave element = (NewsSave) iter.next();
            if (element.getNewId().equals(newsEntity.getOriginalId() + "")) {
                LogUtil.i("getCategoryId", element.getNewId());
                newsEntity.setRead(true);
            }
        }

    }

    private void autoLoadMore() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部,并且是向下滚动
                    if ((lastVisibleItem) == (totalItemCount - 1) && isSlidingToLast) {
                        mPresenter.getMoreArticles(categoryId, ++pageOffset);
                        LogUtil.i("autoMore", "autoMore");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向,dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示正在向右滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0表示停止或向左滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    @Override
    public void showContent(List<ArticlesEntity> list) {
        mSmartRefreshLayout.finishRefresh();
        stateMain();
        mAdapter.chengData(list);
        for (int i = 0; i < list.size(); i++) {
            changeReadState(list.get(i));

        }
        mTvAgainLoad.setVisibility(View.GONE);
        mTvNewsContent.setVisibility(View.GONE);
        mRlProgress.setVisibility(View.GONE);
        LogUtil.i("showAnnouncements", list.get(0).getTitle());

    }

    @OnClick(R.id.tv_news_content)
    void newsAgainLoad() {
        mPresenter.getArticles(categoryId);
    }

    @Override
    public void showMoreContent(List<ArticlesEntity> list) {
        mSmartRefreshLayout.finishLoadmore();
        mRlProgress.setVisibility(View.GONE);
        mTvAgainLoad.setVisibility(View.GONE);
        mTvNewsContent.setVisibility(View.GONE);
        mAdapter.addAll(list);
    }


//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        netType = netMobile;
//    }

    @Override
    public void showErr() {
        mSmartRefreshLayout.finishRefresh();
        mRlProgress.setVisibility(View.GONE);
        mTvAgainLoad.setVisibility(View.VISIBLE);
        mTvNewsContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void moreErr() {
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


}