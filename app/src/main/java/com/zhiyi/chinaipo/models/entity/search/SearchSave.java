package com.zhiyi.chinaipo.models.entity.search;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author chensong
 * @date 2018/8/3 15:06
 */
@Entity(nameInDb = "searchSave")
public class SearchSave {
    @Id(autoincrement = true)
    private Long _id;
    @SerializedName("search_name")
    private String searchName;
    @Generated(hash = 954884124)
    public SearchSave(Long _id, String searchName) {
        this._id = _id;
        this.searchName = searchName;
    }
    @Generated(hash = 456883572)
    public SearchSave() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public String getSearchName() {
        return this.searchName;
    }
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
}
