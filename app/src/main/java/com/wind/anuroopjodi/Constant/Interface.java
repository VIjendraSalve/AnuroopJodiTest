package com.wind.anuroopjodi.Constant;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by User on 2/15/2020.
 */

public interface Interface {


    @GET(IUrls.URL_MOTHER_TONGUE)
    Call<ResponseBody> GETMotherTongue();

    @FormUrlEncoded
    @POST(IUrls.URL_SUBCASTE)
    Call<ResponseBody> GETSubcaste(@Field("cast_id") String caste_id);

    @GET(IUrls.URL_CASTE_LIST)
    Call<ResponseBody> GETCaste();

    @GET(IUrls.URL_STATE_LIST)
    Call<ResponseBody> GETState();

    @FormUrlEncoded
    @POST(IUrls.URL_CITY_LIST)
    Call<ResponseBody> POSTCity(@Field("state_id") String state_id);

    @GET(IUrls.URL_PACKAGE_LIST)
    Call<ResponseBody> GETPackages();

    @GET(IUrls.URL_HEIGHT)
    Call<ResponseBody> GETHeight();

    @GET(IUrls.URL_COMPLEXION)
    Call<ResponseBody> GETComplexion();

    @GET(IUrls.URL_RASHI)
    Call<ResponseBody> GETRashi();

    @GET(IUrls.URL_NAKSHATRA)
    Call<ResponseBody> GETNakshatra();

    @GET(IUrls.URL_GAN)
    Call<ResponseBody> GETGAN();

    @GET(IUrls.URL_CHARAN)
    Call<ResponseBody> GETCHARAN();

    @GET(IUrls.URL_NADI)
    Call<ResponseBody> GETNADI();

    @GET(IUrls.URL_EDUCATION)
    Call<ResponseBody> GETEducation();

    @GET(IUrls.URL_PROFESSION)
    Call<ResponseBody> GETProfession();


    @GET(IUrls.URL_SERVICES)
    Call<ResponseBody> GETServices();

    @FormUrlEncoded
    @POST(IUrls.URL_REGISTRATION)
    public Call<ResponseBody> POSTRegistration(
            @Field("firstName") String firstName,
            @Field("middle_name") String middle_name,
            @Field("lastName") String lastName,
            @Field("profileFor") String profileFor,
            @Field("emailId") String emailId,
            @Field("contact_number") String contact_number,
            @Field("gender") String gender,
            @Field("maritalStatus") String maritalStatus,
            @Field("password") String password,
            @Field("device") String device,
            @Field("notification_token") String notification_token,
            @Field("relative_relation") String relative_relation
    );

