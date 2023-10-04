package com.whtech.anuroopjodi.InitialActivities.RegistrationSteps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.Object.CityObject;
import com.whtech.anuroopjodi.Object.ProfessionObject;
import com.whtech.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessionalDetailsActivity extends BaseActivity {

    private static final String TAG = "ProfessionalDetailsActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private String user_profile_path = null;


    //Profession
    private ArrayList<ProfessionObject> professionObjectArrayList;
    private SearchableSpinner spinnerProfession;
    private ArrayAdapter<ProfessionObject> spinnerProfession_Adapter;
    private String strProfessionId = "0", strProfession;

    //City Spinner Zone
    private ArrayList<CityObject> cityObjectArrayList;
    private SearchableSpinner spinnerCitylist;
    private ArrayAdapter<CityObject> spinnerCitylist_Adapter;
    private String strCityId = "0", strCity;


    private TextView tvEducation;
    private EditText etCompanyName, etDesignation, etJobLocation,etAgriLand, etAnnualIncome, etOtherIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_details);

        _act = ProfessionalDetailsActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_professional_details));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        spinnerCitylist = findViewById(R.id.spinnerCitylist);
        spinnerProfession = findViewById(R.id.spinnerProfession);
        tvEducation = findViewById(R.id.tvEducation);
        etCompanyName = findViewById(R.id.etCompanyName);
        etDesignation = findViewById(R.id.etDesignation);
        etJobLocation = findViewById(R.id.etJobLocation);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        etOtherIncome = findViewById(R.id.etOtherIncome);
        etAgriLand = findViewById(R.id.etAgriLand);
        if (SharedPref.getPrefs(_act, IConstant.EDUCATION_ID) != null
                && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).isEmpty()
                && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("null")
                && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("")) {

            //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
            //selectedBrandsIds = Arrays.asList(myArray);
            tvEducation.setText(SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME));
        } else {
        }

        tvEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_act, EducationMultiSelectionActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        //Default Webcall
        if (connectionDetector.isConnectionAvailable()) {
            webcallprofession();
        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));

        }

       /* findViewById(R.id.btnStepthree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity(_act, FamilyDetailsActivity.class, false);

            }
        });*/

        findViewById(R.id.btnStepthree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPref.getPrefs(_act, IConstant.EDUCATION_ID) != null && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).isEmpty() && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("null") && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("")) {
                    if (strProfessionId != null && !strProfessionId.isEmpty() && !strProfessionId.equals("null") && !strProfessionId.equals("0")) {
                        if (strCityId != null && !strCityId.isEmpty() && !strCityId.equals("null") && !strCityId.equals("0")) {
                            if (isValid()) {
                                if (connectionDetector.isConnectionAvailable()) {
                                    webcallProfessionDetails();
                                } else {
                                    Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                }
                            } else {
                                Helper_Method.toaster(_act, "Check and fill all the fields");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Job location");
                        }

                    } else {
                        Helper_Method.toaster(_act, "Select Profession");
                    }
                } else {
                    Helper_Method.toaster(_act, "Select Education");
                }


            }
        });

        webcallCityList("");

        if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
            if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                setValuesToUpdate();
            }
        }
    }

    private void setValuesToUpdate() {

        tvEducation.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.EDUCATION_NAME));
        etCompanyName.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.COMPANY));
        etDesignation.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.DESIGNATION));
        etJobLocation.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.JOB_LOCATION));
        etAgriLand.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.AGRI_LAND));
        etAnnualIncome.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.INCOME));
        etOtherIncome.setText(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OTHER_INCOME));

        strProfessionId = SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID);
        strCity = SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_NAME);

    }

    private void webcallProfessionDetails() {

        Helper_Method.showProgressBar(_act, "Updating Profession Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);


        Call<ResponseBody> result = api.POSTProfessionDetails(
                strProfessionId,
                SharedPref.getPrefs(_act, IConstant.EDUCATION_ID),
                etCompanyName.getText().toString().trim(),
                etDesignation.getText().toString().trim(),
                strCity,
                etAnnualIncome.getText().toString().trim(),
                etOtherIncome.getText().toString().trim(),
                etAgriLand.getText().toString().trim(),
                SharedPref.getPrefs(_act, IConstant.USER_ID));

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
                            SharedPref.setPrefs(_act, IConstant.AGRI_LAND, String.valueOf(jsonObjectData.getString("agriculture_land")));
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
                          //  SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, String.valueOf(jsonObjectData.getString("email_verified")));
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



                            if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
                                if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                                    Helper_Method.hideSoftInput(_act);
                                    Helper_Method.dismissProgessBar();
                                    Helper_Method.toaster(_act, stringMsg);
                                    onBackPressed();
                                }
                            }else {
                                Helper_Method.hideSoftInput(_act);
                                Helper_Method.dismissProgessBar();
                                Helper_Method.toaster(_act, stringMsg);
                                Helper_Method.intentActivity(_act, FamilyDetailsActivity.class, false);
                            }


                        } else {
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

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

    private void webcallprofession() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETProfession();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    professionObjectArrayList = new ArrayList<>();
                    professionObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("occupation_list");
                            professionObjectArrayList.add(new ProfessionObject("0", getResources().getString(R.string.lbl_select_profession)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    professionObjectArrayList.add(new ProfessionObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (professionObjectArrayList.size() == 0) {


                            } else {
                                spinnerProfession.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                                spinnerProfession_Adapter = new ArrayAdapter<ProfessionObject>(_act, android.R.layout.simple_spinner_item, professionObjectArrayList);
                                spinnerProfession_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerProfession.setAdapter(spinnerProfession_Adapter);
                                Helper_Method.dismissProgessBar();
                                spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strProfessionId = professionObjectArrayList.get(i).id;
                                        strProfession = professionObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "DESIGNATION: " + SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.DESIGNATION));

                                if (SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID) != null &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID).isEmpty() &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID).equals("null") &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID).equals("")) {

                                    for (int k = 0; k < professionObjectArrayList.size(); k++) {
                                        Log.d("Vijendra", "getName: " + professionObjectArrayList.get(k).getId());
                                        if (professionObjectArrayList.get(k).getId().equals
                                                (SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.OCCUPATION_ID))) {
                                            spinnerProfession.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            professionObjectArrayList.clear();
                            spinnerProfession.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                            spinnerProfession_Adapter = new ArrayAdapter<ProfessionObject>(_act, android.R.layout.simple_spinner_item, professionObjectArrayList);
                            spinnerProfession_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerProfession.setAdapter(spinnerProfession_Adapter);
                            Helper_Method.dismissProgessBar();


                        }
                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK) {

                //String requiredValue = data.getStringExtra("key");
                //Helper_Method.intentActivity(_act, LoginActivity.class, true);

                if (SharedPref.getPrefs(_act, IConstant.EDUCATION_ID) != null
                        && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).isEmpty()
                        && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("null")
                        && !SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvEducation.setText(SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME));
                } else {
                }
            }
        } catch (Exception ex) {
            Toast.makeText(_act, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValid() {

        /*if (validations.isBlank(etCompanyName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etCompanyName.startAnimation(shake);
            etCompanyName.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etDesignation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etDesignation.startAnimation(shake);
            etDesignation.setError(IErrors.EMPTY);
            return false;
        }*/
       /* if (validations.isBlank(etJobLocation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etJobLocation.startAnimation(shake);
            etJobLocation.setError(IErrors.EMPTY);
            return false;
        }*/
       /* if (validations.isBlank(etAnnualIncome)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAnnualIncome.startAnimation(shake);
            etAnnualIncome.setError(IErrors.EMPTY);
            return false;
        }*/
        /*if (validations.isBlank(etOtherIncome)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etOtherIncome.startAnimation(shake);
            etOtherIncome.setError(IErrors.EMPTY);
            return false;
        }*/

        return true;
    }

    private void webcallCityList(String state_id) {

        // Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTCity(state_id);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    cityObjectArrayList = new ArrayList<>();
                    cityObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("city_list");
                            cityObjectArrayList.add(new CityObject("0", getResources().getString(R.string.lbl_select_city)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    cityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (cityObjectArrayList.size() == 0) {


                            } else {
                                spinnerCitylist.setTitle(getResources().getString(R.string.lbl_city_hint));
                                spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, cityObjectArrayList);
                                spinnerCitylist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCitylist.setAdapter(spinnerCitylist_Adapter);
                                spinnerCitylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strCityId = cityObjectArrayList.get(i).id;
                                        strCity = cityObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                if (SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_ID) != null &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_ID).isEmpty() &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_ID).equals("null") &&
                                        !SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_ID).equals("")) {

                                    for (int k = 0; k < cityObjectArrayList.size(); k++) {
                                        if (cityObjectArrayList.get(k).getId().equals(SharedPref.getPrefs(ProfessionalDetailsActivity.this, IConstant.CITY_ID))) {
                                            spinnerCitylist.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            cityObjectArrayList.clear();
                            spinnerCitylist.setTitle(getResources().getString(R.string.lbl_city_hint));
                            spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, cityObjectArrayList);
                            spinnerCitylist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCitylist.setAdapter(spinnerCitylist_Adapter);
                            // Helper_Method.toaster(_act, stringMsg);
                            // scheduleDismiss();


                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } finally {

                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

}