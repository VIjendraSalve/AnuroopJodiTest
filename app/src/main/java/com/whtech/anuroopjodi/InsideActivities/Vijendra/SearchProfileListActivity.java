package com.whtech.anuroopjodi.InsideActivities.Vijendra;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whtech.anuroopjodi.Adapter.SearchProfileListActivityAdapter;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Object.ListProfileObject;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.CheckNetwork;
import com.whtech.anuroopjodi.my_library.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static android.view.View.GONE;

public class SearchProfileListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "intent123";
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    WindowManager.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    FloatingActionButton floatingbtn_add;
    private ArrayList<ListProfileObject> listProfileObjectArrayList = new ArrayList<>();
    private ArrayList<ListProfileObject> filteredlistProfileObjectArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SearchProfileListActivityAdapter mAdapter;
    private LinearLayout noRecordLayout, noConnectionLayout;
    private ProgressBar progressView, progressBar_endless;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private int page_count = 1, remainingCount = 0;
    private int shipFlag = 1;
    private Button btnRetry;
    //public static final String IMAGE_URL = "http://annadata.windhans.in/";
    private View retView;
    private Bundle bundle;
    private SearchView mSearchView;
    private String profile_path = "";
    private Handler mHandler;
    private String query = "";
    private ImageView iv_gif_image;
    private EditText edt_ship_code;
    private String gender="", ageFrom = "", ageTo = "", HeightFrom = "", heightTo = "", stateId = "", cityId = "",
            maritalStatus="",motherTounge = "", manglik="", physical="", education="", profession="", annualIncome="",
            eating="", drinking="", smokining="", searchType="", name="", number="", subcaste="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Search Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        noRecordLayout = (LinearLayout) findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
       // swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        //swipeRefreshLayout.setOnRefreshListener(this);

        progressView = (ProgressBar) findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) findViewById(R.id.progressBar_endless);
        searchType =  getIntent().getStringExtra(IConstant.SearchType);

        if(searchType.equals("0")) {

            gender = getIntent().getStringExtra(IConstant.Gender);
            ageFrom = getIntent().getStringExtra(IConstant.AgeFrom);
            ageTo = getIntent().getStringExtra(IConstant.AgeTo);
            HeightFrom = getIntent().getStringExtra(IConstant.HeightFrom);
            heightTo = getIntent().getStringExtra(IConstant.HeightTo);
            stateId = getIntent().getStringExtra(IConstant.StateID);
            cityId = getIntent().getStringExtra(IConstant.CityID);
            maritalStatus = getIntent().getStringExtra(IConstant.MaritalStatus);
            motherTounge = getIntent().getStringExtra(IConstant.MotherTounge);
            name = getIntent().getStringExtra(IConstant.Name);
            number = getIntent().getStringExtra(IConstant.Number);
        }else {
            gender = getIntent().getStringExtra(IConstant.Gender);
            ageFrom = getIntent().getStringExtra(IConstant.AgeFrom);
            ageTo = getIntent().getStringExtra(IConstant.AgeTo);
            HeightFrom = getIntent().getStringExtra(IConstant.HeightFrom);
            heightTo = getIntent().getStringExtra(IConstant.HeightTo);
            stateId = getIntent().getStringExtra(IConstant.StateID);
            cityId = getIntent().getStringExtra(IConstant.CityID);
            maritalStatus = getIntent().getStringExtra(IConstant.MaritalStatus);
            motherTounge = getIntent().getStringExtra(IConstant.MotherTounge);
            manglik = getIntent().getStringExtra(IConstant.ManglikID);
            physical = getIntent().getStringExtra(IConstant.PhysicalStatusId);
            education = getIntent().getStringExtra(IConstant.EducationID);
            profession = getIntent().getStringExtra(IConstant.ProfessionID);
            annualIncome = getIntent().getStringExtra(IConstant.AnnualIncome);
            eating = getIntent().getStringExtra(IConstant.EatingId);
            drinking = getIntent().getStringExtra(IConstant.DrinkingId);
            smokining = getIntent().getStringExtra(IConstant.SmokingId);
            name = getIntent().getStringExtra(IConstant.Name);
            number = getIntent().getStringExtra(IConstant.Number);
            subcaste = getIntent().getStringExtra(IConstant.SUBCASTE);
        }

    }

    private void getMyMatches() {
        progressDialog.show();
        listProfileObjectArrayList.clear();
        filteredlistProfileObjectArrayList.clear();
        Log.d(TAG, "getMyMatches: test by vij "+gender);
        Log.d(TAG, "getMyMatches: test by vij "+ageFrom);
        Log.d(TAG, "getMyMatches: test by vij "+ageTo);
        Log.d(TAG, "getMyMatches: test by vij "+HeightFrom);
        Log.d(TAG, "getMyMatches: test by vij "+heightTo);
        Log.d(TAG, "getMyMatches: test by vij "+maritalStatus);
        Log.d(TAG, "getMyMatches: test by vij "+cityId);
        Log.d(TAG, "getMyMatches: test by vij "+stateId);
        Log.d(TAG, "getMyMatches: test by vij "+motherTounge);
        //filteredlistProfileObjectArrayList.clear();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.matchingProfiles(
                gender,
                ageFrom,
                ageTo,
                HeightFrom,
                heightTo,
                maritalStatus,
                cityId,
                stateId,
                motherTounge,
                SharedPref.getPrefs(SearchProfileListActivity.this, IConstant.USER_ID),
                number,
                name,
                page_count

        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag1232", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    if (res) {
                        remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse: " + remainingCount);
                        profile_path = jsonObject.getString("user_profile_path");
                        JSONArray jsonArray = jsonObject.getJSONArray("profiles");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                listProfileObjectArrayList.add(new ListProfileObject(obj, profile_path));
                                filteredlistProfileObjectArrayList.add(new ListProfileObject(obj, profile_path));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressDialog.dismiss();
                        progressView.setVisibility(GONE);
                        progressBar_endless.setVisibility(GONE);
                        noRecordLayout.setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();


                    if (filteredlistProfileObjectArrayList.isEmpty()) {
                        noRecordLayout.setVisibility(View.VISIBLE);
                    } else
                        noRecordLayout.setVisibility(GONE);
                    progressView.setVisibility(GONE);
                    progressBar_endless.setVisibility(GONE);
                   /* mAdapter = new SearchProfileListActivityAdapter(filteredlistProfileObjectArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);*/
                    mAdapter.notifyDataSetChanged();
                //    swipeRefreshLayout.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar_endless.setVisibility(GONE);
                progressView.setVisibility(GONE);
               // swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    private void getMyMatchesAdvance() {
        progressDialog.show();
        //filteredlistProfileObjectArrayList.clear();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.matchingProfilesAdvance(
                gender,
                ageFrom,
                ageTo,
                HeightFrom,
                heightTo,
                maritalStatus,
                cityId,
                stateId,
                motherTounge,
                manglik,
                physical,
                education,
                profession,
                annualIncome,
                eating,
                drinking,
                smokining,
                number,
                name,
                subcaste,
                SharedPref.getPrefs(SearchProfileListActivity.this, IConstant.USER_ID),
                page_count
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponse11sdgsfgdfsgf: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    if (res) {
                       /* remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse: " + remainingCount);*/
                        profile_path = jsonObject.getString("user_profile_path");
                        JSONArray jsonArray = jsonObject.getJSONArray("profiles");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                listProfileObjectArrayList.add(new ListProfileObject(obj, profile_path));
                                filteredlistProfileObjectArrayList.add(new ListProfileObject(obj, profile_path));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        progressDialog.dismiss();
                        progressView.setVisibility(GONE);
                        progressBar_endless.setVisibility(GONE);
                        noRecordLayout.setVisibility(View.VISIBLE);
                    }
                    progressDialog.dismiss();


                    if (filteredlistProfileObjectArrayList.isEmpty()) {
                        noRecordLayout.setVisibility(View.VISIBLE);
                    } else
                        noRecordLayout.setVisibility(GONE);
                    progressView.setVisibility(GONE);
                    progressBar_endless.setVisibility(GONE);
                    mAdapter = new SearchProfileListActivityAdapter(filteredlistProfileObjectArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                   // swipeRefreshLayout.setRefreshing(false);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar_endless.setVisibility(GONE);
                progressView.setVisibility(GONE);
                //swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(getApplicationContext()))  //if connection available
        {
            noConnectionLayout.setVisibility(GONE);
            noRecordLayout.setVisibility(GONE);
            init();
        } else {
          /*  Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.internet_not_avilable,
                    Snackbar.LENGTH_INDEFINITE).setAction("RETRY",
                    v -> check_connection()).show();*/
            noConnectionLayout.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        mHandler = new Handler();
        //bundle = getActivity().getIntent().getExtras();
        //    exhibitorsPojo = bundle.getParcelable(Constants.TOUR_PLAN_DATA);
        progressDialog = new ProgressDialog(SearchProfileListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_matches_list);
        mAdapter = new SearchProfileListActivityAdapter(filteredlistProfileObjectArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && filteredlistProfileObjectArrayList.size() > 9) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            if(searchType.equals("0"))
                                getMyMatches();
                            else
                                getMyMatchesAdvance();
                        }
                    }
                }
            }
        });


        //listProfileObjectArrayList.clear();
       // filteredlistProfileObjectArrayList.clear();
        mAdapter.notifyDataSetChanged();
        progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        if(searchType.equals("0"))
            getMyMatches();
        else
            getMyMatchesAdvance();


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetry) {
            //orderList.clear();
            check_connection();
        }
    }

    @Override
    public void onRefresh() {
        if (listProfileObjectArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                //swipeRefreshLayout.setRefreshing(true);
                listProfileObjectArrayList.clear();
                filteredlistProfileObjectArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                if(searchType.equals("0"))
                    getMyMatches();
                else
                    getMyMatchesAdvance();
                //swipe=true;
            }
        } else {
            //swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            if(searchType.equals("0"))
                getMyMatches();
            else
                getMyMatchesAdvance();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        page_count = 1;
        check_connection();
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

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_simple_search")
        public Call<ResponseBody> matchingProfiles(
                @Field("gender") String gender,
                @Field("age_from") String age_from,
                @Field("age_to") String age_to,
                @Field("height_from") String height_from,
                @Field("height_to") String height_to,
                @Field("marital_status") String marital_status,
                @Field("city") String city,
                @Field("state") String state,
                @Field("mother_toungue") String mother_toungue,
                @Field("userId") String userId,
                @Field("mobile_no") String mobile_no,
                @Field("name") String name,
                @Field("page_no") int page_no
        );

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_advance_search")
        public Call<ResponseBody> matchingProfilesAdvance(
                @Field("gender") String gender,
                @Field("age_from") String age_from,
                @Field("age_to") String age_to,
                @Field("height_from") String height_from,
                @Field("height_to") String height_to,
                @Field("marital_status") String marital_status,
                @Field("city") String city,
                @Field("state") String state,
                @Field("mother_toungue") String mother_toungue,
                @Field("manglik") String manglik,
                @Field("physical_status") String physical_status,
                @Field("highest_education") String highest_education,
                @Field("occupation") String occupation,
                @Field("annual_income") String annual_income,
                @Field("eating") String eating,
                @Field("drinking") String drinking,
                @Field("smoking") String smoking,
                @Field("mobile_no") String mobile_no,
                @Field("name") String name,
                @Field("subcaste") String subcaste,
                @Field("userId") String userId,
                @Field("page_no") int page_no
        );

    }
}
