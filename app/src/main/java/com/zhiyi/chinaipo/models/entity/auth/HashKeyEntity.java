package com.zhiyi.chinaipo.models.entity.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HashKeyEntity {
    private String hashkey;

    public void setHashkey(String hashkey) {
        this.hashkey = hashkey;
    }

    public String getHashkey() {
        return this.hashkey;
    }
}
