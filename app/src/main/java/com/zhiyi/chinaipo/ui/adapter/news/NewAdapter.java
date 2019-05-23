package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.news.NewsSave;
import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.Collection;
import java.util.List;

/**
 * 首页新闻
 */

public class NewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ARTICLE_WITH_IMAGE = 1;
    private int ARTICLE_WITHOUT_IMAGE = 2;
    private int ADVERTISE_ITEM = 3;
    private List<ArticlesEntity> list;
    private Context mContext;

    //根据不同的内容在使用它的fragment或者activity里面写他的点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public NewAdapter(Context mContext, List<ArticlesEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ARTICLE_WITH_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ARTICLE_WITHOUT_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zuoshi, parent, false);
            return new NoImageViewHolder(view);
        } else if (viewType == ADVERTISE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad, parent, false);
            return new ViewHolderAd(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            mViewHolder.setItem(list.get(position));
            mViewHolder.refreshView();
        } else if (holder instanceof ViewHolderAd) {

        } else if (holder instanceof NoImageViewHolder) {
            NoImageViewHolder mViewHolder = (NoImageViewHolder) holder;
            mViewHolder.setItem(list.get(position));
            mViewHolder.refreshView();
        }

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    int position = holder.getAdapterPosition();

                    NewsDetailActivity.launch(new NewsDetailActivity.Builder()
                            .setContext(mContext)
                            .setId(list.get(position).getId())
                            .setOriginalId(list.get(position).getOriginalId()));

                }
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();

    }


    @Override
    public int getItemViewType(int position) {
        //被注释这个是判断显示图片的多少
        // return Integer.parseInt(list.get(position).getThumbnail_pic_s());
        if (list.get(position).getTitlepic() != null) {
            return ARTICLE_WITH_IMAGE;
        } else if (list.get(position).getTitlepic() == null) {
            return ARTICLE_WITHOUT_IMAGE;
        } else {
            return ARTICLE_WITH_IMAGE;
        }
    }

    //广告位
    class ViewHolderAd extends RecyclerView.ViewHolder {
        ImageView mad;

        public ViewHolderAd(View itemView) {
            super(itemView);
            mad = (ImageView) itemView.findViewById(R.id.img_ad);
        }
    }


    class NoImageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mType, mCorp;
        private ArticlesEntity items;

        public NoImageViewHolder(final View item) {
            super(item);
            mTitle = item.findViewById(R.id.tv_news_detail_title);
            mType = item.findViewById(R.id.tv_news_detail_author_name);
            mCorp = item.findViewById(R.id.tv_news_title);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        NewsDetailActivity.launch(new NewsDetailActivity.Builder()
                                .setContext(mContext)
                                .setId(items.getId())
                                .setOriginalId(items.getOriginalId()));
                        if (!items.isRead()) {
                            items.setRead(true);
                            mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.hui1));
                            App.getInstance().getNewsSaveDao().insert(new NewsSave(null, items.getOriginalId() + ""));
                        }

                    }
                }
            });


        }

        public void setItem(ArticlesEntity item) {
            this.items = item;
        }

        void refreshView() {
            if (items.isRead()) {
                mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.hui1));
            } else {
                mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
            if (items.getNewsType() == null) {
                mType.setText("");
            } else {
                mType.setText(items.getNewsType() + "  ");
            }
            mTitle.setText(items.getTitle());
            mCorp.setText(items.getSource());

        }
    }

    /**
     * @param
     * @note
     * @return 有图片的格式
     */

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mTitle, mType, mCorp;
        private ArticlesEntity items;

        public ViewHolder(final View item) {
            super(item);
            mimageView = item.findViewById(R.id.iv_news_detail_pic);
            mTitle = item.findViewById(R.id.tv_news_detail_title);
            mType = item.findViewById(R.id.tv_news_detail_author_name);
            mCorp = item.findViewById(R.id.tv_news_title);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        NewsDetailActivity.launch(new NewsDetailActivity.Builder()
                                .setContext(mContext)
                                .setId(items.getId())
                                .setOriginalId(items.getOriginalId()));
                        if (!items.isRead()) {
                            items.setRead(true);
                            mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.hui1));
                            App.getInstance().getNewsSaveDao().insert(new NewsSave(null, items.getOriginalId() + ""));
                        }

                    }
                }
            });


        }

        public void setItem(ArticlesEntity item) {
            this.items = item;
        }

        void refreshView() {

            if (items.isRead()) {
                mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.hui1));
            } else {
                mTitle.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
            if (items.getNewsType() == null) {
                mType.setText("");
            } else {
                mType.setText(items.getNewsType() + "  ");
            }
            mTitle.setText(items.getTitle());
            mCorp.setText(items.getSource());
            //Glide.with(mContext).load(items.getTitlepic()).into(mimageView);
             ImageLoader.load(mContext, items.getTitlepic(), mimageView);
            //  Picasso.with(mContext).load(items.getTitlepic()).into(mimageView);
        }

    }

    public void chengData(List<ArticlesEntity> collection) {
        list = collection;
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends ArticlesEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }

}