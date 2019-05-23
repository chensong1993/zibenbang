package com.zhiyi.chinaipo.models.services;

import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.models.entity.auth.TokenEntity;
import com.zhiyi.chinaipo.models.entity.auth.captchaAuthEntity;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CommonService {

    @GET("ads/")
    Flowable<ApiResponse<List<AdverticeEntity>>> getAds(@Query("adType") int adType, @Query("page") int offset);

    @GET("welcome/")
    Flowable<ApiResponse<List<AdverticeEntity>>> getWelcomeData();
//
//    @GET("version/")
//    Flowable<ApiResponse<List<>>> getVersionInfo();


    @GET("auth/me/")
    Flowable<UserEntity> getUserInfo(@Header("Authorization") String token);

    @PUT("auth/me/")
    Flowable<UserEntity> saveUserInfo(
            @Header("Authorization") String token,
            @Body UserEntity userInfo
    );

    @Multipart
    @POST("auth/photo/")
    Flowable<HashKeyEntity> tryAvatar(@Header("Authorization") String token, @Part MultipartBody.Part body);

    @FormUrlEncoded
    @PUT("auth/avatar/")
    Flowable<HashKeyEntity> avatarUpload(@Header("Authorization") String token, @Body RequestBody body);

    @FormUrlEncoded
    @POST("auth/login/")
    Flowable<TokenEntity> tryLogin(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/register/")
    Flowable<TokenEntity> tryRegister(@Field("username") String userName, @Field("password1") String password, @Field("password2") String affirmPassword);

    @GET("auth/captcha/")
    Flowable<CaptchaEntity> getCaptcha();

    @FormUrlEncoded
    @POST("auth/captcha-auth/")
    Flowable<captchaAuthEntity> captchaAuth(@Field("response") String response, @Field("hashkey") String hasgkey);

    @FormUrlEncoded
    @POST("auth/password/change/")
    Flowable<HashKeyEntity> passwordChange(@Header("Authorization") String token, @Field("new_password1") String newpassword1, @Field("new_password2") String newpassword2);

    @GET("auth/sms/")
    Flowable<HashKeyEntity> smsCaptcha(@Query("phonenum") String phonenum);

    @FormUrlEncoded
    @POST("auth/sms-auth/")
    Flowable<captchaAuthEntity> smsCaptchaAuth(@Field("response") String response, @Field("hashkey") String hashkey);
}
