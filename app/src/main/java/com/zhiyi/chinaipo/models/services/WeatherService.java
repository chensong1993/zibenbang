package com.zhiyi.chinaipo.models.services;

import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.H3;
import com.zhiyi.chinaipo.models.entity.WeatherEntity;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author chensong
 * @date 2018/9/18 17:02
 */
public interface WeatherService {
    @FormUrlEncoded
    @POST("area")
    Flowable<WeatherEntity<H3>> weatherData(@Field("appKey") String appKey, @Field("area") String area);

    @GET("ads/")
    Flowable<WeatherEntity<AdverticeEntity>> ads();
}
