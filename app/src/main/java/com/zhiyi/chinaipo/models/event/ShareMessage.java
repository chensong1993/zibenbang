package com.zhiyi.chinaipo.models.event;

/**
 * @author chensong
 * @date 2018/2/27 13:23
 */
public class ShareMessage {
    String title;
    String titleContent;

    public ShareMessage(String title, String titleContent) {
        this.title = title;
        this.titleContent = titleContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleContent() {
        return titleContent;
    }

    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }
}
