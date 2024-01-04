package com.wind.anuroopjodi.InitialActivities.RegistrationSteps;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wind.anuroopjodi.Adapter.SibilingAdapter;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IErrors;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.RecyclerItemClickListener;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.PrefredPartner.PreferredPatnerActivity;
import com.wind.anuroopjodi.Object.BloodGroupObject;
import com.wind.anuroopjodi.Object.CommonCountObject;
import com.wind.anuroopjodi.Object.CommonYesNoObject;
import com.wind.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyDetailsActivity extends BaseActivity implements SibilingAdapter.OnItemClickListener {

    private static final String TAG = "FamilyDetailsActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private int mNoOfColumns = 0;
    private String user_profile_path = null;

    //Siblings
    private ArrayList<CommonYesNoObject> siblingsObjArrayList;
    private RecyclerView rvSiblings;
    private SibilingAdapter drinkingAdapter;
    private GridLayoutManager getGetGridLayoutManagerDrinking;
    private String strSibling = "0";

    private EditText etFatherName, etMotherName, etFatherOccupation, etMotherOccupation,
            etFatherContact, etMotherContact, etUncleNameOne, etUncleOneOccupation, etMaternalUncleName,
            etMaternalUncleOccupation, etMamaKul;
    private EditText etAboutMe, etHobby;
    //Total No of Brothers
    private ArrayList<CommonCountObject> brothersObjectArrayList;
    private Spinner spinnerBother;
    private ArrayAdapter<CommonCountObject> spinnerBother_Adapter;
    private String strBrotherId = "0", strBrother;

    //Total No of Sisters
    private ArrayList<CommonCountObject> sistersObjectArrayList;
    private Spinner spinnerSister;
    private ArrayAdapter<CommonCountObject> spinnerSister_Adapter;
    private String strSisterId = "0", strSister;

    //Total No of Married Brother
    private ArrayList<CommonCountObject> marriedBrotherObjectArrayList;
    private Spinner spinnerMarriedBrother;
    private ArrayAdapter<CommonCountObject> spinnerMarriedBrother_Adapter;
    private String strMarriedBrotherId = "0", strMarriedBrother;

    //Total No of Married Sisters
    private ArrayList<CommonCountObject> marriedSisterObjectArrayList;
    private Spinner spinnerMarriedSister;
    private ArrayAdapter<CommonCountObject> spinnerMarriedSister_Adapter;
    private String strMarriedSisterId = "0", strMarriedSister;

    //Blood Group Spinner
    private ArrayList<BloodGroupObject> livingStyleObjectArrayList;
    private SearchableSpinner spinnerLivingStyle;
    private ArrayAdapter<BloodGroupObject> spinnerLivingStyle_Adapter;
    private String strLivingStyleId = "0", strLivingStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_details);

        _act = FamilyDetailsActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        mNoOfColumns = SignUpActivity.Utility.calculateNoOfColumns(getApplicationContext(), 120);
        Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_family_details_title));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        etAboutMe = findViewById(R.id.etAboutMe);
        etHobby = findViewById(R.id.etHobby);
        etFatherName = findViewById(R.id.etFatherName);
        etMotherName = findViewById(R.id.etMotherName);
        etFatherOccupation = findViewById(R.id.etFatherOccupation);
        etMotherOccupation = findViewById(R.id.etMotherOccupation);
        etFatherContact = findViewById(R.id.etFatherContact);
        etMotherContact = findViewById(R.id.etMotherContact);
        etUncleNameOne = findViewById(R.id.etUncleNameOne);
        etUncleOneOccupation = findViewById(R.id.etUncleOneOccupation);
        etMaternalUncleName = findViewById(R.id.etMaternalUncleName);
        etMaternalUncleOccupation = findViewById(R.id.etMaternalUncleOccupation);
        spinnerSister = findViewById(R.id.spinnerSister);
        spinnerMarriedBrother = findViewById(R.id.spinnerMarriedBrother);
        spinnerMarriedSister = findViewById(R.id.spinnerMarriedSister);

        //Sachin Khare
        etMamaKul = findViewById(R.id.etMamaKul);
        etMamaKul.setText("None");


        findViewById(R.id.btnStepfour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    if (strSibling != null && !strSibling.isEmpty() && !strSibling.equals("null") && !strSibling.equals("")) {
                        if (strBrotherId != null && !strBrotherId.isEmpty() && !strBrotherId.equals("null") && !strBrotherId.equals("0")) {
                            if (strSisterId != null && !strSisterId.isEmpty() && !strSisterId.equals("null") && !strSisterId.equals("0")) {
                                if (strMarriedBrotherId != null && !strMarriedBrotherId.isEmpty() && !strMarriedBrotherId.equals("null") && !strMarriedBrotherId.equals("0")) {
                                    if (strMarriedSisterId != null && !strMarriedSisterId.isEmpty() && !strMarriedSisterId.equals("null") && !strMarriedSisterId.equals("")) {


                                        if (connectionDetector.isConnectionAvailable()) {
                                            webcallFamilyDetails();
                                        } else {
                                            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                        }


                                    } else {
                                        Helper_Method.toaster(_act, "Select Married Sister Count");
                                    }
                                } else {
                                    Helper_Method.toaster(_act, "Select Married Brother Count");
                                }
                            } else {
                                Helper_Method.toaster(_act, "Select Sister Count");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Brother Count");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Select Sibling");
                    }
                } else {
                    Helper_Method.toaster(_act, "Check and fill all the fields");
                }
            }
        });

        if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
            if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                setValuesToUpdate();
            }
        }

        rvSiblings = findViewById(R.id.rvSiblings);
        siblingsObjArrayList = new ArrayList<>();
        LivingStyle();
        Sibling();
    }

    private void setValuesToUpdate() {

        etAboutMe.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.ABOUT_ME));
        etHobby.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.HOBBY));
        etFatherName.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.FATHER_NAME));
        etMotherName.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MOTHER_NAME));
        etFatherOccupation.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.FATHER_OCCUPATION));
        etMotherOccupation.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MOTHER_OCCUPATION));
        etFatherContact.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.FATHER_CONTACT));
        etMotherContact.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MOTHER_CONTACT));
        etUncleNameOne.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.UNCLE_NAME));
        etUncleOneOccupation.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.UNCLE_OCCUPATION));
        etMaternalUncleName.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARITAL_UNCLE_NAME));
        etMaternalUncleOccupation.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARITAL_UNCLE_OCCUPATION));
        etMamaKul.setText(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MAMAKUL));

        strSibling = SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SIBLING);
        strBrotherId = SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER);
        strSisterId = SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER);
        strMarriedBrotherId = SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER);
        strMarriedSisterId = SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER);



        //Log.d(TAG, "siblingsObjArrayList: "+siblingsObjArrayList.get(0).id);
        //Log.d(TAG, "siblingsObjArrayList: "+SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SIBLING));



    }

    public void LivingStyle() {
        livingStyleObjectArrayList = new ArrayList<>();
        livingStyleObjectArrayList.add(new BloodGroupObject("0", "Select Living Style"));
        livingStyleObjectArrayList.add(new BloodGroupObject("1", "Upper Class"));
        livingStyleObjectArrayList.add(new BloodGroupObject("2", "Upper Middle Class"));
        livingStyleObjectArrayList.add(new BloodGroupObject("3", "Middle Class"));
        livingStyleObjectArrayList.add(new BloodGroupObject("4", "Other"));

        spinnerLivingStyle = findViewById(R.id.spinnerLivingStyle);
        spinnerLivingStyle.setTitle(getResources().getString(R.string.lbl_living_style_hint));
        spinnerLivingStyle_Adapter = new ArrayAdapter<BloodGroupObject>(_act, android.R.layout.simple_spinner_item, livingStyleObjectArrayList);
        spinnerLivingStyle_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivingStyle.setAdapter(spinnerLivingStyle_Adapter);
        spinnerLivingStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strLivingStyleId = livingStyleObjectArrayList.get(i).id;
                strLivingStyle = livingStyleObjectArrayList.get(i).name;
                //Toast.makeText(FamilyDetailsActivity.this, ""+strLivingStyle, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.LIVING_STYLE) != null &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.LIVING_STYLE).isEmpty() &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.LIVING_STYLE).equals("null") &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.LIVING_STYLE).equals("")) {
            for (int k = 0; k < livingStyleObjectArrayList.size(); k++) {
                if (livingStyleObjectArrayList.get(k).getName().equals
                        (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.LIVING_STYLE))) {
                    spinnerLivingStyle.setSelection(k);
                }
            }
        } else {

        }

    }

    private void webcallFamilyDetails() {

        Helper_Method.showProgressBar(_act, "Updating Family Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTFamilyDetails(
                etFatherName.getText().toString().trim(),
                etMotherName.getText().toString().trim(),
                etFatherOccupation.getText().toString().trim(),
                etMotherOccupation.getText().toString().trim(),
                etFatherContact.getText().toString().trim(),
                etMotherContact.getText().toString().trim(),
                strSibling,
                strBrother,
                strSister,
                strMarriedBrother,
                strMarriedSister,
                etUncleNameOne.getText().toString().trim(),
                etUncleOneOccupation.getText().toString().trim(),
                etMaternalUncleName.getText().toString().trim(),
                etMaternalUncleOccupation.getText().toString().trim(),
                etHobby.getText().toString().trim(),
                etAboutMe.getText().toString().trim(),
                strLivingStyle,
                etMamaKul.getText().toString().trim(),
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
                            SharedPref.setPrefs(_act, IConstant.MAMAKUL, String.valueOf(jsonObjectData.getString("mama_kul")));
                            SharedPref.setPrefs(_act, IConstant.LIVING_STYLE, String.valueOf(jsonObjectData.getString("living_style")));
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
                            //SharedPref.setPrefs(_act, IConstant.EMAIL_VERIFIED, String.valueOf(jsonObjectData.getString("email_verified")));
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
                            } else {
                                Helper_Method.hideSoftInput(_act);
                                Helper_Method.dismissProgessBar();
                                Helper_Method.toaster(_act, stringMsg);
                                Helper_Method.intentActivity(_act, PreferredPatnerActivity.class, false);
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

    private boolean isValid() {

        if (validations.isBlank(etFatherName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFatherName.startAnimation(shake);
            etFatherName.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etMotherName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMotherName.startAnimation(shake);
            etMotherName.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etFatherOccupation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFatherOccupation.startAnimation(shake);
            etFatherOccupation.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etMotherOccupation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMotherOccupation.startAnimation(shake);
            etMotherOccupation.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etFatherContact)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFatherContact.startAnimation(shake);
            etFatherContact.setError(IErrors.EMPTY);
            return false;
        }
        if (!validations.isValidPhone(etFatherContact)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFatherContact.startAnimation(shake);
            etFatherContact.setError(IErrors.INVALID_PHONE);
            return false;
        }
        if (validations.isBlank(etMotherContact)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMotherContact.startAnimation(shake);
            etMotherContact.setError(IErrors.EMPTY);
            return false;
        }
        if (!validations.isValidPhone(etMotherContact)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMotherContact.startAnimation(shake);
            etMotherContact.setError(IErrors.INVALID_PHONE);
            return false;
        }

        if (validations.isBlank(etUncleNameOne)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etUncleNameOne.startAnimation(shake);
            etUncleNameOne.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etUncleOneOccupation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etUncleOneOccupation.startAnimation(shake);
            etUncleOneOccupation.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etMaternalUncleName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMaternalUncleName.startAnimation(shake);
            etMaternalUncleName.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etMaternalUncleOccupation)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMaternalUncleOccupation.startAnimation(shake);
            etMaternalUncleOccupation.setError(IErrors.EMPTY);
            return false;
        }
        /*if (validations.isBlank(etMamaKul)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMamaKul.startAnimation(shake);
            etMamaKul.setError(IErrors.EMPTY);
            return false;
        }*/
        if (validations.isBlank(etAboutMe)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etAboutMe.startAnimation(shake);
            etAboutMe.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etHobby)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etHobby.startAnimation(shake);
            etHobby.setError(IErrors.EMPTY);
            return false;
        }

        return true;
    }

    private void Sibling() {
        
        siblingsObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        siblingsObjArrayList.add(new CommonYesNoObject("0", "No", true));

        if(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SIBLING) != null) {
            for (int i = 0; i < siblingsObjArrayList.size(); i++) {

                Log.d(TAG, "siblingsObjArrayList: " + siblingsObjArrayList.get(i).id);
                Log.d(TAG, "siblingsObjArrayList: " + SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SIBLING));

                if (siblingsObjArrayList.get(i).id.equals(SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SIBLING))) {
                    siblingsObjArrayList.get(i).setSelected(true);
                } else {
                    siblingsObjArrayList.get(i).setSelected(false);
                }
            }
        }

        if (siblingsObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvSiblings.setVisibility(View.GONE);

        } else {


            rvSiblings.setHasFixedSize(true);
            rvSiblings.setNestedScrollingEnabled(false);
            // siblingsObjArrayList.get(0).setSelected(true);
            // siblingsObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            drinkingAdapter = new SibilingAdapter(_act, siblingsObjArrayList,
                    FamilyDetailsActivity.this);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvSiblings.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGetGridLayoutManagerDrinking = new GridLayoutManager(_act, mNoOfColumns);
            getGetGridLayoutManagerDrinking.setOrientation(RecyclerView.VERTICAL);
            rvSiblings.setLayoutManager(getGetGridLayoutManagerDrinking);
            rvSiblings.setItemAnimator(new DefaultItemAnimator());
            rvSiblings.setAdapter(drinkingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            drinkingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvSiblings.setVisibility(View.VISIBLE);

            rvSiblings.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < siblingsObjArrayList.size(); i++) {

                                if (i == position) {
                                    siblingsObjArrayList.get(i).setSelected(true);
                                } else {
                                    siblingsObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            drinkingAdapter.notifyDataSetChanged();
                            strSibling = siblingsObjArrayList.get(position).id;
                            Log.d("TAG", "onItemClick: " + strSibling);

                        }
                    })
            );
        }
        Brothers();
    }

    public void Brothers() {
        brothersObjectArrayList = new ArrayList<>();
        brothersObjectArrayList.add(new CommonCountObject("0", "Select Brother(s)", false));
        brothersObjectArrayList.add(new CommonCountObject("1", "0", false));
        brothersObjectArrayList.add(new CommonCountObject("2", "1", false));
        brothersObjectArrayList.add(new CommonCountObject("3", "2", false));
        brothersObjectArrayList.add(new CommonCountObject("4", "3", false));
        brothersObjectArrayList.add(new CommonCountObject("5", "4", false));
        brothersObjectArrayList.add(new CommonCountObject("6", "5", false));
        brothersObjectArrayList.add(new CommonCountObject("7", "6", false));
        brothersObjectArrayList.add(new CommonCountObject("8", "7", false));
        spinnerBother = findViewById(R.id.spinnerBother);
        spinnerBother_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, brothersObjectArrayList);
        spinnerBother_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBother.setAdapter(spinnerBother_Adapter);
        spinnerBother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strBrotherId = brothersObjectArrayList.get(i).id;
                strBrother = brothersObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        Log.d(TAG, "Brothers: "+SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER));

        if (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER) != null &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER).isEmpty() &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER).equals("null") &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER).equals("")) {

            for (int k = 0; k < brothersObjectArrayList.size(); k++) {
                if (brothersObjectArrayList.get(k).getCount().equals(
                        SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.BROTHER))) {
                    spinnerBother.setSelection(k);
                }
            }

        } else {

        }


        Sisters();
    }

    public void Sisters() {
        sistersObjectArrayList = new ArrayList<>();
        sistersObjectArrayList.add(new CommonCountObject("0", "Select Sister(s)", false));
        sistersObjectArrayList.add(new CommonCountObject("1", "0", false));
        sistersObjectArrayList.add(new CommonCountObject("2", "1", false));
        sistersObjectArrayList.add(new CommonCountObject("3", "2", false));
        sistersObjectArrayList.add(new CommonCountObject("4", "3", false));
        sistersObjectArrayList.add(new CommonCountObject("5", "4", false));
        sistersObjectArrayList.add(new CommonCountObject("6", "5", false));
        sistersObjectArrayList.add(new CommonCountObject("7", "6", false));
        sistersObjectArrayList.add(new CommonCountObject("8", "7", false));
        spinnerSister = findViewById(R.id.spinnerSister);
        spinnerSister_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, sistersObjectArrayList);
        spinnerSister_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSister.setAdapter(spinnerSister_Adapter);
        spinnerSister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strSisterId = sistersObjectArrayList.get(i).id;
                strSister = sistersObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER) != null &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER).isEmpty() &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER).equals("null") &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER).equals("")) {

            for (int k = 0; k < sistersObjectArrayList.size(); k++) {
                if (sistersObjectArrayList.get(k).getCount().equals(
                        SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.SISTER))) {
                    spinnerSister.setSelection(k);
                }
            }

        } else {

        }

        MarriedBrother();
    }

    public void MarriedBrother() {
        marriedBrotherObjectArrayList = new ArrayList<>();
        marriedBrotherObjectArrayList.add(new CommonCountObject("0", "Select Married Brother(s)", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("1", "0", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("2", "1", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("3", "2", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("4", "3", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("5", "4", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("6", "5", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("7", "6", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("8", "7", false));
        marriedBrotherObjectArrayList.add(new CommonCountObject("9", "8", false));
        spinnerMarriedBrother = findViewById(R.id.spinnerMarriedBrother);
        spinnerMarriedBrother_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, marriedBrotherObjectArrayList);
        spinnerMarriedBrother_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarriedBrother.setAdapter(spinnerMarriedBrother_Adapter);
        spinnerMarriedBrother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strMarriedBrotherId = marriedBrotherObjectArrayList.get(i).id;
                strMarriedBrother = marriedBrotherObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER) != null &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER).isEmpty() &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER).equals("null") &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER).equals("")) {

            for (int k = 0; k < marriedBrotherObjectArrayList.size(); k++) {
                if (marriedBrotherObjectArrayList.get(k).getCount().equals(
                        SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_BROTHER))) {
                    spinnerMarriedBrother.setSelection(k);
                }
            }

        } else {

        }

        MarriedSister();
    }

    public void MarriedSister() {
        marriedSisterObjectArrayList = new ArrayList<>();
        marriedSisterObjectArrayList.add(new CommonCountObject("0", "Select Married Sister(s)", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("1", "0", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("2", "1", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("3", "2", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("4", "3", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("5", "4", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("6", "5", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("7", "6", false));
        marriedSisterObjectArrayList.add(new CommonCountObject("8", "7", false));
        spinnerMarriedSister = findViewById(R.id.spinnerMarriedSister);
        spinnerMarriedSister_Adapter = new ArrayAdapter<CommonCountObject>(_act, android.R.layout.simple_spinner_item, marriedSisterObjectArrayList);
        spinnerMarriedSister_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarriedSister.setAdapter(spinnerMarriedSister_Adapter);
        spinnerMarriedSister.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strMarriedSisterId = marriedSisterObjectArrayList.get(i).id;
                strMarriedSister = marriedSisterObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER) != null &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER).isEmpty() &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER).equals("null") &&
                !SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER).equals("")) {

            for (int k = 0; k < marriedSisterObjectArrayList.size(); k++) {
                if (marriedSisterObjectArrayList.get(k).getCount().equals(
                        SharedPref.getPrefs(FamilyDetailsActivity.this, IConstant.MARRIED_SISTER))) {
                    spinnerMarriedSister.setSelection(k);
                }
            }

        } else {

        }

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
    public void onItemClickForAdminTemplate(int id) {
        if(id == 0) {
            spinnerBother.setEnabled(true);
            spinnerSister.setEnabled(true);
            spinnerMarriedBrother.setEnabled(true);
            spinnerMarriedSister.setEnabled(true);
        }else{
            spinnerBother.setEnabled(false);
            spinnerSister.setEnabled(false);
            spinnerMarriedBrother.setEnabled(false);
            spinnerMarriedSister.setEnabled(false);
        }
    }
}