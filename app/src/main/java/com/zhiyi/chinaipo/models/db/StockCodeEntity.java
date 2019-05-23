package com.zhiyi.chinaipo.models.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author chensong
 * @date 2018/7/23 16:02
 */
@Entity
public class StockCodeEntity {
    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "TOKEN")
    private String token;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "CODE")
    private String code;
    @Property(nameInDb = "PRICE")
    private String price;
    @Property(nameInDb = "PCT")
    private String pct;
    @Property(nameInDb = "TURNOVER")
    private String turnover;

    @Generated(hash = 1675707504)
    public StockCodeEntity(Long _id, String token, String name, String code,
            String price, String pct, String turnover) {
        this._id = _id;
        this.token = token;
        this.name = name;
        this.code = code;
        this.price = price;
        this.pct = pct;
        this.turnover = turnover;
    }
    @Generated(hash = 380558915)
    public StockCodeEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getPct() {
        return this.pct;
    }
    public void setPct(String pct) {
        this.pct = pct;
    }
    public String getTurnover() {
        return this.turnover;
    }
    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }
}
