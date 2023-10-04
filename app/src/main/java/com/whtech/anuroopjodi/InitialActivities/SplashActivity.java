package com.whtech.anuroopjodi.InitialActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.AppController;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.BasicDetailsAddAndEditActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.FamilyDetailsActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.PersonalDetailsActivity;
import com.whtech.anuroopjodi.InitialActivities.PrefredPartner.PreferredPatnerActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.ProfessionalDetailsActivity;
import com.whtech.anuroopjodi.InsideActivities.DashboardActivity;
import com.whtech.anuroopjodi.R;

import java.io.File;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends BaseActivity {

    private Activity _act;
    public static final int RequestPermissionCode = 1;
    String TAG = "SplashScreen";
    private ImageView iv_app_logo;
    private boolean islogin;

    private Dialog dialog;
    private Button buttonUpdate, buttonExit;
    private String versionCode, versionName;
    private TextView tvVersion;
    private ProgressBar prog;
    public static boolean versionFlag;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        _act = SplashActivity.this;
        connectionDetector = ConnectionDetector.getInstance(this);

        deleteCache(SplashActivity.this);
        iv_app_logo = findViewById(R.id.iv_app_logo);


     /*   DisplayMetrics metricscard = getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        //ivSlideImage.getLayoutParams().width = cardwidth / 2;
        iv_app_logo.getLayoutParams().height = (int) (cardheight / 4);*/


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = String.valueOf(pInfo.versionCode);
            versionName = String.valueOf(pInfo.versionName);
//            tvVersion.setText("Version : " + versionName);
            Log.d(TAG, "onCreate: App Version Code : " + versionCode);
            Log.d(TAG, "onCreate: App Version Code : " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (AppController.isInternet(this)) {


                if (connectionDetector.checkConnection(_act)) {
                    // webServiceVersion();

                    final Thread timer = new Thread() {
                        public void run() {
                            try {

                                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                                iv_app_logo.startAnimation(animation2);
                                animation2.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        // Toast.makeText(SplashScreenActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        deleteCache(_act);
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        if (SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN) != null && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).isEmpty() && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).equals("null") && !SharedPref.getPrefs(_act, IConstant.USER_IS_LOGIN).equals("")) {
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
                                                if(SharedPref.getPrefs(_act, IConstant.is_skip_payement_request).equals("1")){
                                                    Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                                                }else {
                                                    Intent intent = new Intent(_act, PackageActivity.class);
                                                    intent.putExtra("ActivityName", "Splash");

                                                    startActivity(intent);
                                                }
                                            } else {
                                                Helper_Method.intentActivity(_act, DashboardActivity.class, true);
                                            }
                                        } else {
                                            Helper_Method.intentActivity_animate_fade(_act, SplashTwoActivity.class, true);
                                        }
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                            } finally {
                            }
                        }
                    };
                    timer.start();

                } else {

                    new AlertDialog.Builder(_act)
                            .setTitle("Network Checking")
                            .setMessage(getResources().getString(R.string.string_internet_connection_warning))
                            .setCancelable(false)
                            //.setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {

                                    finish();
                                    startActivity(getIntent());

                                }
                            }).create().show();
                }

        }

    }

/*    private void UpdateVersion() {

        int width = (int) (getResources().getDisplayMetrics().widthPixels);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        dialog = new Dialog(_act);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.dailog_update_version);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //dialog.getWindow().getAttributes().windowAnimations = animationSource;
        dialog.show();

        buttonExit = dialog.findViewById(R.id.buttonExit);
        buttonUpdate = dialog.findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(IConstant.APP_URL)));
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ActivityCompat.finishAffinity(_act);
            }
        });
    }*/

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        CAMERA,
                        ACCESS_FINE_LOCATION,
                        /* READ_CONTACTS,
                         READ_PHONE_STATE,
                         GET_ACCOUNTS,
                         READ_SMS,*/
                        WRITE_EXTERNAL_STORAGE
                        //CALL_PHONE
                }, RequestPermissionCode);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadFineLoctionPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                  /*
                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadAccountPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadSmsReadPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;*/
                    boolean WriteExternalStoragePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //boolean CallPhonePermission = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    if (CameraPermission && ReadFineLoctionPermission && /*ReadContactsPermission && ReadPhoneStatePermission && ReadAccountPermission && ReadSmsReadPermission &&*/ WriteExternalStoragePermission /*&& CallPhonePermission*/) {
                        //Toast.makeText(_act, "Permission has been Successfully Granted", Toast.LENGTH_LONG).show();

                 /*       Thread timer = new Thread() {
                            public void run() {
                                try {

                                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                                    // rlIcon.startAnimation(animation2);

                                    animation2.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            Toast.makeText(SplashActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {

                                            deleteCache(SplashActivity.this);
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {

                                        }
                                    });

                                } finally {
                                }
                                if (islogin) {


                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    startActivityForResult(intent, 1);
                                    SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();

                                } else {


                                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                                    startActivityForResult(intent, 1);
                                    SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();
                                }

                            }
                        };
                        timer.start();*/
                        // webServiceVersion();

                        Intent intent = new Intent(SplashActivity.this, SplashTwoActivity.class);
                        startActivityForResult(intent, 1);
                        SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                        finish();

                    } else {
                        Toast.makeText(SplashActivity.this, "Permission has been Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
 /*
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS)*/
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED  /*&&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FifthPermissionResult == PackageManager.PERMISSION_GRANTED*/ &&
                SixthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

/*    private void webServiceVersion() {


        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTGetVersion("1");

        result.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {

                    output = response.body().string();
                    Log.d("mytag", "onResponse: " + output);
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        //String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            JSONObject jsonObject1 = jsonObject.getJSONObject("version");
                            //String version_code = jsonObject.getString(IConstant.SP_VERSION_CODE);
                            // String version_name = jsonObject.getString("version_name");
                            if (Integer.parseInt(versionCode) < jsonObject1.getInt(IConstant.SP_VERSION_CODE)) {
                                //versionFlag = false;
                                UpdateVersion();
                                return;
                            } else {
                                //versionFlag = true;
                                if (islogin) {

                                    Intent intent = new Intent(_act, MainActivity.class);
                                    startActivityForResult(intent, 1);
                                    _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();

                                } else {

                                *//*    Intent intent = new Intent(_act, LoginActivity.class);
                                    startActivityForResult(intent, 1);
                                    _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();*//*
                                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                                    startActivityForResult(intent, 1);
                                    SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
                                    finish();
                                }
                            }

                            // equal to not get
                            // mismatch asel tr get


                            // JSONArray jsonArrayBanners = jsonObject.getJSONArray("banners");

                        } else {
                            //Toast.makeText(_act, stringMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(_act, "Technical Error", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}