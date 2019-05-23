package com.zhiyi.chinaipo.ui.adapter.market;


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

//import com.zhiyi.chinaipo.ui.activity.slideabletableonclick.TableParticularsActivity;
//import com.zhiyi.chinaipo.util.ConversionUnitUtil;

/**
 * 行情中心的涨幅排行
 */

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.MarketViewHolder> {

    private List<StockPriceEntity> mList;
    private Context context;

    public StockListAdapter(Context context, List<StockPriceEntity> mList) {
        this.context = context;
        this.mList = mList;
    }


    @Override
    public StockListAdapter.MarketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhangfubang, parent, false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarketViewHolder holder, int position) {
        holder.setItem(mList.get(position));
        holder.refreshview();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    int position = holder.getAdapterPosition();
                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setContext(context)
                            .setStockName(mList.get(position).getStock_name())
                            .setStockCode(mList.get(position).getStock_code()));

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size() < 11 ? mList.size() : 10;
    }

    class MarketViewHolder extends RecyclerView.ViewHolder {
        TextView gupiao, gupiaohao, time, zhuanrang, zhuce;
        StockPriceEntity mItem;

        public MarketViewHolder(final View v) {
            super(v);
            //股票名称
            gupiao = (TextView) v.findViewById(R.id.tv_gupiao);
            //当前价
            time = (TextView) v.findViewById(R.id.tv_time);
            //股票号
            gupiaohao = (TextView) v.findViewById(R.id.gupaihao);
            //涨跌幅
            zhuanrang = (TextView) v.findViewById(R.id.tv_zhuanrang);
            //成交额
            zhuce = (TextView) v.findViewById(R.id.zhuce);
        }

        public void setItem(StockPriceEntity item) {
            this.mItem = item;
        }

        void refreshview() {

            //单位转换工具类 ConversionUnitUtil
            //公司名称
            gupiao.setText(mItem.getStock_name());
            //当前价
            time.setText(NumericUtil.getStockValue(mItem.getLatest_price()));
//            ConversionUnitUtil.doubleAndString(mItem.getCount1(), time);
//            //公司代码
            gupiaohao.setText(mItem.getStock_code());
//            gupiaohao.setText(mItem.getGupiaohao());
//            //涨跌幅
            zhuanrang.setText(NumericUtil.getPercentDirect(mItem.getChng_pct()));
//            ConversionUnitUtil.Percent(mItem.getCount2(), zhuanrang);
//            // zhuanrang.setText(mItem.getZhuanrang() + "%");
//            //成交额
            zhuce.setText(NumericUtil.getStockValue(mItem.getLatest_turnover()));
//            ConversionUnitUtil.doubleAndString(mItem.getCount3(), zhuce);
            // zhuce.setText(mItem.getZhuce());
            if (NumericUtil.stringToDouble(mItem.getChng()) < 0) {
                time.setTextColor(ContextCompat.getColor(context, R.color.green));
                zhuanrang.setTextColor(ContextCompat.getColor(context, R.color.green));
            } else {
                time.setTextColor(ContextCompat.getColor(context, R.color.red));
                zhuanrang.setTextColor(ContextCompat.getColor(context, R.color.red));
            }
        }
    }

}