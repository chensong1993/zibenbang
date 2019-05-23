package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.ui.activity.news.SpecialColumnActivity;
import com.zhiyi.chinaipo.ui.activity.news.SpecialTopicActivity;

import java.util.Collection;
import java.util.List;

/**
 * 专栏
 */

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TopicEntity> list;
    private Context mContext;
    private int categoryId;

    public TopicAdapter(Context mContext, List<TopicEntity> list, int categoryId) {
        this.mContext = mContext;
        this.list = list;
        this.categoryId = categoryId;
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
                if (categoryId == 15) {
                    SpecialTopicActivity.launch(new SpecialTopicActivity.Builder()
                            .setContext(mContext)
                            .setContent(list.get(position).getCore_ideas())
                            .setOriginalId(list.get(position).getOrig_reference())
                            .setId(position));
                } else {
                    SpecialColumnActivity.launch(new SpecialColumnActivity.Builder()
                            .setContext(mContext)
                            .setTitle(list.get(position).getRelated_name())
                            .setId(position));
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mtitle;
        private TopicEntity items;

        public ViewHolder(final View item) {
            super(item);
            //新闻图片
            mimageView =  item.findViewById(R.id.iv_column_detail_pic);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_column_detail_title);
        }

        public void setItem(TopicEntity item) {
            this.items = item;
        }

        void refreshView() {
            if (categoryId == 15) {
                mtitle.setText(items.getTitle());
            } else {
                mtitle.setText(items.getName());
            }
          //  LogUtil.i("top","ok"+list.size());
          //  Glide.with(mContext).load(items.getDestUrl()).override(150, 100).fitCenter().into(mimageView);
            ImageLoader.load(mContext, items.getDestUrl(), mimageView);
            }

    }

    public void chengeData(List<TopicEntity> topicEntityList) {
        list = topicEntityList;
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends TopicEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }

}