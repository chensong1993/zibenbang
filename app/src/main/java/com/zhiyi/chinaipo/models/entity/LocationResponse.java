package com.zhiyi.chinaipo.models.entity;

import java.io.Serializable;

public class LocationResponse<T> implements Serializable{
    private String address;
    private String status;
    private T content;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
