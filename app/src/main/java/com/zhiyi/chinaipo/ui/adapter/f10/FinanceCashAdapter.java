package com.zhiyi.chinaipo.ui.adapter.f10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinanceCashAdapter extends RecyclerView.Adapter<FinanceCashAdapter.ListViewHolder> {

    private Context mContext;
    private List<CashFlowEntity> mCashList;
    private int viewType;

    public FinanceCashAdapter(Context context, List<CashFlowEntity> mCashs, int viewType) {
        this.mContext = context;
        this.mCashList = mCashs;
        this.viewType = viewType;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_f10_xianjinliuliangbiao, parent, false);
        if (viewType == Constants.FINANCE_LIST_CONTENT_ALL) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_details_lv_f10_xianjinliuliangbiao, parent, false);
        }
        return new ListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if (mCashList != null) {
            holder.setItem(mCashList.get(position));
            holder.refreshView();
        }

    }

    @Override
    public int getItemCount() {
        if (viewType == Constants.FINANCE_LIST_CONTENT_ALL) {
            return mCashList.size();
        } else if (mCashList.size() > 2) {
            return 2;
        }
        return 0;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_meiguxianjinliu)
        TextView mTvCashEachShare;
        @BindView(R.id.tv_yingshouxianjin)
        TextView mTvCashIncome;
        @BindView(R.id.tv_touzixianjin)
        TextView mTvCashInvested;
        @BindView(R.id.tv_chouzixianjin)
        TextView mTvCashRefund;

        CashFlowEntity mItem;

        public ListViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void setItem(CashFlowEntity item) {
            this.mItem = item;
        }

        void refreshView() {
            mTvCashEachShare.setText(NumericUtil.getStockValue(mItem.getCash_each_share()));
            mTvCashIncome.setText(NumericUtil.getStockValue(mItem.getCash_operation()));
            mTvCashInvested.setText(NumericUtil.getStockValue(mItem.getCash_invest()));
            mTvCashRefund.setText(NumericUtil.getStockValue(mItem.getCash_raised()));
        }
    }
}
