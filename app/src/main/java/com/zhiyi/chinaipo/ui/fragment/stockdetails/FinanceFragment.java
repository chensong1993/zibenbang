package com.zhiyi.chinaipo.ui.fragment.stockdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.stocks.FinanceConnector;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;
import com.zhiyi.chinaipo.presenters.stocks.FinancePresenter;
import com.zhiyi.chinaipo.ui.activity.stocks.MoreFinanceActivity;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceAssetAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceBalanceAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceCashAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceTermTitleAdapter;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//import com.zhiyi.chinaipo.ui.activity.slidetableonclick.F10Activity;

/**
 * 可滑动的table点击详情后的f10
 */
public class FinanceFragment extends BaseFragment<FinancePresenter> implements FinanceConnector.View {

    @BindView(R.id.rv_lirun_nianbao)
    RecyclerView mRvAssetYear;
    @BindView(R.id.rv_zichan_nianbao)
    RecyclerView mRvBalanceYear;
    @BindView(R.id.rv_xianjin_nianbao)
    RecyclerView mRvCashYear;
    @BindView(R.id.tv_reload)
    TextView mTvReload;
    @BindView(R.id.ll_f10)
    LinearLayout mLlF10;
    @BindView(R.id.rv_f10_lirun_biao_this_year)
    RecyclerView mF10;
    @BindView(R.id.rv_f10_zichan_fuzhai_biao_this_year)
    RecyclerView mRvZichan;
    @BindView(R.id.rv_f10_xianjin_liuliang_biao_this_year)
    RecyclerView mRvXianjin;

    private FinanceAssetAdapter mF10AssetAdapter;
    private FinanceBalanceAdapter mF10BalanceAdapter;
    private FinanceCashAdapter mF10CashAdapter;
    private FinanceTermTitleAdapter mAssetTitleAdapter, mBalanceTitleAdapter, mCashTitleAdapter;

    private List<FinanceEntity> mFinanceList;
    private List<BalanceEntity> mBalanceList;
    private List<CashFlowEntity> mCashsList;

    private List<Pair<String, Integer>> mAssetTitleValues, mBalanceTitleValues, mCashTitleValues;

    @BindView(R.id.rl_gengduo)
    RelativeLayout mRlGengduo;

    private String mStockCode;
    private String mStockName;

//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(, container, false);
//        ButterKnife.bind(this, view);
//        init();
//        return view;
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_details_f10;
    }

    @Override
    protected void initEventAndData() {
        initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initData() {
        mStockCode = getArguments().getString(Constants.PARAMETER_STOCK_CODE, "");
        mStockName = getArguments().getString(Constants.PARAMETER_STOCK_NAME, "");
        mPresenter.getProfit(mStockCode);
        mPresenter.getFinance(mStockCode);
        mPresenter.getCashFlow(mStockCode);
        //第一个
        mAssetTitleValues = new ArrayList<>();
        mAssetTitleAdapter = new FinanceTermTitleAdapter(getActivity(), mAssetTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvAssetYear.setAdapter(mAssetTitleAdapter);
        LinearLayoutManager titleLayoutmanager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvAssetYear.setLayoutManager(titleLayoutmanager1);

        mFinanceList = new ArrayList<>();
        mF10AssetAdapter = new FinanceAssetAdapter(getActivity(), mFinanceList, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mF10.setAdapter(mF10AssetAdapter);
        LinearLayoutManager verticalLayoutmanager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mF10.setLayoutManager(verticalLayoutmanager1);
        //惯性滑动
        mF10.setNestedScrollingEnabled(false);

        //第二个
        mBalanceTitleValues = new ArrayList<>();
        mBalanceTitleAdapter = new FinanceTermTitleAdapter(getActivity(), mBalanceTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvBalanceYear.setAdapter(mBalanceTitleAdapter);
        LinearLayoutManager titleLayoutmanager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvBalanceYear.setLayoutManager(titleLayoutmanager2);

        mBalanceList = new ArrayList<>();
        LinearLayoutManager verticalLayoutmanager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mF10BalanceAdapter = new FinanceBalanceAdapter(getActivity(), mBalanceList, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvZichan.setAdapter(mF10BalanceAdapter);
        mRvZichan.setLayoutManager(verticalLayoutmanager2);
        //惯性滑动
        mRvZichan.setNestedScrollingEnabled(false);
        //现金流
        mCashTitleValues = new ArrayList<>();
        mCashTitleAdapter = new FinanceTermTitleAdapter(getActivity(), mCashTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvCashYear.setAdapter(mCashTitleAdapter);
        LinearLayoutManager titleLayoutmanager3
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRvCashYear.setLayoutManager(titleLayoutmanager3);

        mCashsList = new ArrayList<>();
        LinearLayoutManager verticalLayoutmanager3
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mF10CashAdapter = new FinanceCashAdapter(getActivity(), mCashsList, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvXianjin.setAdapter(mF10CashAdapter);
        mRvXianjin.setLayoutManager(verticalLayoutmanager3);
        //惯性滑动
        mRvXianjin.setNestedScrollingEnabled(false);
        // 查看更多
        mRlGengduo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    MoreFinanceActivity.launch(new MoreFinanceActivity.Builder()
                            .setContext(mContext)
                            .setStockCode(mStockCode)
                            .setStockName(mStockName));
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void showProfit(List<BalanceEntity> balanceEntities) {
        mTvReload.setVisibility(View.GONE);
        mLlF10.setVisibility(View.VISIBLE);
        mBalanceList.clear();
        mBalanceList.addAll(balanceEntities);
        mF10BalanceAdapter.notifyDataSetChanged();

        mBalanceTitleValues.clear();
        mBalanceTitleValues.add(Pair.create(balanceEntities.get(0).getEnddate(), balanceEntities.get(0).getReporttype()));
        mBalanceTitleValues.add(Pair.create(balanceEntities.get(1).getEnddate(), balanceEntities.get(1).getReporttype()));
        mBalanceTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCashFlow(List<CashFlowEntity> cList) {
        mTvReload.setVisibility(View.GONE);
        mLlF10.setVisibility(View.VISIBLE);
        mCashsList.clear();
        mCashsList.addAll(cList);
        mF10CashAdapter.notifyDataSetChanged();

        mCashTitleValues.clear();
        mCashTitleValues.add(Pair.create(cList.get(0).getEnddate(), cList.get(0).getReporttype()));
        mCashTitleValues.add(Pair.create(cList.get(1).getEnddate(), cList.get(1).getReporttype()));
        mCashTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFinance(List<FinanceEntity> financeEntities) {
        mTvReload.setVisibility(View.GONE);
        mLlF10.setVisibility(View.VISIBLE);
        mFinanceList.clear();
        mFinanceList.addAll(financeEntities);
        mF10AssetAdapter.notifyDataSetChanged();

        mAssetTitleValues.clear();
        mAssetTitleValues.add(Pair.create(financeEntities.get(0).getRpt_date(), financeEntities.get(0).getRpt_src()));
        mAssetTitleValues.add(Pair.create(financeEntities.get(1).getRpt_date(), financeEntities.get(1).getRpt_src()));
        mAssetTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void err() {
        mTvReload.setVisibility(View.VISIBLE);
        mLlF10.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_reload)
    void reload() {
        initData();
    }
}
