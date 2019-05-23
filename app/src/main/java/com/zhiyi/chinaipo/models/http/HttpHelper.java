package com.zhiyi.chinaipo.models.http;

import com.zhiyi.chinaipo.models.entity.AdverticeEntity;
import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.H3;
import com.zhiyi.chinaipo.models.entity.IndexSCEntity;
import com.zhiyi.chinaipo.models.entity.MarketIndexEntity;
import com.zhiyi.chinaipo.models.entity.StasAreaEntity;
import com.zhiyi.chinaipo.models.entity.StasIndustryEntity;
import com.zhiyi.chinaipo.models.entity.StasOrgsEntity;
import com.zhiyi.chinaipo.models.entity.StockPriceEntity;
import com.zhiyi.chinaipo.models.entity.UserEntity;
import com.zhiyi.chinaipo.models.entity.WeatherEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticleDetailEntity;
import com.zhiyi.chinaipo.models.entity.articles.ArticlesEntity;
import com.zhiyi.chinaipo.models.entity.articles.BannerEntity;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;
import com.zhiyi.chinaipo.models.entity.articles.ColumnEntity;
import com.zhiyi.chinaipo.models.entity.articles.CommentEntity;
import com.zhiyi.chinaipo.models.entity.articles.TopicEntity;
import com.zhiyi.chinaipo.models.entity.auth.CaptchaEntity;
import com.zhiyi.chinaipo.models.entity.auth.HashKeyEntity;
import com.zhiyi.chinaipo.models.entity.auth.TokenEntity;
import com.zhiyi.chinaipo.models.entity.auth.captchaAuthEntity;
import com.zhiyi.chinaipo.models.entity.details.AnnouncementEntity;
import com.zhiyi.chinaipo.models.entity.details.BalanceEntity;
import com.zhiyi.chinaipo.models.entity.details.CashFlowEntity;
import com.zhiyi.chinaipo.models.entity.details.FinanceEntity;
import com.zhiyi.chinaipo.models.entity.details.KLineEntity;
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
import okhttp3.RequestBody;

public interface HttpHelper {

    Flowable<ApiResponse<List<AdverticeEntity>>> getWelcomeData();

    Flowable<ApiResponse<List<ArticlesEntity>>> getArticles(int categoryId);

    Flowable<ApiResponse<List<ArticleDetailEntity>>> getArticle(int id, int originalId);

    Flowable<ApiResponse<List<ArticlesEntity>>> getMoreArticles(int categoryId, int pageOffset);

    Flowable<ApiResponse<List<CategoryEntity>>> getCategorys(int page);

    Flowable<ApiResponse<List<BannerEntity>>> getBanners();

    Flowable<WeatherEntity<AdverticeEntity>> getAds();

    Flowable<ApiResponse<AdverticeEntity>> getAds(int page);

    Flowable<ApiResponse<List<TopicEntity>>> getTopics();

    Flowable<ApiResponse<List<TopicEntity>>> getTopics(int pageOffset);

    Flowable<ApiResponse<List<ColumnEntity>>> getColumns();

    Flowable<ApiResponse<List<ColumnEntity>>> getColumns(int pageOffset);

    Flowable<ApiResponse<List<TopicEntity>>> getTColumns();

    Flowable<ApiResponse<List<TopicEntity>>> getTColumns(int pageOffset);


    Flowable<ApiResponse<List<ArticlesEntity>>> getColumns(String name, int page);

    Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId);

    Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId, int pageOffset);

    Flowable<ApiResponse<List<IndexSCEntity>>> getIndexSC();

    Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea();

    Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea(int pageOffset);

    Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry();

    Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry(int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String until, int pageOffset);

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor();

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor(int pageOffset);

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer();

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer(int pageOffset);

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer();

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer(int pageOffset);

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant();

    Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant(int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStockList();

    Flowable<ApiResponse<List<StockPriceEntity>>> getStockList(String baseIndex);


    Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList();

    Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList(int pageOffset);

    Single<ApiResponse<List<MarketIndexEntity>>> getStockIndex(String indexName);

    Flowable<ApiResponse<List<AnnouncementEntity>>> getAnnouncements(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String stockCode);

    Flowable<ApiResponse<List<TimeLineEntity>>> getTimeLine(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<KLineEntity>>> getKDay(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<KLineEntity>>> getKWeek(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<KLineEntity>>> getKMonth(String stockCode, int pageOffset);


    Flowable<ApiResponse<List<ShareListEntity>>> getShareList(String stockCode);

    Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode);

    Flowable<ApiResponse<List<BalanceEntity>>> getBalance(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<ShareHoldersEntity>>> getShareHolders(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<ManagementEntity>>> getManagement(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<ProfileEntity>>> getProfile(String stockCode);

    Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<CashFlowEntity>>> getCash(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByKey(String searchKey, int offset);

    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByAuthor(String author, int offset);

    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTopic(int topicId, int offset);

    Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTag(String tag, int offset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String baseIndex, String until, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getInnovates(int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sort, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sort, String orderBy, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStockByCode(String stockCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStockByName(String stockName, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByIndustry(String baseIndex, String sortBy, String order, String industryCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByArea(String baseIndex, String sortBy, String order, String areaCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByInvestor(String baseIndex, String sortBy, String order, String investorCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByDealer(String baseIndex, String sortBy, String order, String dealerCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByLawyer(String baseIndex, String sortBy, String order, String lawyerCode, int pageOffset);

    Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByAccountant(String baseIndex, String sortBy, String order, String accountantCode, int pageOffset);

    Flowable<ApiResponse<List<Top5Entity>>> getTopDeals(String stockCode);

    Flowable<TokenEntity> tryLogin(String userName, String password);

    Flowable<UserEntity> updateUserInfo(String token);

    Flowable<TokenEntity> tryRegister(String userName, String password, String confirmPassword);

    Flowable<CaptchaEntity> getCaptcha();

    Flowable<captchaAuthEntity> captchaAuth(String response, String hashkey);

    Flowable<HashKeyEntity> passwordChange(String token, String newPassword1, String newPassword2);

    Flowable<HashKeyEntity> smsCaptcha(String phoneNum);

    Flowable<captchaAuthEntity> smsCaptchaAuth(String phoneNum, String hashkey);

    Flowable<HashKeyEntity> getAvatar(String token, MultipartBody.Part file);

    Flowable<HashKeyEntity> avatarUpload(String token, RequestBody file);

    Flowable<WeatherEntity<H3>> weatherData(String appKey, String area);
}
