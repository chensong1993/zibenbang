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
 * 数据模块，主办券商和做市排行，律师，会计顶部样式
 */
public class RankingHeaderAdapter extends RecyclerView.Adapter<RankingHeaderAdapter.GuapaiViewHolder> {
    private Context mContext;
    private List<String> mList;
    //根据不同的内容在使用它的fragment或者activity里面写他的点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RankingHeaderAdapter(Context context, List<String> mList1) {
        this.mContext = context;
        this.mList = mList1;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            if (!RepeatCllickUtil.isFastDoubleClick()) {
//                //注意这里使用getTag方法获取数据
//                mOnItemClickListener.onItemClick(v);
//            }
//        }
//    }

    @Override
    public GuapaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_shuju_head_zhuban, parent, false);
        //itemView.setOnClickListener(this);
        return new GuapaiViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GuapaiViewHolder holder, int position) {

        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    class GuapaiViewHolder extends RecyclerView.ViewHolder {

        private View mContainerView;
        private TextView mName, mDate;

        public GuapaiViewHolder(View v) {
            super(v);
            mContainerView = v;
            mName = (TextView) v.findViewById(R.id.tv_mingcheng);
            mDate = (TextView) v.findViewById(R.id.tv_mingchengdate);
            mContainerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v);
                }
            });
        }

//        public void setOnClickListener(View.OnClickListener listener) {
//            mContainerView.setOnClickListener(listener);
//        }

        void refreshView() {
            mName.setText(mList.get(0));
            //    mDate.setText(mList.get(1));
        }
    }


}
