package com.zhiyi.chinaipo.models;

import android.text.TextUtils;
import android.util.Log;

import com.zhiyi.chinaipo.models.db.DBHelper;
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
import com.zhiyi.chinaipo.models.http.HttpHelper;
import com.zhiyi.chinaipo.models.prefs.PreferencesHelper;
import com.zhiyi.chinaipo.models.prefs.UserPreferencesHelper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class DataManager implements HttpHelper, DBHelper, PreferencesHelper {

    HttpHelper mHttpHelper;
    DBHelper mDbHelper;
    PreferencesHelper mPreferencesHelper;
    UserPreferencesHelper mUserPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DBHelper dbHelper, PreferencesHelper preferencesHelper, UserPreferencesHelper userHelper) {
        mHttpHelper = httpHelper;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mUserPreferenceHelper = userHelper;
    }

    @Override
    public boolean getNoImageState() {
        return mPreferencesHelper.getNoImageState();
    }

    @Override
    public void setNoImageState(boolean state) {
        mPreferencesHelper.setNoImageState(state);
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferencesHelper.getAutoCacheState();
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mPreferencesHelper.setAutoCacheState(state);
    }

    @Override
    public int getCurrentItem() {
        return mPreferencesHelper.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int item) {
        mPreferencesHelper.setCurrentItem(item);
    }

    @Override
    public boolean getLikePoint() {
        return mPreferencesHelper.getLikePoint();
    }

    @Override
    public void setLikePoint(boolean isFirst) {
        mPreferencesHelper.setLikePoint(isFirst);
    }

    @Override
    public boolean getVersionPoint() {
        return mPreferencesHelper.getVersionPoint();
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        mPreferencesHelper.setVersionPoint(isFirst);
    }

    @Override
    public boolean getManagerPoint() {
        return mPreferencesHelper.getManagerPoint();
    }

    @Override
    public void setManagerPoint(boolean isFirst) {
        mPreferencesHelper.setManagerPoint(isFirst);
    }

    @Override
    public void insertNewsId(int id) {
        mDbHelper.insertNewsId(id);
    }

    @Override
    public boolean queryNewsId(int id) {
        return mDbHelper.queryNewsId(id);
    }
//
//    @Override
//    public void insertLikeBean(RealmLikeBean bean) {
//        mDbHelper.insertLikeBean(bean);
//    }

    @Override
    public void deleteLikeBean(String id) {
        mDbHelper.deleteLikeBean(id);
    }

    @Override
    public boolean queryLikeId(String id) {
        return mDbHelper.queryLikeId(id);
    }

