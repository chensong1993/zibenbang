package com.zhiyi.chinaipo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.ui.activity.tables.TableMultiActivity;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 数据模块，主办券商和做市排行，律师，会计的样式
 */
public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.ListOrgsViewHolder> {
    private Context mContext;
    private List<StasOrgsEntity> mList;
    private String listType;
    private int viewType;

    public OrganizationAdapter(Context context, List<StasOrgsEntity> mList1, int viewType, String listType) {
        this.mContext = context;
        this.mList = mList1;
        this.viewType = viewType;
        this.listType = listType;
    }

    @Override
    public ListOrgsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shuju_mingcheng, parent, false);
        return new ListOrgsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListOrgsViewHolder holder, int position) {
        if (mList != null) {
            holder.setItem(mList.get(position));
        }
        holder.refreshView();
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    TableMultiActivity.launch(new TableMultiActivity.Builder()
                                    .setContext(mContext)
                                    .setExtKey(listType)
                                    .setSortBy(Constants.STOCK_SORT_BY_CHNG_PCT)
                                    .setExtValue("" + mList.get(position).getOrg_code())
                                    .setTitle(mList.get(position).getOrg_name())
                                    .setTabTitle(0)
//                        .setExtValue(mList.get(position).getIndu_code_2())
                    );
                    LogUtil.i("getOrg_code", listType);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (this.viewType == Constants.STOCK_DATA_LIST_CONTENT_ALL) {
            return mList.size();
        }
        return mList.size() < 6 ? mList.size() : 5;
    }

    class ListOrgsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_organization_name)
        TextView mName;
        @BindView(R.id.tv_organization_count)
        TextView mCount;
        private View mContainerView;
        StasOrgsEntity mItem;

        public ListOrgsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mContainerView = v;
        }

        public void setItem(StasOrgsEntity item) {
            this.mItem = item;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        void refreshView() {
            //单位转换工具类 ConversionUnitUtil
            //名称
            mName.setText(mItem.getOrg_name());
            mCount.setText("" + mItem.getTotal_company());
        }
    }


}
