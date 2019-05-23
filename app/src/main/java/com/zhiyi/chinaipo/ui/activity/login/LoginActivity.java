package com.zhiyi.chinaipo.ui.activity.login;


import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.auth.LoginConnector;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.event.AuthenticatedEvent;
import com.zhiyi.chinaipo.models.event.EventLogin;
import com.zhiyi.chinaipo.presenters.auth.LoginPresenter;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.ToastUtil;

import butterknife.BindView;
import me.shaohui.shareutil.LoginUtil;
import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;
import me.shaohui.shareutil.login.result.QQToken;
import me.shaohui.shareutil.login.result.QQUser;
import me.shaohui.shareutil.login.result.WeiboToken;
import me.shaohui.shareutil.login.result.WeiboUser;
import me.shaohui.shareutil.login.result.WxToken;
import me.shaohui.shareutil.login.result.WxUser;

/**
 * @note 登录主界面
 * @anthor Song Chen
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginConnector.View, View.OnClickListener {

    @BindView(R.id.tv_wangpwd)
    TextView mTvWangPwd;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.img_off)
    ImageView mImgOff;
    @BindView(R.id.img_share_qq)
    ImageView mImgQQ;
    @BindView(R.id.img_share_weixin)
    ImageView mImgWeixin;
    @BindView(R.id.img_share_weibo)
    ImageView mImgWeibo;
    @BindView(R.id.img_ipone_off)
    ImageView mImgIphoneOff;
    @BindView(R.id.img_pwd_off)
    ImageView mImgPwdOff;
    private LoginListener mLoginListener;
    @BindView(R.id.tv_auto_jump)
    TextView mTvJump;
    @BindView(R.id.tv_zd)
    TextView mTvBarrier;
    int mode=1;
    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
       /* if(mode==1){
            this.setTheme(R.style.Theme_Small);
        }else if(mode==2){
            this.setTheme(R.style.Theme_Medium);
        }else {
            this.setTheme(R.style.Theme_Large);
        }*/
        mImgIphoneOff.setOnClickListener(this);
        mImgPwdOff.setOnClickListener(this);
        mTvWangPwd.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);

        mTvLogin.setOnClickListener(this);
        mImgOff.setOnClickListener(this);
        mImgQQ.setOnClickListener(this);
        mImgWeixin.setOnClickListener(this);
        mImgWeibo.setOnClickListener(this);
        mEtPhone.addTextChangedListener(new EditChangedListener());
        mEtPhone.setText(getIntent().getStringExtra(Constants.SP_USER_INFO_NAME));
        mEtPwd.addTextChangedListener(new EditChangedListener());
        mEtPwd.setText(getIntent().getStringExtra(Constants.SP_USER_INFO_PASSWORD));
        mLoginListener = new LoginListener() {
            @Override
            public void loginSuccess(LoginResult result) {
                if (!RepeatCllickUtil.isFastDoubleClick()) {
                    switch (result.getPlatform()) {
                        case LoginPlatform.QQ:
                            finish();
                            QQUser user = (QQUser) result.getUserInfo();
                            QQToken token = (QQToken) result.getToken();
                            EventLogin login = new EventLogin();
                            login.setIcon(user.getHeadImageUrlLarge());
                            login.setLogin("查看个人信息");
                            login.setName(user.getNickname());
                            RxBus.getDefault().post(login);
                            RxBus.getDefault().post(new AuthenticatedEvent("ok", AuthenticatedEvent.AuthEventType.USER_TOKEN, token.getOpenid()));
                            break;
                        case LoginPlatform.WEIBO:
                            WeiboUser user1 = (WeiboUser) result.getUserInfo();
                            WeiboToken token1 = (WeiboToken) result.getToken();
                            EventLogin login1 = new EventLogin();
                            login1.setIcon(user1.getHeadImageUrlLarge());
                            login1.setLogin("查看个人信息");
                            login1.setName(user1.getNickname());
                            RxBus.getDefault().post(login1);
                            RxBus.getDefault().post(new AuthenticatedEvent("ok", AuthenticatedEvent.AuthEventType.USER_TOKEN, token1.getOpenid()));
                            finish();
                            break;
                        case LoginPlatform.WX:
                            WxUser wxUser = (WxUser) result.getUserInfo();
                            WxToken wxToken = (WxToken) result.getToken();
                            EventLogin eventLogin = new EventLogin();
                            eventLogin.setIcon(wxUser.getHeadImageUrlLarge());
                            eventLogin.setLogin("查看个人信息");
                            eventLogin.setName(wxUser.getNickname());
                            eventLogin.setToken(wxToken.getAccessToken());
                            RxBus.getDefault().post(eventLogin);
                            RxBus.getDefault().post(new AuthenticatedEvent("ok", AuthenticatedEvent.AuthEventType.USER_TOKEN, wxToken.getOpenid()));
                            finish();
                        default:
                            break;
                    }
                }
            }
            @Override
            public void loginFailure(Exception e) {
                mTvJump.setVisibility(View.GONE);
                mTvBarrier.setVisibility(View.GONE);
                ToastUtil.showToast(LoginActivity.this, "登录失败");
            }

            @Override
            public void loginCancel() {
                mTvJump.setVisibility(View.GONE);
                mTvBarrier.setVisibility(View.GONE);
                ToastUtil.showToast(LoginActivity.this, "登录取消");
            }

        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wangpwd:
                startActivity(new Intent(this, FindPwdActivity.class));
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.tv_login:
                mPresenter.doLogin(mEtPhone.getText().toString(), mEtPwd.getText().toString());
                break;
            case R.id.img_off:
                finish();
                break;
            case R.id.img_share_qq:
                LoginUtil.login(this, LoginPlatform.QQ, mLoginListener);
                mTvJump.setVisibility(View.VISIBLE);
                mTvBarrier.setVisibility(View.VISIBLE);
                break;
            case R.id.img_share_weixin:
                LoginUtil.login(this, LoginPlatform.WX, mLoginListener);
                mTvJump.setVisibility(View.VISIBLE);
                mTvBarrier.setVisibility(View.VISIBLE);
                break;
            case R.id.img_share_weibo:
                LoginUtil.login(this, LoginPlatform.WEIBO, mLoginListener);
                mTvJump.setVisibility(View.VISIBLE);
                mTvBarrier.setVisibility(View.VISIBLE);
                break;
            case R.id.img_ipone_off:
                mEtPhone.setText("");
                break;
            case R.id.img_pwd_off:
                mEtPwd.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void inProcessing(String statusMsg) {
        Log.d("LoginActivity", "Login in processing ...");
    }

    @Override
    public void loginSuccess() {
        // inLogining = false;
        ToastUtil.showToast(LoginActivity.this, "登陆成功");
        finish();
    }

    @Override
    public void loginFailed() {
        ToastUtil.showToast(this, "账号或密码不正确");
        // inLogining = false;
    }

    //满足条件登录
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
            if (mEtPhone.getText().toString().length() > 0) {
                mImgIphoneOff.setVisibility(View.VISIBLE);
            } else {
                mImgIphoneOff.setVisibility(View.GONE);
            }
            if (mEtPwd.getText().toString().length() > 0) {
                mImgPwdOff.setVisibility(View.VISIBLE);
            } else {
                mImgPwdOff.setVisibility(View.GONE);
            }
        }
    }

}
