package com.wind.anuroopjodi.InitialActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.InsideActivities.DashboardActivity;
import com.wind.anuroopjodi.R;
import com.wind.anuroopjodi.my_library.MyConfig;
import com.wind.anuroopjodi.my_library.MyValidator;
import com.wind.anuroopjodi.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ChangePasswordActivity extends BaseActivity {

    private TextView btnForgotPassword;
    private EditText etOldPass, etNewPass, etNewPassConfirm;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar();
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);

        init();


    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText("Change Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }



    private void changePassword() {
        progressDialog.show();

        Log.d("Test", "USER_ID: "+Shared_Preferences.getPrefs(ChangePasswordActivity.this, IConstant.USER_ID));
        Log.d("Test", "etOldPass: "+etOldPass.getText().toString());
        Log.d("Test", "etNewPass: "+etNewPass.getText().toString());

        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.changePassword(
                SharedPref.getPrefs(ChangePasswordActivity.this, IConstant.USER_ID),
                etOldPass.getText().toString(),
                etNewPass.getText().toString()
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

                        Intent intent = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();

                    }

                    Toast.makeText(ChangePasswordActivity.this, ""+jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
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
        @POST(MyConfig.BrahminMatrimony + "/app_change_password")
        public Call<ResponseBody> changePassword(
                @Field("user_id") String user_id,
                @Field("old_password") String old_password,
                @Field("new_password") String new_password
        );
    }

    private void init() {

        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);
        etNewPassConfirm = findViewById(R.id.etNewPassConfirm);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyValidator.isValidField(etOldPass) && MyValidator.isMatchPassword(etNewPass, etNewPassConfirm)){
                    changePassword();
                }
            }
        });

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

}