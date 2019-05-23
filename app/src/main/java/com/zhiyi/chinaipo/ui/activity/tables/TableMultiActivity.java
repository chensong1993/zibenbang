package com.zhiyi.chinaipo.ui.activity.tables;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
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
import com.zhiyi.chinaipo.base.connectors.stocks.StockPriceConnector;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.TabEntity;
import com.zhiyi.chinaipo.presenters.stocks.StockPricePresenter;
import com.zhiyi.chinaipo.ui.adapter.slidetable.TableContentAdapter;
import com.zhiyi.chinaipo.ui.adapter.slidetable.TableNameAdapter;
import com.zhiyi.chinaipo.ui.widget.SyncHorizontalScrollView;
import com.zhiyi.chinaipo.ui.widget.TableTitle;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.anim.Cinematics;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 这个也是可以横竖滑动的表，它有tab头
 * 从行情中心 传过来的值
 */
public class TableMultiActivity extends BaseActivity<StockPricePresenter> implements StockPriceConnector.View {
    //表格部分
    @BindView(R.id.tv_table_title_left)
    TextView tv_table_title_left;
    @BindView(R.id.tv_table_title_0)
    TextView tvTab0;
    @BindView(R.id.tv_table_title_1)
    TextView tvTab1;
    @BindView(R.id.tv_table_title_2)
    TextView tvTab2;
    @BindView(R.id.tv_table_title_3)
    TextView tvTab3;
    @BindView(R.id.tv_table_title_4)
    TextView tvTab4;
    @BindView(R.id.tv_table_title_5)
    TextView tvTab5;
    @BindView(R.id.tv_table_title_6)
    TextView tvTab6;
    @BindView(R.id.tv_table_title_7)
    TextView tvTab7;
    @BindView(R.id.tv_table_title_8)
    TextView tvTab8;
    @BindView(R.id.tv_table_title_9)
    TextView tvTab9;
    @BindView(R.id.tv_table_title_10)
    TextView tvTab10;
    @BindView(R.id.tv_table_title_11)
    TextView tvTab11;
    @BindView(R.id.tv_table_title_12)
    TextView tvTab12;
    @BindView(R.id.tv_table_title_13)
    TextView tvTab13;
    @BindView(R.id.tv_table_title_14)
    TextView tvTab14;
    @BindView(R.id.tv_table_title_15)
    TextView tvTab15;
    @BindView(R.id.tv_table_title_16)
    TextView tvTab16;
    @BindView(R.id.left_container_listview)
    RecyclerView leftListView;
    @BindView(R.id.right_container_listview)
    RecyclerView rightListView;
    //横竖联动头容器
    @BindView(R.id.title_horsv)
    SyncHorizontalScrollView titleHorScv;
    //横竖联动内容容器
    @BindView(R.id.content_horsv)
    SyncHorizontalScrollView contentHorScv;
    @BindView(R.id.smart)
    SmartRefreshLayout mSmartRefreshLayout;
    private TableNameAdapter mLvNormalNameAdapter;
    private TableContentAdapter mLvNormalInfoAdapter;

    private TextView[] tvTitles;
    //定义没有点击的标题
    private String[] titles;
    //    private List<Integer> colors;
    private int tabSelected;
    //标题
    @BindView(R.id.tv_title)
    TextView mtvTitle;
    @BindView(R.id.rl_seek)
    RelativeLayout mRl_seek;
    @BindView(R.id.rl_back)
    RelativeLayout mRl_back;
    @BindView(R.id.tab_data)
    CommonTabLayout mCommonTabLayout;
    //XTabLayout mTabLayout;
    @BindView(R.id.img_share)
    ImageView mImgSearch;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    String[] ITEMS;
    private int pageOffset = 1;
    private String sortBy = Constants.STOCK_SORT_BY_CHNG_PCT;
    private String sortOrder = Constants.STOCK_SORT_ORDER_DES;
    private String baseIndex = Constants.STOCK_SORT_BY_CHNG_PCT;
    private String mBaseIndex, mSortBy, extraKey, extraValue, Title;
    private int tabTitle;
    private List<StockPriceEntity> mStockList;
    private TableHeadOnClickListener tbClickListener;
    private Boolean initialized = false;
    ArrayList<CustomTabEntity> TabList;

    @Override
    protected int getLayout() {
        return R.layout.activity_table_multi;
    }

