package com.androidprojects.sunilsharma.newsapp.Interface;

import com.androidprojects.sunilsharma.newsapp.Model.IconBetterIdea;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sunil sharma on 11/16/2017.
 */

public interface IconBetterIdeaService
{
    @GET
    Call<IconBetterIdea> getIconUrl(@Url String url);
}
