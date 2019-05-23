package com.zhiyi.chinaipo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseFragment;
import com.zhiyi.chinaipo.base.connectors.news.CategoryConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;
import com.zhiyi.chinaipo.models.event.BannerIndexEvent;
import com.zhiyi.chinaipo.models.event.NewsIndex;
import com.zhiyi.chinaipo.presenters.news.CategoryPresenter;
import com.zhiyi.chinaipo.ui.activity.search.SearchActivity;
import com.zhiyi.chinaipo.ui.category.ColumnActivity;
import com.zhiyi.chinaipo.ui.category.NewsFragmentStatePagerAdapter;
import com.zhiyi.chinaipo.ui.fragment.news.FirstNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.news.LatterNewsFragment;
import com.zhiyi.chinaipo.ui.fragment.news.SpecialColumnFragment;
import com.zhiyi.chinaipo.ui.fragment.news.SpecialsFragment;
import com.zhiyi.chinaipo.ui.fragment.news.SpecialsWebViewFragment;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.StatusBarUtil;
import com.zhiyi.chinaipo.util.SystemUtil;
import com.zhiyi.chinaipo.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment<CategoryPresenter> implements CategoryConnector.View {

    public final static int COLUMN_MANAGE_REQUEST = 1;
    public final static int COLUMN_MANAGE_RESULT = 2;
    // TabLayout mTabLayout;
    @BindView(R.id.view_main)
    ViewPager mViewPager;
    @BindView(R.id.xTablayout)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.rl_img)
    RelativeLayout mRlImg;
    @BindView(R.id.rl_seek)
    RelativeLayout mRlSeek;
    @BindView(R.id.img_seek)
    ImageView mImgSearch;
    @BindView(R.id.tv_request)
    TextView mTvRequest;
    @BindView(R.id.rv_banner)
    RelativeLayout mRvBanner;
    @BindView(R.id.fillStatusBarView)
    TextView mStatusBarView;
    NewsFragmentStatePagerAdapter mViewPagerAdapetr;
    List<CategoryEntity> userColumnList;
    List<CategoryEntity> otherList;
    List<CategoryEntity> AllColumnList;
    List<CategoryEntity> AllList;
    List<CategoryEntity> userList;
    private ArrayList<Fragment> fragments;
    //新闻首页
    LatterNewsFragment mLatterFragment;
    //其他页面
    FirstNewsFragment mFirstFragment;
    SpecialColumnFragment mColumnFragment;
    SpecialsFragment mSpecialsFragment;
    SpecialsWebViewFragment mSpecialsWebViewFragment;
    //private NetBroadcastReceiver mNetBroadcastReceiver;
    private int IndexNews = 0;
    ArrayList<Integer> picturePixel;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //之前版本的栏目缓存统一清空
      /*  String only = SPHelper.get("only", "");
        if (only.equals("")) {
            SPHelper.save("only", "123456");
            SPHelper.remove(ColumnActivity.USER_LIST);
        }*/
      /*  fragments = new ArrayList<>();
        userColumnList = new ArrayList<>();
        otherList = new ArrayList<>();
        mPresenter.getCategories(1);
        mPresenter.getCategoriesTwo(2);
        initColumnData();
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView, 0);
        stateLoading();
        checkPermissions();*/
    }

    @Override
    protected void initEventAndData() {
        LogUtil.i("132131", "13212132");
        fragments = new ArrayList<>();
        userColumnList = new ArrayList<>();
        otherList = new ArrayList<>();
        mPresenter.getCategories(1);
        mPresenter.getCategoriesTwo(2);
        initColumnData();
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView, 0);
        stateLoading();
        checkPermissions();

    }

    @Override
    public void bannerImg(String img) {
//        if (img != null) {
//            Glide.with(getActivity()).load(img).asBitmap().into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    mSlidingTabLayout.setBackgroundColor(PicturePixel(resource));
//                    mRvBanner.setBackgroundColor(PicturePixel(resource));
//                    mStatusBarView.setBackgroundColor(PicturePixel(resource));
//                }
//            });
//        } else {
//            mSlidingTabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
//            mRvBanner.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
//            mStatusBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
//        }

    }

    /**
     * 获得图片的像素方法
     *
     * @param
     */

