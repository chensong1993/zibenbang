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
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.Collection;
import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ARTICLE_WITH_IMAGE = 1;
    private int ARTICLE_WITHOUT_IMAGE = 2;

    private List<ArticleDetailEntity.OtherLinks> mList;
    private Context mContext;

    //根据不同的内容在使用它的fragment或者activity里面写他的点击事件
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RelatedNewsAdapter(Context mContext, List<ArticleDetailEntity.OtherLinks> list) {
        this.mContext = mContext;
        this.mList = list;
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
            return new ViewHolders(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {
            ViewHolder mViewHolder = (ViewHolder) holder;
            mViewHolder.setItem(mList.get(position));
            mViewHolder.refreshView();
        } else if (holder instanceof ViewHolders) {
            ViewHolders mViewHolder = (ViewHolders) holder;
            mViewHolder.setItem(mList.get(position));
            mViewHolder.refreshView();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    int position = holder.getAdapterPosition();
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constants.NEWS_DETAIL_ID, mList.get(position).getId());
                    intent.putExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, mList.get(position).getOriginalId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //被注释这个是判断显示图片的多少
        // return Integer.parseInt(list.get(position).getThumbnail_pic_s());
        if (mList.get(position).getFeatured_image() != null) {
            return ARTICLE_WITH_IMAGE;
        } else {
            return ARTICLE_WITHOUT_IMAGE;
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


    //  没有图片的格式
    class ViewHolders extends RecyclerView.ViewHolder {
        private TextView mtitle, mtext, mtitle1;
        private ArticleDetailEntity.OtherLinks items;

        public ViewHolders(final View item) {
            super(item);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_news_detail_title);
            //内容下面蓝色字
            mtext = (TextView) item.findViewById(R.id.tv_news_detail_author_name);
            //蓝字右边
            mtitle1 = (TextView) item.findViewById(R.id.tv_news_title);
        }

        public void setItem(ArticleDetailEntity.OtherLinks item) {
            this.items = item;
        }

        void refreshView() {
            if (items.getNewsType().equals("")) {
                mtext.setText(items.getNewsType());
            } else {
                mtext.setText(items.getNewsType() + "  ");
            }
            mtitle.setText(items.getTitle());
            mtitle1.setText(items.getSource());
        }
    }

    //有图片的格式
    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mtitle, mtext, mtitle1;
        private ArticleDetailEntity.OtherLinks items;

        public ViewHolder(final View item) {
            super(item);
            //新闻图片
            mimageView = (ImageView) item.findViewById(R.id.iv_news_detail_pic);
            //内容
            mtitle1 = (TextView) item.findViewById(R.id.tv_news_detail_title);
            //蓝颜色的字
            mtext = (TextView) item.findViewById(R.id.tv_news_detail_author_name);
            //蓝色字旁边的 股转系统
            mtitle = (TextView) item.findViewById(R.id.tv_news_title);
        }

        public void setItem(ArticleDetailEntity.OtherLinks item) {
            this.items = item;
        }

        void refreshView() {
            if (items.getNewsType().equals("")) {
                mtext.setText(items.getNewsType());
            } else {
                mtext.setText(items.getNewsType() + "  ");
            }

            mtitle1.setText(items.getTitle());
            mtitle.setText(items.getSource());
            // Glide.with(mContext).load(items.getFeatured_image()).into(mimageView);
            ImageLoader.load(mContext, items.getFeatured_image(), mimageView);
        }
    }

    public void addAll(Collection<? extends ArticleDetailEntity.OtherLinks> collection) {
        if (mList != null) {
            mList.addAll(collection);
            notifyDataSetChanged();
        }
    }

}