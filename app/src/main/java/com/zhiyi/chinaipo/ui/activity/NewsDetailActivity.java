package com.zhiyi.chinaipo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.ArticleDetailConnector;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.presenters.news.ArticleDetailPresenter;
import com.zhiyi.chinaipo.ui.adapter.news.RelatedNewsAdapter;
import com.zhiyi.chinaipo.ui.adapter.news.WebDetailAdapter;
import com.zhiyi.chinaipo.ui.widget.MyScrollView;
import com.zhiyi.chinaipo.ui.widget.ShareBottomDialog;
import com.zhiyi.chinaipo.util.ActivityCollector;
import com.zhiyi.chinaipo.util.FormatUtil;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SwipeBackLayout;

public class NewsDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailConnector.View {
    private static final String TAG = "NewsDetailActivity";
    private int originalId = 0;                 // 栏目id
    private int id = 0;
    ShareBottomDialog dialog;
    @BindView(R.id.tv_title)
    TextView mTvActivityTitle;
    @BindView(R.id.img_back)
    ImageView mImgbcak;
    @BindView(R.id.img_share)
    ImageView mImgHeadShare;
    @BindView(R.id.rv_detaillist)
    RecyclerView mRvDetaillist;
    @BindView(R.id.rv_otherlist)
    RecyclerView mRvOtherlist;
    @BindView(R.id.ll_back)
    RelativeLayout mRlTopTitle;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    @BindView(R.id.tv_again_loading)
    TextView mTvAgainLoad;
    @BindView(R.id.tv_news_content)
    TextView mTvNewsContent;
    List<ArticleDetailEntity> WebDetailList;
    WebDetailAdapter webDetailAdapter;
    RelatedNewsAdapter mRelatedAdapter;
    private List<ArticleDetailEntity.OtherLinks> mRelatedLinks;
    private String shareTitle;
    private String shareDetail;
    @BindView(R.id.ms_scroll)
    MyScrollView myScrollView;
    int mHeight;
    String scrollTitle;

