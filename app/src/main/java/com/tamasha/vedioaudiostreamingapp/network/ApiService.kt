package com.tamasha.vedioaudiostreamingapp.network

import com.tamasha.vedioaudiostreamingapp.model.ProjectConstants.SEND_OTP
import com.tamasha.vedioaudiostreamingapp.model.ProjectConstants.USER_BY_PHONE_LOGIN
import com.tamasha.vedioaudiostreamingapp.model.ProjectConstants.VERIFY_OTP
import com.tamasha.vedioaudiostreamingapp.model.request.NumberRegisterRequest
import com.tamasha.vedioaudiostreamingapp.model.response.UserByPhoneResponse
import com.tamasha.vedioaudiostreamingapp.model.request.UserOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.request.VerifyOtpRequest
import com.tamasha.vedioaudiostreamingapp.model.response.SendOtpResponse
import com.tamasha.vedioaudiostreamingapp.model.response.VerifyOtpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST(USER_BY_PHONE_LOGIN)
    suspend fun userLogin(@Body numberRegisterRequest: NumberRegisterRequest):UserByPhoneResponse

    @POST(SEND_OTP)
    suspend fun senOtp(@Body userOtpRequest: UserOtpRequest):SendOtpResponse

    @POST(VERIFY_OTP)
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest):VerifyOtpResponse

    /*  @GET(GET_DOCTOR_NUMBER)
      fun getDoctor(): Call<DoctorSignUpResponse>

      @GET(GET_DOCTOR_ID)
      fun getDoctorId(@Path("id") id: String?): Call<GetDoctorByidResponse>

      @POST(DOCTOR_LOGIN)
      fun doctorLogin(@Body doctorLoginRequest: DoctorLoginRequest): Call<DoctorLoginResponse>


      @POST(REGISTER_DOCTOR)
      fun registerDoctor(@Body doctorrequest: DoctorRegisterRequest): Call<DrSignUpResponse>

      @PUT(UPDATE_DOCTOR_PROFILE)
      fun updateDoctor(
          @Body updateDoctorRequest: UpdateDoctorRequest
      ): Call<UpdateDoctorResponse>

      @POST(FORGET_PASSWORD_SEND_OTP)
      fun forgetPasswordOtp(@Body forgetPassword: ForgetPasswordOtpRequest): Call<ForgetPasswordOtpResponse>

      @POST(FORGET_PASSWORD_OTP_VERIFICATION)
      fun verifyOtp(@Body verifyOtpRequest: OtpVerificationRequest): Call<OtpVerificationResponse>

      @POST(CHANGE_PASSWORD)
      fun changePassword(@Body changepasswordRequest: ChangePasswordRequest): Call<ChangePasswordResponse>

      @Multipart
      @POST(CHANGE_DR_PROFILE)
      fun changeProfile(@Part image: MultipartBody.Part):Call<UpdateProfileImageResponse>


      // Patients Api

      *//* @FormUrlEncoded
     @POST(ADD_PATIENT)
     fun addNewPatient(@FieldMap fields: HashMap<String,String>): Call<AddPatientResponse>*//*

    *//* @POST(ADD_PATIENT)
     fun addNewPatient(@Body requestBody: RequestBody):Call<AddPatientResponse>*//*

    @POST(ADD_PATIENT)
    fun registerPatient(@Body addPatientRequest: AddPatientRequest): Call<AddPatientResponse>

    @Multipart
    @POST(ADD_PATIENT)
    fun addPatient(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Call<AddPatientResponse>

    @PUT(UPDATE_PATIENT)
    fun updatePatient(
        @Path("id") id: String,
        @Body updatePatientRequest: UpdatePatientRequest
    ): Call<UpdatePatientResponse>

    @DELETE(DELETE_PATIENT)
    fun deletePatient(@Path("id") id: String): Call<DeletePatientResponse>

    @GET(MY_PATIENT_LIST)
    fun getMyPatientList(): Call<MyPatientListResponse>

    @PUT(CHANGE_PATIENT_STATUS)
    fun changePatientStatus(@Path("id") id: String): Call<PatientStatusChangeResponse>

    @GET(CLOSE_CASE_PATIENT_LIST)
    fun closecasePatient(): Call<CloseCasePatientListResponse>

    @GET(GET_PATIENT_REPORT_BY_ID)
    fun getPatientReport(@Path("id") id: String?): Call<PatientReportByPatientIdResponse>

    @GET(SEARCH_PATIENT)
    fun searchPatient(
        @Query("q") sortBy: String
    ):Call<SearchPatientResponse>


    // Patient Report Apis

*//*    @Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@Part images: MultipartBody.Part, @Part ("patientid") patientId: RequestBody): Call<AddPatientReportResponse>*//*
    *//*@Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@Part images: MultipartBody.Part): Call<AddPatientReportResponse>
*//*
    *//*  @Multipart
      @POST(ADD_PATIENT_REPORT)
      fun addPatientReport(@Part part: MultipartBody.Part): Call<AddPatientReportResponse>*//*


    *//*@Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReport(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Call<AddPatientReportResponse>*//*

    @Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addReport(
        @Part file: MultipartBody.Part,
        @Part("reportname") fname: RequestBody?,
        @Part("patientid") id: RequestBody?,
        @Part("dateofreport") date: RequestBody?
    ): Call<AddPatientReportResponse>?

    @Multipart
    @POST(ADD_PATIENT_REPORT)
    fun addPatientReprt(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>): Call<AddPatientReportResponse>


    @DELETE(DELETE_REPORT)
    fun deleteReport(@Path("id") id: String): Call<DeleteReportResponse>

    //Priscription Apis

    @Multipart
    @POST(ADD_PRESCRIPTION)
    fun addPrescrip(
        @Part file: MultipartBody.Part,
        @Part("title") fname: RequestBody?,
        @Part("patientid") id: RequestBody?,
        @Part("description") date: RequestBody?
    ): Call<AddPrescripResponse>?

    @GET(GET_PRESCRIPTION_PATIENT_ID)
    fun getPrescription(@Path("id") id: String?): Call<GetPrescriptionBypatientIdResponse>



    // Appointment Apis

    @GET(GET_DAILY_APPOINTMENT)
    fun getDailyAppoint(): Call<DailyAppointmentResponse>

    @GET(GET_WEEKLY_APPOINTMENT)
    fun getWeeklyAppoint(): Call<DailyAppointmentResponse>

    @POST(ADD_NEW_APPOINTMENT)
    fun addNewAppointment(@Body newAppointment: AddNewAppointmentRequest): Call<AddNewAppointmentResponse>

    @PUT(UPDATE_APPOINTMENT)
    fun updateAppointment(
        @Path("id") id: String,
        @Body updateAppoitment: UpdateAppointmentRequest
    ): Call<UpdateAppointmentResponse>

    @DELETE(DELETE_APPOINTMENT)
    fun deleteAppointment(@Path("id") id: String): Call<DeleteAppointResponse>

*/
}