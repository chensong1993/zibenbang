package com.zhiyi.chinaipo.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DownloadingService;
import com.shizhefei.guide.GuideHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.RootActivity;
import com.zhiyi.chinaipo.base.connectors.main.MainConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.components.UpdateService;
import com.zhiyi.chinaipo.models.event.HomeIndex;
import com.zhiyi.chinaipo.presenters.main.MainPresenter;
import com.zhiyi.chinaipo.ui.adapter.MainViewPagerAdapter;
import com.zhiyi.chinaipo.ui.widget.navigation.BottomNavigation;
import com.zhiyi.chinaipo.ui.widget.navigation.BottomNavigationAdapter;
import com.zhiyi.chinaipo.ui.widget.navigation.BottomNavigationViewPager;
import com.zhiyi.chinaipo.util.ActivityCollector;
import com.zhiyi.chinaipo.util.DisplayUtils;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;

//import com.zhiyi.chinaipo.ui.fragment.main.AboutFragment;
//import com.zhiyi.chinaipo.ui.main.fragment.LikeFragment;
//import com.zhiyi.chinaipo.ui.main.fragment.SettingFragment;

public class MainActivity extends RootActivity<MainPresenter> implements MainConnector.View {

    // UI
    @BindView(R.id.view_pager)
    BottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)
    BottomNavigation bottomNavigation;
    @BindView(R.id.ll_dow)
    LinearLayout mLinearLayout;
    AgentWeb mAgentWeb;
    long mBackTime;
    // Adapter
    private MainViewPagerAdapter adapter;
    private DownloadingService mDownloadingService;
    private AgentWebDownloader.ExtraService mExtraService;
    int[] index = new int[5];
    //homeIndex homeIndex;
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //检测该类是否打开
        ActivityCollector.addActivity(this, getClass());
        // 隐藏导航栏Items
        BottomNavigationAdapter navigationAdapter = new BottomNavigationAdapter(this, R.menu.menu_bottom_navigation);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation);
        bottomNavigation.setBehaviorTranslationEnabled(false);


        // 隐藏导航栏标题
        bottomNavigation.setTitleState(BottomNavigation.TitleState.ALWAYS_SHOW);
        // 导航点击事件
        bottomNavigation.setOnTabSelectedListener(new BottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, false);
                switch (position) {
                    case 0:
                        refreshData(position);
                        break;
                    case 1:
                        refreshData(position);
                        break;
                    case 2:
                        refreshData(position);
                        break;
                    case 3:
                        refreshData(position);
                        break;
                    case 4:
                        refreshData(position);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

        viewPager.setOffscreenPageLimit(5);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }


    //点击底部刷新数据
    private void refreshData(int position) {
        index[position]++;
        if (index[position] > 1) {
            RxBus.getDefault().post(new HomeIndex(position));
        }
        for (int i = 0; i < index.length; i++) {
            if (i != position) {
                index[i] = 0;
            }
        }
    }

    @Override
    protected void initEventAndData() {
        //   showExitDialog();
        setSwipeBackEnable(false);
    }

    private void initGuide() {
        String only = SPHelper.get("MainOnly", "");
        if (only.equals("")) {
            final GuideHelper guideHelper = new GuideHelper(this);
            View view = guideHelper.inflate(R.layout.item_guide);
            view.findViewById(R.id.rl_guide).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guideHelper.dismiss();
                }
            });
            GuideHelper.TipData tipData = new GuideHelper.TipData(view, Gravity.RIGHT | Gravity.TOP);
            tipData.setLocation(0, DisplayUtils.dipToPix(this, 60));
            guideHelper.addPage(tipData);
            guideHelper.show(false);
            SPHelper.save("MainOnly", "one");
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mBackTime > 2000) {
                ToastUtil.showToast(this, getString(R.string.press_the_exit_again));
                mBackTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showUpdateDialog(String versionContent) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.new_version);
        builder.setMessage(versionContent);
        builder.setNegativeButton(R.string.x_cancel, null);
        builder.setPositiveButton(R.string.update_immediately, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissions();
            }
        });
        builder.show();
    }

    @Override
    public void startDownloadService() {
        startService(new Intent(mContext, UpdateService.class));
    }

    public void checkPermissions() {
        mPresenter.checkPermissions(new RxPermissions(this));
    }
}
