package com.zhiyi.chinaipo.models.entity.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TokenEntity implements Serializable {
    @SerializedName("key")
    private String userToken;


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
