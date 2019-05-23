package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.zhiyi.chinaipo.util.ConversionUnitUtil;

/**
 * 数据模块 ，       新股挂牌的数据
 */
public class NewStocksAdapter extends RecyclerView.Adapter<NewStocksAdapter.GuapaiViewHolder> {

    private Context mContext;
    private List<StockPriceEntity> mList;
    private int viewType;

    public NewStocksAdapter(Context context, List<StockPriceEntity> mList1, int viewType) {
        this.mContext = context;
        this.mList = mList1;
        this.viewType = viewType;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shujuxinsanbans, parent, false);
        return new GuapaiViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {
        holder.setShujuTop(mList.get(position));
        holder.refreshView();
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setContext(mContext)
                            .setStockCode(mList.get(position).getStock_code())
                            .setStockName(mList.get(position).getStock_name())
                    );
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (viewType == Constants.STOCK_DATA_LIST_CONTENT_ALL) {
            return mList.size();
        }
        return mList.size() < 5 ? mList.size() : 5;
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {

        TextView gupiao, gupiaohao, time, zhuanrang, zhuce;
        View mContainerView;
        StockPriceEntity shujuTop;

        public GuapaiViewHolder(View v) {
            super(v);
            mContainerView = v;
            gupiao = (TextView) v.findViewById(R.id.tv_gupiao);
            time = (TextView) v.findViewById(R.id.tv_time);
            gupiaohao = (TextView) v.findViewById(R.id.gupaihao);
            zhuanrang = (TextView) v.findViewById(R.id.tv_zhuanrang);
            zhuce = (TextView) v.findViewById(R.id.zhuce);
        }

        public void setShujuTop(StockPriceEntity shujuTop) {
            this.shujuTop = shujuTop;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        void refreshView() {
            //挂牌名称
            gupiao.setText(shujuTop.getStock_name());
            //挂牌日期
            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = formats.parse(shujuTop.getRegistered_date());
                formats = new SimpleDateFormat("yyyy-MM-dd");
                time.setText(formats.format(dates));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //挂牌号
            gupiaohao.setText(shujuTop.getStock_code());
            //转让方式
            if (shujuTop.getDeal_type().size() > 0) {
                zhuanrang.setText(shujuTop.getDeal_type().get(0));
            } else {
                zhuanrang.setText("-");
            }
            //注册资本
            zhuce.setText(NumericUtil.getStockValue(shujuTop.getBooked_value()));
//            ConversionUnitUtil.doubleAndString(shujuTop.getCount1(),zhuce);
           // zhuce.setText(shujuTop.getZhuce());
        }
    }

}
