package com.zhiyi.chinaipo.ui.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.auth.RegisterConnector;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;
import com.zhiyi.chinaipo.presenters.auth.RegisterPresenter;
import com.zhiyi.chinaipo.ui.fragment.ProfileFragment;
import com.zhiyi.chinaipo.util.CountDownTimerUtils;
import com.zhiyi.chinaipo.util.RegUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;


/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterConnector.View, View.OnClickListener {

    @BindView(R.id.tv_denglu)
    TextView mDenglu;
    @BindView(R.id.tv_xieyishu)
    TextView mXieyi;
    @BindView(R.id.img_off)
    ImageView mOff;
    @BindView(R.id.img_tuxing)
    ImageView mImgYanzhengma;
    @BindView(R.id.img_phone_off)
    ImageView mImgPhone;
    @BindView(R.id.img_yanzheng_clear)
    ImageView mImgYanzhengOff;
    @BindView(R.id.img_duanxin_clear)
    ImageView mImgDuanxinOff;
    @BindView(R.id.img_name_off)
    ImageView mImgNickName;
    @BindView(R.id.img_pwd_off)
    ImageView mImgPwd;
    @BindView(R.id.img_newpwd_off)
    ImageView mImgNewPwd;
    @BindView(R.id.but_note)
    Button mButNote;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_duanxin)
    EditText mEtDuanxin;
    @BindView(R.id.et_yanzheng)
    EditText mEtYanzheng;
    @BindView(R.id.et_name)
    EditText mEtname;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_affirm_pwd)
    EditText mEtAffirmPwd;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    //获取验证码
    private String realCode, result, smsCode, smsOK;
    CountDownTimerUtils countDownTimerUtils;


    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    @Override
    public void inProcessing(String statusMsg) {
        // ToastUtil.showToast(this, statusMsg);
    }

    @Override
    public void registerSuccess(String tokenEntity) {
        Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public void registerErr() {
        ToastUtil.showToast(this,"手机号码已经注册或者密码不一致");
    }

    @Override
    public void getCaptcha(CaptchaEntity captchaEntity) {
       // Glide.with(this).load(captchaEntity.getImage2x_url()).into(mImgYanzhengma);
        ImageLoader.load(this,captchaEntity.getImage2x_url(),mImgYanzhengma);
        realCode = captchaEntity.getHashkey();
      //  LogUtil.i("getCaptcha", captchaEntity.getImage2x_url() + captchaEntity.getHashkey());
    }

    @Override
    public void captchaAuth(String status) {
        result = status;
        if (status != null) {
            if (status.equals("status")) {
                mEtYanzheng.setText("");
                ToastUtil.showToast(this, "验证码错误");
            }
        }
      //  LogUtil.i("captchaAuth", mEtYanzheng.getText().toString() + realCode + result);
    }

    @Override
    public void smsCaptcha(String hashkey) {
        smsCode = hashkey;
      //  LogUtil.i("Captcha", hashkey);
    }

    @Override
    public void smsCaptchaAuth(String s) {
        smsOK = s;
    }

    @Override
    public void getCaptchaErr() {

    }

    @Override
    public void smsCaptchaErr(String s) {
    //    LogUtil.i("Captcha", s);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    private void init() {
        //已有账号
        mDenglu.setOnClickListener(this);

        //清除手机号
        mImgPhone.setOnClickListener(this);
        //清除昵称
        mImgNickName.setOnClickListener(this);
        //清除密码
        mImgPwd.setOnClickListener(this);
        //清除新密码
        mImgNewPwd.setOnClickListener(this);

        mXieyi.setOnClickListener(this);
        //返回
        mOff.setOnClickListener(this);
        //验证码图形
        mImgYanzhengma.setOnClickListener(this);
        mImgDuanxinOff.setOnClickListener(this);
        mImgYanzhengOff.setOnClickListener(this);
        mEtDuanxin.setOnClickListener(this);
        //验证码输入框
        mEtYanzheng.addTextChangedListener(new EditChangedListener());
        //短信验证
        mButNote.setOnClickListener(this);
        //短信输入框
        //手机号码
        mEtPhone.addTextChangedListener(new EditChangedListener());
        //昵称
        mEtname.addTextChangedListener(new EditChangedListener());
        //创建密码
        mEtPwd.addTextChangedListener(new EditChangedListener());
        //确认密码
        mEtAffirmPwd.addTextChangedListener(new EditChangedListener());
        //注册按钮
        mTvLogin.setOnClickListener(this);
        mPresenter.getCaptcha();

    }

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                //已有账号
                case R.id.tv_denglu:
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                //协议书
                case R.id.tv_xieyishu:
//                startActivity(new Intent(RegisterActivity.this, UserAgreementActivity.class));
                    break;
                case R.id.img_off:
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                //验证码
                case R.id.img_tuxing:
                    mPresenter.getCaptcha();
                    break;
                //短信验证
                case R.id.but_note:
                    if (result != null) {
                        if ("validated".equals(result)) {
                            mPresenter.smsCaptcha(mEtPhone.getText().toString());
                            countDownTimerUtils = new CountDownTimerUtils(mButNote, 60000, 1000);
                            countDownTimerUtils.start();
                        } else {
                            ToastUtil.showToast(this, "验证码错误");
                        }
                    }
                    break;
                //清除手机号
                case R.id.img_phone_off:
                    mEtPhone.setText("");
                    break;
                case R.id.img_yanzheng_clear:
                    mEtYanzheng.setText("");
                    break;
                case R.id.img_duanxin_clear:
                    mEtDuanxin.setText("");
                    break;
                //清除昵称
                case R.id.img_name_off:
                    mEtname.setText("");
                    break;
                case R.id.img_pwd_off:
                    mEtPwd.setText("");
                    break;
                case R.id.img_newpwd_off:
                    mEtAffirmPwd.setText("");
                    break;
                //登录
                case R.id.tv_login:
                    if (RegUtil.isMobile(mEtPhone.getText().toString()) && RegUtil.isPassword(mEtPwd.getText().toString()) && "validated".equals(result) && "validated".equals(smsOK)) {
                        mPresenter.goRegister(mEtPhone.getText().toString(), mEtPwd.getText().toString(), mEtAffirmPwd.getText().toString());
                    } else {
                        mPresenter.captchaAuth(mEtYanzheng.getText().toString(), realCode);
                        ToastUtil.showToast(this, "注册失败");
                    }
                    break;
                default:
                    break;


            }
        }
    }
    //按返回键返回
    @Override
    public void onBackPressedSupport() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }


    //在此行判断输入的手机号码格式是否正确
    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        //注册的条件
        @Override
        public void afterTextChanged(Editable editable) {
            //手机号码长度判断
            int length = mEtPhone.getText().toString().length();
            int pwd = mEtPwd.getText().toString().length();
            int newPwd = mEtAffirmPwd.getText().length();
            //判断清除键何时显示
            if (length > 0) {
                mImgPhone.setVisibility(View.VISIBLE);
            } else {
                mImgPhone.setVisibility(View.GONE);
            }
            if (mEtname.getText().toString().length() > 0) {
                mImgNickName.setVisibility(View.VISIBLE);
            } else {
                mImgNickName.setVisibility(View.GONE);
            }
            if(mEtYanzheng.getText().toString().length()>0){
                mImgYanzhengOff.setVisibility(View.VISIBLE);
            }else {
                mImgYanzhengOff.setVisibility(View.GONE);
            }
            if(mEtDuanxin.getText().toString().length()>0){
                mImgDuanxinOff.setVisibility(View.VISIBLE);
            }else {
                mImgDuanxinOff.setVisibility(View.GONE);
            }
            //清除密码
            if (pwd > 0) {
                mImgPwd.setVisibility(View.VISIBLE);
            } else {
                mImgPwd.setVisibility(View.GONE);
            }
            //清除确认密码
            if (newPwd > 0) {
                mImgNewPwd.setVisibility(View.VISIBLE);
            } else {
                mImgNewPwd.setVisibility(View.GONE);
            }
            if(mEtYanzheng.getText().toString().length()>=4){
                mPresenter.captchaAuth(mEtYanzheng.getText().toString(), realCode);
            }
            if(mEtDuanxin.getText().toString().length()>=4){
                mPresenter.smsCaptchaAuth(mEtDuanxin.getText().toString(), smsCode);
            }
        }
    }
}
