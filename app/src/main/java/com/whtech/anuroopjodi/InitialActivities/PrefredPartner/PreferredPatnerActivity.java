package com.whtech.anuroopjodi.InitialActivities.PrefredPartner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whtech.anuroopjodi.Adapter.ManglikAdapter;
import com.whtech.anuroopjodi.Adapter.PrefredPartenerMaritalStatusAdapter;
import com.whtech.anuroopjodi.Adapter.WorkingAdapter;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.RecyclerItemClickListener;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.SignUpActivity;
import com.whtech.anuroopjodi.Object.Caste;
import com.whtech.anuroopjodi.Object.CityObject;
import com.whtech.anuroopjodi.Object.CommonCountObject;
import com.whtech.anuroopjodi.Object.CommonYesNoObject;
import com.whtech.anuroopjodi.Object.HeightObject;
import com.whtech.anuroopjodi.Object.MaritalStatusObj;
import com.whtech.anuroopjodi.Object.StateObject;
import com.whtech.anuroopjodi.Object.SubcasteObject;
import com.whtech.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferredPatnerActivity extends BaseActivity {

    private static final String TAG = "PreferredActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private int mNoOfColumns = 0;
    private String user_profile_path = null;

    //Age Spinner
    private ArrayList<CommonCountObject> ageObjectArrayList;
    private Spinner spinnerAgeFrom, spinnerAgeTo;
    private ArrayAdapter<CommonCountObject> spinnerAgeFrom_Adapter, spinnerAgeTo_Adapter;
    private String strFromAgeId = "0", strFromAge, strToAgeId = "0", strToAge;

    //Height Spinner
    private ArrayList<HeightObject> heightObjectArrayList;
    private SearchableSpinner spinnerHeightFrom, spinnerHeightTo;
    private ArrayAdapter<HeightObject> spinnerHeightFrom_Adapter, spinnerHeightTo_Adapter;
    private String strHeightFromId = "0", strHeightFrom, strHeightToId = "0", strHeightTo;

    //Caste Spinner Zone
    private ArrayList<Caste> casteArrayList;
    private SearchableSpinner spinnerCastelist;
    private ArrayAdapter<Caste> spinnerCastelist_Adapter;
    private String strCasteId = "0", strCaste;

    //Subcaste Spinner Zone
    private ArrayList<SubcasteObject> subcasteObjectArrayList;
    private SearchableSpinner spinnerSubcastelist;
    private ArrayAdapter<SubcasteObject> spinnerSubcastelist_Adapter;
    private String strSubCasteId = "-1", strSubCaste;
    private TextView tvSubcaste;

    //State Spinner Zone
    private ArrayList<StateObject> stateObjectArrayList;
    private SearchableSpinner spinnerStatelist;
    private ArrayAdapter<StateObject> spinnerStatelist_Adapter;
    private String strStateId = "0", strState;

    //City Spinner Zone
    private ArrayList<CityObject> cityObjectArrayList;
    private SearchableSpinner spinnerCitylist;
    private ArrayAdapter<CityObject> spinnerCitylist_Adapter;
    private String strCityId = "0", strCity;

    //Manglik Types
    private ArrayList<CommonYesNoObject> manglikObjArrayList, workingStatusObjArrayList;
    private RecyclerView rvManglik, rvWorkingStatus;
    private ManglikAdapter manglikAdapter;
    private WorkingAdapter workingAdapter;
    private GridLayoutManager getGridLayoutManagerManglik, getGridLayoutManagerWorking;
    private String strManglik = null/*, strWorking = null*/;

    //Marital Status Types
    private ArrayList<MaritalStatusObj> maritalStatusObjArrayList;
    private RecyclerView rvMaritalStatus;
    private PrefredPartenerMaritalStatusAdapter maritalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerMaritalStatus;
    // private String strMaritalStatus = null;

    private TextView tvEducation, tvMotherTougue;
    private EditText etGotra;

    private String strMaritalStatusId, strMaritalStatus, allMaritalStatusString, allMaritalStatusIds;
    private List<String> listMaritalStatusId, listMaritalStatus;

    private String strWorkingId, strWorking, allWorkingString, allWorkingIds;
    private List<String> listWorkingId, listWorking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferred_patner);

        _act = PreferredPatnerActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        mNoOfColumns = SignUpActivity.Utility.calculateNoOfColumns(getApplicationContext(), 120);
        Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_partner_preference));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        maritalStatusObjArrayList = new ArrayList<>();
        listMaritalStatusId = new ArrayList<>();
        listMaritalStatus = new ArrayList<>();

        workingStatusObjArrayList = new ArrayList<>();
        listWorkingId = new ArrayList<>();
        listWorking = new ArrayList<>();

        spinnerHeightFrom = findViewById(R.id.spinnerHeightFrom);
        spinnerHeightTo = findViewById(R.id.spinnerHeightTo);
        spinnerStatelist = findViewById(R.id.spinnerStatelist);
        spinnerCitylist = findViewById(R.id.spinnerCitylist);
        spinnerSubcastelist = findViewById(R.id.spinnerSubcastelist);
        tvSubcaste = findViewById(R.id.tvSubcaste);
        spinnerCastelist = findViewById(R.id.spinnerCastelist);
        tvEducation = findViewById(R.id.tvEducation);
        tvMotherTougue = findViewById(R.id.tvMotherTougue);
        etGotra = findViewById(R.id.etGotra);
        etGotra.setText("");

        //Education
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID) != null
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).isEmpty()
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("null")
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("")) {

            //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
            //selectedBrandsIds = Arrays.asList(myArray);
            tvEducation.setText(SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_NAME));
        } else {
        }

        //Mother Tongue
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).isEmpty()
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("null")
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("")) {

            //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
            //selectedBrandsIds = Arrays.asList(myArray);
            tvMotherTougue.setText(SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE));
        } else {
        }

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Helper_Method.intentActivity(_act, PackageActivity.class, false);

                maritalStatusCommaSepratedString(_act);
                workingCommaSepratedString(_act);
               /* if (listMaritalStatusId.size()==0)
                {
                    Helper_Method.toaster(_act,"Select Marital Status");
                }
                else
                {
                    maritalStatusCommaSepratedString(_act);
                }*/
                if (strFromAgeId != null && !strFromAgeId.isEmpty() && !strFromAgeId.equals("null") && !strFromAgeId.equals("0")) {

                    Log.d("Hcheck", "onClick: onepass");
                    if (strToAgeId != null && !strToAgeId.isEmpty() && !strToAgeId.equals("null") && !strToAgeId.equals("0")) {

                        Log.d("Hcheck", "onClick: twopass");
                        if (strHeightFromId != null && !strHeightFromId.isEmpty() && !strHeightFromId.equals("null") && !strHeightFromId.equals("0")) {
                            Log.d("Hcheck", "onClick: threepass");
                            if (strHeightToId != null && !strHeightToId.isEmpty() && !strHeightToId.equals("null") && !strHeightToId.equals("0")) {

                                Log.d("Hcheck", "onClick: fourpass");
                                //if (strSubCasteId != null && !strSubCasteId.isEmpty() && !strSubCasteId.equals("null") && !strSubCasteId.equals("0")) {
                                //if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID) != null && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).isEmpty() && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).equals("null") && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).equals("")) {
                                    Log.d("Hcheck", "onClick: fivepass");
                                    if (isValid()) {
                                        Log.d("Hcheck", "onClick: sixpass");
                                        if (strManglik != null && !strManglik.isEmpty() && !strManglik.equals("null") && !strManglik.equals("0")) {
                                            Log.d("Hcheck", "onClick: sevenpass");
                                            if (listMaritalStatusId.size() != 0) {
                                                Log.d("Hcheck", "onClick: eightpass");
                                                if (listWorkingId.size() != 0) {
                                                    Log.d("Hcheck", "onClick: ninepass");
                                                  //  if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID) != null && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).isEmpty() && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("null") && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("")) {

                                                            if (connectionDetector.isConnectionAvailable()) {
                                                                webcallProfessionDetails();
                                                                Log.d("afterwebcall", "onClick: after webcall");
                                                            } else {
                                                                Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                                            }



                                                   /* } else {
                                                        Helper_Method.toaster(_act, "Select Education");
                                                    }*/
                                                } else {
                                                    Helper_Method.toaster(_act, "Select Working Status");
                                                }
                                            } else {
                                                Helper_Method.toaster(_act, "Select Marital Status");
                                            }
                                        } else {
                                            Helper_Method.toaster(_act, "Select Manglik or Not");
                                        }
                                    } else {
                                        Helper_Method.toaster(_act, "Check and fill all the fields");
                                    }


                                /*} else {
                                    Helper_Method.toaster(_act, "Select Sub Caste");
                                }*/
                            } else {
                                Helper_Method.toaster(_act, "Select Height To");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Height From");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Select Age To");
                    }
                } else {
                    Helper_Method.toaster(_act, "Select Age From");
                }

            }
        });

        tvSubcaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_act, SubCasteMultiSelectionActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        tvEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_act, PreferedPartnerEducationMutiSelectionActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        tvMotherTougue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_act, PreferedPartnerMotherTongueActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        WorkingStatus();
    }

    private void webcallProfessionDetails() {

        // this opertion perform because of last string is (,)
        String motherTounge = "";
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null &&
                SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).length() > 0) {
            motherTounge = SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).substring(0,
                    SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).length() - 1);
        }

        String education = "";
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID) != null &&
                SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).length() > 0) {
            education = SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).substring(0,
                    SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).length() - 1);
        }

        //String subcaste = "";
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID) != null &&
                SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).length() > 0) {
            strSubCasteId = SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).substring(0,
                    SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).length() - 1);
        }
        Log.d("strSubCasteId: ", "onClick: " + strSubCasteId);

        Helper_Method.showProgressBar(_act, "Updating Partner Preferences Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTPatnerPrefrences(
                strFromAge, strToAge, strHeightFromId, strHeightToId,
                SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID),
                motherTounge,
                strSubCasteId, etGotra.getText().toString().trim(), strManglik, strStateId, strCityId,
                education,
                SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_WORKING_NAME),
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
                            SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, String.valueOf(jsonObjectData.getString("email_verified")));
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
                            //Helper_Method.intentActivity(_act, PackageActivity.class, false);
                            Intent intent = new Intent(_act, PackageActivity.class);
                            intent.putExtra("ActivityName", "PreferredPatner");
                            startActivity(intent);


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

    private boolean isValid() {

       /* if (validations.isBlank(etGotra)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etGotra.startAnimation(shake);
            etGotra.setError(IErrors.EMPTY);
            return false;
        }*/
        return true;
    }

    private void WorkingStatus() {

        rvWorkingStatus = findViewById(R.id.rvWorkingStatus);
        workingStatusObjArrayList.clear();
        workingStatusObjArrayList.add(new CommonYesNoObject("3", "ANY", false));
        workingStatusObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        workingStatusObjArrayList.add(new CommonYesNoObject("2", "No", false));


        if (workingStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvWorkingStatus.setVisibility(View.GONE);

        } else {


            rvWorkingStatus.setHasFixedSize(true);
            rvWorkingStatus.setNestedScrollingEnabled(false);
            // workingStatusObjArrayList.get(0).setSelected(true);
            // workingStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            workingAdapter = new WorkingAdapter(_act, workingStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvWorkingStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerManglik = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerManglik.setOrientation(RecyclerView.VERTICAL);
            rvWorkingStatus.setLayoutManager(getGridLayoutManagerManglik);
            rvWorkingStatus.setItemAnimator(new DefaultItemAnimator());
            rvWorkingStatus.setAdapter(workingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            workingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvWorkingStatus.setVisibility(View.VISIBLE);

        }
        MaritalStatus();
    }

    private void MaritalStatus() {

        rvMaritalStatus = findViewById(R.id.rvMaritalStatus);

        maritalStatusObjArrayList.clear();
        maritalStatusObjArrayList.add(new MaritalStatusObj("1", "Single", false));
        //maritalStatusObjArrayList.add(new MaritalStatusObj("2", "Married", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("3", "Divorce", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("4", "Awaiting Divorce", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("5", "Separated", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("6", "Widow", false));


        if (maritalStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvMaritalStatus.setVisibility(View.GONE);

        } else {


            rvMaritalStatus.setHasFixedSize(true);
            rvMaritalStatus.setNestedScrollingEnabled(false);
            // maritalStatusObjArrayList.get(0).setSelected(true);
            // maritalStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            maritalStatusAdapter = new PrefredPartenerMaritalStatusAdapter(_act, maritalStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvMaritalStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerMaritalStatus = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerMaritalStatus.setOrientation(RecyclerView.VERTICAL);
            rvMaritalStatus.setLayoutManager(getGridLayoutManagerMaritalStatus);
            rvMaritalStatus.setItemAnimator(new DefaultItemAnimator());
            rvMaritalStatus.setAdapter(maritalStatusAdapter);
            //profileCreatedByAdapter.setSelected(0);
            maritalStatusAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvMaritalStatus.setVisibility(View.VISIBLE);

            /*rvMaritalStatus.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            maritalStatusObjArrayList.get(position).setSelected(true);

                           *//* for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {
                                if (i == position) {
                                    maritalStatusObjArrayList.get(i).setSelected(true);
                                } else {
                                    maritalStatusObjArrayList.get(i).setSelected(false);
                                }
                            }*//*

                            // pos = profileCreatedForAdapter.setSelected(position);
                            maritalStatusAdapter.notifyDataSetChanged();
                            //strMaritalStatus = maritalStatusObjArrayList.get(position).id;
                            //Log.d(TAG, "onItemClick: " + strMaritalStatus);

                        }
                    })
            );*/
        }
        Manglik();
    }

    private void Manglik() {

        rvManglik = findViewById(R.id.rvManglik);
        manglikObjArrayList = new ArrayList<>();
        manglikObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        manglikObjArrayList.add(new CommonYesNoObject("2", "No", false));

        if (manglikObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvManglik.setVisibility(View.GONE);

        } else {


            rvManglik.setHasFixedSize(true);
            rvManglik.setNestedScrollingEnabled(false);
            // manglikObjArrayList.get(0).setSelected(true);
            // manglikObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            manglikAdapter = new ManglikAdapter(_act, manglikObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvManglik.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerManglik = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerManglik.setOrientation(RecyclerView.VERTICAL);
            rvManglik.setLayoutManager(getGridLayoutManagerManglik);
            rvManglik.setItemAnimator(new DefaultItemAnimator());
            rvManglik.setAdapter(manglikAdapter);
            //profileCreatedByAdapter.setSelected(0);
            manglikAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvManglik.setVisibility(View.VISIBLE);

            rvManglik.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < manglikObjArrayList.size(); i++) {

                                if (i == position) {
                                    manglikObjArrayList.get(i).setSelected(true);
                                } else {
                                    manglikObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            manglikAdapter.notifyDataSetChanged();
                            strManglik = manglikObjArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strManglik);

                        }
                    })
            );
        }
        Age();
    }

    public void Age() {
        ageObjectArrayList = new ArrayList<>();
        ageObjectArrayList.add(new CommonCountObject("0", "Select Age", false));
        for (int i = 18; i < 100; i++) {
            ageObjectArrayList.add(new CommonCountObject(String.valueOf(i), String.valueOf(i), false));
        }

        //From Age
        spinnerAgeFrom = findViewById(R.id.spinnerAgeFrom);
        spinnerAgeFrom_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, ageObjectArrayList);
        spinnerAgeFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeFrom.setAdapter(spinnerAgeFrom_Adapter);
        spinnerAgeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strFromAgeId = ageObjectArrayList.get(i).id;
                strFromAge = ageObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //To Age
        spinnerAgeTo = findViewById(R.id.spinnerAgeTo);
        spinnerAgeTo_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, ageObjectArrayList);
        spinnerAgeTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeTo.setAdapter(spinnerAgeTo_Adapter);
        spinnerAgeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strToAgeId = ageObjectArrayList.get(i).id;
                strToAge = ageObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        webcallHeight();
    }

    private void webcallHeight() {

        //Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETHeight();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    heightObjectArrayList = new ArrayList<>();
                    heightObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("height_list");
                            heightObjectArrayList.add(new HeightObject("0", getResources().getString(R.string.lbl_select_height)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    heightObjectArrayList.add(new HeightObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (heightObjectArrayList.size() == 0) {


                            } else {

                                //From height
                                spinnerHeightFrom.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                                spinnerHeightFrom_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                                spinnerHeightFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHeightFrom.setAdapter(spinnerHeightFrom_Adapter);
                                spinnerHeightFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strHeightFromId = heightObjectArrayList.get(i).id;
                                        strHeightFrom = heightObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < heightObjectArrayList.size(); k++) {
                                        if (heightObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerHeightFrom.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                                //To height
                                spinnerHeightTo.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                                spinnerHeightTo_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                                spinnerHeightTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHeightTo.setAdapter(spinnerHeightTo_Adapter);
                                spinnerHeightTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strHeightToId = heightObjectArrayList.get(i).id;
                                        strHeightTo = heightObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < heightObjectArrayList.size(); k++) {
                                        if (heightObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerHeightTo.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/
                            }

                        } else {
                            heightObjectArrayList.clear();

                            //fromHeight
                            spinnerHeightFrom.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                            spinnerHeightFrom_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                            spinnerHeightFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHeightFrom.setAdapter(spinnerHeightFrom_Adapter);

                            //To Height
                            spinnerHeightTo.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                            spinnerHeightTo_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                            spinnerHeightTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHeightTo.setAdapter(spinnerHeightTo_Adapter);

                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    // Helper_Method.dismissProgessBar();

                } finally {
                    //Helper_Method.dismissProgessBar();
                    webcallCasteList();
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

    private void webcallCasteList() {

        // Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETCaste();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    casteArrayList = new ArrayList<>();
                    casteArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("caste_list");
                            casteArrayList.add(new Caste("-1", "ANY"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    casteArrayList.add(new Caste(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (casteArrayList.size() == 0) {


                            } else {
                                spinnerCastelist.setTitle(getResources().getString(R.string.lbl_caste_hint));
                                spinnerCastelist_Adapter = new ArrayAdapter<Caste>(_act, android.R.layout.simple_spinner_item, casteArrayList);
                                spinnerCastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCastelist.setAdapter(spinnerCastelist_Adapter);
                                spinnerCastelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strCasteId = casteArrayList.get(i).getId();
                                        strCaste = casteArrayList.get(i).getCaste_name();
                                        Toast.makeText(PreferredPatnerActivity.this, "" + strCasteId, Toast.LENGTH_SHORT).show();
                                        SharedPref.setPrefs(PreferredPatnerActivity.this, "SubcastePartner", strCasteId);
                                        webcallSubcasteList(strCasteId);
                                        //webcallSubcasteList("1");
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "SUBCASTE: " + SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE));
                                if (SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE) != null &&
                                        !SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE).isEmpty() &&
                                        !SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE).equals("null") &&
                                        !SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE).equals("")) {

                                    for (int k = 0; k < casteArrayList.size(); k++) {
                                        if (casteArrayList.get(k).getCaste_name().equals
                                                (SharedPref.getPrefs(PreferredPatnerActivity.this, IConstant.CASTE))) {
                                            spinnerCastelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            casteArrayList.clear();
                            spinnerCastelist.setTitle(getResources().getString(R.string.lbl_caste_hint));
                            spinnerCastelist_Adapter = new ArrayAdapter<Caste>(_act, android.R.layout.simple_spinner_item, casteArrayList);
                            spinnerCastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCastelist.setAdapter(spinnerCastelist_Adapter);
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
                    webcallStateList();
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

    private void webcallSubcasteList(String casteID) {

        // Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETSubcaste(casteID);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    subcasteObjectArrayList = new ArrayList<>();
                    subcasteObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("subcaste_list");
                            subcasteObjectArrayList.add(new SubcasteObject("0", getResources().getString(R.string.lbl_select_subcaste)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    subcasteObjectArrayList.add(new SubcasteObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();
                                }
                            }

                            if (subcasteObjectArrayList.size() == 0) {

                            } else {
                                spinnerSubcastelist.setTitle(getResources().getString(R.string.lbl_subcaste_hint));
                                spinnerSubcastelist_Adapter = new ArrayAdapter<SubcasteObject>(_act, android.R.layout.simple_spinner_item, subcasteObjectArrayList);
                                spinnerSubcastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerSubcastelist.setAdapter(spinnerSubcastelist_Adapter);
                                spinnerSubcastelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strSubCasteId = subcasteObjectArrayList.get(i).id;
                                        strSubCaste = subcasteObjectArrayList.get(i).casteName;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < subcasteObjectArrayList.size(); k++) {
                                        if (subcasteObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerSubcastelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            subcasteObjectArrayList.clear();
                            spinnerSubcastelist.setTitle(getResources().getString(R.string.lbl_subcaste_hint));
                            spinnerSubcastelist_Adapter = new ArrayAdapter<SubcasteObject>(_act, android.R.layout.simple_spinner_item, subcasteObjectArrayList);
                            spinnerSubcastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubcastelist.setAdapter(spinnerSubcastelist_Adapter);
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
                    webcallStateList();
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

    private void webcallStateList() {

        // Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETState();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    stateObjectArrayList = new ArrayList<>();
                    stateObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("states_list");
                            stateObjectArrayList.add(new StateObject("0", getResources().getString(R.string.lbl_select_state)));
                            stateObjectArrayList.add(new StateObject("-1", "ANY"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    stateObjectArrayList.add(new StateObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (stateObjectArrayList.size() == 0) {


                            } else {
                                spinnerStatelist.setTitle(getResources().getString(R.string.lbl_state_hint));
                                spinnerStatelist_Adapter = new ArrayAdapter<StateObject>(_act, android.R.layout.simple_spinner_item, stateObjectArrayList);
                                spinnerStatelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerStatelist.setAdapter(spinnerStatelist_Adapter);
                                spinnerStatelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strStateId = stateObjectArrayList.get(i).id;
                                        strState = stateObjectArrayList.get(i).name;
                                        webcallCityList(strStateId);
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < stateObjectArrayList.size(); k++) {
                                        if (stateObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerStatelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            stateObjectArrayList.clear();
                            spinnerStatelist.setTitle(getResources().getString(R.string.lbl_state_hint));
                            spinnerStatelist_Adapter = new ArrayAdapter<StateObject>(_act, android.R.layout.simple_spinner_item, stateObjectArrayList);
                            spinnerStatelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerStatelist.setAdapter(spinnerStatelist_Adapter);
                            webcallCityList("0");
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

                } /*finally {
                    Helper_Method.dismissProgessBar();
                }*/
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

                        cityObjectArrayList.add(new CityObject("-1", "ANY"));
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
                                        strCityId = cityObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < cityObjectArrayList.size(); k++) {
                                        if (cityObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerCitylist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

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
                                    strCityId = cityObjectArrayList.get(i).city_name;
                                }

                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    return;
                                }
                            });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 101 && resultCode == RESULT_OK) {

                //String requiredValue = data.getStringExtra("key");
                //Helper_Method.intentActivity(_act, LoginActivity.class, true);

                if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID) != null
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).isEmpty()
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).equals("null")
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvSubcaste.setText(SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_SUBCASTE_NAME));
                } else {
                }
            } else if (requestCode == 100 && resultCode == RESULT_OK) {

                //String requiredValue = data.getStringExtra("key");
                //Helper_Method.intentActivity(_act, LoginActivity.class, true);

                if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID) != null
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).isEmpty()
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("null")
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvEducation.setText(SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_EDUCATION_NAME));
                } else {
                }
            } else if (requestCode == 200 && resultCode == RESULT_OK) {
                if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).isEmpty()
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("null")
                        && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(_act, IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvMotherTougue.setText(SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE));
                } else {
                }
            }
        } catch (Exception ex) {
            Toast.makeText(_act, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void maritalStatusCommaSepratedString(Activity _act) {

        listMaritalStatusId.clear();
        listMaritalStatus.clear();
        for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {

            if (maritalStatusObjArrayList.get(i).isSelected()) {
                strMaritalStatusId = maritalStatusObjArrayList.get(i).getId();
                strMaritalStatus = maritalStatusObjArrayList.get(i).getName();
                Log.d("MyTag", "commasepratedString: District Id :" + strMaritalStatusId);
                Log.d("MyTag", "commasepratedString: Taluka Id :" + strMaritalStatus);

                listMaritalStatusId.add(strMaritalStatusId);
                listMaritalStatus.add(strMaritalStatus);
                Log.d("MyTag", "listMaritalStatusId ids: " + maritalStatusObjArrayList.get(i).getId());
            }

            allMaritalStatusIds = "";
            for (String strIds : listMaritalStatusId) {
                allMaritalStatusIds += strIds + ",";
            }
            if (allMaritalStatusIds.endsWith(",")) {
                allMaritalStatusIds = allMaritalStatusIds.substring(0, allMaritalStatusIds.length() - 1);

            }

            allMaritalStatusString = "";
            for (String strData : listMaritalStatus) {
                allMaritalStatusString += strData + ",";
            }
            if (allMaritalStatusString.endsWith(",")) {
                allMaritalStatusString = allMaritalStatusString.substring(0, allMaritalStatusString.length() - 1);
            }
        }
        Log.d("mytag", "commaseprated Brands ids: " + allMaritalStatusIds);
        Log.d("mytag", "commaseprated Brands names: " + allMaritalStatusString);
        Log.d("mytag", "listMaritalStatusId ids: " + listMaritalStatus.size());
        Log.d("mytag", "listMaritalStatusId: " + listMaritalStatusId.size());

        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID, allMaritalStatusIds.toString());
        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_MARITAL_STATUS, allMaritalStatusString.toString());
    }

    public void workingCommaSepratedString(Activity _act) {
        listWorkingId.clear();
        listWorking.clear();
        for (int i = 0; i < workingStatusObjArrayList.size(); i++) {

            if (workingStatusObjArrayList.get(i).isSelected()) {
                strWorkingId = workingStatusObjArrayList.get(i).getId();
                strWorking = workingStatusObjArrayList.get(i).getName();
                Log.d("MyTag", "commasepratedString: District Id :" + strWorkingId);
                Log.d("MyTag", "commasepratedString: Taluka Id :" + strWorking);

                listWorkingId.add(strWorkingId);
                listWorking.add(strWorking);
                Log.d("MyTag", "listWorkingId ids: " + workingStatusObjArrayList.get(i).getId());
            }

            allWorkingIds = "";
            for (String strIds : listWorkingId) {
                allWorkingIds += strIds + ",";
            }
            if (allWorkingIds.endsWith(",")) {
                allWorkingIds = allWorkingIds.substring(0, allWorkingIds.length() - 1);

            }

            allWorkingString = "";
            for (String strData : listWorking) {
                allWorkingString += strData + ",";
            }
            if (allWorkingString.endsWith(",")) {
                allWorkingString = allWorkingString.substring(0, allWorkingString.length() - 1);
            }
        }
        Log.d("mytag", "commaseprated Brands ids: " + allWorkingIds);
        Log.d("mytag", "commaseprated Brands names: " + allWorkingString);
        Log.d("mytag", "listWorkingId ids: " + listWorking.size());
        Log.d("mytag", "listWorkingId: " + listWorkingId.size());

        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_WORKING_ID, allWorkingIds.toString());
        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_WORKING_NAME, allWorkingString.toString());
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