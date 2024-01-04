package com.wind.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.wind.anuroopjodi.InsideActivities.Activities.SuccessfulMarriageDetailsActivity;
import com.wind.anuroopjodi.Object.SuccessStories;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.DateTimeFormat;

import java.util.ArrayList;

public class SuccessStoryAdapter extends RecyclerView.Adapter<SuccessStoryAdapter.SuccessStoryViewHolder> {

    private Context mCtx;
    //we are storing all the products in a list
    private ArrayList<SuccessStories> successStoriesArrayList= null;

    //getting the context and product list with constructor
    public SuccessStoryAdapter(Context mCtx, ArrayList<SuccessStories> successStoriesArrayList) {
        this.mCtx = mCtx;
        this.successStoriesArrayList = successStoriesArrayList;
    }

    @Override
    public SuccessStoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.success_story_horizontal_adapter, null);
        return new SuccessStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuccessStoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
        SuccessStories successStories = successStoriesArrayList.get(position);
//        holder.text_code.setText("" + typeMatches.get(position).getProduct_code());
        holder.tvSuccessStoryTitle.setText(successStories.getTitle());
        holder.tvSuccessStoryDescription.setText(successStories.getStory());
        holder.tvDate.setText("Marriage  Date : "+ DateTimeFormat.getDate(successStories.getEntryDate()));
        //holder.ivCoupleImage.setImageResource(successStories.getPhoto());

        Log.d("ImagePath", "onBindViewHolder: "+successStories.getPhoto());
        Glide.with(mCtx)
                .load(successStories.getPhoto())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.ivCoupleImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, SuccessfulMarriageDetailsActivity.class);
                intent.putExtra("title",successStoriesArrayList.get(position).getTitle());
                intent.putExtra("desc",successStoriesArrayList.get(position).getStory());
                intent.putExtra("image",successStoriesArrayList.get(position).getPhoto());
                intent.putExtra("marriagedate",successStoriesArrayList.get(position).getEntryDate());
                mCtx.startActivity(intent);
            }
        });


       /* holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (position==0)
                {
                    Intent intent =  new Intent(mCtx, MyConnectionListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==1)
                {
                    Intent intent =  new Intent(mCtx, MyShortlistedListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==2)
                {
                    Intent intent =  new Intent(mCtx, MyRequestListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==3)
                {
                    Intent intent =  new Intent(mCtx, MyMatchesListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==4)
                {
                    Intent intent =  new Intent(mCtx, MyRequestListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==5)
                {
                    Intent intent =  new Intent(mCtx, InterestSentListActivity.class);
                    mCtx.startActivity(intent);
                }
                else if (position==6)
                {
                    Intent intent =  new Intent(mCtx, MyPreferencesListActivity.class);
                    mCtx.startActivity(intent);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return successStoriesArrayList.size();
    }


    class SuccessStoryViewHolder extends RecyclerView.ViewHolder {

        public TextView tvSuccessStoryTitle, tvSuccessStoryDescription, tvDate;
        public ImageView ivCoupleImage;

        public SuccessStoryViewHolder(View itemView) {
            super(itemView);

            tvSuccessStoryTitle = itemView.findViewById(R.id.tvSuccessStoryTitle);
            tvSuccessStoryDescription = itemView.findViewById(R.id.tvSuccessStoryDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivCoupleImage = itemView.findViewById(R.id.ivCoupleImage);
        }
    }
}