    @FormUrlEncoded
    @POST(IUrls.URL_RESEND_OTP)
    public Call<ResponseBody> POSTResendOtp(
            @Field("contact_number") String mobile);

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_NAME)
    public Call<ResponseBody> POSTUpdateName(
            @Field("user_id") String user_id,
            @Field("firstName") String firstName,
            @Field("middle_name") String middle_name,
            @Field("lastName") String lastName,
            @Field("maritalStatus") String maritalStatus,
            @Field("relative_relation") String relative_relation
    );

    @FormUrlEncoded
    @POST(IUrls.URL_OTP_CHECK)
    Call<ResponseBody> getCheckOtp(
            @Field("contact_number") String contact_number,
            @Field("email") String email,
            @Field("mobile_otp_number") String mobile_otp_number,
            @Field("email_otp_number") String email_otp_number);

    @FormUrlEncoded
    @POST(IUrls.URL_LOGIN)
    public Call<ResponseBody> POSTLogin(
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("notification_token") String notification_token,
            @Field("device") String device,
            @Field("is_email") String is_email
    );

    @FormUrlEncoded
    @POST(IUrls.URL_STEP_ONE_BASIC_DETAILS)
    public Call<ResponseBody> POSTBasicDetails(
            @Field("birthDate") String birthDate,
            @Field("birthPlace") String birthPlace,
            @Field("birthTime") String birthTime,
            @Field("motherTongue") String motherTongue,
            @Field("subCaste") String subCaste,
            @Field("disability") String disability,
            @Field("address") String address,
            @Field("expectaion") String expectaion,
            @Field("state") String state,
            @Field("city") String city,
            @Field("userId") String userId,
            @Field("cast") String cast,
            @Field("working_city_present") String working_city_present,
            @Field("native_place") String native_place
            );


    @FormUrlEncoded
    @POST(IUrls.URL_TWO_ONE_PERSONAL_DETAILS)
    public Call<ResponseBody> POSTPersonalDetails(
            @Field("Height") String Height,
            @Field("Weight") String Weight,
            @Field("Complexion") String Complexion,
            @Field("bloodGroup") String bloodGroup,
            @Field("nakshatra") String nakshatra,
            @Field("gan") String gan,
            @Field("Charan") String Charan,
            @Field("Gotra") String Gotra,
            @Field("Manglik") String Manglik,
            @Field("Nadi") String Nadi,
            @Field("devak") String devak,
            @Field("intercaste_marriage") String intercaste_marriage,
            @Field("Rashi") String Rashi,
            @Field("Diet") String Diet,
            @Field("Smoking") String Smoking,
            @Field("Drinking") String Drinking,
            @Field("userId") String userId);

    @FormUrlEncoded
    @POST(IUrls.URL_THREE_ONE_PERSONAL_DETAILS)
    Call<ResponseBody> POSTProfessionDetails(@Field("occupation") String occupation,
                                             @Field("education") String education,
                                             @Field("companyName") String companyName,
                                             @Field("designation") String designation,
                                             @Field("jobLocation") String jobLocation,
                                             @Field("incomeFromJob") String incomeFromJob,
                                             @Field("otherIncome") String otherIncome,
                                             @Field("agriculture_land") String agriculture_land,
                                             @Field("userId") String userId);


    @FormUrlEncoded
    @POST(IUrls.URL_FOUR_ONE_PERSONAL_DETAILS)
    public Call<ResponseBody> POSTFamilyDetails(@Field("fatherName") String fatherName,
                                                @Field("motherName") String motherName,
                                                @Field("fatherOccupation") String fatherOccupation,
                                                @Field("motherOccupation") String motherOccupation,
                                                @Field("fatherContact") String fatherContact,
                                                @Field("motherContact") String motherContact,
                                                @Field("sibling") String sibling,
                                                @Field("brother") String brother,
                                                @Field("sister") String sister,
                                                @Field("marriedBrother") String marriedBrother,
                                                @Field("marriedSister") String marriedSister,
                                                @Field("uncleName") String uncleName,
                                                @Field("uncleOccupation") String uncleOccupation,
                                                @Field("maternalUncle") String maternalUncle,
                                                @Field("maternalUncleOccupation") String maternalUncleOccupation,
                                                @Field("hobby") String hobby,
                                                @Field("aboutMe") String aboutMe,
                                                @Field("living_style") String living_style,
                                                @Field("mama_kul") String mama_kul,
                                                @Field("userId") String userId);

    @FormUrlEncoded
    @POST(IUrls.URL_FIVE_ONE_PARTNER_DETAILS)
    public Call<ResponseBody> POSTPatnerPrefrences(@Field("ageFrom") String ageFrom,
                                                   @Field("ageTo") String ageTo,
                                                   @Field("heightFrom") String heightFrom,
                                                   @Field("heightTo") String heightTo,
                                                   @Field("maritalStatus") String maritalStatus,
                                                   @Field("motherTongue") String motherTongue,
                                                   @Field("subCaste") String subCaste,
                                                   @Field("gotra") String gotra,
                                                   @Field("manglik") String manglik,
                                                   @Field("state") String state,
                                                   @Field("city") String city,
                                                   @Field("education") String education,
                                                   @Field("working") String working,
                                                   @Field("userId") String userId);


    @FormUrlEncoded
    @POST(IUrls.URL_PACKAGE_UPDATE)
    Call<ResponseBody> POSTPackageUpdate(@Field("package") String package_id,
                                         @Field("summary") String summary,
                                         @Field("transactionId") String transactionId,
                                         @Field("userId") String userId);

    @FormUrlEncoded
    @POST(IUrls.URL_APP_DASHBOARD)
    Call<ResponseBody> POSTAppDashboard(
            @Field("user_id") String user_id,
            @Field("gender") String gender);

    @FormUrlEncoded
    @POST(IUrls.URL_VENDOR_LIST)
    Call<ResponseBody> POSTVendorList(
            @Field("category_id") String category_id);

    @FormUrlEncoded
    @POST(IUrls.URL_PROFILE_BY_ID)
    Call<ResponseBody> POSTProfileById(
            @Field("userId") String userId,
            @Field("otherUserId") String otherUserId);

