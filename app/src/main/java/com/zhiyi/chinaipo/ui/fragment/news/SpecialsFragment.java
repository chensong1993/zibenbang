package com.zhiyi.chinaipo.ui.fragment.news;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RootFragment;
import com.zhiyi.chinaipo.base.connectors.news.TopicConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.event.BannerColorEvent;
import com.zhiyi.chinaipo.presenters.news.TopicPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.TopicAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 专题界面
 */
public class SpecialsFragment extends RootFragment<TopicPresenter> implements TopicConnector.View {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_new_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    private TopicAdapter mTopicAdapter;
    private List<TopicEntity> mTopicEntityList;
    private int page = 1;
    int categoryId;
    int netType, moreErr;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhuanti;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        categoryId = getArguments().getInt(Constants.NEWS_CATEGORY_ID);
        mRlProgress.setVisibility(View.VISIBLE);
        mTopicEntityList = new ArrayList<>();
        mTopicAdapter = new TopicAdapter(getActivity(), mTopicEntityList, categoryId);
        mRecyclerView.setAdapter(mTopicAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (netType < 0) {
//                    ToastUtil.showToast(mContext, "当前网络不可用");
//                }
                mPresenter.getTopic(page);
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getTopicMore(++page);


            }
        });
        mPresenter.getTopic(1);
    }


    @Override
    public void homeIndex(int index) {
//        if (index == 7) {
//            mPresenter.getTopic(page);
//        }


    }

    @Override
    public void bannerIndex(int index) {
        if (index != 0) {
            RxBus.getDefault().post(new BannerColorEvent(null));
        }
    }

    @Override
    public void showTopic(List<TopicEntity> list) {
        mSmartRefreshLayout.finishRefresh();
        mRlProgress.setVisibility(View.GONE);
        mTopicEntityList = list;
        mTopicAdapter.chengeData(mTopicEntityList);
        LogUtil.i("top", "ok");
    }

    @Override
    public void showTopicMore(List<TopicEntity> list) {
        mSmartRefreshLayout.finishLoadmore();
        mRlProgress.setVisibility(View.GONE);
        mTopicEntityList = list;
        mTopicAdapter.addAll(mTopicEntityList);
    }


    @Override
    public void err() {
        mSmartRefreshLayout.finishRefresh();
        mRlProgress.setVisibility(View.GONE);
        //ToastUtil.showToast(getActivity(), "暂无数据");
    }

    @Override
    public void moreErr() {
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        netType = netMobile;
//
//    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
}
