package com.whtech.anuroopjodi.InitialActivities.RegistrationSteps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whtech.anuroopjodi.Adapter.PhysicalStatusAdapter;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IErrors;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.RecyclerItemClickListener;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.Object.Caste;
import com.whtech.anuroopjodi.Object.CityObject;
import com.whtech.anuroopjodi.Object.MotherTongueObject;
import com.whtech.anuroopjodi.Object.PhysicalStatusObj;
import com.whtech.anuroopjodi.Object.StateObject;
import com.whtech.anuroopjodi.Object.SubcasteObject;
import com.whtech.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicDetailsAddAndEditActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "BasicDetailsAddAndEdit";
    int mNoOfColumns = 0;
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    //Mother Tongue Spinner Zone
    private ArrayList<MotherTongueObject> motherTongueObjectArrayList;
    private SearchableSpinner spinnerMotherTonguelist;
    private ArrayAdapter<MotherTongueObject> spinnerMotherTonguelist_Adapter;
    private String strMotherTongueId = "0", strMotherTongue;

    //Caste Spinner Zone
    private ArrayList<Caste> casteArrayList;
    private SearchableSpinner spinnerCastelist;
    private ArrayAdapter<Caste> spinnerCastelist_Adapter;
    private String strCasteId = "0", strCaste;

    //Subcaste Spinner Zone
    private ArrayList<SubcasteObject> subcasteObjectArrayList;
    private SearchableSpinner spinnerSubcastelist;
    private ArrayAdapter<SubcasteObject> spinnerSubcastelist_Adapter;
    private String strSubCasteId = "0", strSubCaste;

    //Physical Status Types
    private ArrayList<PhysicalStatusObj> physicalStatusObjArrayList;
    private RecyclerView rvPhysicalStatus;
    private PhysicalStatusAdapter physicalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerPhysicalStatus;
    private String strPhysicalStatus;
    //State Spinner Zone
    private ArrayList<StateObject> stateObjectArrayList;
    private SearchableSpinner spinnerStatelist;
    private ArrayAdapter<StateObject> spinnerStatelist_Adapter;
    private String strStateId = "0", strState;
    //City Spinner Zone
    private ArrayList<CityObject> cityObjectArrayList;
    private SearchableSpinner spinnerCitylist;
    private ArrayAdapter<CityObject> spinnerCitylist_Adapter;
    private String strCityId = "0", strCityName = "";

    //City Spinner Zone
    private ArrayList<CityObject> birthPlaceObjectArrayList;
    private SearchableSpinner spinnerBirthPlace;
    private ArrayAdapter<CityObject> spinnerBirthPlace_Adapter;
    private String strBirthPlaceId = "0", strBirthPlace;
    private TextView tvDOB, tvBirthTime;

    //City Spinner Zone
    private ArrayList<CityObject> workingCityObjectArrayList;
    private SearchableSpinner spinnerWorkingCity;
    private ArrayAdapter<CityObject> spinnerWorkingCity_Adapter;
    private String strWorkingCityId = "0", strWorkingCity;
    //private TextView tvDOB, tvBirthTime;

    private EditText etAddress, etExpectation, etNativePlace;

    private SimpleDateFormat df, ymd;
    private String formattedDate;
    private DatePickerDialog datePickerDialogBirthDate;
    private String strBirthDate = "Select Birth Date", strBirthTime = "Select Birth Time";
    private Calendar currentCal;
    private int hour, minute;
    private TimePickerDialog timePickerDialog;

    private String user_profile_path = null;
    private TextView btnStepOne;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details_add_and_edit);

        _act = BasicDetailsAddAndEditActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_basic_details_word));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        mNoOfColumns = SignUpActivity.Utility.calculateNoOfColumns(getApplicationContext(), 120);
        Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);


        currentCal = Calendar.getInstance();
        df = new SimpleDateFormat("dd MMM yyyy");
        ymd = new SimpleDateFormat("yyyy-MM-dd");
        //formattedDate = df.format(currentDate);
        // strCurrentDateToSet = ymd.format(currentDate);
