package com.zhiyi.chinaipo.models.entity;


public class MarketIndexEntity {
    private String stockCode;
    private String indx_sname;
    private String tradedate;
    private String isvalid;
    private String lclose;
    private String topen;
    private String tclose;
    private String thigh;
    private String tlow;
    private String chng;
    private String chng_pct;
    private String tvolume;
    private String tvalue;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getLclose() {
        return lclose;
    }

    public void setLclose(String lclose) {
        this.lclose = lclose;
    }

    public String getTopen() {
        return topen;
    }

    public void setTopen(String topen) {
        this.topen = topen;
    }

    public String getTclose() {
        return tclose;
    }

    public void setTclose(String tclose) {
        this.tclose = tclose;
    }

    public String getThigh() {
        return thigh;
    }

    public void setThigh(String thigh) {
        this.thigh = thigh;
    }

    public String getTlow() {
        return tlow;
    }

    public void setTlow(String tlow) {
        this.tlow = tlow;
    }

    public String getChng() {
        return chng;
    }

    public void setChng(String chng) {
        this.chng = chng;
    }

    public String getChng_pct() {
        return chng_pct;
    }

    public void setChng_pct(String chng_pct) {
        this.chng_pct = chng_pct;
    }

    public String getTvolume() {
        return tvolume;
    }

    public void setTvolume(String tvolume) {
        this.tvolume = tvolume;
    }

    public String getTvalue() {
        return tvalue;
    }

    public void setTvalue(String tvalue) {
        this.tvalue = tvalue;
    }

    public String getIndx_sname() {
        return indx_sname;
    }

    public void setIndx_sname(String indx_sname) {
        this.indx_sname = indx_sname;
    }
}
