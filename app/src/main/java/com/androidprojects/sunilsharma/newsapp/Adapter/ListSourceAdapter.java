package com.androidprojects.sunilsharma.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprojects.sunilsharma.newsapp.Common.Common;
import com.androidprojects.sunilsharma.newsapp.Interface.IconBetterIdeaService;
import com.androidprojects.sunilsharma.newsapp.Interface.ItemClickListener;
import com.androidprojects.sunilsharma.newsapp.ListNews;
import com.androidprojects.sunilsharma.newsapp.Model.IconBetterIdea;
import com.androidprojects.sunilsharma.newsapp.Model.WebSite;
import com.androidprojects.sunilsharma.newsapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sunil sharma on 11/16/2017.
 */


class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(View itemView)
    {
        super(itemView);

        source_image = (CircleImageView) itemView.findViewById(R.id.source_image);
        source_title = (TextView) itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view , getAdapterPosition() , false);
    }
}

public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>
{

    private Context context;
    private WebSite webSite;

    private IconBetterIdeaService mService;

    public ListSourceAdapter(Context context, WebSite webSite)
    {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout , parent , false);

        return new ListSourceViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position)
    {
        StringBuilder iconBetterAPI = new StringBuilder("https://icons.better-idea.org/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterAPI.toString()).
                enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response)
            {
                if (response.body()!=null && response.body().getIcons()!=null
                        && response.body().getIcons().size() > 0
                        && !TextUtils.isEmpty(response.body().getIcons().get(0).getUrl()))
                {
                    Picasso.with(context).
                            load(response.body().getIcons().get(0).getUrl()).
                            into(holder.source_image);
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {

            }
        });


        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                Intent intent = new Intent(context , ListNews.class);
                intent.putExtra("source" ,
                        webSite.getSources().get(position).getId());

                intent.putExtra("sortBy" ,
                        webSite.getSources().get(position).getSortBysAvailable().get(0));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount()
    {
        return webSite.getSources().size();
    }
}
