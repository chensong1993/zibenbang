package com.zhiyi.chinaipo.ui.activity.datas;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.datas.IndustryConnector;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.presenters.datas.IndustryPresenter;
import com.zhiyi.chinaipo.ui.adapter.datas.IndustryAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 数据模块 点击行业统计的头部进入的界面
 */
public class IndustryListActivity extends BaseActivity<IndustryPresenter> implements IndustryConnector.View {

    @BindView(R.id.smart)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_Ahangye)
    RecyclerView mHangye;
    @BindView(R.id.rl_back)
    RelativeLayout mRLBack;
    @BindView(R.id.tv_title)
    TextView mTitle;
    IndustryAdapter mIndustryAdapter;
    private List<StasIndustryEntity> mIndustries;
    private int pageOffset = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_hangye;
    }

    @Override
    protected void initEventAndData() {
        init();
        setupRefresh();
        mPresenter.getIndustries(pageOffset);
    }

    private void init() {
        mIndustries = new ArrayList<>();
        mIndustryAdapter=new IndustryAdapter(this,mIndustries, Constants.STOCK_DATA_LIST_CONTENT_ALL);
        mHangye.setAdapter(mIndustryAdapter);
        mHangye.setLayoutManager(new LinearLayoutManager(this));
        mHangye.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(this, R.color.hui)));
        mTitle.setText(R.string.x_hangye_tongji);

    }

    @OnClick(R.id.rl_back)
    void Back() {
        finish();
    }


    public void setupRefresh() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageOffset = 1;
                mPresenter.getIndustries(pageOffset);
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getIndustries(++pageOffset);
            }
        });
    }

    @Override
    public void showIndustry(List<StasIndustryEntity> topIndustry) {
        mIndustries = topIndustry;
        if (pageOffset == 1) {
            mIndustryAdapter.add(mIndustries);
        } else {
            mIndustryAdapter.addAll(mIndustries);
        }
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmore();
    }

    @Override
    public void noContent() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
