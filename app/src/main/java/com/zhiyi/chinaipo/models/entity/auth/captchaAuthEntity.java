package com.zhiyi.chinaipo.models.entity.auth;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class captchaAuthEntity implements Serializable {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
