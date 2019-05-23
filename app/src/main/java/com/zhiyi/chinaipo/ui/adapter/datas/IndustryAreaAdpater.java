package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

public class IndustryAreaAdpater extends RecyclerView.Adapter<IndustryAreaAdpater.ListViewHolder> {

    private List<StasIndustryEntity> mList;
    private Context mContext;

    public IndustryAreaAdpater(List<StasIndustryEntity> dataList, Context context) {
        this.mList = dataList;
        this.mContext = context;
    }

    @Override
    public IndustryAreaAdpater.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_data_annular, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IndustryAreaAdpater.ListViewHolder holder, int position) {
        holder.setItem(mList.get(position));
        if (position == 5) {
            holder.tvNumber.setText(R.string.qitadiqu);
            Double aDouble = 100.00 - (mList.get(0).getPercentage() + mList.get(1).getPercentage() + mList.get(2).getPercentage() + mList.get(3).getPercentage() + mList.get(4).getPercentage());
            holder.tvNumber1.setText("" + NumericUtil.getPercentDirect(aDouble));
        } else {
            holder.tvNumber.setText(mList.get(position).getIndu_name_2());
            holder.tvNumber1.setText("" + NumericUtil.getPercentDirect(mList.get(position).getPercentage()));
        }
        switch (position){
            case 0:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
                break;
            case 1:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.limegreen));
                break;
            case 2:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.deepskyblue));
                break;
            case 3:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.custom_yellow_text));
                break;
            case 4:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.darkviolet));
                break;
            default:
                holder.tvYuan.setBackgroundColor(ContextCompat.getColor(mContext,R.color.hui2));
                break;
        }
        // holder.refreshView();

    }

    @Override
    public int getItemCount() {
        if (mList != null && mList.size() >= 6) {
            return 6;
        } else {
            return mList == null ? 0 : mList.size();
        }

    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvNumber1,tvYuan;
        StasIndustryEntity dataItem;

        public ListViewHolder(View v) {
            super(v);
            tvNumber = (TextView) v.findViewById(R.id.tv_diqu);
            tvNumber1 = (TextView) v.findViewById(R.id.tv_baifenbi);
            tvYuan=v.findViewById(R.id.tv_yuan);
        }

        public void setItem(StasIndustryEntity dataItem) {
            this.dataItem = dataItem;
        }

    }
}
