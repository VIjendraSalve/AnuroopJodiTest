package com.whtech.anuroopjodi.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IUrls {

   // public static final String BASE_URL = "https://www.bramhagath.com/";
    //public static final String BASE_URL = "https://www.wetap.in/melava/";
    public static final String BASE_URL = "http://anuroopjodi.com/Backup2022/";
    //public static final String BASE_URL = "https://wetap.in/2021/bramhagath/";

    public static final String URL_MOTHER_TONGUE = "app_mother_tongue_master";
    public static final String URL_CASTE_LIST = "app_caste_master";
    public static final String URL_SUBCASTE = "app_subcaste_master";
    public static final String URL_STATE_LIST = "app_state_master";
    public static final String URL_CITY_LIST = "app_city_master";
    public static final String URL_PACKAGE_LIST = "app_package_list";
    public static final String URL_REGISTRATION = "app_registration";
    public static final String URL_RESEND_OTP = "app_resend_otp";
    public static final String URL_OTP_CHECK = "app_otp_verification";
    public static final String URL_LOGIN = "app_login";
    public static final String URL_HEIGHT = "app_height_master";
    public static final String URL_COMPLEXION = "app_complexion_list";
    public static final String URL_RASHI = "app_rashi_master";
    public static final String URL_EDUCATION = "app_education_master";
    public static final String URL_PROFESSION = "app_occupation_master";
    public static final String URL_STEP_ONE_BASIC_DETAILS = "app_basic_details_step_1";
    public static final String URL_TWO_ONE_PERSONAL_DETAILS = "app_personal_details_step_2";
    public static final String URL_THREE_ONE_PERSONAL_DETAILS = "app_professional_details_step_3";
    public static final String URL_FOUR_ONE_PERSONAL_DETAILS = "app_family_details_step_4";
    // public static final String URL_FOUR_TWO_PERSONAL_DETAILS= "app_family_own_details_step_4";
    public static final String URL_FIVE_ONE_PARTNER_DETAILS = "app_partner_pref_add_update_step_5";
    public static final String URL_PACKAGE_UPDATE = "app_select_package";
    public static final String URL_SERVICES = "app_services_list";
    public static final String URL_APP_DASHBOARD = "app_dashboard";
    public static final String URL_VENDOR_LIST = "app_service_vendor_list";
    public static final String URL_PROFILE_BY_ID = "app_other_user_profiles";
    public static final String URL_UPDATE_NAME = "app_update_registration";
    public static final String BrahminMatrimony = "app_matching_profiles";

    //Static Pages
    public static final String BASE_URL_Static = "https://www.google.com/bramhsakhi";

/*

    public static final String URL_UPDATE_PROFILE = "app_update_profile";
    public static final String URL_EDUCATION_LIST = "app_education_list";
    public static final String URL_OCCUPATION_LIST = "app_occupation_list";
    public static final String URL_STATIC_DATA = "app_about_contact_us";
    public static final String URL_HELP_FAQ = "app_faq_list";
    public static final String URL_TOTAL_LEAVES = "app_leave_application_list";
    public static final String URL_REMAINING_LEAVES = "app_leaves_type_list";
    public static final String URL_ADD_LEAVE_APPLICATION= "app_leave_application";
    public static final String URL_LEAVES_TYPE= "app_leaves_type_list";
    public static final String URL_GOVERMENT_OFFICIAL_LIST= "app_areawise_govt_officials";
    public static final String URL_ASSIGN_AND_UPDATE_COMPLAINT= "app_assign_complaint";
    public static final String URL_SEND_REMINDER= "app_notification_fellow_govt";
    public static final String URL_SEND_REMINDER_TO_ADMIN= "app_notification_to_admin";
    public static final String URL_CHECKOUT_REASON_LIST= "app_checkout_reasons_list";*/
    public static final String URL_PRIVACY_POLICY = BASE_URL_Static + "app_privacy_policy";
    public static final String URL_ABOUT_US = BASE_URL_Static + "app_about_us";
    public static final String URL_TERMS_AND_CONDITIONS = BASE_URL_Static + "app_term_condition";
    public static final String URL_RETURN_POLICY = BASE_URL_Static + "app_return_policy";
    public static final String URL_CONTACT_US = BASE_URL_Static + "app_contact_us";
    public static final String TERMS_URL = "https://windhans.com/terms-conditions";
    public static final String PRIVACY_URL = "https://windhans.com/privacy-policy";
    public static final String app_url = "https://play.google.com/store/apps/details?id=com.wht.brahminmatrimonialapp&hl=en";
    public static Dispatcher dispatcher;

    public static Retrofit getRetrofit(String BASE_URL) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(50, TimeUnit.SECONDS);
        httpClient.readTimeout(50, TimeUnit.SECONDS);
        httpClient.writeTimeout(50, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);


        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        httpClient.dispatcher(dispatcher);

        Retrofit retrofit = builder.client(httpClient.build()).build();
        Interface service = retrofit.create(Interface.class);
        return retrofit;
    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static Interface getApiService() {
        return getRetroClient().create(Interface.class);
    }

}
