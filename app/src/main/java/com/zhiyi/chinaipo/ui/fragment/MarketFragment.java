package com.zhiyi.chinaipo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RootFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.IndexConnector;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.TabEntity;
import com.zhiyi.chinaipo.presenters.stocks.IndexPresenter;
import com.zhiyi.chinaipo.ui.activity.tables.TableMultiActivity;
import com.zhiyi.chinaipo.ui.adapter.market.MarketIndexAdpater;
import com.zhiyi.chinaipo.ui.adapter.market.StockListAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.StatusBarUtil;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.anim.Cinematics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 行情模块
 */
public class MarketFragment extends RootFragment<IndexPresenter> implements IndexConnector.View {

    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    //标题
    @BindView(R.id.tv_title)
    TextView mTitle;
    //搜索
    @BindView(R.id.img_seek)
    ImageView mImgSeek;
    //三板成指
    @BindView(R.id.rv_sanbanchengzhi)
    RecyclerView mRvSanbanzuoshi;
    //三板做市
    @BindView(R.id.rv_sanbanzuoshi)
    RecyclerView mRvSanbanchengzhi;
    //  LinearLayout mRelativeLayout;

    //涨幅榜
    @BindView(R.id.rv_zhangfu)
    RecyclerView mZhuangfu;
    //成交额
    @BindView(R.id.rv_chengjiaoe)
    RecyclerView mRvChengjiaoe;
    //成交量
    @BindView(R.id.rv_chengjiaoliang)
    RecyclerView mRvChengjiaoliang;
    @BindView(R.id.ll_content)
    LinearLayout mLinearTop;
    //涨幅榜顶部
    @BindView(R.id.ll_zhangfubang)
    LinearLayout mLinearzhangfu;
    //成交额顶部
    @BindView(R.id.ll_chengjiaoe)
    LinearLayout mLinearchengjiaoe;
    //成交量顶部
    @BindView(R.id.ll_chengjiaoliang)
    LinearLayout mLinearchengjiaoliang;
    //下拉刷新
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.fillStatusBarView)
    TextView mStatusBarView;
    @BindView(R.id.tab_data)
    CommonTabLayout commonTabLayout;
    private String[] ITEMS;
    private int[] ITEMS_ICON;
    //    //三板成指 和 三板做市
    private MarketIndexAdpater marketIndexAdpater, marketIndexAdpater1;
    private MarketIndexEntity mIndex1, mIndex2;
    private StockListAdapter mPriceByChangeAdapter, mPriceByVolumeAdapter, mPriceByAmountAdapter;
    private List<StockPriceEntity> mPriceListByChange, mPriceListByVolume, mPriceListByAmount;
    private int mSelectedIndex = 0;
    private MarketOnClickListener myClickListener;
    private String currentIndex;
    ArrayList<CustomTabEntity> TabList;
    Disposable mDisposable;
    boolean isLoadData = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hangqing;
    }


    @Override
    protected void initEventAndData() {
        init();
        initData();
        isLoadData = true;
    }

    private void init() {
        mPresenter.getStockIndex();
        mPresenter.getPriceByChange("all");
        mPresenter.getPriceByAmount("all");
        mPresenter.getPriceByVolume("all");
        mRlProgress.setVisibility(View.VISIBLE);
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView, 0);
        //table
        ITEMS = getActivity().getResources().getStringArray(R.array.tables);
        ITEMS_ICON = getActivity().getResources().getIntArray(R.array.tables_icon);
        mTitle.setText("行情中心");
        mIndex1 = new MarketIndexEntity();
        mIndex2 = new MarketIndexEntity();
        marketIndexAdpater = new MarketIndexAdpater(getActivity(), mIndex1);
        mRvSanbanzuoshi.setAdapter(marketIndexAdpater);
        mRvSanbanzuoshi.setLayoutManager(new LinearLayoutManager(getActivity()));
        marketIndexAdpater1 = new MarketIndexAdpater(getActivity(), mIndex2);
        mRvSanbanchengzhi.setAdapter(marketIndexAdpater1);
        mRvSanbanchengzhi.setLayoutManager(new LinearLayoutManager(getActivity()));

        //全部 创新 基础 做市
        TabList = new ArrayList();
        for (int i = 0; i < ITEMS.length; i++) {
            TabList.add(new TabEntity(ITEMS[i]));
        }
        commonTabLayout.setTabData(TabList);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mSelectedIndex = position;
                mPresenter.getPriceByChange(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
                mPresenter.getPriceByAmount(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
                mPresenter.getPriceByVolume(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    public void updateRvData() {
        mPresenter.getStockIndex();
        mPresenter.getPriceByChange(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
        mPresenter.getPriceByAmount(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
        mPresenter.getPriceByVolume(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);


        marketIndexAdpater = new MarketIndexAdpater(getActivity(), mIndex1);
        mRvSanbanzuoshi.setAdapter(marketIndexAdpater);
        marketIndexAdpater1 = new MarketIndexAdpater(getActivity(), mIndex2);
        mRvSanbanchengzhi.setAdapter(marketIndexAdpater1);
        mPriceByChangeAdapter = new StockListAdapter(getActivity(), mPriceListByChange);
        mZhuangfu.setAdapter(mPriceByChangeAdapter);
        mPriceByAmountAdapter = new StockListAdapter(getActivity(), mPriceListByAmount);
        mRvChengjiaoe.setAdapter(mPriceByAmountAdapter);
        mPriceByVolumeAdapter = new StockListAdapter(getActivity(), mPriceListByVolume);
        mRvChengjiaoliang.setAdapter(mPriceByVolumeAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void initData() {
        //涨跌幅排行
        mPriceListByChange = new ArrayList<>();
        mPriceByChangeAdapter = new StockListAdapter(getActivity(), mPriceListByChange);
        mZhuangfu.setAdapter(mPriceByChangeAdapter);
        mZhuangfu.setLayoutManager(new LinearLayoutManager(getActivity()));
        mZhuangfu.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui)));
        mZhuangfu.setNestedScrollingEnabled(false);
        if (mPriceListByChange != null) {
            mRlProgress.setVisibility(View.GONE);
        }
//
//        //成交额排行
        mPriceListByAmount = new ArrayList<>();
        mPriceByAmountAdapter = new StockListAdapter(getActivity(), mPriceListByAmount);
        mRvChengjiaoe.setAdapter(mPriceByAmountAdapter);
        mRvChengjiaoe.setLayoutManager(new LinearLayoutManager(getActivity()));
        //分割线
        mRvChengjiaoe.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui)));
        mRvChengjiaoe.setNestedScrollingEnabled(false);
//
//        //成交量排行榜
        mPriceListByVolume = new ArrayList<>();
        mPriceByVolumeAdapter = new StockListAdapter(getActivity(), mPriceListByVolume);
        mRvChengjiaoliang.setAdapter(mPriceByVolumeAdapter);
        mRvChengjiaoliang.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvChengjiaoliang.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui)));
        //惯性滑动
        mRvChengjiaoliang.setNestedScrollingEnabled(false);
        RefreshOrOnclick();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoadData) {
            Observable.interval(10000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(Long aLong) {
                            updateRvData();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void homeIndex(int index) {
        if (index == 2) {
            mLinearTop.scrollTo(0, 0);
            mSmartRefreshLayout.autoRefresh();

        }
    }

    public void RefreshOrOnclick() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getStockIndex();
               /* mPresenter.getPriceByChange("all");
                mPresenter.getPriceByAmount("all");
                mPresenter.getPriceByVolume("all");*/
                mPresenter.getPriceByChange(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
                mPresenter.getPriceByAmount(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
                mPresenter.getPriceByVolume(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex]);
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(0);
            }
        });

        mImgSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    Cinematics.searchCinematics(getActivity(), mImgSeek);
                    //  getContext().startActivity(new Intent(getActivity(), SearchActivity.class));
                }
            }
        });

        myClickListener = new MarketOnClickListener();
        mLinearzhangfu.setTag(1);
        mLinearzhangfu.setOnClickListener(myClickListener);
        mLinearchengjiaoe.setTag(2);
        mLinearchengjiaoe.setOnClickListener(myClickListener);
        mLinearchengjiaoliang.setTag(3);
        mLinearchengjiaoliang.setOnClickListener(myClickListener);

    }

    class MarketOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!RepeatCllickUtil.isFastDoubleClick()) {
                TableMultiActivity.launch(new TableMultiActivity.Builder()
                        .setContext(mContext)
                        .setBaseIndex(Constants.STOCK_BASE_INDEX_ARRAY[mSelectedIndex])
                        .setSortBy(Constants.STOCK_SORT_ORDER_ARRAY[(int) v.getTag()])
                        .setTitle("排行")
                        .setTabTitle(1)
                );
            }
        }
    }

    @Override
    public void showStockIndexes(List<MarketIndexEntity> stockIndexes) {
        mIndex1.setChng(stockIndexes.get(0).getChng());
        mIndex1.setChng_pct(stockIndexes.get(0).getChng_pct());
        mIndex1.setIndx_sname(getString(R.string.sanban_chengzhi));
        mIndex1.setTclose(stockIndexes.get(0).getTclose());
        mIndex2.setChng(stockIndexes.get(1).getChng());
        mIndex2.setChng_pct(stockIndexes.get(1).getChng_pct());
        mIndex2.setIndx_sname(getString(R.string.sanban_zuoshi));
        mIndex2.setTclose(stockIndexes.get(1).getTclose());
        marketIndexAdpater.notifyDataSetChanged();
        marketIndexAdpater1.notifyDataSetChanged();


    }

    @Override
    public void showPriceByChange(List<StockPriceEntity> priceList) {
        mPriceListByChange.clear();
        mPriceListByChange.addAll(priceList);
        mPriceByChangeAdapter.notifyDataSetChanged();
        mRlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showPriceByVolume(List<StockPriceEntity> priceList) {
        mPriceListByVolume.clear();
        mPriceListByVolume.addAll(priceList);
        mPriceByVolumeAdapter.notifyDataSetChanged();
        mRlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showPriceByAmount(List<StockPriceEntity> priceList) {
        mPriceListByAmount.clear();
        mPriceListByAmount.addAll(priceList);
        mPriceByAmountAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh();
        mRlProgress.setVisibility(View.GONE);
    }


    @Override
    public void err() {
        ToastUtil.showToast(getActivity(), "数据加载错误");
        mRlProgress.setVisibility(View.GONE);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


}
