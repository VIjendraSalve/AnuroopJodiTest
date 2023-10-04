package com.whtech.anuroopjodi.InsideActivities.Fragmentsss;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smarteist.autoimageslider.SliderView;
import com.whtech.anuroopjodi.Adapter.ShortlistedAdapter;
import com.whtech.anuroopjodi.Adapter.SliderAdapterExample;
import com.whtech.anuroopjodi.Adapter.SuccessStoryAdapter;
import com.whtech.anuroopjodi.Adapter.TypeMatchHomeAdapter;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.whtech.anuroopjodi.InsideActivities.DashboardActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.FeaturedProfileListActivity;
import com.whtech.anuroopjodi.InsideActivities.Vijendra.MyShortlistedListActivity;
import com.whtech.anuroopjodi.Object.ListProfileObject;
import com.whtech.anuroopjodi.Object.SliderItem;
import com.whtech.anuroopjodi.Object.SuccessStories;
import com.whtech.anuroopjodi.Object.TypeMatch;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.Constants;
import com.whtech.anuroopjodi.my_library.Shared_Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements ShortlistedAdapter.RefreshActivity {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    private TextView tvWelcomeTitle, tvName, tvMoreInformation, tvPackageInfo, tvMoreShortListedProfiles;
    private ImageView ivProfilePic;
    private TypeMatchHomeAdapter homeAdapter;
    private ArrayList<TypeMatch> typeMatchArrayList;
    //Slider Category
    private SliderView sliderView;
    private SliderAdapterExample adapter;
    private List<SliderItem> sliderItemList;
    private String sliders_path = null, user_profile_path = null;

    //Prefred Profile
    private ArrayList<ListProfileObject> shortlistedObjectArrayList, featuredObjectArrayList;
    private ArrayList<SuccessStories> successStoriesObjectArrayList;
    private RecyclerView rvShortlisted, rvHappyStories, rvFeaturedProfile, recyclerviewtypematch;
    private LinearLayoutManager linearLayoutManagerShortListed, linearLayoutManagerHappyStories, linearLayoutManagerFeaturedProfile;
    private ShortlistedAdapter shortlistedAdapter, featuredAdapter;
    private SuccessStoryAdapter successStoriesAdapter;
    private int pos = 0;

    private RelativeLayout rlFeaturedProfile, rlShortlistedProfiles;
    private ImageView ivEdit;
    private TextView tvFeaturedProfileMore, tv_my_connections_count, tv_my_shortlisted_count, tv_myrequest_count;
    private LinearLayout ll_acceptance, ll_shortlisted, ll_request, ll_matches, ll_interestreceived, ll_interestsent, ll_myprefernces;

    private GridLayoutManager gridLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Set title bar
        ((DashboardActivity) getActivity()).setActionBarTitle("Profile(s)");

        _act = getActivity();
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        tvPackageInfo = root.findViewById(R.id.tvPackageInfo);
        tvName = root.findViewById(R.id.tvName);
        tvMoreInformation = root.findViewById(R.id.tvMoreInformation);
        ivProfilePic = root.findViewById(R.id.ivProfilePic);
//        tvWelcomeTitle = root.findViewById(R.id.tvWelcomeTitle);
        tvMoreShortListedProfiles = root.findViewById(R.id.tvShortListedProfile);
        rlFeaturedProfile = root.findViewById(R.id.rlFeaturedProfile);
        rlShortlistedProfiles = root.findViewById(R.id.rlShortlistedProfiles);
//        ivEdit = root.findViewById(R.id.ivEdit);
        tvFeaturedProfileMore = root.findViewById(R.id.tvFeaturedProfile);

//        tv_my_connections_count = root.findViewById(R.id.tv_my_connections_count);
//        tv_my_shortlisted_count = root.findViewById(R.id.tv_my_shortlisted_count);
//        tv_myrequest_count = root.findViewById(R.id.tv_myrequest_count);

        String signUpfirst = "<font color='#000000'>" + getResources().getString(R.string.title_welcome) + "</font>";
        String signUpSecond = "<font color='#FA7A76'>" + SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME) + "" + "</font>";
        String signUpThird = "<font color='#000000'> " + getResources().getString(R.string.title_welcome_part_two) + "</font>";
