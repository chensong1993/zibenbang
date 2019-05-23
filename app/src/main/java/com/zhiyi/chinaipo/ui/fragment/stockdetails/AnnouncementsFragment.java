package com.zhiyi.chinaipo.ui.fragment.stockdetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.zhiyi.chinaipo.base.connectors.stocks.AnnouncementsConnector;
import com.zhiyi.chinaipo.models.entity.details.AnnouncementEntity;
import com.zhiyi.chinaipo.presenters.stocks.AnnouncementsPresenter;
import com.zhiyi.chinaipo.ui.adapter.PdfAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * table详情页面的pdf布局
 */
public class AnnouncementsFragment extends BaseFragment<AnnouncementsPresenter> implements AnnouncementsConnector.View {
    private PdfAdapter mAnnounceAdapter;
    private List<AnnouncementEntity> mAnnouncements;
    private String mStockCode;
    private int pageOffset = 1;
    @BindView(R.id.smartre)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_zhongxinpdf)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_huadong)
    TextView mTvHuaDong;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhongxin_pdf;
    }

    @Override
    protected void initEventAndData() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mPresenter.getAnnouncements(mStockCode, pageOffset);
        init();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void init() {
        mAnnouncements = new ArrayList<>();
        mAnnounceAdapter = new PdfAdapter(getActivity(), mAnnouncements);
        mRecyclerView.setAdapter(mAnnounceAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(getActivity(), R.color.hui)));
        mRecyclerView.setNestedScrollingEnabled(false);
        setupRefresher();
    }

    //刷新加载
    private void setupRefresher() {
        //加载更多
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getAnnouncements(mStockCode, ++pageOffset);
                refreshlayout.finishLoadmore();
            }
        });
    }

    @Override
    public void showAnnouncements(List<AnnouncementEntity> announcements) {
        mTvReload.setVisibility(View.GONE);
        mAnnouncements = announcements;
        if (pageOffset == 1) {
            mAnnounceAdapter.add(announcements);
        } else {
            mAnnounceAdapter.addAll(announcements);
        }
        if (announcements.size() <= 4) {
            mTvHuaDong.setVisibility(View.VISIBLE);
        } else {
            mTvHuaDong.setVisibility(View.GONE);
        }
        LogUtil.i("showAnnouncements", announcements.size()+"");
    }
    @OnClick(R.id.tv_reload)
    void reload(){
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        mPresenter.getAnnouncements(mStockCode, pageOffset);
        init();
    }
    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        if(pageOffset==1){
            mTvReload.setVisibility(View.VISIBLE);
        }
        smartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

}
