package com.zhiyi.chinaipo.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.zhiyi.chinaipo.app.App;
import com.zhiyi.chinaipo.models.entity.articles.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是SharedPreferences的工具类
 * <p/>
 * data/data/包名/shared_pre
 */
public class SPHelper {

    private static final String SP_NAME = "share_data";
    //  private volatile static SharedPreferences mSP;

    private static MMKV preferences = MMKV.mmkvWithID(SP_NAME);

    {
        SharedPreferences mSP = App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        preferences.importFromSharedPreferences(mSP);
        mSP.edit().clear().commit();
    }
   /* private static SharedPreferences getSharePreferences() {
        if (mSP == null) {
            synchronized (SP_NAME) {
                if (mSP == null) {
                    mSP = App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
                    preferences.importFromSharedPreferences(mSP);
                    mSP.edit().clear().commit();
                }
            }
        }
        return mSP;
    }*/

    // =============================================================
    // ------------------------保存数据--------------------------------
    // ------------------ key===>键名 value===>保存的数据值 ----------------
    // =============================================================

    /**
     * 保存数据 根据数据类型自动拆箱
     *
     * @param key 键名
     * @param obj Object类型数据 但仅限于(String/int/float/boolean/long)
     */
    public static void save(String key, String obj) {
        Editor editor = preferences.edit();
        editor.putString(key, obj);
    }

    public static void save(String key, Integer obj) {
        Editor editor = preferences.edit();
        editor.putInt(key, obj);
    }

    public static void save(String key, Long obj) {
        Editor editor = preferences.edit();
        editor.putLong(key, obj);
    }

    public static void save(String key, Float obj) {
        Editor editor = preferences.edit();
        editor.putFloat(key, obj);
    }

    public static void save(String key, Boolean obj) {
        Editor editor = preferences.edit();
        editor.putBoolean(key, obj);
    }
    // =============================================================
    // ------------------------获取数据--------------------------------
    // ------ key===>键名 defaultValue===>根据key查找不到的默认数据的数据值 -------
    // =============================================================

   /* */

    /**
     * 获取Object类型数据 根据接收类型自动拆箱
     *
     * @param key          键名
     * @param defaultValue 根据key获取不到是默认值仅限于(String/int/float/boolean/long)
     */

    public static String get(String key, String defaultValue) {
        SharedPreferences sp = preferences;
        return sp.getString(key, defaultValue);

    }

    public static Integer get(String key, int defaultValue) {
        SharedPreferences sp = preferences;
        return sp.getInt(key, defaultValue);

    }

    public static Long get(String key, Long defaultValue) {
        SharedPreferences sp = preferences;
        return sp.getLong(key, defaultValue);

    }

    public static Boolean get(String key, Boolean defaultValue) {
        SharedPreferences sp = preferences;
        return sp.getBoolean(key, defaultValue);

    }

    public static Float get(String key, Float defaultValue) {
        SharedPreferences sp = preferences;
        return sp.getFloat(key, defaultValue);

    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static void setDataList(String tag, List<CategoryEntity> datalist) {
        Editor editor = preferences.edit();
        if (null == datalist || datalist.size() < 0) {
            return;
        } else {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            editor.putString(tag, strJson);
            LogUtil.i("dataLists", strJson);
            //  editor.commit();
        }

    }

    /**
     * 获取List
     *
     * @param tag
     * @return
     */
    public static List<CategoryEntity> getDataList(String tag) {
        SharedPreferences preferencess = preferences;
        List<CategoryEntity> datalist = new ArrayList<>();
        String strJson = preferencess.getString(tag, null);
        if (null == strJson) {
            return datalist;
        } else {
            Gson gson = new Gson();
            datalist = gson.fromJson(strJson, new TypeToken<List<CategoryEntity>>() {
            }.getType());
            for (int i = 0; i < datalist.size(); i++) {
                LogUtil.i("dataGson", datalist.get(i).getName() + "");
            }
            return datalist;
        }
    }

    /**
     * 根据key删除数据
     *
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        return preferences.edit().remove(key).commit();
    }
}
