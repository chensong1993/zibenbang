package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.ui.activity.tables.TableMultiActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

/**
 * 数据模块的挂牌公司地区分布的内容
 */
public class CompanyOrRegionAdapter extends RecyclerView.Adapter<CompanyOrRegionAdapter.GuapaiViewHolder> {

    private Context mContext;
    private List<StasAreaEntity> mList;
    private String listType = "area";
//    private List<StasAreaEntity> mList;

    public CompanyOrRegionAdapter(Context context, List<StasAreaEntity> mList1) {
        this.mContext = context;
        this.mList = mList1;
    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_gongsi, parent, false);
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
                    TableMultiActivity.launch(new TableMultiActivity.Builder()
                            .setContext(mContext)
                            .setExtKey("area")
                            .setTabTitle(0)
                            .setSortBy(Constants.STOCK_SORT_BY_CHNG_PCT)
                            .setTitle(mList.get(position).getArea_name())
                            .setExtValue("" + mList.get(position).getArea_code())
                    );
                    LogUtil.i("getArea_code", ": " + mList.get(position).getArea_code());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {
        private View mContainerView;
        private TextView mName, mCount, mPercent;
        private StasAreaEntity shujuTop;

        public GuapaiViewHolder(View v) {
            super(v);
            mContainerView = v;
            mName = (TextView) v.findViewById(R.id.tv_diquorming);
            mCount = (TextView) v.findViewById(R.id.tv_shuliangor);
            mPercent = (TextView) v.findViewById(R.id.tv_zhanbior);

        }

        public void setShujuTop(StasAreaEntity shujuTop) {
            this.shujuTop = shujuTop;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        void refreshView() {
            //地区名称
            mName.setText(shujuTop.getArea_name());
            //挂牌数量
            mCount.setText("" + shujuTop.getTotal_company());
            //行业占比
            mPercent.setText(NumericUtil.getPercentDirect(shujuTop.getPercentage()));
        }
    }

}
