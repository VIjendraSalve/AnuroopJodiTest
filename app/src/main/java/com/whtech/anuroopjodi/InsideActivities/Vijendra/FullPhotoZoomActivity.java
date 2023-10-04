package com.whtech.anuroopjodi.InsideActivities.Vijendra;

import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.R;

public class FullPhotoZoomActivity extends BaseActivity {

    private ImageView iv_photo;
    private RelativeLayout ivMainImage;
    private String imagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_photo_zoom);
        imagePath = getIntent().getStringExtra(IConstant.UserImage);
        toolbar();
        init();
    }

    private void toolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);

        toolbar_title.setText("Photo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    private void init() {
        iv_photo = findViewById(R.id.iv_photo);
        ivMainImage = findViewById(R.id.ivMainImage);

       /* DisplayMetrics metricscard = getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
        iv_photo.getLayoutParams().height = (int) (cardheight / 5.5);
        iv_photo.getLayoutParams().width = (int) (cardwidth / 2.5);
        ivMainImage.getLayoutParams().width = (int) (cardwidth / 1);*/

        Log.d("Image", "init: "+imagePath);
        Glide.with(FullPhotoZoomActivity.this)
                .load(imagePath)
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(iv_photo);
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
        if (Integer.parseInt(Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}