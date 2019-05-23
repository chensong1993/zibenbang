package com.zhiyi.chinaipo.ui.adapter.slidetable;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;


import java.util.List;

/**
 *复用的布局
 */

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> {

    View mHeaderView;
    private List<String> mList;
    private Context context;
    public StringListAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_zhongxin_ziliaos,parent,false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (holder instanceof ViewHolder && mList.size() > 2){
            holder.setItem(mList.get(position));
            holder.refreshView();
        }
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtitle;
        private String items;
        public ViewHolder(final View item) {
            super(item);
            if(item == mHeaderView){
                return ;
            }
            mtitle = (TextView) item.findViewById(R.id.tv_shijian1);

        }
        public void setItem(String item) {
            this.items = item;
        }

        void refreshView() {
             mtitle.setText(items);
        }

    }

}