package com.zhiyi.chinaipo.models.entity.articles;

import java.io.Serializable;
import java.util.List;

public class ArticleDetailEntity implements Serializable {

    private int categoryId;
    private int[] categories;
    private int id;
    private String title;
    private String description;
    private String newsType;
    private int clickCounter;
    private int originalId;
    private String publishing_date;
    private String titlepic;
    private List<String> morepic;
    private int starredCount;
    private int abuseCount;
    private String newsURL;
    private int commentCounter;
    private boolean bookMarked;
    private String featuredImage;
    private String author;
    private String source;
    private boolean isStar;
    private NewsContent content;
    private List<NewsTag> tags;
    private List<OtherLinks> otherLinks;
    private String lead_in;
    private String categoryName;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public List<String> getMorepic() {
        return morepic;
    }

    public void setMorepic(List<String> morepic) {
        this.morepic = morepic;
    }

    public int getStarredCount() {
        return starredCount;
    }

    public void setStarredCount(int starredCount) {
        this.starredCount = starredCount;
    }

    public int getAbuseCount() {
        return abuseCount;
    }

    public void setAbuseCount(int abuseCount) {
        this.abuseCount = abuseCount;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public boolean isBookMarked() {
        return bookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        this.bookMarked = bookMarked;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public NewsContent getContent() {
        return content;
    }

    public void setContent(NewsContent content) {
        this.content = content;
    }

    public List<NewsTag> getTags() {
        return tags;
    }

    public void setTags(List<NewsTag> tags) {
        this.tags = tags;
    }

    public List<OtherLinks> getOtherLinks() {
        return otherLinks;
    }

    public void setOtherLinks(List<OtherLinks> otherLinks) {
        this.otherLinks = otherLinks;
    }

    public String getLead_in() {
        return lead_in;
    }

    public void setLead_in(String lead_in) {
        this.lead_in = lead_in;
    }

    public String getPublishing_date() {
        return publishing_date;
    }

    public void setPublishing_date(String publishing_date) {
        this.publishing_date = publishing_date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public static class NewsContent {
        private String sekizai;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class OtherLinks {
        private int id;
        private String title;
        private String lead_in;
        private String newsType;
        private int clickCounter;
        private String featured_image;
        private String source;
        private int categoryId;
        private int originalId;

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

        public String getLead_in() {
            return lead_in;
        }

        public void setLead_in(String lead_in) {
            this.lead_in = lead_in;
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

        public String getFeatured_image() {
            return featured_image;
        }

        public void setFeatured_image(String featured_image) {
            this.featured_image = featured_image;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getOriginalId() {
            return originalId;
        }

        public void setOriginalId(int originalId) {
            this.originalId = originalId;
        }
    }

    public static class NewsTag {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
