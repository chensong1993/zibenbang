package com.zhiyi.chinaipo.ui.fragment;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.RootFragment;
import com.zhiyi.chinaipo.base.connectors.auth.ProfileConnector;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.db.StockCodeEntity;
import com.zhiyi.chinaipo.models.entity.H3;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.auth.NicknameEntity;
import com.zhiyi.chinaipo.models.entity.auth.PortraitEntity;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.models.greendao.NicknameEntityDao;
import com.zhiyi.chinaipo.models.greendao.PortraitEntityDao;
import com.zhiyi.chinaipo.models.greendao.StockCodeEntityDao;
import com.zhiyi.chinaipo.presenters.auth.ProfilePresenter;
import com.zhiyi.chinaipo.ui.activity.login.LoginActivity;
import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;
import com.zhiyi.chinaipo.ui.activity.profile.OptionalCompileActivity;
import com.zhiyi.chinaipo.ui.activity.profile.ProfileSettingActivity;
import com.zhiyi.chinaipo.ui.activity.profile.SettingActivity;
import com.zhiyi.chinaipo.ui.activity.search.SearchActivity;
import com.zhiyi.chinaipo.ui.adapter.followed.StockCodeAdapter;
import com.zhiyi.chinaipo.ui.adapter.followed.StockFollowedAdapter;
import com.zhiyi.chinaipo.ui.widget.circleView.CircleImageView;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.StatusBarUtil;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 自选频道
 */
