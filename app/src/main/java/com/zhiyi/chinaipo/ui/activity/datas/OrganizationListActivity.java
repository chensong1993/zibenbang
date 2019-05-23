package com.zhiyi.chinaipo.ui.activity.datas;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.datas.OrganizationConnector;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.presenters.datas.OrganizationPresenter;
import com.zhiyi.chinaipo.ui.adapter.OrganizationAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @note 会计头部点击进入的界面
 */
public class OrganizationListActivity extends BaseActivity<OrganizationPresenter> implements OrganizationConnector.View {

    @BindView(R.id.smart)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.rv_kuaiji)
    RecyclerView mZhuban;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_name)
    TextView mSectionTitle;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;

    private String mOrganizationType;
    private List<StasOrgsEntity> mOrgs;
    private OrganizationAdapter mOrgAdapter;
    private int pageOffset = 1;
    private String mTitleString;

    @Override
    protected int getLayout() {
        return R.layout.activity_kuaiji;
    }

    @Override
    protected void initEventAndData() {

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.LIST_ORGANIZATION_BY_TYPE))) {
            mOrganizationType = getIntent().getStringExtra(Constants.LIST_ORGANIZATION_BY_TYPE);
            init();
            mPresenter.getOrganizations(mOrganizationType, pageOffset);
            if (mOrganizationType.equals("investor")) {
                mTitleString = getString(R.string.zhubanquanshang_paihang);
            } else if (mOrganizationType.equals("dealer")) {
                mTitleString = getString(R.string.zuoshishang_paihang);
            } else if (mOrganizationType.equals("lawyer")) {
                mTitleString = getString(R.string.lvshishiwusuo_paihang);
            } else {
                mTitleString = getString(R.string.x_kuaiji_paihang);
            }
            mTitle.setText(mTitleString);
            mSectionTitle.setText(mTitleString);
        }
    }

    public void setupRefresh() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageOffset = 1;
                mOrgs.clear();
                mOrgAdapter.notifyDataSetChanged();
                mPresenter.getOrganizations(mOrganizationType, pageOffset);
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getOrganizations(mOrganizationType, ++pageOffset);
            }
        });
    }

    private void init() {
        //适配器
        mOrgs = new ArrayList<>();
        mOrgAdapter = new OrganizationAdapter(this, mOrgs, Constants.STOCK_DATA_LIST_CONTENT_ALL, mOrganizationType);
        mZhuban.setAdapter(mOrgAdapter);
        mZhuban.setLayoutManager(new LinearLayoutManager(this));
        mZhuban.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        //头部标题
        mTitle.setText(mTitleString);
        setupRefresh();

    }

    //返回
    @OnClick(R.id.rl_back)
    void rlback() {
        finish();
    }

    @Override
    public void showOrganizations(List<StasOrgsEntity> topIndustry) {
        if (pageOffset == 1) {
            mOrgs.clear();
        }
        mOrgs.addAll(topIndustry);
        mOrgAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmore();
        ;
    }

    @Override
    public void err(String err) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
