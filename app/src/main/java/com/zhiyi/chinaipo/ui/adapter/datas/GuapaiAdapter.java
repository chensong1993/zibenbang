package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;

import java.util.List;

/**
 *
 */
public class GuapaiAdapter extends RecyclerView.Adapter<GuapaiAdapter.GuapaiViewHolder> {

    private Context mContext;
    private List<String> mList;

    public GuapaiAdapter(Context context, List<String> mList1) {
        this.mContext = context;
        this.mList = mList1;
    }


    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_guapais, parent, false);
        return new GuapaiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {
//        holder.setShujuTop(mList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mList.size() > 0 ? 1 : 0;
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {

        TextView count, count2, count3, count4, count5;


        public GuapaiViewHolder(View v) {
            super(v);
            //挂牌数量
            count = (TextView) v.findViewById(R.id.tv_count);
            //总股本
            count2 = (TextView) v.findViewById(R.id.tv_count1);
            //流通股本
            count3 = (TextView) v.findViewById(R.id.tv_count2);
            //成交股数
            count4 = (TextView) v.findViewById(R.id.tv_count3);
            //成交金额
            count5 = (TextView) v.findViewById(R.id.tv_count4);

        }

        void refreshView() {
            //转换单位
            count.setText(mList.get(1));
            count2.setText(mList.get(2));
            count3.setText(mList.get(3));
            count4.setText(mList.get(4));
            count5.setText(mList.get(5));
//            mTopDealCash.setText(topMarkets.get(5).getVals());

//            count.setText(shujuTop.getZhuce()+"家");
//            ConversionUnitUtil.doubleAndString(shujuTop.getCount1(),count2);
//            ConversionUnitUtil.doubleAndString(shujuTop.getCount2(),count3);
//            ConversionUnitUtil.doubleAndString(shujuTop.getCount3(),count4);
//            ConversionUnitUtil.doubleAndString(shujuTop.getCount4(),count5);
        }
    }

}
