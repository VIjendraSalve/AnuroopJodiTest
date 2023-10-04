package com.whtech.anuroopjodi.InsideActivities.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.whtech.anuroopjodi.BaseActivity;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.DateTimeFormat;

public class SuccessfulMarriageDetailsActivity extends BaseActivity {

    private String title, desc, image, marriagedate;
    public TextView tvSuccessStoryTitle, tvSuccessStoryDescription, tvDate;
    public ImageView ivCoupleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_marriage_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        image = getIntent().getStringExtra("image");
        marriagedate = getIntent().getStringExtra("marriagedate");

        init();

    }

    private void init() {

        tvSuccessStoryTitle = findViewById(R.id.tvSuccessStoryTitle);
        tvSuccessStoryDescription = findViewById(R.id.tvSuccessStoryDescription);
        tvDate = findViewById(R.id.tvDate);
        ivCoupleImage = findViewById(R.id.ivCoupleImage);

        tvSuccessStoryTitle.setText(title);
        tvSuccessStoryDescription.setText(Html.fromHtml(desc));
        tvDate.setText("Marriage  Date : "+ DateTimeFormat.getDate(marriagedate));
        //holder.ivCoupleImage.setImageResource(successStories.getPhoto());

        Glide.with(SuccessfulMarriageDetailsActivity.this)
                .load(image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions().placeholder(R.drawable.applogonew).error(R.drawable.applogonew).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivCoupleImage);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}