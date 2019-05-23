package com.zhiyi.chinaipo.ui.activity.profile;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.zhiyi.chinaipo.base.SimpleActivity;
import com.zhiyi.chinaipo.ui.adapter.followed.CompileAdapter;
import com.zhiyi.chinaipo.ui.widget.recycleviewdivider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 具体使用方法
 * com.yanzhenjie:recyclerview-swipe:1.1.1
 * 自选模块，在编辑里面的自选股
 */
public class EditFavoriteActivity extends SimpleActivity {
    @BindView(R.id.rv_zixuan)
    SwipeMenuRecyclerView swipeMenuRecyclerView;
    private List<String> followeds;
    private CompileAdapter mZhuangfuAdapter;
    private LinearLayoutManager linearLayoutManager;
    //完成
    @BindView(R.id.tv_finish)
    TextView mFinish;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixuan);
        ButterKnife.bind(this);

    }*/

    @Override
    protected int getLayout() {
        return R.layout.activity_zixuan;
    }

    @Override
    protected void initEventAndData() {
        init();
    }


    private void init() {
        followeds = new ArrayList<>();
        swipeMenuRecyclerView.setLongPressDragEnabled(true);// 开启拖拽。
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
      //  mZhuangfuAdapter = new CompileAdapter(this, followeds, swipeMenuRecyclerView);
        swipeMenuRecyclerView.setOnItemMoveListener(mItemMoveListener);// Item的拖拽/侧滑删除时，手指状态发生变化监听。
        swipeMenuRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.hui)));
        linearLayoutManager = new LinearLayoutManager(this);

        //置顶
        mZhuangfuAdapter.setOnSlide(new CompileAdapter.onSlide() {
            @Override
            public void onTop(int i) {
                if (i > 0) {
                    String s = followeds.get(i);
                    followeds.remove(s);
                    mZhuangfuAdapter.notifyItemInserted(0);
                    followeds.add(0, s);
                    mZhuangfuAdapter.notifyItemRemoved(i + 1);
                    if (linearLayoutManager.findFirstVisibleItemPosition() < 0) {
                        return;
                    } else {
                        swipeMenuRecyclerView.scrollToPosition(0);
                    }
                }
            }
        });
        swipeMenuRecyclerView.setAdapter(mZhuangfuAdapter);
        swipeMenuRecyclerView.setLayoutManager(linearLayoutManager);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。

    }

    @OnClick(R.id.tv_finish)
    void f() {
        finish();
    }

    @OnClick(R.id.rl_back)
    void b() {
        finish();
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
                followeds.remove(adapterPosition);
                mZhuangfuAdapter.notifyItemRemoved(adapterPosition);
            }

        }
    };

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) {
                return false;
            }
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(followeds, fromPosition, toPosition);
            mZhuangfuAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        //这个是在开启侧滑功能是使用
        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            followeds.remove(position);
            mZhuangfuAdapter.notifyItemRemoved(position);
        }
    };
    //侧滑设置
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 150;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem menuItem = new SwipeMenuItem(EditFavoriteActivity.this);
            menuItem.setText("删除");
            menuItem.setTextColor(ContextCompat.getColor(EditFavoriteActivity.this, R.color.white));
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
