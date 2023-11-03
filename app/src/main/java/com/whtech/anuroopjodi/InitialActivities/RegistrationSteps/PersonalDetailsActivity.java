package com.whtech.anuroopjodi.InitialActivities.RegistrationSteps;

import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whtech.anuroopjodi.Adapter.DietAdapter;
import com.whtech.anuroopjodi.Adapter.DrinkingAdapter;
import com.whtech.anuroopjodi.Adapter.ManglikAdapter;
import com.whtech.anuroopjodi.Adapter.SmokingAdapter;
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
import com.whtech.anuroopjodi.Object.BloodGroupObject;
import com.whtech.anuroopjodi.Object.CommonYesNoObject;
import com.whtech.anuroopjodi.Object.ComplexionObject;
import com.whtech.anuroopjodi.Object.DietObject;
import com.whtech.anuroopjodi.Object.HeightObject;
import com.whtech.anuroopjodi.Object.RashiObject;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.Camera;
import com.whtech.anuroopjodi.my_library.CircleImageView;
import com.whtech.anuroopjodi.my_library.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class PersonalDetailsActivity extends BaseActivity implements Camera.AsyncResponse {

    private static final String TAG = "PersonalDetailsActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private int mNoOfColumns = 0;
    private String user_profile_path = null;

    //Height Spinner
    private ArrayList<HeightObject> heightObjectArrayList;
    private SearchableSpinner spinnerHeight;
    private ArrayAdapter<HeightObject> spinnerHeight_Adapter;
    private String strHeightId = "0", strHeight;

    //Complexion Spinner
    private ArrayList<ComplexionObject> complexionObjectArrayList;
    private SearchableSpinner spinnerComplexion;
    private ArrayAdapter<ComplexionObject> spinnerComplexion_Adapter;
    private String strComplexionId = "0", strComplexion;

    //Rashi Spinner
    private ArrayList<RashiObject> rashiObjectArrayList;
    private SearchableSpinner spinnerRashi;
    private ArrayAdapter<RashiObject> spinnerRashi_Adapter;
    private String strRashiId = "0", strRashi;

    //Blood Group Spinner
    private ArrayList<BloodGroupObject> bloodGroupObjectArrayList;
    private SearchableSpinner spinnerBloodGroup;
    private ArrayAdapter<BloodGroupObject> spinnerBloodGroup_Adapter;
    private String strBloodGroupId = "0", strBloodGroup;

    //Gan Spinner
    private ArrayList<BloodGroupObject> ganObjectArrayList;
    private SearchableSpinner spinnerGan;
    private ArrayAdapter<BloodGroupObject> spinnerGan_Adapter;
    private String strspinnerGanId = "0", strspinnerGan;

    //Nadi Spinner
    private ArrayList<BloodGroupObject> nadiObjectArrayList;
    private SearchableSpinner spinnerNadi;
    private ArrayAdapter<BloodGroupObject> spinnerNadi_Adapter;
    private String strspinnerNadiId = "0", strspinnerNadi;

    //Charan Spinner
    private ArrayList<BloodGroupObject> charanObjectArrayList;
    private SearchableSpinner spinnerCharan;
    private ArrayAdapter<BloodGroupObject> spinnerCharan_Adapter;
    private String strspinnerCharanId = "0", strspinnerCharan;


    //Manglik Types
    private ArrayList<CommonYesNoObject> manglikObjArrayList;
    private RecyclerView rvManglik;
    private ManglikAdapter manglikAdapter;
    private GridLayoutManager getGridLayoutManagerManglik;
    private String strManglik = null;

    //Diet Types
    private ArrayList<DietObject> dietObjectArrayList;
    private RecyclerView rvDiet;
    private DietAdapter dietAdapter;
    private GridLayoutManager getGridLayoutManagerDiet;
    private String strDiet = null;

    //Smoking Types
    private ArrayList<CommonYesNoObject> smokingObjArrayList;
    private RecyclerView rvSmoking;
    private SmokingAdapter smokingAdapter;
    private GridLayoutManager getGridLayoutManagerSmoking;
    private String strSmoking = null;

    //Drinking Types
    private ArrayList<CommonYesNoObject> drinkingObjArrayList;
    private RecyclerView rvDrinking;
    private DrinkingAdapter drinkingAdapter;
    private GridLayoutManager getGetGridLayoutManagerDrinking;
    private String strDrinking = null;

    //Intercast Types
    private ArrayList<CommonYesNoObject> intercastObjArrayList;
    private RecyclerView rvIntercast;
    private DrinkingAdapter IntercastAdapter;
    private GridLayoutManager getGetGridLayoutManagerIntercast;
    private String strIntercast = "No";

    //shakhabedh Types
    private ArrayList<CommonYesNoObject> shakhbedhObjArrayList;
    private RecyclerView rvShakhabedh;
    private DrinkingAdapter rvShakhabedhAdapter;
    private GridLayoutManager getGetGridLayoutManagerrvShakhabedh;
    private String strrvShakhabedh = "";

    private EditText etWeight, etNakshatra, etGan, etCharan, etGotra, etNadi, etDevak, etComplexion;

    //new photos
    private ImageView ivProfileImage;
    private CircleImageView civ_add_photo;
    private Button btn_add_photo;
    private ImageView iv_view_aadhar_image, iv_view_aadhar_image2, iv_view_horoscope_image, ivEditProfilePhoto;
    private Camera camera;
    private CircleImageView ivAdharImg1, ivAdharImg2, ivHoroscopeImage;
    private ProgressDialog progressDialog;
    private String profile_image_name = "", profile_image_path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        _act = PersonalDetailsActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        mNoOfColumns = SignUpActivity.Utility.calculateNoOfColumns(getApplicationContext(), 120);
        Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("" + getResources().getString(R.string.updating_profile_dialog_message));
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_personal_details));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        spinnerHeight = findViewById(R.id.spinnerHeight);
        spinnerComplexion = findViewById(R.id.spinnerComplexion);
        spinnerRashi = findViewById(R.id.spinnerRashi);
        etWeight = findViewById(R.id.etWeight);
        etNakshatra = findViewById(R.id.etNakshatra);
        etGan = findViewById(R.id.etGan);
        etCharan = findViewById(R.id.etCharan);
        etGotra = findViewById(R.id.etGotra);
        etNadi = findViewById(R.id.etNadi);
        etDevak = findViewById(R.id.etDevak);
        etComplexion = findViewById(R.id.etComplexion);

        //Default Webcall
        if (connectionDetector.isConnectionAvailable()) {

            BloodGroup();
        } else {
            Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));

        }

        findViewById(R.id.btnSteptwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (SharedPref.getPrefs(_act, IConstant.AADHAR_PHOTO_STATUS) != null &&
                        !SharedPref.getPrefs(_act, IConstant.AADHAR_PHOTO_STATUS).equals("null")) {*/
                /*if (SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO_STATUS) != null &&
                        !SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO_STATUS).equals("null")) {*/
                    if (strHeightId != null && !strHeightId.isEmpty() && !strHeightId.equals("null") && !strHeightId.equals("0")) {
                        if (strBloodGroupId != null && !strBloodGroupId.isEmpty() && !strBloodGroupId.equals("null") && !strBloodGroupId.equals("0")) {
                            if (strRashiId != null && !strRashiId.isEmpty() && !strRashiId.equals("null") && !strRashiId.equals("0")) {
                                if (strManglik != null && !strManglik.isEmpty() && !strManglik.equals("null") && !strManglik.equals("")) {
                                    if (strDiet != null && !strDiet.isEmpty() && !strDiet.equals("null") && !strDiet.equals("")) {
                                        if (strSmoking != null && !strSmoking.isEmpty() && !strSmoking.equals("null") && !strSmoking.equals("0")) {
                                            if (strDrinking != null && !strDrinking.isEmpty() && !strDrinking.equals("null") && !strDrinking.equals("0")) {
                                                if (isValid()) {
                                                    if (connectionDetector.isConnectionAvailable()) {
                                                        webcallPersonalDetails();
                                                    } else {
                                                        Helper_Method.toaster_long(_act, getResources().getString(R.string.internet_connection_required));
                                                    }
                                                } else {
                                                    Helper_Method.toaster(_act, "Check and fill all the fields");
                                                }
                                            } else {
                                                Helper_Method.toaster(_act, "Select drinking or not");
                                            }
                                        } else {
                                            Helper_Method.toaster(_act, "Select smoking or not");
                                        }
                                    } else {
                                        Helper_Method.toaster(_act, "Select diet");
                                    }
                                } else {
                                    Helper_Method.toaster(_act, "Select Manglik or not");
                                }
                            } else {
                                Helper_Method.toaster(_act, "Select Rashi");
                            }
                        } else {
                            Helper_Method.toaster(_act, "Select Blood Group");
                        }
                    } else {
                        Helper_Method.toaster(_act, "Select Height");
                    }
                 /*else {
                    Helper_Method.toaster(_act, "Please upload profile photo");
                }*/
                 /*else {
                    Helper_Method.toaster(_act, "Please upload Aadhar photo");
                }*/


            }
        });

        if (getIntent().getStringExtra(IConstant.EditFlag) != null) {
            if (getIntent().getStringExtra(IConstant.EditFlag).equals("1")) {
                setValuesToUpdate();
            }
        }

        init();
    }

    private void init() {
        ivProfileImage = findViewById(R.id.ivProfileImage);
        iv_view_aadhar_image = findViewById(R.id.iv_view_aadhar_image);
        iv_view_aadhar_image2 = findViewById(R.id.iv_view_aadhar_image2);
        iv_view_horoscope_image = findViewById(R.id.iv_view_horoscope_image);
        ivEditProfilePhoto = findViewById(R.id.ivEditProfilePhoto);

        civ_add_photo = findViewById(R.id.civ_add_photo);
        //btn_add_photo = findViewById(R.id.btn_add_photo);

        ivAdharImg1 = (CircleImageView) findViewById(R.id.ivAdharImg1);
        ivAdharImg2 = (CircleImageView) findViewById(R.id.ivAdharImg2);
        ivHoroscopeImage = (CircleImageView) findViewById(R.id.ivHoroscopeImage);

        camera = new Camera((AppCompatActivity) _act);

        ivAdharImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK, "front");
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(PersonalDetailsActivity.this)
                            .withPermissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO)
                                    /*Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                        camera.selectImage(ivAdharImg1, 0);
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
                else{
                    Dexter.withActivity(PersonalDetailsActivity.this)
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
            }
        });

        ivAdharImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK, "back");
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(PersonalDetailsActivity.this)
                            .withPermissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO)
                                    /*Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                        camera.selectImage(ivAdharImg2, 0);
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }else{
                    Dexter.withActivity(PersonalDetailsActivity.this)
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
            }
        });

        ivEditProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK, "back");
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(PersonalDetailsActivity.this)
                            .withPermissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO)
                                    /*Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
                else{
                    Dexter.withActivity(PersonalDetailsActivity.this)
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
            }
        });

        ivHoroscopeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(PersonalDetailsActivity.this)
                            .withPermissions(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_MEDIA_VIDEO,
                                    Manifest.permission.READ_MEDIA_IMAGES,
                                    Manifest.permission.READ_MEDIA_AUDIO)
                                    /*Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)*/
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
                else{
                    Dexter.withActivity(PersonalDetailsActivity.this)
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
                                    Toast.makeText(PersonalDetailsActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void processFinish(String output, int img_no) {
        String[] parts = output.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = output;
        if (img_no == 0) {
            //uploadFile(profile_image_path, profile_image_name);
        } else if (img_no == 1) {
            uploadFileHoroscope(profile_image_path, profile_image_name);
        } else if (img_no == 2) {
           // uploadFileUpserProfile(profile_image_path, profile_image_name);
        }
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", PersonalDetailsActivity.this.getPackageName(), null);
        intent.setData(uri);
        ((Activity) PersonalDetailsActivity.this).startActivityForResult(intent, 101);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalDetailsActivity.this);
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

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.USER_ID));
        RequestBody imageType = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK));
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

                        SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.AADHAR_PHOTO_STATUS, jsonObject.getString("adhar_front_image"));

                        /*if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK).equals("front")) {
                            SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.AdharFrontImage, jsonObject.getString("adhar_image"));

                            Glide.with(_act)
                                    .load(SharedPref.getPrefs(_act, IConstant.AdharImagePath) + SharedPref.getPrefs(_act, IConstant.AdharFrontImage))
                                    .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                    .into(ivAdharImg1);
                        } else if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.FRONTBACK).equals("back")) {
                            SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.AdharBackImage, jsonObject.getString("adhar_image"));
                            Glide.with(_act)
                                    .load(SharedPref.getPrefs(_act, IConstant.AdharImagePath) + SharedPref.getPrefs(_act, IConstant.AdharBackImage))
                                    .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                    .into(ivAdharImg2);
                        }*/
                        Toast.makeText(PersonalDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(PersonalDetailsActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
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

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.USER_ID));
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


                        Toast.makeText(PersonalDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                        SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.HoroScopeImage, jsonObject.getString("horoscope_image"));
                        /*Glide.with(_act)
                                .load(SharedPref.getPrefs(_act, IConstant.HoroScopeImagePath) + SharedPref.getPrefs(_act, IConstant.HoroScopeImage))
                                .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew))
                                .into(ivHoroscopeImage);*/
                    } else
                        Toast.makeText(PersonalDetailsActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
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

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.USER_ID));
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

                        SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.PROFILE_PHOTO_STATUS, jsonObject.getString("profile_photo_status"));
                        SharedPref.setPrefs(PersonalDetailsActivity.this, IConstant.PROFILE_PHOTO, jsonObject.getString("image"));
                      /*  Glide.with(_act)
                                .load(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO))
                                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                                .into(ivProfileImage);*/


                        Toast.makeText(PersonalDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(PersonalDetailsActivity.this, "" + reason, Toast.LENGTH_SHORT).show();
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

    private void setValuesToUpdate() {

        etWeight.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.WEIGHT));
        etNakshatra.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NAKSHATRA));
        etGan.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN));
        etCharan.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN));
        etGotra.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GOTRA));
        etNadi.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI));
        //etDevak.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.DEVAK));
        etComplexion.setText(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.COMPLEXION));

        strHeightId = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_NAME);
        strBloodGroup = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD);
        strManglik = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.MANGLIK);
        strRashi = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI);
        strDiet = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.LIFE_STYLE);
        strSmoking = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.SMOKING);
        strDrinking = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.DRINKING);
        strIntercast = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.INTERCASTE);
        strrvShakhabedh = SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.DEVAK);

        for (int i = 0; i < manglikObjArrayList.size(); i++) {
            if (manglikObjArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.MANGLIK))) {
                manglikObjArrayList.get(i).setSelected(true);
            } else {
                manglikObjArrayList.get(i).setSelected(false);
            }
        }

        for (int i = 0; i < dietObjectArrayList.size(); i++) {
            if (dietObjectArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.LIFE_STYLE))) {
                dietObjectArrayList.get(i).setSelected(true);
            } else {
                dietObjectArrayList.get(i).setSelected(false);
            }
        }

        for (int i = 0; i < smokingObjArrayList.size(); i++) {
            if (smokingObjArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.SMOKING))) {
                smokingObjArrayList.get(i).setSelected(true);
            } else {
                smokingObjArrayList.get(i).setSelected(false);
            }
        }


        for (int i = 0; i < drinkingObjArrayList.size(); i++) {
            if (drinkingObjArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.DRINKING))) {
                drinkingObjArrayList.get(i).setSelected(true);
            } else {
                drinkingObjArrayList.get(i).setSelected(false);
            }
        }

      /*  intercastObjArrayList = new ArrayList<>();
        intercastObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        intercastObjArrayList.add(new CommonYesNoObject("2", "No", false));
        Log.d(TAG, "INTERCASTE1: "+SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.INTERCASTE));
*/
        for (int i = 0; i < intercastObjArrayList.size(); i++) {
            if (intercastObjArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.INTERCASTE))) {
                intercastObjArrayList.get(i).setSelected(true);
                Log.d("intercast", "setValuesToUpdate: Yes");
                break;
            } else {
                intercastObjArrayList.get(i).setSelected(false);
                Log.d("intercast", "setValuesToUpdate: No");
                //intercastObjArrayList.get(i).setSelected(false);
            }
        }

        for (int i = 0; i < shakhbedhObjArrayList.size(); i++) {
            if (shakhbedhObjArrayList.get(i).name.equals(SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.DEVAK))) {
                shakhbedhObjArrayList.get(i).setSelected(true);
                Log.d("intercast", "setValuesToUpdate: Yes");
                break;
            } else {
                shakhbedhObjArrayList.get(i).setSelected(false);
                Log.d("intercast", "setValuesToUpdate: No");
                //intercastObjArrayList.get(i).setSelected(false);
            }
        }


    }

    private void webcallPersonalDetails() {

        Log.d("InputValues", "strIntercast: " + strIntercast);

        Helper_Method.showProgressBar(_act, "Updating Personal Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTPersonalDetails(
                strHeightId,
                etWeight.getText().toString().trim(),
                etComplexion.getText().toString().trim(),
                strBloodGroup,
                etNakshatra.getText().toString().trim(),
                strspinnerGan,
                strspinnerCharan,
                etGotra.getText().toString().trim(),
                strManglik,
                strspinnerNadi,
                strrvShakhabedh,
                strIntercast,
                strRashi,
                strDiet,
                strSmoking,
                strDrinking,
                SharedPref.getPrefs(_act, IConstant.USER_ID)
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

                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");

                        if (stringCode.equalsIgnoreCase("true")) {
                            user_profile_path = i.getString("user_profile_path");


                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                            SharedPref.setPrefs(_act, IConstant.USER_ID, String.valueOf(jsonObjectData.getString("id")));
                            SharedPref.setPrefs(_act, IConstant.PACKAGE, String.valueOf(jsonObjectData.getString("package")));
                            SharedPref.setPrefs(_act, IConstant.DEVAK, String.valueOf(jsonObjectData.getString("devak")));
                            SharedPref.setPrefs(_act, IConstant.INTERCASTE, String.valueOf(jsonObjectData.getString("intercaste_marriage")));
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
                                Helper_Method.intentActivity(_act, ProfessionalDetailsActivity.class, false);
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

        if (validations.isBlank(etWeight)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etWeight.startAnimation(shake);
            etWeight.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etComplexion)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etComplexion.startAnimation(shake);
            etComplexion.setError(IErrors.EMPTY);
            return false;
        }
        if (validations.isBlank(etNakshatra)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etNakshatra.startAnimation(shake);
            etNakshatra.setError(IErrors.EMPTY);
            return false;
        }
        /*if (validations.isBlank(etGan)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etGan.startAnimation(shake);
            etGan.setError(IErrors.EMPTY);
            return false;
        }*/
        /*if (validations.isBlank(etCharan)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etCharan.startAnimation(shake);
            etCharan.setError(IErrors.EMPTY);
            return false;
        }*/
        /*if (validations.isBlank(etGotra)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etGotra.startAnimation(shake);
            etGotra.setError(IErrors.EMPTY);
            return false;
        }*/
       /* if (validations.isBlank(etNadi)) {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            etNadi.startAnimation(shake);
            etNadi.setError(IErrors.EMPTY);
            return false;
        }*/

        return true;
    }

    private void webcallHeight() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
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
                                spinnerHeight.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                                spinnerHeight_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                                spinnerHeight_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHeight.setAdapter(spinnerHeight_Adapter);
                                spinnerHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strHeightId = heightObjectArrayList.get(i).id;
                                        strHeight = heightObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_ID) != null &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_ID).isEmpty() &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_ID).equals("null") &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_ID).equals("")) {

                                    for (int k = 0; k < heightObjectArrayList.size(); k++) {
                                        if (heightObjectArrayList.get(k).getId().equals(
                                                SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.HEIGHT_ID))) {
                                            spinnerHeight.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            heightObjectArrayList.clear();
                            spinnerHeight.setTitle(getResources().getString(R.string.lbl_height_from_hint));
                            spinnerHeight_Adapter = new ArrayAdapter<HeightObject>(_act, android.R.layout.simple_spinner_item, heightObjectArrayList);
                            spinnerHeight_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHeight.setAdapter(spinnerHeight_Adapter);
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
                    //Helper_Method.dismissProgessBar();
                    webcallComplexion();
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

    private void webcallComplexion() {

        //Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETComplexion();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    complexionObjectArrayList = new ArrayList<>();
                    complexionObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("complexion_list");
                            complexionObjectArrayList.add(new ComplexionObject("0", getResources().getString(R.string.lbl_select_complexion)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    complexionObjectArrayList.add(new ComplexionObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (complexionObjectArrayList.size() == 0) {


                            } else {
                                spinnerComplexion.setTitle(getResources().getString(R.string.lbl_complexion_hint));
                                spinnerComplexion_Adapter = new ArrayAdapter<ComplexionObject>(_act, android.R.layout.simple_spinner_item, complexionObjectArrayList);
                                spinnerComplexion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerComplexion.setAdapter(spinnerComplexion_Adapter);
                                spinnerComplexion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strComplexionId = complexionObjectArrayList.get(i).id;
                                        strComplexion = complexionObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < complexionObjectArrayList.size(); k++) {
                                        if (complexionObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerComplexion.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            complexionObjectArrayList.clear();
                            spinnerComplexion.setTitle(getResources().getString(R.string.lbl_complexion_hint));
                            spinnerComplexion_Adapter = new ArrayAdapter<ComplexionObject>(_act, android.R.layout.simple_spinner_item, complexionObjectArrayList);
                            spinnerComplexion_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerComplexion.setAdapter(spinnerComplexion_Adapter);
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
                    //Helper_Method.dismissProgessBar();
                    webcallRashi();
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

    private void webcallRashi() {

        //Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETRashi();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    rashiObjectArrayList = new ArrayList<>();
                    rashiObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("rashi_list");
                            rashiObjectArrayList.add(new RashiObject("0", getResources().getString(R.string.lbl_select_rashi)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    rashiObjectArrayList.add(new RashiObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (rashiObjectArrayList.size() == 0) {


                            } else {
                                spinnerRashi.setTitle(getResources().getString(R.string.lbl_your_rashi_hint));
                                spinnerRashi_Adapter = new ArrayAdapter<RashiObject>(_act, android.R.layout.simple_spinner_item, rashiObjectArrayList);
                                spinnerRashi_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerRashi.setAdapter(spinnerRashi_Adapter);
                                spinnerRashi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strRashiId = rashiObjectArrayList.get(i).id;
                                        strRashi = rashiObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });


                                if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI) != null &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI).isEmpty() &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI).equals("null") &&
                                        !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI).equals("")) {
                                    for (int k = 0; k < rashiObjectArrayList.size(); k++) {
                                        if (rashiObjectArrayList.get(k).getName().equals
                                                (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.RASHI))) {
                                            spinnerRashi.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            rashiObjectArrayList.clear();
                            spinnerRashi.setTitle(getResources().getString(R.string.lbl_your_rashi_hint));
                            spinnerRashi_Adapter = new ArrayAdapter<RashiObject>(_act, android.R.layout.simple_spinner_item, rashiObjectArrayList);
                            spinnerRashi_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerRashi.setAdapter(spinnerRashi_Adapter);
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
                    Helper_Method.dismissProgessBar();
                    //Intercaste();
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

    public void BloodGroup() {
        bloodGroupObjectArrayList = new ArrayList<>();
        bloodGroupObjectArrayList.add(new BloodGroupObject("0", "Select Blood Group"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("1", "A+"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("2", "A-"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("3", "B+"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("4", "B-"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("5", "O+"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("6", "O-"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("7", "AB+"));
        bloodGroupObjectArrayList.add(new BloodGroupObject("8", "AB-"));
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        spinnerBloodGroup.setTitle(getResources().getString(R.string.lbl_select_blood_group));
        spinnerBloodGroup_Adapter = new ArrayAdapter<BloodGroupObject>(_act, android.R.layout.simple_spinner_item, bloodGroupObjectArrayList);
        spinnerBloodGroup_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(spinnerBloodGroup_Adapter);
        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strBloodGroupId = bloodGroupObjectArrayList.get(i).id;
                strBloodGroup = bloodGroupObjectArrayList.get(i).name;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD) != null &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD).isEmpty() &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD).equals("null") &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD).equals("")) {
            for (int k = 0; k < bloodGroupObjectArrayList.size(); k++) {
                if (bloodGroupObjectArrayList.get(k).getName().equals
                        (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.BLOOD))) {
                    spinnerBloodGroup.setSelection(k);
                }
            }
        } else {

        }

        Charan();

    }

    public void Charan() {
        charanObjectArrayList = new ArrayList<>();
        charanObjectArrayList.add(new BloodGroupObject("0", "Select Charan"));
        charanObjectArrayList.add(new BloodGroupObject("1", "1st"));
        charanObjectArrayList.add(new BloodGroupObject("2", "2nd"));
        charanObjectArrayList.add(new BloodGroupObject("3", "3rd"));
        charanObjectArrayList.add(new BloodGroupObject("4", "4th"));

        spinnerCharan = findViewById(R.id.spinnerCharan);
        spinnerCharan.setTitle(getResources().getString(R.string.lbl_charan_hint));
        spinnerCharan_Adapter = new ArrayAdapter<BloodGroupObject>(_act, android.R.layout.simple_spinner_item, charanObjectArrayList);
        spinnerCharan_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCharan.setAdapter(spinnerCharan_Adapter);
        spinnerCharan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strspinnerCharanId = charanObjectArrayList.get(i).id;
                strspinnerCharan = charanObjectArrayList.get(i).name;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN) != null &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN).isEmpty() &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN).equals("null") &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN).equals("")) {
            for (int k = 0; k < charanObjectArrayList.size(); k++) {
                if (charanObjectArrayList.get(k).getName().equals
                        (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.CHARAN))) {
                    spinnerCharan.setSelection(k);
                }
            }
        } else {

        }

        Gan();

    }

    public void Gan() {
        ganObjectArrayList = new ArrayList<>();
        ganObjectArrayList.add(new BloodGroupObject("0", "Select Gan"));
        ganObjectArrayList.add(new BloodGroupObject("1", "Dev"));
        ganObjectArrayList.add(new BloodGroupObject("2", "Manushya"));
        ganObjectArrayList.add(new BloodGroupObject("3", "Rakshas"));


        spinnerGan = findViewById(R.id.spinnerGan);
        spinnerGan.setTitle(getResources().getString(R.string.lbl_gan));
        spinnerGan_Adapter = new ArrayAdapter<BloodGroupObject>(_act, android.R.layout.simple_spinner_item, ganObjectArrayList);
        spinnerGan_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGan.setAdapter(spinnerGan_Adapter);
        spinnerGan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strspinnerGanId = ganObjectArrayList.get(i).id;
                strspinnerGan = ganObjectArrayList.get(i).name;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN) != null &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN).isEmpty() &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN).equals("null") &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN).equals("")) {
            for (int k = 0; k < ganObjectArrayList.size(); k++) {
                if (ganObjectArrayList.get(k).getName().equals
                        (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.GAN))) {
                    spinnerGan.setSelection(k);
                }
            }
        } else {

        }

        Nadi();

    }

    public void Nadi() {
        nadiObjectArrayList = new ArrayList<>();
        nadiObjectArrayList.add(new BloodGroupObject("0", "Select Nadi"));
        nadiObjectArrayList.add(new BloodGroupObject("1", "Aadya"));
        nadiObjectArrayList.add(new BloodGroupObject("2", "Madhya"));
        nadiObjectArrayList.add(new BloodGroupObject("3", "Aantya"));


        spinnerNadi = findViewById(R.id.spinnerNadi);
        spinnerNadi.setTitle(getResources().getString(R.string.lbl_nadi));
        spinnerNadi_Adapter = new ArrayAdapter<BloodGroupObject>(_act, android.R.layout.simple_spinner_item, nadiObjectArrayList);
        spinnerNadi_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNadi.setAdapter(spinnerNadi_Adapter);
        spinnerNadi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strspinnerNadiId = nadiObjectArrayList.get(i).id;
                strspinnerNadi = nadiObjectArrayList.get(i).name;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI) != null &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI).isEmpty() &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI).equals("null") &&
                !SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI).equals("")) {
            for (int k = 0; k < nadiObjectArrayList.size(); k++) {
                if (nadiObjectArrayList.get(k).getName().equals
                        (SharedPref.getPrefs(PersonalDetailsActivity.this, IConstant.NADI))) {
                    spinnerNadi.setSelection(k);
                }
            }
        } else {

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
        Diet();
    }

    private void Diet() {

        rvDiet = findViewById(R.id.rvDiet);
        dietObjectArrayList = new ArrayList<>();
        dietObjectArrayList.add(new DietObject("0", "Both", false));
        dietObjectArrayList.add(new DietObject("1", "Vegetarian", false));
        dietObjectArrayList.add(new DietObject("2", "Non-Vegetarian", false));

        if (dietObjectArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvDiet.setVisibility(View.GONE);

        } else {


            rvDiet.setHasFixedSize(true);
            rvDiet.setNestedScrollingEnabled(false);
            // dietObjectArrayList.get(0).setSelected(true);
            // dietObjectArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            dietAdapter = new DietAdapter(_act, dietObjectArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvDiet.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerDiet = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerDiet.setOrientation(RecyclerView.VERTICAL);
            rvDiet.setLayoutManager(getGridLayoutManagerDiet);
            rvDiet.setItemAnimator(new DefaultItemAnimator());
            rvDiet.setAdapter(dietAdapter);
            //profileCreatedByAdapter.setSelected(0);
            dietAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvDiet.setVisibility(View.VISIBLE);

            rvDiet.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < dietObjectArrayList.size(); i++) {

                                if (i == position) {
                                    dietObjectArrayList.get(i).setSelected(true);
                                } else {
                                    dietObjectArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            dietAdapter.notifyDataSetChanged();
                            strDiet = dietObjectArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strDiet);

                        }
                    })
            );
        }
        Smoking();
    }

    private void Smoking() {

        rvSmoking = findViewById(R.id.rvSmoking);
        smokingObjArrayList = new ArrayList<>();
        smokingObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        smokingObjArrayList.add(new CommonYesNoObject("2", "No", false));


        if (smokingObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvSmoking.setVisibility(View.GONE);

        } else {


            rvSmoking.setHasFixedSize(true);
            rvSmoking.setNestedScrollingEnabled(false);
            // smokingObjArrayList.get(0).setSelected(true);
            // smokingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            smokingAdapter = new SmokingAdapter(_act, smokingObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvSmoking.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerSmoking = new GridLayoutManager(_act, mNoOfColumns);
            getGridLayoutManagerSmoking.setOrientation(RecyclerView.VERTICAL);
            rvSmoking.setLayoutManager(getGridLayoutManagerSmoking);
            rvSmoking.setItemAnimator(new DefaultItemAnimator());
            rvSmoking.setAdapter(smokingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            smokingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvSmoking.setVisibility(View.VISIBLE);

            rvSmoking.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < smokingObjArrayList.size(); i++) {

                                if (i == position) {
                                    smokingObjArrayList.get(i).setSelected(true);
                                } else {
                                    smokingObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            smokingAdapter.notifyDataSetChanged();
                            strSmoking = smokingObjArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strSmoking);

                        }
                    })
            );
        }
        Drinking();
    }

    private void Drinking() {

        rvDrinking = findViewById(R.id.rvDrinking);
        drinkingObjArrayList = new ArrayList<>();
        drinkingObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        drinkingObjArrayList.add(new CommonYesNoObject("2", "No", false));
        drinkingObjArrayList.add(new CommonYesNoObject("3", "Occasionally", false));

        if (drinkingObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvDrinking.setVisibility(View.GONE);

        } else {


            rvDrinking.setHasFixedSize(true);
            rvDrinking.setNestedScrollingEnabled(false);
            // drinkingObjArrayList.get(0).setSelected(true);
            // drinkingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            drinkingAdapter = new DrinkingAdapter(_act, drinkingObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvDrinking.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGetGridLayoutManagerDrinking = new GridLayoutManager(_act, mNoOfColumns);
            getGetGridLayoutManagerDrinking.setOrientation(RecyclerView.VERTICAL);
            rvDrinking.setLayoutManager(getGetGridLayoutManagerDrinking);
            rvDrinking.setItemAnimator(new DefaultItemAnimator());
            rvDrinking.setAdapter(drinkingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            drinkingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvDrinking.setVisibility(View.VISIBLE);

            rvDrinking.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < drinkingObjArrayList.size(); i++) {

                                if (i == position) {
                                    drinkingObjArrayList.get(i).setSelected(true);
                                } else {
                                    drinkingObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            drinkingAdapter.notifyDataSetChanged();
                            strDrinking = drinkingObjArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strDrinking);

                        }
                    })
            );
        }

        Intercaste();

    }

    private void Intercaste() {

        rvIntercast = findViewById(R.id.rvIntercast);
        intercastObjArrayList = new ArrayList<>();
        intercastObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        intercastObjArrayList.add(new CommonYesNoObject("2", "No", false));

        if (intercastObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvIntercast.setVisibility(View.GONE);

        } else {


            rvIntercast.setHasFixedSize(true);
            rvIntercast.setNestedScrollingEnabled(false);
            // drinkingObjArrayList.get(0).setSelected(true);
            // drinkingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            IntercastAdapter = new DrinkingAdapter(_act, intercastObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvIntercast.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGetGridLayoutManagerIntercast = new GridLayoutManager(_act, mNoOfColumns);
            getGetGridLayoutManagerIntercast.setOrientation(RecyclerView.VERTICAL);
            rvIntercast.setLayoutManager(getGetGridLayoutManagerIntercast);
            rvIntercast.setItemAnimator(new DefaultItemAnimator());
            rvIntercast.setAdapter(IntercastAdapter);
            //profileCreatedByAdapter.setSelected(0);
            IntercastAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvIntercast.setVisibility(View.GONE);

            rvIntercast.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < intercastObjArrayList.size(); i++) {

                                if (i == position) {
                                    intercastObjArrayList.get(i).setSelected(true);
                                } else {
                                    intercastObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            IntercastAdapter.notifyDataSetChanged();
                            //strIntercast = intercastObjArrayList.get(position).name;
                            strIntercast = "No";
                            Log.d("TAG", "onItemClick: " + strIntercast);

                        }
                    })
            );
        }

        Shakhabedh();

        //setValuesToUpdate();
    }

    private void Shakhabedh() {

        rvShakhabedh = findViewById(R.id.rvShakhabedh);
        shakhbedhObjArrayList = new ArrayList<>();
        shakhbedhObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        shakhbedhObjArrayList.add(new CommonYesNoObject("2", "No", false));

        if (shakhbedhObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvShakhabedh.setVisibility(View.GONE);

        } else {


            rvShakhabedh.setHasFixedSize(true);
            rvShakhabedh.setNestedScrollingEnabled(false);
            // drinkingObjArrayList.get(0).setSelected(true);
            // drinkingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            rvShakhabedhAdapter = new DrinkingAdapter(_act, shakhbedhObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvIntercast.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGetGridLayoutManagerrvShakhabedh = new GridLayoutManager(_act, mNoOfColumns);
            getGetGridLayoutManagerrvShakhabedh.setOrientation(RecyclerView.VERTICAL);
            rvShakhabedh.setLayoutManager(getGetGridLayoutManagerrvShakhabedh);
            rvShakhabedh.setItemAnimator(new DefaultItemAnimator());
            rvShakhabedh.setAdapter(rvShakhabedhAdapter);
            //profileCreatedByAdapter.setSelected(0);
            rvShakhabedhAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvShakhabedh.setVisibility(View.VISIBLE);

            rvShakhabedh.addOnItemTouchListener(
                    new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < shakhbedhObjArrayList.size(); i++) {

                                if (i == position) {
                                    shakhbedhObjArrayList.get(i).setSelected(true);
                                } else {
                                    shakhbedhObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            rvShakhabedhAdapter.notifyDataSetChanged();
                            strrvShakhabedh = shakhbedhObjArrayList.get(position).name;
                            Log.d("TAG", "strrvShakhabedh: " + strrvShakhabedh);

                        }
                    })
            );
        }

        webcallHeight();

        //setValuesToUpdate();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //overridePendingTransition(R.animator.left_right, R.animator.right_left);
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
            //overridePendingTransition(R.animator.left_right, R.animator.right_left);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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