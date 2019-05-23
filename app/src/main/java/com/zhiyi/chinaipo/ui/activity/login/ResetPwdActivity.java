package com.zhiyi.chinaipo.ui.activity.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.auth.PasswordChangeConnector;
import com.zhiyi.chinaipo.presenters.auth.PasswordChangePresenter;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @note 修改密码
 * @anthor Song Chen
 */
public class ResetPwdActivity extends BaseActivity<PasswordChangePresenter> implements PasswordChangeConnector.View {
    //取消
    @BindView(R.id.tv_finishs)
    TextView mTvFinish;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.et_newpwd)
    EditText mEtNewPwd;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.img_off)
    ImageView mImgoldOff;
    @BindView(R.id.img_off1)
    ImageView mImgNewOff;
    String mPhone, mToken;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @Override
    protected int getLayout() {
        return R.layout.activity_amend_pwd;
    }

    @Override
    protected void initEventAndData() {
        mPhone = getIntent().getStringExtra(Constants.SP_USER_INFO_NAME);
        mToken = SPHelper.get(Constants.REGISTER_MESSAGES_TOKEN, "");
    }


    @OnClick(R.id.tv_finishs)
    void Back() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            startActivity(new Intent(this, FindPwdActivity.class));
            finish();
        }
    }

    @OnClick(R.id.img_close)
    void Close(){
            finish();
    }
    @OnClick(R.id.tv_login)
    void login() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            //判断点击事件
            if (mEtNewPwd.getText().length() > 5 && mEtPwd.getText().length() > 5) {
                if (mEtNewPwd.getText().toString().equals(mEtPwd.getText().toString())) {
                    mPresenter.passwordChenge("token " + mToken, mEtNewPwd.getText().toString(), mEtPwd.getText().toString());
                } else {
                    ToastUtil.showToast(ResetPwdActivity.this, "两次密码不一样");
                }
            }
        }
    }
    /*  @Override
      public void onClick(View view) {
          switch (view.getId()) {
              //返回
              case R.id.tv_finishs:
                  startActivity(new Intent(this, FindPwdActivity.class));
                  finish();
                  break;
              case R.id.img_off:
                  mEtPwd.setText("");
                  break;
              case R.id.img_off1:
                  mEtNewPwd.setText("");
                  break;
              case R.id.tv_login:
                  break;
              default:
                  break;
          }
      }
  */
   /* private void init(){

    }*/
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void changeSuccess() {
        finish();
        ToastUtil.showToast(this, "ok");

    }

    @Override
    public void changeErr() {
        ToastUtil.showToast(this, "失败");
    }


   /* //提交修改密码是的验证条件
    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //是否显示清除键
            if (mEtNewPwd.getText().length() > 0) {
                mImgNewOff.setVisibility(View.VISIBLE);
            } else {
                mImgNewOff.setVisibility(View.GONE);
            }
            if (mEtPwd.getText().length() > 0) {
                mImgoldOff.setVisibility(View.VISIBLE);
            } else {
                mImgoldOff.setVisibility(View.GONE);
            }

            //判断点击事件
            if (mEtNewPwd.getText().length() > 5 && mEtPwd.getText().length() > 5) {
                mTvLogin.setBackgroundColor(ContextCompat.getColor(ResetPwdActivity.this, R.color.blue));
                mTvLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mEtNewPwd.getText().toString().equals(mEtPwd.getText().toString())) {
                            finish();
                        } else {
                            ToastUtils.showShortToast(ResetPwdActivity.this, "两次密码不一样");
                        }
                    }
                });
            } else {
                mTvLogin.setBackgroundColor(ContextCompat.getColor(ResetPwdActivity.this, R.color.bluex));
            }
        }
    }*/
}
