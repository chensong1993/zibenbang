package com.zhiyi.chinaipo.ui.activity.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.greendao.StockCodeEntityDao;
import com.zhiyi.chinaipo.ui.adapter.followed.CompileAdapter;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.StatusBarUtil;
import com.zhiyi.chinaipo.util.recycleviewdivider.RecycleViewDivider;

import org.greenrobot.greendao.query.Query;

import java.util.Collections;
import java.util.List;

/**
 * 具体使用方法
 * com.yanzhenjie:recyclerview-swipe:1.1.1
* 自选模块，在编辑里面的自选股
 */
public class OptionalCompileActivity extends AppCompatActivity implements View.OnClickListener {
    private SwipeMenuRecyclerView swipeMenuRecyclerView;
    private List<StockCodeEntity> mStockCodeEntityList;
    private CompileAdapter mStockCodeAdapter;
    private LinearLayoutManager linearLayoutManager;
    //完成
    private TextView mFinish;
    private RelativeLayout mRlBack;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixuan);
        //状态栏字体变黑
        StatusBarUtil.StatusBarLightMode(this,true);
        init();
    }


    @SuppressLint("WrongViewCast")
    private void init() {
        token = SPHelper.get(Constants.REGISTER_MESSAGES_TOKEN, "");
        swipeMenuRecyclerView = findViewById(R.id.rv_zixuan);
        swipeMenuRecyclerView.setLongPressDragEnabled(true);// 开启拖拽。
        Query query = App.getInstance().getStockCodeDao().queryBuilder().where(StockCodeEntityDao.Properties.Token.eq(token)).build();
        mStockCodeEntityList= query.list();
        mStockCodeAdapter=new CompileAdapter(this,mStockCodeEntityList,swipeMenuRecyclerView);
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeMenuRecyclerView.setAdapter(mStockCodeAdapter);
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setOnItemMoveListener(mItemMoveListener);// Item的拖拽/侧滑删除时，手指状态发生变化监听。
        swipeMenuRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this,R.color.hui)));
        linearLayoutManager = new LinearLayoutManager(this);

        //置顶
        mStockCodeAdapter.setOnSlide(new CompileAdapter.onSlide() {
            @Override
            public void onTop(int i) {
                if(i>0){
                    StockCodeEntity s = mStockCodeEntityList.get(i);
                    mStockCodeEntityList.remove(s);
                    App.getInstance().getStockCodeDao().deleteAll();
                    mStockCodeAdapter.notifyItemInserted(0);
                    mStockCodeEntityList.add(0, s);
                    mStockCodeAdapter.notifyItemRemoved(i + 1);
                    for (int j = 0; j < mStockCodeEntityList.size(); j++) {
                        StockCodeEntity   mStockCodeEntity = new StockCodeEntity(null, token,mStockCodeEntityList.get(j).getName(),mStockCodeEntityList.get(j).getCode(),mStockCodeEntityList.get(j).getPrice(),mStockCodeEntityList.get(j).getPct(),mStockCodeEntityList.get(j).getTurnover());
                        App.getInstance().getStockCodeDao().insert(mStockCodeEntity);
                    }

                    if (linearLayoutManager.findFirstVisibleItemPosition()<0) {
                        return;
                    } else {
                        swipeMenuRecyclerView.scrollToPosition(0);
                    }
                }
            }
        });
        swipeMenuRecyclerView.setAdapter(mStockCodeAdapter);
        swipeMenuRecyclerView.setLayoutManager(linearLayoutManager);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。

        //完车
        mFinish = (TextView) findViewById(R.id.tv_finish);
        mFinish.setOnClickListener(this);
        //返回
        mRlBack = findViewById(R.id.rl_back);
        mRlBack.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                finish();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
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
                mStockCodeEntityList.remove(adapterPosition);
                mStockCodeAdapter.notifyItemRemoved(adapterPosition);
            }
            App.getInstance().getStockCodeDao().deleteAll();
            for (int j = 0; j < mStockCodeEntityList.size(); j++) {
                StockCodeEntity   mStockCodeEntity = new StockCodeEntity(null, token,mStockCodeEntityList.get(j).getName(),mStockCodeEntityList.get(j).getCode(),mStockCodeEntityList.get(j).getPrice(),mStockCodeEntityList.get(j).getPct(),mStockCodeEntityList.get(j).getTurnover());
                App.getInstance().getStockCodeDao().insert(mStockCodeEntity);
            }
        }
    };

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType())
            {
                return false;
            }

            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(mStockCodeEntityList, fromPosition, toPosition);
            mStockCodeAdapter.notifyItemMoved(fromPosition, toPosition);
            App.getInstance().getStockCodeDao().deleteAll();
            for (int j = 0; j < mStockCodeEntityList.size(); j++) {
                StockCodeEntity   mStockCodeEntity = new StockCodeEntity(null, token,mStockCodeEntityList.get(j).getName(),mStockCodeEntityList.get(j).getCode(),mStockCodeEntityList.get(j).getPrice(),mStockCodeEntityList.get(j).getPct(),mStockCodeEntityList.get(j).getTurnover());
                App.getInstance().getStockCodeDao().insert(mStockCodeEntity);
            }
            return true;
        }

        //这个是在开启侧滑功能是使用
        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
           /* int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            zhangfu.remove(position);
            mZhuangfuAdapter.notifyItemRemoved(position);
            Toast.makeText(OptionalCompileActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();*/
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
            menuItem.setTextColor(ContextCompat.getColor(OptionalCompileActivity.this,R.color.white));
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


}
