package com.zhiyi.chinaipo.models.entity;

import java.util.List;

/**
 * @author chensong
 * @date 2018/9/19 15:26
 */
public class H3 {

        private AreaInfo areaInfo;

        private int code;

        private String message;

        private WeatherInfo weatherInfo;


        public void setAreaInfo(AreaInfo areaInfo) {
            this.areaInfo = areaInfo;
        }

        public AreaInfo getAreaInfo() {
            return this.areaInfo;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void setWeatherInfo(WeatherInfo weatherInfo) {
            this.weatherInfo = weatherInfo;
        }

        public WeatherInfo getWeatherInfo() {
            return this.weatherInfo;
        }


    public class AreaInfo {
        private String city;

        private String county;

        private String province;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCounty() {
            return this.county;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince() {
            return this.province;
        }

    }

    public class Today {
        private String date;

        private double tem_day;

        private String wdd_night;

        private String wdp_day;

        private String weather_day;

        private String wdp_night;

        private double tem_night;

        private String wdd_day;

        private String weather_night;

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setTem_day(double tem_day) {
            this.tem_day = tem_day;
        }

        public double getTem_day() {
            return this.tem_day;
        }

        public void setWdd_night(String wdd_night) {
            this.wdd_night = wdd_night;
        }

        public String getWdd_night() {
            return this.wdd_night;
        }

        public void setWdp_day(String wdp_day) {
            this.wdp_day = wdp_day;
        }

        public String getWdp_day() {
            return this.wdp_day;
        }

        public void setWeather_day(String weather_day) {
            this.weather_day = weather_day;
        }

        public String getWeather_day() {
            return this.weather_day;
        }

        public void setWdp_night(String wdp_night) {
            this.wdp_night = wdp_night;
        }

        public String getWdp_night() {
            return this.wdp_night;
        }

        public void setTem_night(double tem_night) {
            this.tem_night = tem_night;
        }

        public double getTem_night() {
            return this.tem_night;
        }

        public void setWdd_day(String wdd_day) {
            this.wdd_day = wdd_day;
        }

        public String getWdd_day() {
            return this.wdd_day;
        }

        public void setWeather_night(String weather_night) {
            this.weather_night = weather_night;
        }

        public String getWeather_night() {
            return this.weather_night;
        }

    }

    public class Real {
        private String aq;

        private double rain;

        private String wdp;

        private String wdd;

        private String publish_time;

        private String weather;

        private int aqi;

        private double humidity;

        private double feelst;

        private double tem;

        private String icomfort;

        public void setAq(String aq) {
            this.aq = aq;
        }

        public String getAq() {
            return this.aq;
        }

        public void setRain(double rain) {
            this.rain = rain;
        }

        public double getRain() {
            return this.rain;
        }

        public void setWdp(String wdp) {
            this.wdp = wdp;
        }

        public String getWdp() {
            return this.wdp;
        }

        public void setWdd(String wdd) {
            this.wdd = wdd;
        }

        public String getWdd() {
            return this.wdd;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getPublish_time() {
            return this.publish_time;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather() {
            return this.weather;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public int getAqi() {
            return this.aqi;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getHumidity() {
            return this.humidity;
        }

        public void setFeelst(double feelst) {
            this.feelst = feelst;
        }

        public double getFeelst() {
            return this.feelst;
        }

        public void setTem(double tem) {
            this.tem = tem;
        }

        public double getTem() {
            return this.tem;
        }

        public void setIcomfort(String icomfort) {
            this.icomfort = icomfort;
        }

        public String getIcomfort() {
            return this.icomfort;
        }

    }

    public class H3s {
        private String date;

        private String cloud;

        private double rain;

        private String vis;

        private String wdp;

        private String wdd;

        private double wds;

        private String weather;

        private String humidity;

        private String time;

        private String tem;

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return this.date;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCloud() {
            return this.cloud;
        }

        public void setRain(double rain) {
            this.rain = rain;
        }

        public double getRain() {
            return this.rain;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getVis() {
            return this.vis;
        }

        public void setWdp(String wdp) {
            this.wdp = wdp;
        }

        public String getWdp() {
            return this.wdp;
        }

        public void setWdd(String wdd) {
            this.wdd = wdd;
        }

        public String getWdd() {
            return this.wdd;
        }

        public void setWds(double wds) {
            this.wds = wds;
        }

        public double getWds() {
            return this.wds;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather() {
            return this.weather;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getHumidity() {
            return this.humidity;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return this.time;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getTem() {
            return this.tem;
        }

    }

    public class WeatherInfo {
        private Today today;

        private Real real;

        private List<H3s> h3;

        public void setToday(Today today) {
            this.today = today;
        }

        public Today getToday() {
            return this.today;
        }

        public void setReal(Real real) {
            this.real = real;
        }

        public Real getReal() {
            return this.real;
        }

        public void setH3(List<H3s> h3) {
            this.h3 = h3;
        }

        public List<H3s> getH3() {
            return this.h3;
        }

        @Override
        public String toString() {
            return "WeatherInfo{" +
                    "today=" + today +
                    ", real=" + real +
                    ", h3=" + h3 +
                    '}';
        }
    }


}
