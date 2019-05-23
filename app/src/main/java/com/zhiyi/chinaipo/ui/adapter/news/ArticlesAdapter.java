package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;


import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<ArticlesEntity> mList;
    private OnItemClickListener onItemClickListener;
    private int CONTENT_WITH_IMAGE = 1;
    private int CONTENT_WITHOUT_IMAGE = 2;
    private int CONTENT_AD = 3;
    private int categoryId;

    public ArticlesAdapter(Context mContext, List<ArticlesEntity> mList, int categoryId) {
        inflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.categoryId = categoryId;
    }

    @Override
    public int getItemViewType(int position) {
        //被注释这个是判断显示图片的多少
        // return Integer.parseInt(list.get(position).getThumbnail_pic_s());
        if (mList.get(position).getTitlepic() != null) {
            return CONTENT_WITH_IMAGE;
        } else if (mList.get(position).getTitlepic() == null) {
            return CONTENT_WITHOUT_IMAGE;
        } else {
            return CONTENT_AD;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CONTENT_WITH_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
//            view.setOnClickListener(this);
            return new ViewHolder(view);
        } else if (viewType == CONTENT_WITHOUT_IMAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zuoshi, parent, false);
//            view.setOnClickListener(this);
            return new ViewHolders(view);
        } else if (viewType == CONTENT_AD) {
            View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ad, parent, false);
//            view1.setOnClickListener(this);
            return new ViewHolderAd(view1);
        }
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

//        if(tech.equals(GankMainFragment.tabTitle[0])) {
//            ((ViewHolder)holder).ivIcon.setImageResource(R.mipmap.ic_android);
//        } else if(tech.equals(GankMainFragment.tabTitle[1])) {
//            ((ViewHolder)holder).ivIcon.setImageResource(R.mipmap.ic_ios);
//        } else if(tech.equals(GankMainFragment.tabTitle[2])) {
//            ((ViewHolder)holder).ivIcon.setImageResource(R.mipmap.ic_web);
//        }
//        ((ViewHolder)holder).tvContent.setText(mList.get(position).getDesc());
//        ((ViewHolder)holder).tvAuthor.setText(mList.get(position).getWho());
//        ((ViewHolder)holder).tvTime.setText(DateUtil.formatDateTime(DateUtil.subStandardTime(mList.get(position).getPublishedAt()), true));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(onItemClickListener != null) {
//                    CardView cv = (CardView) view.findViewById(R.id.cv_tech_content);
//                    onItemClickListener.onItemClick(holder.getAdapterPosition(),cv);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
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
        private ArticlesEntity items;

        public ViewHolders(final View item) {
            super(item);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_news_detail_title);
            //内容下面蓝色字
            mtext = (TextView) item.findViewById(R.id.tv_news_detail_author_name);
            //蓝字右边
            mtitle1 = (TextView) item.findViewById(R.id.tv_news_title);
        }

        public void setItem(ArticlesEntity item) {
            this.items = item;
        }

        void refreshView() {

//
        }
    }

    //有图片的格式
    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mimageView;
        private TextView mtitle, mtext, mtitle1;
        private ArticlesEntity items;

        public ViewHolder(final View item) {
            super(item);
            //新闻图片
            mimageView = (ImageView) item.findViewById(R.id.iv_news_detail_pic);
            //内容
            mtitle = (TextView) item.findViewById(R.id.tv_news_detail_title);
            //蓝颜色的字
            mtext = (TextView) item.findViewById(R.id.tv_news_detail_author_name);
            //蓝色字旁边的 股转系统
            mtitle1 = (TextView) item.findViewById(R.id.tv_news_title);
        }

        public void setItem(ArticlesEntity item) {
            this.items = item;
        }

        void refreshView() {
            mtext.setText(items.getAuthor());
            mtitle.setText(items.getTitle());
            mtitle1.setText(items.getNewsType());
//            Glide.with(mContent).load(items.getTitlepic()).into(mimageView);
        }
    }

}
