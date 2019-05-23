package com.zhiyi.chinaipo.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.AdsConnector;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.presenters.news.AdsPresenter;
import com.zhiyi.chinaipo.util.LogUtil;


//BaseActivity<WelcomePresenter> implements WelcomeConnector.View

/**
 * @author chen
 */
public class WelcomeActivity extends BaseActivity<AdsPresenter> implements AdsConnector.View {
    /*@BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuthor;*/


    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initEventAndData() {
        //关闭侧滑退出
        setSwipeBackEnable(false);
        //得到当前界面的装饰视图
        View decorView = getWindow().getDecorView();
        //SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏
        //设置系统UI元素的可见性
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Intent intent = new Intent(WelcomeActivity.this, AdActivity.class);
        startActivity(intent);
        finish();
    }

    // 长时间运行的任务
    private String longRunningOperation() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.e("DEBUG", e.toString());
        }

        return "Complete!";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
//                emitter.onNext(longRunningOperation());
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        Log.i("conition", "accept: ");
//                    }
//                });

        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void Ads(AdverticeEntity list) {
        LogUtil.i("err", "err");

//        if (list.size() > 0) {
//            Intent intent = new Intent(this, AdActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    public void err() {
        LogUtil.i("err", "err");
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    /*@Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
      //  mPresenter.getWelcomeData();
    }

    @Override
    public void showContent(AdverticeEntity welcomeData) {
//        ImageLoader.load(this, welcomeBean.getImg(), ivWelcomeBg);
//        ivWelcomeBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
//        tvWelcomeAuthor.setText(welcomeData.getText());
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
//        Glide.clear(ivWelcomeBg);
        super.onDestroy();
    }

*/


}
