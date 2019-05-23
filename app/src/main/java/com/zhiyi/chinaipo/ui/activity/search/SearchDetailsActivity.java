package com.zhiyi.chinaipo.ui.activity.search;

import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shizhefei.guide.GuideHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.search.SearchConnector;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.search.SearchSave;
import com.zhiyi.chinaipo.presenters.search.SearchPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.NewAdapter;
import com.zhiyi.chinaipo.ui.adapter.search.SearchSavesAdapter;
import com.zhiyi.chinaipo.ui.adapter.search.StockResultExactAdapter;
import com.zhiyi.chinaipo.ui.adapter.search.StockResultsAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;
import com.zhiyi.chinaipo.util.DisplayUtils;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.SnackbarUtil;
import com.zhiyi.chinaipo.util.StarUtil;
import com.zhiyi.chinaipo.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @note 进入搜索页面的详情页
 * @anthor Song Chen
 */
public class SearchDetailsActivity extends BaseActivity<SearchPresenter> implements SearchConnector.View {

    @BindView(R.id.tv_xinxi)
    TextView mTvXinxi;
    @BindView(R.id.tv_quxiao)
    TextView mQuxiao;
    @BindView(R.id.et_seek)
    EditText mEtSeek;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.imgcache)
    ImageView mImgCache;
    @BindView(R.id.rv_search_result_news)
    RecyclerView rvResultsNews;
    @BindView(R.id.rv_search_result_stock)
    RecyclerView rvResultStock;
    @BindView(R.id.rv_search_result_exact_stock)
    RecyclerView rvOneResultStock;
    @BindView(R.id.refresh_search_result)
    SmartRefreshLayout mRefreshSearch;
    @BindView(R.id.rl_search_hangqing_result)
    RelativeLayout mRlhangqingResult;
    @BindView(R.id.rl_search_news_result)
    RelativeLayout mRlNewsResult;
    @BindView(R.id.search_result_container)
    LinearLayout mLlSearchSave;
    @BindView(R.id.clear_record)
    TextView mClearRecord;
    //侧滑删除
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private SearchSavesAdapter mSearchSaveAdapter;
    private List<SearchSave> mSearchSaveList;
    private int mSearchType;
    private String mSearchKey;
    private String mSearchTypeName;
    private int searchStockOffset = 1;
    private int searchNewsOffset = 1;
    private List<ArticlesEntity> mNewsList;
    private List<StockPriceEntity> mStockList, mStockSingle;

    private StockPriceEntity mStock;
    private String mToken = "";
    private NewAdapter mAdapter;
    private StockResultsAdapter mStocksAdapter;
    private StockResultExactAdapter mOneStockAdapter;
    private TextView textView;
    private GuideHelper guideHelper;

    @OnClick(R.id.tv_xinxi)
    void chooseSearch() {
        SearchChoosePopup searchUtil = new SearchChoosePopup(this, mSearchType, mSearchKey);
        searchUtil.showPopupWindow(R.id.tv_xinxi);
        searchUtil.setPopupWindowFullScreen(false);
    }

    @OnClick(R.id.img_back)
    void quit() {
        finish();
    }

    @OnClick(R.id.imgcache)
    void clear() {
        mEtSeek.setText("");
    }

    @OnClick(R.id.tv_quxiao)
    void quitAgain() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search_results;
    }

    @Override
    protected void initEventAndData() {
        mEtSeek.addTextChangedListener(new EditChangedListener());
        mToken = SPHelper.get(Constants.REGISTER_MESSAGES_TOKEN, "");
        mSearchType = getIntent().getIntExtra(Constants.SEARCHING_TYPE_KEY, 0);
        mSearchKey = getIntent().getStringExtra(Constants.SEARCHING_KEY_USING);
        mSearchTypeName = getIntent().getStringExtra(Constants.SEARCHING_TYPE);
        mTvXinxi.setText(mSearchTypeName);
        mNewsList = new ArrayList<>();
        mAdapter = new NewAdapter(mContext, mNewsList);
        rvResultsNews.setAdapter(mAdapter);
        rvResultsNews.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        rvResultsNews.setLayoutManager(new LinearLayoutManager(this));
        rvResultsNews.setNestedScrollingEnabled(false);
        searchSave();

        //行情搜索结果
        mStockList = new ArrayList<>();
        mStocksAdapter = new StockResultsAdapter(mContext, mStockList, mSearchType);
        mStocksAdapter.setOnItemClickListener(new StockResultsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, StockResultsAdapter.ViewName viewName, int position) {
                if (viewName.equals(StockResultsAdapter.ViewName.PRACTISE)) {
                    StarUtil.Star(view, mToken, SearchDetailsActivity.this, mStockList, position);
                }
            }
        });
        rvResultStock.setAdapter(mStocksAdapter);
        rvResultStock.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        rvResultStock.setLayoutManager(new LinearLayoutManager(this));
        rvResultStock.setNestedScrollingEnabled(false);
        mStockSingle = new ArrayList<>();
        mOneStockAdapter = new StockResultExactAdapter(mContext, mStockSingle);
        mOneStockAdapter.setOnItemClickListener(new StockResultExactAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, StockResultExactAdapter.ViewName viewName, int position) {
                if (viewName.equals(StockResultExactAdapter.ViewName.PRACTISE)) {
                    StarUtil.Star(view, mToken, SearchDetailsActivity.this, mStockSingle, position);
                }
            }
        });
        rvOneResultStock.setAdapter(mOneStockAdapter);
        rvOneResultStock.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        rvOneResultStock.setLayoutManager(new LinearLayoutManager(this));
        rvOneResultStock.setNestedScrollingEnabled(false);
        setupSearch();
        setupRefresh();
    }

    //搜索记录
    private void searchSave() {
        mSearchSaveList = new ArrayList<>();
        swipeMenuRecyclerView = findViewById(R.id.rv_news_search_save);
        swipeMenuRecyclerView.setLongPressDragEnabled(false);// 开启拖拽。
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。
        swipeMenuRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                mSearchSaveList.remove(adapterPosition);
                mSearchSaveAdapter.notifyItemRemoved(adapterPosition);
            }
            App.getInstance().getSearchSaveDao().deleteAll();
            for (int j = 0; j < mSearchSaveList.size(); j++) {
                SearchSave mSearchSave = new SearchSave(null, mSearchSaveList.get(j).getSearchName());
                App.getInstance().getSearchSaveDao().insert(mSearchSave);
            }
        }
    };

    //侧滑设置
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 150;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem menuItem = new SwipeMenuItem(App.getInstance());
            menuItem.setText("删除");
            menuItem.setTextColor(ContextCompat.getColor(SearchDetailsActivity.this, R.color.white));
            menuItem.setHeight(height);
            menuItem.setWidth(width);
            menuItem.setBackground(R.color.red);
            swipeRightMenu.addMenuItem(menuItem);

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void setupSearch() {
        //  LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mEtSeek.setText(mSearchKey);
        clearAllResults();
        if (mSearchType == Constants.SEARCHING_TYPE_NEWS) {
            mEtSeek.setHint("搜索资讯");
            mTvXinxi.setText("资讯");
            mRlNewsResult.setVisibility(View.GONE);
            mRlhangqingResult.setVisibility(View.GONE);
            mPresenter.searchByTitle(mSearchKey, searchNewsOffset);
        } else if (mSearchType == Constants.SEARCHING_TYPE_STOCK) {
            mEtSeek.setHint("搜索行情");
            mTvXinxi.setText("行情");
            mRlNewsResult.setVisibility(View.GONE);
            mRlhangqingResult.setVisibility(View.GONE);
            mPresenter.searchStockByKey(mSearchKey, searchStockOffset);
        } else if (mSearchType == Constants.SEARCHING_TYPE_AUTHOR) {
            mTvXinxi.setText("作者");
            mEtSeek.setHint("搜索作者");
            mRlNewsResult.setVisibility(View.GONE);
            mRlhangqingResult.setVisibility(View.GONE);
            mPresenter.searchByAuthor(mSearchKey, searchNewsOffset);
        } else {
            mTvXinxi.setText("全部");
            mEtSeek.setHint("搜索全部");
            mPresenter.searchStockByKey(mSearchKey, searchStockOffset);
            mPresenter.searchByTitle(mSearchKey, searchNewsOffset);
        }
        mEtSeek.setImeActionLabel("搜索", KeyEvent.KEYCODE_ENTER);
        mEtSeek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    SnackbarUtil.showShort(mContext.getWindow().getDecorView(), "开始搜索" + mEtSeek.getText());
                    mSearchKey = mEtSeek.getText().toString();
                    SearchSave mSearchSave = new SearchSave(null, mSearchKey);
                    App.getInstance().getSearchSaveDao().insert(mSearchSave);
                    if (mSearchType == Constants.SEARCHING_TYPE_ALL) {
                        mPresenter.searchByTitle(mSearchKey, 1);
                        mPresenter.searchStockByKey(mSearchKey, searchStockOffset);
                    } else if (mSearchType == Constants.SEARCHING_TYPE_NEWS) {
                        mPresenter.searchByTitle(mSearchKey, 1);
                    } else if (mSearchType == Constants.SEARCHING_TYPE_STOCK) {
                        mPresenter.searchStockByKey(mSearchKey, 1);
                    } else if (mSearchType == Constants.SEARCHING_TYPE_AUTHOR) {
                        mPresenter.searchByAuthor(mSearchKey, searchNewsOffset);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (mEtSeek.getText().length() > 0) {
                mStockList.clear();
                mStockSingle.clear();
                mNewsList.clear();
                mRlNewsResult.setVisibility(View.GONE);
                mRlhangqingResult.setVisibility(View.GONE);
                swipeMenuRecyclerView.setVisibility(View.GONE);
                mLlSearchSave.setVisibility(View.VISIBLE);
                mClearRecord.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mEtSeek.getText().length() <= 0) {
                mStockList.clear();
                mStockSingle.clear();
                mNewsList.clear();
                mSearchSaveList.clear();
                Set set = new HashSet();
                for (Iterator iter = App.getInstance().getSearchSaveDao().queryBuilder().list().iterator(); iter.hasNext(); ) {
                    SearchSave searchSave = (SearchSave) iter.next();
                    if (set.add(searchSave.getSearchName())) {
                        mSearchSaveList.add(searchSave);
                        Collections.reverse(mSearchSaveList);
                    }
                }
                mSearchSaveAdapter = new SearchSavesAdapter(SearchDetailsActivity.this, mSearchSaveList);
                swipeMenuRecyclerView.setAdapter(mSearchSaveAdapter);
                swipeMenuRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        mSearchKey = mSearchSaveList.get(position).getSearchName();
                        mEtSeek.setText(mSearchKey);
                        mPresenter.searchStockByKey(mSearchKey, searchStockOffset);
                        mPresenter.searchByTitle(mSearchKey, searchNewsOffset);
                    }
                });
                swipeMenuRecyclerView.setVisibility(View.VISIBLE);
                mLlSearchSave.setVisibility(View.GONE);
                if (mSearchSaveList.size() > 0) {
                    initGuide();
                    mClearRecord.setVisibility(View.VISIBLE);
                }

            }
        }
    }

    private void initGuide() {
        String only = SPHelper.get("searchOnly", "");
        if (only.equals("")) {
            final GuideHelper guideHelper = new GuideHelper(this);
            View view = guideHelper.inflate(R.layout.item_guide);
            view.findViewById(R.id.rl_guide).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guideHelper.dismiss();
                }
            });
            GuideHelper.TipData tipData = new GuideHelper.TipData(view, Gravity.RIGHT|Gravity.TOP);
            tipData.setLocation(0, DisplayUtils.dipToPix(this, 65));
            guideHelper.addPage(tipData);
            guideHelper.show(false);
            SPHelper.save("searchOnly", "one");
        }
    }

    @OnClick(R.id.clear_record)
    void clearRecord() {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("确认清除所有搜索记录吗?");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().getSearchSaveDao().deleteAll();
                mSearchSaveList.clear();
                mSearchSaveAdapter.notifyDataSetChanged();
                mClearRecord.setVisibility(View.GONE);
            }
        });
        builder.show();
    }

    private void clearAllResults() {
        mStockList.clear();
        mOneStockAdapter.notifyDataSetChanged();
        mStockSingle.clear();
        mStocksAdapter.notifyDataSetChanged();
        mNewsList.clear();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateSearchContext(int searchType, String searchString) {
        mSearchType = searchType;
        mSearchKey = searchString;
        searchStockOffset = 1;
        searchNewsOffset = 1;
        setupSearch();
    }

    @Override
    public void userToken(String userToken) {
        if (userToken != null) {
            mToken = userToken;
            LogUtil.i("userToken", userToken);
        }
    }

    @Override
    public void err(String err) {
        //  mRefreshSearch.finishRefresh();
        ToastUtil.showToast(this, "暂无更多数据");
        mRefreshSearch.finishLoadmoreWithNoMoreData();
    }


    public void setupRefresh() {
        //下拉刷新
        mRefreshSearch.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                searchStockOffset = 1;
                searchNewsOffset = 1;
                if (mSearchType == Constants.SEARCHING_TYPE_ALL || mSearchType == Constants.SEARCHING_TYPE_NEWS) {
                    mPresenter.searchByTitle(mSearchKey, searchNewsOffset);
                } else if (mSearchType == Constants.SEARCHING_TYPE_ALL || mSearchType == Constants.SEARCHING_TYPE_STOCK) {
                    mPresenter.searchStockByKey(mSearchKey, searchStockOffset);
                }
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mRefreshSearch.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mSearchType == Constants.SEARCHING_TYPE_ALL || mSearchType == Constants.SEARCHING_TYPE_NEWS) {
                    mPresenter.searchByTitle(mSearchKey, ++searchNewsOffset);
                } else if (mSearchType == Constants.SEARCHING_TYPE_ALL || mSearchType == Constants.SEARCHING_TYPE_STOCK) {
                    mPresenter.searchStockByKey(mSearchKey, ++searchStockOffset);
                }
            }
        });
    }

    public void showStock(StockPriceEntity gotStock) {
//        mTvStockName.setText(gotStock.getStock_name());
//        mTvStockCode.setText(gotStock.getStock_code());
//        mTvStockChange.setText(NumericUtil.getStockValue(gotStock.getChng()));
//        mTvStockChangePercent.setText(NumericUtil.getPrefixedPercentage(gotStock.getChng_pct()));
//        mTvStockValue.setText(NumericUtil.getStockValue(gotStock.getTotal_value()));

        mStock = gotStock;
        mStocksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showStocks(List<StockPriceEntity> stockIndexes) {
        if (stockIndexes.size() == 1) {
            mStockSingle.clear();
            mStockList.clear();
            mStockSingle.addAll(stockIndexes);
            mOneStockAdapter.notifyDataSetChanged();
            mStocksAdapter.notifyDataSetChanged();
        } else {
            mStockSingle.clear();
            mStockList.clear();
            mStockList.addAll(stockIndexes);
            mOneStockAdapter.notifyDataSetChanged();
            mStocksAdapter.notifyDataSetChanged();
            if (stockIndexes.size() <= 0) {
                mStockSingle.clear();
                mStockList.clear();
                mRlhangqingResult.setVisibility(View.GONE);
            } else {
                mRlhangqingResult.setVisibility(View.VISIBLE);
            }
        }
        mRefreshSearch.finishLoadmore();
    }

    @Override
    public void showArticles(List<ArticlesEntity> newsList) {
        if (searchNewsOffset == 1) {
            mNewsList.clear();
        }
        mNewsList.addAll(newsList);
        mAdapter.notifyDataSetChanged();
        if (newsList.size() <= 0) {
            mNewsList.clear();
            mNewsList.addAll(newsList);
            mRlNewsResult.setVisibility(View.GONE);
        } else {
            mRlNewsResult.setVisibility(View.VISIBLE);
        }
        mRefreshSearch.finishRefresh();
        mRefreshSearch.finishLoadmore();
    }


}
