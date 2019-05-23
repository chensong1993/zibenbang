package com.zhiyi.chinaipo.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    //================= REGISTER_MESSAGES ====================
    public static final String REGISTER_MESSAGES_TOKEN = "register_token";
    public static final String REGISTER_MESSAGES_USER = "register_user";
    public static final String REGISTER_MESSAGES_NICKNAME = "register_nickname";
    public static final String REGISTER_MESSAGES_ICON = "register_icon";
    public static final String REGISTER_MESSAGES_LOGIN = "register_login";
    public static final String REGISTER_MESSAGES_STOCK = "register_stock";
    //================= STOCK_SORT_KEY ====================

    public static final String STOCK_SORT_BY_PRICE = "latest_price";
    public static final String STOCK_SORT_BY_CHNG_PCT = "chng_pct";
    public static final String STOCK_SORT_BY_LATEST_TURNOVER = "latest_turnover";
    public static final String STOCK_SORT_BY_LATEST_VOLUME = "latest_volume";
    public static final String STOCK_SORT_BY_CHNG = "chng";
    public static final String STOCK_SORT_BY_INCOME = "total_income";
    public static final String STOCK_SORT_BY_NET_PROFIT = "net_profit";
    public static final String STOCK_SORT_BY_SHARES_FLOW = "shares_flow";
    public static final String STOCK_SORT_BY_VALUES_FLOW = "values_flow";
    public static final String STOCK_SORT_BY_TVOLUME = "total_volume";
    public static final String STOCK_SORT_BY_TVALUE = "total_value";
    public static final String STOCK_SORT_BY_PE_RATIO = "pe_ratio";
    public static final String STOCK_SORT_BY_PROFIT_EACH_SHARE = "profit_each_share";
    public static final String STOCK_SORT_BY_HIGH_PRICE = "highest_price";
    public static final String STOCK_SORT_BY_LOW_PRICE = "lowest_price";
    public static final String STOCK_SORT_BY_AVG_PRICE = "avg_price";
    public static final String STOCK_SORT_BY_SWG = "swg";

    public static final String STOCK_SORT_ORDER_ASC = "asc";
    public static final String STOCK_SORT_ORDER_DES = "des";

    public static final String[] STOCK_BASE_INDEX_ARRAY = {"all", "innovate", "base", "project", "contract", "transfer"};
    public static final String[] STOCK_SORT_ORDER_ARRAY = {
            STOCK_SORT_BY_PRICE,
            STOCK_SORT_BY_CHNG_PCT,
            STOCK_SORT_BY_LATEST_TURNOVER,
            STOCK_SORT_BY_LATEST_VOLUME,
            STOCK_SORT_BY_CHNG,
            STOCK_SORT_BY_INCOME,
            STOCK_SORT_BY_NET_PROFIT,
            STOCK_SORT_BY_SHARES_FLOW,
            STOCK_SORT_BY_VALUES_FLOW,
            STOCK_SORT_BY_TVOLUME,
            STOCK_SORT_BY_TVALUE,
            STOCK_SORT_BY_PE_RATIO,
            STOCK_SORT_BY_PROFIT_EACH_SHARE,
            STOCK_SORT_BY_HIGH_PRICE,
            STOCK_SORT_BY_LOW_PRICE,
            STOCK_SORT_BY_AVG_PRICE,
            STOCK_SORT_BY_SWG
    };
    //================= KEY ====================

    //    public static final String KEY_API = "f95283476506aa756559dd28a56f0c0b";
    public static final String KEY_API = "52b7ec3471ac3bec6846577e79f20e4c";

    public static final String BUGLY_ID = "257700f3f8";

    //================= PATH ====================
    //okhttp缓存路径
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=360";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "chinaipo" + File.separator + "StockNews";

    //================= PREFERENCE ====================

    public static final String SP_NO_IMAGE = "no_image";
    public static final String SP_AUTO_CACHE = "auto_cache";
    public static final String SP_CURRENT_ITEM = "current_item";
    public static final String SP_LIKE_POINT = "like_point";
    public static final String SP_VERSION_POINT = "version_point";
    public static final String SP_MANAGER_POINT = "manager_point";

    public static final String SP_USER_INFO_NAME = "current_user_name";
    public static final String SP_USER_INFO_PASSWORD = "current_user_password";
    public static final String SP_USER_INFO_TOKEN = "current_user_token";
    public static final String SP_USER_INFO_ID = "current_user_id";
    public static final String SP_USER_INFO_FULLNAME = "current_user_fullname";
    public static final String SP_USER_INFO_SLUG = "current_user_slug";
    public static final String SP_USER_INFO_DESC = "current_user_desc";
    public static final String SP_USER_INFO_PHOTO = "current_user_photo";
    public static final String SP_USER_INFO_AVATAR = "current_user_avatar";
    public static final String SP_USER_INFO_MOBILE = "current_user_mobile";
    public static final String SP_USER_INFO_EMAIL = "current_user_email";
    public static final String SP_USER_INFO_COMPANY = "current_user_company";
    public static final String SP_USER_INFO_DEPARTMENT = "current_user_department";
    public static final String SP_USER_INFO_CLASSIFY = "current_user_classify";
    public static final String SP_USER_INFO_FOCUS = "current_user_focus";
    public static final String SP_USER_INFO_AREA = "current_user_area";
    public static final String SP_USER_INFO_QQ_TOKEN = "current_user_qq_token";
    public static final String SP_USER_INFO_WB_TOKEN = "current_user_wb_token";
    public static final String SP_USER_INFO_WX_TOKEN = "current_user_wx_token";
    public static final String SP_USER_INFO_STOCKS = "current_user_stocks";


    //================= INTENT ====================


    public static final String APK_DOWNLOAD_NEWEST_URL = "http://api.chinaipo.com/media/app-release.apk";
    public static final String APK_DOWNLOAD_URL = "http://api.chinaipo.com/media/app-latest.apk";
    public static final String CHINAIPO_API_HOST = "www.chinaipo.com";
    public static final String CHINAIPO_3G_NEWS_URL = "http://m.chinaipo.com/news/";
    public static final String CHINAIPO_SHARESTOCK_URL="http://m.chinaipo.com/stock/";
    public static final String CHINAIPO_COMMON_API_URL = "http://api.chinaipo.com/";
    public static final String CHINAIPO_NEWS_API_URL = "http://api.chinaipo.com/api/";
    public static final String CHINAIPO_STOCK_API_URL = "http://api.chinaipo.com/markets/v1/";
    public static final String CHINAIPO_WEATHER_API_URL = "http://api.shujuzhihui.cn/api/weather/";
    public static final String CHINAIPO_ABOUT="http://m.chinaipo.com/about";
    public static final String CHINAIPO_BUSINESS="http://m.chinaipo.com/swhz/swhz.html?type=app";
    public static final String CHINAIPO_BRAND="http://m.chinaipo.com/pinpai/?type=app";

    public static final String NEWS_CATEGORY_ID = "chinaipo_news_category_id";
    public static final String NEWS_CATEGORY_NAME = "chinaipo_news_category_name";
    public static final String NEWS_DETAIL_ID = "chinaipo_news_detail_id";
    public static final String NEWS_DETAIL_ORIGINAL_ID = "chinaipo_news_detail_original_id";
    public static final String FRAGMENT_KLINE_TYPE = "chinaipo_stock_fragement_kline_type";
    public static final String LIST_ORGANIZATION_BY_TYPE = "chinaipo_list_organization_type";
    public static final String NEWS_DETAIL_CONTENT = "chinaipo_news_detail_content";

    public static final String PARAMETER_STOCK_CODE = "chinaipo_parameter_stock_code";
    public static final String PARAMETER_STOCK_INDEX = "chinaipo_parameter_stock_index";
    public static final String PARAMETER_LANDSCAPE_STOCK = "chinaipo_landscape_stock";
    public static final String PARAMETER_LANDSCAPE= "landscape_stock";
    public static final String PARAMETER_STOCK_NAME = "chinaipo_parameter_stock_name";
    public static final String PARAMETER_ANNOUNCEMENT_URL = "chinaipo_parameter_announcements_url";
    public static final String PARAMETER_ANNOUNCEMENT_TITLE = "chinaipo_parameter_announcements_title";
    public static final String PARAMETER_STOCK_LIST_BASE_INDEX = "chinaipo_stock_list_base_index";
    public static final String PARAMETER_STOCK_LIST_SORT_BY = "chinaipo_stock_list_sort_by";
    public static final String PARAMETER_STOCK_LIST_EXT_KEY = "chinaipo_stock_list_ext_key";
    public static final String PARAMETER_STOCK_LIST_EXT_VALUE = "chinaipo_stock_list_ext_value";
    public static final String PARAMETER_STOCK_LIST_TITLE = "chinaipo_stock_list_title";
    public static final String PARAMETER_STOCK_LIST_TABTITLE = "chinaipo_stock_list_tabtitle";

    public static final String TIMELINE_PARAMETER_PRE_CLOSE_PRICE = "chinaipo_paramter_stock_last_close_price";

    public static final int FINANCE_LIST_CONTENT_ALL = 0;
    public static final int FINANCE_LIST_CONTENT_SUMMARY = 1;

    public static final String NEWS_LIST_BY_TAG = "chinaipo_news_by_tag";
    public static final int SEARCHING_TYPE_ALL = 0;
    public static final int SEARCHING_TYPE_NEWS = 1;
    public static final int SEARCHING_TYPE_AUTHOR = 2;
    public static final int SEARCHING_TYPE_STOCK = 3;
    public static final String SEARCHING_TYPE_KEY = "chinaipo_searching_by_this";
    public static final String SEARCHING_KEY_USING = "chinaipo_searching_using_key";
    public static final String SEARCHING_TYPE = "chinaipo_searching_type";

    public static final int ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    public static final String GOTO_WEB_TITLE = "chinaipo_web_access_title";
    public static final String GOTO_WEB_URL = "chinaipo_web_url";

    public static final int SHARE_CONTENT_NEWS = 0;
    public static final int SHARE_CONTENT_STOCK = 1;
    public static final long COMMON_COUNT_DOWN_TIME = 60000;
    public static final long AUTH_COUNT_DOWN_TIME = 3000;

    public static final String GUEST_TOKEN_KEY = "guest_token";
    public static final String LOGIN_TOKEN_KEY = "login_token";

    public static int STOCK_DATA_LIST_CONTENT_ALL = 0;
    public static int STOCK_DATA_LIST_SUMMARY = 1;

    public static final String USER_NICKNAME = "user_nickname";
    public static final String TITLE_NAME = "title_name";
    public static final String PROFILE_PARAMETER = "profile_parameter";
    public static final String VIP_MEMBERSHIP = "vip_membership";
    public static final String NET_WORK = "android.net.conn.CONNECTIVITY_CHANGE";
}
