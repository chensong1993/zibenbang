package com.zhiyi.chinaipo.ui.adapter.f10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceBalanceAdapter extends RecyclerView.Adapter<FinanceBalanceAdapter.ListViewHolder> {

    private Context mContext;
    private List<BalanceEntity> mFList;
    private int type;

    public FinanceBalanceAdapter(Context context, List<BalanceEntity> mList, int type) {
        this.mContext = context;
        this.mFList = mList;
        this.type = type;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_f10_zichanfuzhaibiao, parent, false);
        if (viewType == Constants.FINANCE_LIST_CONTENT_ALL) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_details_lv_f10_zichanfuzhaibiao, parent, false);
        }
        return new ListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (mFList != null){
            holder.setItem(mFList.get(position));
            holder.refreshView();
        }

    }

    @Override
    public int getItemCount() {
        if (type == Constants.FINANCE_LIST_CONTENT_ALL) {
            return mFList.size();
        } else if (mFList.size() > 2) {
            return 2;
        }
        return 0;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_meigujngzichan)
        TextView mTvValueEachShare;
        @BindView(R.id.tv_jingzichanshouyi)
        TextView mTvBenefit;
        @BindView(R.id.tv_liudongzichan)
        TextView mTvValueDynamic;
        @BindView(R.id.tv_feiliudongzichan)
        TextView mTvValueStatic;
        @BindView(R.id.tv_zichanheji)
        TextView mTvValueTotal;
        @BindView(R.id.tv_liudongfuzhai)
        TextView mTvDebtDynamic;
        @BindView(R.id.tv_feiliudongfuzhai)
        TextView mTvDebtStatic;
        @BindView(R.id.tv_fuzhaiheji)
        TextView mTvDebtTotal;
        @BindView(R.id.tv_weifenpeilirun)
        TextView mTvBenefitNotDistr;
        @BindView(R.id.tv_zibengongji)
        TextView mTvFund;
        @BindView(R.id.tv_gudongquanyiheji)
        TextView mTvBenifitForHolder;

        BalanceEntity mItem;

        public ListViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setItem(BalanceEntity item) {
            this.mItem = item;
        }

        void refreshView() {
            mTvValueEachShare.setText(NumericUtil.getStockValue(mItem.getValue_each_share()));
            mTvBenefit.setText(NumericUtil.getPercentDirect(mItem.getProfit_ratio_value()));
            mTvValueDynamic.setText(NumericUtil.getStockValue(mItem.getLiquid_assets()));
            mTvValueStatic.setText(NumericUtil.getStockValue(mItem.getFixed_assets()));
            mTvValueTotal.setText(NumericUtil.getStockValue(mItem.getTotal_value()));
            mTvDebtDynamic.setText(NumericUtil.getStockValue(mItem.getLiquid_debt()));
            mTvDebtStatic.setText(NumericUtil.getStockValue(mItem.getFixed_debt()));
            mTvDebtTotal.setText(NumericUtil.getStockValue(mItem.getTotal_debt()));
            mTvBenefitNotDistr.setText(NumericUtil.getStockValue(mItem.getUndistributed_profit()));
            mTvFund.setText(NumericUtil.getStockValue(mItem.getPublic_reserve_fund()));
            mTvBenifitForHolder.setText(NumericUtil.getStockValue(mItem.getHolder_interest()));
        }
    }
}
