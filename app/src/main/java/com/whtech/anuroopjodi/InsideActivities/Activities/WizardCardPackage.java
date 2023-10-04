package com.whtech.anuroopjodi.InsideActivities.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.whtech.anuroopjodi.R;

public class WizardCardPackage extends AppCompatActivity
{

    private static final int MAX_STEP = 4;

    private ViewPager viewPager;
//    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "Diamond",
            "Silver",
            "Gold",
            "Platinum"
    };
    private String about_description_array[] = {
            "Diamond",
            "Silver",
            "Gold",
            "Platinum",
    };
//    private int about_images_array[] = {
//            R.drawable.img_wizard_1,
//            R.drawable.img_wizard_2,
//            R.drawable.img_wizard_3,
//            R.drawable.img_wizard_4
//    };
//
//    private int bg_images_array[] = {
//            R.drawable.image_15,
//            R.drawable.image_10,
//            R.drawable.image_3,
//            R.drawable.image_12
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizardpackage);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        bottomProgressDots(0);



    }

    private void bottomProgressDots(int i) {
    }
}
