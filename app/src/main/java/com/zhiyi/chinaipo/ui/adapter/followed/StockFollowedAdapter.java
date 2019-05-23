package com.zhiyi.chinaipo.ui.adapter.followed;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chensong
 * @date 2018/7/23 17:26
 */
public class StockFollowedAdapter extends RecyclerView.Adapter<StockFollowedAdapter.StockCodeViewHolder> {

    private Context mContext;
    private List<StockPriceEntity> mCodeEntities;

    public StockFollowedAdapter(Context mContext, List<StockPriceEntity> mCodeEntities) {
        this.mContext = mContext;
        this.mCodeEntities = mCodeEntities;
    }

    @Override
    public StockFollowedAdapter.StockCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_zhangfubang, parent, false);

        return new StockCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockFollowedAdapter.StockCodeViewHolder holder, int position) {
        holder.setStockCodeEntity(mCodeEntities.get(position));
        holder.refreshView();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    int position = holder.getAdapterPosition();
                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setContext(mContext)
                            .setStockName(mCodeEntities.get(position).getStock_name())
                            .setStockCode(mCodeEntities.get(position).getStock_code()));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCodeEntities == null ? 0 : mCodeEntities.size();
    }

    public class StockCodeViewHolder extends RecyclerView.ViewHolder {
        StockPriceEntity stockEntity;
        @BindView(R.id.tv_gupiao)
        TextView mStockName;
        @BindView(R.id.gupaihao)
        TextView mStockCode;
        @BindView(R.id.tv_time)
        TextView mStockD;
        @BindView(R.id.tv_zhuanrang)
        TextView mStockZ;
        @BindView(R.id.zhuce)
        TextView mStockC;

        public StockCodeViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        public void setStockCodeEntity(StockPriceEntity stockCodeEntity) {
            this.stockEntity = stockCodeEntity;
        }

        void refreshView() {
            mStockName.setText(stockEntity.getStock_name());
            mStockCode.setText(stockEntity.getStock_code());
            mStockD.setText(NumericUtil.getStockValue(stockEntity.getLatest_price()));
            mStockZ.setText(NumericUtil.getPercentDirect(stockEntity.getChng_pct()));
            mStockC.setText(NumericUtil.getStockValue(stockEntity.getTotal_value()));

            if (NumericUtil.stringToDouble(stockEntity.getChng_pct()) < 0) {
                mStockD.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                mStockZ.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            } else {
                mStockD.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                mStockZ.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
        }
    }
}
