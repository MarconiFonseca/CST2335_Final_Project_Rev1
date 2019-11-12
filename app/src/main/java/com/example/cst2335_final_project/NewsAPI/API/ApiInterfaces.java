package com.example.cst2335_final_project.NewsAPI.API;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaces {



    @GET("top-headlines")
    Call<News> getLatestNews(@Query("country") String source,@Query("apiKey") String apiKey);

    @GET("everything")
    Call<News> getNewsSearch(

            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
}


