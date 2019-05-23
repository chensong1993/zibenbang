package com.zhiyi.chinaipo.models.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.app.Constants;
import com.zhiyi.chinaipo.models.entity.UserEntity;

import javax.inject.Inject;

public class ImplPreferencesHelper implements PreferencesHelper, UserPreferencesHelper {

    private static final boolean DEFAULT_NO_IMAGE = false;
    private static final boolean DEFAULT_AUTO_SAVE = true;
    private static final boolean DEFAULT_LIKE_POINT = false;
    private static final boolean DEFAULT_VERSION_POINT = false;
    private static final boolean DEFAULT_MANAGER_POINT = false;
    private static final String DEFAULT_STRING_VALUE = "未知";

    private static final int DEFAULT_CURRENT_ITEM = Constants.FINANCE_LIST_CONTENT_ALL;

    private static final String SHAREDPREFERENCES_NAME = "chinaipo_sp";
    private final SharedPreferences mSPrefs;

    @Inject
    public ImplPreferencesHelper() {
        mSPrefs = App.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getNoImageState() {
        return mSPrefs.getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    @Override
    public void setNoImageState(boolean state) {
        mSPrefs.edit().putBoolean(Constants.SP_NO_IMAGE, state).apply();
    }

    @Override
    public boolean getAutoCacheState() {
        return mSPrefs.getBoolean(Constants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mSPrefs.edit().putBoolean(Constants.SP_AUTO_CACHE, state).apply();
    }

    @Override
    public int getCurrentItem() {
        return mSPrefs.getInt(Constants.SP_CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
    }

    @Override
    public void setCurrentItem(int item) {
        mSPrefs.edit().putInt(Constants.SP_CURRENT_ITEM, item).apply();
    }

    @Override
    public boolean getLikePoint() {
        return mSPrefs.getBoolean(Constants.SP_LIKE_POINT, DEFAULT_LIKE_POINT);
    }

    @Override
    public void setLikePoint(boolean isFirst) {
        mSPrefs.edit().putBoolean(Constants.SP_LIKE_POINT, isFirst).apply();
    }

    @Override
    public boolean getVersionPoint() {
        return mSPrefs.getBoolean(Constants.SP_VERSION_POINT, DEFAULT_VERSION_POINT);
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        mSPrefs.edit().putBoolean(Constants.SP_VERSION_POINT, isFirst).apply();
    }

    @Override
    public boolean getManagerPoint() {
        return mSPrefs.getBoolean(Constants.SP_MANAGER_POINT, DEFAULT_MANAGER_POINT);
    }

    @Override
    public void setManagerPoint(boolean isFirst) {
        mSPrefs.edit().putBoolean(Constants.SP_MANAGER_POINT, isFirst).apply();
    }

    // User preferences
    @Override
    public String getName() {
        return mSPrefs.getString(Constants.SP_USER_INFO_NAME, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getId() {
        return mSPrefs.getString(Constants.SP_USER_INFO_ID, DEFAULT_STRING_VALUE);
    }

    @Override
    public String getPassword() {
        return mSPrefs.getString(Constants.SP_USER_INFO_PASSWORD, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getToken() {
        return mSPrefs.getString(Constants.SP_USER_INFO_TOKEN, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getAvatar() {
        return mSPrefs.getString(Constants.SP_USER_INFO_AVATAR, "http://www.example.com/");

    }

    @Override
    public String getSlug() {
        return mSPrefs.getString(Constants.SP_USER_INFO_SLUG, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getDesc() {
        return mSPrefs.getString(Constants.SP_USER_INFO_DESC, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getPhoto() {
        return mSPrefs.getString(Constants.SP_USER_INFO_PHOTO, "http://www.example.com/");

    }

    @Override
    public String getMobile() {
        return mSPrefs.getString(Constants.SP_USER_INFO_MOBILE, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getEmail() {
        return mSPrefs.getString(Constants.SP_USER_INFO_EMAIL, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getCompany() {
        return mSPrefs.getString(Constants.SP_USER_INFO_COMPANY, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getDepartment() {
        return mSPrefs.getString(Constants.SP_USER_INFO_DEPARTMENT, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getClassify() {
        return mSPrefs.getString(Constants.SP_USER_INFO_CLASSIFY, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getFocus() {
        return mSPrefs.getString(Constants.SP_USER_INFO_FOCUS, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getArea() {
        return mSPrefs.getString(Constants.SP_USER_INFO_AREA, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getQQToken() {
        return mSPrefs.getString(Constants.SP_USER_INFO_QQ_TOKEN, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getWBToken() {
        return mSPrefs.getString(Constants.SP_USER_INFO_WB_TOKEN, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getWXToken() {
        return mSPrefs.getString(Constants.SP_USER_INFO_WX_TOKEN, DEFAULT_STRING_VALUE);

    }

    @Override
    public String getStocks() {
        return mSPrefs.getString(Constants.SP_USER_INFO_STOCKS, "");
    }

    @Override
    public UserEntity getCurrentUserInfo() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(mSPrefs.getString(Constants.SP_USER_INFO_NAME, ""));
       // userEntity.setPassword(mSPrefs.getString(Constants.SP_USER_INFO_PASSWORD, DEFAULT_STRING_VALUE));
      //  userEntity.setToken(mSPrefs.getString(Constants.SP_USER_INFO_TOKEN, DEFAULT_STRING_VALUE));
        userEntity.setAvatar(mSPrefs.getString(Constants.SP_USER_INFO_AVATAR, "http://www.example.com/"));
        userEntity.setFullname(mSPrefs.getString(Constants.SP_USER_INFO_FULLNAME, DEFAULT_STRING_VALUE));
        userEntity.setDescription(mSPrefs.getString(Constants.SP_USER_INFO_DESC, DEFAULT_STRING_VALUE));
        userEntity.setPhone(mSPrefs.getString(Constants.SP_USER_INFO_PHOTO, "http://www.example.com/"));
        userEntity.setMobile(mSPrefs.getString(Constants.SP_USER_INFO_MOBILE, DEFAULT_STRING_VALUE));
        userEntity.setEmail(mSPrefs.getString(Constants.SP_USER_INFO_EMAIL, DEFAULT_STRING_VALUE));
        userEntity.setCompany(mSPrefs.getString(Constants.SP_USER_INFO_COMPANY, DEFAULT_STRING_VALUE));
        userEntity.setDepartment(mSPrefs.getString(Constants.SP_USER_INFO_DEPARTMENT, DEFAULT_STRING_VALUE));
        userEntity.setClassify(mSPrefs.getString(Constants.SP_USER_INFO_CLASSIFY, DEFAULT_STRING_VALUE));
        userEntity.setFocus(mSPrefs.getString(Constants.SP_USER_INFO_FOCUS, DEFAULT_STRING_VALUE));
        userEntity.setArea(mSPrefs.getString(Constants.SP_USER_INFO_AREA, DEFAULT_STRING_VALUE));
        userEntity.setQq_token(mSPrefs.getString(Constants.SP_USER_INFO_QQ_TOKEN, DEFAULT_STRING_VALUE));
        userEntity.setWeibo_token(mSPrefs.getString(Constants.SP_USER_INFO_WB_TOKEN, DEFAULT_STRING_VALUE));
        userEntity.setWeixin_token(mSPrefs.getString(Constants.SP_USER_INFO_WX_TOKEN, DEFAULT_STRING_VALUE));
        userEntity.setStocks(mSPrefs.getString(Constants.SP_USER_INFO_STOCKS, ""));
        return userEntity;
    }

    @Override
    public void clearUserInfo() {
        SharedPreferences.Editor editor = mSPrefs.edit();
        editor.putString(Constants.SP_USER_INFO_NAME, "").commit();
        editor.putString(Constants.SP_USER_INFO_TOKEN, "").commit();
        editor.putString(Constants.SP_USER_INFO_PASSWORD, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_TOKEN, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_AVATAR, "http://www.example.com/");
        editor.putString(Constants.SP_USER_INFO_FULLNAME, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_DESC, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_PHOTO, "http://www.example.com/");
        editor.putString(Constants.SP_USER_INFO_MOBILE, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_EMAIL, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_COMPANY, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_DEPARTMENT, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_CLASSIFY, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_FOCUS, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_AREA, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_QQ_TOKEN, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_WB_TOKEN, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_WX_TOKEN, DEFAULT_STRING_VALUE);
        editor.putString(Constants.SP_USER_INFO_STOCKS, "");
        editor.apply();
    }

    @Override
    public void updateUserInfo(UserEntity userEntity) {
        SharedPreferences.Editor editor = mSPrefs.edit();
        editor.putString(Constants.SP_USER_INFO_NAME, userEntity.getName());
        //editor.putString(Constants.SP_USER_INFO_PASSWORD, userEntity.getPassword());
        editor.putString(Constants.SP_USER_INFO_AVATAR, userEntity.getAvatar());
        editor.putString(Constants.SP_USER_INFO_FULLNAME, userEntity.getFullname());
        editor.putString(Constants.SP_USER_INFO_DESC, userEntity.getDescription());
        editor.putString(Constants.SP_USER_INFO_PHOTO, userEntity.getPhoto());
        editor.putString(Constants.SP_USER_INFO_MOBILE, userEntity.getMobile());
        editor.putString(Constants.SP_USER_INFO_EMAIL, userEntity.getEmail());
        editor.putString(Constants.SP_USER_INFO_COMPANY, userEntity.getEmail());
        editor.putString(Constants.SP_USER_INFO_DEPARTMENT, userEntity.getDepartment());
        editor.putString(Constants.SP_USER_INFO_CLASSIFY, userEntity.getClassify());
        editor.putString(Constants.SP_USER_INFO_FOCUS, userEntity.getFocus());
        editor.putString(Constants.SP_USER_INFO_AREA, userEntity.getArea());
        editor.putString(Constants.SP_USER_INFO_QQ_TOKEN, userEntity.getQq_token());
        editor.putString(Constants.SP_USER_INFO_WB_TOKEN, userEntity.getWeibo_token());
        editor.putString(Constants.SP_USER_INFO_WX_TOKEN, userEntity.getWeixin_token());
        editor.putString(Constants.SP_USER_INFO_STOCKS, userEntity.getStocks());
        editor.apply();
    }

    @Override
    public void setName(String name) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_NAME, name).apply();
    }

    @Override
    public void setId(String id) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_ID, id).apply();
    }

    @Override
    public void setPassword(String password) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_PASSWORD, password).apply();
    }

    @Override
    public void setToken(String userToken) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_TOKEN, userToken).apply();
    }

    @Override
    public void setAvatar(String avatarUrl) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_AVATAR, avatarUrl).apply();
    }

    @Override
    public void setSlug(String slug) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_SLUG, slug).apply();
    }

    @Override
    public void setDesc(String desc) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_DESC, desc).apply();
    }

    @Override
    public void setPhoto(String photoUrl) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_PHOTO, photoUrl).apply();
    }

    @Override
    public void setMobile(String mobile) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_MOBILE, mobile).apply();
    }

    @Override
    public void setEmail(String email) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_EMAIL, email).apply();
    }

    @Override
    public void setCompany(String company) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_COMPANY, company).apply();
    }

    @Override
    public void setDepartment(String department) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_DEPARTMENT, department).apply();
    }

    @Override
    public void setClassify(String classify) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_CLASSIFY, classify).apply();
    }

    @Override
    public void setFocus(String focused) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_FOCUS, focused).apply();
    }

    @Override
    public void setArea(String areaString) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_AREA, areaString).apply();
    }

    @Override
    public void setQQToken(String qqToken) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_QQ_TOKEN, qqToken).apply();
    }

    @Override
    public void setWBToken(String wbToken) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_WB_TOKEN, wbToken).apply();
    }

    @Override
    public void setWXToken(String wxToken) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_WX_TOKEN, wxToken).apply();
    }

    @Override
    public void setStocks(String stocksList) {
        mSPrefs.edit().putString(Constants.SP_USER_INFO_STOCKS, stocksList).apply();
    }
}
