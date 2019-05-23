package com.zhiyi.chinaipo.models.entity;

public class StasOrgsEntity {
    private int org_code;
    private String org_name;
    private int total_company;
    private Double percentage;


    public int getTotal_company() {
        return total_company;
    }

    public void setTotal_company(int total_company) {
        this.total_company = total_company;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double  percentage) {
        this.percentage = percentage;
    }

    public int getOrg_code() {
        return org_code;
    }

    public void setOrg_code(int org_code) {
        this.org_code = org_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
