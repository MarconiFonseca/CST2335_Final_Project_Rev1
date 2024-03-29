package com.example.cst2335_final_project.NewsAPI.News_API_Models;

import com.google.gson.annotations.SerializedName;

/*
@autor Marconi Fonseca
 */

public class Article {



    @SerializedName("source")
    private SourceModel source;
    @SerializedName("author")
    private String author;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;
    @SerializedName("urlToImage")
    private String urlToImage;
    @SerializedName("publishedAt")
    private String publishedAt;

    private long id;


    public Article(long id, String title, String url, String urlToImage, String description) {
        this.id = id;
        this.title=title;
        this.url=url;
        this.urlToImage=urlToImage;
        this.description=description;

    }

    public Article() {

    }

    public SourceModel getSource() {
        return source;
    }
    public void setSource(SourceModel source) {
        this.source = source;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrlToImage() { return urlToImage;
    }
    public long getId() {return this.id;} /** @return id*/
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
    public String getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


}
