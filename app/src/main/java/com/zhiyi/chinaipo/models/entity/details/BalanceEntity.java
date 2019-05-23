package com.zhiyi.chinaipo.models.entity.details;


public class BalanceEntity {
    private String enddate;
    private Integer reporttype;
    private Double value_each_share;
    private Double profit_ratio_value;
    private Double liquid_assets;
    private Double fixed_assets;
    private Double total_value;
    private Double liquid_debt;
    private Double fixed_debt;
    private Double total_debt;
    private Double undistributed_profit;
    private Double public_reserve_fund;
    private Double holder_interest;

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Double getValue_each_share() {
        return value_each_share;
    }

    public void setValue_each_share(Double value_each_share) {
        this.value_each_share = value_each_share;
    }

    public Double getProfit_ratio_value() {
        return profit_ratio_value;
    }

    public void setProfit_ratio_value(Double profit_ratio_value) {
        this.profit_ratio_value = profit_ratio_value;
    }

    public Double getLiquid_assets() {
        return liquid_assets;
    }

    public void setLiquid_assets(Double liquid_assets) {
        this.liquid_assets = liquid_assets;
    }

    public Double getFixed_assets() {
        return fixed_assets;
    }

    public void setFixed_assets(Double fixed_assets) {
        this.fixed_assets = fixed_assets;
    }

    public Double getTotal_value() {
        return total_value;
    }

    public void setTotal_value(Double total_value) {
        this.total_value = total_value;
    }

    public Double getLiquid_debt() {
        return liquid_debt;
    }

    public void setLiquid_debt(Double liquid_debt) {
        this.liquid_debt = liquid_debt;
    }

    public Double getFixed_debt() {
        return fixed_debt;
    }

    public void setFixed_debt(Double fixed_debt) {
        this.fixed_debt = fixed_debt;
    }

    public Double getTotal_debt() {
        return total_debt;
    }

    public void setTotal_debt(Double total_debt) {
        this.total_debt = total_debt;
    }

    public Double getUndistributed_profit() {
        return undistributed_profit;
    }

    public void setUndistributed_profit(Double undistributed_profit) {
        this.undistributed_profit = undistributed_profit;
    }

    public Double getHolder_interest() {
        return holder_interest;
    }

    public void setHolder_interest(Double holder_interest) {
        this.holder_interest = holder_interest;
    }

    public Double getPublic_reserve_fund() {
        return public_reserve_fund;
    }

    public void setPublic_reserve_fund(Double public_reserve_fund) {
        this.public_reserve_fund = public_reserve_fund;
    }

    public Integer getReporttype() {
        return reporttype;
    }

    public void setReporttype(Integer reporttype) {
        this.reporttype = reporttype;
    }
}
