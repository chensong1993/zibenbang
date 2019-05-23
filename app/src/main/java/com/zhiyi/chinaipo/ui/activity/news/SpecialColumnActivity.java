package com.zhiyi.chinaipo.ui.activity.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.ui.widget.circleView.CircleImageView;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SpecialColumnActivity extends BaseActivity<ArticlesPresenter> implements ArticlesConnector.View {

    @BindView(R.id.rv_zuijin_wenzhang)
    RecyclerView mRvZuijin;
    @BindView(R.id.rv_xiangguan_wenzhang)
    RecyclerView mRvXiangguan;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_zhuanti_content)
    TextView mTvZhuantiContent;
    @BindView(R.id.img_icon)
    CircleImageView mImgIcon;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    NewAdapter newAdapter;
    List<ArticlesEntity> mDetailEntities;
    List<ColumnEntity> mColumnEntities;

    @Override
    protected int getLayout() {
        return R.layout.activity_special_column;
    }

    @Override
    protected void initEventAndData() {
        mTvTitle.setText("专题");
        String name = getIntent().getStringExtra(Constants.NEWS_DETAIL_ORIGINAL_ID);
        LogUtil.i("related_name", name);
        mDetailEntities = new ArrayList<>();
        mColumnEntities = new ArrayList<>();
        newAdapter = new NewAdapter(this, mDetailEntities);
        mRvZuijin.setAdapter(newAdapter);
        mRvZuijin.setLayoutManager(new LinearLayoutManager(this));
        mRvZuijin.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 14, ContextCompat.getColor(this, R.color.hui)));
        mPresenter.getNewsColumn(name, 1);
        mPresenter.getNewsColumnTitle(1);
    }

    @OnClick(R.id.img_back)
    void Back() {
        finish();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showContent(List<ArticlesEntity> list) {
        mDetailEntities = list;
        newAdapter.chengData(mDetailEntities);
    }

    @Override
    public void showColumn(List<ColumnEntity> list) {
        int id = getIntent().getIntExtra(Constants.NEWS_DETAIL_ID, 0);
        mColumnEntities = list;
        mTvName.setText(mColumnEntities.get(id).getName());
        mTvZhuantiContent.setText(mColumnEntities.get(id).getDescription());
        Glide.with(this).load(mColumnEntities.get(id).getDestUrl()).into(mImgIcon);
    }

    public static class Builder {
    private String title;
    private int id;
    private int originalId;
    private Context mContext;
    private Activity mActivity;

    public Builder() {

    }

    public SpecialColumnActivity.Builder setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public SpecialColumnActivity.Builder setActivity(Activity mActivity) {
        this.mActivity = mActivity;
        return this;
    }

    public SpecialColumnActivity.Builder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SpecialColumnActivity.Builder setId(int id) {
        this.id = id;
        return this;
    }

    public SpecialColumnActivity.Builder setOriginalId(int originalId) {
        this.originalId = originalId;
        return this;
    }
}

    public static void launch(SpecialColumnActivity.Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, SpecialColumnActivity.class);
        intent.putExtra(Constants.NEWS_DETAIL_ID, builder.id);
        intent.putExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, builder.title);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }
    @Override
    public void showTopic(List<TopicEntity> mTopicEntityList) {

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

}
