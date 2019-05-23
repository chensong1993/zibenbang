package com.zhiyi.chinaipo.models.prefs;

import com.zhiyi.chinaipo.models.entity.UserEntity;

public interface UserPreferencesHelper {

    String getName();
    String getId();
    String getPassword();
    String getToken();
    String getAvatar();
    String getSlug();
    String getDesc();
    String getPhoto();
    String getMobile();
    String getEmail();
    String getCompany();
    String getDepartment();
    String getClassify();
    String getFocus();
    String getArea();
    String getQQToken();
    String getWBToken();
    String getWXToken();
    String getStocks();

    UserEntity getCurrentUserInfo();
    void clearUserInfo();
    void updateUserInfo(UserEntity userEntity);

    void setName(String theValue);
    void setId(String theValue);
    void setPassword(String theValue);
    void setToken(String theValue);
    void setAvatar(String theValue);
    void setSlug(String theValue);
    void setDesc(String theValue);
    void setPhoto(String theValue);
    void setMobile(String theValue);
    void setEmail(String theValue);
    void setCompany(String theValue);
    void setDepartment(String theValue);
    void setClassify(String theValue);
    void setFocus(String theValue);
    void setArea(String theValue);
    void setQQToken(String theValue);
    void setWBToken(String theValue);
    void setWXToken(String theValue);
    void setStocks(String theValue);
}
