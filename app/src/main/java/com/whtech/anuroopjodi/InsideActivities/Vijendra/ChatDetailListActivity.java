package com.whtech.anuroopjodi.InsideActivities.Vijendra;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whtech.anuroopjodi.Adapter.ChatDetailsListActivityAdapter;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Object.ChatDetails;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.CheckNetwork;
import com.whtech.anuroopjodi.my_library.Constants;
import com.whtech.anuroopjodi.my_library.MyConfig;
import com.whtech.anuroopjodi.my_library.Shared_Preferences;

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

public class ChatDetailListActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "intent123";
    private ArrayList<ChatDetails> chatDetailsArrayList = new ArrayList<>();
    private ArrayList<ChatDetails> filteredchatDetailsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ChatDetailsListActivityAdapter mAdapter;
    private LinearLayout noRecordLayout, noConnectionLayout;
    private ProgressBar progressView, progressBar_endless;
    // private SwipeRefreshLayout swipeRefreshLayout;
    private int page_count = 1, remainingCount = 0;
    private Button btnRetry, btn_add_contact;
    //public static final String IMAGE_URL = "http://annadata.windhans.in/";
    private View retView;
    private Bundle bundle;
    private SearchView mSearchView;
    private String branch = "", flag = "", name = "", city = "", state = "";
    private Handler mHandler;
    private String query = "", receiverId = "", senderID = "", receiverName = "", SenderName = "";
    private ImageView btn_replay;
    private Dialog dialog;
    private EditText edt_message;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detailas);

        noRecordLayout = (LinearLayout) findViewById(R.id.noRecordLayout);
        noConnectionLayout = (LinearLayout) findViewById(R.id.noConnectionLayout);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        // swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimaryDark));
        //swipeRefreshLayout.setOnRefreshListener(this);

        progressView = (ProgressBar) findViewById(R.id.progress_view);
        progressBar_endless = (ProgressBar) findViewById(R.id.progressBar_endless);


        receiverId = getIntent().getStringExtra(IConstant.ReceiverID);
        senderID = getIntent().getStringExtra(IConstant.SenderID);
        receiverName = getIntent().getStringExtra(IConstant.ReceiverName);
        SenderName = getIntent().getStringExtra(IConstant.SenderName);

        if (receiverId.equals(SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID))) {
            receiverId = senderID;
        } else {
            receiverId = receiverId;
        }

        toolbar();
        Log.d(TAG, "receiverId: " + receiverId);
        Log.d(TAG, "senderID: " + senderID);
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        Log.d(TAG, "UserID: "+SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID));
        Log.d(TAG, "receiverId: "+receiverId);
        Log.d(TAG, "SenderName: "+SenderName);
        Log.d(TAG, "receiverName: "+receiverName);

        if (SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID).equals(receiverId)) {
            toolbar_title.setText("" + receiverName);
        } else {
            toolbar_title.setText("" +SenderName );
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    private void getChatDetailList() {
        chatDetailsArrayList.clear();
        filteredchatDetailsArrayList.clear();
        Log.d(TAG, "getChatDetailList receiverId: " + receiverId);
        Log.d(TAG, "getChatDetailList senderID: " + senderID);
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getContact(
                SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID),
                receiverId
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag123", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("status");

                    // Log.d(TAG, "onResponse: " + object.getString("Message"));
                    if (res) {
                     /*   remainingCount = Integer.parseInt(jsonObject.getString("remaining"));
                        Log.d(TAG, "onResponse: " + remainingCount);*/
                        JSONArray jsonArray = jsonObject.getJSONArray("chatdata");
                        // Parsing json
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                chatDetailsArrayList.add(new ChatDetails(obj));
                                filteredchatDetailsArrayList.add(new ChatDetails(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        mAdapter = new ChatDetailsListActivityAdapter(filteredchatDetailsArrayList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mLayoutManager.setReverseLayout(true);
                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        //mLayoutManager.setStackFromEnd(true);
                        recyclerView.setLayoutManager(mLayoutManager);
                        //recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.smoothScrollToPosition(chatDetailsArrayList.size());
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        //recyclerView.scrollToPosition(filteredchatDetailsArrayList.size()-1);


                    } else {
                        Toast.makeText(ChatDetailListActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        progressView.setVisibility(GONE);
                        progressBar_endless.setVisibility(GONE);
                        noRecordLayout.setVisibility(View.VISIBLE);
                    }


                    if (filteredchatDetailsArrayList.isEmpty()) {
                        noRecordLayout.setVisibility(View.VISIBLE);
                    } else
                        noRecordLayout.setVisibility(GONE);
                    progressView.setVisibility(GONE);
                    progressBar_endless.setVisibility(GONE);
                    mAdapter.notifyDataSetChanged();
                    //swipeRefreshLayout.setRefreshing(false);


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
        progressDialog = new ProgressDialog(ChatDetailListActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);

       /* mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("my_tag", "onQueryTextChange: " + s);
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        query = s;
                        portList.clear();
                        mAdapter.notifyDataSetChanged();
                        page_count = 1;
                        remainingCount = 0;
                        progressView.setVisibility(View.VISIBLE);
                        getChatDetailList();

                    }
                }, 300);
                return false;
            }
        }) ;*/

       /* iv_gif_image = findViewById(R.id.iv_gif_image);
        Glide.with( PortListActivity.this )
                .load(R.drawable.giphy)
                //.load( "https://static1.squarespace.com/static/593191dde4fcb50f8656c7b8/t/5c40f28740ec9adbb47da3fd/1547760301129/AOG+Port.gif" )
                .into( iv_gif_image );*/


        btn_replay = (ImageView) findViewById(R.id.btn_send_message);
        edt_message = (EditText) findViewById(R.id.edt_message_to_send);


        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Shared_Preferences.getPrefs(ChatDetailListActivity.this, Constants.IsPlanExpired).equals("0")) {
                    if (Shared_Preferences.getPrefs(ChatDetailListActivity.this, Constants.IsChat).equals("1")) {
                        if (!edt_message.getText().toString().equals("")) {
                            AddType(edt_message.getText().toString().trim());
                        } else {
                            Toast.makeText(ChatDetailListActivity.this, getResources().getString(R.string.enter_message), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ChatDetailListActivity.this, "Please upgrade your package to chat with this person", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ChatDetailListActivity.this, "Your package is expired please upgrade your packge", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_ticket);
        mAdapter = new ChatDetailsListActivityAdapter(filteredchatDetailsArrayList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //recyclerView.scrollToPosition(filteredchatDetailsArrayList.size()-1);
        //recyclerView.setHasFixedSize(true);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && filteredchatDetailsArrayList.size() > 9) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getChatDetailList();
                        }
                    }
                }
            }
        });


        chatDetailsArrayList.clear();
        filteredchatDetailsArrayList.clear();
        mAdapter.notifyDataSetChanged();
        progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(GONE);
        getChatDetailList();

       /* recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter().getItemCount() != 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition >= recyclerView.getAdapter().getItemCount() - 1 && portList.size() > 9) {
                        if (remainingCount > 0) {
                            page_count++;
                            progressBar_endless.setVisibility(View.VISIBLE);
                            getChatDetailList();
                        }
                    }
                }
            }
        });

        portList.clear();
        mAdapter.notifyDataSetChanged();
        progressView.setVisibility(View.VISIBLE);
        progressBar_endless.setVisibility(View.GONE);
        getChatDetailList();*/
    }

    /*private void ReplayDialog() {
        dialog = new Dialog(ChatDetailListActivity.this);
        dialog.setContentView(R.layout.dialogue_add_replay);
        dialog.setCancelable(true);
        edt_message = (EditText) dialog.findViewById(R.id.edt_message);

        dialog.findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyValidator.isValidField(edt_message)){

                }else {
                    Toast.makeText(ChatDetailListActivity.this, getResources().getString(R.string.enter_message), Toast.LENGTH_SHORT).show();
                }
                //dialog.dismiss();

            }



        });


        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MaterialDialog; //style id
        dialog.show();



    }*/

    private void AddType(String message) {

        if (receiverId.equals(SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID))) {
            receiverId = senderID;
        } else {
            receiverId = receiverId;
        }

         progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        Call<ResponseBody> result = api.getData(
                SharedPref.getPrefs(ChatDetailListActivity.this, IConstant.USER_ID),
                receiverId,
                edt_message.getText().toString().trim()
        );


        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("status"));
                    String reason = jsonObject.getString("message");

                    if (res) {
                        edt_message.setText("");
                        mAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                        getChatDetailList();

                    } else {
                        //Toast.makeText(ChatDetailListActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                //progressDialog.dismiss();
            }
        });
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
        if (filteredchatDetailsArrayList.size() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition == 0) {
                //swipeRefreshLayout.setRefreshing(true);
                chatDetailsArrayList.clear();
                filteredchatDetailsArrayList.clear();
                mAdapter.notifyDataSetChanged();
                //page_count = dash1;count = dash1;
                page_count = 1;
                remainingCount = 0;
                //swipe=false;
                //getOrderList();
                getChatDetailList();
                //swipe=true;
            }
        } else {
            //swipeRefreshLayout.setRefreshing(false);
            progressBar_endless.setVisibility(GONE);
            remainingCount = 0;
            page_count = 1;
            //get_my_rides(2);
            getChatDetailList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        page_count = 1;
        check_connection();
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
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_getchartlist")
        public Call<ResponseBody> getContact(
                @Field("sender_id") String sender_id,
                @Field("receiver_id") String receiver_id
        );

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_addchart")
        public Call<ResponseBody> getData(
                @Field("sender_id") String sender_id,
                @Field("receiver_id") String receiver_id,
                @Field("message") String message
        );
    }
}
