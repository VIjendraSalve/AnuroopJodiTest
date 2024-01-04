package com.wind.anuroopjodi.InsideActivities.Fragmentsss;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wind.anuroopjodi.Adapter.GenderAdapter;
import com.wind.anuroopjodi.Adapter.PrefredPartenerMaritalStatusAdapter;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.RecyclerItemClickListener;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.PrefredPartner.PreferedPartnerMotherTongueActivity;
import com.wind.anuroopjodi.InsideActivities.Vijendra.SearchProfileListActivity;
import com.wind.anuroopjodi.Object.CityObject;
import com.wind.anuroopjodi.Object.CommonCountObject;
import com.wind.anuroopjodi.Object.GenderObj;
import com.wind.anuroopjodi.Object.HeightObject;
import com.wind.anuroopjodi.Object.MaritalStatusObj;
import com.wind.anuroopjodi.Object.StateObject;
import com.wind.anuroopjodi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private int mNoOfColumns = 2;
    private String user_profile_path = null;

    //Age Spinner
    private ArrayList<CommonCountObject> ageObjectArrayList;
    private Spinner spinnerAgeFrom, spinnerAgeTo;
    private ArrayAdapter<CommonCountObject> spinnerAgeFrom_Adapter, spinnerAgeTo_Adapter;
    private String gender = "", strFromAgeId = "0", strFromAge, strToAgeId = "0", strToAge;

    //Height Spinner
    private ArrayList<HeightObject> heightObjectArrayList;
    private SearchableSpinner spinnerHeightFrom, spinnerHeightTo;
    private ArrayAdapter<HeightObject> spinnerHeightFrom_Adapter, spinnerHeightTo_Adapter;
    private String strHeightFromId = "0", strHeightFrom, strHeightToId = "0", strHeightTo;


    //State Spinner Zone
    private ArrayList<StateObject> stateObjectArrayList;
    private SearchableSpinner spinnerStatelist;
    private ArrayAdapter<StateObject> spinnerStatelist_Adapter;
    private String strStateId = "0", strState;

    //City Spinner Zone
    private ArrayList<CityObject> cityObjectArrayList;
    private SearchableSpinner spinnerCitylist;
    private ArrayAdapter<CityObject> spinnerCitylist_Adapter;
    private String strCityId = "0", strCity;


    //Marital Status Types
    private ArrayList<MaritalStatusObj> maritalStatusObjArrayList;
    private RecyclerView rvMaritalStatus;
    private PrefredPartenerMaritalStatusAdapter maritalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerMaritalStatus;
    // private String strMaritalStatus = null;

    //Gender Types
    private ArrayList<GenderObj> genderObjArrayList;
    private RecyclerView rvGender;
    private GenderAdapter genderAdapter;
    private GridLayoutManager getGridLayoutManagerGender;
    private String strGender = "Male";

    private TextView tvMotherTougue;

    private String strMaritalStatusId, strMaritalStatus, allMaritalStatusString, allMaritalStatusIds;
    private List<String> listMaritalStatusId, listMaritalStatus;
    private EditText etName, etMobile;

    private View view;

    public SimpleSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimpleSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleSearchFragment newInstance(String param1, String param2) {
        SimpleSearchFragment fragment = new SimpleSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_simple_search, container, false);

        validations = new Validations();
        //getContext() = DashboardActivity.this;
        connectionDetector = ConnectionDetector.getInstance(getContext());
