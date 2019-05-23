package com.zhiyi.chinaipo.ui.activity.stocks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.stocks.FinanceConnector;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;
import com.zhiyi.chinaipo.presenters.stocks.FinancePresenter;
import com.zhiyi.chinaipo.ui.adapter.f10.F10NameAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceAssetAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceBalanceAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceCashAdapter;
import com.zhiyi.chinaipo.ui.adapter.f10.FinanceTermTitleAdapter;
import com.zhiyi.chinaipo.ui.widget.SyncHorizontalScrollView;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.anim.Cinematics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @note F10点击进入的界面
 * @anthor Song Chen
 */
public class MoreFinanceActivity extends BaseActivity<FinancePresenter> implements FinanceConnector.View {
    //利润表
    @BindView(R.id.right_container_listview_lirun)
    RecyclerView rightListViewLiRun;
    //资产表
    @BindView(R.id.right_container_listview_zichan)
    RecyclerView rightListViewZiChan;
    //现金表
    @BindView(R.id.right_container_listview_xianjin)
    RecyclerView rightListViewXianjin;

    @BindView(R.id.rv_lirun_bao)
    RecyclerView mRvAssetYear;
    @BindView(R.id.rv_zichanfuzhai_bao)
    RecyclerView mRvBalanceYear;
    @BindView(R.id.rv_xianjinliuliang_bao)
    RecyclerView mRvCashYear;

    private F10NameAdapter mLvNormalNameAdapter, mLvNormalNameAdapter1, mLvNormalNameAdapter2;
    private FinanceAssetAdapter mLvAssetAdapter;
    private FinanceBalanceAdapter mLvBalanceAdapter;
    private FinanceCashAdapter mLvCashAdapter;

    @BindView(R.id.tv_gongsi_title)
    TextView mTvTitle;
    @BindView(R.id.tv_title_number)
    TextView mTvNumber;
    @BindView(R.id.img_seek)
    ImageView mImgSearch;
    @BindView(R.id.title_horsv)
    SyncHorizontalScrollView titleHorScv;
    @BindView(R.id.title_horsv1)
    SyncHorizontalScrollView titleHorScv2;
    @BindView(R.id.title_horsv2)
    SyncHorizontalScrollView titleHorScv3;

    @BindView(R.id.content_horsv)
    SyncHorizontalScrollView contentHorScv;
    @BindView(R.id.content_horsv1)
    SyncHorizontalScrollView contentHorScv2;
    @BindView(R.id.content_horsv2)
    SyncHorizontalScrollView contentHorScv3;

    private FinanceTermTitleAdapter mAssetTitleAdapter, mBalanceTitleAdapter, mCashTitleAdapter;
    private List<Pair<String, Integer>> mAssetTitleValues, mBalanceTitleValues, mCashTitleValues;

    private List<String> nameList1, nameList, nameList2;
    private List<FinanceEntity> mAssetsList;
    private List<BalanceEntity> mBalanceList;
    private List<CashFlowEntity> mCashList;

    private String mStockCode;
    private String mStockName;
    private int pageOffset;

