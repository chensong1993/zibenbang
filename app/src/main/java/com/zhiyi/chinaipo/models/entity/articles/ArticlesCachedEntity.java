package com.zhiyi.chinaipo.models.entity.articles;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

@Entity
public class ArticlesCachedEntity {
    private int categoryId;
    @Transient
    private List<Integer> categories;
    private int id;
    private String title;
    private String description;
    private String author;
    private String source;
    private String newsType;
    private int clickCounter;
    private int originalId;
    private int commentCounter;
    private String newstime;
    private String titlepic;
    @Transient
    private List<String> morepic;

    @Generated(hash = 1756214983)
    public ArticlesCachedEntity(int categoryId, int id, String title, String description, String author,
            String source, String newsType, int clickCounter, int originalId, int commentCounter,
            String newstime, String titlepic) {
        this.categoryId = categoryId;
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.source = source;
        this.newsType = newsType;
        this.clickCounter = clickCounter;
        this.originalId = originalId;
        this.commentCounter = commentCounter;
        this.newstime = newstime;
        this.titlepic = titlepic;
    }

    @Generated(hash = 401761136)
    public ArticlesCachedEntity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public int getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(int commentCounter) {
        this.commentCounter = commentCounter;
    }

    public String getNewstime() {
        return newstime;
    }

    public void setNewstime(String newstime) {
        this.newstime = newstime;
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public List<String> getMorepic() {
        return morepic;
    }

    public void setMorepic(List<String> morepic) {
        this.morepic = morepic;
    }
}

