package com.zhiyi.chinaipo.ui.activity.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.ArticlesConnector;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.presenters.news.ArticlesPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.SpecialTopicAdapter;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author chensong
 * @date 2018/2/24 14:42
 */
public class SpecialTopicActivity extends BaseActivity<ArticlesPresenter> implements ArticlesConnector.View {

    @BindView(R.id.tv_title)
    TextView mMainTitle;
    @BindView(R.id.tv_topic_title)
    TextView mTvTitle;
    @BindView(R.id.tv_laiyuan)
    TextView mTvLaiYuan;
    @BindView(R.id.tv_hexin_guandian)
    TextView mTvGuandian;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.rv_zhongdashijian)
    RecyclerView mRvZhongdaShiJian;
   /* @BindView(R.id.rv_zuixin_dongtai)
    RecyclerView mRvZuiXinDongTai;*/
    SpecialTopicAdapter mSpeicialAdapter;
    List<ArticlesEntity> mTopicList;
    List<TopicEntity> mTopicEntityList;

    @Override
    protected int getLayout() {
        return R.layout.activity_zhuanti;
    }

    @Override
    protected void initEventAndData() {
        mMainTitle.setText("专栏");
        int topic = getIntent().getIntExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, 0);
        mTvGuandian.setText(getIntent().getStringExtra(Constants.NEWS_DETAIL_CONTENT));
        mTopicList = new ArrayList<>();
        mTopicEntityList = new ArrayList<>();
        mSpeicialAdapter = new SpecialTopicAdapter(this, mTopicList);
        mRvZhongdaShiJian.setLayoutManager(new LinearLayoutManager(this));
        mRvZhongdaShiJian.setAdapter(mSpeicialAdapter);
        mPresenter.getSpecialTopic(topic, 1);
        mPresenter.getTopic(1);
    }

    @OnClick(R.id.rl_back)
    void rlBack() {
        finish();
    }

    @Override
    public void showTopic(List<TopicEntity> mTopicEntityList) {
        int pos = getIntent().getIntExtra(Constants.NEWS_DETAIL_ID, 0);
        mTvTitle.setText(mTopicEntityList.get(pos).getTitle());
        mTvLaiYuan.setText(mTopicEntityList.get(pos).getSource());
      //  mTvGuandian.setText(mTopicEntityList.get(pos).getCore_ideads());
    }

    @Override
    public void homeIndex(int index) {

    }

    @Override
    public void bannerIndex(int index) {

    }

    @Override
    public void showErr() {

    }

    @Override
    public void moreErr() {

    }

    @Override
    public void showContent(List<ArticlesEntity> list) {
        mTopicList = list;
        LogUtil.i("listsss", "showContent: "+list.size());
        mSpeicialAdapter.addAll(mTopicList);
    }
    public static class Builder {

        private String title;
        private int id;
        private int originalId;
        private Context mContext;
        private String content;
        private Activity mActivity;

        public Builder() {

        }

        public SpecialTopicActivity.Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public SpecialTopicActivity.Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public SpecialTopicActivity.Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public SpecialTopicActivity.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public SpecialTopicActivity.Builder setOriginalId(int originalId) {
            this.originalId = originalId;
            return this;
        }

        public SpecialTopicActivity.Builder setContent(String content) {
            this.content = content;
            return this;
        }
    }

    public static void launch(SpecialTopicActivity.Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, SpecialTopicActivity.class);
        intent.putExtra(Constants.NEWS_DETAIL_ID, builder.id);
        intent.putExtra(Constants.NEWS_DETAIL_CONTENT,builder.content);
        intent.putExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, builder.originalId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }
    @Override
    public void showBanner(List<BannerEntity> banners) {

    }

    @Override
    public void showMarketTops(List<IndexSCEntity> topMarkets) {

    }


    @Override
    public void showMoreContent(List<ArticlesEntity> list) {

    }

    @Override
    public void showStockIndex(List<MarketIndexEntity> stockIndexes) {

    }

    @Override
    public void showColumn(List<ColumnEntity> columnEntities) {
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

}
