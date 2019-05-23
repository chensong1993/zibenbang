package com.zhiyi.chinaipo.models.entity.articles;

import java.util.List;

public class ColumnEntity {
    private int id;
    private String name;
    private String description;
    private  String destUrl;
    private int showingOrder;
    private int click_counter;
    private String publishing_date;
    private String photo;
    private String author;
    private String related_name;
    private String related_author;



    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getClick_counter() {
        return click_counter;
    }

    public void setClick_counter(int click_counter) {
        this.click_counter = click_counter;
    }

    public String getPublishing_date() {
        return publishing_date;
    }

    public void setPublishing_date(String publishing_date) {
        this.publishing_date = publishing_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRelated_author() {
        return related_author;
    }

    public void setRelated_author(String related_author) {
        this.related_author = related_author;
    }

    public String getRelated_name() {
        return related_name;
    }

    public void setRelated_name(String related_name) {
        this.related_name = related_name;
    }
}
