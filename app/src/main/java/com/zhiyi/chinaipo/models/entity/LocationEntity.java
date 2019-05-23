package com.zhiyi.chinaipo.models.entity;

/**
 * @author chensong
 * @date 2018/9/19 17:29
 */
public class LocationEntity {

        private String address;

        private Address_detail address_detail;

        private Point point;

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress_detail(Address_detail address_detail) {
            this.address_detail = address_detail;
        }

        public Address_detail getAddress_detail() {
            return this.address_detail;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public Point getPoint() {
            return this.point;
        }


    public class Point {
        private String x;

        private String y;

        public void setX(String x) {
            this.x = x;
        }

        public String getX() {
            return this.x;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getY() {
            return this.y;
        }

    }


    public class Address_detail {
        private String city;

        private int city_code;

        private String district;

        private String province;

        private String street;

        private String street_number;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity_code(int city_code) {
            this.city_code = city_code;
        }

        public int getCity_code() {
            return this.city_code;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDistrict() {
            return this.district;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince() {
            return this.province;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreet() {
            return this.street;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }

        public String getStreet_number() {
            return this.street_number;
        }

    }
    }