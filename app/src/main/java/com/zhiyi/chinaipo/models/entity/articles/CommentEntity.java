package com.zhiyi.chinaipo.models.entity.articles;


public class CommentEntity {
    private int page_id;
    private int author_id;
    private String body;
    private int requries_attention;
    private int moderated;
    private String moderated_reason;
    private String user_ip;
    private String user_agent;
    private String referrer;
    private boolean published;

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRequries_attention() {
        return requries_attention;
    }

    public void setRequries_attention(int requries_attention) {
        this.requries_attention = requries_attention;
    }

    public int getModerated() {
        return moderated;
    }

    public void setModerated(int moderated) {
        this.moderated = moderated;
    }

    public String getModerated_reason() {
        return moderated_reason;
    }

    public void setModerated_reason(String moderated_reason) {
        this.moderated_reason = moderated_reason;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
