package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.ui.activity.datas.NewStockActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
//import com.zhiyi.chinaipo.ui.activity.datamodule.DataNewShareActivity;

/**
 * 数据模块，新股挂牌（新三板）头部
 */
public class XinguHeaderAdapter extends RecyclerView.Adapter<XinguHeaderAdapter.GuapaiViewHolder> {
    private Context mContext;

    public XinguHeaderAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shuju_head_xingu, parent, false);
        return new GuapaiViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {


        public GuapaiViewHolder(View v) {
            super(v);

            //recycleview的点击事件
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        mContext.startActivity(new Intent(mContext, NewStockActivity.class));
                    }
                }
            });
        }


    }


}