//        Helper_Method.hideSoftInput(getContext());
        //    mNoOfColumns = SignUpActivity.Utility.calculateNoOfColumns(getContext(), 120);
        //  Log.d(TAG, "onCreate: GridColoumn" + mNoOfColumns);
        init();
        return view;
    }

    private void init() {

        maritalStatusObjArrayList = new ArrayList<>();
        listMaritalStatusId = new ArrayList<>();
        listMaritalStatus = new ArrayList<>();


        spinnerHeightFrom = view.findViewById(R.id.spinnerHeightFrom);
        spinnerHeightTo = view.findViewById(R.id.spinnerHeightTo);
        spinnerStatelist = view.findViewById(R.id.spinnerStatelist);
        spinnerCitylist = view.findViewById(R.id.spinnerCitylist);
        tvMotherTougue = view.findViewById(R.id.tvMotherTougue);
        etName = view.findViewById(R.id.etName);
        etMobile = view.findViewById(R.id.etMobile);


        //Mother Tongue
        if (SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).isEmpty()
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("null")
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("")) {

            //String[] myArray = SharedPref.getPrefs(getContext(), IConstant.EDUCATION_ID).split(",");
            //selectedBrandsIds = Arrays.asList(myArray);
            tvMotherTougue.setText(SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE));
        } else {
        }

        view.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Helper_Method.intentActivity(getContext(), PackageActivity.class, false);

                maritalStatusCommaSepratedString();
               /* if (listMaritalStatusId.size()==0)
                {
                    Helper_Method.toaster(getContext(),"Select Marital Status");
                }
                else
                {
                    maritalStatusCommaSepratedString(getContext());
                }*/
                if (strFromAgeId != null && !strFromAgeId.isEmpty() && !strFromAgeId.equals("null") && !strFromAgeId.equals("0")) {

                    if (strToAgeId != null && !strToAgeId.isEmpty() && !strToAgeId.equals("null") && !strToAgeId.equals("0")) {

                        if (strHeightFromId != null && !strHeightFromId.isEmpty() && !strHeightFromId.equals("null") && !strHeightFromId.equals("0")) {

                            if (strHeightToId != null && !strHeightToId.isEmpty() && !strHeightToId.equals("null") && !strHeightToId.equals("0")) {


                                if (connectionDetector.isConnectionAvailable()) {

                                    Log.d("Values", "strFromAgeId: " + strGender);
                                    Log.d("Values", "strFromAgeId: " + strFromAgeId);
                                    Log.d("Values", "strToAgeId: " + strToAgeId);
                                    Log.d("Values", "strHeightFromId: " + strHeightFromId);
                                    Log.d("Values", "strHeightToId: " + strHeightToId);
                                    Log.d("Values", "strStateId: " + strStateId);
                                     Log.d("Values", "strCityId: " + strCityId);
                                    // Log.d("Values", "listMaritalStatusId: " + listMaritalStatusId.get(0));
                                    //Log.d("Values", "PREFERED_PARTNER_MOTHER_TONGUE_ID: " + SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID));

                                    if (strCityId.equals("0")) {
                                        strCityId = "";
                                    }
                                    if (strStateId.equals("0")) {
                                        strStateId = "";
                                    }
                                    Intent intent = new Intent(getContext(), SearchProfileListActivity.class);
                                    intent.putExtra(IConstant.Gender, strGender);
                                    intent.putExtra(IConstant.AgeFrom, strFromAgeId);
                                    intent.putExtra(IConstant.AgeTo, strToAgeId);
                                    intent.putExtra(IConstant.HeightFrom, strHeightFromId);
                                    intent.putExtra(IConstant.HeightTo, strHeightToId);
                                    intent.putExtra(IConstant.StateID, strStateId);
                                    intent.putExtra(IConstant.CityID, strCityId);
                                    intent.putExtra(IConstant.Name, etName.getText().toString().trim());
                                    intent.putExtra(IConstant.Number, etMobile.getText().toString().trim());
                                    intent.putExtra(IConstant.SearchType, "0"); //1: advanced 0: simple
                                    if (listMaritalStatus.size() > 0) {
                                        intent.putExtra(IConstant.MaritalStatus, listMaritalStatusId.get(0));
                                    } else {
                                        intent.putExtra(IConstant.MaritalStatus, "");
                                    }
                                    if(SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null) {
                                        intent.putExtra(IConstant.MotherTounge, SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID));
                                    }else {
                                        intent.putExtra(IConstant.MotherTounge, "");
                                    }

                                    startActivity(intent);


                                } else {
                                    Toast.makeText(getContext(), "Internet connection is required to run this app. Turn on mobile-network/Wi-Fi", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(getContext(), "Select Height To", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Select Height From", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getContext(), "Select Age To", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), "Select Age From", Toast.LENGTH_SHORT).show();

                }

            }
        });


        tvMotherTougue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PreferedPartnerMotherTongueActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        Gender();
    }

    private void Gender() {
        rvGender = view.findViewById(R.id.rvGender);
        genderObjArrayList = new ArrayList<>();
        if(SharedPref.getPrefs(getContext(), IConstant.USER_GENDER).equals("Female")) {
            genderObjArrayList.add(new GenderObj("1", "Male", true));
            strGender = "Male";
        }else {
            genderObjArrayList.add(new GenderObj("2", "Female", true));
            strGender = "Female";
        }
        if (genderObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvGender.setVisibility(View.GONE);

        } else {


            rvGender.setHasFixedSize(true);
            rvGender.setNestedScrollingEnabled(false);
            // genderObjArrayList.get(0).setSelected(true);
            // genderObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            genderAdapter = new GenderAdapter(getContext(), genderObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvGender.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerGender = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerGender.setOrientation(RecyclerView.VERTICAL);
            rvGender.setLayoutManager(getGridLayoutManagerGender);
            rvGender.setItemAnimator(new DefaultItemAnimator());
            rvGender.setAdapter(genderAdapter);
            //profileCreatedByAdapter.setSelected(0);
            genderAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvGender.setVisibility(View.VISIBLE);

            rvGender.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < genderObjArrayList.size(); i++) {

                                if (i == position) {
                                    genderObjArrayList.get(i).setSelected(true);
                                } else {
                                    genderObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            genderAdapter.notifyDataSetChanged();
                            strGender = genderObjArrayList.get(position).name;
                            Log.d("Gender", "onItemClick: " + strGender);

                        }
                    })
            );
        }

        MaritalStatus();
    }

    private void MaritalStatus() {

        rvMaritalStatus = view.findViewById(R.id.rvMaritalStatus);

        maritalStatusObjArrayList.clear();
        maritalStatusObjArrayList.add(new MaritalStatusObj("1", "Single", false));
        //maritalStatusObjArrayList.add(new MaritalStatusObj("2", "Married", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("3", "Divorce", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("4", "Awaiting Divorce", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("5", "Separated", false));
        maritalStatusObjArrayList.add(new MaritalStatusObj("6", "Widow", false));


        if (maritalStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvMaritalStatus.setVisibility(View.GONE);

        } else {


            rvMaritalStatus.setHasFixedSize(true);
            rvMaritalStatus.setNestedScrollingEnabled(false);
            // maritalStatusObjArrayList.get(0).setSelected(true);
            // maritalStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            maritalStatusAdapter = new PrefredPartenerMaritalStatusAdapter(getContext(), maritalStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvMaritalStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerMaritalStatus = new GridLayoutManager(getContext(), 3);
            getGridLayoutManagerMaritalStatus.setOrientation(RecyclerView.VERTICAL);
            rvMaritalStatus.setLayoutManager(getGridLayoutManagerMaritalStatus);
            rvMaritalStatus.setItemAnimator(new DefaultItemAnimator());
            rvMaritalStatus.setAdapter(maritalStatusAdapter);
            //profileCreatedByAdapter.setSelected(0);
            maritalStatusAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvMaritalStatus.setVisibility(View.VISIBLE);
            Age();
//            rvMaritalStatus.addOnItemTouchListener(
//                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//
//                            maritalStatusObjArrayList.get(position).setSelected(true);
//
//                           for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {
//                                if (i == position) {
//                                    maritalStatusObjArrayList.get(i).setSelected(true);
//                                } else {
//                                    maritalStatusObjArrayList.get(i).setSelected(false);
//                                }
//                            }
//
//                            // pos = profileCreatedForAdapter.setSelected(position);
//                            maritalStatusAdapter.notifyDataSetChanged();
//                            //strMaritalStatus = maritalStatusObjArrayList.get(position).id;
//                            //Log.d(TAG, "onItemClick: " + strMaritalStatus);
//
//                        }
//                    })
//            );
        }
    }

    public void Age() {
        ageObjectArrayList = new ArrayList<>();
        ageObjectArrayList.add(new CommonCountObject("0", "Select Age", false));
        for (int i = 18; i < 100; i++) {
            ageObjectArrayList.add(new CommonCountObject(String.valueOf(i), String.valueOf(i), false));
        }

        //From Age
        spinnerAgeFrom = view.findViewById(R.id.spinnerAgeFrom);
        spinnerAgeFrom_Adapter = new ArrayAdapter<CommonCountObject>(getContext(), android.R.layout.simple_spinner_item, ageObjectArrayList);
        spinnerAgeFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeFrom.setAdapter(spinnerAgeFrom_Adapter);
        spinnerAgeFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strFromAgeId = ageObjectArrayList.get(i).id;
                strFromAge = ageObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //To Age
        spinnerAgeTo = view.findViewById(R.id.spinnerAgeTo);
        spinnerAgeTo_Adapter = new ArrayAdapter<CommonCountObject>(getContext(), android.R.layout.simple_spinner_item, ageObjectArrayList);
        spinnerAgeTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeTo.setAdapter(spinnerAgeTo_Adapter);
        spinnerAgeTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // On selecting a spinner item
                String item = adapterView.getItemAtPosition(i).toString();
                //showToast(siteTaskCategoryObjArrayList.get(i).task);
                //category = categoryList.get(i).getCategoryID();
                strToAgeId = ageObjectArrayList.get(i).id;
                strToAge = ageObjectArrayList.get(i).count;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        webcallHeight();
    }

    private void webcallHeight() {

//        Helper_Method.showProgressBar(getContext(), getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETHeight();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    heightObjectArrayList = new ArrayList<>();
                    heightObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("height_list");
                            heightObjectArrayList.add(new HeightObject("0", "Select Height"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    heightObjectArrayList.add(new HeightObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (heightObjectArrayList.size() == 0) {


                            } else {

                                //From height
                                spinnerHeightFrom.setTitle("Height of bride / groom");
                                spinnerHeightFrom_Adapter = new ArrayAdapter<HeightObject>(getContext(), android.R.layout.simple_spinner_item, heightObjectArrayList);
                                spinnerHeightFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHeightFrom.setAdapter(spinnerHeightFrom_Adapter);
                                spinnerHeightFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strHeightFromId = heightObjectArrayList.get(i).id;
                                        strHeightFrom = heightObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < heightObjectArrayList.size(); k++) {
                                        if (heightObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerHeightFrom.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                                //To height
                                spinnerHeightTo.setTitle("Height of bride / groom");
                                spinnerHeightTo_Adapter = new ArrayAdapter<HeightObject>(getContext(), android.R.layout.simple_spinner_item, heightObjectArrayList);
                                spinnerHeightTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerHeightTo.setAdapter(spinnerHeightTo_Adapter);
                                spinnerHeightTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strHeightToId = heightObjectArrayList.get(i).id;
                                        strHeightTo = heightObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < heightObjectArrayList.size(); k++) {
                                        if (heightObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerHeightTo.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            heightObjectArrayList.clear();

                            //fromHeight
                            spinnerHeightFrom.setTitle("Height of bride / groom");
                            spinnerHeightFrom_Adapter = new ArrayAdapter<HeightObject>(getContext(), android.R.layout.simple_spinner_item, heightObjectArrayList);
                            spinnerHeightFrom_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHeightFrom.setAdapter(spinnerHeightFrom_Adapter);

                            //To Height
                            spinnerHeightTo.setTitle("Height of bride / groom");
                            spinnerHeightTo_Adapter = new ArrayAdapter<HeightObject>(getContext(), android.R.layout.simple_spinner_item, heightObjectArrayList);
                            spinnerHeightTo_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerHeightTo.setAdapter(spinnerHeightTo_Adapter);

                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        //Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    // Helper_Method.dismissProgessBar();

                } finally {
                    webcallStateList();
                    //Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getContext().getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallStateList() {

        // Helper_Method.showProgressBar(getContext(), getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETState();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    stateObjectArrayList = new ArrayList<>();
                    stateObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("states_list");
                            stateObjectArrayList.add(new StateObject("-1", "Select State"));
                            stateObjectArrayList.add(new StateObject("-1", "ANY"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    stateObjectArrayList.add(new StateObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    //Helper_Method.dismissProgessBar();

                                }
                            }

                            if (stateObjectArrayList.size() == 0) {


                            } else {
                                spinnerStatelist.setTitle("Select state of bride / groom");
                                spinnerStatelist_Adapter = new ArrayAdapter<StateObject>(
                                        getContext(), android.R.layout.simple_spinner_item, stateObjectArrayList);
                                spinnerStatelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerStatelist.setAdapter(spinnerStatelist_Adapter);
                                spinnerStatelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strStateId = stateObjectArrayList.get(i).id;
                                        strState = stateObjectArrayList.get(i).name;
                                        webcallCityList(strStateId);
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < stateObjectArrayList.size(); k++) {
                                        if (stateObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerStatelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            stateObjectArrayList.clear();
                            spinnerStatelist.setTitle("Select state of bride / groom");
                            spinnerStatelist_Adapter = new ArrayAdapter<StateObject>(getContext(), android.R.layout.simple_spinner_item, stateObjectArrayList);
                            spinnerStatelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerStatelist.setAdapter(spinnerStatelist_Adapter);
                            webcallCityList("0");
                            // Helper_Method.toaster(getContext(), stringMsg);
                            // scheduleDismiss();


                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } /*finally {
                    Helper_Method.dismissProgessBar();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void webcallCityList(String state_id) {

        // Helper_Method.showProgressBar(getContext(), getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTCity(state_id);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    cityObjectArrayList = new ArrayList<>();
                    cityObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("city_list");
                            cityObjectArrayList.add(new CityObject("0", "Select City"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    cityObjectArrayList.add(new CityObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (cityObjectArrayList.size() == 0) {


                            } else {
                                spinnerCitylist.setTitle("Select City");
                                spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(
                                        getActivity(), android.R.layout.simple_spinner_item, cityObjectArrayList);
                                spinnerCitylist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCitylist.setAdapter(spinnerCitylist_Adapter);
                                spinnerCitylist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strCityId = cityObjectArrayList.get(i).id;
                                        strCity = cityObjectArrayList.get(i).city_name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < cityObjectArrayList.size(); k++) {
                                        if (cityObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerCitylist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            cityObjectArrayList.clear();
                            spinnerCitylist.setTitle("Select City");
                            spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(getContext(), android.R.layout.simple_spinner_item, cityObjectArrayList);
                            spinnerCitylist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCitylist.setAdapter(spinnerCitylist_Adapter);
                            // Helper_Method.toaster(getContext(), stringMsg);
                            // scheduleDismiss();


                        }
                    } catch (JSONException e) {
                        //scheduleDismiss();
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    //scheduleDismiss();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
                //scheduleDismiss();
                Helper_Method.dismissProgessBar();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && resultCode == RESULT_OK) {

            } else if (requestCode == 200 && resultCode == RESULT_OK) {
                if (SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID) != null
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).isEmpty()
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("null")
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(getContext(), IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvMotherTougue.setText(SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE));
                } else {
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void maritalStatusCommaSepratedString() {

        listMaritalStatusId.clear();
        listMaritalStatus.clear();
        for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {

            if (maritalStatusObjArrayList.get(i).isSelected()) {
                strMaritalStatusId = maritalStatusObjArrayList.get(i).getId();
                strMaritalStatus = maritalStatusObjArrayList.get(i).getName();
                Log.d("MyTag", "commasepratedString: District Id :" + strMaritalStatusId);
                Log.d("MyTag", "commasepratedString: Taluka Id :" + strMaritalStatus);

                listMaritalStatusId.add(strMaritalStatusId);
                listMaritalStatus.add(strMaritalStatus);
                Log.d("MyTag", "listMaritalStatusId ids: " + maritalStatusObjArrayList.get(i).getId());
            }

            allMaritalStatusIds = "";
            for (String strIds : listMaritalStatusId) {
                allMaritalStatusIds += strIds + ",";
            }
            if (allMaritalStatusIds.endsWith(",")) {
                allMaritalStatusIds = allMaritalStatusIds.substring(0, allMaritalStatusIds.length() - 1);

            }

            allMaritalStatusString = "";
            for (String strData : listMaritalStatus) {
                allMaritalStatusString += strData + ",";
            }
            if (allMaritalStatusString.endsWith(",")) {
                allMaritalStatusString = allMaritalStatusString.substring(0, allMaritalStatusString.length() - 1);
            }
        }
        Log.d("mytag", "commaseprated Brands ids: " + allMaritalStatusIds);
        Log.d("mytag", "commaseprated Brands names: " + allMaritalStatusString);
        Log.d("mytag", "listMaritalStatusId ids: " + listMaritalStatus.size());
        Log.d("mytag", "listMaritalStatusId: " + listMaritalStatusId.size());

        SharedPref.setPrefs(getContext(), IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID, allMaritalStatusIds.toString());
        SharedPref.setPrefs(getContext(), IConstant.PREFERED_PARTNER_MARITAL_STATUS, allMaritalStatusString.toString());
    }


}