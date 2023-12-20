package com.whtech.anuroopjodi.InitialActivities.RegistrationSteps;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.InitialActivities.PrefredPartner.PreferredPatnerActivity;
import com.whtech.anuroopjodi.InsideActivities.DashboardActivity;
import com.whtech.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BothOtpActivity extends BaseActivity {

    private static final String TAG = "BothOtpActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private TextView tvMailOtpTitle, tvMessageOtpTitle;
    private Dialog dialog;
    private TextView tvDailogDesc, tvDailogSuccessfulMsg;
    private ImageView ivDailogClose;
    private String strRegMobile, strRegEmail, maskRegMobile;
    private String user_profile_path, maskRegEmail, strProfileId, strActvity;
    /*private OtpView otp_mail_otp, otp_mobile_otp;*/
    private EditText otp_mail_otp, otp_mobile_otp;
    private TextView tvResendOtp;
    private String otpMobile, otpEmail;
    private boolean flagOtpMobileComplete = false, flagOtpEmailComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_both_otp);

        _act = BothOtpActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        strActvity = getIntent().getStringExtra("Activity");
        if (strActvity.equalsIgnoreCase("Login")) {
            strRegMobile = getIntent().getStringExtra("mobile");
            strRegEmail = getIntent().getStringExtra("email");
        } else {
            strRegMobile = getIntent().getStringExtra("mobile");
            strRegEmail = getIntent().getStringExtra("email");
            strProfileId = getIntent().getStringExtra("profile_id");
            SuccessfulDailog();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_verfy_email_and_mobile));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        //  toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        otp_mail_otp = findViewById(R.id.otp_mail_otp);
        otp_mobile_otp = findViewById(R.id.otp_mobile_otp);
        tvResendOtp = findViewById(R.id.tvResendOtp);

        tvMailOtpTitle = findViewById(R.id.tvMailOtpTitle);
        tvMessageOtpTitle = findViewById(R.id.tvMessageOtpTitle);
        otp_mail_otp.requestFocus();
        //otp_mobile_otp.requestFocus();

        //Mask Mobile No
        maskRegMobile = null;
        maskRegMobile = markMobile(strRegMobile);

        //MaskEmail
        maskRegEmail = strRegEmail.replaceAll("(?<=.{3}).(?=[^@]*?@)", "*");
        Log.i("mail", maskRegEmail);


        String email = "<font color='#FA7A76'>" + maskRegEmail + "</font>";
        String mobile = "<font color='#FA7A76'>" + maskRegMobile + "</font>";

        tvMailOtpTitle.setText(Html.fromHtml("An MAIL with verification CODE has been send to your registered EMAIL " + email));
        tvMessageOtpTitle.setText(Html.fromHtml("An SMS with verification CODE has been send to your registered MOBILE NO " + mobile));


        /*otp_mail_otp.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted", otp);
                otpEmail = otp;
                //flagOtpEmailComplete = true;
            }
        });*/


        otp_mail_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    String otp = s.toString();
                    Log.d("otpChanged", "afterTextChanged: "+otp);
                    otpEmail = otp;
                    flagOtpMobileComplete = true;
                }
            }
        });

        /*otp_mobile_otp.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted", otp);
                otpMobile = otp;
                flagOtpMobileComplete = true;
            }
        });*/


        otp_mobile_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    String otp = s.toString();
                    Log.d("onOtpCompleted", otp);
                    otpMobile = otp;
                    flagOtpMobileComplete = true;
                }
            }
        });


        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (connectionDetector.checkConnection(_act)) {

                    webcallResendOtp();

                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }


            }
        });
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.checkConnection(_act)) {

                            otpMobile = otp_mobile_otp.getText().toString();

                            if (otpMobile != null && !otpMobile.isEmpty() && !otpMobile.equals("null")) {
                                if (flagOtpMobileComplete) {
                                    webcallOtp();
                                } else {
                                    Helper_Method.toaster(_act, "Enter OTP Received in your registered Mobile Number");
                                }
                            } else {
                                Helper_Method.toaster(_act, "Enter OTP Received in your registered Mobile Number");
                            }


                } else {
                    Helper_Method.toaster_long(_act, getResources().getString(R.string.string_internet_connection_warning));

                }
            }
        });
    }

    private void SuccessfulDailog() {

        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        dialog = new Dialog(_act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dailog_registration_congratulation);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();

        tvDailogSuccessfulMsg = dialog.findViewById(R.id.tvDailogSuccessfulMsg);
        tvDailogDesc = dialog.findViewById(R.id.tvDailogDesc);
        ivDailogClose = dialog.findViewById(R.id.ivDailogClose);

        tvDailogSuccessfulMsg.setText("You are successfully registered with AnuroopJodi Matrimony Id : " + strProfileId);

        tvDailogDesc.setText("You can login using registered mobile number and email. A confirmation email is send to your registered email id.");
        ivDailogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //   ActivityCompat.finishAffinity(_act);

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void webcallResendOtp() {

        Helper_Method.showProgressBar(_act, "Resending...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTResendOtp(strRegMobile);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);
                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallOtp() {

        Helper_Method.showProgressBar(_act, "Verifying...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.getCheckOtp(strRegMobile, strRegEmail, otpMobile, otpEmail);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                //EraseLocalData();
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            user_profile_path = i.getString("user_profile_path");
                            Helper_Method.hideSoftInput(_act);
                            Helper_Method.dismissProgessBar();
                            Helper_Method.toaster(_act, stringMsg);

                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                            SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("id"));
                            SharedPref.setPrefs(_act, IConstant.PACKAGE, jsonObjectData.getString("package"));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_TIME, jsonObjectData.getString("birthTime"));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_PLACE, jsonObjectData.getString("birthPlace"));
                            SharedPref.setPrefs(_act, IConstant.ABOUT_ME, jsonObjectData.getString("profileDescription"));
                            //SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("loginId"));
                            SharedPref.setPrefs(_act, IConstant.USER_PHOTO, user_profile_path + jsonObjectData.getString("ProfilePhoto"));
                            SharedPref.setPrefs(_act, IConstant.PHOTO_APPROVED, jsonObjectData.getString("photoApproved"));
                            SharedPref.setPrefs(_act, IConstant.PROFILE_ID, jsonObjectData.getString("registrationNumber"));
                            SharedPref.setPrefs(_act, IConstant.USER_FIRST_NAME, jsonObjectData.getString("firstName"));
                            SharedPref.setPrefs(_act, IConstant.USER_EMAIL, jsonObjectData.getString("emailId"));
                            SharedPref.setPrefs(_act, IConstant.DISABILITY, jsonObjectData.getString("disability"));
                            SharedPref.setPrefs(_act, IConstant.STATE_ID, jsonObjectData.getString("state"));
                            SharedPref.setPrefs(_act, IConstant.CITY_ID, jsonObjectData.getString("city"));
                            SharedPref.setPrefs(_act, IConstant.USER_MOBILE, jsonObjectData.getString("mobileNumber"));
                            SharedPref.setPrefs(_act, IConstant.USER_LAST_NAME, jsonObjectData.getString("lastName"));
                            SharedPref.setPrefs(_act, IConstant.USER_GENDER, jsonObjectData.getString("gender"));
                            SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS_ID, jsonObjectData.getString("maritalStatus"));
                            SharedPref.setPrefs(_act, IConstant.GOTRA, jsonObjectData.getString("gotra"));
                            SharedPref.setPrefs(_act, IConstant.BIRTH_DATE, jsonObjectData.getString("birthDate"));
                            SharedPref.setPrefs(_act, IConstant.RELIGION, jsonObjectData.getString("religion"));
                            SharedPref.setPrefs(_act, IConstant.LANGUAGE_ID, jsonObjectData.getString("language"));
                            SharedPref.setPrefs(_act, IConstant.LIFE_STYLE, jsonObjectData.getString("lifestyle"));
                            SharedPref.setPrefs(_act, IConstant.HEIGHT_ID, jsonObjectData.getString("height"));
                            SharedPref.setPrefs(_act, IConstant.WEIGHT, jsonObjectData.getString("weight"));
                            SharedPref.setPrefs(_act, IConstant.NAKSHATRA, jsonObjectData.getString("nakshatra"));
                            SharedPref.setPrefs(_act, IConstant.GAN, jsonObjectData.getString("gan"));
                            SharedPref.setPrefs(_act, IConstant.CHARAN, jsonObjectData.getString("charan"));
                            SharedPref.setPrefs(_act, IConstant.BLOOD, jsonObjectData.getString("blood"));
                            SharedPref.setPrefs(_act, IConstant.PROFILE_FOR, jsonObjectData.getString("profileFor"));
                            SharedPref.setPrefs(_act, IConstant.VERIFIED, jsonObjectData.getString("verified"));
                            SharedPref.setPrefs(_act, IConstant.STATUS, jsonObjectData.getString("status"));
                            SharedPref.setPrefs(_act, IConstant.FEATURED, jsonObjectData.getString("featured"));
                            SharedPref.setPrefs(_act, IConstant.PAYMENT, jsonObjectData.getString("payment"));
                            SharedPref.setPrefs(_act, IConstant.CASTE, jsonObjectData.getString("cast"));
                            SharedPref.setPrefs(_act, IConstant.SUBCASTE, jsonObjectData.getString("subCast"));
                            SharedPref.setPrefs(_act, IConstant.INCOME, jsonObjectData.getString("income"));
                            SharedPref.setPrefs(_act, IConstant.PHYSIQUE, jsonObjectData.getString("physique"));
                            SharedPref.setPrefs(_act, IConstant.OCCUPATION_ID, jsonObjectData.getString("occupation"));
                            SharedPref.setPrefs(_act, IConstant.EDUCATION_ID, jsonObjectData.getString("education"));
                            SharedPref.setPrefs(_act, IConstant.COMPANY, jsonObjectData.getString("company"));
                            SharedPref.setPrefs(_act, IConstant.DESIGNATION, jsonObjectData.getString("designation"));
                            SharedPref.setPrefs(_act, IConstant.JOB_LOCATION, jsonObjectData.getString("jobLocation"));
                            SharedPref.setPrefs(_act, IConstant.OTHER_INCOME, jsonObjectData.getString("otherIncome"));
                            SharedPref.setPrefs(_act, IConstant.COMPLEXION, jsonObjectData.getString("complexion"));
                            SharedPref.setPrefs(_act, IConstant.MANGLIK, jsonObjectData.getString("manglik"));
                            SharedPref.setPrefs(_act, IConstant.NADI, jsonObjectData.getString("nadi"));
                            SharedPref.setPrefs(_act, IConstant.RASHI, jsonObjectData.getString("rashi"));
                            SharedPref.setPrefs(_act, IConstant.SMOKING, jsonObjectData.getString("smoking"));
                            SharedPref.setPrefs(_act, IConstant.HOTELING, jsonObjectData.getString("hoteling"));
                            SharedPref.setPrefs(_act, IConstant.PRIMARY_LANGUAGE, jsonObjectData.getString("primaryLanguage"));
                            SharedPref.setPrefs(_act, IConstant.DRINKING, jsonObjectData.getString("drinking"));
                            SharedPref.setPrefs(_act, IConstant.FATHER_NAME, jsonObjectData.getString("fatherName"));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_NAME, jsonObjectData.getString("motherName"));
                            SharedPref.setPrefs(_act, IConstant.BROTHER, jsonObjectData.getString("brother"));
                            SharedPref.setPrefs(_act, IConstant.SISTER, jsonObjectData.getString("sister"));
                            SharedPref.setPrefs(_act, IConstant.FATHER_OCCUPATION, jsonObjectData.getString("father_occupation"));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_OCCUPATION, jsonObjectData.getString("mother_occupation"));
                            SharedPref.setPrefs(_act, IConstant.FATHER_CONTACT, jsonObjectData.getString("father_contact"));
                            SharedPref.setPrefs(_act, IConstant.MOTHER_CONTACT, jsonObjectData.getString("mother_contact"));
                            SharedPref.setPrefs(_act, IConstant.SIBLING, jsonObjectData.getString("sibling"));
                            SharedPref.setPrefs(_act, IConstant.MARRIED_SISTER, jsonObjectData.getString("married_sister"));
                            SharedPref.setPrefs(_act, IConstant.MARRIED_BROTHER, jsonObjectData.getString("married_brother"));
                            SharedPref.setPrefs(_act, IConstant.UNCLE_NAME, jsonObjectData.getString("uncle_name"));
                            SharedPref.setPrefs(_act, IConstant.UNCLE_OCCUPATION, jsonObjectData.getString("uncle_occupation"));
                            SharedPref.setPrefs(_act, IConstant.MARITAL_UNCLE_NAME, jsonObjectData.getString("m_uncle_name"));
                            SharedPref.setPrefs(_act, IConstant.MARITAL_UNCLE_OCCUPATION, jsonObjectData.getString("mama_occupation"));
                            SharedPref.setPrefs(_act, IConstant.HOBBY, jsonObjectData.getString("hobby"));
                            SharedPref.setPrefs(_act, IConstant.USER_ADDRESS, jsonObjectData.getString("address"));
                            // SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("lastSeen"));
                            SharedPref.setPrefs(_act, IConstant.TRANSACTION_ID, jsonObjectData.getString("transactionId"));
                           // SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, jsonObjectData.getString("email_verified"));
                            SharedPref.setPrefs(_act, IConstant.MOBILE_VERIFIED, jsonObjectData.getString("mobile_verified"));
                            SharedPref.setPrefs(_act, IConstant.STEP_ONE, jsonObjectData.getString("step1"));
                            SharedPref.setPrefs(_act, IConstant.STEP_TWO, jsonObjectData.getString("step2"));
                            SharedPref.setPrefs(_act, IConstant.STEP_THREE, jsonObjectData.getString("step3"));
                            SharedPref.setPrefs(_act, IConstant.STEP_FOUR, jsonObjectData.getString("step4"));
                            SharedPref.setPrefs(_act, IConstant.STEP_FIVE, jsonObjectData.getString("step5"));
                            SharedPref.setPrefs(_act, IConstant.PAYMENT_VERIFICATION, jsonObjectData.getString("payment_verification"));
                            SharedPref.setPrefs(_act, IConstant.STATE_NAME, jsonObjectData.getString("state_name"));
                            SharedPref.setPrefs(_act, IConstant.CITY_NAME, jsonObjectData.getString("city_name"));
                            SharedPref.setPrefs(_act, IConstant.USER_MARITAL_STATUS, jsonObjectData.getString("marital_status"));
                            SharedPref.setPrefs(_act, IConstant.LANGUAGE_NAME, jsonObjectData.getString("language_name"));
                            SharedPref.setPrefs(_act, IConstant.HEIGHT_NAME, jsonObjectData.getString("user_height"));
                            SharedPref.setPrefs(_act, IConstant.OCCUPATION_NAME, jsonObjectData.getString("occupation_name"));
                            SharedPref.setPrefs(_act, IConstant.EDUCATION_NAME, jsonObjectData.getString("education_name"));
                            SharedPref.setPrefs(_act, IConstant.USER_IS_LOGIN, "true");


                            if (!jsonObjectData.getString("step1").equalsIgnoreCase("1")) {
                                Helper_Method.intentActivity(_act, BasicDetailsAddAndEditActivity.class, true);
                            } else if (!jsonObjectData.getString("step2").equalsIgnoreCase("1")) {
                                Helper_Method.intentActivity(_act, PersonalDetailsActivity.class, true);
                            } else if (!jsonObjectData.getString("step3").equalsIgnoreCase("1")) {
                                Helper_Method.intentActivity(_act, ProfessionalDetailsActivity.class, true);
                            } else if (!jsonObjectData.getString("step4").equalsIgnoreCase("1")) {
                                Helper_Method.intentActivity(_act, FamilyDetailsActivity.class, true);
                            } else if (!jsonObjectData.getString("step5").equalsIgnoreCase("1")) {
                                Helper_Method.intentActivity(_act, PreferredPatnerActivity.class, true);
                            } else if (!jsonObjectData.getString("payment_verification").equalsIgnoreCase("1")) {
                                Intent intent = new Intent(_act,PackageActivity.class);
                                intent.putExtra("ActivityName","Otp");
                                startActivity(intent);
                            }else {
                                Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                            }
                            //ActivityCompat.finishAffinity(_act);


                        } else {
                            Helper_Method.logD("OTP", stringMsg);
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                        Helper_Method.logD("JSONException", "onResponse: " + e.getMessage());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.logD("IOException", "onResponse: " + e.getMessage());
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }


    public String markMobile(String regMobile) {
        StringBuffer buf = new StringBuffer(regMobile);
        int start = 3;
        int end = 8;
        buf.replace(start, end, "xxxxx");
        regMobile = buf.toString();
        Log.i("Number", regMobile);
        return regMobile;
    }

}