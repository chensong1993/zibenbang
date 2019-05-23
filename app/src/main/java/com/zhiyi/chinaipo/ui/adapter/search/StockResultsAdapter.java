package com.zhiyi.chinaipo.ui.adapter.search;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockResultsAdapter extends RecyclerView.Adapter<StockResultsAdapter.ListViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<StockPriceEntity> mStockList;
    StockPriceEntity stockPriceEntity;
    private int mSearchType;
    private View mContainerView;

    public StockResultsAdapter(Context context, List<StockPriceEntity> mStocks) {
        mContext = context;
        this.mStockList = mStocks;
    }

    public StockResultsAdapter(Context mContext, List<StockPriceEntity> mStockList, int mSearchType) {
        this.mContext = mContext;
        this.mStockList = mStockList;
        this.mSearchType = mSearchType;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int stockCount) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_stocks, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.mTvStovkFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    mOnItemClickListener.onClick(view, ViewName.PRACTISE, position);
                    LogUtil.i("13465", position + "");
                }
            }
        });
        if (mStockList != null) {
            holder.setItem(mStockList.get(position));
            holder.refreshView();

        }
    }

    @Override
    public int getItemCount() {
        if (mSearchType == Constants.SEARCHING_TYPE_STOCK) {
            return mStockList == null ? 0 : mStockList.size();
        } else if (mSearchType == Constants.SEARCHING_TYPE_ALL && mStockList.size() > 5) {
            return 5;
        } else {
            return mStockList == null ? 0 : mStockList.size();
        }
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_search_stockname)
        TextView mTvStockName;
        @BindView(R.id.tv_search_stockcode)
        TextView mTvStockCode;
        @BindView(R.id.tv_search_focus)
        TextView mTvStovkFocus;
        @BindView(R.id.rl_stock)
        RelativeLayout mLayout;
        StockPriceEntity mItem;

        public ListViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mLayout.setOnClickListener(StockResultsAdapter.this);
            mTvStovkFocus.setOnClickListener(StockResultsAdapter.this);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setContext(mContext)
                            .setStockName(mItem.getStock_name())
                            .setStockCode(mItem.getStock_code())
                    );
                }
            });
            mContainerView = v;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        public void setItem(StockPriceEntity item) {
            this.mItem = item;
            stockPriceEntity = item;
        }

        void refreshView() {
            if (mItem != null) {
                mTvStockName.setText(mItem.getStock_name());
                mTvStockCode.setText(mItem.getStock_code());
                for (Iterator iter = App.getInstance().getStockCodeDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
                    StockCodeEntity element = (StockCodeEntity) iter.next();
                    if (mItem.getStock_code().equals(element.getCode())) {
                        mTvStovkFocus.setText("已关注");
                        mTvStovkFocus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.yiguanzhu));
                    }
                }
            }
        }

    }

    ////////////////////////////以下为item点击处理///////////////////////////////
    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, ViewName viewName, int position);
    }

    private StockResultsAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(StockResultsAdapter.OnRecyclerViewItemClickListener listener) {
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
                case R.id.rl_stock:
                    mOnItemClickListener.onClick(view, ViewName.ITEM, (Integer) view.getTag());
                    break;
                default:
                    break;
            }
        }

    }
}
