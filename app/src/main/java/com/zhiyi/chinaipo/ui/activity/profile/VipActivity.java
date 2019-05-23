package com.zhiyi.chinaipo.ui.activity.profile;

import android.app.Dialog;
import android.content.Intent;
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
 * @note 进入vip认证
 * @anthor Song Chen
 */
public class VipActivity extends AuthenticateActivity implements View.OnClickListener {
    private View inflate;
    private Dialog mDialog;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.tv_nicheng)
    EditText mTvNicheng;
    @BindView(R.id.tv_bumenwei)
    EditText mTvBumen;
    @BindView(R.id.tv_phonewei)
    EditText mTvPhone;
    @BindView(R.id.tv_emailwei)
    EditText mTvMail;
    @BindView(R.id.tv_qiyewei)
    EditText mTvQiye;
    @BindView(R.id.tv_leibiewei)
    EditText mTvLeibie;
    @BindView(R.id.tv_baocun)
    TextView mTvBaocun;
    @BindView(R.id.tv_vip_baocun)
    TextView mTvVipBaocun;
    @BindView(R.id.tv_diquwei)
    TextView mTvDiqu;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rl_name)
    RelativeLayout mRlName;
    @BindView(R.id.rl_guanzhu)
    RelativeLayout mRlGuanzhu;
    @BindView(R.id.rl_diqu)
    RelativeLayout mRlDiqu;
    @BindView(R.id.rl_leibie)
    RelativeLayout mRlLeibie;
    @BindView(R.id.rl_qiye)
    RelativeLayout mRlQiye;
    @BindView(R.id.rl_bumen)
    RelativeLayout mRlBumen;
    @BindView(R.id.rl_phone)
    RelativeLayout mRlPhone;
    @BindView(R.id.rl_email)
    RelativeLayout mRlMail;
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        initView();

    }*/

    @Override
    protected int getLayout() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initEventAndData() {
        initView();
    }

    private void initView() {
        //dingbu 标题
        mTvTitle.setText(R.string.x_vip);
        mImgBack.setOnClickListener(this);
        //真实姓名
        mRlName.setOnClickListener(this);
        //关注
        mRlGuanzhu.setOnClickListener(this);
        mTvBaocun.setOnClickListener(this);
        //底部保存
        mTvVipBaocun.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                //左上角返回
                case R.id.img_back:
                    finish();
                    break;
                //保存
                case R.id.tv_baocun:
                    finish();
                    break;
                //姓名
                case R.id.rl_name:
             /*   intent = new Intent(this, InformationChangesActivity.class);
                startActivity(intent);*/
                    break;
                //关注
                case R.id.rl_guanzhu:
                    startActivity(new Intent(this, OptionalAttentionActivity.class));
                    break;
                case R.id.tv_vip_baocun:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}
