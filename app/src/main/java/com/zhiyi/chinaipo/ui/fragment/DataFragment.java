package com.zhiyi.chinaipo.ui.fragment;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RootFragment;
import com.zhiyi.chinaipo.base.connectors.datas.DataConnector;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.event.HomeIndex;
import com.zhiyi.chinaipo.presenters.datas.DataPresenter;
import com.zhiyi.chinaipo.ui.activity.datas.AreaListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.IndustryAreaListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.IndustryListActivity;
import com.zhiyi.chinaipo.ui.activity.datas.OrganizationListActivity;
import com.zhiyi.chinaipo.ui.adapter.OrganizationAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.DataGongsiAdpater;
import com.zhiyi.chinaipo.ui.adapter.datas.GuapaiAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.IndustryAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.NewStocksAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.RankingHeaderAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.TopShujuAdapter;
import com.zhiyi.chinaipo.ui.adapter.datas.XinguHeaderAdapter;
import com.zhiyi.chinaipo.ui.widget.FingNestedScrollView;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleviewGDivider;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.StatusBarUtil;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.anim.Cinematics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import com.zhiyi.chinaipo.ui.activity.datamodule.OrganizationListActivity;
//import com.zhiyi.chinaipo.ui.activity.datamodule.DataAttorneyActivity;
//import com.zhiyi.chinaipo.ui.activity.datamodule.IndustryListActivity;
//import com.zhiyi.chinaipo.ui.activity.datamodule.DataMarketMakerActivity;
//import com.zhiyi.chinaipo.ui.activity.datamodule.DataSponsorActivity;
//import com.zhiyi.chinaipo.ui.activity.search.stair.SearchActivity;

/**
 * 数据模块
 */
public class DataFragment extends RootFragment<DataPresenter> implements DataConnector.View {
    //顶部标题
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    //搜索
    @BindView(R.id.img_seek)
    ImageView mImgSeek;
    @BindView(R.id.rv_main_one)
    RecyclerView mOne;
    //第二个挂牌公司和挂牌行业
    @BindView(R.id.rvdiqufenbu)
    RecyclerView mRvDuQuFenbu;
    @BindView(R.id.rv_hangyefenbu)
    RecyclerView mRvHangyeFenbu;
    //挂牌公司
    @BindView(R.id.rv_guapai)
    RecyclerView mGuapai;
    @BindView(R.id.tv_updated_time)
    TextView mTotalUpdatedTime;

    //行业统计
    @BindView(R.id.rv_hangye)
    RecyclerView mHangye;
    //头部点击进入的地方
    @BindView(R.id.rv_xingu)
    RecyclerView mXingu;
    @BindView(R.id.rv_zhubanpaihang)
    RecyclerView mPaihang;
    @BindView(R.id.rv_zuoshi)
    RecyclerView mZuoshi;
    @BindView(R.id.rv_lvshi)
    RecyclerView mLvshi;

    //会计排行
    @BindView(R.id.rv_kuaiji)
    RecyclerView mKuaiji;
    //主办排行
    @BindView(R.id.rv_zhubanHeader)
    RecyclerView mZhubanHeader;
    //做市排行
    @BindView(R.id.rv_zuoshiHeader)
    RecyclerView mZuoshiHeader;
    //律师排行
    @BindView(R.id.rv_lvshiHeader)
    RecyclerView mLvshiHeader;
    @BindView(R.id.rv_kuaijiHeader)
    RecyclerView mKuaijiHeader;
    //新股挂牌
    @BindView(R.id.rv_xinguHeader)
    RecyclerView mXinguHeader;
    @BindView(R.id.ll_content)
    LinearLayout mLlTop;
    //行业统计
    @BindView(R.id.ll_hangye)
    LinearLayout mLhangye;
    //下拉刷新
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.fillStatusBarView)
    TextView mStatusBarView;
    @BindView(R.id.fn_scroll)
    FingNestedScrollView mScrollView;
    //1.第一个
    private TopShujuAdapter mTopShujuAdapter;
    //挂牌公司行业分布和地区分布
    private DataGongsiAdpater mGongsiDiquAdpater, mGongsiHangyeAdpater;
    private GuapaiAdapter mGuapaiAdapter;
    //行业统计
    private IndustryAdapter mHangyeAdapter;
    private NewStocksAdapter mXinguAdapter;
    //主办券商，会计，律师，做市的内容
    private OrganizationAdapter mInvestorsAdapter, mDealersAdapter, mLawyersAdapter, mAccountantsAdapter;
    //主办券商，会计，律师，做市的头部
    private RankingHeaderAdapter mZhubanHeaderAdapter;
    //新股挂牌的头部
    private XinguHeaderAdapter mXinguHeaderAdapter;

    //横竖分割线
    private RecycleviewGDivider recycleviewGDivider;
    //横向分割线
    private RecycleViewDivider recycleViewDivider;

    List<String> mMarketTops;
    //    StasAreaEntity mTopArea;
