package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;

import java.util.List;

/**
 * 专题最新动态
 */

public class SpecialTopicZAdapter extends RecyclerView.Adapter<SpecialTopicZAdapter.ViewHolder> {

    private List<ArticlesEntity> list;
    private Context mContext;

    public SpecialTopicZAdapter(Context mContext, List<ArticlesEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zhongda_zhuanti, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

                holder.setItem(list.get(position+3));
                holder.refreshView();




    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mtitle;
        private ArticlesEntity items;

        public ViewHolder(final View item) {
            super(item);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_shijian);
        }

        public void setItem(ArticlesEntity item) {
            this.items = item;
        }

        void refreshView() {
                mtitle.setText(items.getTitle());
        //    ImageLoader.load(mContext, items.getDestUrl(), mimageView);
        }
    }

    public void addAll(List<ArticlesEntity> collection) {
        list = collection;
        notifyDataSetChanged();
    }

}