//        System.out.println("Formated Date => " + formattedDate);
//        System.out.println("Formated Date => " + strCurrentDateToSet);


        tvDOB = findViewById(R.id.tvDOB);
        tvBirthTime = findViewById(R.id.tvBirthTime);
        etAddress = findViewById(R.id.etAddress);
        etExpectation = findViewById(R.id.etExpectation);
        spinnerMotherTonguelist = findViewById(R.id.spinnerMotherTonguelist);
        spinnerSubcastelist = findViewById(R.id.spinnerSubcastelist);
        spinnerCastelist = findViewById(R.id.spinnerCastelist);
        //spinnerCastelist.setEnabled(false);
        spinnerStatelist = findViewById(R.id.spinnerStatelist);
        spinnerCitylist = findViewById(R.id.spinnerCitylist);
        spinnerBirthPlace = findViewById(R.id.spinnerBirthPlace);
        spinnerWorkingCity = findViewById(R.id.spinnerWorkingCity);
        etNativePlace = findViewById(R.id.etNativePlace);
        btnStepOne = findViewById(R.id.btnStepOne);

        //Default Webcall
        if (connectionDetector.isConnectionAvailable()) {
            webcallBirthPlaces();
        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
        }


        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialogBirthDate = new DatePickerDialog(_act,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
                                // SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                formattedDate = df.format(date);
                                strBirthDate = ymd.format(date);
                                tvDOB.setText(formattedDate);
                                //refreshData();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialogBirthDate.show();
                //datePickerDialogBirthDate.getDatePicker().setMinDate(System.currentTimeMillis());
                //  datePickerDialogBirthDate.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialogBirthDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //datePickerDialogBirthDate.getDatePicker().setMinDate(System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000));
                //datePickerDialogBirthDate.getDatePicker().setMaxDate(System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000));
                datePickerDialogBirthDate.getDatePicker().setMaxDate(System.currentTimeMillis() - 568025136000L);

            }
        });

        tvBirthTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });

        findViewById(R.id.btnStepOne).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strBirthDate != null && !strBirthDate.isEmpty() && !strBirthDate.equals("null") && !strBirthDate.equals("Select Birth Date")) {
                    if (strBirthTime != null && !strBirthTime.isEmpty() && !strBirthTime.equals("null") && !strBirthTime.equals("Select Birth Time")) {
                        if (strBirthPlaceId != null && !strBirthPlaceId.isEmpty() && !strBirthPlaceId.equals("null") && !strBirthPlaceId.equals("0")) {
                            if (strMotherTongueId != null && !strMotherTongueId.isEmpty() && !strMotherTongueId.equals("null") && !strMotherTongueId.equals("0")) {
                                if (strSubCasteId != null && !strSubCasteId.isEmpty() && !strSubCasteId.equals("null") && !strSubCasteId.equals("0")) {
                                    if (strPhysicalStatus != null && !strPhysicalStatus.isEmpty() && !strPhysicalStatus.equals("null") && !strPhysicalStatus.equals("0")) {
                                        if (strStateId != null && !strStateId.isEmpty() && !strStateId.equals("null") && !strStateId.equals("0")) {
                                            if (strCityId != null && !strCityId.isEmpty() && !strCityId.equals("null") && !strCityId.equals("0")) {
                                                if (isValid()) {
                                                    if (connectionDetector.isConnectionAvailable()) {
                                                        webcallBasicDetails();
                                                    } else {
                                                        Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                                    }
                                                }
                                            } else {
                                                Helper_Method.toaster(_act, "Select City");
                                            }
                                        } else {
                                            Helper_Method.toaster(_act, "Select State");
                                        }
                                    } else {
                                        Helper_Method.toaster(_act, "Select Physical Status");
                                    }
                                } else {
                                    Helper_Method.toaster(_act, "Select Sub Caste");
                                }
                            } else {
                                Helper_Method.toaster(_act, "Select Mother Tongue");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Birth Place");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Select BirthTime");
                    }
                } else {
                    Helper_Method.toaster(_act, "Select Birth Date");
                }
            }
        });



        if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
            if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                setValuesToUpdate();
                btnStepOne.setText("Update");
            }
        }else {
            btnStepOne.setText("Next");
        }
    }

    private void setValuesToUpdate() {

       /* for (int i = 0; i < physicalStatusObjArrayList.size(); i++) {
            if (physicalStatusObjArrayList.get(i).name.equals(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.DISABILITY))) {
                physicalStatusObjArrayList.get(i).setSelected(true);
            } else {
                physicalStatusObjArrayList.get(i).setSelected(false);
            }
        }*/


        tvDOB.setText(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_DATE));
        tvBirthTime.setText(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_TIME));
        etAddress.setText(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.USER_ADDRESS));
        etExpectation.setText(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.USER_EXPECTATION));
        etNativePlace.setText(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.NATIVE_PLACE));

        strBirthDate = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_DATE);
        strBirthPlace = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE);
        strBirthTime = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_TIME);
        strMotherTongueId = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME);
        strSubCasteId = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE);
        strPhysicalStatus = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.DISABILITY);
        strStateId = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_ID);
        strCityId = SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_ID);

       // Log.d(TAG, "DISABILITY: "+SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.DISABILITY));
