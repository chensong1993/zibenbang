package com.zhiyi.chinaipo.models.services;

import com.zhiyi.chinaipo.models.entity.LocationEntity;
import com.zhiyi.chinaipo.models.entity.LocationResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * @author chensong
 * @date 2018/9/19 17:25
 */
public interface LocationService {
    @GET("ip?ak=UKBtIB3ARS9E2g5q8QYZsneWXLaExjon")
    Flowable<LocationResponse<LocationEntity>> location();
}
