package com.zhiyi.chinaipo.ui.activity.profile;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.AuthenticateActivity;
import com.zhiyi.chinaipo.ui.activity.misc.WebActivity;
import com.zhiyi.chinaipo.util.FileUtil;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.VersionCodeUtils;

import butterknife.BindView;

/**
 * 设置
 */
public class SettingActivity extends AuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_versions)
    TextView mTvVersions;
    @BindView(R.id.rl_clear)
    RelativeLayout mRlClear;
    @BindView(R.id.tv_clear)
    TextView mTvClear;
    @BindView(R.id.rl_about)
    RelativeLayout mRlAbout;
    @BindView(R.id.rl_business)
    RelativeLayout mRlBusiness;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.rl_version)
    RelativeLayout mRlVersion;
    @BindView(R.id.img_message)
    ImageView mImgMessage;

    @Override
    protected int getLayout() {
        return R.layout.activity_shezhi;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        mTitle.setText("设置");
        mRlBack.setOnClickListener(this);
        //清理缓存
        mRlClear.setOnClickListener(this);
        mRlAbout.setOnClickListener(this);
        mRlBusiness.setOnClickListener(this);
        mRlVersion.setOnClickListener(this);
        mTvClear.setText(FileUtil.getFormatSize(FileUtil.FileSize(Constants.PATH_SDCARD) + FileUtil.FileSize(Constants.PATH_CACHE)));
        mTvVersions.setText(VersionCodeUtils.getVerName(this));

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                mTvClear.setText(FileUtil.getFormatSize(FileUtil.FileSize(Constants.PATH_SDCARD) + FileUtil.FileSize(Constants.PATH_CACHE)));
            }
            return true;
        }
    });

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                //返回
                case R.id.rl_back:
                    finish();
                    break;
                //清理缓存
                case R.id.rl_clear:
                    FileUtil.deleteDir(Constants.PATH_SDCARD);
                    FileUtil.deleteDir(Constants.PATH_CACHE);
                    handler.sendEmptyMessage(1);
                    ToastUtil.showToast(this, "清理成功");
                    break;
                //关于
                case R.id.rl_about:
                    WebActivity.launch(new WebActivity.Builder()
                            .setContext(this)
                            .setTitle(getString(R.string.x_guanyu))
                            .setUrl(Constants.CHINAIPO_ABOUT));
                    break;
                //商务合作
                case R.id.rl_business:
                    WebActivity.launch(new WebActivity.Builder()
                            .setContext(this)
                            .setTitle(getString(R.string.x_shangwu))
                            .setUrl(Constants.CHINAIPO_BUSINESS));
                    break;
                case R.id.rl_version:
                    Beta.checkUpgrade();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo !=null) {
            if(upgradeInfo.versionCode!=0){
                LogUtil.i("upgrade",upgradeInfo.versionName);
                mImgMessage.setVisibility(View.VISIBLE);
            } else {
                LogUtil.i("upgrade","ok");
                mImgMessage.setVisibility(View.GONE);
            }
        }
    }
}
