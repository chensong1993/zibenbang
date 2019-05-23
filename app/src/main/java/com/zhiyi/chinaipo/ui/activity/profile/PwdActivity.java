package com.zhiyi.chinaipo.ui.activity.profile;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.AuthenticateActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import butterknife.BindView;

/**
 * @note 个人中心里面的密码修改
 * @anthor Song Chen
 */
public class PwdActivity extends AuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.tv_baocun)
    TextView mTvBack;
    @BindView(R.id.et_old_pwd)
    EditText mEtOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText mEtNewPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText mEtConfirmPwd;
    @BindView(R.id.imgcache)
    ImageView imgcache;
    @BindView(R.id.imgcache1)
    ImageView imgcache1;
    @BindView(R.id.imgcache2)
    ImageView imgcache2;

    @Override
    protected int getLayout() {
        return R.layout.activity_pwd;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {

        mRlBack.setOnClickListener(this);
        mTvBack.setOnClickListener(this);

        //清除
        imgcache.setOnClickListener(this);
        imgcache1.setOnClickListener(this);
        imgcache2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                //返回
                case R.id.rl_back:
                    finish();
                    break;
                //保存按钮
                case R.id.tv_baocun:
                    finish();
                case R.id.imgcache:
                    mEtOldPwd.setText("");
                    break;
                case R.id.imgcache1:
                    mEtNewPwd.setText("");
                    break;
                case R.id.imgcache2:
                    mEtConfirmPwd.setText("");
                    break;
                default:
                    break;

            }
        }
    }

    //返回键
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(this, ProfileSettingActivity.class);
//        startActivity(intent);
//        finish();
//    }
}
