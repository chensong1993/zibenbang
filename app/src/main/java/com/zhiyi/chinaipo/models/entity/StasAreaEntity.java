package com.zhiyi.chinaipo.models.entity;

public class StasAreaEntity {
    private int area_code;
    private String area_name;
    private int total_company;
    private Double percentage;
    private String updated_at;

    public int getArea_code() {
        return area_code;
    }

    public void setArea_code(int area_code) {
        this.area_code = area_code;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public int getTotal_company() {
        return total_company;
    }

    public void setTotal_company(int total_company) {
        this.total_company = total_company;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
