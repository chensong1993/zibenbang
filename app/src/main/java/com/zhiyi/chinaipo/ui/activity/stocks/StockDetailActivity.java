package com.zhiyi.chinaipo.ui.activity.stocks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RootActivity;
import com.zhiyi.chinaipo.base.connectors.stocks.StockDetailConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.TabEntity;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.models.event.UpdateStockEvent;
import com.zhiyi.chinaipo.presenters.stocks.StockDetailPresenter;
import com.zhiyi.chinaipo.ui.activity.login.LoginActivity;
import com.zhiyi.chinaipo.ui.adapter.StockDetailAdapter;
import com.zhiyi.chinaipo.ui.fragment.kchart.KLineFragment;
import com.zhiyi.chinaipo.ui.fragment.kchart.TimelineFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.AnnouncementsFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.FinanceFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.RelatedNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.ShareHoldersFragment;
import com.zhiyi.chinaipo.ui.fragment.stockdetails.SummaryFragment;
import com.zhiyi.chinaipo.ui.widget.ShareBottomDialog;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @note 横竖滑动列表的详情页
 * @anthor Song Chen
 */
public class StockDetailActivity extends RootActivity<StockDetailPresenter> implements StockDetailConnector.View {
    //公告资讯那栏的标题
    public String[] ITEMS;
    public String[] ITEMK;
    //k线图
    @BindView(R.id.tab_k_line)
    CommonTabLayout mTabKLines;
    //公告 资讯 资料 股东 f10
    @BindView(R.id.tab_stock_details)
    CommonTabLayout mTabStockDetails;
    //退出
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;