public class ProfileFragment extends RootFragment<ProfilePresenter> implements ProfileConnector.View {
    @BindView(R.id.rl_login)
    RelativeLayout mRlLogin;
    @BindView(R.id.img_optional_search)
    ImageView mImgSearch;
    @BindView(R.id.img_optional_head_portrait)
    CircleImageView mAvatarImageView;
    @BindView(R.id.tv_optional_setting)
    TextView mTvSetting;
    @BindView(R.id.tv_optional_bianji)
    TextView mTvBianji;
    @BindView(R.id.tv_optional_guanyu)
    TextView mTvGuanYu;
    @BindView(R.id.tv_optional_shangwu_hezuo)
    TextView mTvShangWuHeZuo;
    @BindView(R.id.tv_optional_login)
    TextView mTvLogin;
    @BindView(R.id.tv_optional_geren_xinxi)
    TextView mTvXinxi;
    @BindView(R.id.view_main)
    RecyclerView mRvFollowed;
    //    @BindView(R.id.sv_main_content)
//    ObservableScrollView mObservableScrollView;
//    @BindView(R.id.ll_header)
//    RelativeLayout mHeaderContent;
    @BindView(R.id.rl_head)
    LinearLayout mProfileHeader;
    //    @BindView(R.id.tv_head_title)
//    TextView mTvHeadTitle;
//    @BindView(R.id.tv_head_setting)
//    TextView mTvHeadSetting;
//    @BindView(R.id.img_head_search)
//    ImageView mImgHeadSearch;
    @BindView(R.id.fillStatusBarView)
    TextView mStatusBarView;
    @BindView(R.id.img_message)
    ImageView mImgMessage;
    @BindView(R.id.app)
    AppBarLayout mAppBarLayout;
    int mHeight;
    private String token;
    private String name, user;
    String login;
    String icon;
    public final static int COLUMN_MANAGE_REQUEST = 1;
    StockCodeAdapter mStockCodeAdapter;
    StockFollowedAdapter mStockCodeAdapter1;
    List<StockPriceEntity> mStockPriceEntityList;
    List list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_optional;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        //  initData();
    }

    @OnClick(R.id.rl_login)
    void clickLogin() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            if (token == null || token.equals("")) {
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), COLUMN_MANAGE_REQUEST);
            } else {
                if (Build.VERSION.SDK_INT > 20) {
                    Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.REGISTER_MESSAGES_NICKNAME, name);
                    bundle.putString(Constants.REGISTER_MESSAGES_USER, user);
                    bundle.putString(Constants.REGISTER_MESSAGES_TOKEN, token);
                    bundle.putString(Constants.REGISTER_MESSAGES_ICON, icon);
                    intent.putExtra(Constants.PROFILE_PARAMETER, bundle);
                    startActivityForResult(intent, COLUMN_MANAGE_REQUEST, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                            Pair.create(mAvatarImageView, "portrait"))
                            .toBundle());
                } else {
                    Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.REGISTER_MESSAGES_NICKNAME, name);
                    bundle.putString(Constants.REGISTER_MESSAGES_USER, user);
                    bundle.putString(Constants.REGISTER_MESSAGES_TOKEN, token);
                    bundle.putString(Constants.REGISTER_MESSAGES_ICON, icon);
                    intent.putExtra(Constants.PROFILE_PARAMETER, bundle);
                    startActivityForResult(intent, COLUMN_MANAGE_REQUEST);
                }
            }
        }
    }

    //编辑
    @OnClick(R.id.tv_optional_bianji)
    void editFavorites() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            getContext().startActivity(new Intent(getActivity(), OptionalCompileActivity.class));
        }
    }

    @OnClick(R.id.img_optional_search)
    void goSearch() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
           // Cinematics.searchCinematics(getActivity(), mImgSearch);
             getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
        }
    }

    @OnClick(R.id.tv_optional_setting)
    void doSetting() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            getContext().startActivity(new Intent(getActivity(), SettingActivity.class));
        }
    }

  /*  @OnClick(R.id.tv_head_setting)
    void headerSetting() {
        getContext().startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @OnClick(R.id.img_head_search)
    void headerSearch() {
        getContext().startActivity(new Intent(getActivity(), SearchActivity.class));
    }
*/

    @OnClick(R.id.tv_optional_guanyu)
    void clickAboutUs() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            WebActivity.launch(new WebActivity.Builder()
                    .setContext(getActivity())
                    .setTitle("关于我们")
                    .setUrl(Constants.CHINAIPO_ABOUT)
            );
        }
    }

    @OnClick(R.id.tv_optional_shangwu_hezuo)
    void clickBusiness() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            WebActivity.launch(new WebActivity.Builder()
                    .setContext(getActivity())
                    .setTitle("商务合作")
                    .setUrl(Constants.CHINAIPO_BUSINESS)
            );
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    private void initData() {
        list = new ArrayList();
        //设置透明状态栏
        StatusBarUtil.enableTranslucentStatusbar(getActivity(), mStatusBarView, 1);
        token = SPHelper.get(Constants.REGISTER_MESSAGES_TOKEN, "");
        login = SPHelper.get(Constants.REGISTER_MESSAGES_LOGIN, "");
        user = SPHelper.get(Constants.REGISTER_MESSAGES_USER, "");
        icon = SPHelper.get(Constants.REGISTER_MESSAGES_ICON, "");
        if (token != null && !token.equals("")) {
            mTvXinxi.setText(login);
            Query query = App.getInstance().getPortraitEntityDao().queryBuilder().where(PortraitEntityDao.Properties.Token.eq(token)).build();
            List<PortraitEntity> mPortraitEntities = query.list();
            if (mPortraitEntities.size() > 0) {
                for (PortraitEntity entity : mPortraitEntities) {
                    icon = entity.getPortrait();
                    ImageLoader.load(getActivity(), entity.getPortrait(), R.mipmap.ic_icon_blue_ipo_03, mAvatarImageView);
                }
            } else {
                icon = SPHelper.get(Constants.REGISTER_MESSAGES_ICON, "");
                ImageLoader.load(getActivity(), icon, R.mipmap.ic_icon_blue_ipo_03, mAvatarImageView);
            }
            //修改昵称是否保存成功
            Query nicknameQuery = App.getInstance().getNicknameEntityDao().queryBuilder().where(NicknameEntityDao.Properties.Token.eq(token)).build();
            List<NicknameEntity> nNickname = nicknameQuery.list();
            if (nNickname.size() > 0) {
                for (NicknameEntity entity : nNickname) {
                    name = entity.getNickneme();
                }
            } else {
                name = SPHelper.get(Constants.REGISTER_MESSAGES_NICKNAME, "");
            }
            mTvLogin.setText(name);
        } else {
            ImageLoader.load(getActivity(), icon, R.mipmap.icon_op, mAvatarImageView);
        }
        mRvFollowed.setLayoutManager(new LinearLayoutManager(mContext));
        // mPresenter.weaherData("西安市");
        //监听是否到状态栏变化的高度
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == mAppBarLayout.getTotalScrollRange()) {
                    mStatusBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hui4));
                } else {
                    mStatusBarView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blue));
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mStockPriceEntityList = new ArrayList<>();
        LogUtil.i("tokennn3", token);
        if (token != null && !token.equals("")) {
            Query query = App.getInstance().getStockCodeDao().queryBuilder().where(StockCodeEntityDao.Properties.Token.eq(token)).build();
            List<StockCodeEntity> mStockCodeEntityList = query.list();
            Collections.reverse(mStockCodeEntityList
            );
            for (int i = 0; i < mStockCodeEntityList.size(); i++) {
                mPresenter.userCode(mStockCodeEntityList.get(i).getCode());
            }
           /* for (StockCodeEntity entity : mStockCodeEntityList) {
                mPresenter.userCode(entity.getCode());
            }*/
            if (mStockCodeEntityList.size() <= 0) {
                mStockCodeAdapter1 = new StockFollowedAdapter(mContext, mStockPriceEntityList);
                mRvFollowed.setAdapter(mStockCodeAdapter1);
            }
            //修改头像是否保存成功
            Query query1 = App.getInstance().getPortraitEntityDao().queryBuilder().where(PortraitEntityDao.Properties.Token.eq(token)).build();
            List<PortraitEntity> mPortraitEntities = query1.list();
            if (mPortraitEntities.size() > 0) {
                for (PortraitEntity entity : mPortraitEntities) {
                    icon = entity.getPortrait();
                    ImageLoader.load(getActivity(), icon, R.mipmap.ic_icon_blue_ipo_03, mAvatarImageView);
                    LogUtil.i("abl", "6");
                }
            } else {
                icon = SPHelper.get(Constants.REGISTER_MESSAGES_ICON, "");
                ImageLoader.load(getActivity(), icon, R.mipmap.ic_icon_blue_ipo_03, mAvatarImageView);
                LogUtil.i("abl", "7");
            }

            //修改昵称是否保存成功
            Query nicknameQuery = App.getInstance().getNicknameEntityDao().queryBuilder().where(NicknameEntityDao.Properties.Token.eq(token)).build();
            List<NicknameEntity> nNickname = nicknameQuery.list();
            if (nNickname.size() > 0) {
                for (NicknameEntity entity : nNickname) {
                    name = entity.getNickneme();
                    LogUtil.i("abl", "8");
                }
            } else {
                name = SPHelper.get(Constants.REGISTER_MESSAGES_NICKNAME, "");
                LogUtil.i("abl", "9");
            }
            mTvLogin.setText(name);
        } else {
            mStockCodeAdapter1 = new StockFollowedAdapter(mContext, mStockPriceEntityList);
            mRvFollowed.setAdapter(mStockCodeAdapter1);
        }

        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo!=null) {
            if(upgradeInfo.versionCode!=0){
                LogUtil.i("upgrade",upgradeInfo.versionName);
                mImgMessage.setVisibility(View.VISIBLE);
            }else {
                LogUtil.i("upgrade","err");
                mImgMessage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void UserFollowed(List<StockPriceEntity> userInfo) {
        mStockPriceEntityList.addAll(userInfo);
        mStockCodeAdapter1 = new StockFollowedAdapter(mContext, mStockPriceEntityList);
        mRvFollowed.setAdapter(mStockCodeAdapter1);
        LogUtil.i("StockPriceEntity", mStockPriceEntityList.size() + "");
    }

    @Override
    public void weaherData(H3 weatherEntity) {
        LogUtil.i("WeatherEntity", weatherEntity.getWeatherInfo().getH3().get(0).getWeather());
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
        LogUtil.i("StockPriceEntity", msg);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateUserInfo(UserEntity userInfo) {
        user = userInfo.getName();
        SPHelper.save(Constants.REGISTER_MESSAGES_USER, userInfo.getName());
        RxBus.getDefault().post(new EventLogin(userInfo.getFullname(), userInfo.getAvatar(), "查看个人信息"));
    }

    @Override
    public void updateThirdPartyUser(EventLogin user) {
        SPHelper.save(Constants.REGISTER_MESSAGES_NICKNAME, user.getName());
        SPHelper.save(Constants.REGISTER_MESSAGES_LOGIN, user.getLogin());
        SPHelper.save(Constants.REGISTER_MESSAGES_ICON, user.getIcon());
        if (user.getToken() != null) {
            token = user.getToken();
        }
        mTvLogin.setText(user.getName());
        mTvXinxi.setText(user.getLogin());
        ImageLoader.load(getActivity(), user.getIcon(), R.mipmap.ic_icon_blue_ipo_03, mAvatarImageView);
        LogUtil.i("user.getIcon", user.getIcon());
    }

    @Override
    public void clearUserInfo() {

    }

    @Override
    public void userToken(String userToken) {
        if (userToken != null) {
            token = userToken;
            SPHelper.save(Constants.REGISTER_MESSAGES_TOKEN, token);
        }
        //    LogUtil.i("updateThirdPartyUser", token + "  " + userToken);
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case COLUMN_MANAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getBundleExtra(Constants.PROFILE_PARAMETER);
                    name = bundle.getString(Constants.REGISTER_MESSAGES_NICKNAME);
                    token = bundle.getString(Constants.REGISTER_MESSAGES_TOKEN);
                    user = bundle.getString(Constants.REGISTER_MESSAGES_USER);
                    icon = bundle.getString(Constants.REGISTER_MESSAGES_ICON);
                    login = bundle.getString(Constants.REGISTER_MESSAGES_LOGIN);
                    mTvLogin.setText(name);
                    mTvXinxi.setText(login);
                    // Glide.with(getActivity()).load(icon).error(R.mipmap.icon_op).into(mAvatarImageView);
                    ImageLoader.load(getActivity(), icon, R.mipmap.icon_op, mAvatarImageView);
                    SPHelper.save(Constants.REGISTER_MESSAGES_ICON, icon);
                    SPHelper.save(Constants.REGISTER_MESSAGES_NICKNAME, name);
                    SPHelper.save(Constants.REGISTER_MESSAGES_TOKEN, token);
                    SPHelper.save(Constants.REGISTER_MESSAGES_USER, user);
                    SPHelper.save(Constants.REGISTER_MESSAGES_LOGIN, login);
                }
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
