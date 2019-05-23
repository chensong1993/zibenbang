package com.zhiyi.chinaipo.ui.activity.stocks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import com.androidkun.xtablayout.XTabLayout;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.models.entity.TabEntity;
import com.zhiyi.chinaipo.ui.fragment.kchart.KLineFragment;
import com.zhiyi.chinaipo.ui.fragment.kchart.TimelineFragment;
import com.zhiyi.chinaipo.util.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class LandscapeKLineActivity extends BaseActivity {
    @BindView(R.id.img_off)
    ImageView mImgOff;
    @BindView(R.id.tab_k_line)
    CommonTabLayout mTabLayout;
    private Fragment mKLineFragments[];
    String[] ITEMK = {"分时", "日线", "周线", "月线"};
    private int KIndex;
    String mStockCode, mPreClose;
    ArrayList<CustomTabEntity> mTabKLineList;

    @Override
    protected int getLayout() {
        return R.layout.activity_landscape_kline;
    }

    @Override
    protected void initEventAndData() {
        //关闭侧滑退出
        setSwipeBackEnable(false);
        Bundle bundle = getIntent().getBundleExtra(Constants.PARAMETER_LANDSCAPE_STOCK);
        mStockCode = bundle.getString(Constants.PARAMETER_STOCK_CODE);
        KIndex = bundle.getInt(Constants.PARAMETER_STOCK_INDEX);
        LogUtil.i("LINDEX", "" + KIndex);
        initData();
        if(KIndex>-1){
            hideAllKLineFragment();
            switchChart(KIndex);
            mTabLayout.setCurrentTab(KIndex);
        }
    }

    @OnClick(R.id.img_off)
    void close() {
        finish();
    }

    private void initData() {
        //K线图
        mKLineFragments = new Fragment[ITEMK.length];
        mTabKLineList=new ArrayList<>();
        for (int i = 0; i < ITEMK.length; i++) {
//            XTabLayout.Tab tab = mTabLayout.newTab();
//            tab.setText(ITEMK[i]);
//            mTabLayout.addTab(tab, i, i == KIndex);
            createKLineFragments(i);
            mTabKLineList.add(new TabEntity(ITEMK[i]));
        }
        mTabLayout.setTabData(mTabKLineList);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                hideAllKLineFragment();
                switchChart(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    //K线图
    XTabLayout.OnTabSelectedListener onKLineTabSelectedListener = new XTabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(XTabLayout.Tab tab) {

            // KIndex = tab.getPosition();
        }

        @Override
        public void onTabUnselected(XTabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(XTabLayout.Tab tab) {

        }
    };

    private void hideAllKLineFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mKLineFragments) {
            if (f != null) {
                if (!f.isHidden()) {
                    ft.hide(f);
                }
            }
        }
        ft.commit();
    }

    private void showKLineFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    private void addKLineFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fl_table, fragment);
            ft.commit();
        }
    }

    private void switchChart(int position) {
        addKLineFragment(mKLineFragments[position]);
        showKLineFragment(mKLineFragments[position]);
    }

    public void createKLineFragments(int position) {
        Fragment fragTo = mKLineFragments[position];
        if (fragTo == null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PARAMETER_STOCK_CODE, mStockCode);
            switch (position) {
                case 0:
                    fragTo = new TimelineFragment();
                    bundle.putString(Constants.TIMELINE_PARAMETER_PRE_CLOSE_PRICE, "0.01");
                    bundle.putString(Constants.PARAMETER_LANDSCAPE, "landscape");
                    fragTo.setArguments(bundle);
                    break;
                case 1:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "day");
                    bundle.putString(Constants.PARAMETER_LANDSCAPE, "landscape");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                case 2:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "week");
                    bundle.putString(Constants.PARAMETER_LANDSCAPE, "landscape");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                case 3:
                    bundle.putString(Constants.FRAGMENT_KLINE_TYPE, "month");
                    bundle.putString(Constants.PARAMETER_LANDSCAPE, "landscape");
                    fragTo = new KLineFragment();
                    fragTo.setArguments(bundle);
                    break;
                default:
                    break;
            }
            mKLineFragments[position] = fragTo;
        }
    }


    @Override
    protected void initInject() {

    }
}
