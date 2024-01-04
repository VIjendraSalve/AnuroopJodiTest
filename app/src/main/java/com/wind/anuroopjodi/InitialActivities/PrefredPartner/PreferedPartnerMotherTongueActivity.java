package com.wind.anuroopjodi.InitialActivities.PrefredPartner;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wind.anuroopjodi.Adapter.MotherTongueAdapter;
import com.wind.anuroopjodi.BaseActivity;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Object.MotherTongueObject;
import com.wind.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferedPartnerMotherTongueActivity extends BaseActivity {

    private static final String TAG = "PreferedPartnerMotherTongueActivity";
    private Activity _act;
    private ConnectionDetector connectionDetector;
    private Bundle extras;
    private ArrayList<MotherTongueObject> educationObjectArrayList, defaultTypesObjArrayListNew, filterListArray;

    private RecyclerView rvProductType;
    private TextView btnselect, btndeselect;
    private String strBrandsId, setBrandsValue, allDataString, allDataIds;
    private List<String> listBrandsId, listBrands;
    private MotherTongueAdapter multipleGrampanchayatAdapter;
    private EditText etWord;

    private List<String> selectedBrandsIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_multi_selection);

        _act = PreferedPartnerMotherTongueActivity.this;
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.lbl_select_mother_tongue));
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getApplicationContext().getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        educationObjectArrayList = new ArrayList<>();
        defaultTypesObjArrayListNew = new ArrayList<>();
        filterListArray = new ArrayList<>();
        selectedBrandsIds = new ArrayList<>();
        if (SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).isEmpty()
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("null")
                && !SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("")) {

            String[] myArray = SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).split(",");
            selectedBrandsIds = Arrays.asList(myArray);
        } else {
        }
        Log.d("TAG", "onCreateView:  Brands List Size" + SharedPref.getPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID));
        Log.d("TAG", "onCreateView: Brands List Size" + selectedBrandsIds.size());

        etWord = findViewById(R.id.etWord);
        btnselect = findViewById(R.id.select);
        btndeselect = findViewById(R.id.deselect);
        rvProductType = (RecyclerView) findViewById(R.id.rvProductType);
        rvProductType.setHasFixedSize(true);
        rvProductType.setNestedScrollingEnabled(false);

        webcallEducationList();


        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTypesObjArrayListNew = getModel(true, "2");
                multipleGrampanchayatAdapter = new MotherTongueAdapter(_act, defaultTypesObjArrayListNew);
                rvProductType.setAdapter(multipleGrampanchayatAdapter);
                btnselect.setVisibility(View.GONE);
                btndeselect.setVisibility(View.VISIBLE);

            }
        });
        btndeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultTypesObjArrayListNew = getModel(false, "3");
                multipleGrampanchayatAdapter = new MotherTongueAdapter(_act, defaultTypesObjArrayListNew);
                rvProductType.setAdapter(multipleGrampanchayatAdapter);
                btnselect.setVisibility(View.VISIBLE);
                btndeselect.setVisibility(View.GONE);

            }
        });
        etWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("TAG", "filter: " + charText);
        Log.d("TAG", "filter: defaultListSize1" + defaultTypesObjArrayListNew.size());
        //filterList = new ArrayList<MotherTongueObject>();
        filterListArray.clear();
        Log.d("TAG", "filter: defaultListSize2" + defaultTypesObjArrayListNew.size());
        if (charText.length() == 0) {
            filterListArray.addAll(defaultTypesObjArrayListNew);
            //filterListArray = defaultTypesObjArrayListNew;
            Log.d("TAG", "filter: if" + defaultTypesObjArrayListNew.size());
        } else {
            Log.d("TAG", "filter: else" + defaultTypesObjArrayListNew.size());
            for (MotherTongueObject item : defaultTypesObjArrayListNew) {
                Log.d("TAG", "Inside filter: " + charText);
                if (item.languageName.toLowerCase().contains(charText.toLowerCase())) {
                    filterListArray.add(item);
                }
            }
        }
        Log.d("Filter", "filter: " + filterListArray.size());
        //updateList(filterListArray);
        //defaultTypesObjArrayListNew = filterListArray;
        multipleGrampanchayatAdapter.notifyDataSetChanged();
    }

    private ArrayList<MotherTongueObject> getModel(boolean isSelect, String type) {
        ArrayList<MotherTongueObject> list = new ArrayList<>();
        Log.d("TAG", "getModel: " + selectedBrandsIds.size());
        //1 : for common list
        //2 : Select All list
        //3 : Deselect All list
        if (type.equals("1")) {
            if (selectedBrandsIds.size() == 0) {
                for (int i = 0; i < educationObjectArrayList.size(); i++) {
                    MotherTongueObject model = new MotherTongueObject();
                    model.setId(educationObjectArrayList.get(i).getId());
                    model.setLanguageName(educationObjectArrayList.get(i).getLanguageName());
                    model.setSelected(isSelect);
                    list.add(model);
                }
            } else {
                for (int i = 0; i < educationObjectArrayList.size(); i++) {
                    MotherTongueObject model = new MotherTongueObject();
                    model.setId(educationObjectArrayList.get(i).getId());
                    model.setLanguageName(educationObjectArrayList.get(i).getLanguageName());
                    for (int j = 0; j < selectedBrandsIds.size(); j++) {
                        if (selectedBrandsIds.get(j).toString().equals(educationObjectArrayList.get(i).getId())) {
                            Log.d("TAG", "getModel: Area Map" + selectedBrandsIds.get(j));
                            Log.d("TAG", "getModel: Rec Grampanchat " + educationObjectArrayList.get(i).getId());
                            model.setSelected(true);
                        } else {
                            // model.setSelected(isSelect);
                        }
                    }
                    list.add(model);
                }
            }
        } else {
            for (int i = 0; i < educationObjectArrayList.size(); i++) {
                MotherTongueObject model = new MotherTongueObject();
                model.setId(educationObjectArrayList.get(i).getId());
                model.setLanguageName(educationObjectArrayList.get(i).getLanguageName());
                model.setSelected(isSelect);
                list.add(model);
                //}
            }
        }
        return list;
    }

    public void brandsCommaSepratedString(Activity _act) {


        listBrandsId = new ArrayList<>();
        listBrands = new ArrayList<>();
        // areaMappingObjectArrayList.clear();
        listBrandsId.clear();
        listBrands.clear();
        for (int i = 0; i < MotherTongueAdapter.educationObjectArrayList.size(); i++) {

            if (MotherTongueAdapter.educationObjectArrayList.get(i).isSelected()) {
                strBrandsId = MotherTongueAdapter.educationObjectArrayList.get(i).getId();
                setBrandsValue = MotherTongueAdapter.educationObjectArrayList.get(i).getLanguageName();
                Log.d("Grampanchayat", "commasepratedString: District Id :" + strBrandsId);
                Log.d("Grampanchayat", "commasepratedString: Taluka Id :" + setBrandsValue);
                // areaMappingObjectArrayList.add(MotherTongueAdapter.educationObjectArrayList.get(i).getVariant_value());

                listBrandsId.add(strBrandsId);
                listBrands.add(setBrandsValue);
                Log.d("MyTag", "listBrandsId ids: " + MotherTongueAdapter.educationObjectArrayList.get(i).getId());
            }

            allDataIds = "";
            for (String strIds : listBrandsId) {
                allDataIds += strIds + ",";
            }
            if (allDataIds.endsWith(",")) {
                allDataIds = allDataIds.substring(0, allDataIds.length() - 1);

            }

            allDataString = "";
            for (String strData : listBrands) {
                allDataString += strData + ",";
            }
            if (allDataString.endsWith(",")) {
                allDataString = allDataString.substring(0, allDataString.length() - 1);
            }
        }
        Log.d("mytag", "commaseprated Brands ids: " + allDataIds);
        Log.d("mytag", "commaseprated Brands names: " + allDataString);
        Log.d("mytag", "listBrandsId ids: " + listBrands.size());
        Log.d("mytag", "listBrandsId: " + listBrandsId.size());

        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID, allDataIds.toString());
        SharedPref.setPrefs(_act, IConstant.PREFERED_PARTNER_MOTHER_TONGUE, allDataString.toString());
    }

    private void webcallEducationList() {

        Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETMotherTongue();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    educationObjectArrayList.clear();
                    defaultTypesObjArrayListNew.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString(IConstant.RESPONSE_MESSAGE);


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("mother_tongue_list");
                            educationObjectArrayList.add(new MotherTongueObject("-1", "ANY"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    educationObjectArrayList.add(new MotherTongueObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (educationObjectArrayList.size() == 0) {

                                Helper_Method.dismissProgessBar();


                            } else {
                                Log.d("TAG", "onResponse: Main List:  " + educationObjectArrayList.size());
                                Helper_Method.dismissProgessBar();
                                defaultTypesObjArrayListNew = getModel(false, "1");
                                filterListArray.addAll(defaultTypesObjArrayListNew);
                                multipleGrampanchayatAdapter = new MotherTongueAdapter(_act, filterListArray);
                                rvProductType.setAdapter(multipleGrampanchayatAdapter);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(_act, 1);
                                gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
                                rvProductType.setLayoutManager(gridLayoutManager);
                                multipleGrampanchayatAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Helper_Method.toaster(_act, stringMsg);
                            Helper_Method.dismissProgessBar();


                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", "Technical Error");
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.service_menu, menu);
        //menu.removeItem(R.id.action_add);
     /*
        menu.removeItem(R.id.action_inbox);
        menu.removeItem(R.id.action_add_wardrobe);
        menu.removeItem(R.id.action_outfit);
        menu.removeItem(R.id.action_suggestion);
        menu.removeItem(R.id.action_suggestion);*/
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                brandsCommaSepratedString(_act);
                if (listBrandsId.size()!=0) {
                    Intent intent = getIntent();
                   /* intent.putExtra("grampanchayat_ids", allDataIds);
                    intent.putExtra("grampanchayatName", allDataString);
                    intent.putStringArrayListExtra("select_grampanchayat_ids", (ArrayList<String>) listIds);
                    intent.putStringArrayListExtra("select_grampanchayat_name", (ArrayList<String>) list);
                    intent.putExtra("select_multi_ids", areaMappingObjectArrayList);*/
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Helper_Method.toaster(_act, getResources().getString(R.string.lbl_mother_tongue_hint));
                }

                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}