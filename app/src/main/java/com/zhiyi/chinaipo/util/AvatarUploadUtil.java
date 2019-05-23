package com.zhiyi.chinaipo.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhiyi.chinaipo.BuildConfig;
import com.zhiyi.chinaipo.ui.widget.circleView.ClipImageActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author chensong
 * @date 2018/8/28 9:58
 */
public class AvatarUploadUtil {
    Context mContext;
    Photo mPhoto;
    // PopupWindow popupWindow;
    Dialog dialog;

    public AvatarUploadUtil(Context mContext, Photo mPhoto, Dialog dialog) {
        this.mContext = mContext;
        this.mPhoto = mPhoto;
        this.dialog = dialog;
        //  this.popupWindow = popupWindow;
    }

    public void Dialog(int layout1, int camera, int photo, int cancel) {
        View v = LayoutInflater.from(mContext).inflate(layout1, null);
        TextView btnCamera = (TextView) v.findViewById(camera);
        TextView btnPhoto = (TextView) v.findViewById(photo);
        TextView btnCancel = (TextView) v.findViewById(cancel);
        //将布局设置给Dialog
        dialog.setContentView(v);
        //获取当前Activity所在的窗体
        Window window = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        window.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.x = 0;
        //获取显示器的宽度
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels;
        //    将属性设置给窗体
        window.setAttributes(lp);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相机权限
                AndPermission.with(mContext)
                        .requestCode(ClipImageActivity.REQUEST_CODE_PERMISSION_SINGLE)
                        .permission(Permission.CAMERA, Permission.STORAGE)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                // 第二种：用自定义的提示语。
                                com.yanzhenjie.alertdialog.AlertDialog.newBuilder(mContext)
                                        .setTitle("友好提醒")
                                        .setMessage("你已拒绝过打开相机或者保存功能，所以看不到你的绝世美颜，你看着办！")
                                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.resume();
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.cancel();
                                            }
                                        }).show();

                            }
                        })
                        .start();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相机权限
                AndPermission.with(mContext)
                        .requestCode(200)
                        .permission(Permission.STORAGE)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                // 第二种：用自定义的提示语。
                                com.yanzhenjie.alertdialog.AlertDialog.newBuilder(mContext)
                                        .setTitle("友好提醒")
                                        .setMessage("你已拒绝过存储功能，所以不能保存！")
                                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.resume();
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.cancel();
                                            }
                                        }).show();

                            }
                        })
                        .start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private PermissionListener permissionListener = new PermissionListener() {
        //权限获取成功后的回调方法
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case ClipImageActivity.REQUEST_CODE_PERMISSION_SINGLE:
                    Log.d("evan", "*****************打开相机********************");
                    //创建拍照存储的图片文件
                    SimpleDateFormat format = new SimpleDateFormat("yy-dd-ss");
                    Date date = new Date();
                    String time = format.format(date);
                    File tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath()), time + "avatar.png");

                    //跳转到调用系统相机
                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
                        intent1.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID, tempFile);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                    } else {
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    }
                    mPhoto.gotoCamera(intent1, tempFile);
                    dialog.dismiss();
                    break;
                case 200:
                    Log.d("evan", "*****************打开图库********************");
                    //跳转到调用系统图库
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //跳转到相册
                    mPhoto.gotoPhoto(intent);
                    dialog.dismiss();
                    break;
                default:
                    break;

            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case ClipImageActivity.REQUEST_CODE_PERMISSION_SINGLE:
                    dialog.dismiss();
                    break;
                case 200:
                    dialog.dismiss();
                    break;
                default:
                    break;

            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(mContext, deniedPermissions)) {
                // 第一种：用默认的提示语。
                //  AndPermission.defaultSettingDialog(mContext, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog((Activity) mContext, ClipImageActivity.REQUEST_CODE_SETTING)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();

           /*// 第三种：自定义dialog样式。
            SettingService settingService = AndPermission.defineSettingDialog((Activity) mContext, REQUEST_CODE_SETTING);
          //  你的dialog点击了确定调用：
            settingService.execute();
           // 你的dialog点击了取消调用：
            settingService.cancel();*/
            }
        }
    };

    public interface Photo {
        void gotoPhoto(Intent intent);

        void gotoCamera(Intent intent, File file);

    }

   /* *//**
     * 上传头像
     *//*
    public void uploadHeadImage(int layout1, int camera, int photo, int cancel, int popup, Window getWindow) {
        View view = LayoutInflater.from(mContext).inflate(layout1, null);
        TextView btnCamera = (TextView) view.findViewById(camera);
        TextView btnPhoto = (TextView) view.findViewById(photo);
        TextView btnCancel = (TextView) view.findViewById(cancel);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(false);
        // popupWindow.setTouchable(false);
        //View parent = LayoutInflater.from(this).inflate(R.layout.activity_personalcenter, null);
        LinearLayout layout = view.findViewById(popup);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow.getAttributes();
        params.alpha = 0.5f;
        getWindow.setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow.setAttributes(params);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相机权限
                AndPermission.with(mContext)
                        .requestCode(ClipImageActivity.REQUEST_CODE_PERMISSION_SINGLE)
                        .permission(Permission.CAMERA, Permission.STORAGE)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                // 第二种：用自定义的提示语。
                                com.yanzhenjie.alertdialog.AlertDialog.newBuilder(mContext)
                                        .setTitle("友好提醒")
                                        .setMessage("你已拒绝过打开相机或者保存功能，所以看不到你的绝世美颜，你看着办！")
                                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.resume();
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.cancel();
                                            }
                                        }).show();

                            }
                        })
                        .start();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相机权限
                AndPermission.with(mContext)
                        .requestCode(200)
                        .permission(Permission.STORAGE)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                // 第二种：用自定义的提示语。
                                com.yanzhenjie.alertdialog.AlertDialog.newBuilder(mContext)
                                        .setTitle("友好提醒")
                                        .setMessage("你已拒绝过存储功能，所以不能保存！")
                                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.resume();
                                            }
                                        })
                                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                rationale.cancel();
                                            }
                                        }).show();

                            }
                        })
                        .start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }*/


}
