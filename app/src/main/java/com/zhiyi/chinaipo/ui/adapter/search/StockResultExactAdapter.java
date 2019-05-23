package com.zhiyi.chinaipo.ui.adapter.search;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockResultExactAdapter extends RecyclerView.Adapter<StockResultExactAdapter.ListViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<StockPriceEntity> mStockItem;

    public StockResultExactAdapter(Context context, List<StockPriceEntity> mStock) {
        mContext = context;
        this.mStockItem = mStock;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int stockCount) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_stock_only_one, parent, false);
        return new ListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.mTvStockFocus.setTag(position);
        if (mStockItem != null && mStockItem.size() == 1) {
            holder.setItem(mStockItem.get(0));
            holder.refreshView();
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setStockCode(mStockItem.get(0).getStock_code())
                            .setStockName(mStockItem.get(0).getStock_name())
                            .setContext(mContext)
                    );
                }
            }
        });
        holder.mTvStockFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onClick(view, ViewName.PRACTISE, position);
                LogUtil.i("13465", 0 + "");
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mStockItem.size() == 1) {
            return 1;
        }

        return mStockItem == null ? 0 : mStockItem.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        View mContainerView;
        @BindView(R.id.tv_search_stockname)
        TextView mTvStockName;
        @BindView(R.id.tv_search_stockcode)
        TextView mTvStockCode;
        @BindView(R.id.tv_stock_price)
        TextView mTvStockPrice;
        @BindView(R.id.tv_stock_change)
        TextView mTvStockChange;
        @BindView(R.id.tv_stock_change_percentage)
        TextView mTvStockChangePercent;
        @BindView(R.id.tv_stock_stockvalue)
        TextView mTvStockValue;
        @BindView(R.id.tv_search_focus)
        TextView mTvStockFocus;
        @BindView(R.id.ll_stock)
        LinearLayout linearLayout;
        StockPriceEntity mItem;


        public ListViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mContainerView = v;
            linearLayout.setOnClickListener(StockResultExactAdapter.this);
            mTvStockFocus.setOnClickListener(StockResultExactAdapter.this);
        }


        public void setItem(StockPriceEntity item) {
            this.mItem = item;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        void refreshView() {
            if (mItem != null) {
                mTvStockName.setText(mItem.getStock_name());
                mTvStockCode.setText(mItem.getStock_code());
                mTvStockPrice.setText(NumericUtil.getStockValue(mItem.getLatest_price()));
                mTvStockChange.setText(NumericUtil.getStockValue(mItem.getChng()));
                mTvStockChangePercent.setText(NumericUtil.getPercentDirect(mItem.getChng_pct()));
                mTvStockValue.setText(NumericUtil.getStockValue(mItem.getTotal_value()));
                if (NumericUtil.stringToDouble(mItem.getChng()) < 0) {
                    mTvStockPrice.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    mTvStockChange.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    mTvStockChangePercent.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    mTvStockValue.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                } else if (NumericUtil.stringToDouble(mItem.getChng()) > 0) {
                    mTvStockPrice.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    mTvStockChange.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    mTvStockChangePercent.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    mTvStockValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                }

            }
            for (Iterator iter = App.getInstance().getStockCodeDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
                StockCodeEntity element = (StockCodeEntity) iter.next();

                if (mItem.getStock_code().equals(element.getCode())) {
                    mTvStockFocus.setText("已关注");
                    mTvStockFocus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yiguanzhu));

                }
            }
        }

    }

    ////////////////////////////以下为item点击处理///////////////////////////////
    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, ViewName viewName, int position);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * item里面有多个控件可以点击
     */
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            switch (view.getId()) {
                case R.id.ll_stock:
                    mOnItemClickListener.onClick(view, ViewName.ITEM, 0);
                    break;
                default:
                    break;
            }
        }

    }

}
