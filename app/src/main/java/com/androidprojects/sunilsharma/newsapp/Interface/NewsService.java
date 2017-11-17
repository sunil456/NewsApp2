package com.androidprojects.sunilsharma.newsapp.Interface;

import com.androidprojects.sunilsharma.newsapp.Model.News;
import com.androidprojects.sunilsharma.newsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sunil sharma on 11/16/2017.
 */

public interface NewsService
{
    @GET("v1/sources?language=en")
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
