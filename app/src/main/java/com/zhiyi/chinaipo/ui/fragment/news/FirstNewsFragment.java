package com.zhiyi.chinaipo.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.news.ArticlesConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.entity.news.NewsSave;
import com.zhiyi.chinaipo.models.event.TabTitle;
import com.zhiyi.chinaipo.presenters.news.ArticlesPresenter;
import com.zhiyi.chinaipo.ui.activity.NewsDetailActivity;
import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;
import com.zhiyi.chinaipo.ui.activity.stocks.StockDetailActivity;
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.ui.widget.GlideImage;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RegUtil;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 首页新闻的展示
 * 轮播图使用方法
 * https://github.com/youth5201314/banner
 * 刷新加载的各种属性和方法
 * http://www.jianshu.com/p/7479b6ed5ebf
 */
public class FirstNewsFragment extends BaseFragment<ArticlesPresenter> implements ArticlesConnector.View {
    //下拉刷新
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;
    //轮播图
    @BindView(R.id.banner)
    Banner mBanner;
    //轮播图下面的文字
    @BindView(R.id.tv_bannertitle)
    TextView mTitleBanner;

    /*@BindView(R.id.tv_time)
    TextView mTopUpdateTime;
    @BindView(R.id.tv_shuliang1)
    TextView mTopTotalCompany;
    @BindView(R.id.tv_zonggu1)
    TextView mTopTotalShare;
    @BindView(R.id.tv_liutong1)
    TextView mTopFlowShare;
    @BindView(R.id.tv_chengjiao1)
    TextView mTopDealAmount;
    @BindView(R.id.tv_jiage1)
    TextView mTopDealCash;*/
   /* @BindView(R.id.marquee)
    MarqueeView marqueeView;*/
    @BindView(R.id.ns_scroll)
    NestedScrollView mScrollView;
    List<ArticlesEntity> mList;
    NewAdapter mNewsAdapter;
    int pageOffset = 1;
    //横向分割线
    private RecycleViewDivider recycleViewDivider;
    List<MarketIndexEntity> mStockMarketIndexes;
    int loopCounter;
    List<View> views;
    /* @BindView(R.id.tv_no_net)
     TextView mTvNoNet;*/
    //int netType;
    int categoryId;
    boolean bannerColor = true;
    public static FirstNewsFragment mNewsFragment;

