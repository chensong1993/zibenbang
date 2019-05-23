package com.zhiyi.chinaipo.models.services;

import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsService {

    @GET("articles/")
    Flowable<ApiResponse<List<ArticlesEntity>>> articles(@Query("categoryId") int categoryId, @Query("page") int offset);

    @GET("articles/")
    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByKey(@Query("search") String searchKey, @Query("page") int offset);

    @GET("articles/")
    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByAuthor(@Query("author") String author, @Query("page") int offset);

    @GET("articles/")
    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTopic(@Query("topic") int topicId, @Query("page") int offset);

    @GET("articles/")
    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTag(@Query("tag") String tag, @Query("page") int offset);

    @GET("article/")
    Flowable<ApiResponse<List<ArticleDetailEntity>>> articleFromId(@Query("id") int id);

    @GET("article/")
    Flowable<ApiResponse<List<ArticleDetailEntity>>> articleFromOriginalId(@Query("originalId") int originalId);

    @GET("category/")
    Flowable<ApiResponse<List<CategoryEntity>>> categories(@Query("page") int pageOffset);

    @Headers(Constants.CACHE_CONTROL_AGE)
    @GET("banners/")
    Flowable<ApiResponse<List<BannerEntity>>> banners();

    @Headers(Constants.CACHE_CONTROL_AGE)
    @GET("columns/")
    Flowable<ApiResponse<List<ColumnEntity>>> columns(@Query("page") int pageOffset);

    @Headers(Constants.CACHE_CONTROL_AGE)
    @GET("columns/")
    Flowable<ApiResponse<List<TopicEntity>>> Tcolumns(@Query("page") int pageOffset);

    @GET("topics/")
    Flowable<ApiResponse<List<TopicEntity>>> topics(@Query("page") int pageOffset);

    @GET("ads/")
    Flowable<ApiResponse<AdverticeEntity>> ads();

    @GET("ads/")
    Flowable<ApiResponse<AdverticeEntity>> ads(@Query("page") int pageOffset);
}
