package com.zhiyi.chinaipo.ui.adapter.f10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;

import java.util.List;

/**
 * 从列表里面进来后的F10 ，点进去的数据
 */
public class F10NameAdapter extends RecyclerView.Adapter<F10NameAdapter.ListViewHolder> {
    private Context mContext;
    private List<String> mStrings;

    public F10NameAdapter(Context context, List<String> mStrings) {
        this.mContext = context;
        this.mStrings = mStrings;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lv_f10_name, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.setItem(mStrings.get(position));
        holder.refreshView();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mStrings == null ? 0 : mStrings.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private String mItem;

        public ListViewHolder(final View item) {
            super(item);
            mTvTitle = (TextView) item.findViewById(R.id.tv_name);
        }

        public void setItem(String item) {
            this.mItem = item;
        }

        void refreshView() {
            mTvTitle.setText(mItem);
        }

    }

}