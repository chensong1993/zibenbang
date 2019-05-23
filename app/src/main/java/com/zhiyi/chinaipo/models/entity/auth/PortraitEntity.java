package com.zhiyi.chinaipo.models.entity.auth;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author chensong
 * @date 2018/11/7 9:32
 */
@Entity
public class PortraitEntity {

    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "TOKEN")
    private String token;
    @Property(nameInDb = "PORTRAIT")
    private String portrait;
    @Generated(hash = 1204928369)
    public PortraitEntity(Long _id, String token, String portrait) {
        this._id = _id;
        this.token = token;
        this.portrait = portrait;
    }
    @Generated(hash = 348598422)
    public PortraitEntity() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getPortrait() {
        return this.portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }

}
