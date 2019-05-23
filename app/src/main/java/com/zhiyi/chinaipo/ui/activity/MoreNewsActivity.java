package com.zhiyi.chinaipo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.TagConnector;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.presenters.news.TagPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreNewsActivity extends BaseActivity<TagPresenter> implements TagConnector.View {

    @BindView(R.id.refresh_related_news)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.pro_loading)
    ProgressBar mRlProgress;
    private String mTagName;
    private int pageOffset = 1;
    private List<ArticlesEntity> mNewsList;
    private NewAdapter mAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_more_news;
    }

    @OnClick(R.id.img_back)
    void imgBack() {
        finish();
    }

    @Override
    protected void initEventAndData() {
        mRlProgress.setVisibility(View.VISIBLE);
        mTagName = getIntent().getStringExtra(Constants.NEWS_LIST_BY_TAG);
        mPresenter.getArticlesByTag(mTagName, pageOffset);

        mNewsList = new ArrayList<>();
        mAdapter = new NewAdapter(mContext, mNewsList);
        mRecyclerView.setAdapter(mAdapter);

        //分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(this, R.color.hui)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRefresh();
        mTvTitle.setText(mTagName);
    }


    @Override
    public void showContent(List<ArticlesEntity> list) {
        mRlProgress.setVisibility(View.GONE);
        mNewsList = list;
        if (pageOffset == 1) {
            mAdapter.chengData(mNewsList);
        } else {
            mNewsList.clear();
            mAdapter.addAll(mNewsList);
            if (list.size() < 1) {
                mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
            } else {
                mSmartRefreshLayout.finishLoadmore();
            }
        }
    }

    @Override
    public void showErr() {
        mRlProgress.setVisibility(View.GONE);
        ToastUtil.showToast(this, "暂无数据");

    }


    public void setupRefresh() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getArticlesByTag(mTagName, pageOffset);
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getArticlesByTag(mTagName, ++pageOffset);
            }
        });
    }

    public static class Builder {

        private String tagName;
        private Context mContext;
        private Activity mActivity;

        public Builder() {

        }

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setActivity(Activity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public Builder setTag(String title) {
            this.tagName = title;
            return this;
        }

    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, MoreNewsActivity.class);
        intent.putExtra(Constants.NEWS_LIST_BY_TAG, builder.tagName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }
}
