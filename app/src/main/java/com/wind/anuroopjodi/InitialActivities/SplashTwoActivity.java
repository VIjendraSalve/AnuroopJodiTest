package com.wind.anuroopjodi.InitialActivities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.InitialActivities.NewShowcase.IntroViewPagerAdapter;
import com.wind.anuroopjodi.InitialActivities.NewShowcase.ScreenItem;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.SignUpActivity;
import com.wind.anuroopjodi.R;

import java.util.ArrayList;
import java.util.List;

public class SplashTwoActivity extends BaseActivity {

    private Activity _act;
    private ConnectionDetector connectionDetector;

    private TextView tvTermsAndConditions;
    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    android.widget.Button btnNext;
    int position = 0;
    android.widget.Button btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();


        }


        setContentView(R.layout.activity_splash_two);


        _act = SplashTwoActivity.this;
        connectionDetector = ConnectionDetector.getInstance(this);


        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        //btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
    /*    String first = "<font color='#ffffff'>By Signing in you agree to our </font>";
        String second = "<font color='#D13816'>Terms And Conditions</font>";
        tvTermsAndConditions.setText(Html.fromHtml(first + second));*/
        String first = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_one) + "</font>";
        String second = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_terms_n_cond_two) + "</font>";
        String third = "<font color='#000000'>" + getResources().getString(R.string.lbl_and) + "</font>";
        String fourth = "<font color='#FA7A76'>" + getResources().getString(R.string.lbl_privacy_policy) + "</font>";
        String fifth = "<font color='#000000'>" + getResources().getString(R.string.lbl_terms_n_cond_three) + "</font>";
        tvTermsAndConditions.setText(Html.fromHtml(first + second + third + fourth + fifth));


        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("LEGACY", "Lot of Success stories of our happily married couples.", R.drawable.slide_one));
        mList.add(new ScreenItem("PRODUCT", "Complete your profile to get more attentions from your Life partner.", R.drawable.splash_new_girl));
        //mList.add(new ScreenItem("WORK", "Verified profile via photo id document and get more responses", R.drawable.slide_three));
        mList.add(new ScreenItem("WORK", "Communicate with Verified profiles and get more responses.", R.drawable.slide_three));
        //mList.add(new ScreenItem("DELIVERY", "Freshest Chicken & Nutritional Eggs Range now @ Your Door Step", R.drawable.slider_four));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listner

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size() - 1) { // when we rech to the last screen

                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }


            }
        });

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity(_act, LoginActivity.class, false);
            }
        });

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper_Method.intentActivity(_act, SignUpActivity.class, false);
            }
        });

//        findViewById(R.id.tvTakeTour).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Helper_Method.intentActivity(_act, IntroActivity.class, false);
//            }
//        });
    }

    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);


    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;


    }
}