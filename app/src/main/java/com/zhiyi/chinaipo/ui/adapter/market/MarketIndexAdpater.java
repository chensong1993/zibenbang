package com.zhiyi.chinaipo.ui.adapter.market;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

//import com.zhiyi.chinaipo.ui.adapter.bean.MarketIndexEntity;
//import com.zhiyi.chinaipo.util.ConversionUnitUtil;

/**
 * 行情顶部两个的样式
 * Created by admin on 2017/9/4.
 */
public class MarketIndexAdpater extends RecyclerView.Adapter<MarketIndexAdpater.SanbanViewHolder> {

    private MarketIndexEntity mDatas;
    private Context mContext;
    public MarketIndexAdpater(Context mContext, MarketIndexEntity mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }


    @Override
    public MarketIndexAdpater.SanbanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanbanchengzhi,parent,false);
        return new SanbanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MarketIndexAdpater.SanbanViewHolder holder, int position) {
        holder.setItem(mDatas);
        holder.refreshView();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class SanbanViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvNumber,tvNumber1,tvNumbe2;
        MarketIndexEntity mValue;
        public SanbanViewHolder(View v) {
            super(v);
            tvTitle= (TextView) v.findViewById(R.id.tv_zuoshi);
            tvNumber= (TextView) v.findViewById(R.id.tv_jiage);
            tvNumber1= (TextView) v.findViewById(R.id.tv_baifenbi);
            tvNumbe2= (TextView) v.findViewById(R.id.tv_baifenbi1);
        }

        public void setItem(MarketIndexEntity mValue) {
            this.mValue = mValue;
        }
        void refreshView() {

            //单位转换工具类 ConversionUnitUtil
            //名称
            if (mValue != null) {
                tvTitle.setText(mValue.getIndx_sname());
                //数量
                tvNumber.setText(NumericUtil.getStockValue(mValue.getTclose()));
//            ConversionUnitUtil.doubleAndString(mValue.getCount1(),tvNumber);
//           // tvNumber.setText(mValue.getGupiaohao());
//            //单价
//            ConversionUnitUtil.doubleAndString(mValue.getCount2(),tvNumber1);
                tvNumber1.setText(NumericUtil.getStockValue(mValue.getChng()));
                if(NumericUtil.isNullString(mValue.getChng_pct())){
                    tvNumbe2.setText("--");
                }else {
                    tvNumbe2.setText(mValue.getChng_pct()+"%");
                }


                Log.i("refreshView", mValue.getChng_pct()+"refreshView: ");
//            //百分比
//            ConversionUnitUtil.Percent(mValue.getCount3(),tvNumbe2);
                //  tvNumbe2.setText("0."+mValue.getZhuce()+"%");
                if (NumericUtil.stringToDouble(mValue.getChng()) > 0) {
                    tvNumber.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    tvNumber1.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    tvNumbe2.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                } else {
                    tvNumber.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    tvNumber1.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    tvNumbe2.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                }
            }
        }
    }
}
