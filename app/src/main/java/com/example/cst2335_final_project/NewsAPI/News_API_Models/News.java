package com.example.cst2335_final_project.NewsAPI.News_API_Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class News {


    @SerializedName("status")
    private String status;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("articles")
    private List<Article> articles = null;
    public String getStatus() {
      return status;
    }
    public void setStatus(String status) {
      this.status = status;
    }
    public int getTotalResults() {
      return totalResults;
    }
    public void setTotalResults(int totalResults) {
      this.totalResults = totalResults;
    }
    public List<Article> getArticles() {
      return articles;
    }
    public void setArticles(List<Article> articles) {
      this.articles = articles;
    }
  }