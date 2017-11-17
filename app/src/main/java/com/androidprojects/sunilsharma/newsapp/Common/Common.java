package com.androidprojects.sunilsharma.newsapp.Common;

import com.androidprojects.sunilsharma.newsapp.Interface.IconBetterIdeaService;
import com.androidprojects.sunilsharma.newsapp.Interface.NewsService;
import com.androidprojects.sunilsharma.newsapp.Remote.IconBetterIdeaClient;
import com.androidprojects.sunilsharma.newsapp.Remote.RetrofitClient;

/**
 * Created by sunil sharma on 11/16/2017.
 */

public class Common
{
    private static final String BASE_URL = "https://newsapi.org/";

    public static final String API_KEY = "a4c2a3e374e54d8fa7bf5d51469d4303";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a4c2a3e374e54d8fa7bf5d51469d4303
    public static String getAPIUrl(String source , String sortBy , String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source).
                append("&sortBy=").
                append(sortBy).
                append("&apiKey=").
                append(apiKEY).
                toString();
    }
}
