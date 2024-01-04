package com.wind.anuroopjodi.InsideActivities.Fragmentsss;

import android.annotation.SuppressLint;
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
import com.wind.anuroopjodi.Adapter.DietAdapter;
import com.wind.anuroopjodi.Adapter.DrinkingAdapter;
import com.wind.anuroopjodi.Adapter.GenderAdapter;
import com.wind.anuroopjodi.Adapter.ManglikAdapter;
import com.wind.anuroopjodi.Adapter.PhysicalStatusAdapter;
import com.wind.anuroopjodi.Adapter.PrefredPartenerMaritalStatusAdapter;
import com.wind.anuroopjodi.Adapter.SmokingAdapter;
import com.wind.anuroopjodi.Adapter.WorkingAdapter;
import com.wind.anuroopjodi.Constant.IConstant;
import com.wind.anuroopjodi.Constant.IUrls;
import com.wind.anuroopjodi.Constant.Interface;
import com.wind.anuroopjodi.Helper.ConnectionDetector;
import com.wind.anuroopjodi.Helper.Helper_Method;
import com.wind.anuroopjodi.Helper.RecyclerItemClickListener;
import com.wind.anuroopjodi.Helper.SharedPref;
import com.wind.anuroopjodi.Helper.Validations;
import com.wind.anuroopjodi.InitialActivities.PrefredPartner.PreferedPartnerEducationMutiSelectionActivity;
import com.wind.anuroopjodi.InitialActivities.PrefredPartner.PreferedPartnerMotherTongueActivity;
import com.wind.anuroopjodi.InitialActivities.RegistrationSteps.PackageActivity;
import com.wind.anuroopjodi.InsideActivities.Vijendra.SearchProfileListActivity;
import com.wind.anuroopjodi.Object.Caste;
import com.wind.anuroopjodi.Object.CityObject;
import com.wind.anuroopjodi.Object.CommonCountObject;
import com.wind.anuroopjodi.Object.CommonYesNoObject;
import com.wind.anuroopjodi.Object.DietObject;
import com.wind.anuroopjodi.Object.GenderObj;
import com.wind.anuroopjodi.Object.HeightObject;
import com.wind.anuroopjodi.Object.MaritalStatusObj;
import com.wind.anuroopjodi.Object.PhysicalStatusObj;
import com.wind.anuroopjodi.Object.ProfessionObject;
import com.wind.anuroopjodi.Object.StateObject;
import com.wind.anuroopjodi.Object.SubcasteObject;
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
 * Use the {@link AdvancedSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvancedSearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "PreferredActivity";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private Activity getContext();
    private TextView tvTermsAndConditions;
    private Validations validations;
    private ConnectionDetector connectionDetector;
    private int mNoOfColumns = 3;
    private String user_profile_path = null;

    //Age Spinner
    private ArrayList<CommonCountObject> ageObjectArrayList;
    private Spinner spinnerAgeFrom, spinnerAgeTo;
    private ArrayAdapter<CommonCountObject> spinnerAgeFrom_Adapter, spinnerAgeTo_Adapter;
    private String strFromAgeId = "0", strFromAge, strToAgeId = "0", strToAge;

    //Height Spinner
    private ArrayList<HeightObject> heightObjectArrayList;
    private SearchableSpinner spinnerHeightFrom, spinnerHeightTo;
    private ArrayAdapter<HeightObject> spinnerHeightFrom_Adapter, spinnerHeightTo_Adapter;
    private String strHeightFromId = "0", strHeightFrom, strHeightToId = "0", strHeightTo;

    //Caste Spinner Zone
    private ArrayList<Caste> casteArrayList;
    private SearchableSpinner spinnerCastelist;
    private ArrayAdapter<Caste> spinnerCastelist_Adapter;
    private String strCasteId = "0", strCaste;

    //Subcaste Spinner Zone
    private ArrayList<SubcasteObject> subcasteObjectArrayList;
    private SearchableSpinner spinnerSubcastelist;
    private ArrayAdapter<SubcasteObject> spinnerSubcastelist_Adapter;
    private String strSubCasteId = "0", strSubCaste;

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

    //Manglik Types
    private ArrayList<CommonYesNoObject> manglikObjArrayList, workingStatusObjArrayList;
    private RecyclerView rvManglik, rvWorkingStatus;
    private ManglikAdapter manglikAdapter;
    private WorkingAdapter workingAdapter;
    private GridLayoutManager getGridLayoutManagerManglik, getGridLayoutManagerWorking;
    private String strManglik = "0"/*, strWorking = null*/;

    //Physical Status Types
    private ArrayList<PhysicalStatusObj> physicalStatusObjArrayList;
    private RecyclerView rvPhysicalStatus;
    private PhysicalStatusAdapter physicalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerPhysicalStatus;
    private String strPhysicalStatus = "";

    //Marital Status Types
    private ArrayList<MaritalStatusObj> maritalStatusObjArrayList;
    private RecyclerView rvMaritalStatus;
    private PrefredPartenerMaritalStatusAdapter maritalStatusAdapter;
    private GridLayoutManager getGridLayoutManagerMaritalStatus;
    private String strMaritalStatusSingle = "";

    //Profession
    private ArrayList<ProfessionObject> professionObjectArrayList;
    private SearchableSpinner spinnerProfession;
    private ArrayAdapter<ProfessionObject> spinnerProfession_Adapter;
    private String strProfessionId = "0", strProfession;

    //Diet Types
    private ArrayList<DietObject> dietObjectArrayList;
    private RecyclerView rvDiet;
    private DietAdapter dietAdapter;
    private GridLayoutManager getGridLayoutManagerDiet;
    private String strDiet = "";

    //Smoking Types
    private ArrayList<CommonYesNoObject> smokingObjArrayList;
    private RecyclerView rvSmoking;
    private SmokingAdapter smokingAdapter;
    private GridLayoutManager getGridLayoutManagerSmoking;
    private String strSmoking = "";

    //Drinking Types
    private ArrayList<CommonYesNoObject> drinkingObjArrayList;
    private RecyclerView rvDrinking;
    private DrinkingAdapter drinkingAdapter;
    private GridLayoutManager getGetGridLayoutManagerDrinking;
    private String strDrinking = "";

    //Gender Types
    private ArrayList<GenderObj> genderObjArrayList;
    private RecyclerView rvGender;
    private GenderAdapter genderAdapter;
    private GridLayoutManager getGridLayoutManagerGender;
    private String strGender = "Male";

    private TextView tvEducation, tvMotherTougue;
    private EditText etGotra;

    private String strMaritalStatusId, strMaritalStatus, allMaritalStatusString, allMaritalStatusIds;
    private List<String> listMaritalStatusId, listMaritalStatus;

    private String strWorkingId, strWorking, allWorkingString, allWorkingIds;
    private List<String> listWorkingId, listWorking;
    private EditText etName, etMobile;

    private View view;

    public AdvancedSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvancedSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvancedSearchFragment newInstance(String param1, String param2) {
        AdvancedSearchFragment fragment = new AdvancedSearchFragment();
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

  /*  @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityContext = context;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_advanced_search, container, false);

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

        workingStatusObjArrayList = new ArrayList<>();
        listWorkingId = new ArrayList<>();
        listWorking = new ArrayList<>();

        spinnerHeightFrom = view.findViewById(R.id.spinnerHeightFrom);
        spinnerHeightTo = view.findViewById(R.id.spinnerHeightTo);
        spinnerStatelist = view.findViewById(R.id.spinnerStatelist);
        spinnerCitylist = view.findViewById(R.id.spinnerCitylist);
        spinnerSubcastelist = view.findViewById(R.id.spinnerSubcastelist);
        spinnerCastelist = view.findViewById(R.id.spinnerCastelist);
        spinnerProfession = view.findViewById(R.id.spinnerProfession);
        tvEducation = view.findViewById(R.id.tvEducation);
        tvMotherTougue = view.findViewById(R.id.tvMotherTougue);
        etGotra = view.findViewById(R.id.etGotra);

        etName = view.findViewById(R.id.etName);
        etMobile = view.findViewById(R.id.etMobile);

        //Education
        if (SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID) != null
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).isEmpty()
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("null")
                && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("")) {

            //String[] myArray = SharedPref.getPrefs(getContext(), IConstant.EDUCATION_ID).split(",");
            //selectedBrandsIds = Arrays.asList(myArray);
            tvEducation.setText(SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_NAME));
        } else {
        }

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
                workingCommaSepratedString();

                if (strCityId.equals("0")) {
                    strCityId = "";
                }

                if (strStateId.equals("0")) {
                    strStateId = "";
                }



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


                                    Log.d("Data", "gender: " + strGender);
                                    Log.d("Data", "age_from: " + strFromAgeId);
                                    Log.d("Data", "age_to: " + strToAgeId);
                                    Log.d("Data", "height_from: " + strHeightFromId);
                                    Log.d("Data", "height_to: " + strHeightToId);
                                    Log.d("Data", "marital_status: " + SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID));
                                    Log.d("Data", "city: " + strCityId);
                                    Log.d("Data", "state: " + strStateId);
                                    Log.d("Data", "mother_toungue: " + SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID));
                                    Log.d("Data", "manglik: " + strManglik);
                                    Log.d("Data", "physical_status: " + strPhysicalStatus);
                                    Log.d("Data", "highest_education: " + SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID));
                                    Log.d("Data", "occupation: " + strProfessionId);
                                    Log.d("Data", "annual_income: " + "");
                                    Log.d("Data", "eating: " + strDiet);
                                    Log.d("Data", "drinking: " + strDrinking);
                                    Log.d("Data", "smoking: " + strSmoking);

                                    Intent intent = new Intent(getContext(), SearchProfileListActivity.class);
                                    intent.putExtra(IConstant.Gender, strGender);
                                    intent.putExtra(IConstant.AgeFrom, strFromAgeId);
                                    intent.putExtra(IConstant.AgeTo, strToAgeId);
                                    intent.putExtra(IConstant.HeightFrom, strHeightFromId);
                                    intent.putExtra(IConstant.HeightTo, strHeightToId);
                                    intent.putExtra(IConstant.MaritalStatus,SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID));
                                    intent.putExtra(IConstant.StateID, strStateId);
                                    intent.putExtra(IConstant.CityID, strCityId);
                                    intent.putExtra(IConstant.MotherTounge, SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID));

                                    intent.putExtra(IConstant.ManglikID, strManglik);
                                    intent.putExtra(IConstant.PhysicalStatusId, strPhysicalStatus);
                                    intent.putExtra(IConstant.EducationID, SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID));
                                    intent.putExtra(IConstant.ProfessionID, strProfessionId);
                                    intent.putExtra(IConstant.AnnualIncome, "");
                                    intent.putExtra(IConstant.EatingId, strDiet);
                                    intent.putExtra(IConstant.DrinkingId, strDrinking);
                                    intent.putExtra(IConstant.SmokingId, strSmoking);
                                    intent.putExtra(IConstant.Name, etName.getText().toString().trim());
                                    intent.putExtra(IConstant.Number, etMobile.getText().toString().trim());
                                    intent.putExtra(IConstant.SUBCASTE, strSubCasteId);
                                    intent.putExtra(IConstant.SearchType, "1"); //1: advanced 0: simple

                                    startActivity(intent);

                                    //webcallProfessionDetails();
                                } else {
                                    Toast.makeText(getContext(), "" + getResources().getString(R.string.internet_connection_required), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getContext(), "Select Heght To", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Select Heght From", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getContext(), "Select Age To", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), "Select Age From", Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PreferedPartnerEducationMutiSelectionActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        tvMotherTougue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PreferedPartnerMotherTongueActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        WorkingStatus();
        webcallprofession();
        PhysicalStatus();

    }

    private void PhysicalStatus() {

        rvPhysicalStatus = view.findViewById(R.id.rvPhysicalStatus);
        physicalStatusObjArrayList = new ArrayList<>();
        physicalStatusObjArrayList.add(new PhysicalStatusObj("-1", "Doesn't matter", true));
        physicalStatusObjArrayList.add(new PhysicalStatusObj("No", "Normal", false));
        physicalStatusObjArrayList.add(new PhysicalStatusObj("Yes", "Physical Challenged", false));

        if (physicalStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvPhysicalStatus.setVisibility(View.GONE);

        } else {


            rvPhysicalStatus.setHasFixedSize(true);
            rvPhysicalStatus.setNestedScrollingEnabled(false);
            // physicalStatusObjArrayList.get(0).setSelected(true);
            // physicalStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            physicalStatusAdapter = new PhysicalStatusAdapter(getContext(), physicalStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(_act);
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvPhysicalStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerPhysicalStatus = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerPhysicalStatus.setOrientation(RecyclerView.VERTICAL);
            rvPhysicalStatus.setLayoutManager(getGridLayoutManagerPhysicalStatus);
            rvPhysicalStatus.setItemAnimator(new DefaultItemAnimator());
            rvPhysicalStatus.setAdapter(physicalStatusAdapter);
            //profileCreatedByAdapter.setSelected(0);
            physicalStatusAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvPhysicalStatus.setVisibility(View.VISIBLE);
            Gender();
            rvPhysicalStatus.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < physicalStatusObjArrayList.size(); i++) {

                                if (i == position) {
                                    physicalStatusObjArrayList.get(i).setSelected(true);
                                } else {
                                    physicalStatusObjArrayList.get(i).setSelected(false);
                                }
                            }
                            // pos = profileCreatedForAdapter.setSelected(position);
                            physicalStatusAdapter.notifyDataSetChanged();
                            strPhysicalStatus = physicalStatusObjArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strPhysicalStatus);
                            if (strPhysicalStatus.equals("Physical Challenged")) {
                                strPhysicalStatus = "1";
                            } else {
                                strPhysicalStatus = "0";
                            }

                            if (strPhysicalStatus.equals("Doesn't matter")) {
                                strPhysicalStatus = "";
                            }
                            Log.d("TAG", "onItemClick: " + strPhysicalStatus);

                        }
                    })
            );
        }
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
    }

    private void webcallprofession() {

        // Helper_Method.showProgressBar(getContext(), getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETProfession();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    professionObjectArrayList = new ArrayList<>();
                    professionObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("occupation_list");
                            professionObjectArrayList.add(new ProfessionObject("0", "Select Profession"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    professionObjectArrayList.add(new ProfessionObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (professionObjectArrayList.size() == 0) {


                            } else {
                                spinnerProfession.setTitle("Height of bride / groom");
                                spinnerProfession_Adapter = new ArrayAdapter<ProfessionObject>(getContext(), android.R.layout.simple_spinner_item, professionObjectArrayList);
                                spinnerProfession_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerProfession.setAdapter(spinnerProfession_Adapter);
                                Helper_Method.dismissProgessBar();
                                spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strProfessionId = professionObjectArrayList.get(i).id;
                                        strProfession = professionObjectArrayList.get(i).name;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "DESIGNATION: " + SharedPref.getPrefs(getContext(), IConstant.DESIGNATION));

                                if (SharedPref.getPrefs(getContext(), IConstant.OCCUPATION_ID) != null &&
                                        !SharedPref.getPrefs(getContext(), IConstant.OCCUPATION_ID).isEmpty() &&
                                        !SharedPref.getPrefs(getContext(), IConstant.OCCUPATION_ID).equals("null") &&
                                        !SharedPref.getPrefs(getContext(), IConstant.OCCUPATION_ID).equals("")) {

                                    for (int k = 0; k < professionObjectArrayList.size(); k++) {
                                        Log.d("Vijendra", "getName: " + professionObjectArrayList.get(k).getId());
                                        if (professionObjectArrayList.get(k).getId().equals
                                                (SharedPref.getPrefs(getContext(), IConstant.OCCUPATION_ID))) {
                                            spinnerProfession.setSelection(k);
                                        }
                                    }
                                } else {

                                }

                            }

                        } else {
                            professionObjectArrayList.clear();
                            spinnerProfession.setTitle("Height of bride / groom");
                            spinnerProfession_Adapter = new ArrayAdapter<ProfessionObject>(getContext(), android.R.layout.simple_spinner_item, professionObjectArrayList);
                            spinnerProfession_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerProfession.setAdapter(spinnerProfession_Adapter);
                            Helper_Method.dismissProgessBar();


                        }
                    } catch (JSONException e) {
                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                } finally {
                    Helper_Method.dismissProgessBar();
                    Diet();
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

    private void webcallProfessionDetails() {

        Helper_Method.showProgressBar(getContext(), "Updating Partner Prefrences Details...");
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.POSTPatnerPrefrences(strFromAge, strToAge, strHeightFromId, strHeightToId,
                SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MARITAL_STATUS_ID),
                SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_MOTHER_TONGUE_ID),
                strSubCasteId, etGotra.getText().toString().trim(), strManglik, strStateId, strCityId,
                SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID),
                SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_WORKING_NAME),
                SharedPref.getPrefs(getContext(), IConstant.USER_ID));

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);

                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");

                        if (stringCode.equalsIgnoreCase("true")) {
                            user_profile_path = i.getString("user_profile_path");


                            JSONArray jsonArray = i.getJSONArray("user_data");
                            JSONObject jsonObjectData = jsonArray.getJSONObject(0);

                            SharedPref.setPrefs(getContext(), IConstant.USER_ID, String.valueOf(jsonObjectData.getString("id")));
                            SharedPref.setPrefs(getContext(), IConstant.PACKAGE, String.valueOf(jsonObjectData.getString("package")));
                            SharedPref.setPrefs(getContext(), IConstant.BIRTH_TIME, String.valueOf(jsonObjectData.getString("birthTime")));
                            SharedPref.setPrefs(getContext(), IConstant.BIRTH_PLACE, String.valueOf(jsonObjectData.getString("birthPlace")));
                            SharedPref.setPrefs(getContext(), IConstant.ABOUT_ME, String.valueOf(jsonObjectData.getString("profileDescription")));
                            //SharedPref.setPrefs(getContext(), IConstant.USER_ID, jsonObjectData.getString("loginId"));
                            SharedPref.setPrefs(getContext(), IConstant.USER_PHOTO, String.valueOf(user_profile_path + jsonObjectData.getString("ProfilePhoto")));
                            SharedPref.setPrefs(getContext(), IConstant.PHOTO_APPROVED, String.valueOf(jsonObjectData.getString("photoApproved")));
                            SharedPref.setPrefs(getContext(), IConstant.PROFILE_ID, String.valueOf(jsonObjectData.getString("registrationNumber")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_FIRST_NAME, String.valueOf(jsonObjectData.getString("firstName")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_EMAIL, String.valueOf(jsonObjectData.getString("emailId")));
                            SharedPref.setPrefs(getContext(), IConstant.DISABILITY, String.valueOf(jsonObjectData.getString("disability")));
                            SharedPref.setPrefs(getContext(), IConstant.STATE_ID, String.valueOf(jsonObjectData.getString("state")));
                            SharedPref.setPrefs(getContext(), IConstant.CITY_ID, String.valueOf(jsonObjectData.getString("city")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_MOBILE, String.valueOf(jsonObjectData.getString("mobileNumber")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_LAST_NAME, String.valueOf(jsonObjectData.getString("lastName")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_GENDER, String.valueOf(jsonObjectData.getString("gender")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_MARITAL_STATUS_ID, String.valueOf(jsonObjectData.getString("maritalStatus")));
                            SharedPref.setPrefs(getContext(), IConstant.GOTRA, String.valueOf(jsonObjectData.getString("gotra")));
                            SharedPref.setPrefs(getContext(), IConstant.BIRTH_DATE, String.valueOf(jsonObjectData.getString("birthDate")));
                            SharedPref.setPrefs(getContext(), IConstant.RELIGION, String.valueOf(jsonObjectData.getString("religion")));
                            SharedPref.setPrefs(getContext(), IConstant.LANGUAGE_ID, String.valueOf(jsonObjectData.getString("language")));
                            SharedPref.setPrefs(getContext(), IConstant.LIFE_STYLE, String.valueOf(jsonObjectData.getString("lifestyle")));
                            SharedPref.setPrefs(getContext(), IConstant.HEIGHT_ID, String.valueOf(jsonObjectData.getString("height")));
                            SharedPref.setPrefs(getContext(), IConstant.WEIGHT, String.valueOf(jsonObjectData.getString("weight")));
                            SharedPref.setPrefs(getContext(), IConstant.NAKSHATRA, String.valueOf(jsonObjectData.getString("nakshatra")));
                            SharedPref.setPrefs(getContext(), IConstant.GAN, String.valueOf(jsonObjectData.getString("gan")));
                            SharedPref.setPrefs(getContext(), IConstant.CHARAN, String.valueOf(jsonObjectData.getString("charan")));
                            SharedPref.setPrefs(getContext(), IConstant.BLOOD, String.valueOf(jsonObjectData.getString("blood")));
                            SharedPref.setPrefs(getContext(), IConstant.PROFILE_FOR, String.valueOf(jsonObjectData.getString("profileFor")));
                            SharedPref.setPrefs(getContext(), IConstant.VERIFIED, String.valueOf(jsonObjectData.getString("verified")));
                            SharedPref.setPrefs(getContext(), IConstant.STATUS, String.valueOf(jsonObjectData.getString("status")));
                            SharedPref.setPrefs(getContext(), IConstant.FEATURED, String.valueOf(jsonObjectData.getString("featured")));
                            SharedPref.setPrefs(getContext(), IConstant.PAYMENT, String.valueOf(jsonObjectData.getString("payment")));
                            //SharedPref.setPrefs(getContext(), IConstant.USER_ID, jsonObjectData.getString("entryDate"));
                            SharedPref.setPrefs(getContext(), IConstant.SUBCASTE, String.valueOf(jsonObjectData.getString("subCast")));
                            SharedPref.setPrefs(getContext(), IConstant.INCOME, String.valueOf(jsonObjectData.getString("income")));
                            SharedPref.setPrefs(getContext(), IConstant.PHYSIQUE, String.valueOf(jsonObjectData.getString("physique")));
                            SharedPref.setPrefs(getContext(), IConstant.OCCUPATION_ID, String.valueOf(jsonObjectData.getString("occupation")));
                            SharedPref.setPrefs(getContext(), IConstant.EDUCATION_ID, String.valueOf(jsonObjectData.getString("education")));
                            SharedPref.setPrefs(getContext(), IConstant.COMPANY, String.valueOf(jsonObjectData.getString("company")));
                            SharedPref.setPrefs(getContext(), IConstant.DESIGNATION, String.valueOf(jsonObjectData.getString("designation")));
                            SharedPref.setPrefs(getContext(), IConstant.JOB_LOCATION, String.valueOf(jsonObjectData.getString("jobLocation")));
                            SharedPref.setPrefs(getContext(), IConstant.OTHER_INCOME, String.valueOf(jsonObjectData.getString("otherIncome")));
                            SharedPref.setPrefs(getContext(), IConstant.COMPLEXION, String.valueOf(jsonObjectData.getString("complexion")));
                            SharedPref.setPrefs(getContext(), IConstant.MANGLIK, String.valueOf(jsonObjectData.getString("manglik")));
                            SharedPref.setPrefs(getContext(), IConstant.NADI, String.valueOf(jsonObjectData.getString("nadi")));
                            SharedPref.setPrefs(getContext(), IConstant.RASHI, String.valueOf(jsonObjectData.getString("rashi")));
                            SharedPref.setPrefs(getContext(), IConstant.SMOKING, String.valueOf(jsonObjectData.getString("smoking")));
                            SharedPref.setPrefs(getContext(), IConstant.HOTELING, String.valueOf(jsonObjectData.getString("hoteling")));
                            SharedPref.setPrefs(getContext(), IConstant.PRIMARY_LANGUAGE, String.valueOf(jsonObjectData.getString("primaryLanguage")));
                            SharedPref.setPrefs(getContext(), IConstant.DRINKING, String.valueOf(jsonObjectData.getString("drinking")));
                            SharedPref.setPrefs(getContext(), IConstant.FATHER_NAME, String.valueOf(jsonObjectData.getString("fatherName")));
                            SharedPref.setPrefs(getContext(), IConstant.MOTHER_NAME, String.valueOf(jsonObjectData.getString("motherName")));
                            SharedPref.setPrefs(getContext(), IConstant.BROTHER, String.valueOf(jsonObjectData.getString("brother")));
                            SharedPref.setPrefs(getContext(), IConstant.SISTER, String.valueOf(jsonObjectData.getString("sister")));
                            SharedPref.setPrefs(getContext(), IConstant.FATHER_OCCUPATION, String.valueOf(jsonObjectData.getString("father_occupation")));
                            SharedPref.setPrefs(getContext(), IConstant.MOTHER_OCCUPATION, String.valueOf(jsonObjectData.getString("mother_occupation")));
                            SharedPref.setPrefs(getContext(), IConstant.FATHER_CONTACT, String.valueOf(jsonObjectData.getString("father_contact")));
                            SharedPref.setPrefs(getContext(), IConstant.MOTHER_CONTACT, String.valueOf(jsonObjectData.getString("mother_contact")));
                            SharedPref.setPrefs(getContext(), IConstant.SIBLING, String.valueOf(jsonObjectData.getString("sibling")));
                            SharedPref.setPrefs(getContext(), IConstant.MARRIED_SISTER, String.valueOf(jsonObjectData.getString("married_sister")));
                            SharedPref.setPrefs(getContext(), IConstant.MARRIED_BROTHER, String.valueOf(jsonObjectData.getString("married_brother")));
                            SharedPref.setPrefs(getContext(), IConstant.UNCLE_NAME, String.valueOf(jsonObjectData.getString("uncle_name")));
                            SharedPref.setPrefs(getContext(), IConstant.UNCLE_OCCUPATION, String.valueOf(jsonObjectData.getString("uncle_occupation")));
                            SharedPref.setPrefs(getContext(), IConstant.MARITAL_UNCLE_NAME, String.valueOf(jsonObjectData.getString("m_uncle_name")));
                            SharedPref.setPrefs(getContext(), IConstant.MARITAL_UNCLE_OCCUPATION, String.valueOf(jsonObjectData.getString("mama_occupation")));
                            SharedPref.setPrefs(getContext(), IConstant.HOBBY, String.valueOf(jsonObjectData.getString("hobby")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_ADDRESS, String.valueOf(jsonObjectData.getString("address")));
                            // SharedPref.setPrefs(getContext(), IConstant.USER_ID, jsonObjectData.getString("lastSeen"));
                            SharedPref.setPrefs(getContext(), IConstant.TRANSACTION_ID, String.valueOf(jsonObjectData.getString("transactionId")));
                         //   SharedPref.setPrefs(getContext(), IConstant.EMAIL_VERIFIED, String.valueOf(jsonObjectData.getString("email_verified")));
                            SharedPref.setPrefs(getContext(), IConstant.MOBILE_VERIFIED, String.valueOf(jsonObjectData.getString("mobile_verified")));
                            SharedPref.setPrefs(getContext(), IConstant.STEP_ONE, String.valueOf(jsonObjectData.getString("step1")));
                            SharedPref.setPrefs(getContext(), IConstant.STEP_TWO, String.valueOf(jsonObjectData.getString("step2")));
                            SharedPref.setPrefs(getContext(), IConstant.STEP_THREE, String.valueOf(jsonObjectData.getString("step3")));
                            SharedPref.setPrefs(getContext(), IConstant.STEP_FOUR, String.valueOf(jsonObjectData.getString("step4")));
                            SharedPref.setPrefs(getContext(), IConstant.STEP_FIVE, String.valueOf(jsonObjectData.getString("step5")));
                            SharedPref.setPrefs(getContext(), IConstant.PAYMENT_VERIFICATION, String.valueOf(jsonObjectData.getString("payment_verification")));
                            SharedPref.setPrefs(getContext(), IConstant.STATE_NAME, String.valueOf(jsonObjectData.getString("state_name")));
                            SharedPref.setPrefs(getContext(), IConstant.CITY_NAME, String.valueOf(jsonObjectData.getString("city_name")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_MARITAL_STATUS, String.valueOf(jsonObjectData.getString("marital_status")));
                            SharedPref.setPrefs(getContext(), IConstant.LANGUAGE_NAME, String.valueOf(jsonObjectData.getString("language_name")));
                            SharedPref.setPrefs(getContext(), IConstant.HEIGHT_NAME, String.valueOf(jsonObjectData.getString("user_height")));
                            SharedPref.setPrefs(getContext(), IConstant.OCCUPATION_NAME, String.valueOf(jsonObjectData.getString("occupation_name")));
                            SharedPref.setPrefs(getContext(), IConstant.EDUCATION_NAME, String.valueOf(jsonObjectData.getString("education_name")));
                            SharedPref.setPrefs(getContext(), IConstant.USER_IS_LOGIN, "true");

                            Helper_Method.hideSoftInput(getContext());
                            Helper_Method.dismissProgessBar();
                            Toast.makeText(getContext(), "" + stringMsg, Toast.LENGTH_SHORT).show();
                            //Helper_Method.intentActivity(getContext(), PackageActivity.class, false);
                            Intent intent = new Intent(getContext(), PackageActivity.class);
                            intent.putExtra("ActivityName", "PreferredPatner");
                            startActivity(intent);


                        } else {
                            //Helper_Method.toaster(getContext(), stringMsg);
                            Toast.makeText(getContext(), "" + stringMsg, Toast.LENGTH_SHORT).show();
                            Helper_Method.dismissProgessBar();

                        }


                    } catch (JSONException e) {

                        Helper_Method.dismissProgessBar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Helper_Method.dismissProgessBar();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                Log.d("Issue", "Technical Error");
                Helper_Method.dismissProgessBar();

            }
        });
    }

    private void WorkingStatus() {

        rvWorkingStatus = view.findViewById(R.id.rvWorkingStatus);
        workingStatusObjArrayList.clear();
        workingStatusObjArrayList.add(new CommonYesNoObject("1", "Yes", false));
        workingStatusObjArrayList.add(new CommonYesNoObject("2", "No", false));

        if (workingStatusObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvWorkingStatus.setVisibility(View.GONE);

        } else {


            rvWorkingStatus.setHasFixedSize(true);
            rvWorkingStatus.setNestedScrollingEnabled(false);
            // workingStatusObjArrayList.get(0).setSelected(true);
            // workingStatusObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            workingAdapter = new WorkingAdapter(getContext(), workingStatusObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvWorkingStatus.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerManglik = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerManglik.setOrientation(RecyclerView.VERTICAL);
            rvWorkingStatus.setLayoutManager(getGridLayoutManagerManglik);
            rvWorkingStatus.setItemAnimator(new DefaultItemAnimator());
            rvWorkingStatus.setAdapter(workingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            workingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvWorkingStatus.setVisibility(View.VISIBLE);

        }
        MaritalStatus();
    }

    private void MaritalStatus() {

        rvMaritalStatus = view.findViewById(R.id.rvMaritalStatus);

        maritalStatusObjArrayList.clear();
        maritalStatusObjArrayList = new ArrayList<>();
        maritalStatusObjArrayList.add(new MaritalStatusObj("-1", "Doesn't matter", true));
        maritalStatusObjArrayList.add(new MaritalStatusObj("1", "Single", false));
       // maritalStatusObjArrayList.add(new MaritalStatusObj("5", "Married", false));
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
//
//            rvMaritalStatus.addOnItemTouchListener(
//                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View view, int position) {
//
//                            maritalStatusObjArrayList.get(position).setSelected(true);
//
//                            for (int i = 0; i < maritalStatusObjArrayList.size(); i++) {
//                                if (i == position) {
//                                    maritalStatusObjArrayList.get(i).setSelected(true);
//                                } else {
//                                    maritalStatusObjArrayList.get(i).setSelected(false);
//                                }
//                            }
//
//                            // pos = profileCreatedForAdapter.setSelected(position);
//                            maritalStatusAdapter.notifyDataSetChanged();
//                            strMaritalStatusSingle = maritalStatusObjArrayList.get(position).id;
//                            Log.d(TAG, "onItemClick: " + strMaritalStatusSingle);
//
//                        }
//                    })
//            );
        }
        Manglik();
    }

    private void Manglik() {

        rvManglik = view.findViewById(R.id.rvManglik);
        manglikObjArrayList = new ArrayList<>();
        manglikObjArrayList.add(new CommonYesNoObject("-1", "Doesn't matter", true));
        manglikObjArrayList.add(new CommonYesNoObject("Yes", "Yes", false));
        manglikObjArrayList.add(new CommonYesNoObject("No", "No", false));

        if (manglikObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvManglik.setVisibility(View.GONE);

        } else {


            rvManglik.setHasFixedSize(true);
            rvManglik.setNestedScrollingEnabled(false);
            // manglikObjArrayList.get(0).setSelected(true);
            // manglikObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            manglikAdapter = new ManglikAdapter(getContext(), manglikObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvManglik.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerManglik = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerManglik.setOrientation(RecyclerView.VERTICAL);
            rvManglik.setLayoutManager(getGridLayoutManagerManglik);
            rvManglik.setItemAnimator(new DefaultItemAnimator());
            rvManglik.setAdapter(manglikAdapter);
            //profileCreatedByAdapter.setSelected(0);
            manglikAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvManglik.setVisibility(View.VISIBLE);

            rvManglik.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < manglikObjArrayList.size(); i++) {

                                if (i == position) {
                                    manglikObjArrayList.get(i).setSelected(true);
                                } else {
                                    manglikObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            manglikAdapter.notifyDataSetChanged();
                            strManglik = manglikObjArrayList.get(position).id;
                            if (strManglik.equals("-1")) {
                                strManglik = "";
                            }else if(strManglik.equals("Yes")) {
                                strManglik = "Yes";
                            } else {
                                strManglik = "No";
                            }
                            Log.d("TAG", "onItemClick: " + strManglik);


                        }
                    })
            );
        }
        Age();
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
                                spinnerHeightFrom.setTitle("Select Height");
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
                                spinnerHeightTo.setTitle("Select Height");
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
                    //Helper_Method.dismissProgessBar();
                    webcallCasteList();
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

    private void webcallCasteList() {

        // Helper_Method.showProgressBar(_act, getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETCaste();

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    casteArrayList = new ArrayList<>();
                    casteArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tag434", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("caste_list");
                            casteArrayList.add(new Caste("0", getActivity().getResources().getString(R.string.lbl_caste_hint)));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    casteArrayList.add(new Caste(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (casteArrayList.size() == 0) {


                            } else {
                                spinnerCastelist.setTitle(getResources().getString(R.string.lbl_caste_hint));
                                spinnerCastelist_Adapter = new ArrayAdapter<Caste>(getContext(), android.R.layout.simple_spinner_item, casteArrayList);
                                spinnerCastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCastelist.setAdapter(spinnerCastelist_Adapter);
                                spinnerCastelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @SuppressLint("SuspiciousIndentation")
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strCasteId = casteArrayList.get(i).getId();
                                        strCaste = casteArrayList.get(i).getCaste_name();

                                   //     Toast.makeText(getContext(), ""+strCasteId, Toast.LENGTH_SHORT).show();
                                        if(i != 0) {
                                            webcallSubcasteList(strCasteId);
                                        }
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });

                                Log.d("Vijendra", "SUBCASTE: " + SharedPref.getPrefs(getContext(), IConstant.CASTE));
                                if (SharedPref.getPrefs(getContext(), IConstant.CASTE) != null &&
                                        !SharedPref.getPrefs(getContext(), IConstant.CASTE).isEmpty() &&
                                        !SharedPref.getPrefs(getContext(), IConstant.CASTE).equals("null") &&
                                        !SharedPref.getPrefs(getContext(), IConstant.CASTE).equals("")) {

                                   /* for (int k = 0; k < casteArrayList.size(); k++) {
                                        if (casteArrayList.get(k).getCaste_name().equals(SharedPref.getPrefs(getContext(), IConstant.CASTE))) {
                                            spinnerCastelist.setSelection(k);
                                        }
                                    }*/
                                } else {

                                }

                            }

                        } else {
                            casteArrayList.clear();
                            spinnerCastelist.setTitle(getResources().getString(R.string.lbl_caste_hint));
                            spinnerCastelist_Adapter = new ArrayAdapter<Caste>(getContext(), android.R.layout.simple_spinner_item, casteArrayList);
                            spinnerCastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerCastelist.setAdapter(spinnerCastelist_Adapter);
                            // Helper_Method.toaster(_act, stringMsg);
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
                    webcallStateList();
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

    private void webcallSubcasteList(String casteID) {

        // Helper_Method.showProgressBar(getContext(), getResources().getString(R.string.lbl_loading));
        Interface api = IUrls.getRetrofit(IUrls.BASE_URL).create(Interface.class);
        Call<ResponseBody> result = api.GETSubcaste(casteID);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    subcasteObjectArrayList = new ArrayList<>();
                    subcasteObjectArrayList.clear();
                    output = response.body().string();
                    Log.d("my_tagCasrw", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("subcaste_list");

                            subcasteObjectArrayList.add(new SubcasteObject("0", "Select Subcaste"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    subcasteObjectArrayList.add(new SubcasteObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (subcasteObjectArrayList.size() == 0) {


                            } else {
                                spinnerSubcastelist.setTitle("Select subcaste for perfect match from your religion");
                                spinnerSubcastelist_Adapter = new ArrayAdapter<SubcasteObject>(getContext(), android.R.layout.simple_spinner_item, subcasteObjectArrayList);
                                spinnerSubcastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerSubcastelist.setAdapter(spinnerSubcastelist_Adapter);
                                spinnerSubcastelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        // On selecting a spinner item
                                        String item = adapterView.getItemAtPosition(i).toString();
                                        //showToast(siteTaskCategoryObjArrayList.get(i).task);
                                        //category = categoryList.get(i).getCategoryID();
                                        strSubCasteId = subcasteObjectArrayList.get(i).id;
                                        strSubCaste = subcasteObjectArrayList.get(i).casteName;
                                    }

                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        return;
                                    }
                                });
                             /*   if (complaintObject.district_name != null && !complaintObject.district_name.isEmpty() && !complaintObject.district_name.equals("null") && !complaintObject.district_name.equals("")) {

                                    for (int k = 0; k < subcasteObjectArrayList.size(); k++) {
                                        if (subcasteObjectArrayList.get(k).getDistrict_name_english().equals(complaintObject.district_name)) {
                                            spinnerSubcastelist.setSelection(k);
                                        }
                                    }
                                } else {

                                }*/

                            }

                        } else {
                            subcasteObjectArrayList.clear();
                            spinnerSubcastelist.setTitle("Select subcaste for perfect match from your religion");
                            spinnerSubcastelist_Adapter = new ArrayAdapter<SubcasteObject>(getContext(), android.R.layout.simple_spinner_item, subcasteObjectArrayList);
                            spinnerSubcastelist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSubcastelist.setAdapter(spinnerSubcastelist_Adapter);
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
                    webcallStateList();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
//                Log.d("Issue", getResources().getString(R.string.lbl_technical_error));
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
                    Log.d("my_tag333", "onResponseSachin: " + output);
                    try {
                        JSONObject i = new JSONObject(output);
                        String stringCode = i.getString("result");
                        String stringMsg = i.getString("reason");


                        if (stringCode.equalsIgnoreCase("true")) {

                            JSONArray jsonArray = i.getJSONArray("states_list");
                            stateObjectArrayList.add(new StateObject("-1", "Any"));
                            for (int index = 0; index < jsonArray.length(); index++) {
                                try {
                                    stateObjectArrayList.add(new StateObject(jsonArray.getJSONObject(index)));

                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Helper_Method.dismissProgessBar();

                                }
                            }

                            if (stateObjectArrayList.size() == 0) {


                            } else {
                                spinnerStatelist.setTitle("Select state of bride / groom");
                                spinnerStatelist_Adapter = new ArrayAdapter<StateObject>(getContext(), android.R.layout.simple_spinner_item, stateObjectArrayList);
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
                                if (getActivity() != null) {
                                    spinnerCitylist.setTitle("Select city to find the near matches");
                                    spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(getActivity(), android.R.layout.simple_spinner_item, cityObjectArrayList);
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

                            }

                        } else {
                            cityObjectArrayList.clear();
                            //spinnerCitylist.setTitle("Select city to find the near matches");
                            //spinnerCitylist_Adapter = new ArrayAdapter<CityObject>(getContext(), android.R.layout.simple_spinner_item, cityObjectArrayList);
                            //spinnerCitylist_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //spinnerCitylist.setAdapter(spinnerCitylist_Adapter);
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

                //String requiredValue = data.getStringExtra("key");
                //Helper_Method.intentActivity(getContext(), LoginActivity.class, true);

                if (SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID) != null
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).isEmpty()
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("null")
                        && !SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_ID).equals("")) {

                    //String[] myArray = SharedPref.getPrefs(getContext(), IConstant.EDUCATION_ID).split(",");
                    //selectedBrandsIds = Arrays.asList(myArray);
                    tvEducation.setText(SharedPref.getPrefs(getContext(), IConstant.PREFERED_PARTNER_EDUCATION_NAME));
                } else {
                }
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
            Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();
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

    public void workingCommaSepratedString() {
        listWorkingId.clear();
        listWorking.clear();
        for (int i = 0; i < workingStatusObjArrayList.size(); i++) {

            if (workingStatusObjArrayList.get(i).isSelected()) {
                strWorkingId = workingStatusObjArrayList.get(i).getId();
                strWorking = workingStatusObjArrayList.get(i).getName();
                Log.d("MyTag", "commasepratedString: District Id :" + strWorkingId);
                Log.d("MyTag", "commasepratedString: Taluka Id :" + strWorking);

                listWorkingId.add(strWorkingId);
                listWorking.add(strWorking);
                Log.d("MyTag", "listWorkingId ids: " + workingStatusObjArrayList.get(i).getId());
            }

            allWorkingIds = "";
            for (String strIds : listWorkingId) {
                allWorkingIds += strIds + ",";
            }
            if (allWorkingIds.endsWith(",")) {
                allWorkingIds = allWorkingIds.substring(0, allWorkingIds.length() - 1);

            }

            allWorkingString = "";
            for (String strData : listWorking) {
                allWorkingString += strData + ",";
            }
            if (allWorkingString.endsWith(",")) {
                allWorkingString = allWorkingString.substring(0, allWorkingString.length() - 1);
            }
        }
        Log.d("mytag", "commaseprated Brands ids: " + allWorkingIds);
        Log.d("mytag", "commaseprated Brands names: " + allWorkingString);
        Log.d("mytag", "listWorkingId ids: " + listWorking.size());
        Log.d("mytag", "listWorkingId: " + listWorkingId.size());

        SharedPref.setPrefs(getContext(), IConstant.PREFERED_PARTNER_WORKING_ID, allWorkingIds.toString());
        SharedPref.setPrefs(getContext(), IConstant.PREFERED_PARTNER_WORKING_NAME, allWorkingString.toString());
    }

    private void Diet() {
        Log.d(TAG, "In Diet: ");
        rvDiet = view.findViewById(R.id.rvDiet);
        dietObjectArrayList = new ArrayList<>();
        dietObjectArrayList.add(new DietObject("Both", "Both", true));
        dietObjectArrayList.add(new DietObject("Vegetarian", "Vegetarian", false));
        dietObjectArrayList.add(new DietObject("Non-Vegetarian", "Non-Vegetarian", false));

        if (dietObjectArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvDiet.setVisibility(View.GONE);

        } else {


            rvDiet.setHasFixedSize(true);
            rvDiet.setNestedScrollingEnabled(false);
            // dietObjectArrayList.get(0).setSelected(true);
            // dietObjectArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            dietAdapter = new DietAdapter(getContext(), dietObjectArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvDiet.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerDiet = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerDiet.setOrientation(RecyclerView.VERTICAL);
            rvDiet.setLayoutManager(getGridLayoutManagerDiet);
            rvDiet.setItemAnimator(new DefaultItemAnimator());
            rvDiet.setAdapter(dietAdapter);
            //profileCreatedByAdapter.setSelected(0);
            dietAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvDiet.setVisibility(View.VISIBLE);

            rvDiet.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < dietObjectArrayList.size(); i++) {

                                if (i == position) {
                                    dietObjectArrayList.get(i).setSelected(true);
                                } else {
                                    dietObjectArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            dietAdapter.notifyDataSetChanged();
                            strDiet = dietObjectArrayList.get(position).name;
                            Log.d("TAG", "onItemClick: " + strDiet);

                        }
                    })
            );
        }
        Smoking();
    }

    private void Smoking() {

        rvSmoking = view.findViewById(R.id.rvSmoking);
        smokingObjArrayList = new ArrayList<>();

        smokingObjArrayList.add(new CommonYesNoObject("-1", "Doesn't matter", true));
        smokingObjArrayList.add(new CommonYesNoObject("Yes", "Yes", false));
        smokingObjArrayList.add(new CommonYesNoObject("No", "No", false));

        if (smokingObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvSmoking.setVisibility(View.GONE);

        } else {


            rvSmoking.setHasFixedSize(true);
            rvSmoking.setNestedScrollingEnabled(false);
            // smokingObjArrayList.get(0).setSelected(true);
            // smokingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            smokingAdapter = new SmokingAdapter(getContext(), smokingObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvSmoking.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGridLayoutManagerSmoking = new GridLayoutManager(getContext(), mNoOfColumns);
            getGridLayoutManagerSmoking.setOrientation(RecyclerView.VERTICAL);
            rvSmoking.setLayoutManager(getGridLayoutManagerSmoking);
            rvSmoking.setItemAnimator(new DefaultItemAnimator());
            rvSmoking.setAdapter(smokingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            smokingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvSmoking.setVisibility(View.VISIBLE);

            rvSmoking.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < smokingObjArrayList.size(); i++) {

                                if (i == position) {
                                    smokingObjArrayList.get(i).setSelected(true);
                                } else {
                                    smokingObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            smokingAdapter.notifyDataSetChanged();
                            strSmoking = smokingObjArrayList.get(position).id;
                            Log.d("TAG", "onItemClick: " + strSmoking);

                        }
                    })
            );
        }
        Drinking();
    }

    private void Drinking() {

        rvDrinking = view.findViewById(R.id.rvDrinking);
        drinkingObjArrayList = new ArrayList<>();
        drinkingObjArrayList.add(new CommonYesNoObject("-1", "Doesn't matter", true));
        drinkingObjArrayList.add(new CommonYesNoObject("Yes", "Yes", false));
        drinkingObjArrayList.add(new CommonYesNoObject("No", "No", false));

        if (drinkingObjArrayList.size() == 0) {

            Helper_Method.dismissProgessBar();
            rvDrinking.setVisibility(View.GONE);

        } else {


            rvDrinking.setHasFixedSize(true);
            rvDrinking.setNestedScrollingEnabled(false);
            // drinkingObjArrayList.get(0).setSelected(true);
            // drinkingObjArrayList.add(new HomeCategoryObj("MM", "More", "http://the-best.co.in/backend/upload/category/dot.png"));
            drinkingAdapter = new DrinkingAdapter(getContext(), drinkingObjArrayList);
            /*linearLayoutManagerProfileCreatedByList = new LinearLayoutManager(getContext());
            linearLayoutManagerProfileCreatedByList.setOrientation(RecyclerView.HORIZONTAL);
            rvDrinking.setLayoutManager(linearLayoutManagerProfileCreatedByList);*/
            getGetGridLayoutManagerDrinking = new GridLayoutManager(getContext(), mNoOfColumns);
            getGetGridLayoutManagerDrinking.setOrientation(RecyclerView.VERTICAL);
            rvDrinking.setLayoutManager(getGetGridLayoutManagerDrinking);
            rvDrinking.setItemAnimator(new DefaultItemAnimator());
            rvDrinking.setAdapter(drinkingAdapter);
            //profileCreatedByAdapter.setSelected(0);
            drinkingAdapter.notifyDataSetChanged();
            Helper_Method.dismissProgessBar();
            rvDrinking.setVisibility(View.VISIBLE);

            rvDrinking.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            for (int i = 0; i < drinkingObjArrayList.size(); i++) {

                                if (i == position) {
                                    drinkingObjArrayList.get(i).setSelected(true);
                                } else {
                                    drinkingObjArrayList.get(i).setSelected(false);
                                }
                            }

                            // pos = profileCreatedForAdapter.setSelected(position);
                            drinkingAdapter.notifyDataSetChanged();
                            strDrinking = drinkingObjArrayList.get(position).id;
                            Log.d("TAG", "onItemClick: " + strDrinking);

                        }
                    })
            );
        }
        webcallHeight();
    }
}