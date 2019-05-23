package com.zhiyi.chinaipo.ui.activity.search;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.components.RxBus;
import com.zhiyi.chinaipo.models.event.SearchEvent;
import com.zhiyi.chinaipo.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by admin on 2017/8/14.
 * 搜索界面详情页面上面的
 * 可以进入这个网页看详情
 * 是一个弹窗动画
 * https://github.com/razerdp/BasePopup
 */
public class SearchChoosePopup extends BasePopupWindow implements View.OnClickListener {
    private Context mContext;
    private int mSearchType;
    private String mSearchKey;

    @BindView(R.id.tv_zixun)
    TextView mTvNews;
    @BindView(R.id.tv_hangqing)
    TextView mTvStock;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_quanbu)
    TextView mTvAll;

    public SearchChoosePopup(Activity mContext, int searchType, String searchKey) {
        super(mContext);

        this.mContext = mContext;
        this.mSearchType = searchType;
        this.mSearchKey = searchKey;
        mTvNews = (TextView) findViewById(R.id.tv_zixun);
        mTvStock = (TextView) findViewById(R.id.tv_hangqing);
        mTvAuthor = (TextView) findViewById(R.id.tv_author);
        mTvAll = (TextView) findViewById(R.id.tv_quanbu);
        setViewClickListener(this, mTvNews, mTvStock, mTvAuthor, mTvAll);

        setupDefaultColor();
    }

    private void setupDefaultColor() {
        mTvNews.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        mTvStock.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        mTvAuthor.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
        mTvAll.setTextColor(ContextCompat.getColor(mContext, R.color.blue));

        switch (mSearchType) {
            case Constants.SEARCHING_TYPE_NEWS:
                mTvNews.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                break;
            case Constants.SEARCHING_TYPE_STOCK:
                mTvStock.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                break;
            case Constants.SEARCHING_TYPE_AUTHOR:
                mTvAuthor.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                break;
            default:
                mTvAll.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
                break;
        }
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(false);
        Animation shakeAnima = new RotateAnimation(0, 90, Animation.ABSOLUTE, 1f, Animation.ZORDER_TOP, 1f);
        shakeAnima.setInterpolator(new CycleInterpolator(5));
        shakeAnima.setDuration(400);
        set.addAnimation(getDefaultAlphaAnimation());
        set.addAnimation(shakeAnima);
        return set;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.item_seek_souuso);
    }

    @Override
    public View initAnimaView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_zixun:
                RxBus.getDefault().post(new SearchEvent(mSearchKey, Constants.SEARCHING_TYPE_NEWS));
                break;
            case R.id.tv_hangqing:
                RxBus.getDefault().post(new SearchEvent(mSearchKey, Constants.SEARCHING_TYPE_STOCK));
                break;
            case R.id.tv_author:
                RxBus.getDefault().post(new SearchEvent(mSearchKey, Constants.SEARCHING_TYPE_AUTHOR));
                break;
            default:
                RxBus.getDefault().post(new SearchEvent(mSearchKey, Constants.SEARCHING_TYPE_ALL));
                break;
        }
        dismiss();
    }

}
