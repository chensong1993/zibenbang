package com.zhiyi.chinaipo.ui.activity.profile;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.AuthenticateActivity;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import butterknife.BindView;

/**
 * @note 昵称的修改界面
 * @anthor Song Chen
 */
public class NickNameChangesActivity extends AuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_baocun)
    TextView mBaocun;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    //清除
    @BindView(R.id.imgcache)
    ImageView mImgCache;

    private UserEntity userInfo;
    String nickName;
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
        //标题
        mTitle.setText(R.string.nickname_modification);
        userInfo = mDataManager.getUserEntityFromPrefs();
        nickName = getIntent().getStringExtra(Constants.USER_NICKNAME);
        mEtContent.setText(nickName);
    }


    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_baocun:
                   /* userInfo.setFullname(mEtContent.getText().toString());
                    mDataManager.updateUserPrefs(userInfo);*/
                    Intent intent = new Intent();
                    if(mEtContent.getText().toString()!=null){
                        intent.putExtra(Constants.USER_NICKNAME, mEtContent.getText().toString());
                        setResult(Activity.RESULT_OK, intent);
                    }else {
                        intent.putExtra(Constants.USER_NICKNAME, nickName);
                        setResult(Activity.RESULT_OK, intent);
                    }
                    finish();
                    break;
                case R.id.imgcache:
                    mEtContent.setText("");
                    break;
                case R.id.rl_back:
                    finish();
                default:
                    break;

            }
        }
    }


}