//
//    public int PicturePixel(Bitmap bitmap) {
//
//        //  int width = bitmap.getWidth();
//        //  int height = bitmap.getHeight();
//
//        // 保存所有的像素的数组，图片宽×高
//        int[] pixels = new int[200 * 200];
//
//        bitmap.getPixels(pixels, 0, 200, 0, 0, 200, 200);
//
//        ArrayList<Integer> rgb = new ArrayList<>();
//        for (int i = 0; i < pixels.length; i++) {
//            int clr = pixels[i];
//            int red = (clr & 0x00ff0000) >> 16; // 取高两位
//            int green = (clr & 0x0000ff00) >> 8; // 取中两位
//            int blue = clr & 0x000000ff; // 取低两位
////            Log.d("tag", "r=" + red + ",g=" + green + ",b=" + blue);
//            int color = Color.rgb(red, green, blue);
//            rgb.add(color);
//        }
//        //计数相同颜色数量并保存
//        HashMap<Integer, Integer> color2 = new HashMap<>();
//
//        for (Integer color : rgb) {
//            if (color2.containsKey(color)) {
//                Integer integer = color2.get(color);
//                integer++;
//                color2.remove(color);
//                color2.put(color, integer);
//
//            } else {
//                color2.put(color, 1);
//            }
//        }
//        //挑选数量最多的颜色
//        Iterator iter = color2.entrySet().iterator();
//        int count = 0;
//        int color = 0;
//        while (iter.hasNext())
//
//        {
//            Map.Entry entry = (Map.Entry) iter.next();
//            int value = (int) entry.getValue();
//            if (count < value) {
//                count = value;
//                color = (int) entry.getKey();
//            }
//
//        }
//        if (color == 0) {
//            return R.color.white;
//        }
//        return color;
//
//    }


