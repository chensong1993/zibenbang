package com.zhiyi.chinaipo.models.entity.news;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author chensong
 * @date 2018/8/3 15:06
 */
@Entity(nameInDb = "newsSave")
public class NewsSave {
    @Id(autoincrement = true)
    private Long _id;
    @SerializedName("news_id")
    private String newId;
    @Generated(hash = 1857655025)
    public NewsSave(Long _id, String newId) {
        this._id = _id;
        this.newId = newId;
    }
    @Generated(hash = 28464038)
    public NewsSave() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getNewId() {
        return this.newId;
    }
    public void setNewId(String newId) {
        this.newId = newId;
    }

}
