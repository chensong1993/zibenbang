package com.zhiyi.chinaipo.models.entity.details;


public class CashFlowEntity {

    private String declaredate;
    private String enddate;
    private Double cash_each_share;
    private Double cash_operation;
    private Double cash_invest;
    private Double cash_raised;
    private Integer reporttype;

    public String getDeclaredate() {
        return declaredate;
    }

    public void setDeclaredate(String declaredate) {
        this.declaredate = declaredate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Double getCash_each_share() {
        return cash_each_share;
    }

    public void setCash_each_share(Double cash_each_share) {
        this.cash_each_share = cash_each_share;
    }

    public Double getCash_operation() {
        return cash_operation;
    }

    public void setCash_operation(Double cash_operation) {
        this.cash_operation = cash_operation;
    }

    public Double getCash_invest() {
        return cash_invest;
    }

    public void setCash_invest(Double cash_invest) {
        this.cash_invest = cash_invest;
    }

    public Double getCash_raised() {
        return cash_raised;
    }

    public void setCash_raised(Double cash_raised) {
        this.cash_raised = cash_raised;
    }

    public Integer getReporttype() {
        return reporttype;
    }

    public void setReporttype(Integer reporttype) {
        this.reporttype = reporttype;
    }
}