//        tvWelcomeTitle.setText(Html.fromHtml(signUpfirst + " " + signUpSecond + " " + signUpThird));

        tvName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
        tvMoreInformation.setText(SharedPref.getPrefs(_act, IConstant.HEIGHT_NAME) + " | " + SharedPref.getPrefs(_act, IConstant.WEIGHT) + "(in kg) | " + SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME) + " | "
                /*+ SharedPref.getPrefs(_act, IConstant.CITY_NAME) + ", "*/ + SharedPref.getPrefs(_act, IConstant.STATE_NAME) + ", india | " + SharedPref.getPrefs(_act, IConstant.OCCUPATION_NAME));

        Glide.with(_act)
                .load(SharedPref.getPrefs(_act, IConstant.USER_PHOTO))
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(ivProfilePic);


        //Slider Code
//        sliderView = root.findViewById(R.id.imageSlider);
        sliderItemList = new ArrayList<>();

        successStoriesObjectArrayList = new ArrayList<>();
        shortlistedObjectArrayList = new ArrayList<>();
        featuredObjectArrayList = new ArrayList<>();
        rvShortlisted = root.findViewById(R.id.rvShortlisted);
        rvHappyStories = root.findViewById(R.id.rvHappyStories);
        rvFeaturedProfile = root.findViewById(R.id.rvFeaturedProfile);
//        ll_acceptance = root.findViewById(R.id.ll_acceptance);
//        ll_shortlisted = root.findViewById(R.id.ll_shortlisted);
//        ll_request = root.findViewById(R.id.ll_request);
//        ll_matches = root.findViewById(R.id.ll_matches);
//        ll_interestreceived = root.findViewById(R.id.ll_interestreceived);
//        ll_interestsent = root.findViewById(R.id.ll_interestsent);
//        ll_myprefernces = root.findViewById(R.id.ll_myprefernces);

        typeMatchArrayList = new ArrayList<>();
        typeMatchArrayList.add(new TypeMatch("Connections", R.drawable.matches));
        typeMatchArrayList.add(new TypeMatch("ShortListed", R.drawable.selected));
        typeMatchArrayList.add(new TypeMatch("Request", R.drawable.reqest));
        typeMatchArrayList.add(new TypeMatch("View Profile", R.drawable.viewprofile));
        typeMatchArrayList.add(new TypeMatch("Interest Received", R.drawable.interrece));
        typeMatchArrayList.add(new TypeMatch("Interest Sent", R.drawable.interrece));
        typeMatchArrayList.add(new TypeMatch("My Preferences", R.drawable.prefernce));
        recyclerviewtypematch = root.findViewById(R.id.recyclerviewtypematch);
        recyclerviewtypematch.setHasFixedSize(true);
        recyclerviewtypematch.setNestedScrollingEnabled(false);
        homeAdapter = new TypeMatchHomeAdapter(getActivity(), typeMatchArrayList);
        gridLayoutManager = new GridLayoutManager(_act, 2);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerviewtypematch.setLayoutManager(gridLayoutManager);
        recyclerviewtypematch.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

//        ll_acceptance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyConnectionListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        ll_shortlisted.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyShortlistedListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//
//        ll_request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyRequestListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        ll_matches.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyMatchesListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        ll_interestreceived.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyRequestListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//
//        ll_interestsent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), InterestSentListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });
//        ll_myprefernces.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), MyPreferencesListActivity.class);
//                getContext().startActivity(intent);
//            }
//        });

        tvMoreShortListedProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_act, MyShortlistedListActivity.class);
                startActivity(intent);
            }
        });

