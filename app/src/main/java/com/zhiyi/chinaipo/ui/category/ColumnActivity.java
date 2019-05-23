package com.zhiyi.chinaipo.ui.category;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.CategoryConnector;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;
import com.zhiyi.chinaipo.presenters.news.CategoryPresenter;
import com.zhiyi.chinaipo.ui.fragment.HomeFragment;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 栏目管理页
 *
 * @author
 */
public class ColumnActivity extends BaseActivity<CategoryPresenter> implements CategoryConnector.View, AdapterView.OnItemClickListener, DragGrid.EnterEditModeListener {
    /**
     * 用户栏目的GRIDVIEW
     */
    private DragGrid userGridView;
    /**
     * 其它栏目的GRIDVIEW
     */
    private OtherGridView otherGridView;
    /**
     * 用户栏目对应的适配器，可以拖动
     */
    DragAdapter userAdapter;
    /**
     * 其它栏目对应的适配器
     */
    OtherAdapter otherAdapter;
    /**
     * 点击保存
     */
    TextView tvDragDone;
    /**
     * 其它栏目列表
     */
    List<CategoryEntity> otherColumnList;
    /**
     * 用户栏目列表
     */
    List<CategoryEntity> userColumnList;
    List<CategoryEntity> userList;
    List<CategoryEntity> otherList;
    List<CategoryEntity> AllList;
    List<CategoryEntity> AllColumnList;
    /**
     * 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */
    boolean isMove = false;
    private boolean isEditable = false;//是否是编辑模式
    /**
     * 编辑模式可以长按拖动排序，点击删除（不是真正意义上的删除，只是移动到非用户栏目）
     * 长按进入编辑模式
     */
    private TextView bianji, tip;
    private ImageView mImgback;
    /**
     * 跳转到指定页面
     */
    public static final String COLUMN_ID = "columnId";
    /**
     * 已选频道保存
     */
    public static final String USER_LIST = "userList";
    public static final String SP_USER_LIST = "spUserList";
    /**
     * 更多频道保存
     */
    public static final String OTHER_LIST = "otherList";
    public static final String SP_OTHER_LIST = "spOtherList";
    int userSize, otherSize, ColumnSize;
    private int notSelected = 0;
    private int selected = 0;
    long mBackTime;

    @Override
    protected int getLayout() {
        return R.layout.activity_home_column;
    }

    @Override
    protected void initEventAndData() {
        initViews();
        initData();
    }

