package com.zhiyi.chinaipo.ui.adapter.f10;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;

import java.util.List;

public class FinanceTermTitleAdapter extends RecyclerView.Adapter<FinanceTermTitleAdapter.ListViewHolder> {

    private List<Pair<String, Integer>> mList;
    private Context mContext;
    private int viewType;

    public FinanceTermTitleAdapter(Context context, List<Pair<String, Integer>> mList, int viewType) {
        this.mContext = context;
        this.mList = mList;
        this.viewType = viewType;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_f10_nianbao, parent,false);
        if (viewType == Constants.FINANCE_LIST_CONTENT_ALL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_details_f10_table_right_title, parent, false);
        }
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        holder.setItem(mList.get(position));
        holder.refreshView();
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView mtitle;
        private Pair<String, Integer> mItem;

        public ListViewHolder(final View item) {
            super(item);
            mtitle = (TextView) item.findViewById(R.id.tv_table_title_0);
        }
        public void setItem(Pair<String, Integer> item) {
            this.mItem = item;
        }

        void refreshView() {
            String mStringTitle = mItem.first.substring(0, 4);
            switch (mItem.second) {
                case 1:
                    mStringTitle += "一季报";
                    break;
                case 2:
                    mStringTitle += "中报";
                    break;
                case 3:
                    mStringTitle += "三季报";
                    break;
                default:
                    mStringTitle += "年报";
                    break;
            }
            mtitle.setText(mStringTitle);
        }

    }

}