package com.zhiyi.chinaipo.models.entity;


import java.util.List;

public class StockPriceEntity {

    private String stock_code;
    private String stock_name ;
    private String tradedate;
    private String chng ;
    private String chng_pct ;
    private String last_close ;
    private String today_open ;
    private String total_income ;
    private String net_profit ;     // 净利润
    private String latest_price ;     // 最近成交价
    private String latest_volume ;     // 成交数量
    private String latest_turnover ;     // 成交金额
    private String latest_deals ;     // 成交笔数
    private String highest_price ;     // 最高成交价
    private String lowest_price ;     // 最低成交价
    private String static_pe ;     // 市盈率1  静态市盈率
    private String dynamic_pe ;     // 市盈率2  动态市盈率
    private String profit_each_share ;  // 每股盈利
    private String shares_flow ; // 流通股本
    private String values_flow ; // 流通市值
    private String total_volume ;
    private String total_value ;
    private String pe_ratio ;
    private String swg ;
    private String registered_date;
    private List<String> deal_type ;
    private String booked_value ;
    private String avg_price;
    private Boolean is_innovate;
    private Boolean is_indeal;


    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getTradedate() {
        return tradedate;
    }

    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
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

    public String getLast_close() {
        return last_close;
    }

    public void setLast_close(String last_close) {
        this.last_close = last_close;
    }

    public String getToday_open() {
        return today_open;
    }

    public void setToday_open(String today_open) {
        this.today_open = today_open;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }

    public String getNet_profit() {
        return net_profit;
    }

    public void setNet_profit(String net_profit) {
        this.net_profit = net_profit;
    }

    public String getLatest_price() {
        return latest_price;
    }

    public void setLatest_price(String latest_price) {
        this.latest_price = latest_price;
    }

    public String getLatest_volume() {
        return latest_volume;
    }

    public void setLatest_volume(String latest_volume) {
        this.latest_volume = latest_volume;
    }

    public String getLatest_turnover() {
        return latest_turnover;
    }

    public void setLatest_turnover(String latest_turnover) {
        this.latest_turnover = latest_turnover;
    }

    public String getLatest_deals() {
        return latest_deals;
    }

    public void setLatest_deals(String latest_deals) {
        this.latest_deals = latest_deals;
    }

    public String getHighest_price() {
        return highest_price;
    }

    public void setHighest_price(String highest_price) {
        this.highest_price = highest_price;
    }

    public String getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(String lowest_price) {
        this.lowest_price = lowest_price;
    }

    public String getStatic_pe() {
        return static_pe;
    }

    public void setStatic_pe(String static_pe) {
        this.static_pe = static_pe;
    }

    public String getDynamic_pe() {
        return dynamic_pe;
    }

    public void setDynamic_pe(String dynamic_pe) {
        this.dynamic_pe = dynamic_pe;
    }

    public String getProfit_each_share() {
        return profit_each_share;
    }

    public void setProfit_each_share(String profit_each_share) {
        this.profit_each_share = profit_each_share;
    }

    public String getShares_flow() {
        return shares_flow;
    }

    public void setShares_flow(String shares_flow) {
        this.shares_flow = shares_flow;
    }

    public String getValues_flow() {
        return values_flow;
    }

    public void setValues_flow(String values_flow) {
        this.values_flow = values_flow;
    }

    public String getTotal_volume() {
        return total_volume;
    }

    public void setTotal_volume(String total_volume) {
        this.total_volume = total_volume;
    }

    public String getTotal_value() {
        return total_value;
    }

    public void setTotal_value(String total_value) {
        this.total_value = total_value;
    }

    public String getPe_ratio() {
        return pe_ratio;
    }

    public void setPe_ratio(String pe_ratio) {
        this.pe_ratio = pe_ratio;
    }

    public String getSwg() {
        return swg;
    }

    public void setSwg(String swg) {
        this.swg = swg;
    }

    public String getRegistered_date() {
        return registered_date;
    }

    public void setRegistered_date(String registered_date) {
        this.registered_date = registered_date;
    }

    public List<String> getDeal_type() {
        return deal_type;
    }

    public void setDeal_type(List<String> deal_type) {
        this.deal_type = deal_type;
    }

    public String getBooked_value() {
        return booked_value;
    }

    public void setBooked_value(String booked_value) {
        this.booked_value = booked_value;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public Boolean getIs_innovate() {
        return is_innovate;
    }

    public void setIs_innovate(Boolean is_innovate) {
        this.is_innovate = is_innovate;
    }

    public Boolean getIs_indeal() {
        return is_indeal;
    }

    public void setIs_indeal(Boolean is_indeal) {
        this.is_indeal = is_indeal;
    }
}
