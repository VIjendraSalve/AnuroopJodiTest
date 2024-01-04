package com.wind.anuroopjodi.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.anuroopjodi.Object.CategoryObject;
import com.wind.anuroopjodi.R;


import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.SingleItemRowHolder> {

    private ArrayList<CategoryObject> itemsList;
    private Context mContext;
    private CategoryObject singleItem;

    public HomeCategoryAdapter(Context context, ArrayList<CategoryObject> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_category_list_row_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        singleItem = new CategoryObject();
        singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.category);

       /* Picasso.with(mContext)
                .load(singleItem.RestaurantImage)
                .placeholder(R.drawable.applogonew)
                .into(holder.itemImage);*/

        DisplayMetrics metricscard = mContext.getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        holder.itemImage.getLayoutParams().height = cardheight / 12;
        holder.itemImage.getLayoutParams().width = cardwidth / 10;

        Glide.with(mContext)
                .load(singleItem.category_image)
                .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                .into(holder.itemImage);


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvProductName);
            this.itemImage = (ImageView) view.findViewById(R.id.ivProductImg);


            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                }
            });*/


        }

    }
}
