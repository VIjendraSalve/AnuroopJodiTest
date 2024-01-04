package com.wind.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.wind.anuroopjodi.Object.ProfilePackageInfor;
import com.wind.anuroopjodi.R;

import java.util.ArrayList;

public class ProfilePackageInfoAdapter extends RecyclerView.Adapter<ProfilePackageInfoAdapter.ProductViewHolder> {

    private Context mCtx;
    //we are storing all the products in a list
    private ArrayList<ProfilePackageInfor> profilePackageInforArrayList= null;

    //getting the context and product list with constructor
    public ProfilePackageInfoAdapter(Context mCtx, ArrayList<ProfilePackageInfor> profilePackageInforArrayList) {
        this.mCtx = mCtx;
        this.profilePackageInforArrayList = profilePackageInforArrayList;
    }

    @Override
    public ProfilePackageInfoAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.profile_package_info_adapter, null);
        return new ProfilePackageInfoAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilePackageInfoAdapter.ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //getting the product of the specified position
//        ProfilePackageInfor product = profilePackageInforArrayList.get(position);
//        holder.text_code.setText("" + profilePackageInforArrayList.get(position).getProduct_code());
//        holder.text_size.setText("" + product.getValue());
//        holder.text_package.setText(String.valueOf(product.getInventory_count()));
        holder.textView_title.setText(profilePackageInforArrayList.get(position).getTitle());
        holder.tv_package_count.setText(profilePackageInforArrayList.get(position).getCount());
        holder.imageView.setImageResource(profilePackageInforArrayList.get(position).getImage());
        holder.imageView.setColorFilter(ContextCompat.getColor(mCtx,R.color.colorPrimary));


        
    }


    @Override
    public int getItemCount() {
        return profilePackageInforArrayList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView textView_title, tv_package_count, text_package, txtCount;
        public ImageView imageView;



        public ProductViewHolder(View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.tv_matchconnect);
            imageView = itemView.findViewById(R.id.img_Connect);
            tv_package_count = itemView.findViewById(R.id.tv_package_count);

        }
    }


}
