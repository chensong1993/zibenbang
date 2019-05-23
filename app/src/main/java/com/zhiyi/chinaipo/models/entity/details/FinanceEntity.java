package com.zhiyi.chinaipo.models.entity.details;


public class FinanceEntity {

    private String declaredate;
    private String rpt_date;
    private Integer rpt_src; // 2 中报 ， 4年报
    private Integer rpt_type;
    private String net_pro_aft_ei; //扣除非经常损益后的净利润
    private String epsp; //每股收益
    private String epsfd; //稀释每股收益
    private String di_epsp_aft_ei;
    private String di_epsfd_aft_ei;
    private String wa_roe; //净资产收益率
    private String wa_roe_aft_ei;
    private String bps; //每股净资产
    private String ps_net_val; //每股现金流量净额
    private Double operation_income;
    private Double operation_income_change;
    private Double net_profit;
    private Double net_profit_change;
    private Double net_pro_aft_ei_change;

    public FinanceEntity(String declaredate) {
        this.declaredate = declaredate;
    }

    public String getDeclaredate() {
        return declaredate;
    }

    public void setDeclaredate(String declaredate) {
        this.declaredate = declaredate;
    }

    public Integer getRpt_src() {
        return rpt_src;
    }

    public void setRpt_src(Integer rpt_src) {
        this.rpt_src = rpt_src;
    }

    public Integer getRpt_type() {
        return rpt_type;
    }

    public void setRpt_type(Integer rpt_type) {
        this.rpt_type = rpt_type;
    }

    public String getNet_pro_aft_ei() {
        return net_pro_aft_ei;
    }

    public void setNet_pro_aft_ei(String net_pro_aft_ei) {
        this.net_pro_aft_ei = net_pro_aft_ei;
    }

    public String getEpsp() {
        return epsp;
    }

    public void setEpsp(String epsp) {
        this.epsp = epsp;
    }

    public String getEpsfd() {
        return epsfd;
    }

    public void setEpsfd(String epsfd) {
        this.epsfd = epsfd;
    }

    public String getDi_epsp_aft_ei() {
        return di_epsp_aft_ei;
    }

    public void setDi_epsp_aft_ei(String di_epsp_aft_ei) {
        this.di_epsp_aft_ei = di_epsp_aft_ei;
    }

    public String getDi_epsfd_aft_ei() {
        return di_epsfd_aft_ei;
    }

    public void setDi_epsfd_aft_ei(String di_epsfd_aft_ei) {
        this.di_epsfd_aft_ei = di_epsfd_aft_ei;
    }

    public String getWa_roe() {
        return wa_roe;
    }

    public void setWa_roe(String wa_roe) {
        this.wa_roe = wa_roe;
    }

    public String getWa_roe_aft_ei() {
        return wa_roe_aft_ei;
    }

    public void setWa_roe_aft_ei(String wa_roe_aft_ei) {
        this.wa_roe_aft_ei = wa_roe_aft_ei;
    }

    public String getBps() {
        return bps;
    }

    public void setBps(String bps) {
        this.bps = bps;
    }

    public String getPs_net_val() {
        return ps_net_val;
    }

    public void setPs_net_val(String ps_net_val) {
        this.ps_net_val = ps_net_val;
    }

    public Double getOperation_income() {
        return operation_income;
    }

    public void setOperation_income(Double operation_income) {
        this.operation_income = operation_income;
    }

    public Double getOperation_income_change() {
        return operation_income_change;
    }

    public void setOperation_income_change(Double operation_income_change) {
        this.operation_income_change = operation_income_change;
    }

    public Double getNet_profit() {
        return net_profit;
    }

    public void setNet_profit(Double net_profit) {
        this.net_profit = net_profit;
    }

    public Double getNet_profit_change() {
        return net_profit_change;
    }

    public void setNet_profit_change(Double net_profit_change) {
        this.net_profit_change = net_profit_change;
    }

    public Double getNet_pro_aft_ei_change() {
        return net_pro_aft_ei_change;
    }

    public void setNet_pro_aft_ei_change(Double net_pro_aft_ei_change) {
        this.net_pro_aft_ei_change = net_pro_aft_ei_change;
    }

    public String getRpt_date() {
        return rpt_date;
    }

    public void setRpt_date(String rpt_date) {
        this.rpt_date = rpt_date;
    }
}
