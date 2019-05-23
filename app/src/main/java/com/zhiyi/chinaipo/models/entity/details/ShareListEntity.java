package com.zhiyi.chinaipo.models.entity.details;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShareListEntity implements Serializable {

    private String declaredate;
    private String ex_divi_date;

    @SerializedName("chng_reas_code")
    private int changeResone;

    private int shr_hld_type;
    private String total;
    private Double total_holders;
    private String out_shr;
    private String tot_ltdfl;
    private String res_mng_nonfl;
    private String cis_cdd;


    public String getDeclaredate() {
        return declaredate;
    }

    public void setDeclaredate(String declaredate) {
        this.declaredate = declaredate;
    }

    public String getEx_divi_date() {
        return ex_divi_date;
    }

    public void setEx_divi_date(String ex_divi_date) {
        this.ex_divi_date = ex_divi_date;
    }

    public int getShr_hld_type() {
        return shr_hld_type;
    }

    public void setShr_hld_type(int shr_hld_type) {
        this.shr_hld_type = shr_hld_type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Double getTotal_holders() {
        return total_holders;
    }

    public void setTotal_holders(Double total_holders) {
        this.total_holders = total_holders;
    }

    public String getOut_shr() {
        return out_shr;
    }

    public void setOut_shr(String out_shr) {
        this.out_shr = out_shr;
    }

    public String getTot_ltdfl() {
        return tot_ltdfl;
    }

    public void setTot_ltdfl(String tot_ltdfl) {
        this.tot_ltdfl = tot_ltdfl;
    }

    public String getRes_mng_nonfl() {
        return res_mng_nonfl;
    }

    public void setRes_mng_nonfl(String res_mng_nonfl) {
        this.res_mng_nonfl = res_mng_nonfl;
    }

    public String getCis_cdd() {
        return cis_cdd;
    }

    public void setCis_cdd(String cis_cdd) {
        this.cis_cdd = cis_cdd;
    }

    public int getChangeResone() {
        return changeResone;
    }

    public void setChangeResone(int changeResone) {
        this.changeResone = changeResone;
    }
}