//    StasIndustryEntity mTopIndustry;
    List<String> mTopArea;
    List<String> mTopIndustry;
    List<StasOrgsEntity> mDealers;
    List<StasOrgsEntity> mInvestors;
    List<StasOrgsEntity> mLawyers;
    List<StasOrgsEntity> mAccountants;
    List<StasIndustryEntity> mIndustries;
    List<StockPriceEntity> mNewStocks;

    @OnClick(R.id.img_seek)
    void clickedSearch() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
//        SnackbarUtil.showShort(getActivity().getWindow().getDecorView(), "搜索出现了问题");
            //startActivity(new Intent(getActivity(), SearchActivity.class));
            Cinematics.searchCinematics(getActivity(), mImgSeek);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shuju;
    }

    @OnClick(R.id.ll_hangye)
    void listIndustries() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            getContext().startActivity(new Intent(getActivity(), IndustryListActivity.class));
        }
    }


    private void init() {
        mRlProgress.setVisibility(View.VISIBLE);
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView,0);
        mTvTitle.setText(R.string.data_centre);
        //最上面的数据
        mTopShujuAdapter = new TopShujuAdapter(getActivity());
        mOne.setAdapter(mTopShujuAdapter);
        mTopShujuAdapter.notifyDataSetChanged();
        //清除分割线
        mOne.removeItemDecoration(recycleviewGDivider);
        mOne.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //分割线
        recycleviewGDivider = new RecycleviewGDivider(2, ContextCompat.getColor(getActivity(), R.color.hui));
        mOne.addItemDecoration(recycleviewGDivider);

//        //挂牌公司diqu 分布
        mTopArea = new ArrayList<>();
        mGongsiHangyeAdpater = new DataGongsiAdpater(getActivity(), mTopArea);
        mRvHangyeFenbu.setAdapter(mGongsiHangyeAdpater);
        mGongsiHangyeAdpater.setOnItemClickListener(new DataGongsiAdpater.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(getActivity(), AreaListActivity.class);
                intent.putExtra(Constants.TITLE_NAME, 0);
                getContext().startActivity(intent);
            }
        });
        mRvHangyeFenbu.setLayoutManager(new LinearLayoutManager(getActivity()));

//        //挂牌公司行业分布
        mTopIndustry = new ArrayList<>();
        mGongsiDiquAdpater = new DataGongsiAdpater(getActivity(), mTopIndustry);
        mRvDuQuFenbu.setAdapter(mGongsiDiquAdpater);
        mGongsiDiquAdpater.setOnItemClickListener(new DataGongsiAdpater.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(getActivity(), IndustryAreaListActivity.class);
                intent.putExtra(Constants.TITLE_NAME, 1);
                getContext().startActivity(intent);
            }
        });
        mRvDuQuFenbu.setLayoutManager(new LinearLayoutManager(getActivity()));

//        //挂牌公司
        mMarketTops = new ArrayList();
        mGuapaiAdapter = new GuapaiAdapter(getContext(), mMarketTops);
        mGuapai.setAdapter(mGuapaiAdapter);
        if (mMarketTops != null) {
            mRlProgress.setVisibility(View.GONE);
        }
        //分割线，清除重叠的
        mGuapai.removeItemDecoration(recycleViewDivider);
        mHangye.removeItemDecoration(recycleViewDivider);
        mXingu.removeItemDecoration(recycleViewDivider);
        mZuoshi.removeItemDecoration(recycleViewDivider);
        mLvshi.removeItemDecoration(recycleViewDivider);
        mKuaiji.removeItemDecoration(recycleViewDivider);
        mGuapai.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewDivider = new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui));
        mGuapai.addItemDecoration(recycleViewDivider);

//        //行业统计
        mIndustries = new ArrayList<>();
        mHangyeAdapter = new IndustryAdapter(getActivity(), mIndustries, Constants.STOCK_DATA_LIST_SUMMARY);
        mHangye.setAdapter(mHangyeAdapter);
        mHangye.removeItemDecoration(recycleViewDivider);
        mHangye.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHangye.addItemDecoration(recycleViewDivider);

//        //新股挂牌
        mXinguHeaderAdapter = new XinguHeaderAdapter(getActivity());
        mXinguHeader.setAdapter(mXinguHeaderAdapter);
        mXinguHeader.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNewStocks = new ArrayList<>();
        mXinguAdapter = new NewStocksAdapter(getActivity(), mNewStocks, Constants.STOCK_DATA_LIST_SUMMARY);
        mXingu.setAdapter(mXinguAdapter);
        mXingu.removeItemDecoration(recycleViewDivider);
        mXingu.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXingu.addItemDecoration(recycleViewDivider);
