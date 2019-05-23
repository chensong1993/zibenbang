package com.zhiyi.chinaipo.injections.modules;

import com.zhiyi.chinaipo.BuildConfig;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.injections.qualifier.CommonURL;
import com.zhiyi.chinaipo.injections.qualifier.NewsURL;
import com.zhiyi.chinaipo.injections.qualifier.StockURL;
import com.zhiyi.chinaipo.injections.qualifier.WeatherURL;
import com.zhiyi.chinaipo.models.services.CommonService;
import com.zhiyi.chinaipo.models.services.NewsService;
import com.zhiyi.chinaipo.models.services.StockService;
import com.zhiyi.chinaipo.models.services.WeatherService;
import com.zhiyi.chinaipo.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    @CommonURL
    Retrofit provideCommonRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.CHINAIPO_COMMON_API_URL);
    }

    @Singleton
    @Provides
    @NewsURL
    Retrofit provideNewsRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.CHINAIPO_NEWS_API_URL);
    }


    @Singleton
    @Provides
    @StockURL
    Retrofit provideStockRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.CHINAIPO_STOCK_API_URL);
    }

    @Singleton
    @Provides
    @WeatherURL
    Retrofit provideWeatherRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, Constants.CHINAIPO_NEWS_API_URL);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            //NONE不打印log  BODY 请求+相应行+Http头+Http请求体
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public,only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("Authorization", Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        };
//        设置统一的请求头部参数
//        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(80, TimeUnit.SECONDS);
        builder.readTimeout(40, TimeUnit.SECONDS);
        builder.writeTimeout(40, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Singleton
    @Provides
    NewsService provideNewsService(@NewsURL Retrofit retrofit) {
        return retrofit.create(NewsService.class);
    }

    @Singleton
    @Provides
    CommonService provideCommonService(@CommonURL Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }


    @Singleton
    @Provides
    StockService provideStockService(@StockURL Retrofit retrofit) {
        return retrofit.create(StockService.class);
    }

    @Singleton
    @Provides
    WeatherService provideWeatherService(@WeatherURL Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
