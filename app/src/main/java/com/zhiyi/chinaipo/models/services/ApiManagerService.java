package com.zhiyi.chinaipo.models.services;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiManagerService {

    @GET
    Observable<ResponseBody> downloadPicFromNet(@Url String fileUrl);
  
}  