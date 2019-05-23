package com.zhiyi.chinaipo.ui.fragment.news;


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
import com.zhiyi.chinaipo.base.connectors.news.ColumnConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.event.BannerColorEvent;
import com.zhiyi.chinaipo.presenters.news.ColumnPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.TopicAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 专栏
 */
public class SpecialColumnFragment extends RootFragment<ColumnPresenter> implements ColumnConnector.View {

    //下拉刷新
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_new_detail)
    RecyclerView mRecyclerView;
    private TopicAdapter mTopicAdapter;
    private List<TopicEntity> mTopicEntityList;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    int categoryId;
    int netType;

    @Override
    protected void initEventAndData() {
        init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_special;
    }

    private void init() {
        LogUtil.i("inits", "加载");
        categoryId = getArguments().getInt(Constants.NEWS_CATEGORY_ID);
        mRlProgress.setVisibility(View.VISIBLE);
        mTopicEntityList = new ArrayList<>();
        mTopicAdapter = new TopicAdapter(getActivity(), mTopicEntityList, categoryId);
        mRecyclerView.setAdapter(mTopicAdapter);
        //分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.hui)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getColumns(1);
//                if (netType < 0) {
//                    ToastUtil.showToast(mContext, "当前网络不可用");
//                }
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmoreWithNoMoreData();
            }
        });
        mPresenter.getColumns(1);
    }

    @Override
    public void showColumns(List<TopicEntity> list) {
        mRlProgress.setVisibility(View.GONE);
        mTopicAdapter.chengeData(list);
        mSmartRefreshLayout.finishRefresh();
        LogUtil.i("showAnnouncements", list.size() + "");
    }

    @Override
    public void homeIndex(int index) {
//        if(index==8){
//            mPresenter.getColumns(1);
//        }


    }

    @Override
    public void bannerIndex(int index) {
        if (index != 0) {
            RxBus.getDefault().post(new BannerColorEvent(null));
        }
    }

    @Override
    public void err() {
        mSmartRefreshLayout.finishRefresh();
        mRlProgress.setVisibility(View.GONE);
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
