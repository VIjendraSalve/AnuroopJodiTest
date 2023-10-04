package com.whtech.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whtech.anuroopjodi.InsideActivities.Vijendra.InterestSentListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyConnectionListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyMatchesListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyPreferencesListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyRequestListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyShortlistedListActivity;
import com.whtech.anuroopjodi.Object.TypeMatch;
import com.whtech.anuroopjodi.R;

import java.util.ArrayList;

public class TypeMatchHomeAdapter extends RecyclerView.Adapter<TypeMatchHomeAdapter.ProductViewHolder> {

    private Context mCtx;
    //we are storing all the products in a list
    private ArrayList<TypeMatch> typeMatches= null;

    //getting the context and product list with constructor
    public TypeMatchHomeAdapter(Context mCtx, ArrayList<TypeMatch> typeMatches) {
        this.mCtx = mCtx;
        this.typeMatches = typeMatches;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.match_type_adapter, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
//        TypeMatch product = typeMatches.get(position);
//        holder.text_code.setText("" + typeMatches.get(position).getProduct_code());
//        holder.text_size.setText("" + product.getValue());
//        holder.text_package.setText(String.valueOf(product.getInventory_count()));
        holder.textView_title.setText(typeMatches.get(position).getTitle());
        holder.imageView.setImageResource(typeMatches.get(position).getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener()
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
        });
    }


    @Override
    public int getItemCount() {
        return typeMatches.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_title, text_size, text_package, txtCount;
        public ImageView imageView;



        public ProductViewHolder(View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.tv_matchconnect);
            imageView = itemView.findViewById(R.id.img_Connect);


        }
    }


}
