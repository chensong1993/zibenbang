package com.zhiyi.chinaipo.ui.adapter.market;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.details.ShareHoldersEntity;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

/**
 * 行情模块 ，创新里面的股东的十大股东
 */
public class TenShareholderAdapter extends RecyclerView.Adapter<TenShareholderAdapter.GuapaiViewHolder> {

    private Context mContext;
    private List<ShareHoldersEntity> mList;

    public TenShareholderAdapter(Context context, List<ShareHoldersEntity> mList1) {
        mContext = context;
        mList = mList1;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_hangqing_gudong, parent, false);
        return new GuapaiViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {
        holder.setShujuTop(mList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mCount, mBaifenbi, mZengjin;
        ShareHoldersEntity shujuTop;

        public GuapaiViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.tv_xingming);
            mCount = (TextView) v.findViewById(R.id.tv_gufen);
            mBaifenbi = (TextView) v.findViewById(R.id.tv_baifenbi);
            mZengjin = (TextView) v.findViewById(R.id.tv_zengjin);

        }

        public void setShujuTop(ShareHoldersEntity shujuTop) {
            this.shujuTop = shujuTop;
        }

        void refreshView() {
            //姓名
            mName.setText(shujuTop.getHold_name());
            //股份
            mCount.setText(NumericUtil.getStockValue(shujuTop.getHold_num()));
            //占比
            mBaifenbi.setText(NumericUtil.getPercentDirect(shujuTop.getHold_pct()));
            //bubian
            if (shujuTop.getChange() == null) {
                mZengjin.setText("不变");
            } else {
                mZengjin.setText(NumericUtil.getPercentDirect(shujuTop.getChange()));
            }
        }
    }

}
