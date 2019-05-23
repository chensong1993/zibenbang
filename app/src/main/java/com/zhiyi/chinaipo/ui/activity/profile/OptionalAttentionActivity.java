package com.zhiyi.chinaipo.ui.activity.profile;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import butterknife.BindView;

/**
 * @note 自选里面的个人中心里面的 vip认证里面的关注领域
 * @anthor Song Chen
 */
public class OptionalAttentionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_baocun)
    TextView mBaocun;
    @BindView(R.id.tv_guanzhu)
    TextView mGuanzhu;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    private int flag = 1;


    @Override
    protected int getLayout() {
        return R.layout.activity_guanzhu;
    }

    @Override
    protected void initEventAndData() {
        init();
    }

    private void init() {
        //头部标题
        mTitle.setText(R.string.follow_territory);
        //保存按钮
        mBaocun.setOnClickListener(this);
        //关注按钮
        mGuanzhu.setOnClickListener(this);
        //返回
        mRlBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_baocun:
                    finish();
                    break;
                case R.id.tv_guanzhu:
                    flag = (flag + 1) % 2;
                    mGuanzhu.setText(flag > 0 ? "未关注" : "已关注");
                    mGuanzhu.setTextColor(flag > 0 ? ContextCompat.getColor(this, R.color.blue) : ContextCompat.getColor(this, R.color.hui7));
                    break;
                case R.id.rl_back:
                    //  startActivity(new Intent(OptionalAttentionActivity.this, VipActivity.class));
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void initInject() {

    }
}