/*    @Multipart
    @POST(IUrls.URL_FOUR_TWO_PERSONAL_DETAILS)
    Call<ResponseBody> POSTUpdateProfile(@Part("hobby") RequestBody hobby,
                                        @Part("aboutMe") RequestBody aboutMe,
                                        @Part("userId") RequestBody userId,
                                        @Part MultipartBody.Part complaintAreaFile);*/


    /*

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_LOCATION)
    public Call<ResponseBody> updateLocation(
            @FieldMap Map<String, String> params);

    @GET(IUrls.URL_APP_DASHBOARD)
    Call<ResponseBody> POSTAppDashboard();






    @FormUrlEncoded
    @POST(IUrls.URL_CANCEL_COMPLAINT_REASON)
    Call<ResponseBody> POSTCancelReasonList(@Field("role") String role);



    @GET(IUrls.URL_EDUCATION_LIST)
    Call<ResponseBody> GETEducations();

    @GET(IUrls.URL_OCCUPATION_LIST)
    Call<ResponseBody> GETOccupations();




    @FormUrlEncoded
    @POST(IUrls.URL_GRAMPANCHAYAT_LIST)
    Call<ResponseBody> POSTGramPanchayat(@Field("taluka_id") String taluka_id);

    @GET(IUrls.URL_DEPARTMENT_LIST)
    Call<ResponseBody> GETDepartments();



    @Multipart
    @POST(IUrls.URL_UPDATE_COMPLAINT)
    Call<ResponseBody> POSTUpdateComplaint(@Part("user_id") RequestBody user_id,
                                           @Part("title") RequestBody title,
                                           @Part("description") RequestBody description,
                                           @Part("district") RequestBody district,
                                           @Part("taluka") RequestBody taluka,
                                           @Part("grampanchayat") RequestBody grampanchayat,
                                           @Part("landmark") RequestBody landmark,
                                           @Part("lat") RequestBody lat,
                                           @Part("lng") RequestBody lng,
                                           @Part("govt_dept") RequestBody jsonArray,
                                           @Part MultipartBody.Part complaintAreaFile,
                                           @Part("complaint_id") RequestBody complaint_id);

    @FormUrlEncoded
    @POST(IUrls.URL_RESOURCE_LIST)
    Call<ResponseBody> POSTResourceList(@Field("role") String role);


    @Multipart
    @POST(IUrls.URL_UPDATE_PROFILE)
    Call<ResponseBody> POSTUpdateProfile(@Part("first_name") RequestBody first_name,
                                         @Part("last_name") RequestBody last_name,
                                         @Part("email") RequestBody email,
                                         @Part("gender") RequestBody gender,
                                         @Part("education") RequestBody education,
                                         @Part("occupation") RequestBody occupation,
                                         @Part("age") RequestBody age,
                                         @Part("district") RequestBody district,
                                         @Part("taluka") RequestBody taluka,
                                         @Part("grampanchayat") RequestBody grampanchayat,
                                         @Part("landmark") RequestBody landmark,
                                         @Part("user_id") RequestBody user_id,
                                         @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST(IUrls.URL_STATIC_DATA)
    Call<ResponseBody> POSTStaticData(@Field("role") String role);


    @FormUrlEncoded
    @POST(IUrls.URL_HELP_FAQ)
    Call<ResponseBody> POSTFAQList(@Field("search") String search,
                                   @Field("page") String page,
                                   @Field("role") String role);


    @FormUrlEncoded
    @POST(IUrls.URL_REMAINING_LEAVES)
    Call<ResponseBody> POSTRemainingLeaves(@Field("user_id") String user_id);



    @Multipart
    @POST(IUrls.URL_ADD_LEAVE_APPLICATION)
    Call<ResponseBody> POSTAddLeaveApplication(@Part("user_id") RequestBody user_id,
                                               @Part("leave_type") RequestBody leave_type,
                                               @Part("subject") RequestBody subject,
                                               @Part("start_date") RequestBody start_date,
                                               @Part("end_date") RequestBody end_date,
                                               @Part("total_leaves_count") RequestBody total_leaves_count,
                                               @Part("description") RequestBody description,
                                               @Part MultipartBody.Part attachmentFile);

    @GET(IUrls.URL_LEAVES_TYPE)
    Call<ResponseBody> GETLeavesType();

    @FormUrlEncoded
    @POST(IUrls.URL_GOVERMENT_OFFICIAL_LIST)
    Call<ResponseBody> POSTGovermentOfficialList(
            @Field("user_id") String user_id,
            @Field("complaint_id") String complaint_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ASSIGN_AND_UPDATE_COMPLAINT)
    Call<ResponseBody> getAssignAndUpdateComplaint(
            @Field("user_id") String user_id,
            @Field("complaint_id") String complaint_id,
            @Field("govt_offical_id") String govt_offical_id,
            @Field("action") String action,
            @Field("change_reason") String change_reason);

    @FormUrlEncoded
    @POST(IUrls.URL_SEND_REMINDER)
    Call<ResponseBody> getSendReminder(
            @Field("user_id") String user_id,
            @Field("complaint_id") String complaint_id,
            @Field("msg") String msg);

    @FormUrlEncoded
    @POST(IUrls.URL_SEND_REMINDER_TO_ADMIN)
    Call<ResponseBody> getSendReminderToAdmin(
            @Field("user_id") String user_id,
            @Field("complaint_id") String complaint_id,
            @Field("msg") String msg);
    @FormUrlEncoded
    @POST(IUrls.URL_CHECKOUT_REASON_LIST)
    public Call<ResponseBody> POSTCheckoutReasonList(@Field("role") String role);







    @FormUrlEncoded
    @POST(IUrls.URL_OTP_CHECK)
    Call<ResponseBody> getCheckOtp(
            @Field("contact_number") String contact_number,
            @Field("otp_number") String otp_number,
            @Field("notification_token") String notification_token,
            @Field("device") String device);




    @FormUrlEncoded
    @POST(IUrls.URL_STATE_LIST)
    Call<ResponseBody> POSTStateList(
            @Field("country_id") String country_id,
            @Field("lang") String lang);


    @FormUrlEncoded
    @POST(IUrls.URL_CITY_LIST)
    Call<ResponseBody> POSTCityList(
            @Field("state_id") String state_id,
            @Field("lang") String lang);

    @GET(IUrls.URL_RELATION_LIST)
    Call<ResponseBody> getRelationList();


    @GET(IUrls.URL_EDUCATIONAL_LIST)
    Call<ResponseBody> getEducationalList();


    @GET(IUrls.URL_PROFESSION_LIST)
    Call<ResponseBody> getProfessionList();

    @FormUrlEncoded
    @POST(IUrls.URL_APP_DASHBOARD)
    Call<ResponseBody> POSTAppDashboard(
            @Field("category_level") String category_level,
            @Field("category_id") String category_id,
            @Field("lang") String lang,
            @Field("search") String search);

    @FormUrlEncoded
    @POST(IUrls.URL_PRODUCT_LIST)
    Call<ResponseBody> POSTAllProductList(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id,
            @Field("category_id") String category_id,
            @Field("city_id") String city_id,
            @Field("search") String search,
            @Field("page_no") String page_no,
            @Field("lang") String lang);


    @FormUrlEncoded
    @POST(IUrls.URL_BUSINESS_DETAILS)
    Call<ResponseBody> POSTBussinessDetails(
            @Field("user_id") String user_id,
            @Field("item_id") String item_id,
            @Field("lang") String lang);

    @FormUrlEncoded
    @POST(IUrls.URL_CENSUS_MEMBER_LIST)
    Call<ResponseBody> POSTCensusMemberList(@Field("user_id") String user_id);





/*


    @GET(IUrls.URL_INSTRUCTIONS)
    Call<ResponseBody> getInstructions();

    @FormUrlEncoded
    @POST(IUrls.URL_MY_ORDERS)
    Call<ResponseBody> POSTMyOrder(@Field("user_id") String user_id);*/

   /*




    @FormUrlEncoded
    @POST(IUrls.URL_VARIANTS_LIST)
    Call<ResponseBody> POSTAllVariants(
            @Field("user_id") String user_id,
            @Field("category_id") String category_id);




    @FormUrlEncoded
    @POST(IUrls.URL_CATEGORY_WISE_PRODUCTS)
    Call<ResponseBody> POSTCategoryWiseProducts(
            @Field("user_id") String user_id,
            @Field("vendor_id") String vendor_id);


    @FormUrlEncoded
    @POST(IUrls.URL_ADD_TO_CART)
    Call<ResponseBody> POSTAddToCart(@Field("user_id") String user_id,
                                     @Field("product_id") String product_id,
                                     @Field("vendor_id") String vendor_id,
                                     @Field("quantity") String quantity);


    @FormUrlEncoded
    @POST(IUrls.URL_REMOVE_FROM_CART)
    Call<ResponseBody> POSTRemoveFromCart(
            @Field("user_id") String user_id,
            @Field("vendor_id") String vendor_id,
            @Field("product_id") String item_id);


    @FormUrlEncoded
    @POST(IUrls.URL_CART_LIST)
    public Call<ResponseBody> POSTCartList(@Field("user_id") String user_id,
                                           @Field("vendor_id") String vendor_id);

    @FormUrlEncoded
    @POST(IUrls.URL_CART_COUNT)
    public Call<ResponseBody> POSTCartCount(@Field("user_id") String user_id,
                                            @Field("vendor_id") String vendor_id);


    @FormUrlEncoded
    @POST(IUrls.URL_PLACE_ORDER)
    Call<ResponseBody> POSTPlaceOrder(@Field("user_id") String user_id,
                                      @Field("vendor_id") String vendor_id,
                                      @Field("discount_price") String discount_price,
                                      @Field("gst_price") String gst_price,
                                      @Field("grand_total") String grand_total,
                                      @Field("total_price") String total_price,
                                      @Field("zone_id") String zone_id,
                                      @Field("region_id") String region_id,
                                      @Field("call_id") String call_id,
                                      @Field("order_type") String order_type,
                                      @Field("lat") String lat,
                                      @Field("lng") String lng,
                                      @Field("products") JSONArray jsonArray);






    @FormUrlEncoded
    @POST(IUrls.URL_CHANGE_PASSWORD)
    Call<ResponseBody> POSTChangePassword(@Field("user_id") String user_id,
                                          @Field("password") String password,
                                          @Field("new_password") String new_password);


    @FormUrlEncoded
    @POST(IUrls.URL_NEW_CATEGORY_LIST)
    Call<ResponseBody> POSTNewCategoryList(
            @Field("user_id") String user_id,
            @Field("category_id") String category_id);




    @FormUrlEncoded
    @POST(IUrls.URL_NEW_PRODUCT_LIST)
    Call<ResponseBody> POSTProductListByCategory(
            @Field("user_id") String user_id,
            @Field("vendor_id") String vendor_id,
            @Field("product_id") String product_id,
            @Field("category_id") String category_id,
            @Field("variant_id") String variant_id,
            @Field("search") String search,
            @Field("page_no") String page_no,
            @Field("zone_id") String zone_id);


    @FormUrlEncoded
    @POST(IUrls.URL_PREVIOUS_ORDER_LIST)
    Call<ResponseBody> POSTPreOrderList(@Field("user_id") String user_id,
                                        @Field("vendor_id") String vendor_id,
                                        @Field("search") String search,
                                        @Field("page_no") String page_no);


    @FormUrlEncoded
    @POST(IUrls.URL_LEAVE_LIST)
    Call<ResponseBody> POSTLeaveList(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(IUrls.URL_LEAVE_APPLICATION)
    Call<ResponseBody> POSTLeaveApplication(@Field("user_id") String user_id,
                                            @Field("leave_id") String billable,
                                            @Field("reason") String invoice,
                                            @Field("leave_from") String rate,
                                            @Field("leave_to") String task_id,
                                            @Field("return_date") String startdate);


    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_ORDER_STATUS)
    Call<ResponseBody> POSTUpdateOrderStatus(
            @Field("user_id") String user_id,
            @Field("order_id") String order_id,
            @Field("status") String status);



    @FormUrlEncoded
    @POST(IUrls.URL_ZONE_LIST)
    Call<ResponseBody> POSTZoneList(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(IUrls.URL_REGION_LIST)
    Call<ResponseBody> POSTRegionList(
            @Field("user_id") String user_id,
            @Field("zone_id") String zone_id,
            @Field("search") String search);


    @FormUrlEncoded
    @POST(IUrls.URL_AREA_LIST)
    Call<ResponseBody> POSTAreaList(
            @Field("user_id") String user_id,
            @Field("search") String search,
            @Field("zone_id") String zone_id,
            @Field("region_id") String region_id);


    @FormUrlEncoded
    @POST(IUrls.URL_CHECK_IN)
    public Call<ResponseBody> postCheckIn(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(IUrls.URL_CHECK_OUT)
    public Call<ResponseBody> postCheckOut(@Field("user_id") String user_id,
                                           @Field("reason_id") String reason_id,
                                           @Field("reason") String reason,
                                           @Field("checkin_id") String checkin_id);


    @FormUrlEncoded
    @POST(IUrls.URL_TIME_LINE)
    Call<ResponseBody> POSTTimeLine(@Field("user_id") String user_id,
                                    @Field("area_id") String area_id);

    @FormUrlEncoded
    @POST(IUrls.URL_NEW_TIME_LINE)
    Call<ResponseBody> POSTNewTimeLine(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST(IUrls.URL_SCHEDULE_CALL)
    Call<ResponseBody> POSTCreateSchedule(@Field("user_id") String user_id,
                                          @Field("zone_id") String zone_id,
                                          @Field("area_id") String area_id,
                                          @Field("area_name") String area_name);

    @FormUrlEncoded
    @POST(IUrls.URL_RESCHEDULE_REASON)
    Call<ResponseBody> POSTRescheduledReason(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(IUrls.URL_RESCHEDULE_CALL)
    Call<ResponseBody> POSTRescheduleCall(@Field("user_id") String user_id,
                                          @Field("call_id") String call_id,
                                          @Field("vendor_id") String vendor_id,
                                          @Field("zone_id") String zone_id,
                                          @Field("area_id") String area_id,
                                          @Field("date") String date,
                                          @Field("reschedule_reason") String reschedule_reason);

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_LOCATION)
    public Call<ResponseBody> updateLocation(
            @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(IUrls.URL_CHECKOUT_REASONS_LIST)
    Call<ResponseBody> POSTCheckoutReason(@Field("user_id") String user_id);*/





   /* ,
    @Field("vendors") JSONArray jsonArray*/



  /*











    @GET(IUrls.URL_ESTORE_DASHBOARD_BANNERS)
    Call<ResponseBody> getEstoreBanners();

    @GET(IUrls.URL_ESTORE_DASHBOARD_CATEGORY)
    Call<ResponseBody> getEstoreCategory();

    @GET(IUrls.URL_PRE_SCHOOL_EVENT_MANAGEMENT)
    Call<ResponseBody> getPreschoolEventBanner();

    @GET(IUrls.URL_PRE_SCHOOL_BRANDING_AND_MARKETING)
    Call<ResponseBody> getPreschoolEventBrandingAndMarketing();



    @GET(IUrls.URL_ESTORE_DASHBOARD_SUB_CATEGORY_PRODUCT)
    Call<ResponseBody> getEstoreSubCategoryProduct(
            @Query("category_id") String catId,
            @Query("subcat_id") String sub_catId
    );





    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_CATEGORY)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategory(
            @Query("pre_owner_dash_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_CIRRCULUM_WEEK)
    Call<ResponseBody> getPreschoolOwnerSmartCirriculumCategoryWeeks(
            @Query("smart_curriculum_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_WORK_SHEET_PDF)
    Call<ResponseBody> getPreschoolOwnerSmartWorksheetPDF(
            @Query("smart_worksheet_id") String catId
    );

    @GET(IUrls.URL_PRESCHOOL_OWNER_SMART_TRAINING_OPTIONS)
    Call<ResponseBody> getPreschoolOwnerSmartTrainingOptions(
            @Query("pre_owner_dash_id") String catId
    );






    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_NEW_WEBCALL)
    Call<ResponseBody> getPreschoolOwnerSmartCurriculumNewWebcall(
            @Query("user_id") String user_id
    );

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT)
    Call<ResponseBody> getEstoreProductList();

    @GET(IUrls.URL_PRESCHOOL_ESTORE_PRODUCT_BY_CATEGORY_ID)
    Call<ResponseBody> getProductByCategoryId(
            @Query("category_id") String category_id
    );

    @GET(IUrls.URL_WORKSHEET_STUDY_MATERIAL_LIST)
    Call<ResponseBody> getWorksheetList(
            @Query("smart_worksheet_id") String smart_worksheet_id

    );

    @GET(IUrls.URL_PRESCHOOL_SMART_CURRICULUM_ALl_COURCE_IMAGES)
    Call<ResponseBody> getSmartCurriculamAllCourceImage(
            @Query("smart_curriculum_id") String smart_curriculum_id

    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_LIST)
    Call<ResponseBody> getCuriculamWeekList(
            @Query("smart_curriculum_id") String smart_curriculum_id,
            @Query("session_id") String session_id
    );

    @GET(IUrls.URL_SMART_CURICULAM_WEEK_WISE_IMAGE)
    Call<ResponseBody> getWeekWiseImage(
            @Query("smart_curriculum_week_id") String smart_curriculum_week_id
    );

    @GET(IUrls.URL_SMART_ASSESSMENT_MANUAL)
    Call<ResponseBody> get3yrAssessmentManual(
            @Query("smart_assessment_id") String smart_assessment_id

    );



    @GET(IUrls.URL_SMART_ASSESSMENT_GRADING)
    Call<ResponseBody> getAssessmentGradient(
            @Query("smart_assessment_id") String smart_assessment_id

    );

    @GET(IUrls.URL_SMART_TRANING_BANNER)
    Call<ResponseBody> getSmartTraining(
            @Query("join_training_type_id") String join_training_type_id

    );


    @GET(IUrls.URL_SMART_ASSESSMENT_ORDER_KIT_VALUES)
    Call<ResponseBody> getAssessmentOrderValues();

    @GET(IUrls.URL_STATIONARY_LIST)
    Call<ResponseBody> getStatinaryList();

    @GET(IUrls.URL_MY_ORDER_CURRICULUM)
    Call<ResponseBody> getMyOrdersCurriculum(
            @Query("curriculum_user_id") String user_id,
            @Query("order_status") String order_status

    );


    @GET(IUrls.URL_MY_PREVIOUS_ORDER)
    Call<ResponseBody> getMyPreviousOrder(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id,
            @Query("order_status") String order_status

    );

    @GET(IUrls.URL_MY_PREVIOUS_ORDER_DETAILS)
    Call<ResponseBody> getMyPreviousOrderDetails(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("common_order_id") String common_order_id,
            @Query("order_status") String order_status

    );



    @GET(IUrls.URL_CANCEL_MY_ORDER)
    Call<ResponseBody> getCancelStatus(
            @Query("curriculum_user_id") String curriculum_user_id,
            @Query("curriculum_order_id") String curriculum_order_id,
            @Query("status") String order_status

    );

    @FormUrlEncoded
    @POST(IUrls.URL_SMART_CURICULAM_ORDER_DETAILS)

    public Call<ResponseBody> getCurriculamOrder(@Field("curriculum_user_id") String curriculum_user_id,
                                                 @Field("curriculum_order_person_name") String curriculum_order_person_name,
                                                 @Field("curriculum_order_person_contact") String curriculum_order_person_contact,
                                                 @Field("curriculum_order_person_email") String curriculum_order_person_email,
                                                 @Field("curriculum_order_person_address") String curriculum_order_person_address,
                                                 @Field("curriculum_order_person_delivery_address") String curriculum_order_person_delivery_address,
                                                 @Field("curriculum_pg_total_qty") String curriculum_pg_total_qty,
                                                 @Field("curriculum_pg_total_amount") String curriculum_pg_total_amount,
                                                 @Field("curriculum_nur_total_qty") String curriculum_nur_total_qty,
                                                 @Field("curriculum_nur_total_amount") String curriculum_nur_total_amount,
                                                 @Field("curriculum_jrkg_total_qty") String curriculum_jrkg_total_qty,
                                                 @Field("curriculum_jrkg_total_amount") String curriculum_jrkg_total_amount,
                                                 @Field("curriculum_srkg_total_qty") String curriculum_srkg_total_qty,
                                                 @Field("curriculum_srkg_total_amount") String curriculum_srkg_total_amount,
                                                 @Field("curriculum_total_amount") String curriculum_total_amount,
                                                 @Field("curriculum_discount_percentage") String curriculum_discount_percentage,
                                                 @Field("curriculum_breaking_price") String curriculum_breaking_price,
                                                 @Field("curriculum_delivery_charges") String curriculum_delivery_charges,
                                                 @Field("curriculum_base_amount") String curriculum_base_amount,
                                                 @Field("curriculum_total_items") String curriculum_total_items,
                                                 @Field("curriculum_cgst") String curriculum_cgst,
                                                 @Field("curriculum_sgst") String curriculum_sgst,
                                                 @Field("curriculum_payable_amount") String curriculum_payable_amount);


    @FormUrlEncoded
    @POST(IUrls.URL_SMART_WORKSHEET_ORDER_DETAILS)
    public Call<ResponseBody> getWorksheetOrder(@Field("worksheet_user_id") String worksheet_user_id,
                                                @Field("worksheet_order_person_name") String worksheet_order_person_name,
                                                @Field("worksheet_order_person_contact") String worksheet_order_person_contact,
                                                @Field("worksheet_order_person_email") String worksheet_order_person_email,
                                                @Field("worksheet_order_person_address") String worksheet_order_person_address,
                                                @Field("worksheet_order_person_delivery_address") String worksheet_order_person_delivery_address,
                                                @Field("worksheet_pg_total_qty") String worksheet_pg_total_qty,
                                                @Field("worksheet_pg_total_amount") String worksheet_pg_total_amount,
                                                @Field("worksheet_nur_total_qty") String worksheet_nur_total_qty,
                                                @Field("worksheet_nur_total_amount") String worksheet_nur_total_amount,
                                                @Field("worksheet_jrkg_total_qty") String worksheet_jrkg_total_qty,
                                                @Field("worksheet_jrkg_total_amount") String worksheet_jrkg_total_amount,
                                                @Field("worksheet_srkg_total_qty") String worksheet_srkg_total_qty,
                                                @Field("worksheet_srkg_total_amount") String worksheet_srkg_total_amount,
                                                @Field("worksheet_total_amount") String worksheet_total_amount,
                                                @Field("worksheet_discount_percentage") String worksheet_discount_percentage,
                                                @Field("worksheet_breaking_price") String worksheet_breaking_price,
                                                @Field("worksheet_delivery_charges") String worksheet_delivery_charges,
                                                @Field("worksheet_base_amount") String worksheet_base_amount,
                                                @Field("worksheet_total_items") String worksheet_total_items,
                                                @Field("worksheet_cgst") String worksheet_cgst,
                                                @Field("worksheet_sgst") String worksheet_sgst,
                                                @Field("worksheet_payable_amount") String worksheet_payable_amount);



    @FormUrlEncoded
    @POST(IUrls.URL_JOIN_TRAINING_CONNTACT_US_BY_MAIL)
    public Call<ResponseBody> getContactByMail(@Field("user_id") String user_id,
                                               @Field("username") String username,
                                               @Field("email") String email,
                                               @Field("mobile") String mobile,
                                               @Field("training_on_mobile") String training_on_mobile,
                                               @Field("workshops") String workshops,
                                               @Field("courses_and_diploma") String courses_and_diploma
    );


    @GET(IUrls.URL_PROFILE)
    Call<ResponseBody> getProfile(
            @Query("user_id") String user_id,
            @Query("user_type") String user_type


    );




    @FormUrlEncoded
    @POST(IUrls.URL_PROFILE_UPDATE)
    public Call<ResponseBody> preschoolOwner_ProfileUpdate(@Field("user_id") String user_id,
                                                           @Field("user_type") String user_type,
                                                           @Field("username") String username,
                                                           @Field("address") String address,
                                                           @Field("preschool_name") String preschool_name);


    @GET(IUrls.URL_ESTORE_NEW_DASHBOARD_PRODUCTS)
    Call<ResponseBody> getEstoreNewDashboardProductlist(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type,
            @Query("menu_id") String menu_id
    );

  *//*  @GET(IUrls.URL_CLASSES_INFO)
    Call<ResponseBody> getClassInfo();


    @GET(IUrls.URL_FACULTY)
    Call<ResponseBody> getFacultyInfo();

    @GET(IUrls.URL_SUBJECT)
    Call<ResponseBody> getSubjectInfo();

    @GET(IUrls.URL_PARENTS_DASHBOARD_GRID)
    Call<ResponseBody> getParentsDashboardgrid();*//*


     *//* @GET(IUrls.URL_CLASSES_INFO)
    Call<List<UserBannerObj>> getBanners();*//*




    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_UPDATE_ANNUAL_SPORTS_DAY_CERTIFICATE_FORM)
    public Call<ResponseBody> getUpdateAnnualSportsFinalDayCertificateForm(@Field("user_id") String user_id,
                                                                           @Field("stationary_type_id") String stationary_type_id,
                                                                           @Field("aspfinal_id") String aspfinal_id,
                                                                           @Field("student_name") String student_name,
                                                                           @Field("student_group") String student_group,
                                                                           @Field("student_rollno") String student_rollno,
                                                                           @Field("academic_year") String academic_year,
                                                                           @Field("competition_name") String competition_name,
                                                                           @Field("competition_date") String competition_date,
                                                                           @Field("rank") String rank
    );

    @GET(IUrls.URL_STATIONARY_TYPE_WISE_DESIGN_LIST)
    Call<ResponseBody> getStatinaryTypeWiseDesign(
            @Query("stationary_id") String stationary_id,
            @Query("stationary_type_id") String stationary_type_id


    );

    @GET(IUrls.URL_STATIONARY_EXPANDABLE_LIST)
    Call<StationaryPojo> getStationaryList();

    @GET(IUrls.URL_STATIONARY_STUDENT_ID_AND_PROGRESS_LIST)
    Call<ResponseBody> getStudentAndProgressList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_COMMON_ANNUAL_SPORTS_FINAL_LIST)
    Call<ResponseBody> getCommonSportsAnualFinalList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_SUMMER_CAMP_LIST)
    Call<ResponseBody> getSummerCampList(
            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );

    @GET(IUrls.URL_STATIONARY_TRANSFER_CERTIFICATE_LIST)
    Call<ResponseBody> getTrasferCertificateList(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ANNUL_SPORTS_FINAL)
    Call<ResponseBody> getCancelAnnualSportsFinalDayCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_ID_CARD_PROG_CARD)
    Call<ResponseBody> getCancelIDCardProgressCard(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_TRANSFER_CERTIFICATE)
    Call<ResponseBody> getCancelTransferCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );


    @GET(IUrls.URL_STATIONARY_CANCEL_SUMMER_CAMP_CERTIFICATE)
    Call<ResponseBody> getCancelSummerCampCertificate(

            @Query("user_id") String user_id,
            @Query("stationary_type_id") String stationary_type_id,
            @Query("design_id") String design_id,
            @Query("stationary_school_detail_id") String stationary_school_detail_id

    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_SUMMER_CAMP_CERTIFICATE_FORM)

    public Call<ResponseBody> getSummerCampCertificateForm(@Field("user_id") String user_id,
                                                           @Field("stationary_type_id") String stationary_type_id,
                                                           @Field("design_id") String design_id,
                                                           @Field("stationary_school_detail_id") String stationary_school_detail_id,
                                                           @Field("student_name") String student_name,
                                                           @Field("summer_camp_year") String summer_camp_year,
                                                           @Field("summer_camp_start_date") String summer_camp_start_date,
                                                           @Field("summer_camp_end_date") String summer_camp_end_date,
                                                           @Field("competition_rank") String competition_rank
    );

    @FormUrlEncoded
    @POST(IUrls.URL_UPDATE_STATIONARY_SUMMER_CAMP_CERTIFICATE_FORM)

    public Call<ResponseBody> getUpdateSummerCampCertificateForm(@Field("user_id") String user_id,
                                                                 @Field("stationary_type_id") String stationary_type_id,
                                                                 @Field("summer_camp_id") String summer_camp_id,
                                                                 @Field("student_name") String student_name,
                                                                 @Field("summer_camp_year") String summer_camp_year,
                                                                 @Field("summer_camp_start_date") String summer_camp_start_date,
                                                                 @Field("summer_camp_end_date") String summer_camp_end_date,
                                                                 @Field("competition_rank") String competition_rank
    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_TRANSFER_CERTIFICATE_FORM)


    public Call<ResponseBody> getTransferCertificateForm(@Field("user_id") String user_id,
                                                         @Field("stationary_type_id") String stationary_type_id,
                                                         @Field("design_id") String design_id,
                                                         @Field("stationary_school_detail_id") String stationary_school_detail_id,
                                                         @Field("student_name") String student_name,
                                                         @Field("father_name") String father_name,
                                                         @Field("mother_name") String mother_name,
                                                         @Field("birth_date") String birth_date,
                                                         @Field("academic_year") String academic_year,
                                                         @Field("present_group_name") String present_group_name,
                                                         @Field("promoted_group") String promoted_group,
                                                         @Field("reason") String reason,
                                                         @Field("remark") String remark

    );

    @FormUrlEncoded
    @POST(IUrls.URL_STATIONARY_UPDATE_TRANSFER_CERTIFICATE_FORM)


    public Call<ResponseBody> getUpdateTransferCertificateForm(
            @Field("user_id") String user_id,
            @Field("stationary_type_id") String stationary_type_id,
            @Field("transfer_certficate_id") String transfer_certficate_id,
            @Field("student_name") String student_name,
            @Field("father_name") String father_name,
            @Field("mother_name") String mother_name,
            @Field("birth_date") String birth_date,
            @Field("academic_year") String academic_year,
            @Field("present_group_name") String present_group_name,
            @Field("promoted_group") String promoted_group,
            @Field("reason") String reason,
            @Field("remark") String remark

    );


    @GET(IUrls.URL_STATIONARY_PRE_SCHOOL_LIST)
    Call<ResponseBody> getSchoolList(
            @Query("user_id") String user_id
    );



    //New Web Calls

    @GET(IUrls.URL_CURRICULUM_NEW_PRODUCT_WEBCALL)
    Call<ResponseBody> getCurriculumNewProductList(
            @Query("menu_id") String menu_id,
            @Query("category_id") String category_id,
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_CURRICULUM_ADD_PRODUCT_TO_CART)
    public Call<ResponseBody> postAddProductInList(@Field("user_id") String user_id,
                                                   @Field("login_type") String login_type,
                                                   @Field("menu_id") String menu_id,
                                                   @Field("product_id") String product_id,
                                                   @Field("cart_status") String cart_status);



    @GET(IUrls.URL_CART_PRODUCT_LIST)
    public Call<ResponseBody> getUserCartList(
            *//*@Query("menu_id") String menu_id,*//*
            @Query("user_id") String user_id,
            @Query("login_type") String login_type
    );

    @FormUrlEncoded
    @POST(IUrls.URL_COMMON_ORDER)
    public Call<ResponseBody> postTotalCartProduct(
            *//*  @Field("menu_id") String menu_id,*//*
            @Field("user_id") String user_id,
            @Field("login_type") String login_type,
            @Field("order_person_name") String order_person_name,
            @Field("order_person_contact") String order_person_contact,
            @Field("order_person_email") String order_person_email,
            @Field("order_person_address") String order_person_address,
            @Field("order_person_delivery_address") String order_person_delivery_address,
            @Field("order_receiver_name") String order_receiver_name,
            @Field("delivery_charges") String delivery_charges,
            @Field("pincode") String pincode);

    @GET(IUrls.URL_CONNTACT_US_BY_MAIL_LIST)
    Call<ResponseBody> getAllMailContactList(
            @Query("user_id") String user_id,
            @Query("login_type") String login_type

    );

    @GET(IUrls.URL_ESTORE_BANNERS)
    Call<ResponseBody> getEstoreBannersList();


    @FormUrlEncoded
    @POST(IUrls.URL_CONNTACT_US_BY_MAILFORM)
    public Call<ResponseBody> getCommonContactByMail(@Field("user_id") String user_id,
                                                     @Field("login_type") String login_type,
                                                     @Field("menu_name") String menu_name,
                                                     @Field("username") String username,
                                                     @Field("email") String email,
                                                     @Field("mobile") String mobile
    );*/


}
