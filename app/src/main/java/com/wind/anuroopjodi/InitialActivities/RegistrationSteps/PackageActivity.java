package com.wind.anuroopjodi.InitialActivities.RegistrationSteps;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wind.anuroopjodi.Adapter.PackageAdapter;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IErrors;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.RecyclerItemClickListener;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.LoginActivity;
import com.wind.anuroopjodi.InsideActivities.Vijendra.BaseActivityForPackage;
import com.wind.anuroopjodi.Object.PackageObject;
import com.wind.anuroopjodi.R;

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

public class PackageActivity extends BaseActivityForPackage {

    private static final String TAG = "PackageActivity";
    private Activity _act;
    private TextView tvTermsAndConditions, btnCancel;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private ImageView ivScanImage;

    private ArrayList<PackageObject> packageObjectArrayList;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvPackageList;
    private RelativeLayout noInternet, progress;
    private PackageAdapter packageAdapter;
    private String qr_code_path = null, user_profile_path = null, strPackageId = "0", strPackageAmt = "0.0";
    private EditText etTransactionId;
    private EditText etTransaction;
    private String strActivityName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        // strActivityName = getIntent().getStringExtra("ActivityName");

        _act = PackageActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_package));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

/*
        AlertDialog.Builder builder = new AlertDialog.Builder(PackageActivity.this);
        builder.setTitle("Message");
        builder.setMessage("If You already purchase/upgrade package and waiting for payment approval please select Yes or" +
                " select Cancel to proceed.");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PackageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();*/

        btnCancel = findViewById(R.id.btnCancel);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        String first = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_one) + "</font>";
        String second = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_terms_n_cond_two) + "</font>";
        String third = "<font color='#000000'>" + getResources().getString(R.string.lbl_and) + "</font>";
        String fourth = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_privacy_policy) + "</font>";
        String fifth = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_three) + "</font>";
        tvTermsAndConditions.setText(Html.fromHtml(first + second + third + fourth + fifth));

        ///Product List Object
        packageObjectArrayList = new ArrayList<>();
        rvPackageList = (RecyclerView) findViewById(R.id.rvPackageList);
        progress = (RelativeLayout) findViewById(R.id.progress);
        noInternet = (RelativeLayout) findViewById(R.id.noInternet);
        rvPackageList.setNestedScrollingEnabled(false);
        noInternet.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        rvPackageList.setVisibility(View.VISIBLE);
        ivScanImage = findViewById(R.id.ivScanImage);
        etTransactionId = findViewById(R.id.etTransactionId);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Default Webcall
        if (connectionDetector.isConnectionAvailable()) {
            webServiceCallMyOrder();
        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
        }


        rvPackageList.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        for (int i = 0; i < packageObjectArrayList.size(); i++) {

                            if (i == position) {
                                packageObjectArrayList.get(i).setSelected(true);
                            } else {
                                packageObjectArrayList.get(i).setSelected(false);
                            }
                        }

                        // pos = profileCreatedForAdapter.setSelected(position);
                        packageAdapter.notifyDataSetChanged();

                    }
                })
        );

        findViewById(R.id.btnUpdatePackage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Helper_Method.intentActivity(_act, DashboardActivity.class, false);
                for (int i = 0; i < packageObjectArrayList.size(); i++) {

                    if (packageObjectArrayList.get(i).isSelected()) {
                        strPackageId = packageObjectArrayList.get(i).id;
                        strPackageAmt = packageObjectArrayList.get(i).packageAmount;
                    }

                }
                Log.d(TAG, "onClick: PackageAmount");

                if (!strPackageId.equalsIgnoreCase("0")) {
                    if (strPackageAmt.equalsIgnoreCase("0.00")) {
                        webcallPackageUpdate("");
                    } else {

                        webcallPackageUpdate(etTransactionId.getText().toString().trim());

                    }
                } else {
                    Helper_Method.toaster(_act, "Select Package");
                }
            }
        });


    }

    private void webcallPackageUpdate(String transactionId) {

        Helper_Method.showProgressBar(_act, "Updating Package Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTPackageUpdate(
                strPackageId,
                "",
                transactionId, SharedPref.getPrefs(_act, IConstant.USER_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);

                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {
                            user_profile_path = i.getString("user_profile_path");


                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                            SharedPref.setPrefs(_act, IConstant.USER_ID, String.valueOf(jsonObjectData.getString("id")));
                            SharedPref.setPrefs(_act, IConstant.PACKAGE, String.valueOf(jsonObjectData.getString("package")));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_TIME, String.valueOf(jsonObjectData.getString("birthTime")));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_PLACE, String.valueOf(jsonObjectData.getString("birthPlace")));
                            SharedPref.setPrefs(_act, IConstant.ABOUT_ME, String.valueOf(jsonObjectData.getString("profileDescription")));
                            //SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("loginId"));
                            SharedPref.setPrefs(_act, IConstant.USER_PHOTO, String.valueOf(user_profile_path + jsonObjectData.getString("ProfilePhoto")));
                            SharedPref.setPrefs(_act, IConstant.PHOTO_APPROVED, String.valueOf(jsonObjectData.getString("photoApproved")));
                            SharedPref.setPrefs(_act, IConstant.PROFILE_ID, String.valueOf(jsonObjectData.getString("registrationNumber")));
                            SharedPref.setPrefs(_act, IConstant.USER_FIRST_NAME, String.valueOf(jsonObjectData.getString("firstName")));
                            SharedPref.setPrefs(_act, IConstant.USER_EMAIL, String.valueOf(jsonObjectData.getString("emailId")));
                            SharedPref.setPrefs(_act, IConstant.DISABILITY, String.valueOf(jsonObjectData.getString("disability")));
                            SharedPref.setPrefs(_act, IConstant.STATE_ID, String.valueOf(jsonObjectData.getString("state")));
                            SharedPref.setPrefs(_act, IConstant.CITY_ID, String.valueOf(jsonObjectData.getString("city")));
                            SharedPref.setPrefs(_act, IConstant.USER_MOBILE, String.valueOf(jsonObjectData.getString("mobileNumber")));
                            SharedPref.setPrefs(_act, IConstant.USER_LAST_NAME, String.valueOf(jsonObjectData.getString("lastName")));
                            SharedPref.setPrefs(_act, IConstant.USER_GENDER, String.valueOf(jsonObjectData.getString("gender")));
                            SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS_ID, String.valueOf(jsonObjectData.getString("maritalStatus")));
                            SharedPref.setPrefs(_act, IConstant.GOTRA, String.valueOf(jsonObjectData.getString("gotra")));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_DATE, String.valueOf(jsonObjectData.getString("birthDate")));
                            SharedPref.setPrefs(_act, IConstant.RELIGION, String.valueOf(jsonObjectData.getString("religion")));
                            SharedPref.setPrefs(_act, IConstant.LANGUAGE_ID, String.valueOf(jsonObjectData.getString("language")));
                            SharedPref.setPrefs(_act, IConstant.LIFE_STYLE, String.valueOf(jsonObjectData.getString("lifestyle")));
                            SharedPref.setPrefs(_act, IConstant.HEIGHT_ID, String.valueOf(jsonObjectData.getString("height")));
                            SharedPref.setPrefs(_act, IConstant.WEIGHT, String.valueOf(jsonObjectData.getString("weight")));
                            SharedPref.setPrefs(_act, IConstant.NAKSHATRA, String.valueOf(jsonObjectData.getString("nakshatra")));
                            SharedPref.setPrefs(_act, IConstant.GAN, String.valueOf(jsonObjectData.getString("gan")));
                            SharedPref.setPrefs(_act, IConstant.CHARAN, String.valueOf(jsonObjectData.getString("charan")));
                            SharedPref.setPrefs(_act, IConstant.BLOOD, String.valueOf(jsonObjectData.getString("blood")));
                            SharedPref.setPrefs(_act, IConstant.PROFILE_FOR, String.valueOf(jsonObjectData.getString("profileFor")));
                            SharedPref.setPrefs(_act, IConstant.VERIFIED, String.valueOf(jsonObjectData.getString("verified")));
                            SharedPref.setPrefs(_act, IConstant.STATUS, String.valueOf(jsonObjectData.getString("status")));
                            SharedPref.setPrefs(_act, IConstant.FEATURED, String.valueOf(jsonObjectData.getString("featured")));
                            SharedPref.setPrefs(_act, IConstant.PAYMENT, String.valueOf(jsonObjectData.getString("payment")));
                            //SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("entryDate"));
                            SharedPref.setPrefs(_act, IConstant.SUBCASTE, String.valueOf(jsonObjectData.getString("subCast")));
                            SharedPref.setPrefs(_act, IConstant.INCOME, String.valueOf(jsonObjectData.getString("income")));
                            SharedPref.setPrefs(_act, IConstant.PHYSIQUE, String.valueOf(jsonObjectData.getString("physique")));
                            SharedPref.setPrefs(_act, IConstant.OCCUPATION_ID, String.valueOf(jsonObjectData.getString("occupation")));
                            SharedPref.setPrefs(_act, IConstant.EDUCATION_ID, String.valueOf(jsonObjectData.getString("education")));
                            SharedPref.setPrefs(_act, IConstant.COMPANY, String.valueOf(jsonObjectData.getString("company")));
                            SharedPref.setPrefs(_act, IConstant.DESIGNATION, String.valueOf(jsonObjectData.getString("designation")));
                            SharedPref.setPrefs(_act, IConstant.JOB_LOCATION, String.valueOf(jsonObjectData.getString("jobLocation")));
                            SharedPref.setPrefs(_act, IConstant.OTHER_INCOME, String.valueOf(jsonObjectData.getString("otherIncome")));
                            SharedPref.setPrefs(_act, IConstant.COMPLEXION, String.valueOf(jsonObjectData.getString("complexion")));
                            SharedPref.setPrefs(_act, IConstant.MANGLIK, String.valueOf(jsonObjectData.getString("manglik")));
                            SharedPref.setPrefs(_act, IConstant.NADI, String.valueOf(jsonObjectData.getString("nadi")));
                            SharedPref.setPrefs(_act, IConstant.RASHI, String.valueOf(jsonObjectData.getString("rashi")));
                            SharedPref.setPrefs(_act, IConstant.SMOKING, String.valueOf(jsonObjectData.getString("smoking")));
                            SharedPref.setPrefs(_act, IConstant.HOTELING, String.valueOf(jsonObjectData.getString("hoteling")));
                            SharedPref.setPrefs(_act, IConstant.PRIMARY_LANGUAGE, String.valueOf(jsonObjectData.getString("primaryLanguage")));
                            SharedPref.setPrefs(_act, IConstant.DRINKING, String.valueOf(jsonObjectData.getString("drinking")));
                            SharedPref.setPrefs(_act, IConstant.FATHER_NAME, String.valueOf(jsonObjectData.getString("fatherName")));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_NAME, String.valueOf(jsonObjectData.getString("motherName")));
                            SharedPref.setPrefs(_act, IConstant.BROTHER, String.valueOf(jsonObjectData.getString("brother")));
                            SharedPref.setPrefs(_act, IConstant.SISTER, String.valueOf(jsonObjectData.getString("sister")));
                            SharedPref.setPrefs(_act, IConstant.FATHER_OCCUPATION, String.valueOf(jsonObjectData.getString("father_occupation")));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_OCCUPATION, String.valueOf(jsonObjectData.getString("mother_occupation")));
                            SharedPref.setPrefs(_act, IConstant.FATHER_CONTACT, String.valueOf(jsonObjectData.getString("father_contact")));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_CONTACT, String.valueOf(jsonObjectData.getString("mother_contact")));
                            SharedPref.setPrefs(_act, IConstant.SIBLING, String.valueOf(jsonObjectData.getString("sibling")));
                            SharedPref.setPrefs(_act, IConstant.MARRIED_SISTER, String.valueOf(jsonObjectData.getString("married_sister")));
                            SharedPref.setPrefs(_act, IConstant.MARRIED_BROTHER, String.valueOf(jsonObjectData.getString("married_brother")));
                            SharedPref.setPrefs(_act, IConstant.UNCLE_NAME, String.valueOf(jsonObjectData.getString("uncle_name")));
                            SharedPref.setPrefs(_act, IConstant.UNCLE_OCCUPATION, String.valueOf(jsonObjectData.getString("uncle_occupation")));
                            SharedPref.setPrefs(_act, IConstant.MARITAL_UNCLE_NAME, String.valueOf(jsonObjectData.getString("m_uncle_name")));
                            SharedPref.setPrefs(_act, IConstant.MARITAL_UNCLE_OCCUPATION, String.valueOf(jsonObjectData.getString("mama_occupation")));
                            SharedPref.setPrefs(_act, IConstant.HOBBY, String.valueOf(jsonObjectData.getString("hobby")));
                            SharedPref.setPrefs(_act, IConstant.USER_ADDRESS, String.valueOf(jsonObjectData.getString("address")));
                            // SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("lastSeen"));
                            SharedPref.setPrefs(_act, IConstant.TRANSACTION_ID, String.valueOf(jsonObjectData.getString("transactionId")));
                            // SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, String.valueOf(jsonObjectData.getString("email_verified")));
                            SharedPref.setPrefs(_act, IConstant.MOBILE_VERIFIED, String.valueOf(jsonObjectData.getString("mobile_verified")));
                            SharedPref.setPrefs(_act, IConstant.STEP_ONE, String.valueOf(jsonObjectData.getString("step1")));
                            SharedPref.setPrefs(_act, IConstant.STEP_TWO, String.valueOf(jsonObjectData.getString("step2")));
                            SharedPref.setPrefs(_act, IConstant.STEP_THREE, String.valueOf(jsonObjectData.getString("step3")));
                            SharedPref.setPrefs(_act, IConstant.STEP_FOUR, String.valueOf(jsonObjectData.getString("step4")));
                            SharedPref.setPrefs(_act, IConstant.STEP_FIVE, String.valueOf(jsonObjectData.getString("step5")));
                            SharedPref.setPrefs(_act, IConstant.PAYMENT_VERIFICATION, String.valueOf(jsonObjectData.getString("payment_verification")));
                            SharedPref.setPrefs(_act, IConstant.STATE_NAME, String.valueOf(jsonObjectData.getString("state_name")));
                            SharedPref.setPrefs(_act, IConstant.CITY_NAME, String.valueOf(jsonObjectData.getString("city_name")));
                            SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS, String.valueOf(jsonObjectData.getString("marital_status")));
                            SharedPref.setPrefs(_act, IConstant.LANGUAGE_NAME, String.valueOf(jsonObjectData.getString("language_name")));
                            SharedPref.setPrefs(_act, IConstant.HEIGHT_NAME, String.valueOf(jsonObjectData.getString("user_height")));
                            SharedPref.setPrefs(_act, IConstant.OCCUPATION_NAME, String.valueOf(jsonObjectData.getString("occupation_name")));
                            SharedPref.setPrefs(_act, IConstant.EDUCATION_NAME, String.valueOf(jsonObjectData.getString("education_name")));
                            SharedPref.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");

                            Helper_Method.hideSoftInput(_act);
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                           /* if (strActivityName.equalsIgnoreCase("HomeFragment")) {
                                finish();
                            } else {*/
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            finish();
                            /* }*/


                        } else {
                            if (i.has(("upgrade_flag"))) {
                                if (i.getString("upgrade_flag").equalsIgnoreCase("1")) {
                                    new AlertDialog.Builder(_act)
                                            .setTitle("Message")
                                            .setCancelable(false)
                                            .setMessage(stringMsg)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                public void onClick(DialogInterface arg0, int arg1) {
                                                   /* Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra("EXIT", true);
                                                    startActivity(intent);
                                                    finish();*/
                                                    arg0.dismiss();
                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.putExtra("EXIT", true);
                                                    startActivity(intent);
                                                    finish();

                                                }
                                            }).create().show();
                                } else {
                                    Helper_Method.toaster(_act, stringMsg);
                                    Helper_Method.dismissProgessBar();
                                }
                            } else {
                                Helper_Method.toaster(_act, stringMsg);
                                Helper_Method.dismissProgessBar();
                            }


                        }


                    } catch (JSONException e) {

                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(_act, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private boolean isValid() {

        if (validations.isBlank(etTransactionId)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etTransactionId.startAnimation(shake);
            etTransactionId.setError(IErrors.EMPTY);
            return false;
        }
        return true;
    }

    public void webServiceCallMyOrder() {

        progress.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.GONE);
        rvPackageList.setVisibility(View.GONE);
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETPackages();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    packageObjectArrayList.clear();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        // String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            qr_code_path = jsonObject.getString("qr_code_path");


                            JSONArray jsonArray = jsonObject.getJSONArray("package_list");
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    packageObjectArrayList.add(new PackageObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    progress.setVisibility(View.GONE);
                                    noInternet.setVisibility(View.VISIBLE);
                                    rvPackageList.setVisibility(View.GONE);
                                }
                            }

                            Glide.with(_act)
                                    .load(qr_code_path)
                                    .apply(new RequestOptions().placeholder(R.drawable.default_128).error(R.drawable.default_128).diskCacheStrategy(DiskCacheStrategy.ALL))
                                    .into(ivScanImage);

                            if (packageObjectArrayList.size() == 0) {

                                progress.setVisibility(View.GONE);
                                noInternet.setVisibility(View.VISIBLE);
                                rvPackageList.setVisibility(View.GONE);

                            } else {
                                packageObjectArrayList.get(0).setSelected(true);
                                packageAdapter = new PackageAdapter(_act, packageObjectArrayList);
                                linearLayoutManager = new LinearLayoutManager(_act);
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                rvPackageList.setLayoutManager(linearLayoutManager);
                                rvPackageList.setItemAnimator(new DefaultItemAnimator());
                                rvPackageList.setItemViewCacheSize(packageObjectArrayList.size());
                                rvPackageList.setAdapter(packageAdapter);
                                packageAdapter.notifyDataSetChanged();

                                progress.setVisibility(View.GONE);
                                noInternet.setVisibility(View.GONE);
                                rvPackageList.setVisibility(View.VISIBLE);

                            }

                        } else {

                            // Helper_Method.toaster(_act, stringMsg);
                            progress.setVisibility(View.GONE);
                            noInternet.setVisibility(View.VISIBLE);
                            rvPackageList.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.GONE);
                        noInternet.setVisibility(View.VISIBLE);
                        rvPackageList.setVisibility(View.GONE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    progress.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                    rvPackageList.setVisibility(View.GONE);

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
}