package com.zhiyi.chinaipo.ui.adapter.datas;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.util.NumericUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

/**
 * 数据木块挂牌公司分布和地区详情 环形下方的样式
 * Created by admin on 2017/9/4.
 */
public class DataGongsiAdpater extends RecyclerView.Adapter<DataGongsiAdpater.SanbanViewHolder> implements View.OnClickListener {

    private List<String> mValueList;
    private Context mContext;
    private DataGongsiAdpater.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public DataGongsiAdpater(Context context, List<String> mValueList) {
        this.mValueList = mValueList;
        this.mContext = context;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);

    }

    public void setOnItemClickListener(DataGongsiAdpater.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            if (!RepeatCllickUtil.isFastDoubleClick()) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v);
            }
        }
    }

    @Override
    public DataGongsiAdpater.SanbanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shuju_fenbu, parent, false);
        view.setOnClickListener(this);
        return new SanbanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataGongsiAdpater.SanbanViewHolder holder, int position) {
        holder.setItem(mValueList);
        holder.refreshView();
    }

    @Override
    public int getItemCount() {
        return mValueList.size() > 0 ? 1 : 0;
    }

    public class SanbanViewHolder extends RecyclerView.ViewHolder {
        private View mContainerView;
        private TextView tvNumber, tvNumber1, tvNumbe2;
        private List<String> mValues;

        public SanbanViewHolder(View v) {
            super(v);
            mContainerView = v;
            tvNumber = (TextView) v.findViewById(R.id.gongsi);
            tvNumber1 = (TextView) v.findViewById(R.id.tv_baifenbi1);
            tvNumbe2 = (TextView) v.findViewById(R.id.tv_shuzi1);
        }

        public void setItem(List<String> mItem) {
            this.mValues = mItem;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            mContainerView.setOnClickListener(listener);
        }

        void refreshView() {
            //地区。名称
            tvNumber.setText(mValues.get(0));
            //百分比
            tvNumber1.setText(NumericUtil.getPercentDirect(mValues.get(1)));
//            ConversionUnitUtil.Percent(xingu.getCount2(), tvNumber1);
            tvNumbe2.setText(mValues.get(2));

        }
    }
}
