package com.zhiyi.chinaipo.models.entity;

/**
 * @author chensong
 * @date 2018/9/18 16:26
 */
public class WeatherEntity<T> {
    private int count;

    private T result;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
