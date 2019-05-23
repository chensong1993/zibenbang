package com.zhiyi.chinaipo.models.db;

import java.util.List;


public interface DBHelper {

    void insertNewsId(int id);

    boolean queryNewsId(int id);

//    void insertLikeBean(RealmLikeBean bean);

    void deleteLikeBean(String id);

    boolean queryLikeId(String id);

//    List<RealmLikeBean> getLikeList();

    void changeLikeTime(String id, long time, boolean isPlus);

}
