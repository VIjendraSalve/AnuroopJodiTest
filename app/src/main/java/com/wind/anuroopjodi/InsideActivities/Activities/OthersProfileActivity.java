package com.wind.anuroopjodi.InsideActivities.Activities;

import static android.view.View.GONE;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.anuroopjodi.Adapter.PhotosAdapter;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.LocaleHelper;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InsideActivities.Vijendra.FullPhotoZoomActivity;
import com.wind.anuroopjodi.Object.ListProfileObject;
import com.wind.anuroopjodi.Object.PhotoObjects;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.Camera;
import com.wind.anuroopjodi.my_library.CircleImageView;
import com.wind.anuroopjodi.my_library.DateTimeFormat;
import com.wind.anuroopjodi.my_library.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class OthersProfileActivity extends BaseActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private Toolbar toolbar;


    private ImageView ivProfileImage;
    private TextView tvFullName, tvAboutUs, tvMoreInformation;

    //Basic Details
    private TextView tvFirstName, tvLastName, tvDobName;
    private TextView tvBirthtime, tvBirthPlace, tvEmail;
    private TextView tvMobile, tvGender, tvMaritalStatus;
    private TextView tvCity, tvState, tvCountry, btn_report;
    private Button btn_view_email, btn_view_mobile;

    //Life Style
    private TextView tvDiet, tvSmoking, tvDrinking, tvHobby;

    //Other Information
    private TextView tvHeight, tvCaste, tvGotra, tvGan, tvNakshatra;
    private TextView tvCharan, tvManglik, tvNadi, tvRashi, tvBloodGroup;

    //Family information
    private TextView tvFatherName, tvFatherOccupaition, tvFatherContactNo, tvMotherName, tvMotherOccupaition, tvMotherContactNo, tvSibling;
    private TextView tvBrotherCount, tvSisterCount, tvMarriedSister, tvMarriedBrothers, tvUncleName, tvUncleOccupation, tvMaternalUncleName;
    private TextView tvMaternalUncleOccupation, tvExpectation;

    //Education and Jobs
    private TextView tvEducation, tvPrimaryEducationLanguage, tvOccupation, tvCompany, tvDesignation, tvIncome, tvOtherIncome;

    private ArrayList<ListProfileObject> listProfileObjects;
    private String strId, user_profile_path = null;

    private TextView btn_shortlist, btn_intrested;
    private ProgressDialog progressDialog;

    private ArrayList<PhotoObjects> photoObjectsArrayList = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private String profile_path = "";
    private RecyclerView rcv_photos;
    private int approvePhotoCount = 0;

    private CircleImageView civ_add_photo;
    private Button btn_add_photo;
    private Camera camera;
    private String profile_image_name = "", profile_image_path = "";

    private ImageView ivHoroscopeImage;
    private TextView tvMiddleName, tvNativePlace, tvCurrentWorkingCity, tvIntercast, tvDevak, tvMamakul, tvLivingStyle;
    private RelativeLayout rlBtn;
    private LinearLayout ll_family_details, ll_family_details_not_display;

    private TextView btn_block;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);

        strId = getIntent().getStringExtra("Profile_id");
