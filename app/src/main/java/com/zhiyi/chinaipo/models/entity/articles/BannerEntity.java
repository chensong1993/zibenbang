package com.zhiyi.chinaipo.models.entity.articles;

import com.google.gson.annotations.SerializedName;

public class BannerEntity {
    private int id;
    private String title;
    private String author;
    @SerializedName("publishing_date")
    private String updateDate;
    private String description;
    private String destUrl;
    private int showingOrder;
    private String newsType;
    private String featured_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }

    public int getShowingOrder() {
        return showingOrder;
    }

    public void setShowingOrder(int showingOrder) {
        this.showingOrder = showingOrder;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
