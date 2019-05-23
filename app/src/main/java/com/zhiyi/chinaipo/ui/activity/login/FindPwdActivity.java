package com.zhiyi.chinaipo.ui.activity.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.auth.RegisterConnector;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.presenters.auth.RegisterPresenter;
import com.zhiyi.chinaipo.util.CountDownTimerUtils;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RegUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;


/**
 * @note 忘记密码界面
 * @anthor Song Chen
 */
public class FindPwdActivity extends BaseActivity<RegisterPresenter> implements RegisterConnector.View, View.OnClickListener {
    @BindView(R.id.tvyiyou)
    TextView tv_yiyou;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_tijiao)
    TextView tv_tijiao;
    @BindView(R.id.tv_quxiao)
    TextView tv_quxiao;
    @BindView(R.id.et_yanzheng)
    EditText et_yanzheng;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_duanxin)
    EditText etDuanxin;
    @BindView(R.id.img_tuxing)
    ImageView mYanzhengma;
    @BindView(R.id.img_yanzheng_clear)
    ImageView mImgYanzhengOff;
    @BindView(R.id.img_sms_clear)
    ImageView mSmsClear;
    @BindView(R.id.img_off)
    ImageView mOff;
    @BindView(R.id.img_phone_off)
    ImageView mImgPhoneOff;
    //短信验证
    @BindView(R.id.but_note)
    Button mButNote;
    @BindView(R.id.rl_login_register)
    RelativeLayout mRlZH;
    CountDownTimerUtils countDownTimerUtils;
    //获取验证码
    private String realCode, result, smsHasgkey, smsOK, mPhone;


    @Override
    protected int getLayout() {
        return R.layout.activity_find_pwd;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        //手机号码
        mPhone = SPHelper.get(Constants.REGISTER_MESSAGES_USER, "");
        //点击注册
        tv_register.setOnClickListener(this);
        //注册
        tv_register.setOnClickListener(this);
        //已有账号
        tv_yiyou.setOnClickListener(this);
        //清除
        mImgPhoneOff.setOnClickListener(this);
        //提交
        tv_tijiao.setOnClickListener(this);
        tv_tijiao.addTextChangedListener(new EditChangedListener());
        //验证码
        et_yanzheng.setOnClickListener(this);
        et_yanzheng.addTextChangedListener(new EditChangedListener());
        //点击全选
      /*  et_yanzheng.setSelection(0);
        et_yanzheng.setFocusable(true);
        et_yanzheng.setSelectAllOnFocus(true);*/
        //取消
        tv_quxiao.setOnClickListener(this);
        //退出
        mOff.setOnClickListener(this);
        //验证码图形
        mYanzhengma.setOnClickListener(this);
        mImgYanzhengOff.setOnClickListener(this);
        //短信验证图像
        mButNote.setOnClickListener(this);
        //手机号码
        etPhone.setOnClickListener(this);
        etPhone.addTextChangedListener(new EditChangedListener());
        //短信验证
        mSmsClear.setOnClickListener(this);
        etDuanxin.setOnClickListener(this);
        etDuanxin.addTextChangedListener(new EditChangedListener());
        if (getIntent().getStringExtra(Constants.SP_USER_INFO_TOKEN) != null) {
            mRlZH.setVisibility(View.GONE);
            mOff.setVisibility(View.GONE);
        } else {
            mRlZH.setVisibility(View.VISIBLE);
            mOff.setVisibility(View.VISIBLE);
        }
        if (mPhone != null && mPhone != "") {
            etPhone.setVisibility(View.GONE);
            mImgPhoneOff.setVisibility(View.GONE);
        } else {
            etPhone.setVisibility(View.VISIBLE);
            mImgPhoneOff.setVisibility(View.VISIBLE);
        }
        mPresenter.getCaptcha();
    }


    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tvyiyou:
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
                case R.id.tv_register:
                    startActivity(new Intent(this, RegisterActivity.class));
                    finish();
                    break;
                case R.id.img_tuxing:
                    mPresenter.getCaptcha();
                    break;
                case R.id.img_phone_off:
                    etPhone.setText("");
                    break;
                //退出
                case R.id.img_off:
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;
                case R.id.img_yanzheng_clear:
                    et_yanzheng.setText("");
                    break;
                case R.id.img_sms_clear:
                    etDuanxin.setText("");
                    break;
                //取消
                case R.id.tv_quxiao:
                    finish();
                    break;
                case R.id.but_note:
                    if (result != null) {
                        if ("validated".equals(result)) {
                            if (mPhone != null && !mPhone.equals("")) {
                                mPresenter.smsCaptcha(mPhone);
                            } else {
                                mPresenter.smsCaptcha(etPhone.getText().toString());
                            }
                            countDownTimerUtils = new CountDownTimerUtils(mButNote, 60000, 1000);
                            countDownTimerUtils.start();
                            LogUtil.i("validated123", "jingguo");
                        } else {
                            ToastUtil.showToast(this, "验证码错误");
                        }
                    } else {
                        ToastUtil.showToast(this, "验证码错误");
                    }
                    break;
                //提交
                case R.id.tv_tijiao:
                    if (result != null && smsOK != null) {
                        if (mPhone != null && !mPhone.equals("") && "validated".equals(result) && "validated".equals(smsOK)) {
                            Intent intent = new Intent(FindPwdActivity.this, ResetPwdActivity.class);
                            intent.putExtra(Constants.SP_USER_INFO_NAME, mPhone);
                            startActivity(intent);
                            finish();
                        } else if (RegUtil.isMobile(etPhone.getText().toString()) && "validated".equals(result) && "validated".equals(smsOK)) {
                       /* Intent intent = new Intent(FindPwdActivity.this, ResetPwdActivity.class);
                        intent.putExtra(Constants.SP_USER_INFO_NAME, "");
                        startActivity(intent);
                        finish();*/
                            EventLogin eventLogin = new EventLogin();
                            eventLogin.setIcon("");
                            eventLogin.setLogin("查看个人信息");
                            eventLogin.setName(etPhone.getText().toString());
                            eventLogin.setToken(etPhone.getText().toString());
                            RxBus.getDefault().post(eventLogin);
                            RxBus.getDefault().post(new AuthenticatedEvent("ok", AuthenticatedEvent.AuthEventType.USER_TOKEN, etPhone.getText().toString()));
                            finish();
                        } else {
                            mPresenter.captchaAuth(et_yanzheng.getText().toString(), realCode);
                            ToastUtil.showToast(this, "短信验证错误");
                        }
                    } else {
                        mPresenter.captchaAuth(et_yanzheng.getText().toString(), realCode);
                        ToastUtil.showToast(this, "输入错误");
                    }
                    break;
                default:
                    break;
            }
        }
    }

   /* //按返回键返回
    @Override
    public void onBackPressedSupport() {
       // startActivity(new Intent(FindPwdActivity.this, LoginActivity.class));
        finish();
    }
*/
    @Override
    public void inProcessing(String statusMsg) {

    }

    @Override
    public void registerSuccess(String tokenEntity) {
        LogUtil.i("44444444", tokenEntity);
    }


    @Override
    public void registerErr() {

    }

    @Override
    public void getCaptcha(CaptchaEntity captchaEntity) {
        // Glide.with(this).load(captchaEntity.getImage2x_url()).into(mYanzhengma);
        ImageLoader.load(this, captchaEntity.getImage2x_url(), mYanzhengma);
        realCode = captchaEntity.getHashkey();
        LogUtil.i("getCaptcha", captchaEntity.getImage2x_url());
    }

    @Override
    public void captchaAuth(String status) {
        result = status;
        if (status != null) {
            if (status.equals("status")) {
                et_yanzheng.setText("");
                ToastUtil.showToast(this, "验证码错误");
            }
        }
        LogUtil.i("getCaptcha", status);
    }

    @Override
    public void smsCaptcha(String hashkey) {
        smsHasgkey = hashkey;
        LogUtil.i("getCaptchas", hashkey);
    }

    @Override
    public void smsCaptchaAuth(String s) {
        smsOK = s;
        LogUtil.i("getCaptcha", s);
    }

    @Override
    public void getCaptchaErr() {
        ToastUtil.showToast(this, "验证码错误");
    }

    @Override
    public void smsCaptchaErr(String s) {
        ToastUtil.showToast(this, s);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    //提交修改密码是的验证条件
    class EditChangedListener implements TextWatcher {
        //输入前
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        //输入中
        @Override
        public void onTextChanged(CharSequence ss, int i, int i1, int i2) {

        }

        //输入后
        @Override
        public void afterTextChanged(Editable editable) {
            int phone = etPhone.getText().toString().length();
            int sms = etDuanxin.getText().toString().length();
            int yanzhengma = et_yanzheng.getText().toString().length();
            if (phone > 0) {
                mImgPhoneOff.setVisibility(View.VISIBLE);
            } else {
                mImgPhoneOff.setVisibility(View.GONE);
            }
            if (yanzhengma > 0) {
                mImgYanzhengOff.setVisibility(View.VISIBLE);
            } else {
                mImgYanzhengOff.setVisibility(View.GONE);
            }
            if (sms > 0) {
                mSmsClear.setVisibility(View.VISIBLE);
            } else {
                mSmsClear.setVisibility(View.GONE);
            }
            if (et_yanzheng.getText().toString().length() >= 4) {
                mPresenter.captchaAuth(et_yanzheng.getText().toString(), realCode);
            }
            if (etDuanxin.getText().toString().length() >= 4) {
                mPresenter.smsCaptchaAuth(etDuanxin.getText().toString(), smsHasgkey);
            }
        }
    }
}
