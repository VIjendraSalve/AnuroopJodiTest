package com.whtech.anuroopjodi.InsideActivities.Fragmentsss;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.whtech.anuroopjodi.Constant.IConstant;
import com.whtech.anuroopjodi.Helper.ConnectionDetector;
import com.whtech.anuroopjodi.Helper.Helper_Method;
import com.whtech.anuroopjodi.Helper.SharedPref;
import com.whtech.anuroopjodi.Helper.Validations;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.BasicDetailsAddAndEditActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.FamilyDetailsActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.PersonalDetailsActivity;
import com.whtech.anuroopjodi.InitialActivities.RegistrationSteps.ProfessionalDetailsActivity;
import com.whtech.anuroopjodi.InsideActivities.DashboardActivity;
import com.whtech.anuroopjodi.R;
import com.whtech.anuroopjodi.my_library.Camera;
import com.whtech.anuroopjodi.my_library.CircleImageView;
import com.whtech.anuroopjodi.my_library.MyConfig;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public class ProfileFragment extends Fragment implements Camera.AsyncResponse {

    private Activity _act;
    private Validations validations;
    private ConnectionDetector connectionDetector;

    private ImageView ivProfileImage;
    private TextView tvFullName, tvAboutUs, tvMoreInformation;

    //Basic Details
    private TextView tvFirstName, tvLastName, tvDobName;
    private TextView tvBirthtime, tvBirthPlace, tvEmail;
    private TextView tvMobile, tvGender, tvMaritalStatus;
    private TextView tvCity, tvState, tvCountry;

    //Life Style
    private TextView tvDiet, tvSmoking, tvDrinking, tvHobby;

    //Other Information
    private TextView tvHeight, tvCaste, tvGotra, tvGan, tvNakshatra;
    private TextView tvCharan, tvManglik, tvNadi, tvRashi;

    //Family information
    private TextView tvFatherName, tvFatherOccupaition, tvFatherContactNo, tvMotherName, tvMotherOccupaition, tvMotherContactNo, tvSibling;
    private TextView tvBrotherCount, tvSisterCount, tvMarriedSister, tvMarriedBrothers, tvUncleName, tvUncleOccupation, tvMaternalUncleName;
    private TextView tvMaternalUncleOccupation;

    //Education and Jobs
    private TextView tvEducation, tvPrimaryEducationLanguage, tvOccupation, tvCompany, tvDesignation, tvIncome, tvOtherIncome;

    private ImageView ivEditBasicDetails, ivEditOtherInformation, ivEditFamilyInformation, ivEditEducationAndJob, ivEditProfilePhoto;
    private ProgressDialog progressDialog;
    private String profile_image_name = "", profile_image_path = "";
    private Button btn_update_profile;
    private Camera camera;
    private CircleImageView ivAdharImg1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Set title bar
        ((DashboardActivity) getActivity()).setActionBarTitle("Profile");

        _act = getActivity();
        validations = new Validations();
        connectionDetector = ConnectionDetector.getInstance(_act);
        Helper_Method.hideSoftInput(_act);


        tvFullName = root.findViewById(R.id.tvFullName);
        tvAboutUs = root.findViewById(R.id.tvAboutUs);
        tvMoreInformation = root.findViewById(R.id.tvMoreInformation);
        ivProfileImage = root.findViewById(R.id.ivProfileImage);

        tvAboutUs.setText(SharedPref.getPrefs(_act, IConstant.ABOUT_ME));

        tvFullName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME) + " " + SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
        tvMoreInformation.setText(SharedPref.getPrefs(_act, IConstant.HEIGHT_NAME) + " | " + SharedPref.getPrefs(_act, IConstant.WEIGHT) + "(in kg) | " + SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME) + " | "
                /*+ SharedPref.getPrefs(_act, IConstant.CITY_NAME) + ", "*/ + SharedPref.getPrefs(_act, IConstant.STATE_NAME) + ", india | " + SharedPref.getPrefs(_act, IConstant.OCCUPATION_NAME));

        Glide.with(_act)
                .load(SharedPref.getPrefs(_act, IConstant.USER_PHOTO))
                .apply(new RequestOptions().placeholder(R.drawable.default_user).error(R.drawable.default_user))
                .into(ivProfileImage);

        //Basic Details
        tvFirstName = root.findViewById(R.id.tvFirstName);
        tvLastName = root.findViewById(R.id.tvLastName);
        tvDobName = root.findViewById(R.id.tvDobName);
        tvBirthtime = root.findViewById(R.id.tvBirthtime);
        tvBirthPlace = root.findViewById(R.id.tvBirthPlace);
        tvEmail = root.findViewById(R.id.tvEmail);
        tvMobile = root.findViewById(R.id.tvMobile);
        tvGender = root.findViewById(R.id.tvGender);
        tvMaritalStatus = root.findViewById(R.id.tvMaritalStatus);
        tvCity = root.findViewById(R.id.tvCity);
        tvState = root.findViewById(R.id.tvState);
        tvCountry = root.findViewById(R.id.tvCountry);
        ivEditBasicDetails = root.findViewById(R.id.ivEditBasicDetails);
        ivEditOtherInformation = root.findViewById(R.id.ivEditOtherInformation);
        ivEditFamilyInformation = root.findViewById(R.id.ivEditFamilyInformation);
        ivEditEducationAndJob = root.findViewById(R.id.ivEditEducationAndJob);
        ivEditProfilePhoto = root.findViewById(R.id.ivEditProfilePhoto);
        ivAdharImg1 = (CircleImageView) root.findViewById(R.id.ivAdharImg1);

        tvFirstName.setText(SharedPref.getPrefs(_act, IConstant.USER_FIRST_NAME));
        tvLastName.setText(SharedPref.getPrefs(_act, IConstant.USER_LAST_NAME));
        tvDobName.setText(SharedPref.getPrefs(_act, IConstant.BIRTH_DATE));
        tvBirthtime.setText(SharedPref.getPrefs(_act, IConstant.BIRTH_TIME));
        tvBirthPlace.setText(SharedPref.getPrefs(_act, IConstant.BIRTH_PLACE));
        tvEmail.setText(SharedPref.getPrefs(_act, IConstant.USER_EMAIL));
        tvMobile.setText(SharedPref.getPrefs(_act, IConstant.USER_MOBILE));
        tvGender.setText(SharedPref.getPrefs(_act, IConstant.USER_GENDER));
        tvMaritalStatus.setText(SharedPref.getPrefs(_act, IConstant.USER_MARITAL_STATUS));
        tvCity.setText(SharedPref.getPrefs(_act, IConstant.CITY_NAME));
        tvState.setText(SharedPref.getPrefs(_act, IConstant.STATE_NAME));
        tvCountry.setText("India");

        //Life Style
        tvDiet = root.findViewById(R.id.tvDiet);
        tvSmoking = root.findViewById(R.id.tvSmoking);
        tvDrinking = root.findViewById(R.id.tvDrinking);
        tvHobby = root.findViewById(R.id.tvHobby);

        tvDiet.setText(SharedPref.getPrefs(_act,IConstant.LIFE_STYLE));
        tvSmoking.setText(SharedPref.getPrefs(_act, IConstant.SMOKING));
        tvDrinking.setText(SharedPref.getPrefs(_act, IConstant.DRINKING));
        tvHobby.setText(SharedPref.getPrefs(_act, IConstant.HOBBY));


        //Other Information
        tvHeight = root.findViewById(R.id.tvHeight);
        tvCaste = root.findViewById(R.id.tvCaste);
        tvGotra = root.findViewById(R.id.tvGotra);
        tvGan = root.findViewById(R.id.tvGan);
        tvNakshatra = root.findViewById(R.id.tvNakshatra);
        tvCharan = root.findViewById(R.id.tvCharan);
        tvManglik = root.findViewById(R.id.tvManglik);
        tvNadi = root.findViewById(R.id.tvNadi);
        tvRashi = root.findViewById(R.id.tvRashi);

        tvHeight.setText(SharedPref.getPrefs(_act, IConstant.HEIGHT_NAME));
        tvCaste.setText(SharedPref.getPrefs(_act, IConstant.SUBCASTE));
        tvGotra.setText(SharedPref.getPrefs(_act, IConstant.GOTRA));
        tvGan.setText(SharedPref.getPrefs(_act, IConstant.GAN));
        tvNakshatra.setText(SharedPref.getPrefs(_act, IConstant.NAKSHATRA));
        tvCharan.setText(SharedPref.getPrefs(_act, IConstant.CHARAN));
        tvManglik.setText(SharedPref.getPrefs(_act, IConstant.MANGLIK));
        tvNadi.setText(SharedPref.getPrefs(_act, IConstant.NADI));
        tvRashi.setText(SharedPref.getPrefs(_act, IConstant.RASHI));

        //Family Details

        tvFatherName = root.findViewById(R.id.tvFatherName);
        tvFatherOccupaition = root.findViewById(R.id.tvFatherOccupaition);
        tvFatherContactNo = root.findViewById(R.id.tvFatherContactNo);
        tvMotherName = root.findViewById(R.id.tvMotherName);
        tvMotherOccupaition = root.findViewById(R.id.tvMotherOccupaition);
        tvMotherContactNo = root.findViewById(R.id.tvMotherContactNo);
        tvSibling = root.findViewById(R.id.tvSibling);
        tvBrotherCount = root.findViewById(R.id.tvBrotherCount);
        tvSisterCount = root.findViewById(R.id.tvSisterCount);
        tvMarriedSister = root.findViewById(R.id.tvMarriedSister);
        tvMarriedBrothers = root.findViewById(R.id.tvMarriedBrothers);
        tvUncleName = root.findViewById(R.id.tvUncleName);
        tvUncleOccupation = root.findViewById(R.id.tvUncleOccupation);
        tvMaternalUncleName = root.findViewById(R.id.tvMaternalUncleName);
        tvMaternalUncleOccupation = root.findViewById(R.id.tvMaternalUncleOccupation);

        tvFatherName.setText(SharedPref.getPrefs(_act, IConstant.FATHER_NAME));
        tvFatherOccupaition.setText(SharedPref.getPrefs(_act, IConstant.FATHER_OCCUPATION));
        tvFatherContactNo.setText(SharedPref.getPrefs(_act, IConstant.FATHER_CONTACT));
        tvMotherName.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_NAME));
        tvMotherOccupaition.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_OCCUPATION));
        tvMotherContactNo.setText(SharedPref.getPrefs(_act, IConstant.MOTHER_CONTACT));
        if(SharedPref.getPrefs(_act, IConstant.SIBLING).equals("1")) {
            tvSibling.setText("Yes");
        } else {
            tvSibling.setText("No");
        }
        tvBrotherCount.setText(SharedPref.getPrefs(_act, IConstant.BROTHER));
        tvSisterCount.setText(SharedPref.getPrefs(_act, IConstant.SISTER));
        tvMarriedSister.setText(SharedPref.getPrefs(_act, IConstant.MARRIED_SISTER));
        tvMarriedBrothers.setText(SharedPref.getPrefs(_act, IConstant.MARRIED_BROTHER));
        tvUncleName.setText(SharedPref.getPrefs(_act, IConstant.UNCLE_NAME));
        tvUncleOccupation.setText(SharedPref.getPrefs(_act, IConstant.UNCLE_OCCUPATION));
        tvMaternalUncleName.setText(SharedPref.getPrefs(_act, IConstant.MARITAL_UNCLE_NAME));
        tvMaternalUncleOccupation.setText(SharedPref.getPrefs(_act, IConstant.MARITAL_UNCLE_OCCUPATION));

        //Education And Job
        tvEducation = root.findViewById(R.id.tvEducation);
        tvPrimaryEducationLanguage = root.findViewById(R.id.tvPrimaryEducationLanguage);
        tvOccupation = root.findViewById(R.id.tvOccupation);
        tvCompany = root.findViewById(R.id.tvCompany);
        tvDesignation = root.findViewById(R.id.tvDesignation);
        tvIncome = root.findViewById(R.id.tvIncome);
        tvOtherIncome = root.findViewById(R.id.tvOtherIncome);

        tvEducation.setText(SharedPref.getPrefs(_act, IConstant.EDUCATION_NAME));
        tvPrimaryEducationLanguage.setText(SharedPref.getPrefs(_act, IConstant.LANGUAGE_NAME));
        tvOccupation.setText(SharedPref.getPrefs(_act, IConstant.OCCUPATION_NAME));
        tvCompany.setText(SharedPref.getPrefs(_act, IConstant.COMPANY));
        tvDesignation.setText(SharedPref.getPrefs(_act, IConstant.DESIGNATION));
        tvIncome.setText(SharedPref.getPrefs(_act, IConstant.INCOME));
        tvOtherIncome.setText(SharedPref.getPrefs(_act, IConstant.OTHER_INCOME));

        init();

        return root;
    }

    private void init() {

        camera = new Camera((AppCompatActivity) _act);


        ivEditBasicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BasicDetailsAddAndEditActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivEditOtherInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PersonalDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivEditFamilyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FamilyDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivEditEducationAndJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfessionalDetailsActivity.class);
                intent.putExtra(IConstant.EditFlag, "1");
                startActivity(intent);
            }
        });

        ivAdharImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(getActivity())
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    // Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                                    camera.selectImage(ivAdharImg1, 0);
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.myActivityResult(requestCode, resultCode, data);
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        ((Activity) getActivity()).startActivityForResult(intent, 101);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void processFinish(String output, int img_no) {
        String[] parts = output.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = output;
        uploadFile(profile_image_path, profile_image_name);
    }

    private void uploadFile(String fileUri, String filename) {
        // create upload service client
        FileUploadService service = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(FileUploadService.class);

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        // String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        MultipartBody.Part body = null;
        if (!profile_image_path.equalsIgnoreCase("")) {

            File file = new File(fileUri);


            // String mimeType= URLConnection.guessContentTypeFromName(file.getName());

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

            // MultipartBody.Part is used to send also the actual file name

            body = MultipartBody.Part.createFormData("adhar_image", filename, requestFile);
        }


        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(getContext(), IConstant.USER_ID));
        RequestBody imageType = RequestBody.create(MediaType.parse("text/plain"), SharedPref.getPrefs(getContext(), IConstant.FRONTBACK));
        //RequestBody user_last_name = RequestBody.create(MediaType.parse("text/plain"), et_update_profile_lastname.getText().toString());
        Call<ResponseBody> call;
        RequestBody file_name = RequestBody.create(MediaType.parse("text/plain"), "" + filename);
        call = service.upload(user_id, imageType, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    // Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = Boolean.parseBoolean(jsonObject.getString("result"));
                    String reason = jsonObject.getString("reason");

                    if (res) {

                        Toast.makeText(getContext(), reason, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "" + reason, Toast.LENGTH_SHORT).show();
                    //  Log.v("Upload", "" + response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload error:", "");
                progressDialog.dismiss();
            }
        });
    }

    public interface FileUploadService {
        @Multipart
        @POST(MyConfig.BrahminMatrimony+"/app_adharimage")
        Call<ResponseBody> upload(
                @Part("loginId") @Nullable RequestBody loginId,
                @Part("image_type") @Nullable RequestBody image_type,
                @Part @Nullable MultipartBody.Part file);
    }
}