    @Override
    protected void initEventAndData() {
        mRlProgress.setVisibility(View.VISIBLE);
        pageOffset = 1;
        sortBy = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_SORT_BY);
        initData();
        setupDefaultValues();
        initAdapter();
        getStockPrices();
        setListener();
    }

    public void setupTvTitleArray() {
        //根据每个tab一一对应点击哪一个然后让它自己对应没有点击之前的效果
        tvTitles = new TextView[17];
        //当前价
        tvTitles[0] = tvTab0;
        //涨跌幅
        tvTitles[1] = tvTab1;
        //成交额
        tvTitles[2] = tvTab2;
        //成交量
        tvTitles[3] = tvTab3;
        //涨跌幅
        tvTitles[4] = tvTab4;
        //营收
        tvTitles[5] = tvTab5;
        //净利润
        tvTitles[6] = tvTab6;
        //流通股本
        tvTitles[7] = tvTab7;
        //流通市值
        tvTitles[8] = tvTab8;
        //总股本
        tvTitles[9] = tvTab9;
        //总市值
        tvTitles[10] = tvTab10;
        //市盈率
        tvTitles[11] = tvTab11;
        //每股收益
        tvTitles[12] = tvTab12;
        //最高价
        tvTitles[13] = tvTab13;
        //最低价
        tvTitles[14] = tvTab14;
        //均值
        tvTitles[15] = tvTab15;
        //振幅
        tvTitles[16] = tvTab16;
    }

    private void setupClickable() {
        for (int i = 0; i < titles.length; i++) {
            if (tvTitles[i] != null) {
                tvTitles[i].setTag(i);
                tvTitles[i].setOnClickListener(tbClickListener);
            }
        }
    }

    public class TableHeadOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            TextView tvClicked = (TextView) view;
            if (tvClicked == null) {
                return;
            }
            mRlProgress.setVisibility(View.VISIBLE);
            restoreTableState();
            setTitle(tvClicked, titles[(int) view.getTag()]);
            //标题
            mtvTitle.setText(titles[(int) view.getTag()] + "-" + Title);
            sortBy = Constants.STOCK_SORT_ORDER_ARRAY[(int) view.getTag()];
            pageOffset = 1;
            getStockPrices();
            //  tvClicked.setSelected(true);
        }
    }

    public void setTitle(TextView view, String s) {
        view.setTextColor(ContextCompat.getColor(this, R.color.black));
        if (view.isSelected()) {
            view.setText(Html.fromHtml("<font color='#d71820'>▲</font>" + s));
            view.setSelected(false);
            sortOrder = Constants.STOCK_SORT_ORDER_ASC;
        } else {
            view.setText(Html.fromHtml("<font color='#0b9d0b'>▼</font>" + s));
            view.setSelected(true);
            sortOrder = Constants.STOCK_SORT_ORDER_DES;
        }
    }

    private void restoreTableState() {
        //给没有点击的titile赋值
        //还原tableTitle之前的样式
        for (int i = 0; i < tvTitles.length; i++) {
            tvTitles[i].setText(titles[i]);
            tvTitles[i].setTextColor(ContextCompat.getColor(this, R.color.hui4));
        }
    }

    public void initData() {
        mBaseIndex = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_BASE_INDEX);
        mSortBy = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_SORT_BY);
        extraKey = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_EXT_KEY);
        extraValue = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_EXT_VALUE);
        Title = getIntent().getStringExtra(Constants.PARAMETER_STOCK_LIST_TITLE);
        tabTitle = getIntent().getIntExtra(Constants.PARAMETER_STOCK_LIST_TABTITLE, 1);
        if (tabTitle == 0) {
            mCommonTabLayout.setVisibility(View.GONE);
        } else {
            mCommonTabLayout.setVisibility(View.VISIBLE);
        }
        //table
        ITEMS = getResources().getStringArray(R.array.tables);
        tv_table_title_left.setText("股票名称");
        // 设置两个水平控件的联动
        titleHorScv.setScrollView(contentHorScv);
        contentHorScv.setScrollView(titleHorScv);
        mtvTitle.setText(Title);
        //点击其他返回以前的文字
        titles = getResources().getStringArray(R.array.table);
        //全部 创新 基础 做市
        TabList = new ArrayList<>();
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mRlProgress.setVisibility(View.VISIBLE);
                baseIndex = Constants.STOCK_BASE_INDEX_ARRAY[position];
                getStockPrices();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        for (int j = 0; j < ITEMS.length; j++) {
            TabList.add(new TabEntity(ITEMS[j]));
//            XTabLayout.Tab layout = mTabLayout.newTab();
//            layout.setText(ITEMS[j]);
//            mTabLayout.addTab(layout);
        }
        mCommonTabLayout.setTabData(TabList);
        setupTvTitleArray();
        tbClickListener = new TableHeadOnClickListener();
        setupClickable();
        for (int i = 0; i < Constants.STOCK_BASE_INDEX_ARRAY.length; i++) {
            if (Constants.STOCK_BASE_INDEX_ARRAY[i].equals(mBaseIndex)) {
                LogUtil.i("mBaseIndex", mBaseIndex);
                LogUtil.i("mBaseIndex", i + "");
                baseIndex = mBaseIndex;
                getStockPrices();
                mCommonTabLayout.setCurrentTab(i);

            }

        }

    }

    XTabLayout.OnTabSelectedListener onTabSelectedListener = new XTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(XTabLayout.Tab tab) {
            mRlProgress.setVisibility(View.VISIBLE);
            int i = tab.getPosition();
            baseIndex = Constants.STOCK_BASE_INDEX_ARRAY[i];
            getStockPrices();
        }

        @Override
        public void onTabUnselected(XTabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(XTabLayout.Tab tab) {
        }
    };

    private void getStockPrices() {
        if (extraKey == null) {
            mPresenter.getPrices(baseIndex, sortBy, sortOrder, pageOffset);
            LogUtil.i("getStockPrices", "123456");
        } else {
            mPresenter.getPricesWithExtra("all", sortBy, sortOrder, extraKey, extraValue, pageOffset);
            LogUtil.i("getStockPrices", extraKey);
            LogUtil.i("getStockPrices", baseIndex +" "+ sortBy +" "+ sortOrder +" "+ extraKey +" "+ extraValue +" "+ pageOffset);
        }
    }

    private void setupDefaultValues() {
        if (mSortBy != null) {
            sortBy = mSortBy;
            switch (mSortBy) {
                //在这里写每一个tab的处理根据点击的文字判断请求涨跌额还是成交量
                case Constants.STOCK_SORT_BY_CHNG_PCT:
                    //涨跌幅
                    tvTab1.setTextColor(ContextCompat.getColor(this, R.color.black));
                    TableTitle.setSelected(tvTab1, "涨跌幅");
                    mtvTitle.setText("涨跌幅-" + Title);
                    break;
                case Constants.STOCK_SORT_BY_LATEST_TURNOVER:
                    //成交额
                    tvTab2.setTextColor(ContextCompat.getColor(this, R.color.black));
                    TableTitle.setSelected(tvTab2, "成交额");
                    mtvTitle.setText("成交额-" + Title);
                    break;
                case Constants.STOCK_SORT_BY_LATEST_VOLUME:
                    //成交量
                    tvTab3.setTextColor(ContextCompat.getColor(this, R.color.black));
                    TableTitle.setSelected(tvTab3, "成交量");
                    mtvTitle.setText("成交量-" + Title);
                    break;
                default:
                    sortBy = Constants.STOCK_SORT_BY_CHNG_PCT;
                    break;
            }
        }

    }

    @OnClick(R.id.rl_back)
    void quit() {
        finish();
    }

    @OnClick(R.id.rl_seek)
    void search() {
        Cinematics.searchCinematics(this, mImgSearch);
      /*  Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);*/
    }

    private void initAdapter() {
        mStockList = new ArrayList();
        mLvNormalNameAdapter = new TableNameAdapter(this, mStockList);
        mLvNormalInfoAdapter = new TableContentAdapter(this, mStockList);
        leftListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        leftListView.setLayoutManager(new LinearLayoutManager(this));
        leftListView.setNestedScrollingEnabled(false);
        rightListView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        rightListView.setLayoutManager(new LinearLayoutManager(this));
        rightListView.setNestedScrollingEnabled(false);
        leftListView.setAdapter(mLvNormalNameAdapter);
        rightListView.setAdapter(mLvNormalInfoAdapter);
    }

    public void setListener() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageOffset = 1;
                getStockPrices();
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageOffset++;
                getStockPrices();
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showPrice(List<StockPriceEntity> priceList) {
        mRlProgress.setVisibility(View.GONE);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmore();
        LogUtil.i("showPriceList", priceList.size() + "");
        mStockList = priceList;
        if (pageOffset == 1) {
            mLvNormalInfoAdapter.add(mStockList);
            mLvNormalNameAdapter.add(mStockList);
        } else {
            mLvNormalNameAdapter.addAll(mStockList);
            mLvNormalInfoAdapter.addAll(mStockList);
        }

    }

    @Override
    public void err(String s) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
        ToastUtil.showToast(this, s);
    }

    public static class Builder {

        private String baseIndex;
        private String sortBy;
        private String extKey;
        private String extValue;
        private String title;
        private int tabTitle;
        private Context mContext;

        public Builder() {

        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setBaseIndex(String mBaseIndex) {
            this.baseIndex = mBaseIndex;
            return this;
        }

        public Builder setSortBy(String mSortBy) {
            this.sortBy = mSortBy;
            return this;
        }

        public Builder setExtKey(String mExtKey) {
            this.extKey = mExtKey;
            return this;
        }

        public Builder setExtValue(String mExtValue) {
            this.extValue = mExtValue;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTabTitle(int mTabTitle) {
            tabTitle = mTabTitle;
            return this;
        }
    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, TableMultiActivity.class);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_BASE_INDEX, builder.baseIndex);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_SORT_BY, builder.sortBy);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_EXT_KEY, builder.extKey);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_EXT_VALUE, builder.extValue);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_TITLE, builder.title);
        intent.putExtra(Constants.PARAMETER_STOCK_LIST_TABTITLE, builder.tabTitle);
        builder.mContext.startActivity(intent);
    }
}
