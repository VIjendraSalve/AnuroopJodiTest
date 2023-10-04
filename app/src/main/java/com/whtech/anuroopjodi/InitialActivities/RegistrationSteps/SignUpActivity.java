package com.whtech.anuroopjodi.InitialActivities.RegistrationSteps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.whtech.anuroopjodi.Adapter.GenderAdapter;
import com.whtech.anuroopjodi.Adapter.MaritalStatusAdapter;
import com.whtech.anuroopjodi.Adapter.ProfileCreatedByAdapter;
import com.whtech.anuroopjodi.Adapter.ProfileCreatedForAdapter;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IErrors;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.RecyclerItemClickListener;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.InitialActivities.LoginActivity;
import com.whtech.anuroopjodi.Object.GenderObj;
import com.whtech.anuroopjodi.Object.MaritalStatusObj;
import com.whtech.anuroopjodi.Object.ProfileCreatedByObj;
import com.whtech.anuroopjodi.Object.ProfileCreatedForObj;
import com.whtech.anuroopjodi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = "SignUp";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    public static final int REQUEST_CODE = 1;

    //Profile Created By Types
    private ArrayList<ProfileCreatedByObj> profileCreatedByObjArrayList;
    private RecyclerView rvCreatedBy;
    private ProfileCreatedByAdapter profileCreatedByAdapter;
    private GridLayoutManager gridLayoutManager;
    private String strProfileCreatedById = null;


    //Profile Created By Types
    private ArrayList<ProfileCreatedForObj> profileCreatedForObjArrayList;
    private RecyclerView rvCreatedFor;
    private ProfileCreatedForAdapter profileCreatedForAdapter;
    private GridLayoutManager getGridLayoutManagerProfileCreatedFor;
    private String strProfileCreatedForId = null, stringUserToken="";


    //Gender Types
    private ArrayList<GenderObj> genderObjArrayList;
    private RecyclerView rvGender;
    private GenderAdapter genderAdapter;
    private GridLayoutManager getGridLayoutManagerGender;
    private String strGender = null;

    //Marital Status Types
    private ArrayList<MaritalStatusObj> maritalStatusObjArrayList;
    private RecyclerView rvMaritalStatus;
    private MaritalStatusAdapter maritalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerMaritalStatus;
    private String strMaritalStatus = null;

    int mNoOfColumns = 0;

    private EditText etFirstName, etMiddleName, etLastName, etEmail, etMobile, etPassword, etRelativeRelation;
    private TextView tvShowPassword, tvHidePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_new);

        _act = SignUpActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        mNoOfColumns = Utility.calculateNoOfColumns(getApplicationContext(), 120);
        Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);

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
                        Log.d("Notification_Token1", "onComplete: " + token);
                        stringUserToken = token;

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d(TAG, msg);
                        // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(getResources().getString(R.string.lbl_create_account));
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));

        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        String first = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_one) + "</font>";
        String second = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_terms_n_cond_two) + "</font>";
        String third = "<font color='#000000'>" + getResources().getString(R.string.lbl_and) + "</font>";
        String fourth = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_privacy_policy) + "</font>";
        String fifth = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_three) + "</font>";
        tvTermsAndConditions.setText(Html.fromHtml(first + second + third + fourth + fifth));


        Init();


        tvTermsAndConditions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Helper_Method.toaster(_act, "Coming Soon!!!");
                return true;
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strProfileCreatedById != null && !strProfileCreatedById.isEmpty() && !strProfileCreatedById.equals("null")) {
                    if (strProfileCreatedForId != null && !strProfileCreatedForId.isEmpty() && !strProfileCreatedForId.equals("null")) {
                        if (strGender != null && !strGender.isEmpty() && !strGender.equals("null")) {
                            if (strMaritalStatus != null && !strMaritalStatus.isEmpty() && !strMaritalStatus.equals("null")) {
                                if (isValid()) {
                                    if (connectionDetector.isConnectionAvailable()) {
                                        webcallRegistration();
                                    } else {
                                        Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                    }
                                }
                            } else {
                                Helper_Method.toaster(_act, "Select Marital Status");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Gender");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Select Profile Created for");
                    }
                } else {
                    Helper_Method.toaster(_act, "Select Created By");
                }
            }
        });


    }


    private void webcallRegistration() {

        if (!_act.isFinishing()) {
            Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_sign_up_loading));
        } else {

        }

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTRegistration(
                etFirstName.getText().toString().trim(),
                etMiddleName.getText().toString().trim(),
                etLastName.getText().toString().trim(),
                strProfileCreatedForId,
                etEmail.getText().toString().trim(),
                etMobile.getText().toString().trim(),
                strGender,
                strMaritalStatus,
                etPassword.getText().toString().trim(),
                "A",
                stringUserToken,
                etRelativeRelation.getText().toString()
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
                        String stringCode = i.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase("true")) {
                            String registration_number = i.getString("registration_number");
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();

                            Intent intent = new Intent(_act, BothOtpActivity.class);
                            intent.putExtra("Activity", "Reg");
                            intent.putExtra("mobile", etMobile.getText().toString().trim());
                            intent.putExtra("email", etEmail.getText().toString().trim());
                            intent.putExtra("profile_id", registration_number);
                            //overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                            startActivityForResult(intent, REQUEST_CODE);

                        } else {
                            Helper_Method.toaster_long(_act, stringMsg);
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
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private boolean isValid() {

        if (validations.isBlank(etFirstName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etFirstName.startAnimation(shake);
            etFirstName.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etLastName)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etLastName.startAnimation(shake);
            etLastName.setError(IErrors.EMPTY);
            return false;
        }

        if (validations.isBlank(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.EMPTY);
            return false;
        }

        if (!validations.isValidEmail(etEmail)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etEmail.startAnimation(shake);
            etEmail.setError(IErrors.INVALID_EMAIL);
            return false;
        }

        if (validations.isBlank(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(IErrors.EMPTY);
            return false;
        }
        if (!validations.isValidPhone(etMobile)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(IErrors.INVALID_PHONE);
            return false;
        }

        if (validations.isBlank(etPassword)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etMobile.startAnimation(shake);
            etMobile.setError(IErrors.EMPTY);
            return false;
        }
        return true;
    }

    private void Init() {

        etFirstName = findViewById(R.id.etFirstName);
        etRelativeRelation = findViewById(R.id.etRelativeRelation);
        etMiddleName = findViewById(R.id.etMiddleName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPassword);
        tvShowPassword = findViewById(R.id.tvShowPassword);
        tvHidePassword = findViewById(R.id.tvHidePassword);


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

        //Static(local) Data
        ProfileCreateBy();
        ProfileCreateFor();
        Gender();
        MaritalStatus();

        //Default Webcall
      /*  if (connectionDetector.isConnectionAvailable()) {
            webcallStateList();
        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));

        }*/


    }

    public static class Utility {
        public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
            return noOfColumns;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu_words, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuApply:
                Intent intent = new Intent(_act, LoginActivity.class);
                startActivityForResult(intent, 700);
                _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ProfileCreateBy() {
        rvCreatedBy = findViewById(R.id.rvCreatedBy);
        profileCreatedByObjArrayList = new ArrayList<>();
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("1", "Self", false));
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("2", "Parent", false));
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("3", "Sibling", false));
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("4", "Relative", false));
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("5", "Friend", false));
        profileCreatedByObjArrayList.add(new ProfileCreatedByObj("6", "Other", false));

        if (profileCreatedByObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvCreatedBy.setVisibility(View.GONE);

        } else {


            rvCreatedBy.setHasFixedSize(true);
            rvCreatedBy.setNestedScrollingEnabled(false);
            // profileCreatedByObjArrayList.get(0).setSelected(true);
            // profileCreatedByObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            profileCreatedByAdapter = new ProfileCreatedByAdapter(_act, profileCreatedByObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvCreatedBy.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            gridLayoutManager = new GridLayoutManager(_act, mNoOfColumns);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
            rvCreatedBy.setLayoutManager(gridLayoutManager);
            rvCreatedBy.setItemAnimator(new DefaultItemAnimator());
            rvCreatedBy.setAdapter(profileCreatedByAdapter);
            //profileCreatedByAdapter.setSelected(0);
            profileCreatedByAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvCreatedBy.setVisibility(View.VISIBLE);
        }

        rvCreatedBy.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        for (int i = 0; i < profileCreatedByObjArrayList.size(); i++) {

                            if (i == position) {
                                profileCreatedByObjArrayList.get(i).setSelected(true);
                            } else {
                                profileCreatedByObjArrayList.get(i).setSelected(false);
                            }
                        }

                        // pos = profileCreatedByAdapter.setSelected(position);
                        profileCreatedByAdapter.notifyDataSetChanged();
                        strProfileCreatedById = profileCreatedByObjArrayList.get(position).id;
                        Log.d(TAG, "onItemClick: " + strProfileCreatedById);

                    }
                })
        );

    }

    public void ProfileCreateFor() {
        rvCreatedFor = findViewById(R.id.rvCreatedFor);
        profileCreatedForObjArrayList = new ArrayList<>();
        profileCreatedForObjArrayList.add(new ProfileCreatedForObj("1", "Self", false));
        //profileCreatedForObjArrayList.add(new ProfileCreatedForObj("2", "Parent", false));
        profileCreatedForObjArrayList.add(new ProfileCreatedForObj("2", "Son", false));
        profileCreatedForObjArrayList.add(new ProfileCreatedForObj("3", "Daughter", false));
        profileCreatedForObjArrayList.add(new ProfileCreatedForObj("4", "Brother", false));
        profileCreatedForObjArrayList.add(new ProfileCreatedForObj("5", "Sister", false));
        // profileCreatedForObjArrayList.add(new ProfileCreatedForObj("6", "Relative", false));
        //  profileCreatedForObjArrayList.add(new ProfileCreatedForObj("7", "Friend", false));

        if (profileCreatedForObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvCreatedFor.setVisibility(View.GONE);

        } else {


            rvCreatedFor.setHasFixedSize(true);
            rvCreatedFor.setNestedScrollingEnabled(false);
            profileCreatedForAdapter = new ProfileCreatedForAdapter(_act, profileCreatedForObjArrayList);
            getGridLayoutManagerProfileCreatedFor = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerProfileCreatedFor.setOrientation(RecyclerView.VERTICAL);
            rvCreatedFor.setLayoutManager(getGridLayoutManagerProfileCreatedFor);
            rvCreatedFor.setItemAnimator(new DefaultItemAnimator());
            rvCreatedFor.setAdapter(profileCreatedForAdapter);
            profileCreatedForAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvCreatedFor.setVisibility(View.VISIBLE);
        }

        rvCreatedFor.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        for (int i = 0; i < profileCreatedForObjArrayList.size(); i++) {

                            if (i == position) {
                                profileCreatedForObjArrayList.get(i).setSelected(true);
                            } else {
                                profileCreatedForObjArrayList.get(i).setSelected(false);
                            }
                        }

                        // pos = profileCreatedForAdapter.setSelected(position);
                        profileCreatedForAdapter.notifyDataSetChanged();
                        strProfileCreatedForId = profileCreatedForObjArrayList.get(position).name;
                        Log.d(TAG, "onItemClick: " + strProfileCreatedForId);

                    }
                })
        );

    }

    private void Gender() {
        rvGender = findViewById(R.id.rvGender);
        genderObjArrayList = new ArrayList<>();
        genderObjArrayList.add(new GenderObj("1", "Male", false));
        genderObjArrayList.add(new GenderObj("2", "Female", false));

        if (genderObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvGender.setVisibility(View.GONE);

        } else {


            rvGender.setHasFixedSize(true);
            rvGender.setNestedScrollingEnabled(false);
            // genderObjArrayList.get(0).setSelected(true);
            // genderObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            genderAdapter = new GenderAdapter(_act, genderObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvGender.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerGender = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerGender.setOrientation(RecyclerView.VERTICAL);
            rvGender.setLayoutManager(getGridLayoutManagerGender);
            rvGender.setItemAnimator(new DefaultItemAnimator());
            rvGender.setAdapter(genderAdapter);
            //profileCreatedByAdapter.setSelected(0);
            genderAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvGender.setVisibility(View.VISIBLE);

            rvGender.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < genderObjArrayList.size(); i++) {

                                if (i == position) {
                                    genderObjArrayList.get(i).setSelected(true);
                                } else {
                                    genderObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            genderAdapter.notifyDataSetChanged();
                            strGender = genderObjArrayList.get(position).name;
                            Log.d(TAG, "onItemClick: " + strGender);

                        }
                    })
            );
        }
    }

    private void MaritalStatus() {

        rvMaritalStatus = findViewById(R.id.rvMaritalStatus);
        maritalStatusObjArrayList = new ArrayList<>();
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
            maritalStatusAdapter = new MaritalStatusAdapter(_act, maritalStatusObjArrayList);
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

            rvMaritalStatus.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {

                                if (i == position) {
                                    maritalStatusObjArrayList.get(i).setSelected(true);
                                } else {
                                    maritalStatusObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            maritalStatusAdapter.notifyDataSetChanged();
                            strMaritalStatus = maritalStatusObjArrayList.get(position).id;
                            Log.d(TAG, "onItemClick: " + strMaritalStatus);

                        }
                    })
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

                //String requiredValue = data.getStringExtra("key");
                Helper_Method.intentActivity(_act, LoginActivity.class, true);
            }
        } catch (Exception ex) {
            Toast.makeText(_act, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }


}