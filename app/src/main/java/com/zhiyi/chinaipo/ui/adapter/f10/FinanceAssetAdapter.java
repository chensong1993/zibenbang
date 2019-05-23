package com.zhiyi.chinaipo.ui.adapter.f10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceAssetAdapter extends RecyclerView.Adapter<FinanceAssetAdapter.ListViewHolder> {

    private Context mContext;
    private List<FinanceEntity> mAssetList;
    private int viewType;

    public FinanceAssetAdapter(Context context, List<FinanceEntity> mAssets, int viewType) {
        this.mContext = context;
        this.mAssetList = mAssets;
        this.viewType = viewType;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_f10_lirunbiao, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (mAssetList != null){
            holder.setItem(mAssetList.get(position));
            holder.refreshView();
        }

    }

    @Override
    public int getItemCount() {
        if (viewType == Constants.FINANCE_LIST_CONTENT_ALL) {
            return mAssetList == null ? 0 : mAssetList.size();
        } else if (mAssetList.size() > 2) {
            return mAssetList == null ? 0 : 2;
        }
        return mAssetList == null ? 0 : mAssetList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_meigushouyi)
        TextView mBenefitEachShare;
        @BindView(R.id.tv_meigushouyi_tanbo)
        TextView mBenefitEachShareAfter;
        @BindView(R.id.tv_yingyeshouru)
        TextView mIncome;
        @BindView(R.id.tv_yingshoutongbi)
        TextView mIncomeChange;
        @BindView(R.id.tv_jinglirun)
        TextView mProfit;
        @BindView(R.id.tv_jingliruntongbi)
        TextView mProfitChange;
        @BindView(R.id.tv_jinglirun_koufei)
        TextView mProfitAlter;
        @BindView(R.id.tv_jinglirun_koufei_tongbi)
        TextView mProfitAlterChange;

        FinanceEntity mItem;

        public ListViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setItem(FinanceEntity item) {
            this.mItem = item;
        }

        void refreshView() {
            if (mItem != null) {
                mBenefitEachShare.setText(NumericUtil.getStockValue(mItem.getBps()));
                mBenefitEachShareAfter.setText(NumericUtil.getStockValue(mItem.getDi_epsp_aft_ei()));
                mIncome.setText(NumericUtil.getStockValue(mItem.getOperation_income()));
                mIncomeChange.setText(NumericUtil.getPercentDirect(mItem.getOperation_income_change()));
                mProfit.setText(NumericUtil.getStockValue(mItem.getNet_profit()));
                mProfitChange.setText(NumericUtil.getPercentDirect(mItem.getNet_profit_change()));
                mProfitAlter.setText(NumericUtil.getStockValue(mItem.getNet_pro_aft_ei()));
                mProfitAlterChange.setText(NumericUtil.getPercentDirect(mItem.getNet_pro_aft_ei_change()));
            }
        }
    }

}
