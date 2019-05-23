package com.zhiyi.chinaipo.models.event;

/**
 * @author chensong
 * @date 2019/5/7 15:30
 */
public class BannerIndexEvent {
    int index;

    public BannerIndexEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
