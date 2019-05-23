package com.zhiyi.chinaipo.models.entity.auth;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author chensong
 * @date 2018/11/7 14:13
 */
@Entity
public class NicknameEntity {

    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "TOKEN")
    private String token;
    @Property(nameInDb = "NICKNAME")
    private String nickneme;
    @Generated(hash = 1276148513)
    public NicknameEntity(Long _id, String token, String nickneme) {
        this._id = _id;
        this.token = token;
        this.nickneme = nickneme;
    }
    @Generated(hash = 206191062)
    public NicknameEntity() {
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
    public String getNickneme() {
        return this.nickneme;
    }
    public void setNickneme(String nickneme) {
        this.nickneme = nickneme;
    }
}
