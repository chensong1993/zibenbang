package com.zhiyi.chinaipo.ui.activity.datas;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.datas.NewStockConnector;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.TabEntity;
import com.zhiyi.chinaipo.presenters.datas.NewStockPresenter;
import com.zhiyi.chinaipo.ui.adapter.datas.NewStocksAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//import com.zhiyi.chinaipo.adapter.datamodule.NewStocksAdapter;

/**
 * 数据中心里面的 新股挂牌头部点击进入的界面
 */
public class NewStockActivity extends BaseActivity<NewStockPresenter> implements NewStockConnector.View {
    @BindView(R.id.smart)
    SmartRefreshLayout mSmartRefreshLayout;

    //返回
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    //顶部标题
    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.rv_xinguguapai)
    RecyclerView mRvXinguguapai;
    @BindView(R.id.X_xTablayout)
    //XTabLayout mTabLayout;
    CommonTabLayout mCommonTabLayout;
    String[] ITEMS;
    private NewStocksAdapter mNewStockAdapter;
    private List<StockPriceEntity> mNewStockList;
    private int pageOffset = 1;
    private int currentNewStockType = 0;
    private String[] newStockUntil = new String[]{"month", "week", "day"};
    ArrayList<CustomTabEntity> mTabKLineList;
    @Override
    protected int getLayout() {
        return R.layout.activity_xingu;
    }

    @Override
    protected void initEventAndData() {
        init();
        setupRefresh();
        pageOffset = 1;
        mPresenter.getNewStocks(newStockUntil[currentNewStockType], pageOffset);
    }

    public void setupRefresh(){
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageOffset = 1;
                mNewStockList.clear();
                mNewStockAdapter.notifyDataSetChanged();
                mPresenter.getNewStocks(newStockUntil[currentNewStockType], pageOffset);
                refreshlayout.finishRefresh(2000);
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getNewStocks(newStockUntil[currentNewStockType], ++pageOffset);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }


    private void init() {
        mTitle.setText(R.string.x_xingu_guapai);
        onClick();
        //新股挂牌
        mNewStockList = new ArrayList<>();
        mNewStockAdapter = new NewStocksAdapter(this, mNewStockList, Constants.STOCK_DATA_LIST_CONTENT_ALL);
        mRvXinguguapai.setAdapter(mNewStockAdapter);
        mRvXinguguapai.setLayoutManager(new LinearLayoutManager(this));
        mRvXinguguapai.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        //本月 本周 当天
        ITEMS = getResources().getStringArray(R.array.data_xinguguapia);
        mTabKLineList=new ArrayList<>();
        for (int j = 0; j < ITEMS.length; j++) {
//            XTabLayout.Tab layout = mTabLayout.newTab();
//            layout.setText(ITEMS[j]);
//            mTabLayout.addTab(layout);
            mTabKLineList.add(new TabEntity(ITEMS[j]));
        }
        mCommonTabLayout.setTabData(mTabKLineList);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                pageOffset = 1;
                mNewStockList.clear();
                mNewStockAdapter.notifyDataSetChanged();
                currentNewStockType = position;
                mPresenter.getNewStocks(newStockUntil[currentNewStockType], pageOffset);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    XTabLayout.OnTabSelectedListener onTabSelectedListener = new XTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(XTabLayout.Tab tab) {
            pageOffset = 1;
            mNewStockList.clear();
            mNewStockAdapter.notifyDataSetChanged();
            currentNewStockType = tab.getPosition();
            mPresenter.getNewStocks(newStockUntil[currentNewStockType], pageOffset);
        }

        @Override
        public void onTabUnselected(XTabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(XTabLayout.Tab tab) {

        }
    };

    private void onClick() {
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void showNewStocks(List<StockPriceEntity> newStocks) {
        if (pageOffset == 1) {
            mNewStockList.clear();
        }
        mNewStockList.addAll(newStocks);
        mNewStockAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh(300);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