    @BindView(R.id.rv_xieyi)
    RecyclerView mStockDetailTop;
    //标题
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_shuzi)
    TextView mTvPrice;
    @BindView(R.id.tv_baifenbi)
    TextView mTvChange;
    @BindView(R.id.tv_baifenbi1)
    TextView mTvChangePercent;

    @BindView(R.id.tv_stock_exchange_type)
    TextView mTvExchangeType;
    @BindView(R.id.tv_stock_type)
    TextView mTvStockType;
    @BindView(R.id.tv_focusing_on_stock)
    TextView mTvFocusStock;

    //标题编号
    @BindView(R.id.tv_title_number)
    TextView mTvTitleNumber;
    //搜索
    @BindView(R.id.img_share)
    ImageView mImgShare;
    //刷新
    @BindView(R.id.Smart)
    SmartRefreshLayout swipeRefreshLayout;
    //今开昨收 营收等的数据
    private StockDetailAdapter mStockDetailAdapter;
    private List<StockPriceEntity> mStockList;
    private Fragment mKLineFragments[];
    private Fragment mOtherFragments[];
    ShareBottomDialog dialog;
    // 记录选择的位置
    private int currPosition;
    // 记录K选择的位置
    private int mKLinePosition;
    private String mStockCode;
    private String mStockName = "";
    private String mToken = "";
    private int KIndex = 0;
    private int NewsIndex = 0;
    private StockCodeEntity mStockCodeEntity;
    //private NetBroadcastReceiver mNetBroadcastReceiver;
    ArrayList<CustomTabEntity> mTabKLineList, mTabStockDetailList;

    @Override
    protected int getLayout() {
        return R.layout.activity_zhongxin;
    }


    @Override
    protected void initEventAndData() {
        mToken = SPHelper.get(Constants.REGISTER_MESSAGES_TOKEN, "");
        mStockCode = getIntent().getStringExtra(Constants.PARAMETER_STOCK_CODE);
        mStockName = getIntent().getStringExtra(Constants.PARAMETER_STOCK_NAME);
        init();
        setList();
        mPresenter.getPrice(mStockCode);

    }


    private void init() {
        ITEMK = getResources().getStringArray(R.array.k_line);
        //公告 一列的table
        ITEMS = getResources().getStringArray(R.array.gonggao);

        //上面数据的适配器
        mStockList = new ArrayList<>();
        mStockDetailAdapter = new StockDetailAdapter(mContext, mStockList);
        mStockDetailTop.setAdapter(mStockDetailAdapter);
        mStockDetailTop.setLayoutManager(new LinearLayoutManager(this));

        //K线图
        mKLineFragments = new Fragment[ITEMK.length];
        mTabKLineList = new ArrayList<>();
        for (int i = 0; i < ITEMK.length; i++) {
//            XTabLayout.Tab tab = mTabKLine.newTab();
//            tab.setText(ITEMK[i]);
            createKLineFragments(i);
//            mTabKLine.addTab(tab, i, i == 0);
            mTabKLineList.add(new TabEntity(ITEMK[i]));
        }
        mTabKLines.setTabData(mTabKLineList);
        mTabKLines.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                hideAllKLineFragment();
                switchChart(position);
                KIndex = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //公告资讯研报的
        mOtherFragments = new Fragment[ITEMS.length];
        //mTabOthers.removeAllTabs();
        mTabStockDetailList = new ArrayList<>();
        for (int i = 0; i < ITEMS.length; i++) {
//            XTabLayout.Tab tab = mTabOthers.newTab();
//            tab.setText(ITEMS[i]);
//            mTabOthers.addTab(tab, i, i == 0);
            createOtherFragments(i);
            mTabStockDetailList.add(new TabEntity(ITEMS[i]));
        }

        hideAllKLineFragment();
        switchChart(KIndex);
        hideAllOtherFragment();
        switchOtherFragment(NewsIndex);
        mTabStockDetails.setTabData(mTabStockDetailList);
        mTabStockDetails.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                hideAllOtherFragment();
                switchOtherFragment(position);
                NewsIndex = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getPrice(mStockCode);
                hideAllKLineFragment();
                switchChart(KIndex);
                hideAllOtherFragment();
                switchOtherFragment(NewsIndex);
                LogUtil.i("mKLineFragments", "" + KIndex);
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        if (netMobile >= 0) {
            if (mStockList == null || mStockList.size() == 0) {
                mPresenter.getPrice(mStockCode);
                hideAllKLineFragment();
                switchChart(KIndex);
                hideAllOtherFragment();
                switchOtherFragment(NewsIndex);
            }
        } else {
            ToastUtil.showToast(this, "暂无网络");
        }

    }

    //在onResume()方法注册
    @Override
    public void onResume() {
//        if (mNetBroadcastReceiver == null) {
//            mNetBroadcastReceiver = new NetBroadcastReceiver();
//        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        mContext.registerReceiver(mNetBroadcastReceiver, filter);
        super.onResume();
    }


    //onPause()方法注销
    @Override
    public void onPause() {
       // mContext.unregisterReceiver(mNetBroadcastReceiver);
        super.onPause();
    }

    private void setList() {
        for (Iterator iter = App.getInstance().getStockCodeDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
            StockCodeEntity element = (StockCodeEntity) iter.next();
            if (mStockCode.equals(element.getCode()) && mToken.equals(element.getToken())) {
                mTvFocusStock.setText("已关注");
                mTvFocusStock.setBackground(ContextCompat.getDrawable(this, R.drawable.yiguanzhu));

            }
        }


    }

    //K线图
    XTabLayout.OnTabSelectedListener onKLineTabSelectedListener = new XTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(XTabLayout.Tab tab) {
            hideAllKLineFragment();
            switchChart(tab.getPosition());
            KIndex = tab.getPosition();
        }

        @Override
        public void onTabUnselected(XTabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(XTabLayout.Tab tab) {

        }
    };

    private void hideAllKLineFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mKLineFragments) {
            if (f != null) {
                if (!f.isHidden()) {
                    ft.hide(f);
                }
            }
        }
        ft.commit();
    }

    private void showKLineFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    private void addKLineFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fl_k, fragment);
            ft.commit();
        }
    }

    private void switchChart(int position) {
        addKLineFragment(mKLineFragments[position]);
        showKLineFragment(mKLineFragments[position]);
    }

    public void createKLineFragments(int position) {
        Fragment fragTo = mKLineFragments[position];
        if (fragTo == null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PARAMETER_STOCK_CODE, mStockCode);
            switch (position) {
                case 0:
                    if (mStockList.size() > 0) {
                        bundle.putString(Constants.TIMELINE_PARAMETER_PRE_CLOSE_PRICE, mStockList.get(0).getLast_close());
                    }
                    fragTo = new TimelineFragment();
                    fragTo.setArguments(bundle);
                    break;
                case 1:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "day");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                case 2:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "week");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                case 3:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "month");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                default:
                    break;
            }
            mKLineFragments[position] = fragTo;
        }
    }

    //公告 资讯 研报 资料
    XTabLayout.OnTabSelectedListener onTabSelectedListener = new XTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(XTabLayout.Tab tab) {
            hideAllOtherFragment();
            switchOtherFragment(tab.getPosition());
            NewsIndex = tab.getPosition();
        }

        @Override
        public void onTabUnselected(XTabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(XTabLayout.Tab tab) {

        }
    };

    private void hideAllOtherFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mOtherFragments) {
            if (f != null) {
                if (!f.isHidden()) {
                    ft.hide(f);
                }
            }
        }
        ft.commit();
    }

    private void showOtherFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    private void addOtherFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fl_table, fragment);
            ft.commit();
        }
    }

    private void switchOtherFragment(int position) {
        addOtherFragment(mOtherFragments[position]);
        showOtherFragment(mOtherFragments[position]);
    }

    public void createOtherFragments(int position) {
        Fragment fragTo = mOtherFragments[position];
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PARAMETER_STOCK_CODE, mStockCode);
        bundle.putString(Constants.PARAMETER_STOCK_NAME, mStockName);
        switch (position) {
            case 0:
                fragTo = new AnnouncementsFragment();
                fragTo.setArguments(bundle);
                break;
            case 1:
                fragTo = new RelatedNewsFragment();
                fragTo.setArguments(bundle);
                break;
            case 2:
                fragTo = new SummaryFragment();
                fragTo.setArguments(bundle);
                break;
            case 3:
                fragTo = new ShareHoldersFragment();
                fragTo.setArguments(bundle);
                break;
            case 4:
                fragTo = new FinanceFragment();
                fragTo.setArguments(bundle);
            default:
                break;
        }
        mOtherFragments[position] = fragTo;
    }

    @OnClick(R.id.img_share)
    void clickedSearch() {
        // Cinematics.searchCinematics(this, mImgSeek);
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            if (mStockCode != null) {
                String stockDetail = "提供" + mStockName + "(" + mStockCode + ")股票的行情走势、五档盘口、逐笔交易等实时行情数据";
                String stockName = mStockName + "(" + mStockCode + ")";
                dialog = ShareBottomDialog.newInstance(stockName, stockDetail, mStockCode, 1);
                dialog.show(getSupportFragmentManager());
            }
            //  startActivity(new Intent(this, SearchActivity.class));
        }
    }

    @OnClick(R.id.rl_back)
    void quit() {
        finish();
    }

    @OnClick(R.id.tv_focusing_on_stock)
    void GuanZhu() {
        if (mToken == null || mToken == "") {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            if (mTvFocusStock.getText().equals("+关注")) {
                if (mStockList != null && mStockList.size() > 0) {
                    mStockCodeEntity = new StockCodeEntity(null, mToken, mStockList.get(0).getStock_name(), mStockCode, mStockList.get(0).getLatest_price(), mStockList.get(0).getChng_pct(), mStockList.get(0).getLatest_turnover());
                    App.getInstance().getStockCodeDao().insert(mStockCodeEntity);
                    mTvFocusStock.setBackground(ContextCompat.getDrawable(this, R.drawable.yiguanzhu));
                    mTvFocusStock.setText("已关注");
                }
            } else {
                for (Iterator iter = App.getInstance().getStockCodeDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
                    StockCodeEntity element = (StockCodeEntity) iter.next();
                    if (mStockCode.equals(element.getCode())) {
                        App.getInstance().getStockCodeDao().deleteByKey(element.get_id());
                        mTvFocusStock.setBackground(ContextCompat.getDrawable(this, R.drawable.weiguanzhu));
                        mTvFocusStock.setText("+关注");

                    }
                }
            }
        }
    }

    @Override
    public void showPrice(StockPriceEntity stockDetail) {
        mStockList.clear();
        mStockList.add(stockDetail);
        //公司名称
        mTvTitle.setText(stockDetail.getStock_name());
        mStockName = stockDetail.getStock_name();
        //公司编号
        mTvTitleNumber.setText(stockDetail.getStock_code());

        mTvPrice.setText(NumericUtil.getStockValue(stockDetail.getLatest_price()));
        mTvChange.setText(NumericUtil.getStockValue(stockDetail.getChng()));
        mTvChangePercent.setText(NumericUtil.getPercentDirect(stockDetail.getChng_pct()));
        mStockDetailAdapter.notifyDataSetChanged();
        mTvExchangeType.setText(stockDetail.getDeal_type().get(0));
        if (Double.parseDouble(stockDetail.getChng()) > 0) {
            mTvPrice.setTextColor(ContextCompat.getColor(this, R.color.red));
            mTvChange.setTextColor(ContextCompat.getColor(this, R.color.red));
            mTvChangePercent.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            mTvPrice.setTextColor(ContextCompat.getColor(this, R.color.green));
            mTvChange.setTextColor(ContextCompat.getColor(this, R.color.green));
            mTvChangePercent.setTextColor(ContextCompat.getColor(this, R.color.green));
        }
        if (stockDetail.getIs_innovate()) {
            mTvStockType.setText(R.string.innovation_layer);
            mTvStockType.setTextColor(ContextCompat.getColor(this, R.color.mediumpurple));
            mTvStockType.setBackground(ContextCompat.getDrawable(this, R.drawable.yanbao));
        } else {
            mTvStockType.setText(R.string.base_layer);
            mTvStockType.setTextColor(ContextCompat.getColor(this, R.color.peachpuff));
            mTvStockType.setBackground(ContextCompat.getDrawable(this, R.drawable.jichu));
        }

//        mTvFocusStock.setText(stockDetail.getIs_innovate())

        RxBus.getDefault().post(new UpdateStockEvent(mStockCode, stockDetail));
    }

    @Override
    public void showFollow(EventLogin login) {
        if (login.getToken() != null) {
            mToken = login.getToken();
            LogUtil.i("userToken", login.getToken() + "qwer");
        }
    }

    @Override
    public void userToken(String userToken) {
        if (userToken != null) {
            mToken = userToken;
            LogUtil.i("userToken", userToken);
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    public static class Builder {

        private String stockName;
        private String stockCode;
        private Context mContext;
        private Activity mActivity;

        public Builder() {

        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public Builder setStockCode(String stockCode) {
            this.stockCode = stockCode;
            return this;
        }

        public Builder setStockName(String stockName) {
            this.stockName = stockName;
            return this;
        }
    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, StockDetailActivity.class);
        intent.putExtra(Constants.PARAMETER_STOCK_CODE, builder.stockCode);
        intent.putExtra(Constants.PARAMETER_STOCK_NAME, builder.stockName);
        builder.mContext.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