//    /**
//     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
//     */
//    private void changeTopBgColor(String img) {
//        // 用来提取颜色的Bitmap
//        // Bitmap bitmap = BitmapFactory.decodeFile(img);
//        Glide.with(getActivity()).load(img).asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                // Palette的部分
//                Palette.Builder builder = Palette.from(resource);
//                builder.generate(new Palette.PaletteAsyncListener() {
//                    @Override
//                    public void onGenerated(Palette palette) {
//                        if (palette != null) {
//                            List<Palette.Swatch> swatches = palette.getSwatches();
//                            Log.e("onGenerated", swatches.toString());
//                            //获取到充满活力的这种色调
//                            Palette.Swatch vibrant = palette.getVibrantSwatch();
//                            //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
//                            mSlidingTabLayout.setBackgroundColor(vibrant.getRgb());
//                            mRvBanner.setBackgroundColor(vibrant.getRgb());
//                            if (android.os.Build.VERSION.SDK_INT >= 21) {
//                                Window window = getActivity().getWindow();
//                                window.setStatusBarColor(colorBurn(vibrant.getRgb()));
//                                window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
//                            }
//                        }
//
//                    }
//                });
//            }
//        });
//
//    }
//
//    /**
//     * 颜色加深处理
//     *
//     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
//     *                  Android中我们一般使用它的16进制，
//     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
//     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
//     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
//     * @return
//     */
//    private int colorBurn(int RGBValues) {
//        int alpha = RGBValues >> 24;
//        int red = RGBValues >> 16 & 0xFF;
//        int green = RGBValues >> 8 & 0xFF;
//        int blue = RGBValues & 0xFF;
//        red = (int) Math.floor(red * (1 - 0.1));
//        green = (int) Math.floor(green * (1 - 0.1));
//        blue = (int) Math.floor(blue * (1 - 0.1));
//        return Color.rgb(red, green, blue);
//    }

    @OnClick(R.id.rl_seek)
    void clickedSearch() {
//        RxView.clicks(mImgSearch).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Unit>() {
//            @Override
//            public void accept(Unit unit) throws Exception {

//            }
//        });
         if (!RepeatCllickUtil.isFastDoubleClick()) {
             startActivity(new Intent(getActivity(), SearchActivity.class));
        //  SnackbarUtil.showShort(getActivity().getWindow().getDecorView(), "搜索出现了问题");
            // Cinematics.searchCinematics(getActivity(), mImgSearch);

          }
    }

    @OnClick(R.id.rl_img)
    void clickedChannel() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), ColumnActivity.class);
            startActivityForResult(intent, COLUMN_MANAGE_REQUEST);
            getActivity().overridePendingTransition(R.anim.column_show, 0);
        }

    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        //将我的频道和频道推荐合并
        if (SPHelper.getDataList(ColumnActivity.USER_LIST) != null) {
            AllColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
            if (SPHelper.getDataList(ColumnActivity.OTHER_LIST) != null) {
                AllColumnList.addAll(SPHelper.getDataList(ColumnActivity.OTHER_LIST));
            }
            if (SPHelper.getDataList(ColumnActivity.USER_LIST) != null) {
                AllColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
                if (SPHelper.getDataList(ColumnActivity.OTHER_LIST) != null) {
                    AllColumnList.addAll(SPHelper.getDataList(ColumnActivity.OTHER_LIST));
                }
                //遍历对比网络请求的数据个本地数据
                userColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
                otherList = SPHelper.getDataList(ColumnActivity.OTHER_LIST);
                //检测网络获取的频道是否有删减
                //合并网络请求的我的频道和频道推荐
                if (SystemUtil.isNetworkConnected()) {
                    AllList = SPHelper.getDataList(ColumnActivity.SP_USER_LIST);
                    AllList.addAll(SPHelper.getDataList(ColumnActivity.SP_OTHER_LIST));
                    for (CategoryEntity entity : AllColumnList) {
                        //如果本地有多余的频道进行排查到底实在我的频道还是在推荐中然后进行删除
                        if (!AllList.contains(entity)) {
                            for (int i = 0; i < userColumnList.size(); i++) {
                                if (userColumnList.get(i).equals(entity)) {
                                    userColumnList.remove(entity);
                                    SPHelper.setDataList(ColumnActivity.USER_LIST, userColumnList);
                                    LogUtil.i("sadasdasdada", entity.getName());
                                }
                            }
                            for (int i = 0; i < otherList.size(); i++) {
                                if (otherList.equals(entity)) {
                                    otherList.remove(entity);
                                    SPHelper.setDataList(ColumnActivity.OTHER_LIST, otherList);
                                    LogUtil.i("sadasdasdada", entity.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateCategories(List<CategoryEntity> lists) {
        SPHelper.setDataList(ColumnActivity.SP_USER_LIST, lists);
        if (SPHelper.getDataList(ColumnActivity.USER_LIST).size() > 0) {
            //  userColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
            LogUtil.i("111111111111111", userColumnList.toString());
            //检测网络获取的频道是否有增加
            for (CategoryEntity list : lists) {
                LogUtil.i("1", "2");
                //判断网络的频道和本地保存的完全相等就直接引用sp里面的
                if (!AllColumnList.contains(list)) {
                    userColumnList.add(list);
                    LogUtil.i("没有的值1", list.toString() + "");
                }
            }
            initTabColumn();
            initFragment();

        } else {
            userColumnList = lists;
            initTabColumn();
            initFragment();
        }
        if (userColumnList.size() > 0) {
            mTvRequest.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
        } else {
            mViewPager.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_request)
    void request() {
        mPresenter.getCategories(1);
        mPresenter.getCategoriesTwo(2);
    }

    @Override
    public void updateCategoriesTwo(List<CategoryEntity> list) {
        SPHelper.setDataList(ColumnActivity.SP_OTHER_LIST, list);
        /*if (SPHelper.getDataList(ColumnActivity.OTHER_LIST).size() <= 0) {
            SPHelper.setDataList(ColumnActivity.OTHER_LIST, list);
        }*/

    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        fragments.clear();//清空
        if (userColumnList.size() > 0) {
            int count = userColumnList.size();
            for (int i = 0; i < count; i++) {
                Bundle data = new Bundle();
                data.putInt(Constants.NEWS_CATEGORY_ID, userColumnList.get(i).getId());
                data.putString(Constants.NEWS_CATEGORY_NAME, userColumnList.get(i).getName());
                int j = userColumnList.get(i).getId();
                if (j == 0) {
                    mFirstFragment = new FirstNewsFragment();
                    mFirstFragment.setArguments(data);
                    fragments.add(mFirstFragment);
                } else if (j == 15) {
                    mSpecialsFragment = new SpecialsFragment();
                    mSpecialsFragment.setArguments(data);
                    fragments.add(mSpecialsFragment);
                } else if (j == 16) {
                    mColumnFragment = new SpecialColumnFragment();
                    mColumnFragment.setArguments(data);
                    fragments.add(mColumnFragment);
                } else {
                    mLatterFragment = new LatterNewsFragment();
                    mLatterFragment.setArguments(data);
                    fragments.add(mLatterFragment);
                }
            }
            //  LogUtil.i("userColumnList", "" + j);

        }

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        if (mViewPagerAdapetr == null) {
            LogUtil.i("userColumnList", "123");
            mViewPagerAdapetr = new NewsFragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), fragments);
            mViewPager.setOffscreenPageLimit(userColumnList.size());
            mViewPager.setAdapter(mViewPagerAdapetr);
            //新闻滚动选项卡一页内显示的个数
            //mXTabLayout.setxTabDisplayNum(7);
            // mXTabLayout.setupWithViewPager(mViewPager);
            mSlidingTabLayout.setViewPager(mViewPager);

        } else {
            LogUtil.i("userColumnList", "456");
            mViewPagerAdapetr.notifyDataSetChanged();
            // mXTabLayout.setxTabDisplayNum(7);
            // mXTabLayout.setupWithViewPager(mViewPager);
            mSlidingTabLayout.setViewPager(mViewPager);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.i("123456", "" + position);
                RxBus.getDefault().post(new BannerIndexEvent(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void showErrorMsg(String msg) {
        userColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
        initTabColumn();
        initFragment();
        mViewPager.setVisibility(View.GONE);
        mTvRequest.setVisibility(View.VISIBLE);
        ToastUtil.showToast(getActivity(), "加载出错");
        LogUtil.i("aaaaa", userColumnList.toString());
    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case COLUMN_MANAGE_REQUEST:
                if (resultCode == COLUMN_MANAGE_RESULT) {
                    //initColumnData();
                    mPresenter.getCategories(1);
                } else if (resultCode == Activity.RESULT_OK) {
                    userColumnList = SPHelper.getDataList(ColumnActivity.USER_LIST);
                    for (int j = 0; j < 2; j++) {
                        initTabColumn();
                        initFragment();
                        // initColumnData();
                        //获取传回来的columnId并设置pager
                        int columnId = data.getIntExtra(ColumnActivity.COLUMN_ID, 0);
                        LogUtil.i("1111111111111112", userColumnList.toString());
                        for (int i = 0; i < userColumnList.size(); i++) {
                            if (columnId == userColumnList.get(i).getId()) {
                                //  mXTabLayout.getTabAt(i).select();
                                mSlidingTabLayout.setCurrentTab(i);
                                Log.i("onActivityResult", "onActivityResult: " + i);
                                break;
                            }
                        }
                    }

                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkPermissions() {
        if (mPresenter != null) {
            mPresenter.checkPermissions(new RxPermissions(getActivity()));
        }
    }


    //轮播图跳转到指定过的tabTitle
    @Override
    public void updateTableTitle(int titleId) {
        //获取传回来的columnId并设置pager
        for (int i = 0; i < userColumnList.size(); i++) {
            if (titleId == userColumnList.get(i).getId()) {
                //mXTabLayout.getTabAt(i).select();
                mSlidingTabLayout.setCurrentTab(i);
                break;

            }
        }
    }

    @Override
    public void indexNews(int index) {
        if (index == 0) {
            RxBus.getDefault().post(new NewsIndex(IndexNews));
        }
        Log.i("indexNews", "" + index);
    }


//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        if (netMobile >= 0) {
//            if (userColumnList == null) {
//                userColumnList = new ArrayList<>();
//                mPresenter.getCategories(1);
//            }
//        } else {
//            ToastUtil.showToast(getActivity(), "暂无网络");
//        }
//
//    }

    //在onResume()方法注册
    @Override
    public void onResume() {
//        if (mNetBroadcastReceiver == null) {
//            mNetBroadcastReceiver = new NetBroadcastReceiver();
//        }
//        IntentFilter filter1 = new IntentFilter();
//        filter1.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        mContext.registerReceiver(mNetBroadcastReceiver, filter1);
        super.onResume();
    }


    //onPause()方法注销
    @Override
    public void onPause() {
       // mContext.unregisterReceiver(mNetBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
