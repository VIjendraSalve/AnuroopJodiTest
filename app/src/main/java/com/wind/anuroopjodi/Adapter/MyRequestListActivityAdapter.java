package com.wind.anuroopjodi.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.InsideActivities.Activities.OthersProfileActivity;
import com.wind.anuroopjodi.Object.ListProfileObject;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.MyConfig;

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

public class MyRequestListActivityAdapter extends RecyclerView.Adapter<MyRequestListActivityAdapter.SponsorsHolder> {

    private ArrayList<ListProfileObject> listProfileObjectArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;

    public MyRequestListActivityAdapter(ArrayList<ListProfileObject> listProfileObjectArrayList) {
        this.listProfileObjectArrayList = listProfileObjectArrayList;
    }

    @Override
    public MyRequestListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_request_adapter_row_item, parent, false);

        return new MyRequestListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyRequestListActivityAdapter.SponsorsHolder holder, final int position) {
        final ListProfileObject familyMember = listProfileObjectArrayList.get(position);

        holder.tvBusinessName.setTypeface(holder.tvBusinessName.getTypeface(), Typeface.BOLD);

        holder.tvBusinessName.setText(Helper_Method.toTitleCase(listProfileObjectArrayList.get(position).firstName) + " / " + listProfileObjectArrayList.get(position).profile_id);
        holder.tvAddress.setText(listProfileObjectArrayList.get(position).city_name);
        //holder.tvExperience.setText(imageModelArrayList.get(position).contact);

        if (listProfileObjectArrayList.get(position).birthDate != null &&
                !listProfileObjectArrayList.get(position).birthDate.equals("null") &&
                listProfileObjectArrayList.get(position).birthDate.isEmpty()) {

        Log.d("Birthdate", "1997-05-10 onBindViewHolder: "+listProfileObjectArrayList.get(position).birthDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // get current date time with Date()
        Date date = new Date();
        Date date1=null, date2=null;

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

        Log.d("Birthdate", "years: "+elapsedYears);
        Log.d("Birthdate", "height: "+listProfileObjectArrayList.get(position).height);

        holder.tvExperience.setText("" +
                elapsedYears + " Years, "+
                listProfileObjectArrayList.get(position).user_height + ", "+
//                listProfileObjectArrayList.get(position).city_name +", "+
                listProfileObjectArrayList.get(position).Caste_name +", "+
                listProfileObjectArrayList.get(position).education_name);
        } else {
            holder.tvExperience.setText("" +
                    listProfileObjectArrayList.get(position).user_height + ", " +
//                listProfileObjectArrayList.get(position).city_name +", "+
                    listProfileObjectArrayList.get(position).Caste_name + ", " +
                    listProfileObjectArrayList.get(position).education_name);
        }

        DisplayMetrics metricscard = context.getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        holder.ivProfile.getLayoutParams().height = (int) (cardheight / 5.5);
        holder.ivProfile.getLayoutParams().width = (int) (cardwidth / 2.5);
        holder.rlImage.getLayoutParams().width = (int) (cardwidth / 1);

        Glide.with(context)
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OthersProfileActivity.class);
                intent.putExtra("Profile_id", listProfileObjectArrayList.get(position).id);
                context.startActivity(intent);
            }
        });

        holder.tvAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation_alert(listProfileObjectArrayList.get(position).id, position, "1");
                Log.d("Values", "onClick: "+listProfileObjectArrayList.get(position).id);
                Log.d("Values", "position: "+position);
            }
        });

        holder.tvRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmation_alert(listProfileObjectArrayList.get(position).id, position, "2");
                Log.d("Values", "onClick: "+listProfileObjectArrayList.get(position).id);
                Log.d("Values", "position: "+position);
            }
        });

    }

    private void confirmation_alert(final String memberId, final int position1, String status) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Confirmation");
        if(status.equals("1"))
        alertDialog.setMessage("Are you sure, you want to accept this person request?");
        else
            alertDialog.setMessage("Are you sure, you want to reject this person request?");
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                requestPersonFunction(memberId, position1, status);
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

    private void requestPersonFunction(String memberId, final int position, String status) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        Log.d("Values", "memberId: "+memberId);
        Log.d("Values", "USER_ID: "+SharedPref.getPrefs(context, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.acceptRejectRequest(
                memberId,
                SharedPref.getPrefs(context, IConstant.USER_ID),
                status
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

                        listProfileObjectArrayList.remove(position);
                        notifyDataSetChanged();

                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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
        @POST(MyConfig.BrahminMatrimony + "/app_accept_reject_request")
        public Call<ResponseBody> acceptRejectRequest(
                @Field("otherUserId") String otherUserId,
                @Field("userId") String userId,
                @Field("status") String status
        );
    }

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        TextView tvBusinessName, tvAddress, tvAcceptRequest, tvRejectRequest,/* tvRatings, tvTotalRatings,*/
                tvExperience/*, tvLikeCount*/;
        // RatingBar ratingBar;
        private ImageView ivProfile/*, ivShare, ivLike, ivUnlike*/;
        //private TextView tvCall;
        private RelativeLayout rlImage;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();

            tvBusinessName = (TextView) itemView.findViewById(R.id.tvBusinessName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvAcceptRequest = (TextView) itemView.findViewById(R.id.tvAcceptRequest);
            tvRejectRequest = (TextView) itemView.findViewById(R.id.tvRejectRequest);
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


        }
    }

}

