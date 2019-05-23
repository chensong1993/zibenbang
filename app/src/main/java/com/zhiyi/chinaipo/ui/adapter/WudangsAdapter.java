package com.zhiyi.chinaipo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.List;

/**
 * 五档样式
 */
public class WudangsAdapter extends RecyclerView.Adapter<WudangsAdapter.TopsViewHolder> {

    private Context mContext;
    private List<Pair<String, String>> mList;

    public WudangsAdapter(Context context, List<Pair<String, String>> mList1) {
        this.mContext = context;
        this.mList = mList1;
    }

    @Override
    public TopsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_wudang, parent, false);
        return new TopsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopsViewHolder holder, int position) {
        holder.setTopsData(mList.get(position));
        holder.refreshView();

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class TopsViewHolder extends RecyclerView.ViewHolder {

        TextView mCount, mMoney;
        Pair<String, String> mPair;

        public TopsViewHolder(View v) {
            super(v);
            mCount = (TextView) v.findViewById(R.id.tv_mai1);
            mMoney = (TextView) v.findViewById(R.id.tv_mai2);
        }

        public void setTopsData(Pair<String, String> mPair) {
            this.mPair = mPair;
        }

        void refreshView() {

            mCount.setText(mPair.first);
            LogUtil.i("refreshView", mPair.first);
            mMoney.setText(mPair.second);

//            double i= Double.valueOf(mPair.second);
//            if(i>4.80){
//                mCount.setTextColor(ContextCompat.getColor(mContext, R.color.red));
//            }else if (i<4.80){
//                mCount.setTextColor(ContextCompat.getColor(mContext, R.color.green));
//            }else {
//                mCount.setTextColor(ContextCompat.getColor(mContext, R.color.black));
//            }
        }
    }

}
