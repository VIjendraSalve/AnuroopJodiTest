package com.wind.anuroopjodi.InitialActivities.RegistrationSteps;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.R;

public class AboutDetailsActivity extends BaseActivity {

    private static final String TAG = "AboutDetailsActivity";
    private Activity _act;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_details);

        _act = AboutDetailsActivity.this;
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_update_profile));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity(_act, PackageActivity.class, false);

            }
        });


    }
}