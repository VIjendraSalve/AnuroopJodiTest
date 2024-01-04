package com.wind.anuroopjodi.InitialActivities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IErrors;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.PrefredPartner.PreferredPatnerActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.BasicDetailsAddAndEditActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.BothOtpActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.FamilyDetailsActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.PersonalDetailsActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.ProfessionalDetailsActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.SignUpActivity;
import com.wind.anuroopjodi.InsideActivities.DashboardActivity;
import com.wind.anuroopjodi.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_CODE = 1;
    private TextView tvTermsAndConditions, tvSignUp;
    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView tvShowPassword, tvHidePassword, tvForgotPassword,tv_login_title;
    private EditText etPassword, etMobileAndEmail;
    private String stringUserToken = "None", user_profile_path;
    private String is_email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _act = LoginActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.lbl_sign_in));
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

/*        String first = "<font color='#000000'>By Signing in you agree to our </font>";
        String second = "<font color='#D13816'>Terms And Conditions</font>";

        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        tvTermsAndConditions.setText(Html.fromHtml(first + second));*/


        tvSignUp = findViewById(R.id.tvSignUp);
        tv_login_title = findViewById(R.id.tv_login_title);
        etPassword = findViewById(R.id.etPassword);
        etMobileAndEmail = findViewById(R.id.etMobileAndEmail);
        tvShowPassword = findViewById(R.id.tvShowPassword);
        tvHidePassword = findViewById(R.id.tvHidePassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d("Notification_Token1:", "onComplete: " + token);
                        stringUserToken = token;

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });



        //String signUpfirst = "<font color='#BDBDBD'>" + getResources().getString(R.string.reg_title1) + "</font>";
        String signUpfirst = "<font color='#000000'>" + getResources().getString(R.string.reg_title1) + "</font>";
        String signUpsecond = "<font color='#B10B05'> " + getResources().getString(R.string.reg_title2) + "</font>";
        tvSignUp.setText(Html.fromHtml(signUpfirst + signUpsecond));


        String signUpfirsttitle = "<font color='#000000'>" + getResources().getString(R.string.login_title1) + "</font>";
        String signUpsecondtitle = "<font color='#B10B05'> " + getResources().getString(R.string.login_title2) + "</font>";
        tv_login_title.setText(Html.fromHtml(signUpfirsttitle + signUpsecondtitle));
        tvShowPassword.setVisibility(View.VISIBLE);
        etPassword.setTransformationMethod(PasswordTransformationMethod
                .getInstance());


        tvShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvShowPassword.setVisibility(View.INVISIBLE);
                tvHidePassword.setVisibility(View.VISIBLE);
                etPassword.setTransformationMethod(HideReturnsTransformationMethod
                        .getInstance());
                etPassword.setSelection(etPassword.getText().length());
            }
        });

        tvHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHidePassword.setVisibility(View.INVISIBLE);
                tvShowPassword.setVisibility(View.VISIBLE);
                etPassword.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                etPassword.setSelection(etPassword.getText().length());

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity_animate_fade(_act, SignUpActivity.class, false);
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    if (isValid()) {
                        String data = etMobileAndEmail.getText().toString().trim();
                        boolean flag = false;
                        if (data.matches("[0-9]+")) {
                            if (isValidMobile()) {
                                flag = true;
                                is_email = "0";
                            }
                        } else {
                            if (isValidEmail()) {
                                flag = true;
                                is_email = "1";
                            } else {

                            }
                        }

                        if (flag) {
                            Helper_Method.hideSoftInput(_act);
                            webcallLogin();
                        }


                    }
                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }
            }
        });

    }

    private void webcallLogin() {

        Log.d("ISEMAIL", "webcallLogin: " + is_email);
        Log.i("ISEMAIL", "webcallLogin: " + is_email);
        //Log.i("ISEMAIL", "webcallLogin: "+is_email);
        Helper_Method.showProgressBar(_act, "Sign In...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTLogin(
                etMobileAndEmail.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                stringUserToken,
                IConstant.ANDROID_ID,
                is_email
        );

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);

                        if (i.getBoolean("result")) {

                            if (i.has("mobile_verified")) {
                                String is_verified = i.getString("mobile_verified");
                                String isRegMobile = i.getString("mobile");
                                String isRegEmail = i.getString("email");

                                if (is_verified.equalsIgnoreCase("true")) {
                                    String stringCode = i.getString("result");
                                    String stringMsg = i.getString("reason");

                                    SharedPref.setPrefs(_act, IConstant.PROFILE_PHOTO_STATUS, String.valueOf(i.getString("profile_photo_status")));
                                    SharedPref.setPrefs(_act, IConstant.AADHAR_PHOTO_STATUS, String.valueOf(i.getString("adhar_photo_status")));

                                    if (stringCode.equalsIgnoreCase("true")) {
                                        user_profile_path = i.getString("user_profile_path");

                                        SharedPref.setPrefs(_act, IConstant.HoroScopeImage, String.valueOf(i.getString("horoscope_image")));
                                        SharedPref.setPrefs(_act, IConstant.AdharBackImage, String.valueOf(i.getString("adhar_back_image")));
                                        SharedPref.setPrefs(_act, IConstant.AdharFrontImage, String.valueOf(i.getString("adhar_front_image")));
                                        SharedPref.setPrefs(_act, IConstant.is_skip_payement_request, String.valueOf(i.getString("is_skip_payement_request")));

                                        SharedPref.setPrefs(_act, IConstant.AdharImagePath, String.valueOf(i.getString("adhar_path")));
                                        SharedPref.setPrefs(_act, IConstant.HoroScopeImagePath, String.valueOf(i.getString("horoscope_path")));
                                        SharedPref.setPrefs(_act, IConstant.UserProfileImagePath, String.valueOf(i.getString("user_profile_path")));


                                        JSONArray jsonArray = i.getJSONArray("user_data");
                                        JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                                        SharedPref.setPrefs(_act, IConstant.USER_ID, String.valueOf(jsonObjectData.getString("loginId")));
                                        SharedPref.setPrefs(_act, IConstant.PACKAGE, String.valueOf(jsonObjectData.getString("package")));
                                        SharedPref.setPrefs(_act, IConstant.BIRTH_TIME, String.valueOf(jsonObjectData.getString("birthTime")));
                                        SharedPref.setPrefs(_act, IConstant.BIRTH_PLACE, String.valueOf(jsonObjectData.getString("birthPlace")));
                                        SharedPref.setPrefs(_act, IConstant.ABOUT_ME, String.valueOf(jsonObjectData.getString("profileDescription")));
                                        SharedPref.setPrefs(_act, IConstant.PROFILE_PHOTO, String.valueOf(jsonObjectData.getString("ProfilePhoto")));

                                        //SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("loginId"));
                                        SharedPref.setPrefs(_act, IConstant.USER_PHOTO, String.valueOf(user_profile_path + jsonObjectData.getString("ProfilePhoto")));
                                        SharedPref.setPrefs(_act, IConstant.PHOTO_APPROVED, String.valueOf(jsonObjectData.getString("photoApproved")));
                                        SharedPref.setPrefs(_act, IConstant.PROFILE_ID, String.valueOf(jsonObjectData.getString("registrationNumber")));
                                        SharedPref.setPrefs(_act, IConstant.USER_FIRST_NAME, String.valueOf(jsonObjectData.getString("firstName")));
                                        SharedPref.setPrefs(_act, IConstant.USER_MIDDLE_NAME, String.valueOf(jsonObjectData.getString("middle_name")));
                                        SharedPref.setPrefs(_act, IConstant.USER_EMAIL, String.valueOf(jsonObjectData.getString("emailId")));
                                        SharedPref.setPrefs(_act, IConstant.DISABILITY, String.valueOf(jsonObjectData.getString("disability")));
                                        SharedPref.setPrefs(_act, IConstant.STATE_ID, String.valueOf(jsonObjectData.getString("state")));
                                        SharedPref.setPrefs(_act, IConstant.CITY_ID, String.valueOf(jsonObjectData.getString("city")));
                                        SharedPref.setPrefs(_act, IConstant.USER_MOBILE, String.valueOf(jsonObjectData.getString("mobileNumber")));
                                        SharedPref.setPrefs(_act, IConstant.USER_LAST_NAME, String.valueOf(jsonObjectData.getString("lastName")));
                                        SharedPref.setPrefs(_act, IConstant.USER_GENDER, String.valueOf(jsonObjectData.getString("gender")));
                                        SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS_ID, String.valueOf(jsonObjectData.getString("maritalStatus")));
                                        SharedPref.setPrefs(_act, IConstant.GOTRA, String.valueOf(jsonObjectData.getString("gotra")));
                                        SharedPref.setPrefs(_act, IConstant.NATIVE_PLACE, String.valueOf(jsonObjectData.getString("native_place")));
                                        SharedPref.setPrefs(_act, IConstant.LIVING_STYLE, String.valueOf(jsonObjectData.getString("living_style")));
                                        SharedPref.setPrefs(_act, IConstant.AGRI_LAND, String.valueOf(jsonObjectData.getString("agriculture_land")));
                                        SharedPref.setPrefs(_act, IConstant.DEVAK, String.valueOf(jsonObjectData.getString("devak")));
                                        SharedPref.setPrefs(_act, IConstant.MAMAKUL, String.valueOf(jsonObjectData.getString("mama_kul")));
                                        SharedPref.setPrefs(_act, IConstant.CASTE_NAME, String.valueOf(jsonObjectData.getString("caste_name")));
                                        SharedPref.setPrefs(_act, IConstant.WORKING_CITY, String.valueOf(jsonObjectData.getString("working_city_present_name")));
                                        Log.d("Hche", "onResponse: "+String.valueOf(jsonObjectData.getString("working_city_present_name")));
                                        SharedPref.setPrefs(_act, IConstant.WORKING_CITY_ID, String.valueOf(jsonObjectData.getString("working_city_present")));
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
                                        SharedPref.setPrefs(_act, IConstant.CASTE, String.valueOf(jsonObjectData.getString("cast")));
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
                                        SharedPref.setPrefs(_act, IConstant.INTERCASTE, String.valueOf(jsonObjectData.getString("intercaste_marriage")));
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
                                        SharedPref.setPrefs(_act, IConstant.USER_EXPECTATION, String.valueOf(jsonObjectData.getString("expectaion")));
                                        SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS, String.valueOf(jsonObjectData.getString("marital_status")));
                                        SharedPref.setPrefs(_act, IConstant.LANGUAGE_NAME, String.valueOf(jsonObjectData.getString("language_name")));
                                        SharedPref.setPrefs(_act, IConstant.HEIGHT_NAME, String.valueOf(jsonObjectData.getString("user_height")));
                                        SharedPref.setPrefs(_act, IConstant.OCCUPATION_NAME, String.valueOf(jsonObjectData.getString("occupation_name")));
                                        SharedPref.setPrefs(_act, IConstant.EDUCATION_NAME, String.valueOf(jsonObjectData.getString("education_name")));
                                        SharedPref.setPrefs(_act, IConstant.APP_ID, String.valueOf(jsonObjectData.getString("profile_id")));

                                        SharedPref.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");

                                        if (SharedPref.getPrefs(_act, IConstant.STEP_ONE) != null && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equalsIgnoreCase("1")) {
                                            Helper_Method.intentActivity(_act, BasicDetailsAddAndEditActivity.class, true);
                                        } else if (SharedPref.getPrefs(_act, IConstant.STEP_TWO) != null && !SharedPref.getPrefs(_act, IConstant.STEP_TWO).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_TWO).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_TWO).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_TWO).equalsIgnoreCase("1")) {
                                            Helper_Method.intentActivity(_act, PersonalDetailsActivity.class, true);
                                        } else if (SharedPref.getPrefs(_act, IConstant.STEP_THREE) != null && !SharedPref.getPrefs(_act, IConstant.STEP_THREE).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_THREE).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_THREE).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_THREE).equalsIgnoreCase("1")) {
                                            Helper_Method.intentActivity(_act, ProfessionalDetailsActivity.class, true);
                                        } else if (SharedPref.getPrefs(_act, IConstant.STEP_FOUR) != null && !SharedPref.getPrefs(_act, IConstant.STEP_FOUR).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_FOUR).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_FOUR).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_FOUR).equalsIgnoreCase("1")) {
                                            Helper_Method.intentActivity(_act, FamilyDetailsActivity.class, true);
                                        } else if (SharedPref.getPrefs(_act, IConstant.STEP_FIVE) != null && !SharedPref.getPrefs(_act, IConstant.STEP_FIVE).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_FIVE).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_FIVE).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_FIVE).equalsIgnoreCase("1")) {
                                            Helper_Method.intentActivity(_act, PreferredPatnerActivity.class, true);
                                        } else if (SharedPref.getPrefs(_act, IConstant.PAYMENT_VERIFICATION) != null && !SharedPref.getPrefs(_act, IConstant.PAYMENT_VERIFICATION).isEmpty() && !SharedPref.getPrefs(_act, IConstant.PAYMENT_VERIFICATION).equals("null") && !SharedPref.getPrefs(_act, IConstant.PAYMENT_VERIFICATION).equals("") && !SharedPref.getPrefs(_act, IConstant.PAYMENT_VERIFICATION).equalsIgnoreCase("1")) {
                                            if(i.getString("is_skip_payement_request").equals("1")){
                                                Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                                            }else {
                                                Helper_Method.intentActivity(_act, PackageActivity.class, true);
                                            }
                                            //Intent intent = new Intent(_act, DashboardActivity.class);
                                            //intent.putExtra("ActivityName", "Login");
                                            //startActivity(intent);

                                        } else {
                                            Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                                        }
                                        Helper_Method.hideSoftInput(_act);
                                        Helper_Method.dismissProgessBar();
                                        Helper_Method.toaster(_act, stringMsg);
                                        /* ActivityCompat.finishAffinity(_act);*/

                                    } else {
                                        Helper_Method.toaster(_act, stringMsg);
                                        Helper_Method.dismissProgessBar();

                                    }
                                } else {
                                    Helper_Method.dismissProgessBar();
                                    Intent intent = new Intent(_act, BothOtpActivity.class);
                                    intent.putExtra("Activity", "Login");
                                    intent.putExtra("mobile", isRegMobile);
                                    intent.putExtra("email", isRegEmail);
                                   // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                                    startActivityForResult(intent, REQUEST_CODE);
                                }
                            } else {
                                Helper_Method.dismissProgessBar();
                            }
                        } else {
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(_act, "" + i.getString("reason"), Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Message");
                            builder.setMessage(i.getString("reason"));
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
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

    private boolean isValidEmail() {
        if (!validations.isValidEmail(etMobileAndEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobileAndEmail.startAnimation(shake);
            etMobileAndEmail.setError(IErrors.INVALID_EMAIL);
            return false;
        }

        return true;
    }

    private boolean isValidMobile() {
        if (!validations.isValidPhone(etMobileAndEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobileAndEmail.startAnimation(shake);
            etMobileAndEmail.setError(IErrors.INVALID_PHONE);
            return false;
        }
        return true;
    }

    private boolean isValid() {
        if (validations.isBlank(etMobileAndEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobileAndEmail.startAnimation(shake);
            etMobileAndEmail.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etPassword)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etPassword.startAnimation(shake);
            etPassword.setError(IErrors.EMPTY);
            return false;
        }


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Close")
                .setMessage("Are you sure you want to close application?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        /*Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);*/
                        ActivityCompat.finishAffinity(_act);
                        //  DashboardAcivity.super.onBackPressed();

                    }
                }).create().show();
    }
}