    @Override
    protected int getLayout() {
        return R.layout.activity_f10;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mStockName = intent.getExtras().getString(Constants.PARAMETER_STOCK_NAME, "阳光恒美");
            mStockCode = intent.getExtras().getString(Constants.PARAMETER_STOCK_CODE, "833027");
        } else {
            mStockCode = "830925";
        }
        init();
        initAdapter();
        mPresenter.getCashFlow(mStockCode);
        mPresenter.getFinance(mStockCode);
        mPresenter.getProfit(mStockCode);
    }

    public void init() {
        //标题
        mTvTitle.setText(mStockName);
        //编号
        mTvNumber.setText(mStockCode);
//        tv_table_title_left.setText("利润表");
//        tv_table_title_left2.setText("资产负债表");
//        tv_table_title_left3.setText("现金流量表");
        // 设置两个水平控件的联动
        titleHorScv.setScrollViews(contentHorScv, contentHorScv2, contentHorScv3);
        contentHorScv.setScrollViews(titleHorScv, titleHorScv2, titleHorScv3);
        titleHorScv2.setScrollViews(contentHorScv3, contentHorScv2, contentHorScv);
        contentHorScv2.setScrollViews(titleHorScv3, titleHorScv2, titleHorScv);
        titleHorScv3.setScrollViews(contentHorScv3, contentHorScv2, contentHorScv);
        contentHorScv3.setScrollViews(titleHorScv3, titleHorScv2, titleHorScv);
    }

    @OnClick(R.id.rl_back)
    void quit() {
        finish();
    }

    @OnClick(R.id.img_seek)
    void search() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            Cinematics.searchCinematics(this, mImgSearch);
           // startActivity(new Intent(this, SearchActivity.class));
        }
    }

    private void initAdapter() {
        mAssetTitleValues = new ArrayList<>();
        mAssetTitleAdapter = new FinanceTermTitleAdapter(this, mAssetTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvAssetYear.setAdapter(mAssetTitleAdapter);
        LinearLayoutManager titleLayoutmanager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRvAssetYear.setLayoutManager(titleLayoutmanager1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rightListViewLiRun.setLayoutManager(layoutManager);
        mAssetsList = new ArrayList<>();
        mLvAssetAdapter = new FinanceAssetAdapter(this, mAssetsList, Constants.FINANCE_LIST_CONTENT_ALL);
        rightListViewLiRun.setAdapter(mLvAssetAdapter);

        mBalanceTitleValues = new ArrayList<>();
        mBalanceTitleAdapter = new FinanceTermTitleAdapter(this, mBalanceTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvBalanceYear.setAdapter(mBalanceTitleAdapter);
        LinearLayoutManager titleLayoutmanager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRvBalanceYear.setLayoutManager(titleLayoutmanager2);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rightListViewZiChan.setLayoutManager(layoutManager2);
        mBalanceList = new ArrayList<>();
        mLvBalanceAdapter = new FinanceBalanceAdapter(this, mBalanceList, Constants.FINANCE_LIST_CONTENT_ALL);
        rightListViewZiChan.setAdapter(mLvBalanceAdapter);

        mCashTitleValues = new ArrayList<>();
        mCashTitleAdapter = new FinanceTermTitleAdapter(this, mCashTitleValues, Constants.FINANCE_LIST_CONTENT_SUMMARY);
        mRvCashYear.setAdapter(mCashTitleAdapter);
        LinearLayoutManager titleLayoutmanager3
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        mRvCashYear.setLayoutManager(titleLayoutmanager3);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rightListViewXianjin.setLayoutManager(layoutManager3);
        mCashList = new ArrayList<>();
        mLvCashAdapter = new FinanceCashAdapter(this, mCashList, Constants.FINANCE_LIST_CONTENT_ALL);
        rightListViewXianjin.setAdapter(mLvCashAdapter);

//        LinearLayoutManager layoutManagerHeader = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        leftListView.setLayoutManager(layoutManagerHeader);
//
//        LinearLayoutManager layoutManagerHeader1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        leftListView2.setLayoutManager(layoutManagerHeader1);
//
//        LinearLayoutManager layoutManagerHeader2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        leftListView3.setLayoutManager(layoutManagerHeader2);

//
//        nameList = new ArrayList<>();
//        mLvNormalNameAdapter = new F10NameAdapter(this, nameList);
//        leftListView.setAdapter(mLvNormalNameAdapter);
//        LinearLayoutManager nameLayoutmanager1
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        leftListView.setLayoutManager(nameLayoutmanager1);
//
//        nameList1 = new ArrayList<>();
//        mLvNormalNameAdapter1 = new F10NameAdapter(this, nameList1);
//        leftListView2.setAdapter(mLvNormalNameAdapter1);
//        LinearLayoutManager nameLayoutmanager2
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        leftListView2.setLayoutManager(nameLayoutmanager2);
//
//        nameList2 = new ArrayList<>();
//        mLvNormalNameAdapter2 = new F10NameAdapter(this, nameList2);
//        leftListView3.setAdapter(mLvNormalNameAdapter2);
//        LinearLayoutManager nameLayoutmanager3
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        leftListView3.setLayoutManager(nameLayoutmanager3);
//
//        namedata();
//        namedata1();
//        namedata2();
    }

//    private void namedata() {
////        nameList.clear();
//        nameList.addAll(Arrays.asList("每股收益","每股收益(摊薄)","营业收入","营收同比增长率","净利润","净利润同比增长率","净利润(扣非)","净利润(扣非)同步增长率"));
//        mLvNormalNameAdapter.notifyDataSetChanged();
//    }
//
//    private void namedata1() {
////        nameList1.clear();
//        nameList1.addAll(Arrays.asList("每股净资产","净资产收益率","流动资产","非流动资产","资产总计","流动负债","非流动负债","负债合计","未分配利润","资本公积","股东权益合计"));
//        mLvNormalNameAdapter1.notifyDataSetChanged();
//    }
//
//    private void namedata2() {
////        nameList2.clear();
//        nameList2.addAll(Arrays.asList("每股现金流净额","经营现金流净额","投资现金流净额","筹资现金流净"));
//        mLvNormalNameAdapter2.notifyDataSetChanged();
//    }

    @Override
    public void showProfit(List<BalanceEntity> balanceEntities) {
        mBalanceList.clear();
        mBalanceList.addAll(balanceEntities);
        mLvBalanceAdapter.notifyDataSetChanged();

        mBalanceTitleValues.clear();
        for (BalanceEntity bE : balanceEntities) {
            mBalanceTitleValues.add(Pair.create(bE.getEnddate(), bE.getReporttype()));
        }

        mBalanceTitleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCashFlow(List<CashFlowEntity> cList) {
        mCashList.clear();
        mCashList.addAll(cList);
        mLvCashAdapter.notifyDataSetChanged();

        mCashTitleValues.clear();
        for (CashFlowEntity cE : cList) {
            mCashTitleValues.add(Pair.create(cE.getEnddate(), cE.getReporttype()));
        }
        mCashTitleAdapter.notifyDataSetChanged();

    }

    @Override
    public void showFinance(List<FinanceEntity> financeEntities) {
        mAssetsList.clear();
        mAssetsList.addAll(financeEntities);
        mLvAssetAdapter.notifyDataSetChanged();

        mAssetTitleValues.clear();
        for (FinanceEntity fE : financeEntities) {
            mAssetTitleValues.add(Pair.create(fE.getRpt_date(), fE.getRpt_src()));
        }
        mAssetTitleAdapter.notifyDataSetChanged();

    }

    @Override
    public void err() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    public static class Builder {

        private String stockName;
        private String stockCode;
        private Context mContext;

        public MoreFinanceActivity.Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public MoreFinanceActivity.Builder setStockCode(String stockCode) {
            this.stockCode = stockCode;
            return this;
        }

        public MoreFinanceActivity.Builder setStockName(String stockName) {
            this.stockName = stockName;
            return this;
        }
    }

    public static void launch(MoreFinanceActivity.Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, MoreFinanceActivity.class);
        intent.putExtra(Constants.PARAMETER_STOCK_CODE, builder.stockCode);
        intent.putExtra(Constants.PARAMETER_STOCK_NAME, builder.stockName);
        builder.mContext.startActivity(intent);
    }
}
