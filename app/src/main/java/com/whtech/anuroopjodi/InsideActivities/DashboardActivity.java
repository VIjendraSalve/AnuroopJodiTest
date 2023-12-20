package com.whtech.anuroopjodi.InsideActivities;

import android.Manifest;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.InitialActivities.ChangePasswordActivity;
import com.whtech.anuroopjodi.InitialActivities.LoginActivity;
import com.whtech.anuroopjodi.InitialActivities.ProfileActivity;
import com.whtech.anuroopjodi.InsideActivities.Fragmentsss.HomeFragment;
import com.whtech.anuroopjodi.InsideActivities.Fragmentsss.NewProfileFragment;
import com.whtech.anuroopjodi.InsideActivities.Fragmentsss.SearchFragment;
import com.whtech.anuroopjodi.InsideActivities.Fragmentsss.ServicesFragment;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.ActivityInformation;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.InterestSentListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyBlockListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyChatListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyConnectionListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyMatchesListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyPreferencesListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyProfileViewedMemberListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyRequestListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyShortlistedListActivity;
import com.whtech.anuroopjodi.Notification.ActivityNotification;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.Constants;
import com.whtech.anuroopjodi.my_library.MyConfig;
import com.whtech.anuroopjodi.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Activity _act;
    private String version = "0.0";
    private Toolbar toolbar;
    private Bundle bundle;
    private CircleImageView iv_userImage;
    private TextView tvName, tvPackage;
    private ImageView iv_notification;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.action_home:
                    bundle = new Bundle();
                    NewProfileFragment newProfilesFragment = new NewProfileFragment();
                    newProfilesFragment.setArguments(bundle);
                    changeFragment(newProfilesFragment, "New");
                    return true;

                case R.id.action_profile:
                    bundle = new Bundle();
                    HomeFragment nearByFragment = new HomeFragment();
                    nearByFragment.setArguments(bundle);
                    changeFragment(nearByFragment, "Home");
                    return true;
                case R.id.action_search:
                    bundle = new Bundle();
                    SearchFragment searchFragment = new SearchFragment();
                    searchFragment.setArguments(bundle);
                    changeFragment(searchFragment, "Search");
                    return true;
                case R.id.action_services:
                    bundle = new Bundle();
                    ServicesFragment sendFragment = new ServicesFragment();
                    sendFragment.setArguments(bundle);
                    changeFragment(sendFragment, "Service");
                    return true;
                case R.id.action_chat:
                    Intent intent = new Intent(_act, MyChatListActivity.class);
                    startActivity(intent);
                    return true;
               /* case R.id.action_notification:
                    bundle = new Bundle();
                    ShareFragment shareFragment = new ShareFragment();
                    shareFragment.setArguments(bundle);
                    changeFragment(shareFragment, "Notification");
                    return true;*/

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryFullDark));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.white)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimaryFullDark), PorterDuff.Mode.SRC_ATOP);

        iv_notification = toolbar.findViewById(R.id.iv_notification);
        iv_notification.setVisibility(View.VISIBLE);

        _act = DashboardActivity.this;


        iv_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ActivityNotification.class);
                startActivity(intent);
            }
        });

     /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(DashboardActivity.this);
        //TextView textViewMob = findViewById(R.id.textViewMob);
        View headerView = navigationView.getHeaderView(0);
        // ivProfileImage = headerView.findViewById(R.id.ivProfileImage);
        //tvVersionCode = headerView.findViewById(R.id.tvVersionCode);
        //tvVersionCode.setText("Version : " + version);
        tvPackage = headerView.findViewById(R.id.tvPackage);
        tvName = headerView.findViewById(R.id.tvName);
        iv_userImage = headerView.findViewById(R.id.iv_userImage);
        iv_userImage.setVisibility(View.VISIBLE);
        bundle = new Bundle();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationBottom);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.action_home);

        NewProfileFragment newProfilesFragment = new NewProfileFragment();
        newProfilesFragment.setArguments(bundle);
        changeFragment(newProfilesFragment, "New");

        //getVersionDetail();
        Log.d("ProfileImagePath", "onCreate: " + SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO));
        Glide.with(getApplicationContext())
                .load(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO))
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(iv_userImage);

        tvName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        iv_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        MenuItem itemProfilePic = menu.getItem(0);
        //View view = findViewById(R.id.action_profile_pic);

        if (SharedPref.getPrefs(DashboardActivity.this, IConstant.PROFILE_PHOTO) != null) {
           /* Glide.with(this)
                    .load(Uri.parse(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath)
                            + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO)))
                    .into(itemProfilePic);*/
            if (itemProfilePic.getItemId() == R.id.action_profile) {
                ImageView imageView = new ImageView(DashboardActivity.this);
                imageView.setMaxHeight(18);
                imageView.setMaxWidth(18);
                //imageView.setImageResource(R.drawable.barchart32);
                Glide.with(this)
                        .load(Uri.parse(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath)
                                + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO)))
                        .into(imageView);
                itemProfilePic.setActionView(imageView);
            }

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setMessage("Are you sure you want to exit?")
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

    private void changeFragment(Fragment fm, String TagSome) {
        /* android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();*/
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fm, TagSome);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

