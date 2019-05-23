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
 * @note 修改信息界面
 * @anthor Song Chen
 */
public class InformationChangesActivity extends AuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_baocun)
    TextView mBaocun;
    @BindView(R.id.tv_title)
    TextView mTitle;
    //标题
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    //清除
    @BindView(R.id.imgcache)
    ImageView mImgCache;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinxixiugai);
        ButterKnife.bind(this);
        initView();
    }*/

    @Override
    protected int getLayout() {
        return R.layout.activity_xinxixiugai;
    }

    @Override
    protected void initEventAndData() {
        initView();
    }

    private void initView() {
        //清除
        mImgCache.setOnClickListener(this);
        mEtContent.setOnClickListener(this);
        mBaocun.setOnClickListener(this);
        //返回
        mRlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.et_content:
                    break;
                case R.id.tv_baocun:
                    finish();
                    break;
                case R.id.imgcache:
                    mEtContent.setText(null);
                    break;
                case R.id.rl_back:
                    finish();
                default:
                    break;

            }
        }
    }

}