    @OnClick(R.id.rl_back)
    void quit() {
        MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
        if (activity != null) {
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initEventAndData() {
        // 加载网络 data
        mRelatedLinks = new ArrayList<>();
        WebDetailList = new ArrayList<>();
        siwpeBack();
        mRlProgress.setVisibility(View.VISIBLE);
        if (getIntent().getIntExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, -1) >0 && getIntent().getIntExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, -1) >0) {
            originalId = getIntent().getIntExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, 0);
            id = getIntent().getIntExtra(Constants.NEWS_DETAIL_ID, 0);


            //获取推送的参数
            if (getIntent().getExtras() != null) {
                Bundle bun = getIntent().getExtras();
                Set<String> keySet = bun.keySet();
                // LogUtil.i("pushSize", keySet.size() + "");
                for (String key : keySet) {
                    String value = bun.getString(key);
                    if (value != null && FormatUtil.isNumeric(value)) {
                        originalId = Integer.parseInt(value);
                    }
                }
            }
            //  LogUtil.i("uPushMessage", push);

            mPresenter.getNewsDetail(id, originalId);
            mImgHeadShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!RepeatCllickUtil.isFastDoubleClick()) {
                        LogUtil.i(TAG, originalId + "originalId");
                        dialog = ShareBottomDialog.newInstance(shareTitle, shareDetail, originalId, 0);
                        dialog.show(getSupportFragmentManager());
                    }
                }
            });
            prepareUI();

        }else {
            ToastUtil.showToast(this,"请求出错");
        }
    }

    private void prepareUI() {
        webDetailAdapter = new WebDetailAdapter(WebDetailList, NewsDetailActivity.this);
        mRvDetaillist.setAdapter(webDetailAdapter);
        mRvDetaillist.setLayoutManager(new LinearLayoutManager(this));
        mRvDetaillist.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
//        mRelatedAdapter = new RelatedNewsAdapter(NewsDetailActivity.this, mRelatedLinks);
//        mRvOtherlist.setAdapter(mRelatedAdapter);
//        mRvOtherlist.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 12, ContextCompat.getColor(this, R.color.hui)));
//        mRvOtherlist.setLayoutManager(new LinearLayoutManager(this));
        scrollDis();
    }


    public void scrollDis() {
        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = mRlTopTitle.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRlTopTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = 100;
                //注册滑动监听
                myScrollView.setOnObservableScrollViewListener(new MyScrollView.OnObservableScrollViewListener() {
                    @Override
                    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
                        if (t <= 0) {
                            //顶部图处于最顶部，标题栏透明
                            mTvActivityTitle.setText(scrollTitle);
                        } else if (t > 0 && t < mHeight) {
                            mTvActivityTitle.setText(scrollTitle);
                        } else {
                            mTvActivityTitle.setText(shareTitle);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void showNewsDetail(ArticleDetailEntity newsDetail) {
        mTvActivityTitle.setText(newsDetail.getCategoryName());
        scrollTitle = newsDetail.getCategoryName();
        shareTitle = newsDetail.getTitle();
        shareDetail = newsDetail.getLead_in();
        WebDetailList.add(newsDetail);
        webDetailAdapter.addAll(WebDetailList);
        //mRelatedAdapter.addAll(newsDetail.getOtherLinks());
        mRlProgress.setVisibility(View.GONE);
        mTvAgainLoad.setVisibility(View.GONE);
        mTvNewsContent.setVisibility(View.GONE);

        LogUtil.i(TAG, "showNewsDetail: " + mRelatedLinks.size());
    }


    @Override
    public void err() {
        mRlProgress.setVisibility(View.GONE);
        mTvAgainLoad.setVisibility(View.VISIBLE);
        mTvNewsContent.setVisibility(View.VISIBLE);

        ToastUtil.showToast(this, "请求超时");
    }

    @OnClick(R.id.tv_news_content)
    void againLoad() {
        mPresenter.getNewsDetail(id, originalId);
    }

    //判断mainactivty是否已经启动
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
            if (activity != null) {
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void siwpeBack() {
        getSwipeBackLayout().addSwipeListener(new SwipeBackLayout.OnSwipeListener() {
            @Override
            public void onDragStateChange(int state) {
                // Drag state
                if (state == 3) {
                    MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                    if (activity != null) {
                        finish();
                    } else {
                        startActivity(new Intent(NewsDetailActivity.this, MainActivity.class));
                        finish();
                    }
                }

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                // 触摸的边缘flag
            }

            @Override
            public void onDragScrolled(float scrollPercent) {
                // 滑动百分比
            }
        });
    }

    /**
     * 收藏news
     */
//    private void collectArticle() {
//        if (UserBean.isLogin()) {
//            HashMap<String, String> parameters = new HashMap<>();
//            parameters.put("username", UserBean.shared().getUsername());
//            parameters.put("userid", UserBean.shared().getUserid());
//            parameters.put("token", UserBean.shared().getToken());
//            parameters.put("classid", classid);
//            parameters.put("id", id);
//            NetworkUtils.shared.post(APIs.ADD_DEL_FAVA, parameters, new NetworkUtils.StringCallback() {
//                @Override
//                public void onError(Call call, Exception e, int id) {
//                    ProgressHUD.showInfo(NewsDetailActivity.this, "Network error");
//                }
//
//                @Override
//                public void onResponse(String response, int id) {
//                    Timber.d(TAG, response);
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String tipString = jsonObject.getString("info");
//                        if (jsonObject.getString("err_msg").equals("success")) {
//                            if (jsonObject.getJSONObject("result").getInt("status") == 1) {
//                                // 收藏成功
//                                tipString = "收藏成功";
//                                mCollectionButton.setImageResource(R.drawable.bottom_bar_collection_selected);
//                            } else {
//                                // 取消收藏成功
//                                tipString = "取消收藏";
//                                mCollectionButton.setImageResource(R.drawable.bottom_bar_collection_normal2);
//                            }
//                        }
//                        if (tipString.equals("您还没登录!")) {
//                            showLoginTipDialog();
//                            // 注销本地user信息
//                            UserBean.shared().logout();
//                        } else {
//                            ProgressHUD.showInfo(NewsDetailActivity.this, tipString);
//                        }
//                        collectionButtonSpringAnimation();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        ProgressHUD.showInfo(NewsDetailActivity.this, "data parsing error");
//                    }
//                }
//            });
//        } else {
//            showLoginTipDialog();
//        }
//    }

    /**
     * show 登录提示会话框
     */
    private void showLoginTipDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("您还未登录");
//        builder.setMessage("登录以后才能收藏哦！");
////        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                LoginActivity.start(NewsDetailActivity.this);
////            }
////        });
//        builder.setNegativeButton("以后再说", null);
//        builder.show();
    }

    /**
     * 收藏按钮弹簧动画
     */
    private void collectionButtonSpringAnimation() {
//        SpringSystem springSystem = SpringSystem.create();
//        Spring spring = springSystem.createSpring();
//        spring.addListener(new SimpleSpringListener() {
//
//            @Override
//            public void onSpringUpdate(Spring spring) {
//                float value = (float) spring.getCurrentValue();
//                float scale = value * 2f;
//                mCollectionButton.setScaleX(scale);
//                mCollectionButton.setScaleY(scale);
//            }
//        });
//
//        spring.setEndValue(0.5);
    }

    /**
     * 弹出评论' 会话框
     */
    private void showCommentDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = View.inflate(this, R.layout.dialog_comment, null);
//        builder.setView(view);
//        builder.setCancelable(true);
//        commentDialog = builder.create();
//        commentDialog.show();
//
//        commentEditText = (EditText) view.findViewById(R.id.et_comment_edittext);
//        Button cancelButton = (Button) view.findViewById(R.id.btn_set_font_cancel);
//        Button sendButton = (Button) view.findViewById(R.id.btn_set_font_send);
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                commentDialog.dismiss();
//            }
//        });
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String comment = commentEditText.getText().toString();
//                if (!TextUtils.isEmpty(comment)) {
//                    sendComment(comment);
//                    commentDialog.dismiss();
//                } else {
//                    ProgressHUD.showInfo(NewsDetailActivity.this, "请输入评论 content");
//                }
//            }
//        });
//
//        // 自动弹出键盘
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            public void run() {
//                InputMethodManager inputManager = (InputMethodManager) commentEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputManager.showSoftInput(commentEditText, 0);
//            }
//
//        }, 250);
    }

    /**
     * 发布评论 - 把所有评论信息' user名都改了。。
     *
     * @param comment 评论信息
     */
    private void sendComment(String comment) {

//        HashMap<String, String> parameters = new HashMap<>();
//        parameters.put("classid", classid);
//        parameters.put("id", id);
//        parameters.put("saytext", comment);
//        if (UserBean.isLogin()) {
//            parameters.put("nomember", "0");
//            parameters.put("username", UserBean.shared().getUsername());
//            parameters.put("userid", UserBean.shared().getUserid());
//            parameters.put("token", UserBean.shared().getToken());
//        } else {
//            parameters.put("nomember", "1");
//        }
//
//        NetworkUtils.shared.post(APIs.SUBMIT_COMMENT, parameters, new NetworkUtils.StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ProgressHUD.showInfo(NewsDetailActivity.this, "Network error");
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Timber.d(TAG, response);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (jsonObject.getString("err_msg").equals("success")) {
//                        // 评论成功后，去重 new 加载评论信息
//                        loadCommentFromNetwork();
//                        ProgressHUD.showInfo(NewsDetailActivity.this, "评论成功");
//                    } else {
//                        ProgressHUD.showInfo(NewsDetailActivity.this, jsonObject.getString("info"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    ProgressHUD.showInfo(NewsDetailActivity.this, "data parsing error");
//                }
//            }
//        });

    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    public static class Builder {

        private String title;
        private int id;
        private int originalId;
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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setOriginalId(int originalId) {
            this.originalId = originalId;
            return this;
        }
    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, NewsDetailActivity.class);
        intent.putExtra(Constants.NEWS_DETAIL_ID, builder.id);
        intent.putExtra(Constants.NEWS_DETAIL_ORIGINAL_ID, builder.originalId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.mContext.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