//        Log.d(TAG, "physicalStatusObjArrayList: "+physicalStatusObjArrayList.size());

      /*else if(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.DISABILITY).equals("No")) {
                if (physicalStatusObjArrayList.get(i).name.equals("NO")) {
                    physicalStatusObjArrayList.get(i).setSelected(true);
                } else {
                    physicalStatusObjArrayList.get(i).setSelected(false);
                }
            }
        }*/

    }

    private void webcallBasicDetails() {

        Log.d("SendingData", "strCityId: " + strCityId);
        Log.d("SendingData", "strPhysicalStatus: " + strPhysicalStatus);
        Log.d("SendingData", "etExpectation: " + etExpectation.getText().toString());
        Helper_Method.showProgressBar(_act, "Updating Basic Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTBasicDetails(

                strBirthDate,
                strBirthPlace,
                strBirthTime,
                strMotherTongueId,
                strSubCasteId,
                strPhysicalStatus,
                etAddress.getText().toString().trim(),
                etExpectation.getText().toString().trim(),
                strStateId,
                strCityId,
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                strCasteId,
                strWorkingCityId,
                etNativePlace.getText().toString()


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

                        //  if (is_verified.equalsIgnoreCase("true")) {
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");

                        if (stringCode.equalsIgnoreCase("true")) {
                            user_profile_path = i.getString("user_profile_path");


                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                            SharedPref.setPrefs(_act, IConstant.USER_ID, String.valueOf(jsonObjectData.getString("id")));
                            SharedPref.setPrefs(_act, IConstant.CASTE, String.valueOf(jsonObjectData.getString("cast")));
                            SharedPref.setPrefs(_act, IConstant.WORKING_CITY, String.valueOf(jsonObjectData.getString("working_city_present")));
                            SharedPref.setPrefs(_act, IConstant.NATIVE_PLACE, String.valueOf(jsonObjectData.getString("native_place")));
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
                            SharedPref.setPrefs(_act, IConstant.USER_EXPECTATION, String.valueOf(jsonObjectData.getString("expectaion")));
                            //SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("entryDate"));
                            SharedPref.setPrefs(_act, IConstant.SUBCASTE, String.valueOf(jsonObjectData.getString("subCast")));
                            SharedPref.setPrefs(_act, IConstant.INCOME, String.valueOf(jsonObjectData.getString("income")));
                            SharedPref.setPrefs(_act, IConstant.PHYSIQUE, String.valueOf(jsonObjectData.getString("physique")));
                            SharedPref.setPrefs(_act, IConstant.OCCUPATION_ID, String.valueOf(jsonObjectData.getString("occupation")));
                            SharedPref.setPrefs(_act, IConstant.EDUCATION_ID, String.valueOf(jsonObjectData.getString("education")));
                            SharedPref.setPrefs(_act, IConstant.COMPANY, String.valueOf(jsonObjectData.getString("company")));
                            SharedPref.setPrefs(_act, IConstant.DESIGNATION, String.valueOf(jsonObjectData.getString("designation")));
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
                            Log.d("Expectation", "onResponse: "+jsonObjectData.getString("expectaion"));
                            SharedPref.setPrefs(_act, IConstant.USER_EXPECTATION, jsonObjectData.getString("expectaion"));
                            // SharedPref.setPrefs(_act, IConstant.USER_ID, jsonObjectData.getString("lastSeen"));
                            SharedPref.setPrefs(_act, IConstant.TRANSACTION_ID, jsonObjectData.getString("transactionId"));
                            SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, jsonObjectData.getString("email_verified"));
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

                              /*  if (SharedPref.getPrefs(_act, IConstant.STEP_ONE) != null && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).isEmpty() && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equals("null") && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equals("") && !SharedPref.getPrefs(_act, IConstant.STEP_ONE).equalsIgnoreCase("1")) {
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
                                    Helper_Method.intentActivity(_act, PackageActivity.class, true);
                                } else {
                                    Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                                }*/
                            if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
                                if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                                    Helper_Method.hideSoftInput(_act);
                                    Helper_Method.dismissProgessBar();
                                    Helper_Method.toaster(_act, stringMsg);
                                    onBackPressed();
                                }
                            } else {
                                Helper_Method.hideSoftInput(_act);
                                Helper_Method.dismissProgessBar();
                                Helper_Method.toaster(_act, stringMsg);
                                /* ActivityCompat.finishAffinity(_act);*/
                                Helper_Method.intentActivity(_act, PersonalDetailsActivity.class, false);
                            }


                        } else {
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                        }
                       /* } else {
                            Helper_Method.dismissProgessBar();
                            Intent intent = new Intent(_act, BothOtpActivity.class);
                            intent.putExtra("Activity", "Login");
                            intent.putExtra("mobile", isRegMobile);
                            intent.putExtra("email", isRegEmail);
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            startActivityForResult(intent, REQUEST_CODE);
                        }*/

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

    private void webcallBirthPlaces() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTCity("");

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    birthPlaceObjectArrayList = new ArrayList<>();
                    birthPlaceObjectArrayList.clear();

                    workingCityObjectArrayList = new ArrayList<>();
                    workingCityObjectArrayList.clear();

                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("city_list");
                            birthPlaceObjectArrayList.add(new CityObject("0", getResources().getString(R.string.lbl_select_birth_place)));
                            workingCityObjectArrayList.add(new CityObject("0", getResources().getString(R.string.lbl_working_city_hint)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    birthPlaceObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));
                                    workingCityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (birthPlaceObjectArrayList.size() == 0 && workingCityObjectArrayList.size() == 0) {


                            } else {
                                spinnerBirthPlace.setTitle(getResources().getString(R.string.lbl_select_birth_place));
                                spinnerBirthPlace_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item,
                                        birthPlaceObjectArrayList);
                                spinnerBirthPlace_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerBirthPlace.setAdapter(spinnerBirthPlace_Adapter);
                                spinnerBirthPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strBirthPlaceId = birthPlaceObjectArrayList.get(i).id;
                                        strBirthPlace = birthPlaceObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                spinnerWorkingCity.setTitle(getResources().getString(R.string.lbl_working_city_hint));
                                spinnerWorkingCity_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item,
                                        workingCityObjectArrayList);
                                spinnerWorkingCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerWorkingCity.setAdapter(spinnerWorkingCity_Adapter);
                                spinnerWorkingCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strWorkingCityId = birthPlaceObjectArrayList.get(i).id;
                                        strWorkingCity = birthPlaceObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE).equals("")) {

                                    for (int k = 0; k < birthPlaceObjectArrayList.size(); k++) {
                                        if (birthPlaceObjectArrayList.get(k).getCasteName().equals(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.BIRTH_PLACE))) {
                                            spinnerBirthPlace.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.WORKING_CITY) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.WORKING_CITY).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.WORKING_CITY).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.WORKING_CITY).equals("")) {

                                    for (int k = 0; k < workingCityObjectArrayList.size(); k++) {
                                        if (workingCityObjectArrayList.get(k).getCasteName().equals(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.WORKING_CITY))) {
                                            spinnerWorkingCity.setSelection(k);
                                        }
                                    }
                                } else {

                                }


                            }

                        } else {
                            birthPlaceObjectArrayList.clear();
                            spinnerBirthPlace.setTitle(getResources().getString(R.string.lbl_birth_place_hint));
                            spinnerBirthPlace_Adapter = new ArrayAdapter<CityObject>(_act, android.R.layout.simple_spinner_item, birthPlaceObjectArrayList);
                            spinnerBirthPlace_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerBirthPlace.setAdapter(spinnerBirthPlace_Adapter);
                            // Helper_Method.toaster(_act, stringMsg);
                            // scheduleDismiss();

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
                    webcallMotherTongueList();
                    //Helper_Method.dismissProgessBar();
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

    private void webcallMotherTongueList() {

        //Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETMotherTongue();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    motherTongueObjectArrayList = new ArrayList<>();
                    motherTongueObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("mother_tongue_list");
                            motherTongueObjectArrayList.add(new MotherTongueObject("0", getResources().getString(R.string.lbl_select_mother_tongue)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    motherTongueObjectArrayList.add(new MotherTongueObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (motherTongueObjectArrayList.size() == 0) {


                            } else {
                                spinnerMotherTonguelist.setTitle(getResources().getString(R.string.lbl_mother_tongue_hint));
                                spinnerMotherTonguelist_Adapter = new ArrayAdapter<MotherTongueObject>(_act, android.R.layout.simple_spinner_item, motherTongueObjectArrayList);
                                spinnerMotherTonguelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerMotherTonguelist.setAdapter(spinnerMotherTonguelist_Adapter);
                                spinnerMotherTonguelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strMotherTongueId = motherTongueObjectArrayList.get(i).id;
                                        strMotherTongue = motherTongueObjectArrayList.get(i).languageName;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME).equals("")) {


                                    for (int k = 0; k < motherTongueObjectArrayList.size(); k++) {
                                        Log.d("Vijendra", "languageName: " + motherTongueObjectArrayList.get(k).languageName);
                                        if (motherTongueObjectArrayList.get(k).languageName.equals
                                                (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.LANGUAGE_NAME))) {
                                            spinnerMotherTonguelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            motherTongueObjectArrayList.clear();
                            spinnerMotherTonguelist.setTitle(getResources().getString(R.string.lbl_mother_tongue_hint));
                            spinnerMotherTonguelist_Adapter = new ArrayAdapter<MotherTongueObject>(_act, android.R.layout.simple_spinner_item, motherTongueObjectArrayList);
                            spinnerMotherTonguelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerMotherTonguelist.setAdapter(spinnerMotherTonguelist_Adapter);
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
                    Log.d("my_tag", "onResponseSachin543534: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("caste_list");
                           // casteArrayList.add(new Caste("0", getResources().getString(R.string.lbl_caste_hint)));
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
                                        webcallSubcasteList(strCasteId);
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "CASTE: " + SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE));
                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE).equals("")) {
                                    spinnerCastelist.setSelection(0);
                                    for (int k = 0; k < casteArrayList.size(); k++) {
                                        Log.d(TAG, "onResponse: 1"+casteArrayList.get(k).getCaste_name());

                                        if (casteArrayList.get(k).getId().equals
                                                (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CASTE))) {
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

                                Log.d("Vijendra", "SUBCASTE: " + SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE));
                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE).equals("")) {

                                    for (int k = 0; k < subcasteObjectArrayList.size(); k++) {
                                        if (subcasteObjectArrayList.get(k).getCasteName().equals
                                                (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.SUBCASTE))) {
                                            spinnerSubcastelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }
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


                                Log.d("Vijendra", "STATE_NAME: " + SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME));
                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME).equals("")) {

                                    for (int k = 0; k < stateObjectArrayList.size(); k++) {
                                        if (stateObjectArrayList.get(k).getName().equals
                                                (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.STATE_NAME))) {
                                            spinnerStatelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }

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

                } finally {
                    PhysicalStatus();
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
                                        strCityName = cityObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "CITY_NAME: " + SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME));
                                if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME) != null &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME).isEmpty() &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME).equals("null") &&
                                        !SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME).equals("")) {

                                    for (int k = 0; k < cityObjectArrayList.size(); k++) {
                                        if (cityObjectArrayList.get(k).getCasteName().equals(SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.CITY_NAME))) {
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

    private void PhysicalStatus() {

        rvPhysicalStatus = findViewById(R.id.rvPhysicalStatus);
        physicalStatusObjArrayList = new ArrayList<>();
        physicalStatusObjArrayList.add(new PhysicalStatusObj("No", "Normal", false));
        physicalStatusObjArrayList.add(new PhysicalStatusObj("Yes", "Physical Challenged", false));

        if (physicalStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            //rvPhysicalStatus.setVisibility(View.GONE);

        } else {


            rvPhysicalStatus.setHasFixedSize(true);
            rvPhysicalStatus.setNestedScrollingEnabled(false);
            // physicalStatusObjArrayList.get(0).setSelected(true);
            // physicalStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            physicalStatusAdapter = new PhysicalStatusAdapter(_act, physicalStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvPhysicalStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerPhysicalStatus = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerPhysicalStatus.setOrientation(RecyclerView.VERTICAL);
            rvPhysicalStatus.setLayoutManager(getGridLayoutManagerPhysicalStatus);
            rvPhysicalStatus.setItemAnimator(new DefaultItemAnimator());
            rvPhysicalStatus.setAdapter(physicalStatusAdapter);
            //profileCreatedByAdapter.setSelected(0);
            physicalStatusAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvPhysicalStatus.setVisibility(View.VISIBLE);

            rvPhysicalStatus.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < physicalStatusObjArrayList.size(); i++) {

                                if (i == position) {
                                    physicalStatusObjArrayList.get(i).setSelected(true);
                                } else {
                                    physicalStatusObjArrayList.get(i).setSelected(false);
                                }
                            }
                            // pos = profileCreatedForAdapter.setSelected(position);
                            physicalStatusAdapter.notifyDataSetChanged();
                            strPhysicalStatus = physicalStatusObjArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strPhysicalStatus);
                            if (strPhysicalStatus.equals("Physical Challenged")) {
                                strPhysicalStatus = "YES";
                            } else {
                                strPhysicalStatus = "NO";
                            }
                            Log.d("TAG", "onItemClick: " + strPhysicalStatus);

                        }
                    })
            );
        }


        for (int i = 0; i < physicalStatusObjArrayList.size(); i++) {
            if (SharedPref.getPrefs(BasicDetailsAddAndEditActivity.this, IConstant.DISABILITY).equals("Yes")) {
                if (physicalStatusObjArrayList.get(i).name.equals("YES")) {
                    physicalStatusObjArrayList.get(i).setSelected(true);
                } else {
                    physicalStatusObjArrayList.get(i).setSelected(false);
                }
            }
        }
    }

    private boolean isValid() {

        if (validations.isBlank(etAddress)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAddress.startAnimation(shake);
            etAddress.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etExpectation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etExpectation.startAnimation(shake);
            etExpectation.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etNativePlace)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etNativePlace.startAnimation(shake);
            etNativePlace.setError(IErrors.EMPTY);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.common_menu_words, menu);
         return super.onCreateOptionsMenu(menu);
     }
 */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
       /*     case R.id.menuApply:
                Intent intent = new Intent(_act, LoginActivity.class);
                startActivityForResult(intent, 700);
                _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //display time picker dialog
    public void timePicker() {
        hour = currentCal.get(Calendar.HOUR_OF_DAY);
        minute = currentCal.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, this, hour, minute, false);
        timePickerDialog.show();
    }

    //display selected time from time picker dialog
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = hourOfDay;
        minute = minute;
        String timeSet = "";
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        String min = "";
        if (minute < 10)
            min = "0" + minute;
        else
            min = String.valueOf(minute);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(min).append(" ").append(timeSet).toString();

        strBirthTime = String.format("%02d:%02d %2s", hour, minute, timeSet);
        //strBirthTime = String.format("%02d:%02d", hour, minute);

        tvBirthTime.setText(strBirthTime);
    }



    public static class Utility {
        public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
            return noOfColumns;
        }
    }
}