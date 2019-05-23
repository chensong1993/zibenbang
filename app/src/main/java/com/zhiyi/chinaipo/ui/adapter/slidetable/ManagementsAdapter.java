package com.zhiyi.chinaipo.ui.adapter.slidetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
//import com.zhiyi.chinaipo.ui.activity.slideabletableonclick.F10Activity;
import com.zhiyi.chinaipo.models.entity.details.ManagementEntity;
//import com.zhiyi.chinaipo.util.ConversionUnitUtil;
import com.zhiyi.chinaipo.util.NumericUtil;

import java.util.List;

/**
 * 从列表里面进来后的资料
 */
public class ManagementsAdapter extends RecyclerView.Adapter<ManagementsAdapter.ManagersViewHolder> {

    private Context mContext;
    private List<ManagementEntity> mList;

    public ManagementsAdapter(Context context, List<ManagementEntity> mList1) {
        this.mContext = context;
        this.mList = mList1;
    }

    @Override
    public ManagersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_zhongxin_ziliaos1, parent, false);
        return new ManagersViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ManagersViewHolder holder, int position) {
        holder.setShujuTop(mList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ManagersViewHolder extends RecyclerView.ViewHolder {

        TextView management, name, percentage;
        ManagementEntity shujuTop;

        public ManagersViewHolder(View v) {
            super(v);
            //董秘之类
            management = (TextView) v.findViewById(R.id.tv_management);
            name = (TextView) v.findViewById(R.id.tv_name);
            percentage = (TextView) v.findViewById(R.id.tv_percentage);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mContext.startActivity(new Intent(mContext, F10Activity.class));
                }
            });
        }

        public void setShujuTop(ManagementEntity shujuTop) {
            this.shujuTop = shujuTop;
        }

        void refreshView() {
//            ConversionUnitUtil.Percent(shujuTop.getCount1(),percentage);
            management.setText(shujuTop.getPost());
            name.setText(shujuTop.getMng_name());
            percentage.setText(NumericUtil.getStockValue(shujuTop.getEnd_vol()));
        }
    }
}
