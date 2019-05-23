package com.zhiyi.chinaipo.ui.widget.ad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhiyi.chinaipo.ui.activity.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author chensong
 *         更新UI(直接加载网络方式)
 * @date 2018/12/5 10:51
 */
public class AdsUiFresco {
   // UiFrescos frescos = AdActivity.frescos;

    public interface UiFrescos {
        void onShowAds();
    }

    public void UiFresco(Activity context, int url, ImageView imageView) {
        Glide.with(context).load(url).fitCenter().crossFade().listener(new RequestListener<Integer, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
                context.startActivity(new Intent(context, MainActivity.class));
                context.finish();
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
                        //        frescos.onShowAds();
                            }
                        });

//                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//                float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom()+50;
//
//                imageView.setLayoutParams(params);
                // executor.execute(runnable);
                return false;
            }


        }).into(imageView);
    }


    public static void UiFresco(Activity context, String url, ImageView imageView, ThreadPoolExecutor executor, Runnable runnable) {
        Glide.with(context).load(url).fitCenter().crossFade().listener(new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                context.startActivity(new Intent(context, MainActivity.class));
                context.finish();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                executor.execute(runnable);
                return false;
            }


        }).into(imageView);
    }

    /**
     * 图片下载到本地
     */
    public void downLoad(final String url, Activity context) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final File file = saveFile(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/test/", "ad.jpg", response.body().byteStream());
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (file != null) {
                            //  Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 更新UI(下载保存本地方式)
     *
     * @param url
     */
    private void updateUi(String url, ImageView imageView, ThreadPoolExecutor executor, Runnable runnable) {

        final File file = new File(url);
        if (file.exists()) {
            //mAdBotton.setVisibility(View.VISIBLE);
            executor.execute(runnable);
            imageView.setImageURI(Uri.parse("file://" + url));
        }
    }


    /**
     * 保存下载文件
     */
    public File saveFile(String destFileDir, String destFileName, InputStream inputStream) throws IOException {

        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = inputStream;

            File filedir = new File(destFileDir);
            //如果文件夹不存在则创建
            if (!filedir.exists() && !filedir.isDirectory()) {
                filedir.mkdirs();
            }

            File file = new File(destFileDir + File.separator + destFileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }

        }
    }

}
