package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.ui.activity.news.SpecialColumnActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.List;

/**
 * 专栏
 */

public class ColumnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ColumnEntity> list;
    private Context mContext;

    public ColumnAdapter(Context mContext, List<ColumnEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mViewHolder = (ViewHolder) holder;
        mViewHolder.setItem(list.get(position));
        mViewHolder.refreshView();
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!RepeatCllickUtil.isFastDoubleClick()){
                    Intent intent = new Intent(mContext, SpecialColumnActivity.class);
                    intent.putExtra("related_name", list.get(position).getRelated_name());
                    intent.putExtra("id",position);
                    mContext.startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    //有图片的格式
    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mtitle;
        private ColumnEntity items;

        public ViewHolder(final View item) {
            super(item);
            //新闻图片
            mimageView = (ImageView) item.findViewById(R.id.iv_column_detail_pic);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_column_detail_title);
        }

        public void setItem(ColumnEntity item) {
            this.items = item;
        }

        void refreshView() {
            mtitle.setText(items.getName());
           // Glide.with(mContext).load(items.getDestUrl()).into(mimageView);
            ImageLoader.load(mContext, items.getDestUrl(), mimageView);
        }
    }

    public void addAll(List<ColumnEntity> collection) {
        list = collection;
        notifyDataSetChanged();
    }

}