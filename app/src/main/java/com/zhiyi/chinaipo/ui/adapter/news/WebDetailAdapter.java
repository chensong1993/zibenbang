package com.zhiyi.chinaipo.ui.adapter.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.ui.activity.MoreNewsActivity;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.WebviewUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 2017/9/30.
 */
public class WebDetailAdapter extends RecyclerView.Adapter<WebDetailAdapter.WebViewHolder> {

    private List<ArticleDetailEntity> list;
    private Context context;
    WebviewUtil analyzeUnit;

    public WebDetailAdapter(List<ArticleDetailEntity> list, Context context) {
        this.list = list;
        this.context = context;
        analyzeUnit = new WebviewUtil(context);
    }

    @Override
    public WebDetailAdapter.WebViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wab_activity, parent, false);
        return new WebViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WebDetailAdapter.WebViewHolder holder, int position) {
        holder.setItems(list.get(position));
        holder.refreshView();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class WebViewHolder extends RecyclerView.ViewHolder {

        TextView title, yuanchuang, gongsi, zuozhe, date, phototitle;
        WebView webView;
        ImageView imageView, ad;
        ArticleDetailEntity items;
        RecyclerView mRvOtherlist;
        LinearLayout layout;
        RelatedNewsAdapter mRelatedAdapter;
        public WebViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_article_title);
            yuanchuang = v.findViewById(R.id.tv_type);
            gongsi = v.findViewById(R.id.tv_company);
            date = v.findViewById(R.id.tv_time);
            zuozhe = v.findViewById(R.id.tv_author);
            phototitle = v.findViewById(R.id.tv_photo_below);
            ad = v.findViewById(R.id.img_ad);
            webView = v.findViewById(R.id.web);
            layout = v.findViewById(R.id.ll_keywords);
            mRvOtherlist=v.findViewById(R.id.rv_otherlist);
        }

        public void setItems(ArticleDetailEntity items) {
            this.items = items;
        }

        void refreshView() {
            title.setText(items.getTitle());
            if (items.getNewsType().equals("原创")) {
                yuanchuang.setText(items.getNewsType());
                zuozhe.setText(items.getAuthor());
            } else {
                yuanchuang.setVisibility(View.GONE);
            }
            phototitle.setText(items.getLead_in());
            gongsi.setText(items.getSource());
            SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dates = formats.parse(items.getPublishing_date());
                formats = new SimpleDateFormat("yyyy-MM-dd");
                date.setText(formats.format(dates));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //关键字
            for (int i = 0; i < items.getTags().size(); i++) {
                final String key = items.getTags().get(i).getName().toString();
                final TextView tv_key = new TextView(context);
                tv_key.setText(key);
                tv_key.setTextSize(18);
                tv_key.setPadding(10, 5, 10, 5);
                tv_key.setTextColor(context.getResources().getColor(R.color.blue));
                tv_key.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.wabdialog));
                layout.addView(tv_key, i);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tv_key.getLayoutParams();
                lp.rightMargin = 20;
                tv_key.setLayoutParams(lp);
                tv_key.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!RepeatCllickUtil.isFastDoubleClick()) {
                            Intent intent = new Intent(context, MoreNewsActivity.class);
                            intent.putExtra(Constants.NEWS_LIST_BY_TAG, key);
                            context.startActivity(intent);
                        }
                    }
                });
            }

           /* Glide.with(context).load(items.getFeatured_image()).into(imageView);
             Glide.with(context).load(items.getTitlepic()).into(ad);*/
            //后台图片路径加载有误需要处理后才能显示
            String unnecessary = "http://www.chinaipo.comhttp://www.chinaipo.com/";
            String unnecessary1 = "/data/upload/";
            String content;
            if (items.getContent().getContent().contains(unnecessary1)) {
                content = items.getContent().getContent().replace(unnecessary1, "http://www.chinaipo.com/data/upload/");
                if (content.contains(unnecessary)) {
                    String content1 = content.replace(unnecessary, "http://www.chinaipo.com/");
                    analyzeUnit.setWBcontents(webView, content1);
                } else {
                    analyzeUnit.setWBcontents(webView, content);
                }
                Log.d("webviewc1", content);
            } else {
                analyzeUnit.setWBcontents(webView, items.getContent().getContent());
            }

            Log.d("webviewc", items.getContent().getContent());
            //精选新闻
            mRelatedAdapter = new RelatedNewsAdapter(context, items.getOtherLinks());
            mRvOtherlist.setAdapter(mRelatedAdapter);
            mRvOtherlist.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(context, R.color.hui)));
            mRvOtherlist.setLayoutManager(new LinearLayoutManager(context));
            mRvOtherlist.setAdapter(mRelatedAdapter);
        }


    }

    public void addAll(List<ArticleDetailEntity> resultses) {
        list = resultses;
        notifyDataSetChanged();
    }
}
