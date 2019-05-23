package com.zhiyi.chinaipo.ui.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.base.BaseActivity;
import com.zhiyi.chinaipo.base.connectors.news.AdsConnector;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.presenters.news.AdsPresenter;
import com.zhiyi.chinaipo.ui.activity.misc.WebAdActivity;
import com.zhiyi.chinaipo.ui.widget.ad.AdsUiFresco;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.RepeatCllickUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AdActivity extends BaseActivity<AdsPresenter> implements AdsConnector.View, AdsUiFresco.UiFrescos {
    private int progress = 6;
    @BindView(R.id.ad_view)
    ImageView mAd;
    //    @BindView(R.id.img_ad)
//    ImageView mImgAds;
    boolean click = true;
    ThreadPoolExecutor executor;
    ExecutorService executorService;
    //AdsTime adsTime;
    String mAdDetail = null;
    String mAdImage;
    // MyHandler myHandler;
    @BindView(R.id.time_progress)
    TextView mAdBottons;
    // public static AdsUiFresco.UiFrescos frescos;
    AdsUiFresco adsUiFresco;

    @Override
    protected int getLayout() {
        return R.layout.activity_ad;
    }


    @Override
    protected void initEventAndData() {
        //  frescos = this;
        //关闭侧滑退出
        setSwipeBackEnable(false);
         mPresenter.getAds();
       /* SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String str = format.format(date);*/
        //adsTime = new AdsTime();
        // ThreadFactory threadFactory = new CustomThreadFactoryBuilder().setNamePrefix("Ad-Thread").setPriority(Thread.MAX_PRIORITY).build();
        // executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5), threadFactory);
        // myHandler = new MyHandler(this);
        // AdsUiFresco adsUiFresco = new AdsUiFresco();
        UiFresco(R.drawable.ic_welcome, mAd);
        // adsUiFresco.UiFresco(AdActivity.this, R.drawable.ic_welcome, mAd);
        //AdOnclick();
    }

    //广告跳转
    @Override
    public void onShowAds() {
        if (progress >= 1) {
            progress -= 1;
//            Message message = new Message();
//            message.what = progress;
//            Log.i("accept", progress + "");
            if (click) {
                if (progress == 1) {
                    startActivity(new Intent(AdActivity.this, MainActivity.class));
                    finish();
                }
                mAdBottons.setText(progress + "  跳过");
            }

        }

    }

    @OnClick(R.id.time_progress)
    void AdOnclick() {
//        RxView.clicks(mAdBottons).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Unit>() {
//            @Override
//            public void accept(Unit unit) throws Exception {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            click = false;
            startActivity(new Intent(AdActivity.this, MainActivity.class));
            finish();
        }
//        });

    }

    @OnClick(R.id.ad_view)
    void adDetail() {
        if (!RepeatCllickUtil.isFastDoubleClick()) {
            if (mAdDetail != null) {
                click = false;
                WebAdActivity.launch(new WebAdActivity.Builder()
                        .setContext(this)
                        .setTitle("推荐")
                        .setUrl(mAdDetail)
                );
                finish();
            }

        }
    }

    @Override
    public void Ads(AdverticeEntity list) {
        mAdDetail = list.getText();
        mAdImage = list.getImg();
        LogUtil.i("132", "ok");
        if (list!=null) {
            LogUtil.i("132", "1");
            UiFresco(mAdImage, mAd);
            // adsUiFresco.UiFresco(AdActivity.this, R.drawable.ic_welcome, mAd);
        }

    }

    public void UiFresco(int url, ImageView imageView) {
        Glide.with(this).load(url).fitCenter().crossFade().listener(new RequestListener<Integer, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                startActivity(new Intent(AdActivity.this, MainActivity.class));
                finish();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .take(5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                onShowAds();
                            }
                        });

                return false;
            }


        }).into(imageView);
    }
    public void UiFresco(String url, ImageView imageView) {
        Glide.with(this).load(url).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                startActivity(new Intent(AdActivity.this, MainActivity.class));
                finish();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .take(5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                onShowAds();
                            }
                        });

                return false;
            }


        }).into(imageView);
    }
    @Override
    public void err() {
        LogUtil.i("132", "err");
    }
    //    static class MyHandler extends Handler {
//
//        private WeakReference<AdActivity> weakReference;
//        private TextView mAdBottons;
//
//        public MyHandler(AdActivity handlerMemoryActivity) {
//            weakReference = new WeakReference<AdActivity>(handlerMemoryActivity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            AdActivity adActivity = weakReference.get();
//            mAdBottons = adActivity.findViewById(R.id.time_progress);
//            mAdBottons.setText(msg.what + "  跳过");
//
//        }


//    }


//    class AdsTime implements Runnable {
//
//        @Override
//        public void run() {
//            while (progress >= 1) {
//                // mAdProgress.setCurrentProgress(progress);
//
//                progress -= 1;
//                Message message = new Message();
//                message.what = progress;
//                if (progress > 0) {
//                   // myHandler.sendMessage(message);
//                }
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//                if (click) {
//                    if (progress == 1) {
//                        startActivity(new Intent(AdActivity.this, MainActivity.class));
//                        finish();
//                    }
//                }
//
//            }
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
}
