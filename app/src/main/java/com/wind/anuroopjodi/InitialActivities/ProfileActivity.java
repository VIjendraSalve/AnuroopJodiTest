package com.wind.anuroopjodi.InitialActivities;

import static android.os.Build.VERSION.SDK_INT;
import static android.view.View.GONE;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wind.anuroopjodi.Adapter.GenderAdapter;
import com.wind.anuroopjodi.Adapter.PhotosAdapter;
import com.wind.anuroopjodi.Adapter.ProfilePackageInfoAdapter;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.PermissionManager;
import com.wind.anuroopjodi.Helper.RecyclerItemClickListener;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.BasicDetailsAddAndEditActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.FamilyDetailsActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.PersonalDetailsActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.ProfessionalDetailsActivity;
import com.wind.anuroopjodi.InsideActivities.Vijendra.FullPhotoZoomActivity;
import com.wind.anuroopjodi.Object.GenderObj;
import com.wind.anuroopjodi.Object.PhotoObjects;
import com.wind.anuroopjodi.Object.ProfilePackageInfor;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.Camera;
import com.wind.anuroopjodi.my_library.CircleImageView;
import com.wind.anuroopjodi.my_library.Constants;
import com.wind.anuroopjodi.my_library.DateTimeFormat;
import com.wind.anuroopjodi.my_library.MyConfig;
import com.wind.anuroopjodi.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class ProfileActivity extends BaseActivity implements Camera.AsyncResponse {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    private ImageView ivProfileImage;
    private TextView tvFullName, tvAboutUs, tvMoreInformation;

    //Basic Details
    private TextView tvFirstName, tvLastName, tvDobName;
    private TextView tvBirthtime, tvBirthPlace, tvEmail;
    private TextView tvMobile, tvGender, tvMaritalStatus;
    private TextView tvCity, tvState, tvCountry;

    //Life Style
    private TextView tvDiet, tvSmoking, tvDrinking, tvHobby, tvExpectation;

    //Other Information
    private TextView tvHeight, tvCaste, tvGotra, tvGan, tvNakshatra, tvMotherTougue;
    private TextView tvCharan, tvManglik, tvNadi, tvRashi;

    //Family information
    private TextView tvFatherName, tvFatherOccupaition, tvFatherContactNo, tvMotherName, tvMotherOccupaition, tvMotherContactNo, tvSibling;
    private TextView tvBrotherCount, tvSisterCount, tvMarriedSister, tvMarriedBrothers, tvUncleName, tvUncleOccupation, tvMaternalUncleName;
    private TextView tvMaternalUncleOccupation;

    //Education and Jobs
    private TextView tvEducation, tvPrimaryEducationLanguage, tvOccupation, tvCompany, tvDesignation, tvIncome, tvOtherIncome;

    private ImageView ivEditBasicDetails, ivEditOtherInformation, ivEditFamilyInformation,
            ivEditEducationAndJob, ivEditProfilePhoto, ivHoroscopeImage, ivEditName;
    private ProgressDialog progressDialog;
    private String profile_image_name = "", profile_image_path = "";
    private Button btn_update_profile;
    private TextView btnUpdatePackage;
    private Camera camera;
    private CircleImageView ivAdharImg1, ivAdharImg2;

    private ArrayList<PhotoObjects> photoObjectsArrayList = new ArrayList<>();
    private PhotosAdapter photosAdapter;
    private String profile_path = "";
    private RecyclerView rcv_photos, rv_pacakge;

    private CircleImageView civ_add_photo;
    private TextView btn_add_photo;
    private ImageView iv_view_aadhar_image, iv_view_aadhar_image2, iv_view_horoscope_image;
    private TextView tvTotalMobileViews, tvTotalEmailViews, tvUsedMobileViews, tvUsedEmailViews;
    private GridLayoutManager gridLayoutManager;

    private ProfilePackageInfoAdapter homeAdapter;
    private ArrayList<ProfilePackageInfor> profilePackageInforArrayList;

    private TextView tvMiddleName, tvNativePlace, tvCurrentWorkingCity, tvIntercast, tvDevak, tvMamakul, tvLivingStyle;
    private Dialog dialog;
    private EditText etFirstName, etMiddleName, etLastName;
    private ImageView ivDailogClose;
    private TextView btnSignUp;
    private TextView updateProfile, tv_upload;

    //Gender Types
    private ArrayList<GenderObj> genderObjArrayList;
    private RecyclerView rvGender;
    private GenderAdapter genderAdapter;
    private GridLayoutManager getGridLayoutManagerGender;
    private String strGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        _act = ProfileActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("" + getResources().getString(R.string.updating_profile_dialog_message));
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile ");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        init();

    }

    private void init() {

        tvFullName = findViewById(R.id.tvFullName);
        tvAboutUs = findViewById(R.id.tvAboutUs);
        tvMoreInformation = findViewById(R.id.tvMoreInformation);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        iv_view_aadhar_image = findViewById(R.id.iv_view_aadhar_image);
        iv_view_aadhar_image2 = findViewById(R.id.iv_view_aadhar_image2);
        iv_view_horoscope_image = findViewById(R.id.iv_view_horoscope_image);
        updateProfile = findViewById(R.id.updateProfile);

//        tvTotalMobileViews = findViewById(R.id.tvTotalMobileViews);
//        tvTotalEmailViews = findViewById(R.id.tvTotalEmailViews);
//        tvUsedMobileViews = findViewById(R.id.tvUsedMobileViews);
//        tvUsedEmailViews = findViewById(R.id.tvUsedEmailViews);

//        tvTotalMobileViews.setText(Shared_Preferences.getPrefs(ProfileActivity.this, Constants.TotalMobileViews));
//        tvTotalEmailViews.setText(Shared_Preferences.getPrefs(ProfileActivity.this, Constants.TotalEmailViews));
//        tvUsedMobileViews.setText(Shared_Preferences.getPrefs(ProfileActivity.this, Constants.UsedMobileViews));
//        tvUsedEmailViews.setText(Shared_Preferences.getPrefs(ProfileActivity.this, Constants.UsedEmailViews));

        civ_add_photo = findViewById(R.id.civ_add_photo);
        btn_add_photo = findViewById(R.id.btn_add_photo);

        tvMiddleName = findViewById(R.id.tvMiddleName);
        tvNativePlace = findViewById(R.id.tvNativePlace);
        tvCurrentWorkingCity = findViewById(R.id.tvCurrentWorkingCity);
        tvIntercast = findViewById(R.id.tvIntercast);
        tvDevak = findViewById(R.id.tvDevak);
        tvMamakul = findViewById(R.id.tvMamakul);
        tvLivingStyle = findViewById(R.id.tvLivingStyle);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage,
                        SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO));
                startActivity(intent);
            }
        });


        tvAboutUs.setText(SharedPref.getPrefs(_act, IConstant.ABOUT_ME));

        tvFullName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));

        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.d("Android", "Android ID : " + android_id);

        tvMoreInformation.setText(SharedPref.getPrefs(_act, IConstant.HEIGHT_NAME) + " | " + SharedPref.getPrefs(_act, IConstant.WEIGHT) + "(in kg) | " + SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME) + " | "
                /*+ SharedPref.getPrefs(_act, IConstant.CITY_NAME) + ", "*/ + SharedPref.getPrefs(_act, IConstant.STATE_NAME) + ", india | " + SharedPref.getPrefs(_act, IConstant.OCCUPATION_NAME));

        Glide.with(_act)
                .load(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO))
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(ivProfileImage);

        //Basic Details
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
        ivEditProfilePhoto = findViewById(R.id.ivEditProfilePhoto);
        ivEditBasicDetails = findViewById(R.id.ivEditBasicDetails);
        ivEditName = findViewById(R.id.ivEditName);
        ivEditOtherInformation = findViewById(R.id.ivEditOtherInformation);
        ivEditFamilyInformation = findViewById(R.id.ivEditFamilyInformation);
        ivEditEducationAndJob = findViewById(R.id.ivEditEducationAndJob);
        ivEditProfilePhoto = findViewById(R.id.ivEditProfilePhoto);
        ivAdharImg1 = (CircleImageView) findViewById(R.id.ivAdharImg1);
        ivAdharImg2 = (CircleImageView) findViewById(R.id.ivAdharImg2);
        ivHoroscopeImage = (ImageView) findViewById(R.id.ivHoroscopeImage);

        tvFirstName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME));
        tvLastName.setText(SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
        tvDobName.setText(DateTimeFormat.getDate2(SharedPref.getPrefs(_act, IConstant.BIRTH_DATE)));
        tvBirthtime.setText(SharedPref.getPrefs(_act, IConstant.BIRTH_TIME));
        tvBirthPlace.setText(SharedPref.getPrefs(_act, IConstant.BIRTH_PLACE));
        tvEmail.setText(SharedPref.getPrefs(_act, IConstant.USER_EMAIL));
        tvMobile.setText(SharedPref.getPrefs(_act, IConstant.USER_MOBILE));
        tvGender.setText(SharedPref.getPrefs(_act, IConstant.USER_GENDER));
        tvMaritalStatus.setText(SharedPref.getPrefs(_act, IConstant.USER_MARITAL_STATUS));
        tvCity.setText(SharedPref.getPrefs(_act, IConstant.CITY_NAME));
        tvState.setText(SharedPref.getPrefs(_act, IConstant.STATE_NAME));
        tvCountry.setText("India");

        tvMiddleName.setText(SharedPref.getPrefs(_act, IConstant.USER_MIDDLE_NAME));
        tvNativePlace.setText(SharedPref.getPrefs(_act, IConstant.NATIVE_PLACE));
        Log.d("Hche", "init: "+SharedPref.getPrefs(_act, IConstant.WORKING_CITY));
        tvCurrentWorkingCity.setText(SharedPref.getPrefs(_act, IConstant.WORKING_CITY));
        tvIntercast.setText(SharedPref.getPrefs(_act, IConstant.INTERCASTE));
        tvDevak.setText(SharedPref.getPrefs(_act, IConstant.DEVAK));
        tvMamakul.setText(SharedPref.getPrefs(_act, IConstant.MAMAKUL));
        tvLivingStyle.setText(SharedPref.getPrefs(_act, IConstant.LIVING_STYLE));

        //Life Style
        tvDiet = findViewById(R.id.tvDiet);
        tvSmoking = findViewById(R.id.tvSmoking);
        tvDrinking = findViewById(R.id.tvDrinking);
        tvExpectation = findViewById(R.id.tvExpectation);
        tvHobby = findViewById(R.id.tvHobby);

        tvDiet.setText(SharedPref.getPrefs(_act, IConstant.LIFE_STYLE));
        tvSmoking.setText(SharedPref.getPrefs(_act, IConstant.SMOKING));
        tvDrinking.setText(SharedPref.getPrefs(_act, IConstant.DRINKING));
        tvExpectation.setText(SharedPref.getPrefs(_act, IConstant.USER_EXPECTATION));
        tvHobby.setText(SharedPref.getPrefs(_act, IConstant.HOBBY));


        //Other Information
        tvHeight = findViewById(R.id.tvHeight);
        tvMotherTougue = findViewById(R.id.tvMotherTougue);
        tvCaste = findViewById(R.id.tvCaste);
        tvGotra = findViewById(R.id.tvGotra);
        tvGan = findViewById(R.id.tvGan);
        tvNakshatra = findViewById(R.id.tvNakshatra);
        tvCharan = findViewById(R.id.tvCharan);
        tvManglik = findViewById(R.id.tvManglik);
        tvNadi = findViewById(R.id.tvNadi);
        tvRashi = findViewById(R.id.tvRashi);

        tvHeight.setText(SharedPref.getPrefs(_act, IConstant.HEIGHT_NAME));
        tvMotherTougue.setText(SharedPref.getPrefs(_act, IConstant.LANGUAGE_NAME));

        tvCaste.setText(SharedPref.getPrefs(_act, IConstant.CASTE_NAME));
        tvGotra.setText(SharedPref.getPrefs(_act, IConstant.GOTRA));
        tvGan.setText(SharedPref.getPrefs(_act, IConstant.GAN));
        tvNakshatra.setText(SharedPref.getPrefs(_act, IConstant.NAKSHATRA));
        tvCharan.setText(SharedPref.getPrefs(_act, IConstant.CHARAN));
        tvManglik.setText(SharedPref.getPrefs(_act, IConstant.MANGLIK));
        tvNadi.setText(SharedPref.getPrefs(_act, IConstant.NADI));
        tvRashi.setText(SharedPref.getPrefs(_act, IConstant.RASHI));

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

        tvFatherName.setText(SharedPref.getPrefs(_act, IConstant.FATHER_NAME));
        tvFatherOccupaition.setText(SharedPref.getPrefs(_act, IConstant.FATHER_OCCUPATION));
        tvFatherContactNo.setText(SharedPref.getPrefs(_act, IConstant.FATHER_CONTACT));
        tvMotherName.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_NAME));
        tvMotherOccupaition.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_OCCUPATION));
        tvMotherContactNo.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_CONTACT));
        if (SharedPref.getPrefs(_act, IConstant.SIBLING).equals("1")) {
            tvSibling.setText("Yes");
        } else {
            tvSibling.setText("No");
        }
        tvBrotherCount.setText(SharedPref.getPrefs(_act, IConstant.BROTHER));
        tvSisterCount.setText(SharedPref.getPrefs(_act, IConstant.SISTER));
        tvMarriedSister.setText(SharedPref.getPrefs(_act, IConstant.MARRIED_SISTER));
        tvMarriedBrothers.setText(SharedPref.getPrefs(_act, IConstant.MARRIED_BROTHER));
        tvUncleName.setText(SharedPref.getPrefs(_act, IConstant.UNCLE_NAME));
        tvUncleOccupation.setText(SharedPref.getPrefs(_act, IConstant.UNCLE_OCCUPATION));
        tvMaternalUncleName.setText(SharedPref.getPrefs(_act, IConstant.MARITAL_UNCLE_NAME));
        tvMaternalUncleOccupation.setText(SharedPref.getPrefs(_act, IConstant.MARITAL_UNCLE_OCCUPATION));

        //Education And Job
        tvEducation = findViewById(R.id.tvEducation);
        tvPrimaryEducationLanguage = findViewById(R.id.tvPrimaryEducationLanguage);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvCompany = findViewById(R.id.tvCompany);
        tvDesignation = findViewById(R.id.tvDesignation);
        tvIncome = findViewById(R.id.tvIncome);
        tvOtherIncome = findViewById(R.id.tvOtherIncome);
        rcv_photos = findViewById(R.id.rcv_photos);
        rv_pacakge = findViewById(R.id.rv_pacakge);

        tvEducation.setText(SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME));
        tvPrimaryEducationLanguage.setText(SharedPref.getPrefs(_act, IConstant.LANGUAGE_NAME));
        tvOccupation.setText(SharedPref.getPrefs(_act, IConstant.OCCUPATION_NAME));
        tvCompany.setText(SharedPref.getPrefs(_act, IConstant.COMPANY));
        tvDesignation.setText(SharedPref.getPrefs(_act, IConstant.DESIGNATION));
        tvIncome.setText(SharedPref.getPrefs(_act, IConstant.INCOME));
        tvOtherIncome.setText(SharedPref.getPrefs(_act, IConstant.OTHER_INCOME));

        camera = new Camera((AppCompatActivity) _act);


        profilePackageInforArrayList = new ArrayList<>();
        profilePackageInforArrayList.add(new ProfilePackageInfor("Response Rate Mobile", R.drawable.mobile_rate, Shared_Preferences.getPrefs(ProfileActivity.this, Constants.TotalMobileViews)));
        profilePackageInforArrayList.add(new ProfilePackageInfor("Response Rate Email", R.drawable.email_rate, Shared_Preferences.getPrefs(ProfileActivity.this, Constants.TotalEmailViews)));
        profilePackageInforArrayList.add(new ProfilePackageInfor("Use Mobile View", R.drawable.mobile_view, Shared_Preferences.getPrefs(ProfileActivity.this, Constants.UsedMobileViews)));
        profilePackageInforArrayList.add(new ProfilePackageInfor("Use Email View", R.drawable.email_view, Shared_Preferences.getPrefs(ProfileActivity.this, Constants.UsedEmailViews)));

        rv_pacakge.setHasFixedSize(true);
        rv_pacakge.setNestedScrollingEnabled(false);
        homeAdapter = new ProfilePackageInfoAdapter(getApplicationContext(), profilePackageInforArrayList);
        gridLayoutManager = new GridLayoutManager(_act, 1);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv_pacakge.setLayoutManager(gridLayoutManager);
        rv_pacakge.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();


        iv_view_aadhar_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage,
                        SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharImagePath) + SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharFrontImage));
                startActivity(intent);
            }
        });

        iv_view_aadhar_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage,
                        SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharImagePath) +
                                SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharBackImage));
                startActivity(intent);
            }
        });

        iv_view_horoscope_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FullPhotoZoomActivity.class);
                intent.putExtra(IConstant.UserImage,
                        SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImagePath) + SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImage));
                startActivity(intent);
            }
        });


        if (SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharFrontImage) != null &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharFrontImage).equals(null) &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharFrontImage).equals("null")) {

            Glide.with(ProfileActivity.this)
                    .load(SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharImagePath) + SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharFrontImage))
                    .into(ivAdharImg1);

        } else {
            Glide.with(ProfileActivity.this)
                    .load(R.drawable.no_image_available)
                    .into(ivAdharImg1);
        }

        if (SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharBackImage) != null &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharBackImage).equals(null) &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharBackImage).equals("null")) {

            Glide.with(ProfileActivity.this)
                    .load(SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharImagePath) +
                            SharedPref.getPrefs(ProfileActivity.this, IConstant.AdharBackImage))
                    .into(ivAdharImg2);

        } else {
            Glide.with(ProfileActivity.this)
                    .load(R.drawable.no_image_available)
                    .into(ivAdharImg2);
        }

        if (SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImage) != null &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImage).equals(null) &&
                !SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImage).equals("null")) {

            Glide.with(ProfileActivity.this)
                    .load(SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImagePath) + SharedPref.getPrefs(ProfileActivity.this, IConstant.HoroScopeImage))
                    .into(ivHoroscopeImage);

        } else {
            Glide.with(ProfileActivity.this)
                    .load(R.drawable.no_image_available)
                    .into(ivHoroscopeImage);
        }

        ivEditBasicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, BasicDetailsAddAndEditActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNameDailog();
            }
        });

        ivEditOtherInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PersonalDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivEditFamilyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, FamilyDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivEditEducationAndJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfessionalDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(civ_add_photo, 5);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(ProfileActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();*/


                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(civ_add_photo, 5);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(civ_add_photo, 5);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }
            }
        });

        ivAdharImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(ProfileActivity.this, IConstant.FRONTBACK, "front");
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(ivAdharImg1, 0);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(ivAdharImg1, 0);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }
            }
        });

        ivAdharImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(ProfileActivity.this, IConstant.FRONTBACK, "back");
                /*Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(ivAdharImg2, 0);
                                }else{
                                    for(PermissionDeniedResponse curr : report.getDeniedPermissionResponses()){
                                        Log.d("permission", "PERMISSION 2 : " + curr.getPermissionName());
                                    }
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(ProfileActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();*/

                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(ivAdharImg2, 0);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(ivAdharImg2, 0);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }
            }
        });

        ivEditProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(ProfileActivity.this, IConstant.FRONTBACK, "back");
                /*Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(ivProfileImage, 2);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(ProfileActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();*/

                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(ivProfileImage, 2);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(ivProfileImage, 2);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }

            }
        });

        tv_upload = findViewById(R.id.tv_upload);

        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(ProfileActivity.this, IConstant.FRONTBACK, "back");
                /*Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(ivProfileImage, 2);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(ProfileActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();*/

                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(ivProfileImage, 2);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(ivProfileImage, 2);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }

            }
        });

        ivHoroscopeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Dexter.withActivity(ProfileActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(ivHoroscopeImage, 1);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(ProfileActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();*/

                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_AND_MEDIA_TERAMASU, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            camera.selectImage(ivHoroscopeImage, 1);
                        }

                        @Override
                        public void onPermissionDenied() {
                            //Toast.makeText(_act, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    PermissionManager.requestPermissions(ProfileActivity.this, PermissionManager.PERMISSION_CAMERA_ONLY_AV_10, new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted() {
                            // Code to execute when permissions are granted
                            camera.selectImage(ivHoroscopeImage, 1);
                        }

                        @Override
                        public void onPermissionDenied() {
                            // Code to execute when permissions are denied
                        }
                    });
                }

            }
        });

        btnUpdatePackage = findViewById(R.id.btnUpdatePackage);
        btnUpdatePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PackageActivity.class);
                startActivity(intent);
            }
        });

        getPhotos();

    }

    private void Gender() {

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
            getGridLayoutManagerGender = new GridLayoutManager(_act, 2);
            getGridLayoutManagerGender.setOrientation(RecyclerView.VERTICAL);
            rvGender.setLayoutManager(getGridLayoutManagerGender);
            rvGender.setItemAnimator(new DefaultItemAnimator());
            rvGender.setAdapter(genderAdapter);
            //profileCreatedByAdapter.setSelected(0);
            genderAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvGender.setVisibility(View.VISIBLE);


            Log.d("Value", "Gender: "+SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_GENDER));

            for (int i = 0; i < genderObjArrayList.size(); i++) {
                if (genderObjArrayList.get(i).name.equals(SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_GENDER))) {
                    genderObjArrayList.get(i).setSelected(true);
                } else {
                    genderObjArrayList.get(i).setSelected(false);
                }
            }

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
                            Log.d("Test", "onItemClick: " + strGender);

                        }
                    })
            );
        }
    }

    private void updateNameDailog() {

        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        dialog = new Dialog(_act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dailog_update_personal_details);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();

        etFirstName = dialog.findViewById(R.id.etFirstName);
        etMiddleName = dialog.findViewById(R.id.etMiddleName);
        etLastName = dialog.findViewById(R.id.etLastName);
        ivDailogClose = dialog.findViewById(R.id.ivDailogClose);
        btnSignUp = dialog.findViewById(R.id.btnSignUp);

        etFirstName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME));
        etMiddleName.setText(SharedPref.getPrefs(_act, IConstant.USER_MIDDLE_NAME));
        etLastName.setText(SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
        rvGender = dialog.findViewById(R.id.rvGender);
        Gender();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webcallUpdateName(dialog);
            }
        });

        ivDailogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });

    }

    private void webcallUpdateName( Dialog dialog) {

        Helper_Method.showProgressBar(_act, "Updating...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTUpdateName(
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                etFirstName.getText().toString().trim(),
                etMiddleName.getText().toString().trim(),
                etLastName.getText().toString().trim(),
                strGender,
                ""

        );

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
                            dialog.dismiss();

                            SharedPref.setPrefs(_act,IConstant.USER_FIRST_NAME, etFirstName.getText().toString());
                            SharedPref.setPrefs(_act,IConstant.USER_LAST_NAME, etLastName.getText().toString());
                            SharedPref.setPrefs(_act,IConstant.USER_MIDDLE_NAME, etMiddleName.getText().toString());
                            SharedPref.setPrefs(_act,IConstant.USER_GENDER, strGender);

                            tvFirstName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME));
                            tvLastName.setText(SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
                            tvMiddleName.setText(SharedPref.getPrefs(_act, IConstant.USER_MIDDLE_NAME));
                            tvGender.setText(SharedPref.getPrefs(_act, IConstant.USER_GENDER));
                            Helper_Method.dismissProgessBar();

                        } else {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", ProfileActivity.this.getPackageName(), null);
        intent.setData(uri);
        ((Activity) ProfileActivity.this).startActivityForResult(intent, 101);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void processFinish(String output, int img_no) {
        String[] parts = output.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = output;
        if (img_no == 0) {
            uploadFile(profile_image_path, profile_image_name);
        } else if (img_no == 1) {
            uploadFileHoroscope(profile_image_path, profile_image_name);
        } else if (img_no == 2) {
            uploadFileUpserProfile(profile_image_path, profile_image_name);
        } else if (img_no == 5) {
            civ_add_photo.setVisibility(View.VISIBLE);
            uploadFileUpserMultiplePhotos(profile_image_path, profile_image_name);
        }
    }

    private void uploadFile(String fileUri, String filename) {
        // create upload service client
        progressDialog.show();
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);
            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("adhar_image", filename, requestFile);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_ID));
        RequestBody imageType = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(ProfileActivity.this, IConstant.FRONTBACK));
        //RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), et_update_profile_lastname.getText().toString());
        Call<ResponseBody> call;
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.upload(user_id, imageType, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");

                    if (res) {

                        SharedPref.setPrefs(ProfileActivity.this, IConstant.AADHAR_PHOTO_STATUS, jsonObject.getString("adhar_front_image"));

                        if (SharedPref.getPrefs(ProfileActivity.this, IConstant.FRONTBACK).equals("front")) {
                            SharedPref.setPrefs(ProfileActivity.this, IConstant.AdharFrontImage, jsonObject.getString("adhar_image"));

                            Glide.with(_act)
                                    .load(SharedPref.getPrefs(_act, IConstant.AdharImagePath) + SharedPref.getPrefs(_act, IConstant.AdharFrontImage))
                                    .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                    .into(ivAdharImg1);
                        } else if (SharedPref.getPrefs(ProfileActivity.this, IConstant.FRONTBACK).equals("back")) {
                            SharedPref.setPrefs(ProfileActivity.this, IConstant.AdharBackImage, jsonObject.getString("adhar_image"));
                            Glide.with(_act)
                                    .load(SharedPref.getPrefs(_act, IConstant.AdharImagePath) + SharedPref.getPrefs(_act, IConstant.AdharBackImage))
                                    .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                    .into(ivAdharImg2);
                        }
                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ProfileActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
    }

    private void uploadFileHoroscope(String fileUri, String filename) {
        // create upload service client
        progressDialog.show();
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);
            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("horoscope_image", filename, requestFile);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_ID));
        //RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), et_update_profile_lastname.getText().toString());
        Call<ResponseBody> call;
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.uploadHoroscope(user_id, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");

                    if (res) {


                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();
                        SharedPref.setPrefs(ProfileActivity.this, IConstant.HoroScopeImage, jsonObject.getString("horoscope_image"));
                        Glide.with(_act)
                                .load(SharedPref.getPrefs(_act, IConstant.HoroScopeImagePath) + SharedPref.getPrefs(_act, IConstant.HoroScopeImage))
                                .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                .into(ivHoroscopeImage);
                    } else
                        Toast.makeText(ProfileActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
    }

    private void getPhotos() {
        progressDialog = new ProgressDialog(_act);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(_act.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        photoObjectsArrayList.clear();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getPhotos(
                SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_ID)
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

                    if (photoObjectsArrayList.size() > 3) {
                        btn_add_photo.setVisibility(GONE);
                    } else {
                        btn_add_photo.setVisibility(View.VISIBLE);
                    }


                    photosAdapter = new PhotosAdapter(ProfileActivity.this, photoObjectsArrayList, profile_path);
                    rcv_photos.setLayoutManager(new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false));
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

    private void uploadFileUpserMultiplePhotos(String fileUri, String filename) {
        // create upload service client
        progressDialog.show();
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);
            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("image", filename, requestFile);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_ID));
        //RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), et_update_profile_lastname.getText().toString());
        Call<ResponseBody> call;
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.uploadUserPhoto(user_id, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");


                    if (res) {
                        civ_add_photo.setVisibility(GONE);
                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        getPhotos();

                    } else
                        Toast.makeText(ProfileActivity.this, "" + reason, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
    }

    private void uploadFileUpserProfile(String fileUri, String filename) {
        // create upload service client
        progressDialog.show();
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);
            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("image", filename, requestFile);
        }

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(ProfileActivity.this, IConstant.USER_ID));
        //RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), et_update_profile_lastname.getText().toString());
        Call<ResponseBody> call;
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.uploadUser(user_id, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");


                    if (res) {

                        SharedPref.setPrefs(ProfileActivity.this, IConstant.PROFILE_PHOTO_STATUS, jsonObject.getString("profile_photo_status"));
                        SharedPref.setPrefs(ProfileActivity.this, IConstant.PROFILE_PHOTO, jsonObject.getString("image"));
                        Glide.with(_act)
                                .load(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO))
                                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                                .into(ivProfileImage);


                        Toast.makeText(ProfileActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ProfileActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_profile_image_list")
        public Call<ResponseBody> getPhotos(
                @Field("userId") String userId
        );

    }

    public interface FileUploadService {
        @Multipart
        @POST(MyConfig.BrahminMatrimony + "/app_adharimage")
        Call<ResponseBody> upload(
                @Part("loginId") @Nullable RequestBody loginId,
                @Part("image_type") @Nullable RequestBody image_type,
                @Part @Nullable MultipartBody.Part file);

        @Multipart
        @POST(MyConfig.BrahminMatrimony + "/app_horoscopeimage")
        Call<ResponseBody> uploadHoroscope(
                @Part("loginId") @Nullable RequestBody loginId,
                @Part @Nullable MultipartBody.Part file);

        @Multipart
        @POST(MyConfig.BrahminMatrimony + "/app_profileimage")
        Call<ResponseBody> uploadUser(
                @Part("loginId") @Nullable RequestBody loginId,
                @Part @Nullable MultipartBody.Part file);

        @Multipart
        @POST(MyConfig.BrahminMatrimony + "/app_profile_image_add")
        Call<ResponseBody> uploadUserPhoto(
                @Part("userId") @Nullable RequestBody userId,
                @Part @Nullable MultipartBody.Part file);

    }
}