//        camera = new Camera(OthersProfileActivity.this);
        _act = OthersProfileActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   toolbar.setTitle("Vendor(s) > " + strCategoryName);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        rlBtn = findViewById(R.id.rlBtn);

        tvFullName = findViewById(R.id.tvFullName);
        tvMoreInformation = findViewById(R.id.tvMoreInformation);
        ivProfileImage = findViewById(R.id.ivProfileImage);

        btn_shortlist = findViewById(R.id.btn_shortlist);
        btn_intrested = findViewById(R.id.btn_intrested);

        btn_view_email = findViewById(R.id.btn_view_email);
        btn_view_mobile = findViewById(R.id.btn_view_mobile);
        btn_report = findViewById(R.id.btn_report);
        btn_block = findViewById(R.id.btn_block);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_act, "This profile is reported to admin", Toast.LENGTH_SHORT).show();
            }
        });

        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listProfileObjects.get(0).is_blocked.equals("1")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(_act);
                    alertDialog.setTitle("Confirmation");
                    alertDialog.setMessage("Are you sure you want to block this person?");
                    alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UnblockUser(listProfileObjects.get(0).id);
                        }
                    });

                    alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();

                }else {
                    Toast.makeText(OthersProfileActivity.this, "Already blocked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OthersProfileActivity.this, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage,
                        listProfileObjects.get(0).ProfilePhoto);
                startActivity(intent);
            }
        });

        btn_intrested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!listProfileObjects.get(0).is_interest_sent.equals("1")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(_act);
                    alertDialog.setTitle("Confirmation");
                    alertDialog.setMessage("Are you sure you are to interested this person?");
                    alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SendIntrestedUser(listProfileObjects.get(0).id);
                        }
                    });

                    alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }else {
                    Toast.makeText(_act, "Already request sent!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!listProfileObjects.get(0).is_shortlisted.equals("1")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(_act);
                    alertDialog.setTitle("Confirmation");
                    alertDialog.setMessage("Are you sure you want to shortlist this person?");
                    alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            shortListUser(listProfileObjects.get(0).id);
                        }
                    });

                    alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();

                }else {
                    Toast.makeText(OthersProfileActivity.this, "Already Short listed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Basic Details
        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvDobName = findViewById(R.id.tvDobName);
        tvBirthtime = findViewById(R.id.tvBirthtime);
        tvBirthPlace = findViewById(R.id.tvBirthPlace);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvMobile);
        tvGender = findViewById(R.id.tvGender);
        tvMaritalStatus = findViewById(R.id.tvMaritalStatus);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        tvCountry = findViewById(R.id.tvCountry);
        tvExpectation = findViewById(R.id.tvExpectation);
        rcv_photos = findViewById(R.id.rcv_photos);

        ivHoroscopeImage = findViewById(R.id.ivHoroscopeImage);

        tvMiddleName = findViewById(R.id.tvMiddleName);
        tvNativePlace = findViewById(R.id.tvNativePlace);
        tvCurrentWorkingCity = findViewById(R.id.tvCurrentWorkingCity);
        tvIntercast = findViewById(R.id.tvIntercast);
        tvDevak = findViewById(R.id.tvDevak);
        tvMamakul = findViewById(R.id.tvMamakul);
        tvLivingStyle = findViewById(R.id.tvLivingStyle);


        //Life Style
        tvDiet = findViewById(R.id.tvDiet);
        tvSmoking = findViewById(R.id.tvSmoking);
        tvDrinking = findViewById(R.id.tvDrinking);
        tvHobby = findViewById(R.id.tvHobby);


        //Other Information
        tvHeight = findViewById(R.id.tvHeight);
        tvCaste = findViewById(R.id.tvCaste);
        tvGotra = findViewById(R.id.tvGotra);
        tvGan = findViewById(R.id.tvGan);
        tvNakshatra = findViewById(R.id.tvNakshatra);
        tvCharan = findViewById(R.id.tvCharan);
        tvManglik = findViewById(R.id.tvManglik);
        tvNadi = findViewById(R.id.tvNadi);
        tvRashi = findViewById(R.id.tvRashi);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);


        //Family Details

        tvFatherName = findViewById(R.id.tvFatherName);
        tvFatherOccupaition = findViewById(R.id.tvFatherOccupaition);
        tvFatherContactNo = findViewById(R.id.tvFatherContactNo);
        tvMotherName = findViewById(R.id.tvMotherName);
        tvMotherOccupaition = findViewById(R.id.tvMotherOccupaition);
        tvMotherContactNo = findViewById(R.id.tvMotherContactNo);
        tvSibling = findViewById(R.id.tvSibling);
        tvBrotherCount = findViewById(R.id.tvBrotherCount);
        tvSisterCount = findViewById(R.id.tvSisterCount);
        tvMarriedSister = findViewById(R.id.tvMarriedSister);
        tvMarriedBrothers = findViewById(R.id.tvMarriedBrothers);
        tvUncleName = findViewById(R.id.tvUncleName);
        tvUncleOccupation = findViewById(R.id.tvUncleOccupation);
        tvMaternalUncleName = findViewById(R.id.tvMaternalUncleName);
        tvMaternalUncleOccupation = findViewById(R.id.tvMaternalUncleOccupation);


        //Education And Job
        tvEducation = findViewById(R.id.tvEducation);
        tvPrimaryEducationLanguage = findViewById(R.id.tvPrimaryEducationLanguage);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvCompany = findViewById(R.id.tvCompany);
        tvDesignation = findViewById(R.id.tvDesignation);
        tvIncome = findViewById(R.id.tvIncome);
        tvOtherIncome = findViewById(R.id.tvOtherIncome);
        ll_family_details = findViewById(R.id.ll_family_details);
        ll_family_details_not_display = findViewById(R.id.ll_family_details_not_display);

        webCallProductList();
        getPhotos();
        init();
    }

    private void UnblockUser(String memberId) {
        progressDialog = new ProgressDialog(OthersProfileActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        Log.d("Values", "memberId: " + memberId);
        Log.d("Values", "USER_ID: " + SharedPref.getPrefs(OthersProfileActivity.this, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.unblockUser(
                memberId,
                SharedPref.getPrefs(OthersProfileActivity.this, IConstant.USER_ID)
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

                       onBackPressed();

                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(OthersProfileActivity.this, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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


    private void init() {

        btn_view_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMobileEmail("1"); //1:mobile 2:email
            }
        });

        btn_view_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMobileEmail("2"); //1:mobile 2:email
            }
        });

    }

    private void viewMobileEmail(String flag) {
        progressDialog = new ProgressDialog(_act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(_act.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("Information", "userID: " + SharedPref.getPrefs(_act, IConstant.USER_ID));
        Log.d("Information", "otherProfileID: " + listProfileObjects.get(0).id);
        Log.d("Information", "flag: " + flag);

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.view(
                listProfileObjects.get(0).id,
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                flag
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

                        JSONObject jsonObject1 = jsonObject.getJSONObject("profile");

                        if (flag.equals("1")) {
                            if (jsonObject1.getString("is_mobile_view").equals("1")) {
                                tvMobile.setText(listProfileObjects.get(0).mobileNumber);
                                btn_view_mobile.setVisibility(GONE);
                            } else {
                                Toast.makeText(OthersProfileActivity.this, "You are not allowed to view this number, Please update your package", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (flag.equals("2")) {
                            if (jsonObject1.getString("is_email_view").equals("1")) {
                                tvEmail.setText(listProfileObjects.get(0).emailId);
                                btn_view_email.setVisibility(GONE);
                            } else {
                                Toast.makeText(OthersProfileActivity.this, "You are not allowed to view this email, Please update your package", Toast.LENGTH_SHORT).show();
                            }
                        }

                       /* if(flag.equals("2")){
                            tvEmail.setText(listProfileObjects.get(0).emailId);
                            btn_view_email.setVisibility(GONE);
                        }else if(flag.equals("1")){
                            tvMobile.setText(listProfileObjects.get(0).mobileNumber);
                            btn_view_mobile.setVisibility(GONE);
                        }*/
                    } else {
                        progressDialog.dismiss();
                    }
                    //Toast.makeText(_act, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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

    private void shortListUser(String memberId) {
        progressDialog = new ProgressDialog(_act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(_act.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("Values", "memberId: " + memberId);
        Log.d("Values", "USER_ID: " + SharedPref.getPrefs(_act, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.shortList(
                memberId,
                SharedPref.getPrefs(_act, IConstant.USER_ID)
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
                        listProfileObjects.get(0).setIs_shortlisted("1");
                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(_act, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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

    private void SendIntrestedUser(String memberId) {
        progressDialog = new ProgressDialog(_act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(_act.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("Values", "memberId: " + memberId);
        Log.d("Values", "USER_ID: " + SharedPref.getPrefs(_act, IConstant.USER_ID));

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.sendIntrested(
                memberId,
                SharedPref.getPrefs(_act, IConstant.USER_ID)
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

                    } else {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(_act, "" + jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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

    private void webCallProductList() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTProfileById(
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                strId
        );

        listProfileObjects = new ArrayList<>();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag4", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);

                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            JSONObject profile = jsonObject.getJSONObject("profile");
                            user_profile_path = jsonObject.getString("user_profile_path");
                            listProfileObjects.add(new ListProfileObject(jsonObject.getJSONObject("profile"), user_profile_path));

                            if (listProfileObjects.size() == 0) {
                                Helper_Method.dismissProgessBar();
                            } else {

                                Log.d("Profile", "onResponse: My Connection :"+listProfileObjects.get(0).is_my_connection);
                                if (listProfileObjects.get(0).is_my_connection.equals("1")) {
                                    rlBtn.setVisibility(GONE);
                                    if (listProfileObjects.get(0).is_shortlisted.equals("1")) {
                                        btn_shortlist.setText("ShortListed");
                                    } else {
                                        btn_shortlist.setText("Short List");
                                    }

                                    Log.d("IntrestSent", "onResponse: "+listProfileObjects.get(0).is_interest_sent);
                                    if (listProfileObjects.get(0).is_interest_sent.equals("1")) {
                                        btn_intrested.setText("Request already sent");
                                        ll_family_details.setVisibility(View.VISIBLE);
                                        ll_family_details_not_display.setVisibility(GONE);
                                    } else {
                                        btn_intrested.setText(" Send Request");
                                        ll_family_details.setVisibility(View.GONE);
                                        ll_family_details_not_display.setVisibility(View.VISIBLE);
                                    }

                                }
                                else
                                {
                                    rlBtn.setVisibility(View.VISIBLE);
                                    if (listProfileObjects.get(0).is_shortlisted.equals("1")) {
                                        btn_shortlist.setText("ShortListed");
                                    } else {
                                        btn_shortlist.setText("Short List");
                                    }

                                    Log.d("IntrestSent", "onResponse: "+listProfileObjects.get(0).is_interest_sent);
                                    if (listProfileObjects.get(0).is_interest_sent.equals("1")) {
                                        btn_intrested.setText("Request already sent");
                                        ll_family_details.setVisibility(View.VISIBLE);
                                        ll_family_details_not_display.setVisibility(GONE);
                                    } else {
                                        btn_intrested.setText(" Send Request");
                                        ll_family_details.setVisibility(View.GONE);
                                        ll_family_details_not_display.setVisibility(View.VISIBLE);
                                    }
                                }


                               /* if(listProfileObjects.get(0).is_my_connection.equals("1")){
                                    btn_intrested.setVisibility(View.INVISIBLE);
                                }else {
                                    btn_intrested.setVisibility(View.VISIBLE);
                                }*/


                                toolbar.setTitle(listProfileObjects.get(0).firstName);
                                tvFullName.setText(listProfileObjects.get(0).firstName);
                                tvMoreInformation.setText(listProfileObjects.get(0).user_height + " | " + listProfileObjects.get(0).weight + "(in kg) | " + listProfileObjects.get(0).education_name + " | "
                                        + listProfileObjects.get(0).city_name + ", " + listProfileObjects.get(0).state_name + ", india | " + listProfileObjects.get(0).occupation_name);

                                Log.d("ProfileImage", "act: " + listProfileObjects.get(0).ProfilePhoto);

                                Glide.with(_act)
                                        .load(listProfileObjects.get(0).ProfilePhoto)
                                        .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                                        .into(ivProfileImage);


                                if (!jsonObject.getString("horoscope_image").equals("")) {

                                    Glide.with(OthersProfileActivity.this)
                                            .load(jsonObject.getString("horoscope_profile_path") + jsonObject.getString("horoscope_image"))
                                            .into(ivHoroscopeImage);

                                } else {
                                    Glide.with(OthersProfileActivity.this)
                                            .load(R.drawable.no_image_available)
                                            .into(ivHoroscopeImage);
                                }

                                String pathHoroscope = jsonObject.getString("horoscope_profile_path") + jsonObject.getString("horoscope_image");

                                ivHoroscopeImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(OthersProfileActivity.this, FullPhotoZoomActivity.class);
                                        intent.putExtra(IConstant.UserImage, pathHoroscope);
                                        startActivity(intent);
                                    }
                                });

                                tvAboutUs.setText(listProfileObjects.get(0).profileDescription);
                                tvFirstName.setText(listProfileObjects.get(0).firstName);
                                tvLastName.setText(listProfileObjects.get(0).lastName);
                                tvDobName.setText(DateTimeFormat.getDate2(listProfileObjects.get(0).birthDate));
                                tvBirthtime.setText(listProfileObjects.get(0).birthTime);
                                tvBirthPlace.setText(listProfileObjects.get(0).birthPlace);

                                if (profile.getString("is_email_view").equals("1")) {
                                    tvEmail.setText(listProfileObjects.get(0).emailId);
                                    btn_view_email.setVisibility(GONE);
                                } else {
                                    btn_view_email.setVisibility(View.VISIBLE);
                                    tvEmail.setText("*************");
                                }

                                if (profile.getString("is_mobile_view").equals("1")) {
                                    tvMobile.setText(listProfileObjects.get(0).mobileNumber);
                                    btn_view_mobile.setVisibility(GONE);
                                } else {
                                    btn_view_mobile.setVisibility(View.VISIBLE);
                                    tvMobile.setText("**********");
                                }

                                tvGender.setText(listProfileObjects.get(0).gender);
                                tvMaritalStatus.setText(listProfileObjects.get(0).marital_status);
                                tvCity.setText(listProfileObjects.get(0).city_name);
                                tvState.setText(listProfileObjects.get(0).state_name);
                                tvCountry.setText("India");
                                //   tvDiet.setText(SharedPref.getPrefs(_act,IConstant.));
                                tvSmoking.setText(listProfileObjects.get(0).smoking);
                                tvDrinking.setText(listProfileObjects.get(0).drinking);
                                tvHobby.setText(listProfileObjects.get(0).hobby);
                                tvExpectation.setText(listProfileObjects.get(0).expectaion);

                                tvHeight.setText(listProfileObjects.get(0).user_height);
                                tvCaste.setText(listProfileObjects.get(0).Caste_name);
                                Log.d("Caste", "Caste: "+listProfileObjects.get(0).Cast);
                                tvGotra.setText(listProfileObjects.get(0).gotra);
                                tvGan.setText(listProfileObjects.get(0).gan);
                                tvNakshatra.setText(listProfileObjects.get(0).nakshatra);
                                tvCharan.setText(listProfileObjects.get(0).charan);
                                tvManglik.setText(listProfileObjects.get(0).manglik);
                                tvNadi.setText(listProfileObjects.get(0).nadi);
                                tvRashi.setText(listProfileObjects.get(0).rashi);
                                tvBloodGroup.setText(listProfileObjects.get(0).blood);

                                tvFatherName.setText(listProfileObjects.get(0).fatherName);
                                tvFatherOccupaition.setText(listProfileObjects.get(0).father_occupation);
                                tvFatherContactNo.setText(listProfileObjects.get(0).father_contact);
                                tvMotherName.setText(listProfileObjects.get(0).motherName);
                                tvMotherOccupaition.setText(listProfileObjects.get(0).mother_occupation);
                                tvMotherContactNo.setText(listProfileObjects.get(0).mother_contact);
                                if (listProfileObjects.get(0).sibling.equals("1"))
                                    tvSibling.setText("Yes");
                                else
                                    tvSibling.setText("No");
                                tvBrotherCount.setText(listProfileObjects.get(0).brother);
                                tvSisterCount.setText(listProfileObjects.get(0).sister);
                                tvMarriedSister.setText(listProfileObjects.get(0).married_sister);
                                tvMarriedBrothers.setText(listProfileObjects.get(0).married_brother);
                                tvUncleName.setText(listProfileObjects.get(0).uncle_name);
                                tvUncleOccupation.setText(listProfileObjects.get(0).uncle_occupation);
                                tvMaternalUncleName.setText(listProfileObjects.get(0).m_uncle_name);
                                tvMaternalUncleOccupation.setText(listProfileObjects.get(0).mama_occupation);

                                tvEducation.setText(listProfileObjects.get(0).education_name);
                                tvPrimaryEducationLanguage.setText(listProfileObjects.get(0).language_name);
                                tvOccupation.setText(listProfileObjects.get(0).occupation_name);
                                tvCompany.setText(listProfileObjects.get(0).company);
                                tvDesignation.setText(listProfileObjects.get(0).designation);
                                tvIncome.setText(listProfileObjects.get(0).income);
                                tvOtherIncome.setText(listProfileObjects.get(0).otherIncome);

                                tvMiddleName.setText(listProfileObjects.get(0).MiddleName);
                                tvNativePlace.setText(listProfileObjects.get(0).NativePlace);
                                tvCurrentWorkingCity.setText(listProfileObjects.get(0).working_city_present_name);
                                Log.d("TCheck", "onResponse: "+listProfileObjects.get(0).working_city_present_name);
                                tvIntercast.setText(listProfileObjects.get(0).Intercast);
                                tvDevak.setText(listProfileObjects.get(0).Devak);
                                tvMamakul.setText(listProfileObjects.get(0).Mamakul);
                                tvLivingStyle.setText(listProfileObjects.get(0).LivingStyle);


                                Helper_Method.dismissProgessBar();
                            }

                        } else {

                            showPopup(jsonObject.getString("reason"));
                            Helper_Method.dismissProgessBar();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, t.getMessage());
                Log.d("Error", "onFailure: " + t.getMessage());
                if (t instanceof NetworkErrorException)
                    Helper_Method.warnUser(_act, "Network Error", getString(R.string.error_network), true);
                else if (t instanceof IOException)
                    Helper_Method.warnUser(_act, "Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ServerError)
                    //   Helper_Method.warnUser(_act, "Server Error", getString(R.string.error_server), true);
                else if (t instanceof ConnectException)
                    Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                    //else if (t instanceof ConnectException)
                    //Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                else if (t instanceof TimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else if (t instanceof SocketTimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else
                    Helper_Method.warnUser(_act, "Unknown Error", getString(R.string.error_something_wrong), true);

            }
        });
    }

    private void showPopup(String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OthersProfileActivity.this);
        builder.setTitle("Message");
        builder.setMessage(Message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                onBackPressed();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                onBackPressed();
            }
        });
        builder.show();

    }

    private void getPhotos() {
        progressDialog = new ProgressDialog(_act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(_act.getResources().getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
        photoObjectsArrayList.clear();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getPhotos(
                strId
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
                       /* remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse: " + remainingCount);*/
                        profile_path = jsonObject.getString("user_profile_path");
                        JSONArray jsonArray = jsonObject.getJSONArray("profiles_images");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                photoObjectsArrayList.add(new PhotoObjects(obj));
                                if (obj.getString("approved").equals("1")) {
                                    approvePhotoCount = approvePhotoCount + 1;
                                }
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();


                    if (photoObjectsArrayList.isEmpty()) {
                        rcv_photos.setVisibility(GONE);
                    } else
                        rcv_photos.setVisibility(View.VISIBLE);

                    if (approvePhotoCount > 0) {
                        rcv_photos.setVisibility(View.VISIBLE);
                    } else {
                        rcv_photos.setVisibility(GONE);
                    }

                    photosAdapter = new PhotosAdapter(OthersProfileActivity.this, photoObjectsArrayList, profile_path);
                    rcv_photos.setLayoutManager(new LinearLayoutManager(OthersProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    rcv_photos.setItemAnimator(new DefaultItemAnimator());
                    rcv_photos.setAdapter(photosAdapter);
                    photosAdapter.notifyDataSetChanged();


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_block_unblock_user")
        public Call<ResponseBody> unblockUser(
                @Field("otherUserId") String otherUserId,
                @Field("userId") String userId
        );

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

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_profile_image_list")
        public Call<ResponseBody> getPhotos(
                @Field("userId") String userId
        );

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_other_user_mobile_view")
        public Call<ResponseBody> view(
                @Field("otherUserId") String otherUserId,
                @Field("userId") String userId,
                @Field("view_flag") String view_flag
        );

    }


}