//            Intent intent = new Intent( PreschoolOwnerDashboardActivity.this,PreschoolOwnerDashboardActivity.class);
//            startActivity(intent);


        } /*else if (id == R.id.nav_cart) {
            Intent intent = new Intent(DashboardActivity.this, MyCartActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_wish_list) {

            Intent intent = new Intent(DashboardActivity.this, WishListActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_user_enroll) {

            Intent intent = new Intent(DashboardActivity.this, UserEnrollmentActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notification) {

            Intent intent = new Intent(_act, ActivityNotification.class);
            startActivity(intent);
        } else if (id == R.id.nav_instruction) {

            Intent intent = new Intent(_act, InstructionsActivity.class);
            _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
            startActivity(intent);

        } else if (id == R.id.nav_my_address) {
            Intent intent = new Intent(_act, AddressListActivity.class);
            intent.putExtra("Activity", "Dashboard");
            _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
            startActivity(intent);

        } /*else if (id == R.id.nav_my_order) {
            Intent intent = new Intent(_act, MyOrderActivity.class);
            // intent.putExtra("Setting_Id", "2");
            startActivity(intent);
            _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);
        } */ else if (id == R.id.nav_chat) {
            Intent intent = new Intent(_act, MyChatListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_preferences) {
            Intent intent = new Intent(_act, MyPreferencesListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_requests) {
            Intent intent = new Intent(_act, MyRequestListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_interest_sent) {
            Intent intent = new Intent(_act, InterestSentListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_shortlisted) {
            Intent intent = new Intent(_act, MyShortlistedListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_my_matches) {
            Intent intent = new Intent(_act, MyMatchesListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_my_connections) {
            Intent intent = new Intent(_act, MyConnectionListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_who_viewed_my_profile) {
            Intent intent = new Intent(_act, MyProfileViewedMemberListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_change_pass) {
            Intent intent = new Intent(_act, ChangePasswordActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_block_profile) {
            Intent intent = new Intent(_act, MyBlockListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(_act, ActivityInformation.class);
            intent.putExtra("title", "About Us");
            intent.putExtra("url", "https://anuroopjodi.com/Backup2022/app_about_us");
            startActivity(intent);
            //Toast.makeText(_act, "Coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_term_condition) {
            Intent intent = new Intent(_act, ActivityInformation.class);
            intent.putExtra("title", "Terms and Conditions");
            intent.putExtra("url", "https://anuroopjodi.com/Backup2022/app_term_condition_us");
            startActivity(intent);
            //Toast.makeText(_act, "Coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(_act, ActivityInformation.class);
            intent.putExtra("title", "Contact Us");
            intent.putExtra("url", "https://anuroopjodi.com/Backup2022/app_contact_us");
            startActivity(intent);
            //Toast.makeText(_act, "Coming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {

            // showToast("Coming Soon!!!");
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at:" + getResources().getString(R.string.app_url) + "\n\n");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            //Helper_Method.intentActivity_animate(DashboardActivity.this, FriendsActivity.class, false);


        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(_act)
                    .setTitle("Logout?")
                    .setMessage("Are you sure you want to Logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            SharedPref.clearPref(_act);
                            Intent i = new Intent(_act, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();

                        }
                    }).create().show();

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("User_Profile");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_notify:
                Intent intent = new Intent(DashboardActivity.this, ActivityNotification.class);
                startActivity(intent);
                return true;

            case R.id.action_profile:
                Intent intent2 = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarTitle(String title) {
        // getSupportActionBar().setTitle(title);
        if (title.equalsIgnoreCase("Home")) {
            toolbar.setTitle(title);
        } else {
            toolbar.setTitle(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Values", "PROFILE_PHOTO_STATUS: " + SharedPref.getPrefs(DashboardActivity.this, IConstant.PROFILE_PHOTO_STATUS).equals("0"));
        Log.d("Values", "AADHAR_PHOTO_STATUS: " + SharedPref.getPrefs(DashboardActivity.this, IConstant.AADHAR_PHOTO_STATUS).equals("0"));
        webCallDashboard();
        /*if (SharedPref.getPrefs(DashboardActivity.this, IConstant.PROFILE_PHOTO_STATUS).equals("0")) {
            Toast.makeText(_act, "Please Update Profile Photo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (SharedPref.getPrefs(DashboardActivity.this, IConstant.AADHAR_PHOTO_STATUS).equals("0")) {
            Toast.makeText(_act, "Please Update Adhar Card Photo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        }*/

        Glide.with(getApplicationContext())
                .load(SharedPref.getPrefs(_act, IConstant.UserProfileImagePath) + SharedPref.getPrefs(_act, IConstant.PROFILE_PHOTO))
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(iv_userImage);
    }

    public void getVersionDetail() {
        NearByVehicleAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(NearByVehicleAPI.class);
        Call<ResponseBody> result = api.getVersionDetail("1");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";

                try {
                    output = response.body().string();
                    Log.d("my_tag7777", "onResponse: " + output);

                    JSONObject jsonObject = new JSONObject(output);
                    JSONObject object = jsonObject.getJSONObject("version");

                    int version_code = object.getInt("version_code");
                    int currentVersion = DashboardActivity.this.getPackageManager().getPackageInfo(DashboardActivity.this.getPackageName(), 0).versionCode;
                    Log.d("my_tag7777", "onResponse: " + currentVersion);
                    if (currentVersion < version_code) {
                        versionCodeAlert();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void versionCodeAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setTitle("Get the Latest Version!");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("A new version of Bramhsakhi is available for Update.Please install to avoid inconvenience.");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setPositiveButton(R.string.update_app, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://market.android.com/details?id=com.wht.brahminmatrimonialapp"));
                startActivity(updateIntent);
            }
        });

      /*  alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
*/
        alertDialog.show();
    }

    private void webCallDashboard() {
        Log.d("Gender12", "webCallDashboard: " + SharedPref.getPrefs(_act, IConstant.USER_GENDER));
        Log.d("Gender12", "webCallDashboard: " + SharedPref.getPrefs(_act, IConstant.USER_ID));
        //Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTAppDashboard(
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                SharedPref.getPrefs(_act, IConstant.USER_GENDER)
        );
        //Call<ResponseBody> result = api.POSTAppDashboard("239", "Male");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        // Helper_Method.dismissProgessBar();
                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {


                            /*tv_my_connections_count.setText(jsonObject.getString("my_connection_count"));
                            tv_my_shortlisted_count.setText(jsonObject.getString("shortlisted_count"));
                            tv_myrequest_count.setText(jsonObject.getString("request_received_count"));*/

                            Shared_Preferences.setPrefs(_act, Constants.ConnectionCount, jsonObject.getString("my_connection_count"));
                            Shared_Preferences.setPrefs(_act, Constants.ShortlistCount, jsonObject.getString("shortlisted_count"));
                            Shared_Preferences.setPrefs(_act, Constants.RequestReceivedCount, jsonObject.getString("request_received_count"));

                            Shared_Preferences.setPrefs(_act, Constants.IsChat, jsonObject.getString("is_chat"));
                            Shared_Preferences.setPrefs(_act, Constants.IsPlanExpired, jsonObject.getString("plan_expired"));


                            if (jsonObject.getString("plan_expired").equalsIgnoreCase("1")) {
                                tvPackage.setVisibility(View.GONE);
                                Shared_Preferences.setPrefs(_act, Constants.TotalMobileViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.TotalEmailViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.UsedMobileViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.UsedEmailViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.PACKAGE_NAME, "No Package Subscribe");
                                Shared_Preferences.setPrefs(_act, Constants.PACKAGE_ID, "0");

                            } else {

                                JSONObject packageInfo = jsonObject.getJSONObject("package_details");
                                String totalTime = packageInfo.getString("expired_date");
                                Log.d("StringTime", "StringTime: " + totalTime);
                                /*long currTime = System.currentTimeMillis();
                                long daysRemaining = calculateDaysRemaining(totalTime);*/

                                /*if (daysRemaining <= 400) {
                                    showSubscriptionExpiryNotification(daysRemaining);
                                    Toast.makeText(_act, "Expires in " + daysRemaining + " Days", Toast.LENGTH_LONG).show();
                                } else {

                                }*/

                                Shared_Preferences.setPrefs(_act, Constants.TotalMobileViews, packageInfo.getString("mobile_views_allocated"));
                                Shared_Preferences.setPrefs(_act, Constants.TotalEmailViews, packageInfo.getString("email_views_allocated"));
                                Shared_Preferences.setPrefs(_act, Constants.UsedMobileViews, packageInfo.getString("view_mobile_used"));
                                Shared_Preferences.setPrefs(_act, Constants.UsedEmailViews, packageInfo.getString("view_email_used"));
                                Shared_Preferences.setPrefs(_act, Constants.PACKAGE_NAME, packageInfo.getString("package_name"));
                                Shared_Preferences.setPrefs(_act, Constants.PACKAGE_ID, packageInfo.getString("packageId"));

                                tvPackage.setText(Helper_Method.toTitleCase(packageInfo.getString("package_name") + " Package"));
                            }


                            // Helper_Method.dismissProgessBar();
                        } else {
                            //showToast(stringMsg);
                            // Helper_Method.dismissProgessBar();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //Helper_Method.dismissProgessBar();

                } finally {

                    //category_id = sliderItemList.get(0).id;
                    //page_count = 1;
                    // webServiceCallProductFirstCall(keyWord, category_id);
                    // Helper_Method.dismissProgessBar();

                }
            }


            private long calculateDaysRemaining(String inputDate) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(inputDate);
                    Date currentDate = new Date();
                    long timeDifference = date.getTime() - currentDate.getTime();
                    return timeDifference / (24 * 60 * 60 * 1000);

                } catch (ParseException e) {
                    e.printStackTrace();
                    return -1;
                }
            }


            private void showSubscriptionExpiryNotification(long remainingDays) {
                // Create and show the notification
                Context context = getApplicationContext();
                createNotificationChannel(context);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "subscription_channel")
                        .setSmallIcon(R.drawable.applogonew)
                        .setContentTitle("Subscription Expiring Soon")
                        .setContentText("Your subscription will expire in " + remainingDays + " days. Renew now!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(1, builder.build());
            }

            private void createNotificationChannel(Context context) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Subscription Channel";
                    String description = "Channel for subscription notifications";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel channel = new NotificationChannel("subscription_channel", name, importance);
                    channel.setDescription(description);

                    NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                if (t instanceof NetworkErrorException)
                    Helper_Method.warnUser(_act, "Network Error", getString(R.string.error_network), true);
                else if (t instanceof IOException)
                    Helper_Method.warnUser(_act, "Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ServerError)
                    //   Helper_Method.warnUser(_act, "Server Error", getString(R.string.error_server), true);
                else if (t instanceof ConnectException)
                    Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                    //else if (t instanceof ConnectException)
                    //Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                else if (t instanceof TimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else if (t instanceof SocketTimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else
                    Helper_Method.warnUser(_act, "Unknown Error", getString(R.string.error_something_wrong), true);

            }
        });
    }


    private interface NearByVehicleAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/version")
        Call<ResponseBody> getVersionDetail(@Field("device") String device);

    }

}