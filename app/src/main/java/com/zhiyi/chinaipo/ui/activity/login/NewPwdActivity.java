package com.zhiyi.chinaipo.ui.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import butterknife.BindView;

/**
 * 设置新密码
 */
public class NewPwdActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_pwd)
    EditText mPwd;
    @BindView(R.id.et_querenpwd)
    EditText mQuerenPwd;
    private String pwd;
    //提交
    @BindView(R.id.tv_tijiao)
    TextView mTijiao;
    @BindView(R.id.tv_quxiao)
    TextView mquxiao;
    //退出
    @BindView(R.id.img_off)
    ImageView mOff;

    @Override
    protected int getLayout() {
        return R.layout.activity_new_pwd;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        //密码确认
        mTijiao.setOnClickListener(this);
        mquxiao.setOnClickListener(this);
        mOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_tijiao:
                    pwd = mPwd.toString();

                    break;
                case R.id.tv_quxiao:
                    startActivity(new Intent(NewPwdActivity.this, LoginActivity.class));
                    finish();
                    break;
                case R.id.img_off:
                    startActivity(new Intent(this, FindPwdActivity.class));
                    finish();
                default:
                    break;
            }
        }
    }
    @Override
    protected void initInject() {

    }
}
