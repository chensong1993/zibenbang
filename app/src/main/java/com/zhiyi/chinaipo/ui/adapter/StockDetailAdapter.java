package com.zhiyi.chinaipo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

/**
 * 可滑动table点击进入后的最上面的数据适配器
 */
public class StockDetailAdapter extends RecyclerView.Adapter<StockDetailAdapter.StockDetailHolder> {
    private Context mContext;
    private List<StockPriceEntity> mStockList;

    public StockDetailAdapter(Context context, List<StockPriceEntity> mStockList) {
        this.mContext = context;
        this.mStockList = mStockList;
    }

    @Override
    public StockDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_zhongxinhead, parent, false);
        return new StockDetailHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StockDetailHolder holder, int position) {
        if (mStockList.size() > 0 ) {
            holder.setStockDetail(mStockList.get(0));
        }
        holder.refreshView();
    }

    @Override
    public int getItemCount() {
        return 1;
//        if (mStockList == null) {
//            return 0;
//        } else {
//            return 1;
//        }
    }

    class StockDetailHolder extends RecyclerView.ViewHolder {

        private TextView name4, name5, name6, name7, name8, name9, name10, name11, name12, name13, name14, name15, name16, name17, name18;
        StockPriceEntity mStock;

        public StockDetailHolder(View v) {
            super(v);
            //从上到下从左到右

            //今开
            name4 = (TextView) v.findViewById(R.id.tv_jinkai);
            //最高价
            name5 = (TextView) v.findViewById(R.id.tv_zuigaojia);
            //成交额
            name6 = (TextView) v.findViewById(R.id.tv_chengjiaoe);
            //昨收
            name7 = (TextView) v.findViewById(R.id.tv_zuoshou);
            //最低价
            name8 = (TextView) v.findViewById(R.id.tv_zuidijia);
            //成交量
            name9 = (TextView) v.findViewById(R.id.tv_chengjiaoliang);
            //涨跌额
            name10 = (TextView) v.findViewById(R.id.tv_yingshou);
            //流通股
            name11 = (TextView) v.findViewById(R.id.tv_liutonggu);
            //总股本
            name12 = (TextView) v.findViewById(R.id.tv_zongguben);
            //涨跌幅
            name13 = (TextView) v.findViewById(R.id.tv_zhangdiefu);
            //流值
            name14 = (TextView) v.findViewById(R.id.tv_liuzhi);
            //总市值
            name15 = (TextView) v.findViewById(R.id.tv_zongshizhi);
            //市盈静
            name16 = (TextView) v.findViewById(R.id.tv_shiyingjing);
            //市盈动
            name17 = (TextView) v.findViewById(R.id.tv_shiyingdong);
            //振幅
            name18 = (TextView) v.findViewById(R.id.tv_zhenfu);
        }

        public void setStockDetail(StockPriceEntity mStock) {
            this.mStock = mStock;
        }

        void refreshView() {

            if (mStock != null) {
                name4.setText(NumericUtil.getStockValue(mStock.getToday_open()));
                name5.setText(NumericUtil.getStockValue(mStock.getHighest_price()));
                name6.setText(NumericUtil.getStockValue(mStock.getLatest_turnover()));
                name7.setText(NumericUtil.getStockValue(mStock.getLast_close()));
                name8.setText(NumericUtil.getStockValue(mStock.getLowest_price()));
                name9.setText(NumericUtil.getStockValue(mStock.getLatest_volume()));
                name10.setText(NumericUtil.getStockValue(mStock.getChng()));
                name11.setText(NumericUtil.getStockValue(mStock.getShares_flow()));
                name12.setText(NumericUtil.getStockValue(mStock.getTotal_volume()));
                name13.setText(NumericUtil.getPercentDirect(mStock.getChng_pct()));
                name14.setText(NumericUtil.getStockValue(mStock.getValues_flow()));
                name15.setText(NumericUtil.getStockValue(mStock.getTotal_value()));
                name16.setText(NumericUtil.getStockValue(mStock.getStatic_pe()));
                name17.setText(NumericUtil.getStockValue(mStock.getDynamic_pe()));
                name18.setText(NumericUtil.getPercentDirect(mStock.getSwg()));
            }
        }
    }
}
