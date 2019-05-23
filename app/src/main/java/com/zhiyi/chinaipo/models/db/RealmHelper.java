package com.zhiyi.chinaipo.models.db;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper implements DBHelper {

    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    @Inject
    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    @Override
    public void insertNewsId(int id) {
//        ReadStateBean bean = new ReadStateBean();
//        bean.setId(id);
//        mRealm.beginTransaction();
//        mRealm.copyToRealmOrUpdate(bean);
//        mRealm.commitTransaction();
    }

    @Override
    public boolean queryNewsId(int id) {
//        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
//        for(ReadStateBean item : results) {
//            if(item.getId() == id) {
//                return true;
//            }
//        }
        return false;
    }

//    @Override
//    public void insertLikeBean(RealmLikeBean bean) {
//        mRealm.beginTransaction();
//        mRealm.copyToRealmOrUpdate(bean);
//        mRealm.commitTransaction();
//    }

    @Override
    public void deleteLikeBean(String id) {
//        RealmLikeBean data = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
//        mRealm.beginTransaction();
//        if (data != null) {
//            data.deleteFromRealm();
//        }
//        mRealm.commitTransaction();
    }

    @Override
    public boolean queryLikeId(String id) {
//        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
//        for(RealmLikeBean item : results) {
//            if(item.getId().equals(id)) {
//                return true;
//            }
//        }
        return false;
    }

//    @Override
//    public List<RealmLikeBean> getLikeList() {
//        //使用findAllSort ,先findAll再result.sort无效
//        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAllSorted("time");
//        return mRealm.copyFromRealm(results);
//    }

    @Override
    public void changeLikeTime(String id , long time, boolean isPlus) {
//        RealmLikeBean bean = mRealm.where(RealmLikeBean.class).equalTo("id", id).findFirst();
//        mRealm.beginTransaction();
//        if (isPlus) {
//            bean.setTime(++time);
//        } else {
//            bean.setTime(--time);
//        }
//        mRealm.commitTransaction();
    }

}
