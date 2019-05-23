package com.zhiyi.chinaipo.ui.adapter.slidetable;

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

/**
 * 从列表里面进来后的F10
 */
public class TableContentAdapter extends RecyclerView.Adapter<TableContentAdapter.GuapaiViewHolder> {
    private Context mContext;
    private List<StockPriceEntity> mList;

    public TableContentAdapter(Context mContext, List<StockPriceEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.table_right_item, parent, false);
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

        TextView tv_barcode;
        TextView tv_icon;
        TextView tv_category;
        TextView tv_spec;
        TextView tv_unit;
        TextView tv_supplier;
        TextView tv_sale_money;
        TextView tv_income_money;
        TextView tv_keep;
        TextView tv_intime;
        TextView tv_online;
        TextView tv_time, tv_z01, tv_z02, tv_z03, tv_z04, tv_z05;
        StockPriceEntity mStock;


        public GuapaiViewHolder(View convertView) {
            super(convertView);
            //当前价
            tv_barcode = (TextView) convertView.findViewById(R.id.tv_table_content_right_item0);
            //涨跌幅
            tv_icon = (TextView) convertView.findViewById(R.id.tv_table_content_right_item1);
            //成交额
            tv_category = (TextView) convertView.findViewById(R.id.tv_table_content_right_item2);
            //成交量
            tv_spec = (TextView) convertView.findViewById(R.id.tv_table_content_right_item3);
            //涨跌额
            tv_unit = (TextView) convertView.findViewById(R.id.tv_table_content_right_item4);
            //营收
            tv_supplier = (TextView) convertView.findViewById(R.id.tv_table_content_right_item5);
            //净利润
            tv_sale_money = (TextView) convertView.findViewById(R.id.tv_table_content_right_item6);
            //流通股本
            tv_income_money = (TextView) convertView.findViewById(R.id.tv_table_content_right_item7);
            //流通市值
            tv_keep = (TextView) convertView.findViewById(R.id.tv_table_content_right_item8);
            //总股本
            tv_intime = (TextView) convertView.findViewById(R.id.tv_table_content_right_item9);
            //总市值
            tv_online = (TextView) convertView.findViewById(R.id.tv_table_content_right_item10);
            //市盈率
            tv_time = (TextView) convertView.findViewById(R.id.tv_table_content_right_item11);
            //每股收益
            tv_z01 = (TextView) convertView.findViewById(R.id.tv_table_content_right_item12);
            //最高价
            tv_z02 = (TextView) convertView.findViewById(R.id.tv_table_content_right_item13);
            //最低价
            tv_z03 = (TextView) convertView.findViewById(R.id.tv_table_content_right_item14);
            //均价
            tv_z04 = (TextView) convertView.findViewById(R.id.tv_table_content_right_item15);
            //振幅
            tv_z05 = (TextView) convertView.findViewById(R.id.tv_table_content_right_item16);
            convertView.setOnClickListener(new View.OnClickListener() {
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

            //判断double的单位数据单位换算的工具类
            //当前价
            //第一个参数是值，第二个参数是对应的textview
//            ConversionUnitUtil.doubleAndString(mStock.getTodayMin(), tv_barcode);
            tv_barcode.setText(NumericUtil.getStockValue(mStock.getLatest_price()));

//            //涨跌幅
//            ConversionUnitUtil.doubleAndString(mStock.getMa10(), tv_icon);
            tv_icon.setText(NumericUtil.getPercentDirect(mStock.getChng_pct()));
//            //成交额 Textnull是判断string是否为空
//            ConversionUnitUtil.doubleAndString(mStock.getPreClosePri(),tv_category);
            tv_category.setText(NumericUtil.getStockValue(mStock.getLatest_turnover()));
//            //成交量
//            ConversionUnitUtil.doubleAndString(mStock.getMa10(), tv_spec);
            tv_spec.setText(NumericUtil.getStockValue(mStock.getLatest_volume()));
//            //涨跌额
//            ConversionUnitUtil.doubleAndString(mStock.getTodayMin(), tv_unit);
            tv_unit.setText(NumericUtil.getStockValue(mStock.getChng()));
//            //tv_unit.setText(String.valueOf(glidingtable.getTodayMin()));
//            //营收
//            ConversionUnitUtil.doubleAndString(mStock.getPreClosePri(), tv_supplier);
            tv_supplier.setText(NumericUtil.getStockValue(mStock.getTotal_income()));
//           // tv_supplier.setText(String.valueOf(glidingtable.getPreClosePri()));
//            //净利润
//            ConversionUnitUtil.doubleAndString(mStock.getOpenPri(), tv_sale_money);
            tv_sale_money.setText(NumericUtil.getStockValue(mStock.getNet_profit()));
//          //  tv_sale_money.setText(String.valueOf(glidingtable.getOpenPri()));
//            //流通股本
//            ConversionUnitUtil.doubleAndString(mStock.getMa20(), tv_keep);
            tv_keep.setText(NumericUtil.getStockValue(mStock.getShares_flow()));
//           // tv_keep.setText(String.valueOf(glidingtable.getMa20() + "亿"));
//            //流通市值
//            ConversionUnitUtil.doubleAndString(mStock.getMa10(), tv_intime);
            tv_intime.setText(NumericUtil.getStockValue(mStock.getValues_flow()));
//           // tv_intime.setText(String.valueOf(glidingtable.getMa10() + "亿"));
//            //总股本
//            ConversionUnitUtil.doubleAndString(mStock.getTodayMin(), tv_income_money);
            tv_income_money.setText(NumericUtil.getStockValue(mStock.getTotal_volume()));
//            //总市值
//            ConversionUnitUtil.doubleAndString(mStock.getClosePri(), tv_online);
            tv_online.setText(NumericUtil.getStockValue(mStock.getTotal_value()));
//            //市盈率
//            ConversionUnitUtil.doubleAndString("-4215.1", tv_time);
            tv_time.setText(NumericUtil.getStockValue(mStock.getPe_ratio()));
//            //每股收益
//            ConversionUnitUtil.doubleAndString(mStock.getOpenPri(), tv_z01);
            tv_z01.setText(NumericUtil.getStockValue(mStock.getProfit_each_share()));
//            //最高价
//            ConversionUnitUtil.doubleAndString(mStock.getClosePri(), tv_z02);
            tv_z02.setText(NumericUtil.getStockValue(mStock.getHighest_price()));
//            //最低价
//            ConversionUnitUtil.doubleAndString(mStock.getTodayMin(), tv_z03);
            tv_z03.setText(NumericUtil.getStockValue(mStock.getLowest_price()));
//            //均价
//            ConversionUnitUtil.doubleAndString(mStock.getClosePri(), tv_z04);
            tv_z04.setText(NumericUtil.getStockValue(mStock.getAvg_price()));
//            //振幅
            tv_z05.setText(NumericUtil.getStockValue(mStock.getSwg()));

            if (NumericUtil.stringToDouble(mStock.getChng()) < 0) {
                tv_unit.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                tv_icon.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            } else {
                tv_unit.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                tv_icon.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }


        }
    }

    public void add(List<StockPriceEntity> mPriceEntityList) {
        mList = mPriceEntityList;
        notifyDataSetChanged();
    }

    public void addAll(List<StockPriceEntity> mPriceEntityList) {
        if (mList == null) {
            add(mPriceEntityList);
        } else {
            mList.addAll(mPriceEntityList);
            notifyDataSetChanged();
        }

    }
}
