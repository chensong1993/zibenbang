package com.zhiyi.chinaipo.push;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.zhiyi.chinaipo.util.LogUtil;

/**
 * @author chensong
 * @date 2018/9/20 10:12
 */
public class MyLocationListener extends BDAbstractLocationListener {
    @Override
    public void onReceiveLocation(BDLocation location) {
        String addr = location.getAddrStr();    //获取详细地址信息
        String country = location.getCountry();    //获取国家
        String province = location.getProvince();    //获取省份
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        String street = location.getStreet();    //获取街道信息
        LogUtil.i("city",city+province+district+street);

    }
}
