package com.zhiyi.chinaipo.ui.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.auth.AvatarConnector;
import com.zhiyi.chinaipo.components.ImageLoader;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.models.entity.auth.NicknameEntity;
import com.zhiyi.chinaipo.models.entity.auth.PortraitEntity;
import com.zhiyi.chinaipo.models.greendao.NicknameEntityDao;
import com.zhiyi.chinaipo.models.greendao.PortraitEntityDao;
import com.zhiyi.chinaipo.presenters.auth.AvatarPresenter;
import com.zhiyi.chinaipo.ui.activity.login.FindPwdActivity;
import com.zhiyi.chinaipo.ui.fragment.ProfileFragment;
import com.zhiyi.chinaipo.ui.widget.circleView.CircleImageView;
import com.zhiyi.chinaipo.ui.widget.circleView.ClipImageActivity;
import com.zhiyi.chinaipo.util.AvatarUploadUtil;
import com.zhiyi.chinaipo.util.FileUtil;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.PopupUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;
import com.zhiyi.chinaipo.util.SPHelper;
import com.zhiyi.chinaipo.util.ToastUtil;
import com.zhiyi.chinaipo.util.anim.Cinematics;

import org.greenrobot.greendao.query.Query;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @note 个人中心
 * @anthor Song Chen
 */
public class ProfileSettingActivity extends BaseActivity<AvatarPresenter> implements AvatarConnector.View, View.OnClickListener {
    public final static int NICKNAME_REQUEST = 111;
    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    //修改密码
    @BindView(R.id.rl_pwd)
    RelativeLayout mRlPwd;
    //vip认证
    /*@BindView(R.id.rl_vip)
    RelativeLayout mRlVip;*/
    //昵称
    @BindView(R.id.rl_nickname)
    RelativeLayout mRlNicheng;
    //头像
    @BindView(R.id.rl_head_portrait)
    RelativeLayout mRlHeadPortrait;