    public void initViews() {
        userGridView = findViewById(R.id.userGridView);
        otherGridView = findViewById(R.id.otherGridView);
        bianji = findViewById(R.id.my_category_tip_text);
        tip = findViewById(R.id.tip_text);
        bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEditable = true;
                userAdapter.setIsShowDelete(isEditable);
                tvDragDone.setVisibility(View.VISIBLE);
                bianji.setVisibility(View.GONE);
                tip.setText(R.string.x_colum_tip1);
            }
        });
        /**
         * 关闭按钮
         */
        mImgback = findViewById(R.id.img_off);
        mImgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    boolean redact = (userColumnList.size() > 0 && selected == 1) || (userColumnList.size() > 0 && notSelected == 1);
                    if (redact) {
                        saveColumn();
                        Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                        intent.putExtra(COLUMN_ID, userAdapter.getItem(0).getId());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        finish();
                    }

                    overridePendingTransition(0, R.anim.column_hide);
                }
            }
        });
        /**
         * 保存调整
         */
        tvDragDone = findViewById(R.id.tvDragDone);
        tvDragDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    isEditable = false;
                    selected = 1;
                    userAdapter.setIsShowDelete(isEditable);
                    tvDragDone.setVisibility(View.GONE);
                    bianji.setVisibility(View.VISIBLE);
                    tip.setText(R.string.x_colum_tip);
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        /*userList = new ArrayList<>();
        otherList = new ArrayList<>();*/
        userColumnList = new ArrayList<>();
        otherColumnList = new ArrayList<>();
        mPresenter.getCategories(1);
        mPresenter.getCategoriesTwo(2);
        if (SPHelper.getDataList(USER_LIST).size() > 0) {
            //将我的频道和频道推荐合并
            AllColumnList = SPHelper.getDataList(USER_LIST);
            LogUtil.i("AllList", AllColumnList.toString());
            if (SPHelper.getDataList(OTHER_LIST).size() > 0) {
                AllColumnList.addAll(SPHelper.getDataList(OTHER_LIST));
                LogUtil.i("AllList1", AllColumnList.toString());
            }
            userColumnList = SPHelper.getDataList(USER_LIST);
            otherColumnList = SPHelper.getDataList(OTHER_LIST);
            //检测网络获取的频道是否有删减
            if (SystemUtil.isNetworkConnected()) {
                //合并网络请求的我的频道和频道推荐
                AllList = SPHelper.getDataList(SP_USER_LIST);
                AllList.addAll(SPHelper.getDataList(SP_OTHER_LIST));
                //遍历对比网络请求的数据个本地数据
                for (CategoryEntity entity : AllColumnList) {
                    //如果本地有多余的频道进行排查到底实在我的频道还是在推荐中然后进行删除
                    if (!AllList.contains(entity)) {
                        for (int i = 0; i < SPHelper.getDataList(USER_LIST).size(); i++) {
                            if (userColumnList.get(i).equals(entity)) {
                                userColumnList.remove(entity);
                                SPHelper.setDataList(USER_LIST, userColumnList);
                                //   LogUtil.i("sadasdasdada", entity.getName());
                            }
                        }
                        for (int i = 0; i < SPHelper.getDataList(OTHER_LIST).size(); i++) {
                            if (otherColumnList.get(i).equals(entity)) {
                                otherColumnList.remove(entity);
                                SPHelper.setDataList(OTHER_LIST, otherColumnList);
                                //  LogUtil.i("sadasdasdada2", entity.getName());
                            }
                      /*  if (otherList.get(i).getId() == entity.getId() && !otherList.get(i).getName().equals(entity.getName())) {
                            otherList.remove(entity);
                            otherList.add(i, entity);
                           *//* Set set = new HashSet();
                            for (Iterator iterator = otherList.iterator(); iterator.hasNext(); ) {
                                CategoryEntity entity1 = (CategoryEntity) iterator.next();
                                if (set.add(entity1)) {
                                    otherList.add(entity1);
                                }
                            }*//*
                            SPHelper.setDataList(OTHER_LIST, otherList);
                            LogUtil.i("sadasdasdada", entity.getName());
                        }*/
                        }
                    }
                }
            }
        }
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
        userGridView.setOnEnterEditModeListener(this);
    }


    @Override
    public void doSth() {
        isEditable = true;
        tvDragDone.setVisibility(View.VISIBLE);
        bianji.setVisibility(View.GONE);
        tip.setText(R.string.x_colum_tip1);
    }

    /**
     * GRIDVIEW对应的ITEM点击监听接口
     */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view,
                            final int position, long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if (isMove) {
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                if (isEditable) {
                    //position为 0的不可以进行任何操作
                    if (position != 0) {
                        final ImageView moveImageView = getView(view);
                        if (moveImageView != null) {
                            TextView newTextView = view.findViewById(R.id.text_item);
                            final int[] startLocation = new int[2];
                            newTextView.getLocationInWindow(startLocation);
                            final CategoryEntity column = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                            otherAdapter.setVisible(false);
                            //添加到最后一个
                            otherAdapter.addItem(column);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        int[] endLocation = new int[2];
                                        //获取终点的坐标
                                        otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                        MoveAnim(moveImageView, startLocation, endLocation, column, userGridView);
                                        userAdapter.setRemove(position);
                                    } catch (Exception localException) {
                                    }
                                }
                            }, 50L);
                        }
                    }
                } else {
                    saveColumn();
                    Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                    intent.putExtra(COLUMN_ID, userAdapter.getItem(position).getId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final CategoryEntity column = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    notSelected = 1;
                    //添加到最后一个
                    userAdapter.addItem(column);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, column, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 退出时候保存选择后数据的设置
     */
    private void saveColumn() {
        SPHelper.setDataList(USER_LIST, userAdapter.getColumnList());
        SPHelper.setDataList(OTHER_LIST, otherAdapter.getColumnList());
        // LogUtil.i("USER_LIST", userAdapter.getColumnList().size() + "");
    }

    /**
     * 点击ITEM移动动画
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation,
                          final CategoryEntity movecolumn,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        LogUtil.i("posiition", initLocation + "");

        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();

                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        LogUtil.i("posiition", "possss");
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void updateCategories(List<CategoryEntity> lists) {
        SPHelper.setDataList(SP_USER_LIST, lists);
        if (SPHelper.getDataList(USER_LIST).size() > 0) {
            //   userColumnList = SPHelper.getDataList(USER_LIST);
            LogUtil.i("1", userColumnList.toString());
            //检测网络获取的频道是否有增加
            for (CategoryEntity list : lists) {
                LogUtil.i("1", "2");
                //判断网络的频道和本地保存的完全相等就直接引用sp里面的
                if (!AllColumnList.contains(list)) {
                    userColumnList.add(list);
                    LogUtil.i("有的值11", userColumnList.toString());
                }
            }
        } else {
            userColumnList = lists;
            LogUtil.i("1", "1");
        }
        userAdapter = new DragAdapter(ColumnActivity.this, userColumnList);
        userGridView.setAdapter(userAdapter);
    }


    @Override
    public void updateCategoriesTwo(List<CategoryEntity> lists) {
        SPHelper.setDataList(SP_OTHER_LIST, lists);
        if (SPHelper.getDataList(USER_LIST).size() > 0) {
            //   otherColumnList = SPHelper.getDataList(OTHER_LIST);
            for (CategoryEntity list : lists) {
                LogUtil.i("1", "3");
                if (!AllColumnList.contains(list)) {
                    otherColumnList.add(list);
                }
            }
        } else {
            otherColumnList = lists;
            LogUtil.i("2", "2");
        }
        otherAdapter = new OtherAdapter(ColumnActivity.this, otherColumnList);
        otherGridView.setAdapter(otherAdapter);
    }

    @Override
    public void updateTableTitle(int titleId) {

    }

    @Override
    public void indexNews(int index) {

    }

    @Override
    public void bannerImg(String img) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!RepeatCllickUtil.isFastDoubleClick()) {
                boolean redact = (userColumnList.size() > 0 && selected == 1) || (userColumnList.size() > 0 && notSelected == 1);
                if (redact) {
                    saveColumn();
                    Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                    intent.putExtra(COLUMN_ID, userAdapter.getItem(0).getId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    finish();
                }

                overridePendingTransition(0, R.anim.column_hide);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        userColumnList = SPHelper.getDataList(USER_LIST);
        otherColumnList = SPHelper.getDataList(OTHER_LIST);
        userAdapter = new DragAdapter(ColumnActivity.this, userColumnList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(ColumnActivity.this, otherColumnList);
        otherGridView.setAdapter(otherAdapter);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}