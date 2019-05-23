package com.zhiyi.chinaipo.ui.activity.news;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhiyi.chinaipo.R;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.util.LogUtil;
import com.zhiyi.chinaipo.util.tool.FileUtils;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class NewsPhotoActivity extends SwipeBackActivity implements View.OnClickListener {
    private ImageView crossIv;
    private ViewPager mPager;
    private ImageView centerIv;
    private TextView photoOrderTv;
    private TextView saveTv;
    private String curImageUrl = "";
    private String[] imageUrls = new String[]{};

    private int curPosition = -1;
    private int[] initialedPositions = null;
    private AnimationDrawable animationDrawable;
    private View curPage;
    RelativeLayout mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_photo);
        imageUrls = getIntent().getStringArrayExtra("imageUrls");
        curImageUrl = getIntent().getStringExtra("curImageUrl");
        initialedPositions = new int[imageUrls.length];
        initInitialedPositions();
        mProgressBar=findViewById(R.id.rl_progress);
        photoOrderTv = findViewById(R.id.photoOrderTv);
        saveTv = findViewById(R.id.saveTv);
        saveTv.setOnClickListener(this);
        centerIv = findViewById(R.id.centerIv);
        crossIv = findViewById(R.id.crossIv);
        crossIv.setOnClickListener(this);
        mPager = findViewById(R.id.pager);
        centerIv.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageUrls.length;
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                if (imageUrls[position] != null && !"".equals(imageUrls[position])) {
                    final PhotoView view = new PhotoView(NewsPhotoActivity.this);
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    Glide.with(NewsPhotoActivity.this).load(imageUrls[position]).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).error(R.drawable.ic_err).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            LogUtil.i("123", "123");
                            mProgressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            occupyOnePosition(position);
                            mProgressBar.setVisibility(View.GONE);
                            LogUtil.i("456", "456");
                            return false;
                        }
                    }).into(view);

                    container.addView(view);
                    return view;
                }
                return null;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                releaseOnePosition(position);
                container.removeView((View) object);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                curPage = (View) object;
            }
        });

        curPosition = returnClickedPosition() == -1 ? 0 : returnClickedPosition();
        mPager.setCurrentItem(curPosition);
        mPager.setTag(curPosition);
        photoOrderTv.setText((curPosition + 1) + "/" + imageUrls.length);//设置页面的编号

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                photoOrderTv.setText((position + 1) + "/" + imageUrls.length);//设置页面的编号
                mPager.setTag(position);//为当前view设置tag
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int returnClickedPosition() {
        if (imageUrls == null || curImageUrl == null) {
            return -1;
        }
        for (int i = 0; i < imageUrls.length; i++) {
            if (curImageUrl.equals(imageUrls[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crossIv:
                finish();
                break;
            case R.id.saveTv:
                savePhotoToLocal();
                break;
            default:
                break;
        }
    }

    private void showLoadingAnimation() {
        centerIv.setImageResource(R.drawable.photo_animation);
        animationDrawable = (AnimationDrawable) centerIv.getDrawable();
        animationDrawable.start();
        /*centerIv.setVisibility(View.VISIBLE);
        centerIv.setImageResource(R.drawable.loading);
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(centerIv, "rotation", 0f, 360f);
            objectAnimator.setDuration(2000);
            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                objectAnimator.setAutoCancel(true);
            }
        }
        objectAnimator.start();*/
    }

    private void hideLoadingAnimation() {
        // releaseResource();
        centerIv.setVisibility(View.GONE);
        centerIv.setImageResource(R.drawable.photo_animation);
        animationDrawable = (AnimationDrawable) centerIv.getDrawable();
        animationDrawable.stop();
    }

    private void showErrorLoading() {
        centerIv.setVisibility(View.VISIBLE);
        //  releaseResource();
        //   centerIv.setImageResource(R.drawable.ic_err);
    }

   /* private void releaseResource() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (centerIv.getAnimation() != null) {
            centerIv.getAnimation().cancel();
        }
    }*/

    private void occupyOnePosition(int position) {
        initialedPositions[position] = position;
    }

    private void releaseOnePosition(int position) {
        initialedPositions[position] = -1;
    }

    private void initInitialedPositions() {
        for (int i = 0; i < initialedPositions.length; i++) {
            initialedPositions[i] = -1;
        }
    }

    private void savePhotoToLocal() {
        int neededPermission = ContextCompat.checkSelfPermission(App.getInstance(), "android.permission.WRITE_EXTERNAL_STORAGE");
        if (neededPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NewsPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            PhotoView photoViewTemp = (PhotoView) curPage;
            if (photoViewTemp != null) {
                GlideBitmapDrawable glideBitmapDrawable = (GlideBitmapDrawable) photoViewTemp.getDrawable();
                if (glideBitmapDrawable == null) {
                    return;
                }
                Bitmap bitmap = glideBitmapDrawable.getBitmap();
                if (bitmap == null) {
                    return;
                }
                FileUtils.savePhoto(this, bitmap, new FileUtils.SaveResultCallback() {
                    @Override
                    public void onSavedSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewsPhotoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onSavedFailed() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(NewsPhotoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }

    }

    @Override
    protected void onDestroy() {
        //    releaseResource();
        curPage = null;
        if (mPager != null) {
            mPager.removeAllViews();
            mPager = null;
        }
        super.onDestroy();
    }
}
