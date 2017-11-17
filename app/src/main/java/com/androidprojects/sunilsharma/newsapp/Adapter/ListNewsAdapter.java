package com.androidprojects.sunilsharma.newsapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprojects.sunilsharma.newsapp.Common.ISO8601Parse;
import com.androidprojects.sunilsharma.newsapp.Interface.ItemClickListener;
import com.androidprojects.sunilsharma.newsapp.Model.Article;
import com.androidprojects.sunilsharma.newsapp.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil sharma on 11/17/2017.
 */

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    ItemClickListener itemClickListener;
    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;

    public ListNewsViewHolder(View itemView)
    {
        super(itemView);
        article_image = (CircleImageView) itemView.findViewById(R.id.article_image);
        article_title = (TextView) itemView.findViewById(R.id.article_title);
        article_time = (RelativeTimeTextView) itemView.findViewById(R.id.article_time);


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





public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder>
{
    private List<Article> articleList;
    private Context context;


    public ListNewsAdapter(List<Article> articleList, Context context)
    {
        this.articleList = articleList;
        this.context = context;
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
    public ListNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_layout , parent , false);

        return new ListNewsViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ListNewsViewHolder holder, int position)
    {
        Picasso.with(context).
                load(articleList.get(position).getUrlToImage()).
                into(holder.article_image);

        if(articleList.get(position).getTitle().length() > 65)
            holder.article_title.
                    setText(articleList.get(position).
                            getTitle().
                            substring(0 , 65)+ "....");

        else
            holder.article_title.setText(articleList.get(position).getTitle());


        Date date = null;

        try
        {
            date = ISO8601Parse.parse(articleList.get(position).getPublishedAt());
        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
        }

        holder.article_time.setReferenceTime(date.getTime());

        //Set Event Click
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                //Code Latter
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
        return articleList.size();
    }
}
