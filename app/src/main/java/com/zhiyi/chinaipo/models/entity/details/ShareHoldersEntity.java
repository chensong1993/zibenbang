package com.zhiyi.chinaipo.models.entity.details;


public class ShareHoldersEntity {
    private String declaredate;
    private String enddate;
    private int hld_type;
    private int shr_hld_type;
    private int rank;
    private String hold_num;
    private String hold_pct;
    private String ltd_shr_num;
    private String unli_shr_num;
    private String out_shr;
    private String hold_name;
    private String sec_type;
    private Double change;

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

    public int getHld_type() {
        return hld_type;
    }

    public void setHld_type(int hld_type) {
        this.hld_type = hld_type;
    }

    public int getShr_hld_type() {
        return shr_hld_type;
    }

    public void setShr_hld_type(int shr_hld_type) {
        this.shr_hld_type = shr_hld_type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getHold_num() {
        return hold_num;
    }

    public void setHold_num(String hold_num) {
        this.hold_num = hold_num;
    }

    public String getHold_pct() {
        return hold_pct;
    }

    public void setHold_pct(String hold_pct) {
        this.hold_pct = hold_pct;
    }

    public String getLtd_shr_num() {
        return ltd_shr_num;
    }

    public void setLtd_shr_num(String ltd_shr_num) {
        this.ltd_shr_num = ltd_shr_num;
    }

    public String getUnli_shr_num() {
        return unli_shr_num;
    }

    public void setUnli_shr_num(String unli_shr_num) {
        this.unli_shr_num = unli_shr_num;
    }

    public String getOut_shr() {
        return out_shr;
    }

    public void setOut_shr(String out_shr) {
        this.out_shr = out_shr;
    }

    public String getHold_name() {
        return hold_name;
    }

    public void setHold_name(String hold_name) {
        this.hold_name = hold_name;
    }

    public String getSec_type() {
        return sec_type;
    }

    public void setSec_type(String sec_type) {
        this.sec_type = sec_type;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }
}
