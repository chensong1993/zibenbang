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
import com.zhiyi.chinaipo.ui.adapter.datas.IndustryAreaAdpater;
import com.zhiyi.chinaipo.ui.adapter.datas.IndustryRegionAdapter;
import com.zhiyi.chinaipo.ui.widget.annular.FanChart;
import com.zhiyi.chinaipo.ui.widget.annular.FanChartItem;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 数据中心的 挂牌公司行业分布
 */
public class IndustryAreaListActivity extends BaseActivity<IndustryPresenter> implements IndustryConnector.View {
    @BindView(R.id.rv_diqu)
    RecyclerView rvGongsi;
    @BindView(R.id.rv_annular)
    RecyclerView rvAnnular;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_mingcheng)
    TextView mMingcheng;
    @BindView(R.id.smart)
    SmartRefreshLayout smartRefreshLayout;
    //环形
    @BindView(R.id.fanChart)
    FanChart fanChart;
    private List<FanChartItem> mFanChartList;
    //地区公司占比
    private List<StasIndustryEntity> mDataList;
    private IndustryAreaAdpater mIndustryAreaAdpater;
    private IndustryRegionAdapter mIndustryRegionAdapter;


    private int pageOffset = 1;

    @Override
    protected int getLayout() {
        return R.layout.activity_diqu;
    }

    @Override
    protected void initEventAndData() {
        init();
        setupRefresh();
        mPresenter.getIndustries(pageOffset);
    }

    private void init() {

        int title=getIntent().getIntExtra(Constants.TITLE_NAME,1);
        if(title==1){
            mMingcheng.setText(R.string.hangye_name);
            mTitle.setText(R.string.x_gongsi_hangye_fenbu);
        }


        //左侧的地区占比
        mDataList = new ArrayList<>();
        mIndustryAreaAdpater = new IndustryAreaAdpater(mDataList, this);
        mIndustryRegionAdapter = new IndustryRegionAdapter(this, mDataList);

        rvAnnular.setLayoutManager(new LinearLayoutManager(this));
        rvAnnular.setNestedScrollingEnabled(false);
        rvAnnular.setAdapter(mIndustryAreaAdpater);

        rvGongsi.setLayoutManager(new LinearLayoutManager(this));
        rvGongsi.setNestedScrollingEnabled(false);
        rvGongsi.setAdapter(mIndustryRegionAdapter);
        rvGongsi.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
    }

    @OnClick(R.id.rl_back)
    void Back() {
        finish();
    }


    public void setupRefresh() {
        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDataList.clear();
                pageOffset = 1;
                mIndustryAreaAdpater.notifyDataSetChanged();
                mPresenter.getIndustries(pageOffset);
//                refreshlayout.finishRefresh(2000);
            }
        });
        //加载更多
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getIndustries(++pageOffset);

//                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    private void setupTopChart(List<StasIndustryEntity> topArea) {
        List<FanChartItem> charts = new ArrayList<>();
        FanChartItem.setCountSum(0);
        //1.对应的名称2.对应的占比3.对应的颜色
        float total = 100 - (new Double(topArea.get(0).getPercentage()).floatValue() + new Double(topArea.get(1).getPercentage()).floatValue() + new Double(topArea.get(2).getPercentage()).floatValue() + new Double(topArea.get(3).getPercentage()).floatValue() + new Double(topArea.get(4).getPercentage()).floatValue());
        charts.add(createItem(topArea.get(0).getIndu_name_2(), new Double(topArea.get(0).getPercentage()).floatValue(), ContextCompat.getColor(this, R.color.red)));
        charts.add(createItem(topArea.get(1).getIndu_name_2(), new Double(topArea.get(1).getPercentage()).floatValue(), ContextCompat.getColor(this, R.color.limegreen)));
        charts.add(createItem(topArea.get(2).getIndu_name_2(), new Double(topArea.get(2).getPercentage()).floatValue(), ContextCompat.getColor(this, R.color.deepskyblue)));
        charts.add(createItem(topArea.get(3).getIndu_name_2(), new Double(topArea.get(3).getPercentage()).floatValue(), ContextCompat.getColor(this, R.color.custom_yellow_text)));
        charts.add(createItem(topArea.get(4).getIndu_name_2(), new Double(topArea.get(4).getPercentage()).floatValue(), ContextCompat.getColor(this, R.color.color3)));
        charts.add(createItem(getString(R.string.qita), total, ContextCompat.getColor(this, R.color.hui1)));
        LogUtil.i("getTotal_company", topArea.get(0).getTotal_company() + "");
        fanChart.setCharts(charts);
    }

    private FanChartItem createItem(String name, float count, int color) {
        FanChartItem item = new FanChartItem();
        item.setName(name);
        item.setCount(count);
        item.setColor(color);
        FanChartItem.addCount(count);
        return item;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void showIndustry(List<StasIndustryEntity> topIndustry) {
        if (pageOffset == 1) {
            mDataList.clear();
            setupTopChart(topIndustry);
            smartRefreshLayout.finishRefresh();
        }
        mDataList.addAll(topIndustry);
        mIndustryAreaAdpater.notifyDataSetChanged();
        mIndustryRegionAdapter.notifyDataSetChanged();
        smartRefreshLayout.finishLoadmore();
    }

    @Override
    public void noContent() {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadmoreWithNoMoreData();
    }
}
