package com.wind.anuroopjodi.InsideActivities.Fragmentsss;

import static android.view.View.GONE;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wind.anuroopjodi.Adapter.MyMatchesListActivityAdapter;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.InsideActivities.DashboardActivity;
import com.wind.anuroopjodi.Object.ListProfileObject;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.CheckNetwork;
import com.wind.anuroopjodi.my_library.MyConfig;

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


public class NewProfileFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "intent123";
    //private ArrayList<ListProfileObject> listProfileObjectArrayList = new ArrayList<>();
    private ArrayList<ListProfileObject> filteredlistProfileObjectArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private MyMatchesListActivityAdapter mAdapter;
    private LinearLayout noRecordLayout, noConnectionLayout;
    private ProgressBar progressView, progressBar_endless;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_count = 1, remainingCount = 0;
    private  int shipFlag = 1;
    private Button btnRetry;
    //public static final String IMAGE_URL = "http://annadata.windhans.in/";
    private View retView;
    private Bundle bundle;
    private SearchView mSearchView;
    private String profile_path="";
    private Handler mHandler;
    private String query = "";
    private ImageView iv_gif_image;
    private EditText edt_ship_code;
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    WindowManager.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    FloatingActionButton floatingbtn_add;
    public NewProfileFragment() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((DashboardActivity) getActivity()).setActionBarTitle("Home");
        View root = inflater.inflate(R.layout.fragment_new_profiles, container, false);
        noRecordLayout = (LinearLayout) root.findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) root.findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) root.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_my_matches_list);
        progressView = (ProgressBar) root.findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) root.findViewById(R.id.progressBar_endless);

        return root;
    }

    private void getMyMatches() {
        progressDialog.show();

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        Log.d("USERID", "USERID: "+SharedPref.getPrefs(getContext(), IConstant.USER_ID));

        String gender = "Male";

        if (gender.equals(SharedPref.getPrefs(getActivity(), IConstant.USER_GENDER))){
            gender = "Female";
        }
        Log.d(TAG, "getMyMatches: "+ gender);
        Log.d(TAG, "getMyMatches12: "+  SharedPref.getPrefs(getActivity(), IConstant.USER_GENDER));

        final Call<ResponseBody> result = api.matchingProfiles(
                gender,
                "21",
                "91",
                "1",
                "36",
                "1",
                "",
                "",
                "",
                SharedPref.getPrefs(getContext(), IConstant.USER_ID),
                "",
                "",
                page_count
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag12", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");
                    String reason = jsonObject.getString("reason");

                    if (res) {
                        remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse:12333 " + remainingCount);

                        profile_path = jsonObject.getString("user_profile_path");
                        JSONArray jsonArray = jsonObject.getJSONArray("profiles");
                        Log.d(TAG, "onResponse:12333 " + jsonArray.length());
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                               // listProfileObjectArrayList.add(new ListProfileObject(obj,profile_path));
                               /* if(obj.getString("photoApproved").equals("1")) {*/
                                    filteredlistProfileObjectArrayList.add(new ListProfileObject(obj, profile_path));
                                /*}*/
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        //Helper_Method.showDialog(getContext(), reason);
                        Toast.makeText(getContext(), ""+reason, Toast.LENGTH_SHORT).show();
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
                    mAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);


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
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    public void check_connection() {
        if (CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);



        mAdapter = new MyMatchesListActivityAdapter(filteredlistProfileObjectArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition
                            = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION &&
                            lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1
                            && filteredlistProfileObjectArrayList.size() > 9) {

                        Log.d(TAG, "remainingCount: "+remainingCount);
                        Log.d(TAG, "page_count: "+page_count);
                        Log.d(TAG, "lastVisibleItemPosition: "+lastVisibleItemPosition);

                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getMyMatches();
                        }
                    }
                }
            }
        });


        //listProfileObjectArrayList.clear();
        filteredlistProfileObjectArrayList.clear();
        mAdapter.notifyDataSetChanged();
        progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        getMyMatches();


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
        if (filteredlistProfileObjectArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                swipeRefreshLayout.setRefreshing(true);
                filteredlistProfileObjectArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                getMyMatches();
                //swipe=true;
            }
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            getMyMatches();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        page_count=1;
        check_connection();
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
        @POST(MyConfig.BrahminMatrimony + "/app_viewed_matches")
        public Call<ResponseBody> matchingProfiles(
                @Field("userId") String userId
        );
    }
}