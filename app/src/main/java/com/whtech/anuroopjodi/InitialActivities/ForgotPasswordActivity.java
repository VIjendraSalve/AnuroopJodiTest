package com.whtech.anuroopjodi.InitialActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.MyConfig;
import com.whtech.anuroopjodi.my_library.MyValidator;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ForgotPasswordActivity extends BaseActivity {

    private TextView btnForgotPassword;
    private EditText etMobile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        toolbar();
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);

        init();


    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    private void sendPasswordToMobile(String MobileNumber) {
        progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.forgotPassword(
                MobileNumber
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

                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();

                    }

                    Toast.makeText(ForgotPasswordActivity.this, ""+jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();




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

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.BrahminMatrimony + "/app_forget_password")
        public Call<ResponseBody> forgotPassword(
                @Field("username") String username
        );
    }

    private void init() {

        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        etMobile = findViewById(R.id.etMobile);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyValidator.isValidMobile(etMobile)){
                    sendPasswordToMobile(etMobile.getText().toString());
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}