//
//        //主办排行
        mZhubanHeaderAdapter = new RankingHeaderAdapter(getActivity(), getZhubanH());
        mZhubanHeader.setAdapter(mZhubanHeaderAdapter);
        mZhubanHeaderAdapter.setOnItemClickListener(new RankingHeaderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent getMore = new Intent(getActivity(), OrganizationListActivity.class);
                getMore.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "investor");
                getActivity().startActivity(getMore);
            }
        });
        mZhubanHeader.setLayoutManager(new LinearLayoutManager(getActivity()));

        mInvestors = new ArrayList<>();
        mInvestorsAdapter = new OrganizationAdapter(getActivity(), mInvestors, Constants.STOCK_DATA_LIST_SUMMARY, "investor");
        mPaihang.setAdapter(mInvestorsAdapter);
        mPaihang.removeItemDecoration(recycleViewDivider);
        mPaihang.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPaihang.addItemDecoration(recycleViewDivider);

//        //做市排行
        mZhubanHeaderAdapter = new RankingHeaderAdapter(getActivity(), getZuoshiH());
        mZuoshiHeader.setAdapter(mZhubanHeaderAdapter);
        mZhubanHeaderAdapter.setOnItemClickListener(new RankingHeaderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent getMore = new Intent(getActivity(), OrganizationListActivity.class);
                getMore.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "dealer");
                getContext().startActivity(getMore);
            }
        });
        mZuoshiHeader.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDealers = new ArrayList<>();
        mDealersAdapter = new OrganizationAdapter(getActivity(), mDealers, Constants.STOCK_DATA_LIST_SUMMARY, "dealer");
        mZuoshi.setAdapter(mDealersAdapter);
        mZuoshi.removeItemDecoration(recycleViewDivider);
        mZuoshi.setLayoutManager(new LinearLayoutManager(getActivity()));
        mZuoshi.addItemDecoration(recycleViewDivider);

//        //律师排行
        mZhubanHeaderAdapter = new RankingHeaderAdapter(getActivity(), getlvshiH());
        mLvshiHeader.setAdapter(mZhubanHeaderAdapter);
        mZhubanHeaderAdapter.setOnItemClickListener(new RankingHeaderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent getMore = new Intent(getActivity(), OrganizationListActivity.class);
                getMore.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "lawyer");
                getContext().startActivity(getMore);
            }
        });
        mLvshiHeader.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLawyers = new ArrayList<>();
        mLawyersAdapter = new OrganizationAdapter(getActivity(), mLawyers, Constants.STOCK_DATA_LIST_SUMMARY, "lawyer");
        mLvshi.setAdapter(mLawyersAdapter);
        mLvshi.removeItemDecoration(recycleViewDivider);
        mLvshi.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLvshi.addItemDecoration(recycleViewDivider);

