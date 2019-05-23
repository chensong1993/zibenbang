package com.zhiyi.chinaipo.ui.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;

import me.shaohui.bottomdialog.BaseBottomDialog;
import me.shaohui.shareutil.share.ShareListener;
import me.shaohui.shareutil.share.SharePlatform;

import static me.shaohui.shareutil.ShareUtil.shareMedia;


/**
 * @author
 */
public class ShareBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private int id, type;
    private String title, titleDetail, StockId;
    private ShareListener mShareListener;
    private Bitmap mBitmap;
    //type  0代表新闻类型  1代表股票详情  2广告详情

    public static ShareBottomDialog newInstance(String title, String titleDetail, int id, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("titleDetail", titleDetail);
        bundle.putInt("id", id);
        bundle.putInt("type", type);
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public static ShareBottomDialog newInstance(String title, String titleDetail, String id, int type) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("titleDetail", titleDetail);
        bundle.putString("StockId", id);
        bundle.putInt("type", type);
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    @Override
    public void bindView(final View v) {
        v.findViewById(R.id.rl_demiss).setOnClickListener(this);
        v.findViewById(R.id.img_qq).setOnClickListener(this);
        v.findViewById(R.id.img_qz).setOnClickListener(this);
        v.findViewById(R.id.img_weibo).setOnClickListener(this);
        v.findViewById(R.id.img_weixin).setOnClickListener(this);
        v.findViewById(R.id.img_pengyouquan).setOnClickListener(this);
        v.findViewById(R.id.tv_finish).setOnClickListener(this);
        mShareListener = new ShareListener() {
            @Override
            public void shareSuccess() {
                Toast.makeText(v.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareFailure(Exception e) {
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    public void onClick(View view) {
        id = getArguments().getInt("id");
        StockId = getArguments().getString("StockId");
        title = getArguments().getString("title");
        titleDetail = getArguments().getString("titleDetail");
        type = getArguments().getInt("type");
        Resources r = this.getContext().getResources();
        mBitmap = BitmapFactory.decodeResource(r, R.drawable.ic_search_icon);
        switch (view.getId()) {
            case R.id.img_qq:
                if (type == 0) {
                    shareMedia(getContext(), SharePlatform.QQ, title, titleDetail,
                            Constants.CHINAIPO_3G_NEWS_URL + id + ".html", mBitmap, mShareListener);
                } else if (type == 1) {
                    shareMedia(getContext(), SharePlatform.QQ, title, titleDetail,
                            Constants.CHINAIPO_SHARESTOCK_URL + StockId , mBitmap, mShareListener);
                } else if (type == 2) {
                    shareMedia(getContext(), SharePlatform.QQ, title, title,
                            titleDetail, mBitmap, mShareListener);
                }
                break;
            case R.id.img_qz:
                if (type == 0) {
                    shareMedia(getContext(), SharePlatform.QZONE, title, titleDetail,
                            Constants.CHINAIPO_3G_NEWS_URL + id + ".html", mBitmap, mShareListener);
                } else if (type == 1) {
                    shareMedia(getContext(), SharePlatform.QZONE, title, titleDetail,
                            Constants.CHINAIPO_SHARESTOCK_URL + StockId , mBitmap, mShareListener);
                } else if (type == 2) {
                    shareMedia(getContext(), SharePlatform.QZONE, title, title,
                            titleDetail, mBitmap, mShareListener);
                }
                break;
            case R.id.img_weibo:
                if (type == 0) {
                    shareMedia(getContext(), SharePlatform.WEIBO, title, titleDetail,
                            Constants.CHINAIPO_3G_NEWS_URL + id + ".html", mBitmap, mShareListener);
                } else if (type == 1) {
                    shareMedia(getContext(), SharePlatform.WEIBO, title, titleDetail,
                            Constants.CHINAIPO_SHARESTOCK_URL + StockId , mBitmap, mShareListener);
                } else if (type == 2) {
                    shareMedia(getContext(), SharePlatform.WEIBO, title, title,
                            titleDetail, mBitmap, mShareListener);
                }
                break;
            case R.id.img_pengyouquan:
                if (type == 0) {
                    shareMedia(getContext(), SharePlatform.WX_TIMELINE, title, titleDetail,
                            Constants.CHINAIPO_3G_NEWS_URL + id + ".html", mBitmap, mShareListener);
                } else if (type == 1) {
                    shareMedia(getContext(), SharePlatform.WX_TIMELINE, title, titleDetail,
                            Constants.CHINAIPO_SHARESTOCK_URL + StockId , mBitmap, mShareListener);
                } else if (type == 2) {
                    shareMedia(getContext(), SharePlatform.WX_TIMELINE, title, title,
                            titleDetail, mBitmap, mShareListener);
                }
                break;
            case R.id.img_weixin:
                if (type == 0) {
                    shareMedia(getContext(), SharePlatform.WX, title, titleDetail,
                            Constants.CHINAIPO_3G_NEWS_URL + id + ".html", mBitmap, mShareListener);
                } else if (type == 1) {
                    shareMedia(getContext(), SharePlatform.WX, title, titleDetail,
                            Constants.CHINAIPO_SHARESTOCK_URL + StockId , mBitmap, mShareListener);
                } else if (type == 2) {
                    shareMedia(getContext(), SharePlatform.WX, title, title,
                            titleDetail, mBitmap, mShareListener);
                }
                break;
            case R.id.tv_finish:
                dismiss();
                break;
            case R.id.rl_demiss:
                dismiss();
            default:
                dismiss();
                break;
        }
    }
}