    public static FirstNewsFragment newInstance() {
        if (mNewsFragment == null) {
            synchronized (FirstNewsFragment.class) {
                if (mNewsFragment == null) {
                    mNewsFragment = new FirstNewsFragment();
                }
            }
        }
        return mNewsFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryId = getArguments().getInt(Constants.NEWS_CATEGORY_ID);
        mList = new ArrayList<>();
        //mStockMarketIndexes = new ArrayList<>();
        mNewsAdapter = new NewAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getActivity(), R.color.hui)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mBanner.setImageLoader(new GlideImage());
        mBanner.setFocusableInTouchMode(true);
        mBanner.requestFocus();
        mPresenter.getBanner();
        // mPresenter.getIndexSC();
        mPresenter.getArticles(0);
        // mPresenter.getStockIndex();
        setupRefresh();
        //监听是否到状态栏变化的高度
//        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.i("onScrollChange", oldScrollY + "  " + scrollY);
//                if (scrollY > 100) {
//                    bannerColor = false;
//                    RxBus.getDefault().post(new BannerColorEvent(null));
//                } else {
//                    bannerColor = true;
//                }
//            }
//        });

    }

    private void changeReadState(ArticlesEntity newsEntity) {
        for (Iterator iter = App.getInstance().getNewsSaveDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
            NewsSave element = (NewsSave) iter.next();
            if (element.getNewId().equals(newsEntity.getOriginalId() + "")) {
                LogUtil.i("getCategoryId", element.getNewId());
                newsEntity.setRead(true);
            }
        }

    }


    @Override
    protected void initEventAndData() {
       /* categoryId = getArguments().getInt(Constants.NEWS_CATEGORY_ID);
        mList = new ArrayList<>();
        mStockMarketIndexes = new ArrayList<>();
        mNewsAdapter = new NewAdapter(getActivity(), mList);
        mRecyclerView.removeItemDecoration(recycleViewDivider);
        mRecyclerView.setAdapter(mNewsAdapter);
        recycleViewDivider = new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(getActivity(), R.color.hui));
        mRecyclerView.addItemDecoration(recycleViewDivider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mBanner.setImageLoader(new GlideImage());
        mPresenter.getBanner();
        mPresenter.getIndexSC();
        mPresenter.getArticles(0);
        mPresenter.getStockIndex();
        setupRefresh();*/
    }

    @Override
    public void homeIndex(int index) {
       // LogUtil.i("indexhome", "" + categoryId);
        if (index == 0) {
            mScrollView.scrollTo(0, 0);
         //   LogUtil.i("indexhome", "" + categoryId);
            mSmartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void bannerIndex(int index) {
        if (index != 0) {
            mBanner.stopAutoPlay();
        } else {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void showContent(List<ArticlesEntity> list) {
        mSmartRefreshLayout.finishRefresh();
        mList = list;
        mNewsAdapter.chengData(list);
        for (int i = 0; i < list.size(); i++) {
            changeReadState(list.get(i));
        }

        LogUtil.i("showAnnouncements", list.size() + "2");
    }


    @Override
    public void showMoreContent(List<ArticlesEntity> list) {
        mSmartRefreshLayout.finishLoadmore();
        mList = list;
        mNewsAdapter.addAll(list);
//        isLoadingMore = false;
    }

    public void setupRefresh() {
        //下拉刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.getBanner();
                // mPresenter.getIndexSC();
                mPresenter.getArticles(0);
                // mPresenter.getStockIndex();
//                if (netType < 0) {
//                    ToastUtil.showToast(mContext, "当前网络不可用");
//                }
            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.getMoreArticles(0, ++pageOffset);
                // mPresenter.getStockIndex();
            }
        });
    }


    @Override
    public void showBanner(List<BannerEntity> banners) {
        List<String> mImageUrls = new ArrayList<>();
        List<String> mBannerTitles = new ArrayList<>();
        for (BannerEntity it : banners) {
            mImageUrls.add(it.getFeatured_image());
            mBannerTitles.add(it.getDescription());
        }
        mBanner.setImages(mImageUrls);
        mBanner.setBannerTitles(mBannerTitles);

        //图片点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String currentUrl = banners.get(position).getDestUrl();
                int gotNewsID = RegUtil.getNewsId(currentUrl);
                String gotStockID = RegUtil.getStockId(currentUrl);
                String gotChannelName = RegUtil.getChannelName(currentUrl);
                if (currentUrl == null) {
                    return;
                }
                if (!RegUtil.isChinaIPOContent(currentUrl)) {
                    WebActivity.launch(new WebActivity.Builder()
                            .setContext(mContext)
                            .setTitle(banners.get(position).getTitle())
                            .setUrl(banners.get(position).getDestUrl())
                    );
                    LogUtil.i("1", "1");
                } else if (gotStockID != null) {

                    StockDetailActivity.launch(new StockDetailActivity.Builder()
                            .setStockCode(gotStockID)
                            .setContext(mContext)
                            .setActivity(mActivity));
                    LogUtil.i("1", "2");
                } else if (gotNewsID != -1) {
                    LogUtil.i("gotStockID", gotNewsID + "");
                    NewsDetailActivity.launch(new NewsDetailActivity.Builder()
                            .setActivity(mActivity)
                            .setContext(mContext)
                            .setOriginalId(gotNewsID));
                    LogUtil.i("1", "3");
                } else if (gotChannelName != null) {
                    if (gotChannelName.equals("ipo")) {
                        RxBus.getDefault().post(new TabTitle(2));
                    } else if (gotChannelName.equals("zjxsb") || gotChannelName.equals("jhj")) {
                        WebActivity.launch(new WebActivity.Builder()
                                .setContext(mContext)
                                .setTitle(banners.get(position).getTitle())
                                .setUrl(banners.get(position).getDestUrl())
                        );
                    }
                    LogUtil.i("1", "4");
                }
            }
        });

        //在这个里面设置轮播图下面的文字
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //判断每一个轮播图下面对应的文字
                if (banners.get(position).getTitle() != null) {
                    mTitleBanner.setText(banners.get(position).getTitle());
                    //获取状态栏颜色
//                    if (bannerColor) {
//                        RxBus.getDefault().post(new BannerColorEvent(banners.get(position).getFeatured_image()));
//                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mBanner.start();
    }

    @Override
    public void showMarketTops(List<IndexSCEntity> topMarkets) {
       /* mTopUpdateTime.setText(topMarkets.get(0).getVals());
        mTopTotalCompany.setText(topMarkets.get(1).getVals());
        mTopTotalShare.setText(topMarkets.get(2).getVals());
        mTopFlowShare.setText(topMarkets.get(3).getVals());
        mTopDealAmount.setText(topMarkets.get(4).getVals());
        mTopDealCash.setText(topMarkets.get(5).getVals());*/
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void showStockIndex(List<MarketIndexEntity> stockIndexes) {
        /*mStockMarketIndexes.clear();
        mStockMarketIndexes.addAll(stockIndexes);
        views = new ArrayList<>();
        if (mStockMarketIndexes.size() > 1) {
            views.clear();
            for (int k = 0; k < mStockMarketIndexes.size(); k++) {
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_marquee_home, null);
                //初始化布局的控件
                TextView mTopMarketIndex = moreView.findViewById(R.id.tv_sanban);
                TextView mTopMarketValue = moreView.findViewById(R.id.tv_sanban1);
                mTopMarketIndex.setText(stockIndexes.get(k).getIndx_sname());
                mTopMarketValue.setText(stockIndexes.get(k).getTclose());
                views.add(moreView);
                marqueeView.setViews(views);
                if (NumericUtil.stringToDouble(stockIndexes.get(k).getChng()) < 0) {
                    mTopMarketIndex.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    mTopMarketValue.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                } else if (NumericUtil.stringToDouble(stockIndexes.get(k).getChng()) > 0) {
                    mTopMarketIndex.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    mTopMarketValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                }
            }

        }*/
    }

//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        netType = netMobile;
//        if (netMobile > -1) {
//            if (mList == null || mList.size() == 0) {
//                mPresenter.getBanner();
//                //   mPresenter.getIndexSC();
//                mPresenter.getArticles(0);
//                //  mPresenter.getStockIndex();
//            }
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        //  marqueeView.startFlipping();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("pause", "onPause: ");
        //  marqueeView.stopFlipping();
    }

    @Override
    public void showColumn(List<ColumnEntity> columnEntities) {

    }

    @Override
    public void showTopic(List<TopicEntity> mTopicEntityList) {

    }


    @Override
    public void showErr() {
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void moreErr() {
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}