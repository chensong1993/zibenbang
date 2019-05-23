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
import com.zhiyi.chinaipo.models.services.CommonService;
import com.zhiyi.chinaipo.models.services.NewsService;
import com.zhiyi.chinaipo.models.services.StockService;
import com.zhiyi.chinaipo.models.services.WeatherService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RetrofitHelper implements HttpHelper {

    private CommonService mCommonApiService;
    private NewsService mNewsApiService;
    private StockService mStockApiService;
    private WeatherService mWeatherService;
  /* @Inject
    public RetrofitHelper(CommonService commonService, NewsService newsService, StockService stockService) {
        this.mCommonApiService = commonService;
        this.mNewsApiService = newsService;
        this.mStockApiService = stockService;
    }*/
    @Inject
    public RetrofitHelper(CommonService mCommonApiService, NewsService mNewsApiService, StockService mStockApiService, WeatherService mWeatherService) {
        this.mCommonApiService = mCommonApiService;
        this.mNewsApiService = mNewsApiService;
        this.mStockApiService = mStockApiService;
        this.mWeatherService = mWeatherService;
    }

    @Override
    public Flowable<ApiResponse<List<AdverticeEntity>>> getWelcomeData() {
        return mCommonApiService.getWelcomeData();
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getArticles(int categoryId) {
        return mNewsApiService.articles(categoryId, 1 );
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getMoreArticles(int categoryId, int pageOffset) {
        return mNewsApiService.articles(categoryId, pageOffset );
    }

    @Override
    public Flowable<ApiResponse<List<CategoryEntity>>> getCategorys(int page) {
        return mNewsApiService.categories(page);
    }

    @Override
    public Flowable<ApiResponse<List<BannerEntity>>> getBanners() {
        return mNewsApiService.banners();
    }

    @Override
    public Flowable<WeatherEntity<AdverticeEntity>> getAds() {
        return mWeatherService.ads();
    }

    @Override
    public Flowable<ApiResponse<AdverticeEntity>> getAds(int page) {
        return mNewsApiService.ads(page);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTopics() {
        return mNewsApiService.topics(1);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTopics(int pageOffset) {
        return mNewsApiService.topics(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ColumnEntity>>> getColumns() {
        return mNewsApiService.columns(1);
    }

    @Override
    public Flowable<ApiResponse<List<ColumnEntity>>> getColumns(int pageOffset) {
        return mNewsApiService.columns(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTColumns() {
        return mNewsApiService.Tcolumns(1);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTColumns(int pageOffset) {
        return mNewsApiService.Tcolumns(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getColumns(String name, int page) {
        return mNewsApiService.articlesByAuthor(name,page);
    }

    @Override
    public Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId) {
        return null;
    }

    @Override
    public Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId, int pageOffset) {
        return null;
    }

    @Override
    public Flowable<ApiResponse<List<IndexSCEntity>>> getIndexSC() {
        return mStockApiService.getIndexSC();
    }

    @Override
    public Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea() {
        return mStockApiService.getTotalArea();
    }

    @Override
    public Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea(int pageOffset) {
        return mStockApiService.getTotalArea(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry() {
        return mStockApiService.getTotalIndustries();
    }

    @Override
    public Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry(int pageOffset) {
        return mStockApiService.getTotalIndustries(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String until, int pageOffset) {
        return mStockApiService.getNewStocks("newstock", until, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor() {
        return mStockApiService.getInvestors();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor(int pageOffset) {
        return mStockApiService.getInvestors(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer() {
        return mStockApiService.getDealers();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer(int pageOffset) {
        return mStockApiService.getDealers(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer() {
        return mStockApiService.getLawyers();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer(int pageOffset) {
        return mStockApiService.getLawyers(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant() {
        return mStockApiService.getAccountants();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant(int pageOffset) {
        return mStockApiService.getAccountants(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockList() {
        return mStockApiService.getNewStocks("newstock", "week", 1);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockList(String baseIndex) {
        return mStockApiService.getNewStocks("newstock", "all", 1);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList() {
        return mStockApiService.getInnovates("innovate", 1);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList(int pageOffset) {
        return mStockApiService.getInnovates("innovate", pageOffset);
    }

    @Override
    public Single<ApiResponse<List<MarketIndexEntity>>> getStockIndex(String indexName) {
        return mStockApiService.stockIndex(indexName);
    }

    @Override
    public Flowable<ApiResponse<List<ArticleDetailEntity>>> getArticle(int id, int originalId) {
        if (id != 0) {
            return mNewsApiService.articleFromId(id);
        } else {
            return mNewsApiService.articleFromOriginalId(originalId);
        }
    }

    @Override
    public Flowable<ApiResponse<List<AnnouncementEntity>>> getAnnouncements(String stockCode, int pageOffset) {
        return mStockApiService.getAnnouncement(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String stockCode) {
        return mStockApiService.getStockByCode(stockCode, 1);
    }

    @Override
    public Flowable<ApiResponse<List<TimeLineEntity>>> getTimeLine(String stockCode, int pageOffset) {
        return mStockApiService.getTimeLine(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKDay(String stockCode, int pageOffset) {
        return mStockApiService.getKDay(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKWeek(String stockCode, int pageOffset) {
        return mStockApiService.getKWeek(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKMonth(String stockCode, int pageOffset) {
        return mStockApiService.getKMonth(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ShareListEntity>>> getShareList(String stockCode) {
        return mStockApiService.getShareList(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode) {
        return mStockApiService.getFinance(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<BalanceEntity>>> getBalance(String stockCode, int pageOffset) {
        return mStockApiService.getBalance(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ShareHoldersEntity>>> getShareHolders(String stockCode, int pageOffset) {
        return mStockApiService.getShareHolders(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ManagementEntity>>> getManagement(String stockCode, int pageOffset) {
        return mStockApiService.getManagement(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ProfileEntity>>> getProfile(String stockCode) {
        return mStockApiService.getProfile(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode, int pageOffset) {
        return mStockApiService.getFinance(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<CashFlowEntity>>> getCash(String stockCode, int pageOffset) {
        return mStockApiService.getCash(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByKey(String searchKey, int offset) {
        return mNewsApiService.articlesByKey(searchKey, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByAuthor(String author, int offset) {
        return mNewsApiService.articlesByAuthor(author, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTopic(int topicId, int offset) {
        return mNewsApiService.articlesByTopic(topicId, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTag(String tag, int offset) {
        return mNewsApiService.articlesByTag(tag, offset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String baseIndex, String until, int pageOffset) {
        if (baseIndex.equals("newstock")) {
            return mStockApiService.getNewStocks("newstock", until, pageOffset);
        } else {
            return mStockApiService.getNewStocks(baseIndex, until, pageOffset);
        }
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovates(int pageOffset) {
        return mStockApiService.getInnovates("innovate", pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, int pageOffset) {
        return mStockApiService.getStock(baseIndex, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sortBy, int pageOffset) {
        return mStockApiService.getStock(baseIndex, sortBy, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sortBy, String order, int pageOffset) {
        return mStockApiService.getStock(baseIndex, sortBy, order, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockByCode(String stockCode, int pageOffset) {
        return mStockApiService.getStockByCode(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockByName(String stockName, int pageOffset) {
        return mStockApiService.getStockByName(stockName, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByIndustry(String baseIndex, String sortBy,String order, String industryCode, int pageOffset) {
        return mStockApiService.getStocksByIndustry(baseIndex, sortBy, order, industryCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByArea(String baseIndex, String sortBy,String order, String areaCode, int pageOffset) {
        return mStockApiService.getStocksByArea(baseIndex, sortBy, order, areaCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByInvestor(String baseIndex, String sortBy,String order, String investorCode, int pageOffset) {
        return mStockApiService.getStocksByInvestor(baseIndex, sortBy, order, investorCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByDealer(String baseIndex, String sortBy,String order, String dealerCode, int pageOffset) {
        return mStockApiService.getStocksByDealer(baseIndex, sortBy, order, dealerCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByLawyer(String baseIndex, String sortBy,String order, String lawyerCode, int pageOffset) {
        return mStockApiService.getStocksByLawyer(baseIndex, sortBy, order, lawyerCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByAccountant(String baseIndex, String sortBy,String order, String accountantCode, int pageOffset) {
        return mStockApiService.getStocksByAccountant(baseIndex, sortBy, order, accountantCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<Top5Entity>>> getTopDeals(String stockCode) {
        return mStockApiService.getTopDeals(stockCode);
    }

    @Override
    public Flowable<TokenEntity> tryLogin(String userName, String password) {
        return mCommonApiService.tryLogin(userName, password);
    }

    @Override
    public Flowable<UserEntity> updateUserInfo(String token) {
        return mCommonApiService.getUserInfo(token);
    }

    @Override
    public Flowable<TokenEntity> tryRegister(String userName, String password, String confirmPassword) {
        return mCommonApiService.tryRegister(userName,password,confirmPassword);
    }

    @Override
    public Flowable<CaptchaEntity> getCaptcha() {
        return mCommonApiService.getCaptcha();
    }

    @Override
    public Flowable<captchaAuthEntity> captchaAuth(String response, String hashkey) {
        return mCommonApiService.captchaAuth(response,hashkey);
    }

    @Override
    public Flowable<HashKeyEntity> passwordChange(String token,String newPassword1, String newPassword2) {
        return mCommonApiService.passwordChange(token,newPassword1,newPassword2);
    }

    @Override
    public Flowable<HashKeyEntity> smsCaptcha(String phoneNum) {
        return mCommonApiService.smsCaptcha(phoneNum);
    }

    @Override
    public Flowable<captchaAuthEntity> smsCaptchaAuth(String phoneNum, String hashkey) {
        return mCommonApiService.smsCaptchaAuth(phoneNum, hashkey);
    }

    @Override
    public Flowable<HashKeyEntity> getAvatar(String token,MultipartBody.Part file) {
        return mCommonApiService.tryAvatar(token,file);
    }

    @Override
    public Flowable<HashKeyEntity> avatarUpload(String token, RequestBody file) {
        return mCommonApiService.avatarUpload(token,file);
    }

    @Override
    public Flowable<WeatherEntity<H3>> weatherData(String appKey, String area) {
        return mWeatherService.weatherData(appKey,area);
    }


}
