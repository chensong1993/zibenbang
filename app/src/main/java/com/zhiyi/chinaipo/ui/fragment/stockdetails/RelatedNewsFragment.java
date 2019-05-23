package com.zhiyi.chinaipo.ui.fragment.stockdetails;


import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.RelatedConnector;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.presenters.stocks.RelatedPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatedNewsFragment extends BaseFragment<RelatedPresenter> implements RelatedConnector.View {
    //下拉刷新
    @BindView(R.id.refresh_related_news)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_huadong)
    TextView mTvHuaDong;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    private NewAdapter mAdapter;
    private String mStockCode;
    private String mStockName;
    private List<ArticlesEntity> mNewsList;
    private int pageOffset = 1;

    @Override
    protected void initEventAndData() {
        initData();
    }


    private void initData() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mStockName = getArguments().getString(Constants.PARAMETER_STOCK_NAME, "阳光恒美");
        mPresenter.getNewsFor(mStockCode, mStockName, pageOffset);
        mNewsList = new ArrayList<>();
        mAdapter = new NewAdapter(mContext, mNewsList);
        mRecyclerView.setAdapter(mAdapter);
        //分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(getActivity(), R.color.hui)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        setupRefresh();

    }

    public void setupRefresh() {
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getNewsFor(mStockCode, mStockName, ++pageOffset);
                mSmartRefreshLayout.finishLoadmore();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yanbao;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showNews(List<ArticlesEntity> nList) {
        mTvReload.setVisibility(View.GONE);
        if (pageOffset == 1) {
            mAdapter.chengData(nList);
        } else {
            mAdapter.addAll(nList);
        }
        if (nList.size() <= 4 || nList == null) {
            mTvHuaDong.setVisibility(View.VISIBLE);
        } else {
            mTvHuaDong.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_reload)
    void reload() {
        initData();
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        if(pageOffset==1){
            mTvReload.setVisibility(View.VISIBLE);
        }
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }
}