//        ivEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(_act, ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

        tvPackageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_act, PackageActivity.class);
                intent.putExtra("ActivityName", "HomeFragment");
                startActivity(intent);
            }
        });

        tvFeaturedProfileMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_act, FeaturedProfileListActivity.class);
                startActivity(intent);
            }
        });

      /*  rvFeaturedProfile.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(_act, OthersProfileActivity.class);
                        intent.putExtra("Profile_id", featuredObjectArrayList.get(position).id);
                        startActivity(intent);
                    }
                })
        );

        rvShortlisted.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(_act, OthersProfileActivity.class);
                        intent.putExtra("Profile_id", shortlistedObjectArrayList.get(position).id);
                        startActivityForResult(intent, 1);
                        _act.overridePendingTransition(R.anim.slide_from_right, R.anim.fade_out);


                    }
                })
        );*/
        webCallDashboard();

        return root;
    }


    @Override
    public void Refresh() {
        webCallDashboard();
    }

    private void webCallDashboard() {
        Log.d("Gender12", "webCallDashboard: " + SharedPref.getPrefs(_act, IConstant.USER_GENDER));
        Log.d("Gender12", "webCallDashboard: " + SharedPref.getPrefs(_act, IConstant.USER_ID));
        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTAppDashboard(
                SharedPref.getPrefs(_act, IConstant.USER_ID),
                SharedPref.getPrefs(_act, IConstant.USER_GENDER)
        );
        //Call<ResponseBody> result = api.POSTAppDashboard("239", "Male");
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    sliderItemList.clear();
                    shortlistedObjectArrayList.clear();
                /*
                    blogObjectArrayList.clear();
                    newsObjectArrayList.clear();
                    chikenProductObjectArrayList.clear();
                    eggProductObjectArrayList.clear();
                    productDetailsReceipeObjectArrayList.clear();*/
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);

                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {

                            user_profile_path = jsonObject.getString("user_profile_path");


                            Shared_Preferences.setPrefs(_act, Constants.ConnectionCount, jsonObject.getString("my_connection_count"));
                            Shared_Preferences.setPrefs(_act, Constants.ShortlistCount, jsonObject.getString("shortlisted_count"));
                            Shared_Preferences.setPrefs(_act, Constants.RequestReceivedCount, jsonObject.getString("request_received_count"));

//                            tv_my_connections_count.setText(Shared_Preferences.getPrefs(_act, Constants.ConnectionCount));
//                            tv_my_shortlisted_count.setText(Shared_Preferences.getPrefs(_act, Constants.ShortlistCount));
//                            tv_myrequest_count.setText(Shared_Preferences.getPrefs(_act, Constants.RequestReceivedCount));

                            Shared_Preferences.setPrefs(_act, Constants.IsChat, jsonObject.getString("is_chat"));
                            Shared_Preferences.setPrefs(_act, Constants.IsPlanExpired, jsonObject.getString("plan_expired"));

                            if(jsonObject.getString("plan_expired").equals("0")) {
                                JSONObject packageInfo = jsonObject.getJSONObject("package_details");
                                Shared_Preferences.setPrefs(_act, Constants.TotalMobileViews, packageInfo.getString("mobile_views_allocated"));
                                Shared_Preferences.setPrefs(_act, Constants.TotalEmailViews, packageInfo.getString("email_views_allocated"));
                                Shared_Preferences.setPrefs(_act, Constants.UsedMobileViews, packageInfo.getString("view_mobile_used"));
                                Shared_Preferences.setPrefs(_act, Constants.UsedEmailViews, packageInfo.getString("view_email_used"));
                            }else {
                                Shared_Preferences.setPrefs(_act, Constants.TotalMobileViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.TotalEmailViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.UsedMobileViews, "0");
                                Shared_Preferences.setPrefs(_act, Constants.UsedEmailViews, "0");
                            }

                            sliders_path = jsonObject.getString("slider_path");

                            ////////////////////////Sliders//////////////////////////////////
                            JSONArray jsonArraySlider = jsonObject.getJSONArray("sliders");
                            for (int index = 0; index < jsonArraySlider.length(); index++) {
                                try {
                                    sliderItemList.add(parseObjectSlider(jsonArraySlider.getJSONObject(index), sliders_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();
                                }
                            }

//                            if (sliderItemList.size() == 0) {
//
//                                Helper_Method.dismissProgessBar();
//                            }
//                            else {
//                                adapter = new SliderAdapterExample(getContext(), sliderItemList);
//                                sliderView.setSliderAdapter(adapter);
//                                sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//                                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
//                                sliderView.setIndicatorSelectedColor(Color.WHITE);
//                                sliderView.setIndicatorUnselectedColor(Color.GRAY);
//                                sliderView.setScrollTimeInSec(3);
//                                sliderView.setAutoCycle(true);
//                                sliderView.startAutoCycle();
//                            }

                            ////////////////////////Category//////////////////////////////////
                            JSONArray jsonArrayShortlisted = jsonObject.getJSONArray("shortlisted");
                            for (int index = 0; index < jsonArrayShortlisted.length(); index++) {
                                try {
                                    shortlistedObjectArrayList.add(new ListProfileObject(jsonArrayShortlisted.getJSONObject(index), user_profile_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();
                                }
                            }

                            if (shortlistedObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();
                                rvShortlisted.setVisibility(View.GONE);
                                rlShortlistedProfiles.setVisibility(View.GONE);
                            } else {

                                rlShortlistedProfiles.setVisibility(View.VISIBLE);
                                shortlistedAdapter = new ShortlistedAdapter(_act, shortlistedObjectArrayList);
                                linearLayoutManagerShortListed = new LinearLayoutManager(_act);
                                linearLayoutManagerShortListed.setOrientation(RecyclerView.HORIZONTAL);
                                rvShortlisted.setLayoutManager(linearLayoutManagerShortListed);
                                rvShortlisted.setItemAnimator(new DefaultItemAnimator());
                                rvShortlisted.setAdapter(shortlistedAdapter);
                                rvShortlisted.setHasFixedSize(true);
                                rvShortlisted.setNestedScrollingEnabled(false);
                                // shortlistedAdapter.setSelected(0);
                                shortlistedAdapter.notifyDataSetChanged();
                                Helper_Method.dismissProgessBar();
                                rvShortlisted.setVisibility(View.VISIBLE);
                            }

                            ////////////////////////CHICKEN PRODUCTS//////////////////////////////////
                            JSONArray jsonArrayRecommended = jsonObject.getJSONArray("featured");
                            for (int index = 0; index < jsonArrayRecommended.length(); index++) {
                                try {

                                    featuredObjectArrayList.add(new ListProfileObject(jsonArrayRecommended.getJSONObject(index), user_profile_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();
                                }
                            }

                            if (featuredObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();
                                rvFeaturedProfile.setVisibility(View.GONE);
                            } else {

                                rlFeaturedProfile.setVisibility(View.VISIBLE);
                                featuredAdapter = new ShortlistedAdapter(_act, featuredObjectArrayList);
                                linearLayoutManagerFeaturedProfile = new LinearLayoutManager(_act);
                              //  linearLayoutManagerFeaturedProfile.setOrientation(RecyclerView.HORIZONTAL);
                                rvFeaturedProfile.setLayoutManager(linearLayoutManagerFeaturedProfile);
                                rvFeaturedProfile.setItemAnimator(new DefaultItemAnimator());
                                rvFeaturedProfile.setAdapter(featuredAdapter);
                               /* rvFeaturedProfile.setHasFixedSize(true);
                                rvFeaturedProfile.setNestedScrollingEnabled(false);*/
                                featuredAdapter.notifyDataSetChanged();
                                rvFeaturedProfile.setVisibility(View.VISIBLE);
                            }

                            ////////////////////////EGG PRODUCTS//////////////////////////////////
                            JSONArray jsonArrayEgg = jsonObject.getJSONArray("success_stories");
                            for (int index = 0; index < jsonArrayEgg.length(); index++) {
                                //   try {

                                successStoriesObjectArrayList.add(new SuccessStories(jsonArrayEgg.getJSONObject(index), jsonObject.getString("success_story_path")));
                                //    } catch (JSONException e) {
                                //  e.printStackTrace();

                                //     Helper_Method.dismissProgessBar();
                                //  }
                            }
                            if (successStoriesObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();
                                rvHappyStories.setVisibility(View.GONE);
                            } else {


                                successStoriesAdapter = new SuccessStoryAdapter(_act, successStoriesObjectArrayList);
                              //  linearLayoutManagerHappyStories = new LinearLayoutManager(_act);
                               // RecyclerView.LayoutManager linearLayoutManagerHappyStories = new LinearLayoutManager(_act);
                                rvHappyStories.setLayoutManager(new LinearLayoutManager(_act, LinearLayoutManager.HORIZONTAL, false));
                                //rvHappyStories.setLayoutManager(linearLayoutManagerHappyStories);
                                rvHappyStories.setItemAnimator(new DefaultItemAnimator());
                                rvHappyStories.setAdapter(successStoriesAdapter);
                                //rvHappyStories.setHasFixedSize(true);
                                //rvHappyStories.setNestedScrollingEnabled(false);
                                // categoryIconsAdapter.setSelected(0);
                                successStoriesAdapter.notifyDataSetChanged();
                            }

                            ////////////////////////BEST SELLING//////////////////////////////////
                           /* JSONArray jsonArrayBestSelling = jsonObject.getJSONArray("best_seller");
                            for (int index = 0; index < jsonArrayBestSelling.length(); index++) {
                                try {

                                    bestSellingObjectArrayList.add(parseObjectBestSelling(jsonArrayBestSelling.getJSONObject(index), products_image_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    rlProgressBar.setVisibility(View.GONE);
                                }
                            }

                            if (bestSellingObjectArrayList.size() == 0) {

                                rlProgressBar.setVisibility(View.GONE);
                                rlMostSellingView.setVisibility(View.GONE);
                            } else {


                                bestSellingProductAdapter = new BestSellingProductAdapter(_act, bestSellingObjectArrayList);
                                linearLayoutManagerBestSellingList = new LinearLayoutManager(_act);
                                linearLayoutManagerBestSellingList.setOrientation(RecyclerView.HORIZONTAL);
                                rvMostSellingList.setLayoutManager(linearLayoutManagerBestSellingList);
                                rvMostSellingList.setItemAnimator(new DefaultItemAnimator());
                                rvMostSellingList.setAdapter(bestSellingProductAdapter);
                                rvMostSellingList.setHasFixedSize(true);
                                rvMostSellingList.setNestedScrollingEnabled(false);
                                // categoryIconsAdapter.setSelected(0);
                                bestSellingProductAdapter.notifyDataSetChanged();
                            }*/

                            ////////////////////////BLOGS//////////////////////////////////
                       /*     JSONArray jsonArrayBlogs = jsonObject.getJSONArray("blogs");
                            for (int index = 0; index < jsonArrayBlogs.length(); index++) {
                                try {

                                    blogObjectArrayList.add(parseObjectBlog(jsonArrayBlogs.getJSONObject(index), blogs_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    rlProgressBar.setVisibility(View.GONE);
                                }
                            }

                            if (blogObjectArrayList.size() == 0) {

                                rlProgressBar.setVisibility(View.GONE);
                                rlBlogView.setVisibility(View.GONE);
                            } else {


                                blogsAdapter = new BlogsAdapter(_act, blogObjectArrayList);
                                linearLayoutManagerBlogList = new LinearLayoutManager(_act);
                                linearLayoutManagerBlogList.setOrientation(RecyclerView.HORIZONTAL);
                                rvBlogList.setLayoutManager(linearLayoutManagerBlogList);
                                rvBlogList.setItemAnimator(new DefaultItemAnimator());
                                rvBlogList.setAdapter(blogsAdapter);
                                rvBlogList.setHasFixedSize(true);
                                rvBlogList.setNestedScrollingEnabled(false);
                                // categoryIconsAdapter.setSelected(0);
                                blogsAdapter.notifyDataSetChanged();
                            }*/

                            ////////////////////////Receipe//////////////////////////////////
                  /*          JSONArray jsonArrayReceipe = jsonObject.getJSONArray("recipes_list");
                            for (int index = 0; index < jsonArrayReceipe.length(); index++) {
                                try {

                                    productDetailsReceipeObjectArrayList.add(new ProductDetailsReceipeObject(jsonArrayReceipe.getJSONObject(index)));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    rlProgressBar.setVisibility(View.GONE);
                                }
                            }

                            if (productDetailsReceipeObjectArrayList.size() == 0) {

                                rlProgressBar.setVisibility(View.GONE);
                                rlRecipe.setVisibility(View.GONE);
                            } else {


                                recipeAdapter = new RecipeAdapter(_act, productDetailsReceipeObjectArrayList, recipe_path);
                                linearLayoutManagerRecipeList = new LinearLayoutManager(_act);
                                linearLayoutManagerRecipeList.setOrientation(RecyclerView.HORIZONTAL);
                                rvRecipeList.setLayoutManager(linearLayoutManagerRecipeList);
                                rvRecipeList.setItemAnimator(new DefaultItemAnimator());
                                rvRecipeList.setAdapter(recipeAdapter);
                                rvRecipeList.setHasFixedSize(true);
                                rvRecipeList.setNestedScrollingEnabled(false);
                                // categoryIconsAdapter.setSelected(0);
                                recipeAdapter.notifyDataSetChanged();
                                rlRecipe.setVisibility(View.VISIBLE);
                            }*/


                            ////////////////////////NEWS//////////////////////////////////
                       /*     JSONArray jsonArrayNews = jsonObject.getJSONArray("news");
                            for (int index = 0; index < jsonArrayNews.length(); index++) {
                                try {

                                    newsObjectArrayList.add(parseObjectNews(jsonArrayNews.getJSONObject(index), news_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    rlProgressBar.setVisibility(View.GONE);
                                }
                            }

                            if (newsObjectArrayList.size() == 0) {

                                rlProgressBar.setVisibility(View.GONE);
                                rlNewsView.setVisibility(View.GONE);
                            } else {

                                newsAdapter = new NewsAdapter(_act, newsObjectArrayList);
                                linearLayoutManagerNewsList = new LinearLayoutManager(_act);
                                linearLayoutManagerNewsList.setOrientation(RecyclerView.HORIZONTAL);
                                rvNewsList.setLayoutManager(linearLayoutManagerNewsList);
                                rvNewsList.setItemAnimator(new DefaultItemAnimator());
                                rvNewsList.setAdapter(newsAdapter);
                                rvNewsList.setHasFixedSize(true);
                                rvNewsList.setNestedScrollingEnabled(false);
                                // newsAdapter.setSelected(0);
                                newsAdapter.notifyDataSetChanged();
                            }*/

                        } else {
                            //showToast(stringMsg);
                            Helper_Method.dismissProgessBar();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                } finally {

                    //category_id = sliderItemList.get(0).id;
                    //page_count = 1;
                    // webServiceCallProductFirstCall(keyWord, category_id);
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                if (t instanceof NetworkErrorException)
                    Helper_Method.warnUser(_act, "Network Error", getString(R.string.error_network), true);
                else if (t instanceof IOException)
                    Helper_Method.warnUser(_act, "Connection Error", getString(R.string.error_network), true);
                    //else if (t instanceof ServerError)
                    //   Helper_Method.warnUser(_act, "Server Error", getString(R.string.error_server), true);
                else if (t instanceof ConnectException)
                    Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                    //else if (t instanceof ConnectException)
                    //Helper_Method.warnUser(_act, "No Connection Error", getString(R.string.error_connection), true);
                else if (t instanceof TimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else if (t instanceof SocketTimeoutException)
                    Helper_Method.warnUser(_act, "Timeout Error", getString(R.string.error_timeout), true);
                else
                    Helper_Method.warnUser(_act, "Unknown Error", getString(R.string.error_something_wrong), true);

            }
        });
    }

    public SliderItem parseObjectSlider(JSONObject object, String sliders_path) {
        SliderItem sliderItem = new SliderItem();
        try {
            sliderItem.id = object.getString("id");
            //sliderItem.description = object.getString("slider_title");
            sliderItem.imageUrl = sliders_path + object.getString("slider_image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderItem;
    }


/*
    public CategoryObject parseObjectCategory(JSONObject object, String category_path) {
        CategoryObject categoryObject = new CategoryObject();
        try {
            categoryObject.id = object.getString("id");
            categoryObject.category_name = object.getString("category_name");
            categoryObject.category_image = category_path + object.getString("category_image");
            categoryObject.category_icon = category_path + object.getString("category_icon");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryObject;
    }
*/


/*    public ChikenProductObject parseObjectRecommended(JSONObject object, String recommended_path) {
        ChikenProductObject chikenProductObject = new ChikenProductObject();
        try {
            chikenProductObject.id = object.getString("id");
            chikenProductObject.sku = object.getString("sku");
            chikenProductObject.product_name = object.getString("product_name");
            chikenProductObject.category_id = object.getString("category_id");
            chikenProductObject.short_description = object.getString("short_description");
            chikenProductObject.long_description = object.getString("long_description");
            chikenProductObject.featured_image = recommended_path + object.getString("featured_image");
            chikenProductObject.tax_id = object.getString("tax_id");
            chikenProductObject.brand_id = object.getString("brand_id");
            chikenProductObject.is_recommended = object.getString("is_recommended");
            chikenProductObject.nutrition = object.getString("nutrition");
            chikenProductObject.status = object.getString("status");
            chikenProductObject.is_combo = object.getString("is_combo");
            chikenProductObject.validity = object.getString("validity");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return chikenProductObject;
    }*/

/*    public BestSellingObject parseObjectBestSelling(JSONObject object, String best_selling_path) {
        BestSellingObject bestSellingObject = new BestSellingObject();
        try {
            bestSellingObject.product_name = object.getString("product_name");
            bestSellingObject.sku = object.getString("sku");
            bestSellingObject.featured_image = best_selling_path + object.getString("featured_image");
            bestSellingObject.category_id = object.getString("category_id");
            bestSellingObject.category_name = object.getString("category_name");
            bestSellingObject.brand = object.getString("brand");
            bestSellingObject.variant_unit = object.getString("variant_unit");
            bestSellingObject.strike_thr_rate = object.getString("strike_thr_rate");
            bestSellingObject.narration = object.getString("narration");
            bestSellingObject.sales_rate = object.getString("sales_rate");
            bestSellingObject.variant_name = object.getString("variant_name");
            bestSellingObject.product_variant_id = object.getString("product_variant_id");
            bestSellingObject.product_id = object.getString("product_id");
            bestSellingObject.MostSold = object.getString("MostSold");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return bestSellingObject;
    }*/

/*    public BlogObject parseObjectBlog(JSONObject object, String blogs_path) {
        BlogObject blogObject = new BlogObject();
        try {
            blogObject.id = object.getString("id");
            blogObject.title = object.getString("title");
            blogObject.date = object.getString("date");
            blogObject.short_description = object.getString("short_description");
            blogObject.long_description = object.getString("long_description");
            blogObject.image = blogs_path + object.getString("image");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return blogObject;
    }*/

/*    public NewsObject parseObjectNews(JSONObject object, String news_path) {
        NewsObject newsObject = new NewsObject();
        try {
            newsObject.id = object.getString("id");
            newsObject.title = object.getString("title");
            newsObject.image = blogs_path + object.getString("image");
            newsObject.date = object.getString("date");
            newsObject.description = object.getString("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsObject;
    }*/
}