    @BindView(R.id.tv_back)
    TextView mTvBack;
    //顶部标题
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    //昵称
    @BindView(R.id.tv_nickname)
    TextView mTvNickName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.img_icon_op)
    CircleImageView mImgHeadPortraitView;
    String icon, nickName, token;
    View inflate;
    Dialog mDialog;
    boolean image = true;
    //调用照相机返回图片文件
    private File tempFile;
    // PopupWindow popupWindow;
    AvatarUploadUtil.Photo photo;
    //上传头像文件
    PortraitEntity mPortraitEntity;
    NicknameEntity mNicknameEntity;

    @Override
    protected int getLayout() {
        return R.layout.activity_personalcenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //5.0以上新动画特性
        Cinematics.requestFeature(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData() {
        //NavigationBar.getNavigation(this);
        initview();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void inProcessing(Throwable statusMsg) {

    }

    @Override
    public void loginSuccess(HashKeyEntity mResult) {
        ToastUtil.showToast(this, "ok");
    }

    @Override
    public void avatarFile(String mfilePath) {
        LogUtil.i("avatarFile", mfilePath);
    }


    private void initview() {
        //头部标题
        mTvTitle.setText(R.string.personal_center);
        mRlBack.setOnClickListener(this);
        //密码修改
        mRlPwd.setOnClickListener(this);
        //vip认证
        //mRlVip.setOnClickListener(this);
        //退出
        mTvBack.setOnClickListener(this);
        //昵称修改
        mRlNicheng.setOnClickListener(this);
        //昵称
        mRlHeadPortrait.setOnClickListener(this);
        Bundle bundle = getIntent().getBundleExtra(Constants.PROFILE_PARAMETER);
        String user = bundle.getString(Constants.REGISTER_MESSAGES_USER);
        token = bundle.getString(Constants.REGISTER_MESSAGES_TOKEN);
        //头像保存是否成功
        Query query = App.getInstance().getPortraitEntityDao().queryBuilder().where(PortraitEntityDao.Properties.Token.eq(token)).build();
        List<PortraitEntity> mPortraitEntities = query.list();
        if (mPortraitEntities.size() > 0) {
            for (PortraitEntity entity : mPortraitEntities) {
                icon = entity.getPortrait();
            }
        } else {
            icon = (String) SPHelper.get(Constants.REGISTER_MESSAGES_ICON, "");
        }
        //修改昵称是否保存成功
        Query nicknameQuery = App.getInstance().getNicknameEntityDao().queryBuilder().where(NicknameEntityDao.Properties.Token.eq(token)).build();
        List<NicknameEntity> nNickname = nicknameQuery.list();
        if (nNickname.size() > 0) {
            for (NicknameEntity entity : nNickname) {
                nickName = entity.getNickneme();
                mTvNickName.setText(nickName);
            }
        } else {
            nickName = (String) SPHelper.get(Constants.REGISTER_MESSAGES_NICKNAME, "");
        }

        if (user == null || user.equals("")) {
            mTvPhone.setText(token);
            if (nickName == null && nickName.equals("")) {
                mTvNickName.setText(token);
            } else {
                mTvNickName.setText(nickName);
            }
        } else {
            mTvPhone.setText(user);
            if (nickName == null && nickName.equals("")) {
                mTvNickName.setText(user);
            } else {
                mTvNickName.setText(nickName);
            }
        }
        if ((icon != null && !icon.equals("") && image)) {
            // RequestOptions options = new RequestOptions().error(R.mipmap.icon_op);
            // Glide.with(ProfileSettingActivity.this).load(icon).asBitmap().into(mImgHeadPortraitView);
            ImageLoader.loadBitmap(ProfileSettingActivity.this, icon, mImgHeadPortraitView, R.mipmap.icon_op);
        } else {
            ImageLoader.load(ProfileSettingActivity.this, icon, R.mipmap.ic_icon_blue_ipo_03, mImgHeadPortraitView);
        }
    }


    @Override
    public void onClick(View view) {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.rl_back:
                    finish();
                    break;
           /* //vip认证
            case R.id.rl_vip:
                startActivity(new Intent(this, VipActivity.class));
                break;
                */
                //密码修改
                case R.id.rl_pwd:
                    Intent intent = new Intent(this, FindPwdActivity.class);
                    intent.putExtra(Constants.SP_USER_INFO_TOKEN, "ok");
                    startActivity(intent);
                    break;
                case R.id.tv_back:
                    logOut();
                    break;
                //昵称
                case R.id.rl_nickname:
                    Intent intent1 = new Intent(this, NickNameChangesActivity.class);
                    intent1.putExtra(Constants.USER_NICKNAME, nickName);
                    startActivityForResult(intent1, NICKNAME_REQUEST);
                    break;
                case R.id.rl_head_portrait:
                    uploadHeadImage();
                    break;
                default:
                    break;
            }
        }
    }

    private void logOut() {
        mDialog = new Dialog(this, R.style.DialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.item_dialog_back, null);
        PopupUtil.Dialog(this, inflate, mDialog);
        mDialog.show();//显示对话框

        inflate.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.remove(Constants.REGISTER_MESSAGES_TOKEN);
                SPHelper.remove(Constants.REGISTER_MESSAGES_NICKNAME);
                SPHelper.remove(Constants.REGISTER_MESSAGES_LOGIN);
                SPHelper.remove(Constants.REGISTER_MESSAGES_ICON);
                SPHelper.remove(Constants.REGISTER_MESSAGES_USER);
                Intent intent = new Intent(getApplicationContext(), ProfileFragment.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.REGISTER_MESSAGES_NICKNAME, "点击登录");
                bundle.putString(Constants.REGISTER_MESSAGES_TOKEN, null);
                bundle.putString(Constants.REGISTER_MESSAGES_LOGIN, "点击完善个人信息");
                bundle.putString(Constants.REGISTER_MESSAGES_ICON, "");
                bundle.putString(Constants.REGISTER_MESSAGES_USER, null);
                intent.putExtra(Constants.PROFILE_PARAMETER, bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
                mDialog.dismiss();
            }
        });
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case NICKNAME_REQUEST:
                if (resultCode == RESULT_OK) {
                    String name = intent.getStringExtra(Constants.USER_NICKNAME);
                    mTvNickName.setText(name);
                    mNicknameEntity = new NicknameEntity(null, token, name);
                    App.getInstance().getNicknameEntityDao().insert(mNicknameEntity);
                }
                break;
            //调用系统相机返回
            case ClipImageActivity.REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                    //  ImageLoader.loadBitmap(ProfileSettingActivity.this, tempFile.getPath(), mImgHeadPortraitView, R.mipmap.icon_op);
                }
                break;
            //调用系统相册返回
            case ClipImageActivity.REQUEST_PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            //剪切图片返回
            case ClipImageActivity.REQUEST_CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }

                    String cropImagePath = FileUtil.getRealFilePathFromUri(getApplicationContext(), uri);
                    mPortraitEntity = new PortraitEntity(null, token, cropImagePath);
                    App.getInstance().getPortraitEntityDao().insert(mPortraitEntity);
                    ImageLoader.loadBitmap(ProfileSettingActivity.this, cropImagePath, mImgHeadPortraitView, R.mipmap.icon_op);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    LogUtil.i("cropImagePath", cropImagePath);
                    mImgHeadPortraitView.setImageBitmap(bitMap);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    File file = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath()), "avatar.png");
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("Media", file.getName(), requestBody);
                   /* RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse(""), file))
                            .build();*/
                    mPresenter.getAvatar("token " + token, part);
                    //  mPresenter.getAvatarUpload("token "+token,body);
                    LogUtil.i("afterCrop", file.getName());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        photo = new AvatarUploadUtil.Photo() {
            @Override
            public void gotoPhoto(Intent intent) {
                startActivityForResult(Intent.createChooser(intent, "请选择图片"), ClipImageActivity.REQUEST_PICK);
            }

            @Override
            public void gotoCamera(Intent intent, File file) {
                tempFile = file;
                LogUtil.i("flie", file.getPath());
                startActivityForResult(intent, ClipImageActivity.REQUEST_CAPTURE);
            }
        };
        mDialog = new Dialog(this, R.style.DialogStyle);
        AvatarUploadUtil avatarUploadUtil = new AvatarUploadUtil(this, photo, mDialog);
        avatarUploadUtil.Dialog(R.layout.layout_popupwindow, R.id.btn_camera, R.id.btn_photo, R.id.btn_cancel);
        mDialog.show();
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", 1);
        intent.setData(uri);
        startActivityForResult(intent, ClipImageActivity.REQUEST_CROP_PHOTO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
