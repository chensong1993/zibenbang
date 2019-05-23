package com.zhiyi.chinaipo.ui.adapter.slidetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

/**
 * 从列表里面进来后的F10
 */
public class TableNameAdapter extends RecyclerView.Adapter<TableNameAdapter.GuapaiViewHolder> {
    private Context mContext;
    private List<StockPriceEntity> mList;

    public TableNameAdapter(Context mContext, List<StockPriceEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_good_name, parent, false);
        return new GuapaiViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {
        holder.setmStock(mList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {

        TextView tv_goodname, tv_guapaihao;
        StockPriceEntity mStock;

        public GuapaiViewHolder(View view) {
            super(view);
            tv_goodname = (TextView) view.findViewById(R.id.tv_name);
            tv_guapaihao = (TextView) view.findViewById(R.id.guapaihao);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        StockDetailActivity.launch(new StockDetailActivity.Builder()
                                .setContext(mContext)
                                .setStockName(mStock.getStock_name())
                                .setStockCode(mStock.getStock_code()));
                    }
                }
            });
        }

        public void setmStock(StockPriceEntity mStock) {
            this.mStock = mStock;
        }

        void refreshView() {

            //公司名称
            tv_goodname.setText(mStock.getStock_name());
            //公司代号
            tv_guapaihao.setText(mStock.getStock_code());

        }
    }

    public void add(List<StockPriceEntity> mPriceEntityList) {
        mList = mPriceEntityList;
        notifyDataSetChanged();
    }

    public void addAll(List<StockPriceEntity> mPriceEntityList) {
        mList.addAll(mPriceEntityList);
        notifyDataSetChanged();
    }
}
