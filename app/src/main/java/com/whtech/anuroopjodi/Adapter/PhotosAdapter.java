package com.whtech.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.FullPhotoZoomActivity;
import com.whtech.anuroopjodi.Object.PhotoObjects;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.CircleImageView;
import com.whtech.anuroopjodi.my_library.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.view.View.GONE;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private ArrayList<PhotoObjects> photoObjectsArrayList;
    private Context context;
    private String path;
    private ProgressDialog progressDialog;

    public PhotosAdapter(Context ctx, ArrayList<PhotoObjects> photoObjectsArrayList, String path) {
        context = ctx;
        this.photoObjectsArrayList = photoObjectsArrayList;
        this.path = path;

    }

    @Override
    public PhotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photos_list_view, parent, false);

        PhotosAdapter.ViewHolder viewHolder = new PhotosAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotosAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PhotoObjects photoObjects = photoObjectsArrayList.get(position);

        if(SharedPref.getPrefs(context, IConstant.USER_ID).equals(photoObjects.getLoginId())){

            holder.civ_user_pic.setVisibility(View.VISIBLE);
            holder.iv_delete_image.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(path + photoObjects.getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                    .into(holder.civ_user_pic);

        }else {

            if(photoObjects.getApproved().equals("1")){
                holder.civ_user_pic.setVisibility(View.VISIBLE);
                holder.iv_delete_image.setVisibility(View.GONE);
                Glide.with(context)
                        .load(path + photoObjects.getImage())
                        .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                        .into(holder.civ_user_pic);
            }else {
                holder.civ_user_pic.setVisibility(GONE);
                holder.iv_delete_image.setVisibility(GONE);
            }

        }

        Glide.with(context)
                .load(path + photoObjects.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(holder.civ_user_pic);

        holder.iv_delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Are you sure you want to delete this photo?");
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteImage(photoObjects.getId(), position);
                    }
                });

                alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage, path+photoObjectsArrayList.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return photoObjectsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView civ_user_pic;
        private ImageView iv_delete_image;
      
        public ViewHolder(View view) {
            super(view);

            civ_user_pic = (CircleImageView) view.findViewById(R.id.civ_user_pic);
            iv_delete_image = (ImageView) view.findViewById(R.id.iv_delete_image);

        }
    }

    private interface GetOrderAPI {
        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_profile_image_delete")
        public Call<ResponseBody> matchingProfiles(
                @Field("userId") String userId,
                @Field("imageId") String imageId
        );
    }

    private void DeleteImage(String imageID, int position) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.matchingProfiles(
                SharedPref.getPrefs(context, IConstant.USER_ID),
                imageID
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    if (res) {
                        photoObjectsArrayList.remove(position);
                        notifyDataSetChanged();
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}

