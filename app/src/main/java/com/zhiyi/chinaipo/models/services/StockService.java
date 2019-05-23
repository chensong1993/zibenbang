package com.zhiyi.chinaipo.models.services;

import com.zhiyi.chinaipo.models.entity.details.AnnouncementEntity;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;
import com.zhiyi.chinaipo.models.entity.details.KLineEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.details.ManagementEntity;
import com.zhiyi.chinaipo.models.entity.details.ProfileEntity;
import com.zhiyi.chinaipo.models.entity.details.ShareHoldersEntity;
import com.zhiyi.chinaipo.models.entity.details.ShareListEntity;
import com.zhiyi.chinaipo.models.entity.details.TimeLineEntity;
import com.zhiyi.chinaipo.models.entity.details.Top5Entity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface StockService {

    @GET("indexSC/")
    Flowable<ApiResponse<List<IndexSCEntity>>> getIndexSC();

    @GET("statistics/industry/")
    Flowable<ApiResponse<List<StasIndustryEntity>>> getTotalIndustries();

    @GET("statistics/industry/")
    Flowable<ApiResponse<List<StasIndustryEntity>>> getTotalIndustries(@Query("page") int pageOffset);

    @GET("statistics/area/")
    Flowable<ApiResponse<List<StasAreaEntity>>> getTotalArea();

    @GET("statistics/area/")
    Flowable<ApiResponse<List<StasAreaEntity>>> getTotalArea(@Query("page") int pageOffset);

    @GET("statistics/investor/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getInvestors();

    @GET("statistics/investor/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getInvestors(@Query("page") int pageOffset);

    @GET("statistics/dealer/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getDealers();

    @GET("statistics/dealer/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getDealers(@Query("page") int pageOffset);

    @GET("statistics/lawyer/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getLawyers();

    @GET("statistics/lawyer/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getLawyers(@Query("page") int pageOffset);

    @GET("statistics/accountant/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getAccountants();

    @GET("statistics/accountant/")
    Flowable<ApiResponse<List<StasOrgsEntity>>> getAccountants(@Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(@Query("baseIndex") String baseIndex, @Query("until") String until, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getInnovates(@Query("baseIndex") String baseIndex, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(@Query("baseIndex") String baseIndex, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStockByCode(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStockByName(@Query("name") String stockName, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByIndustry(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("industry") String industryCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByArea(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("area") String areaCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByInvestor(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("investor") String areaCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByDealer(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("dealer") String areaCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByLawyer(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("lawyer") String areaCode, @Query("page") int pageOffset);

    @GET("tchart/")
    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByAccountant(@Query("baseIndex") String baseIndex, @Query("sortBy") String sortBy, @Query("order") String order, @Query("accountant") String areaCode, @Query("page") int pageOffset);

    @GET("index/")
    Single<ApiResponse<List<MarketIndexEntity>>> stockIndex(@Query("name") String indexName);

    @GET("announces/")
    Flowable<ApiResponse<List<AnnouncementEntity>>> getAnnouncement(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("kday/")
    Flowable<ApiResponse<List<KLineEntity>>> getKDay(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("kweek/")
    Flowable<ApiResponse<List<KLineEntity>>> getKWeek(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("kmonth/")
    Flowable<ApiResponse<List<KLineEntity>>> getKMonth(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("timeline/")
    Flowable<ApiResponse<List<TimeLineEntity>>> getTimeLine(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("structures/")
    Flowable<ApiResponse<List<ShareListEntity>>> getShareList(@Query("code") String stockCode);

    @GET("finance/")
    Flowable<ApiResponse<List<FinanceEntity>>> getFinance(@Query("code") String stockCode);

    @GET("cash/")
    Flowable<ApiResponse<List<CashFlowEntity>>> getCash(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("balance/")
    Flowable<ApiResponse<List<BalanceEntity>>> getBalance(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("holders/")
    Flowable<ApiResponse<List<ShareHoldersEntity>>> getShareHolders(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("management/")
    Flowable<ApiResponse<List<ManagementEntity>>> getManagement(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("profile/")
    Flowable<ApiResponse<List<ProfileEntity>>> getProfile(@Query("code") String stockCode);

    @GET("finance/")
    Flowable<ApiResponse<List<FinanceEntity>>> getFinance(@Query("code") String stockCode, @Query("page") int pageOffset);

    @GET("tops/")
    Flowable<ApiResponse<List<Top5Entity>>> getTopDeals(@Query("code") String stockCode);

}