//        //会计排行
        mZhubanHeaderAdapter = new RankingHeaderAdapter(getActivity(), getkuaijiH());
        mKuaijiHeader.setAdapter(mZhubanHeaderAdapter);
        mZhubanHeaderAdapter.setOnItemClickListener(new RankingHeaderAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent getMore = new Intent(getActivity(), OrganizationListActivity.class);
                getMore.putExtra(Constants.LIST_ORGANIZATION_BY_TYPE, "accountant");
                getContext().startActivity(getMore);
            }
        });
        mKuaijiHeader.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAccountants = new ArrayList<>();
        mAccountantsAdapter = new OrganizationAdapter(getActivity(), mAccountants, Constants.STOCK_DATA_LIST_SUMMARY, "accountant");
        mKuaiji.setAdapter(mAccountantsAdapter);
        mKuaiji.setLayoutManager(new LinearLayoutManager(getActivity()));
        mKuaiji.addItemDecoration(recycleViewDivider);
        Refresh();
    }

    @Override
    public void iconRefresh(HomeIndex homeIndex) {
        if (homeIndex.getPosition() == 1) {
            mScrollView.scrollTo(0, 0);
            mSmartRefreshLayout.autoRefresh();
        }
        LogUtil.i("homeIndex", "h" + homeIndex.getPosition());
    }


    public void Refresh() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getMarketTops();
                mPresenter.getNewStocks();
                mPresenter.getTotalAreas();
                mPresenter.getTotalIndustries();
                mPresenter.getStatsByInvestor();
                mPresenter.getStatsByDealer();
                mPresenter.getStatsByLawyer();
                mPresenter.getStatsByAccountant();
                refreshlayout.finishRefresh(1000);
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(0);
            }
        });
    }

    @Override
    public void showMarketTops(List<IndexSCEntity> marketTops) {
        if (mMarketTops.size() <= 0) {
            for (IndexSCEntity it : marketTops) {
                mMarketTops.add(it.getVals());
            }
        }
        for (int i = 0; i < marketTops.size(); i++) {
            mMarketTops.set(i, marketTops.get(i).getVals());
        }
        mGuapaiAdapter.notifyDataSetChanged();
        mTotalUpdatedTime.setText(mMarketTops.get(0));

    }

    //主办头部
    public List<String> getZhubanH() {
        List<String> sectionHead = new ArrayList<>();
        sectionHead.add(getString(R.string.zhubanquanshang_paihang));
        //  sectionHead.add()
        if (mMarketTops.size() > 0) {
            sectionHead.add(mMarketTops.get(0));
        }
        //
        return sectionHead;
    }

    //zuohsi头部
    public List<String> getZuoshiH() {
        List<String> sectionHead = new ArrayList<>();
        sectionHead.add(getString(R.string.zuoshishang_paihang));
        if (mMarketTops.size() > 0) {
            sectionHead.add(mMarketTops.get(0));
        }
        return sectionHead;
    }

    //lvshi头部
    public List<String> getlvshiH() {
        List<String> sectionHead = new ArrayList<>();
        sectionHead.add(getString(R.string.lvshishiwusuo_paihang));
        if (mMarketTops.size() > 0) {
            sectionHead.add(mMarketTops.get(0));
        }
        return sectionHead;
    }

    //kuaji头部
    public List<String> getkuaijiH() {
        List<String> sectionHead = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            sectionHead.add(getString(R.string.x_kuaiji_paihang));
            if (mMarketTops.size() > 0) {
                sectionHead.add(mMarketTops.get(0));
            }
        }
        return sectionHead;
    }

    @Override
    public void showErrorMsg(String msg) {
        mRlProgress.setVisibility(View.GONE);
        ToastUtil.showToast(getActivity(), msg);
        //  Timber.e("Got error as : " + msg);
    }

    @Override
    public void stateError() {
        mRlProgress.setVisibility(View.GONE);
        // Timber.e("Got error as : ");
    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void showAreas(StasAreaEntity topArea) {
        if (mTopArea.size() <= 0) {
            mTopArea.add("--");
            mTopArea.add("--");
            mTopArea.add("--");
        }
        mTopArea.set(0, topArea.getArea_name());
        mTopArea.set(1, topArea.getPercentage().toString());
        mTopArea.set(2, "" + topArea.getTotal_company());
        mGongsiHangyeAdpater.notifyDataSetChanged();
    }

    @Override
    public void showIndustry(List<StasIndustryEntity> topIndustry) {
        if (mTopIndustry.size() <= 0) {
            mTopIndustry.add("--");
            mTopIndustry.add("--");
            mTopIndustry.add("--");
        }
        mTopIndustry.set(0, topIndustry.get(0).getIndu_name_2());
        mTopIndustry.set(1, topIndustry.get(0).getPercentage().toString());
        mTopIndustry.set(2, "" + topIndustry.get(0).getTotal_company());
        mGongsiDiquAdpater.notifyDataSetChanged();

        mIndustries.clear();
        for (int i = 0; i < 3; i++) {
            mIndustries.add(topIndustry.get(i));
        }
        mHangyeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNewStocks(List<StockPriceEntity> newStocks) {
        mNewStocks.clear();
        mNewStocks.addAll(newStocks);
        mXinguAdapter.notifyDataSetChanged();
        mRlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showDealers(List<StasOrgsEntity> topDealers) {
        mDealers.clear();
        for (int i = 0; i < 5; i++) {
            mDealers.add(topDealers.get(i));
        }
        mDealersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInvestors(List<StasOrgsEntity> topInvestors) {
        mInvestors.clear();
        for (int i = 0; i < 5; i++) {
            mInvestors.add(topInvestors.get(i));

        }
        mInvestorsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLawyers(List<StasOrgsEntity> topLawyers) {
        mLawyers.clear();
        for (int i = 0; i < 5; i++) {
            mLawyers.add(topLawyers.get(i));
        }
        mLawyersAdapter.notifyDataSetChanged();
        mRlProgress.setVisibility(View.GONE);
    }

    @Override
    public void showAccountants(List<StasOrgsEntity> topAccountant) {
        mAccountants.clear();
        for (int i = 0; i < 5; i++) {
            mAccountants.add(topAccountant.get(i));
        }
        mAccountantsAdapter.notifyDataSetChanged();
        mSmartRefreshLayout.finishRefresh(500);
    }


    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        init();
        mPresenter.getMarketTops();
        mPresenter.getTotalAreas();
        mPresenter.getNewStocks();
        mPresenter.getTotalIndustries();
        mPresenter.getStatsByInvestor();
        mPresenter.getStatsByDealer();
        mPresenter.getStatsByLawyer();
        mPresenter.getStatsByAccountant();
    }

}
