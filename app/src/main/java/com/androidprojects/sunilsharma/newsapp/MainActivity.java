package com.androidprojects.sunilsharma.newsapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidprojects.sunilsharma.newsapp.Adapter.ListSourceAdapter;
import com.androidprojects.sunilsharma.newsapp.Common.Common;
import com.androidprojects.sunilsharma.newsapp.Interface.NewsService;
import com.androidprojects.sunilsharma.newsapp.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog dialog;
    SwipeRefreshLayout swipehLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        //Init Services
        mService = Common.getNewsService();

        //Init View
        swipehLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipehLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                loadWebsiteSource(true);
            }
        });

        listWebsite = (RecyclerView) findViewById(R.id.list_resource);
        listWebsite.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed)
    {
        if(!isRefreshed)
        {
            String cache = Paper.book().read("cache");

            if(cache != null && !cache.isEmpty())
            {
                WebSite webSite = new Gson().fromJson(cache , WebSite.class);//Convert cache form Json to Object
                adapter = new ListSourceAdapter(getBaseContext() , webSite);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else
            {
                dialog.show();
                //Fetch New Data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response)
                    {
                        adapter = new ListSourceAdapter(getBaseContext() , response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to cache
                        Paper.book().write("cache" , new Gson().toJson(response.body()));
                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t)
                    {

                    }
                });
            }
        }

        else
        {
            //if from swipe to Refresh
            dialog.show();
            //Fetch New Data
            mService.getSources().enqueue(new Callback<WebSite>() {
                @Override
                public void onResponse(Call<WebSite> call, Response<WebSite> response)
                {
                    adapter = new ListSourceAdapter(getBaseContext() , response.body());
                    adapter.notifyDataSetChanged();
                    listWebsite.setAdapter(adapter);

                    //Save to cache
                    Paper.book().write("cache" , new Gson().toJson(response.body()));

                    //Dismiss Refresh Progressing
                    swipehLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<WebSite> call, Throwable t)
                {

                }
            });
        }
    }
}
