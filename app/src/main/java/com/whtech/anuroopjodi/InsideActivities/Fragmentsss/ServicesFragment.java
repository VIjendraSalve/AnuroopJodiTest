package com.whtech.anuroopjodi.InsideActivities.Fragmentsss;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whtech.anuroopjodi.Adapter.HomeCategoryAdapter;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Constant.IUrls;
import com.whtech.anuroopjodi.Constant.Interface;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.RecyclerItemClickListener;
import com.whtech.anuroopjodi.InsideActivities.Activities.VendorListActivity;
import com.whtech.anuroopjodi.InsideActivities.DashboardActivity;
import com.whtech.anuroopjodi.Object.CategoryObject;
import com.whtech.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicesFragment extends Fragment {

    public static Activity _act;
    private String setLang;
    private ConnectionDetector connectionDetector;

   /* //Slider Category
    private SliderView sliderView;
    private SliderAdapterExample adapter;
    private List<SliderItem> sliderItemList;
    private RelativeLayout rlProgressBar, rlSearch;*/

    //Category Imagesw
    private ArrayList<CategoryObject> categoryObjectArrayList;
    private RelativeLayout progress, noInternet;
    private RecyclerView rvCategory;
    private HomeCategoryAdapter homeCategoryAdapter;
    private GridLayoutManager gridLayoutManager;


    private String sliders_path = null, category_path = null, blogs_path = null, news_path = null, cart_count = "0";




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        _act = getActivity();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        // Set title bar
        ((DashboardActivity) getActivity()).setActionBarTitle("Services");

        //Slider Code
    /*    sliderView = root.findViewById(R.id.imageSlider);
        rlProgressBar = root.findViewById(R.id.rlProgressBar);
        rlSearch = root.findViewById(R.id.rlSearch);
        sliderItemList = new ArrayList<>();*/


        categoryObjectArrayList = new ArrayList<>();
        progress = (RelativeLayout) root.findViewById(R.id.progress);
        noInternet = (RelativeLayout) root.findViewById(R.id.noInternet);
        rvCategory = (RecyclerView) root.findViewById(R.id.rvCategory);
        rvCategory.setHasFixedSize(true);
        rvCategory.setNestedScrollingEnabled(false);
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), categoryObjectArrayList);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation

        rvCategory.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(_act, VendorListActivity.class);
                        intent.putExtra("category_id", categoryObjectArrayList.get(position).id);
                        intent.putExtra("category_name", categoryObjectArrayList.get(position).category);
                        startActivity(intent);


                    }
                })
        );


        rvCategory.addOnItemTouchListener(
                new RecyclerItemClickListener(_act, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                    }
                })
        );



     /*   rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_act, CategorySearchActivity.class);
                intent.putExtra("Categories", categoryObjectArrayList);
                startActivityForResult(intent, 900);
                _act.overridePendingTransition(R.anim.activity_slide_from_bottom, R.anim.activity_stay);
            }
        });*/


        webCallDashboard();
        return root;
    }

    private void webCallDashboard() {

        progress.setVisibility(View.VISIBLE);
        noInternet.setVisibility(View.INVISIBLE);
        rvCategory.setVisibility(View.INVISIBLE);

        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETServices();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    // sliderItemList.clear();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {

                        JSONObject jsonObject = new JSONObject(output);
                        String stringCode = jsonObject.getString(IConstant.RESPONSE_CODE);
                        String stringMsg = jsonObject.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase(IConstant.RESPONSE_SUCCESS)) {


                            //sliders_path = jsonObject.getString("banner_path");
                            //category_path = jsonObject.getString("category_path");


                            ////////////////////////Sliders//////////////////////////////////
                          /*  JSONArray jsonArraySlider = jsonObject.getJSONArray("banners_list");
                            for (int index = 0; index < jsonArraySlider.length(); index++) {
                                try {
                                    sliderItemList.add(parseObjectSlider(jsonArraySlider.getJSONObject(index), sliders_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    rlProgressBar.setVisibility(View.GONE);
                                }
                            }


                            if (sliderItemList.size() == 0) {

                                rlProgressBar.setVisibility(View.GONE);
                            } else {


                                adapter = new SliderAdapterExample(getContext(), sliderItemList);
                                sliderView.setSliderAdapter(adapter);
                                sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                                sliderView.setIndicatorSelectedColor(Color.WHITE);
                                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                                sliderView.setScrollTimeInSec(3);
                                sliderView.setAutoCycle(true);
                                sliderView.startAutoCycle();
                            }*/


                            ////////////////////////Category//////////////////////////////////
                            JSONArray jsonArrayCategory = jsonObject.getJSONArray("service_categories");
                            for (int index = 0; index < jsonArrayCategory.length(); index++) {
                                try {
                                    categoryObjectArrayList.add(parseObjectCategory(jsonArrayCategory.getJSONObject(index), category_path));

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    progress.setVisibility(View.INVISIBLE);
                                    noInternet.setVisibility(View.VISIBLE);
                                    rvCategory.setVisibility(View.INVISIBLE);
                                }
                            }

                            if (categoryObjectArrayList.size() == 0) {

                                progress.setVisibility(View.INVISIBLE);
                                noInternet.setVisibility(View.VISIBLE);
                                rvCategory.setVisibility(View.INVISIBLE);

                            } else {

                                // categoryObjectArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
                                rvCategory.setLayoutManager(gridLayoutManager);
                                rvCategory.setAdapter(homeCategoryAdapter);
                                homeCategoryAdapter.notifyDataSetChanged();
                                progress.setVisibility(View.INVISIBLE);
                                noInternet.setVisibility(View.INVISIBLE);
                                rvCategory.setVisibility(View.VISIBLE);
                            }

                        } else {
                            progress.setVisibility(View.INVISIBLE);
                            noInternet.setVisibility(View.VISIBLE);
                            rvCategory.setVisibility(View.INVISIBLE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progress.setVisibility(View.INVISIBLE);
                        noInternet.setVisibility(View.VISIBLE);
                        rvCategory.setVisibility(View.INVISIBLE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    progress.setVisibility(View.INVISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                    rvCategory.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Helper_Method.toaster(_act, t.getMessage());
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

  /*  public SliderItem parseObjectSlider(JSONObject object, String sliders_path) {
        SliderItem sliderItem = new SliderItem();
        try {
            sliderItem.id = object.getString("id");
            sliderItem.city_id = object.getString("city_id");
            sliderItem.category_id = object.getString("category_id");
            sliderItem.banner_image = sliders_path + object.getString("banner_image");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sliderItem;
    }*/

    public CategoryObject parseObjectCategory(JSONObject object, String category_path) {
        CategoryObject categoryObject = new CategoryObject();
        try {
            categoryObject.id = object.getString("id");
            ///categoryObject.category_id = object.getString("category_id");
            categoryObject.category = object.getString("serviceName");
            // categoryObject.lang = object.getString("lang");
            //categoryObject.short_desc = object.getString("short_desc");
            //categoryObject.long_desc = object.getString("long_desc");
            categoryObject.category_image = category_path + object.getString("icon");
            //categoryObject.category_icon = category_path + object.getString("category_icon");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryObject;
    }
}