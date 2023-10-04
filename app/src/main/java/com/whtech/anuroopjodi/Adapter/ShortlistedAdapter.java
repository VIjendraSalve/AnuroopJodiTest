package com.whtech.anuroopjodi.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.InsideActivities.Activities.OthersProfileActivity;
import com.whtech.anuroopjodi.Object.ListProfileObject;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ShortlistedAdapter extends RecyclerView.Adapter<ShortlistedAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ListProfileObject> listProfileObjectArrayList, filterList;
    private Context mContext;

    //private KProgressHUD hud;
    private Dialog dialog;
    private TextView tvBusinessName, tvName, tvEmail, tvCall, tvCancel, tvSubmit;
    private String strUserId, UserType, strImage, strName, strEmail, strMobile, strAddress;
    int pos = 0;
    private ProgressDialog progressDialog;
    public RefreshActivity refreshActivity;


    public ShortlistedAdapter(Context mContext, ArrayList<ListProfileObject> listProfileObjectArrayList) {

        inflater = LayoutInflater.from(mContext);
        this.listProfileObjectArrayList = listProfileObjectArrayList;
        //this.filterList = new ArrayList<ListProfileObject>();
        // this.filterList.addAll(this.listProfileObjectArrayList);
        this.mContext = mContext;

    }

    public interface RefreshActivity {
        public void Refresh();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.new_shortlisted_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        pos = position;
        //ListProfileObject listItem = filterList.get(position);
        Log.d("UserName", "onBindViewHolder: " + listProfileObjectArrayList.get(position).firstName + " - "
                + listProfileObjectArrayList.get(position).profile_id);
        holder.tvBusinessName.setTypeface(holder.tvBusinessName.getTypeface(), Typeface.BOLD);
        holder.tvBusinessName.setText(listProfileObjectArrayList.get(position).firstName + " / " + listProfileObjectArrayList.get(position).profile_id);
        holder.tvAddress.setText(listProfileObjectArrayList.get(position).city_name);
        //holder.tvExperience.setText(listProfileObjectArrayList.get(position).contact);

        if (listProfileObjectArrayList.get(position).birthDate != null &&
                !listProfileObjectArrayList.get(position).birthDate.equals("null") &&
                listProfileObjectArrayList.get(position).birthDate.isEmpty()) {

            Log.d("Birthdate", "1997-05-10 onBindViewHolder: " + listProfileObjectArrayList.get(position).birthDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // get current date time with Date()
            Date date = new Date();
            Date date1 = null, date2 = null;

            try {

                date1 = simpleDateFormat.parse(listProfileObjectArrayList.get(position).birthDate);
                date2 = simpleDateFormat.parse(dateFormat.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            long different = date2.getTime() - date1.getTime();
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long yearsInMilli = daysInMilli * 365;

            long elapsedYears = different / yearsInMilli;
            different = different % yearsInMilli;

            Log.d("Birthdate", "years: " + elapsedYears);
            Log.d("Birthdate", "height: " + listProfileObjectArrayList.get(position).height);

            holder.tvExperience.setText("" +
                    elapsedYears + " Years, " +
                    listProfileObjectArrayList.get(position).user_height + ", " +
//                listProfileObjectArrayList.get(position).city_name +", "+
                    listProfileObjectArrayList.get(position).subCast + ", " +
                    listProfileObjectArrayList.get(position).education_name);

        } else {
            holder.tvExperience.setText("" +
                    listProfileObjectArrayList.get(position).user_height + ", " +
//                listProfileObjectArrayList.get(position).city_name +", "+
                    listProfileObjectArrayList.get(position).subCast + ", " +
                    listProfileObjectArrayList.get(position).education_name);
        }

//        DisplayMetrics metricscard = mContext.getResources().getDisplayMetrics();
//        int cardwidth = metricscard.widthPixels;
//        int cardheight = metricscard.heightPixels;
//        holder.ivProfile.getLayoutParams().height = (int) (cardheight / 5.5);
//        holder.ivProfile.getLayoutParams().width = (int) (cardwidth / 2.5);
//        holder.rlImage.getLayoutParams().width = (int) (cardwidth / 1);

        Glide.with(mContext)
                .load(listProfileObjectArrayList.get(position).ProfilePhoto)
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
                .into(holder.ivProfile);


        holder.tvInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listProfileObjectArrayList.get(position).is_shortlisted.equals("0")) {
                    confirmation_alert(listProfileObjectArrayList.get(position).id, position);
                    Log.d("Values", "onClick: " + listProfileObjectArrayList.get(position).id);
                    Log.d("Values", "position: " + position);
                } else {
                    Toast.makeText(mContext, "Already Shortlisted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.tvSendIntrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation_sendIntrest(listProfileObjectArrayList.get(position).id, position);
                Log.d("Values", "onClick: " + listProfileObjectArrayList.get(position).id);
                Log.d("Values", "position: " + position);
            }
        });

        Log.d("Count: ", "onBindViewHolder: " + listProfileObjectArrayList.size());
        Log.d("position: ", "onBindViewHolder: " + position);

        if (listProfileObjectArrayList.get(position).is_interest_sent.equals("1")) {
            holder.tvSendIntrested.setText("Request Sent");
            holder.tvSendIntrested.setBackground(mContext.getResources().getDrawable(R.drawable.round_green_button));
        } else {
            holder.tvSendIntrested.setText("Send Interest");
            holder.tvSendIntrested.setBackground(mContext.getResources().getDrawable(R.drawable.round_button));
        }

        if (listProfileObjectArrayList.get(position).is_shortlisted.equals("1")) {
            holder.tvInterest.setText("Shortlisted");
            holder.tvInterest.setBackground(mContext.getResources().getDrawable(R.drawable.round_green_button));
        } else {
            holder.tvInterest.setText("Shortlist");
            holder.tvInterest.setBackground(mContext.getResources().getDrawable(R.drawable.round_button));
        }

        if (listProfileObjectArrayList.get(position).is_my_connection.equals("1")) {
            holder.tvSendIntrested.setVisibility(View.INVISIBLE);
        } else {
            holder.tvSendIntrested.setVisibility(View.VISIBLE);
        }


        holder.tvSendIntrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listProfileObjectArrayList.get(position).is_interest_sent.equals("0")) {
                    confirmation_sendIntrest(listProfileObjectArrayList.get(position).id, position);
                    Log.d("Values", "onClick: " + listProfileObjectArrayList.get(position).id);
                    Log.d("Values", "position: " + position);
                } else {
                    Toast.makeText(mContext, "Request Already Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OthersProfileActivity.class);
                intent.putExtra("Profile_id", listProfileObjectArrayList.get(position).id);
                mContext.startActivity(intent);
            }
        });


        holder.image_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.image_heart_red.setVisibility(View.VISIBLE);
                holder.image_heart.setVisibility(View.GONE);
            }
        });

    }

    private void confirmation_alert(final String memberId, final int position1) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are you sure you want to Shortlist this person?");
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                shortListUser(memberId, position1);
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void confirmation_sendIntrest(final String memberId, final int position1) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are you sure you want to send request to this person?");
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SendIntrestedUser(memberId, position1);
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return listProfileObjectArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBusinessName, tvAddress, tvInterest, tvSendIntrested,/* tvRatings, tvTotalRatings,*/
                tvExperience/*, tvLikeCount*/;
        // RatingBar ratingBar;
        private ImageView ivProfile, image_heart, image_heart_red;/*, ivShare, ivLike, ivUnlike*/
        ;
        //private TextView tvCall;
        private RelativeLayout rlImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvBusinessName = (TextView) itemView.findViewById(R.id.tvBusinessName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvInterest = (TextView) itemView.findViewById(R.id.tvInterest);
            tvSendIntrested = (TextView) itemView.findViewById(R.id.tvSendIntrested);
            //tvRatings = (TextView) itemView.findViewById(R.id.tvRatings);
            // ratingBar = itemView.findViewById(R.id.ratingBar);
            // tvTotalRatings = (TextView) itemView.findViewById(R.id.tvTotalRatings);
            tvExperience = (TextView) itemView.findViewById(R.id.tvExperience);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            //tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);

            // ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            // ivLike = (ImageView) itemView.findViewById(R.id.ivLike);
            // ivUnlike = (ImageView) itemView.findViewById(R.id.ivUnlike);
            rlImage = itemView.findViewById(R.id.rlImage);
            image_heart = itemView.findViewById(R.id.image_heart);
            image_heart_red = itemView.findViewById(R.id.image_heart_red);


        }

    }

    // Do Search...
    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {

                    filterList.addAll(listProfileObjectArrayList);

                } else {
                    // Iterate in the original List and add it to filter list...
                    for (ListProfileObject item : listProfileObjectArrayList) {
                        if (item.firstName.toLowerCase().contains(text.toLowerCase()) /*||
                                item.address.toLowerCase().contains(text.toLowerCase())*/) {
                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        ((Activity) mContext).startActivityForResult(intent, 101);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @NonNull
    private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

    private void shortListUser(String memberId, final int position) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(mContext.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        Log.d("Values", "memberId: " + memberId);
        Log.d("Values", "USER_ID: " + SharedPref.getPrefs(mContext, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.shortList(
                memberId,
                SharedPref.getPrefs(mContext, IConstant.USER_ID)
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
                        //refreshActivity.Refresh();
                        notifyDataSetChanged();

                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void SendIntrestedUser(String memberId, final int position) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(mContext.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        Log.d("Values", "memberId: " + memberId);
        Log.d("Values", "USER_ID: " + SharedPref.getPrefs(mContext, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.sendIntrested(
                memberId,
                SharedPref.getPrefs(mContext, IConstant.USER_ID)
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
//                        refreshActivity.Refresh();
                        notifyDataSetChanged();

                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(mContext, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private interface GetOrderAPI {
        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_add_to_shortlist")
        public Call<ResponseBody> shortList(
                @Field("otherUserId") String otherUserId,
                @Field("userId") String userId
        );

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_send_request")
        public Call<ResponseBody> sendIntrested(
                @Field("otherUserId") String otherUserId,
                @Field("userId") String userId
        );

    }


}
