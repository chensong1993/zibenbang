package com.zhiyi.chinaipo.models.entity.articles;

import java.util.List;

/**
 * Created by admin on 2017/9/30.
 */
public class WebNews {

    private List<Results> results;

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return this.results;
    }

    public class Results {
        private int id;

        private String title;

        private String lead_in;

        private String meta_keywords;

        private String search_data;

        private Content content;

        private String author;

        private int clickCounter;

        private int commentCounter;

        private String source;

        private int categoryId;

        private List<OtherLinks> otherLinks;

        private String newsType;

        private String titlepic;

        private boolean bookMarked;

        private int originalId;

        private String publishing_date;

        private String featured_image;

        private List<Tags> tags;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setLead_in(String lead_in) {
            this.lead_in = lead_in;
        }

        public String getLead_in() {
            return this.lead_in;
        }

        public void setMeta_keywords(String meta_keywords) {
            this.meta_keywords = meta_keywords;
        }

        public String getMeta_keywords() {
            return this.meta_keywords;
        }

        public void setSearch_data(String search_data) {
            this.search_data = search_data;
        }

        public String getSearch_data() {
            return this.search_data;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Content getContent() {
            return this.content;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor() {
            return this.author;
        }

        public void setClickCounter(int clickCounter) {
            this.clickCounter = clickCounter;
        }

        public int getClickCounter() {
            return this.clickCounter;
        }

        public void setCommentCounter(int commentCounter) {
            this.commentCounter = commentCounter;
        }

        public int getCommentCounter() {
            return this.commentCounter;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return this.source;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCategoryId() {
            return this.categoryId;
        }

        public void setOtherLinks(List<OtherLinks> otherLinks) {
            this.otherLinks = otherLinks;
        }

        public List<OtherLinks> getOtherLinks() {
            return this.otherLinks;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public String getNewsType() {
            return this.newsType;
        }

        public void setTitlepic(String titlepic) {
            this.titlepic = titlepic;
        }

        public String getTitlepic() {
            return this.titlepic;
        }

        public void setBookMarked(boolean bookMarked) {
            this.bookMarked = bookMarked;
        }

        public boolean getBookMarked() {
            return this.bookMarked;
        }

        public void setOriginalId(int originalId) {
            this.originalId = originalId;
        }

        public int getOriginalId() {
            return this.originalId;
        }

        public void setPublishing_date(String publishing_date) {
            this.publishing_date = publishing_date;
        }

        public String getPublishing_date() {
            return this.publishing_date;
        }

        public void setFeatured_image(String featured_image) {
            this.featured_image = featured_image;
        }

        public String getFeatured_image() {
            return this.featured_image;
        }

        public void setTags(List<Tags> tags) {
            this.tags = tags;
        }

        public List<Tags> getTags() {
            return this.tags;
        }

        public class Tags {
            private int id;

            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return this.id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return this.name;
            }

        }

        public class OtherLinks {
            private int id;

            private String title;

            private String lead_in;

            private String newsType;

            private int clickCounter;

            private String featured_image;

            private String source;

            private int originalId;

            public int getOriginalId() {
                return originalId;
            }

            public void setOriginalId(int originalId) {
                this.originalId = originalId;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getId() {
                return this.id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return this.title;
            }

            public void setLead_in(String lead_in) {
                this.lead_in = lead_in;
            }

            public String getLead_in() {
                return this.lead_in;
            }

            public void setNewsType(String newsType) {
                this.newsType = newsType;
            }

            public String getNewsType() {
                return this.newsType;
            }

            public void setClickCounter(int clickCounter) {
                this.clickCounter = clickCounter;
            }

            public int getClickCounter() {
                return this.clickCounter;
            }

            public void setFeatured_image(String featured_image) {
                this.featured_image = featured_image;
            }

            public String getFeatured_image() {
                return this.featured_image;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getSource() {
                return this.source;
            }

        }

        public class Content {

            private String content;

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent() {
                return this.content;
            }

        }

    }
}