//    @Override
//    public List<RealmLikeBean> getLikeList() {
//        return mDbHelper.getLikeList();
//    }

    @Override
    public void changeLikeTime(String id, long time, boolean isPlus) {
        mDbHelper.changeLikeTime(id, time, isPlus);
    }


    @Override
    public Flowable<ApiResponse<List<AdverticeEntity>>> getWelcomeData() {
        return mHttpHelper.getWelcomeData();
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getArticles(int categoryId) {
        return mHttpHelper.getArticles(categoryId);
    }

    @Override
    public Flowable<ApiResponse<List<ArticleDetailEntity>>> getArticle(int id, int originalId) {
        return mHttpHelper.getArticle(id, originalId);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getMoreArticles(int categoryId, int pageOffset) {
        return mHttpHelper.getMoreArticles(categoryId, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<CategoryEntity>>> getCategorys(int page) {
        return mHttpHelper.getCategorys(page);
    }

    @Override
    public Flowable<ApiResponse<List<BannerEntity>>> getBanners() {
        return mHttpHelper.getBanners();
    }

    @Override
    public Flowable<WeatherEntity<AdverticeEntity>> getAds() {
        return mHttpHelper.getAds();
    }

    @Override
    public Flowable<ApiResponse<AdverticeEntity>> getAds(int page) {
        return mHttpHelper.getAds(page);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTopics() {
        return mHttpHelper.getTopics();
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTopics(int pageOffset) {
        return mHttpHelper.getTopics(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ColumnEntity>>> getColumns() {
        return mHttpHelper.getColumns();
    }

    @Override
    public Flowable<ApiResponse<List<ColumnEntity>>> getColumns(int pageOffset) {
        return mHttpHelper.getColumns(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTColumns() {
        return mHttpHelper.getTColumns();
    }

    @Override
    public Flowable<ApiResponse<List<TopicEntity>>> getTColumns(int pageOffset) {
        return mHttpHelper.getTColumns(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> getColumns(String name, int page) {
        return mHttpHelper.getColumns(name, page);
    }

    @Override
    public Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId) {
        return mHttpHelper.getCommentsForNews(newsId);
    }

    @Override
    public Flowable<ApiResponse<List<CommentEntity>>> getCommentsForNews(int newsId, int pageOffset) {
        return mHttpHelper.getCommentsForNews(newsId, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<IndexSCEntity>>> getIndexSC() {
        return mHttpHelper.getIndexSC();
    }

    @Override
    public Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea() {
        return mHttpHelper.getStatisticByArea();
    }

    @Override
    public Flowable<ApiResponse<List<StasAreaEntity>>> getStatisticByArea(int pageOffset) {
        return mHttpHelper.getStatisticByArea(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry() {
        return mHttpHelper.getStatisticByIndustry();
    }

    @Override
    public Flowable<ApiResponse<List<StasIndustryEntity>>> getStatisticByIndustry(int pageOffset) {
        return mHttpHelper.getStatisticByIndustry(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String until, int pageOffset) {
        return mHttpHelper.getNewStocks(until, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor() {
        return mHttpHelper.getStatisticByInvestor();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByInvestor(int pageOffset) {
        return mHttpHelper.getStatisticByInvestor(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer() {
        return mHttpHelper.getStatisticByDealer();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByDealer(int pageOffset) {
        return mHttpHelper.getStatisticByDealer(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer() {
        return mHttpHelper.getStatisticByLawyer();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByLawyer(int pageOffset) {
        return mHttpHelper.getStatisticByLawyer(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant() {
        return mHttpHelper.getStatisticByAccountant();
    }

    @Override
    public Flowable<ApiResponse<List<StasOrgsEntity>>> getStatisticByAccountant(int pageOffset) {
        return mHttpHelper.getStatisticByAccountant(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockList() {
        return mHttpHelper.getStockList();
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockList(String baseIndex) {
        return mHttpHelper.getStockList(baseIndex);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList() {
        return mHttpHelper.getInnovateList();
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovateList(int pageOffset) {
        return mHttpHelper.getInnovateList(pageOffset);
    }

    @Override
    public Single<ApiResponse<List<MarketIndexEntity>>> getStockIndex(String indexName) {
        return mHttpHelper.getStockIndex(indexName);
    }

    @Override
    public Flowable<ApiResponse<List<AnnouncementEntity>>> getAnnouncements(String stockCode, int pageOffset) {
        return mHttpHelper.getAnnouncements(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String stockCode) {
        return mHttpHelper.getStock(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<TimeLineEntity>>> getTimeLine(String stockCode, int pageOffset) {
        return mHttpHelper.getTimeLine(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKDay(String stockCode, int pageOffset) {
        return mHttpHelper.getKDay(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKWeek(String stockCode, int pageOffset) {
        return mHttpHelper.getKWeek(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<KLineEntity>>> getKMonth(String stockCode, int pageOffset) {
        return mHttpHelper.getKMonth(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ShareListEntity>>> getShareList(String stockCode) {
        return mHttpHelper.getShareList(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode) {
        return mHttpHelper.getFinance(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<BalanceEntity>>> getBalance(String stockCode, int pageOffset) {
        return mHttpHelper.getBalance(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ShareHoldersEntity>>> getShareHolders(String stockCode, int pageOffset) {
        return mHttpHelper.getShareHolders(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ManagementEntity>>> getManagement(String stockCode, int pageOffset) {
        return mHttpHelper.getManagement(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ProfileEntity>>> getProfile(String stockCode) {
        return mHttpHelper.getProfile(stockCode);
    }

    @Override
    public Flowable<ApiResponse<List<FinanceEntity>>> getFinance(String stockCode, int pageOffset) {
        return mHttpHelper.getFinance(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<CashFlowEntity>>> getCash(String stockCode, int pageOffset) {
        return mHttpHelper.getCash(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByKey(String searchKey, int offset) {
        return mHttpHelper.articlesByKey(searchKey, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByAuthor(String author, int offset) {
        return mHttpHelper.articlesByAuthor(author, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTopic(int topicId, int offset) {
        return mHttpHelper.articlesByTopic(topicId, offset);
    }

    @Override
    public Flowable<ApiResponse<List<ArticlesEntity>>> articlesByTag(String tag, int offset) {
        return mHttpHelper.articlesByTag(tag, offset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getNewStocks(String baseIndex, String until, int pageOffset) {
        return mHttpHelper.getNewStocks(baseIndex, until, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getInnovates(int pageOffset) {
        return mHttpHelper.getInnovateList(pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, int pageOffset) {
        return mHttpHelper.getStock(baseIndex, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sort, int pageOffset) {
        return mHttpHelper.getStock(baseIndex, sort, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStock(String baseIndex, String sort, String orderBy, int pageOffset) {
        return mHttpHelper.getStock(baseIndex, sort, orderBy, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockByCode(String stockCode, int pageOffset) {
        return mHttpHelper.getStockByCode(stockCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStockByName(String stockName, int pageOffset) {
        return mHttpHelper.getStockByName(stockName, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByIndustry(String baseIndex, String sortBy, String order, String industryCode, int pageOffset) {
        return mHttpHelper.getStocksByIndustry(baseIndex, sortBy, order, industryCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByArea(String baseIndex, String sortBy, String order, String areaCode, int pageOffset) {
        return mHttpHelper.getStocksByArea(baseIndex, sortBy, order, areaCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByInvestor(String baseIndex, String sortBy, String order, String investorCode, int pageOffset) {
        return mHttpHelper.getStocksByInvestor(baseIndex, sortBy, order, investorCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByDealer(String baseIndex, String sortBy, String order, String dealerCode, int pageOffset) {
        return mHttpHelper.getStocksByDealer(baseIndex, sortBy, order, dealerCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByLawyer(String baseIndex, String sortBy, String order, String lawyerCode, int pageOffset) {
        return mHttpHelper.getStocksByLawyer(baseIndex, sortBy, order, lawyerCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<StockPriceEntity>>> getStocksByAccountant(String baseIndex, String sortBy, String order, String accountantCode, int pageOffset) {
        return mHttpHelper.getStocksByAccountant(baseIndex, sortBy, order, accountantCode, pageOffset);
    }

    @Override
    public Flowable<ApiResponse<List<Top5Entity>>> getTopDeals(String stockCode) {
        return mHttpHelper.getTopDeals(stockCode);
    }

    @Override
    public Flowable<TokenEntity> tryLogin(String userName, String password) {
        return mHttpHelper.tryLogin(userName, password);
    }

    @Override
    public Flowable<UserEntity> updateUserInfo(String token) {
        return mHttpHelper.updateUserInfo(token);
    }

    @Override
    public Flowable<TokenEntity> tryRegister(String userName, String password, String confirmPassword) {
        return mHttpHelper.tryRegister(userName, password, confirmPassword);
    }

    @Override
    public Flowable<CaptchaEntity> getCaptcha() {
        return mHttpHelper.getCaptcha();
    }

    @Override
    public Flowable<captchaAuthEntity> captchaAuth(String response, String hashkey) {
        return mHttpHelper.captchaAuth(response, hashkey);
    }

    @Override
    public Flowable<HashKeyEntity> passwordChange(String token,String newPassword1, String newPassword2) {
        return mHttpHelper.passwordChange(token,newPassword1, newPassword2);
    }

    @Override
    public Flowable<HashKeyEntity> smsCaptcha(String phoneNum) {
        return mHttpHelper.smsCaptcha(phoneNum);
    }

    @Override
    public Flowable<captchaAuthEntity> smsCaptchaAuth(String phoneNum, String hashkey) {
        return mHttpHelper.smsCaptchaAuth(phoneNum, hashkey);
    }

    @Override
    public Flowable<HashKeyEntity> getAvatar(String token,MultipartBody.Part file) {
        return mHttpHelper.getAvatar(token,file);
    }

    @Override
    public Flowable<HashKeyEntity> avatarUpload(String token, RequestBody file) {
        return mHttpHelper.avatarUpload(token,file);
    }

    @Override
    public Flowable<WeatherEntity<H3>> weatherData(String appKey, String area) {
        return mHttpHelper.weatherData(appKey,area);
    }


    public Flowable<UserEntity> getUserInfo() {
        if (isLogin()) {
            String token = mUserPreferenceHelper.getToken();
            return updateUserInfo("token " + token);
        }
        return Flowable.empty();
    }

    public boolean isLogin() {
        String token = mUserPreferenceHelper.getToken();
        Log.d("DataManager ", "got token: " + token);
        return (!TextUtils.isEmpty(token) && token.length() > 39);
    }

    public UserEntity getUserEntityFromPrefs() {
        return mUserPreferenceHelper.getCurrentUserInfo();
    }

    public void logout() {
        mUserPreferenceHelper.clearUserInfo();
    }

    public void updateUserPrefs(UserEntity userInfo) {
        mUserPreferenceHelper.updateUserInfo(userInfo);
    }

    public void updateLogin(String userName, String token) {
        mUserPreferenceHelper.setName(userName);
        mUserPreferenceHelper.setToken(token);
    }

}
