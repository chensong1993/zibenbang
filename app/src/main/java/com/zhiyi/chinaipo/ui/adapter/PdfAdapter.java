package com.zhiyi.chinaipo.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.models.entity.details.AnnouncementEntity;
import com.zhiyi.chinaipo.ui.activity.stocks.PdfViewActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.zhiyi.chinaipo.ui.activity.slideabletableonclick.PdfViewActivity;

/**
 * pdf样式
 */

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder> {

    private List<AnnouncementEntity> mList;
    private Context mContext;

    public PdfAdapter(Context mContext, List<AnnouncementEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(mList.get(position));
        holder.refreshView();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtitle, mtime;
        private AnnouncementEntity items;

        public ViewHolder(final View item) {
            super(item);
            mtitle = (TextView) item.findViewById(R.id.tv_news_detail_title);
            mtime = (TextView) item.findViewById(R.id.tv_time);
            //跳转事件
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        PdfViewActivity.launch(new PdfViewActivity.Builder()
                                .setContext(mContext)
                                .setDocumentURL(items.getAccess_url())
                                .setDocTitle(items.getDisc_title()));
                    }
                }
            });
        }

        public void setItem(AnnouncementEntity item) {
            this.items = item;
        }

        public void refreshView() {
            mtitle.setText(items.getDisc_title());
            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = formats.parse(items.getDeclaredate());
                formats = new SimpleDateFormat("yyyy-MM-dd");
                mtime.setText(formats.format(dates));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(List<AnnouncementEntity> collection) {
        mList = collection;
        notifyDataSetChanged();
    }

    public void addAll(List<AnnouncementEntity> collection) {
        if (mList == null) {
            add(collection);
        }
        {
            mList.addAll(collection);
            notifyDataSetChanged();
        }
    }
}