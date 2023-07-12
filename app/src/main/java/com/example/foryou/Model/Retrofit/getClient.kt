package com.example.foryou.Model.Retrofit

import com.example.foryou.Model.Contents.ApiResponse
import com.example.foryou.Model.Contents.District
import com.example.foryou.Model.Contents.ProvincesRespon
import com.example.foryou.Model.Contents.WardsRespon
import com.example.foryou.Model.Donation.*
import com.example.foryou.Model.Event.*
import com.example.foryou.Model.Event.Event
import com.example.foryou.Model.LocalOfficer.*
import com.example.foryou.Model.Proof.*
import com.example.foryou.Model.RescueTem.*
import com.example.foryou.Model.Soponsor.*
import com.example.foryou.Model.UserModel.LoginRequest
import com.example.foryou.Model.UserModel.LoginRespone
import com.example.foryou.Model.UserModel.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*

interface getClient {
///content

    @GET("contents/provinces")
    fun getProvinced(): Call<ProvincesRespon>
    @GET("contents/provinces/{id}/districts")
    fun getDistrictbyProvince(@Path("id") id: String): Call<ApiResponse>
    @GET("contents/districts/{id}/wards")
    fun getWardbyDistrict(@Path("id") id: String): Call<WardsRespon>
    @GET("contents/wards")
    fun getWard(): Call<WardsRespon>

//user
    @POST("auth/register")
    fun register(@Body userdata:User):Call<User>
    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginRespone>
    @GET("admin/events") //relief-app/v1/admin/events
    fun getAllEvent():Call<Event>
    //Local officer /relief-app/v1/local-officers/events
    @GET("local-officers/events")
    fun getListEvent():Call<Event>
    @GET("admin/events/{id}")
    fun getListEventId(@Path("id") id: String):Call<EventRespone>
    @POST("local-officers/events/{id}/event-subscriptions")
    fun registerEvent(@Body eventRequest: eventRequest,
                      @Path("id") id: String
    ):Call<evenRegisRespone>
    @GET("local-officers/event-subscriptions/{id}")
    fun getDetailSubscriptions(@Path("id") id: String):Call<DetailRegisSub>
    @PUT("/relief-app/v1/local-officers/event-subscriptions/{id}")
    fun updateSubscription(
        @Path("id") id: String,
        @Body updateRequest:EditSubscription):Call<EditSubRespone>

    ///rescue-team


    @GET("rescue-team/events") ///relief-app/v1/rescue-team/events/{id}
    fun getRescueEvent():Call<EventRescueTeam>
    @GET("rescue-team/events")
    fun getSoponorEvent():Call<Event>
    @GET("rescue-team/events/{id}/subscriptions")
    fun subScriptionId(
        @Path("id") id: String
    ):Call<Subscription>

    @POST("rescue-team/rescue-subscriptions") //  /relief-app/v1/rescue-team/rescue-subscriptions
    fun regisSubscriptRescue(@Body SubRequest:SubScriptionRequest):Call<RegisSubRespone>
    @POST("rescue-team/rescue-subscriptions/{id}/relief-plans") //relief-app/v1/rescue-team/rescue-subscriptions/{id}/relief-plans
    fun ReliefPlan(@Body planRequest:PlanRelief,
                   @Path("id") id: String ):Call<PlanRespone>
    @GET("rescue-team/rescue-subscriptions") //relief-app/v1/rescue-team/rescue-subscriptions
    fun getReliefList():Call<ReliefList>
    //Detail Relief plan
    @GET("rescue-team/rescue-subscriptions/{id}/relief-plan") //relief-app/v1/rescue-team/rescue-subscriptions/{id}/relief-plan
    fun detailReliefPlan(@Path("id") id: String ):Call<DetailRelief>

    //infor aid
    @GET("rescue-team/rescue-subscriptions/{id}") //relief-app/v1/rescue-team/rescue-subscriptions/{id}/
    fun getInforAidRescue( @Path("id") id: String ):Call<InforAid>

    //update relief
    @PUT("rescue-team/relief-plans/{id}") ///relief-app/v1/rescue-team/relief-plans/{id}
    fun updateRelief(
        @Path("id") id: String,
        @Body updateRequest:RequestRelief):Call<ResponeRelief>

    //DonationPost
    @GET("rescue-team/donation-posts") //relief-app/v1/rescue-team/donation-posts
    fun getDonationPost():Call<DonationList>
    @POST("rescue-team/rescue-subscriptions/{id}/donation-posts") //relief-app/v1/rescue-team/rescue-subscriptions/{id}/donation-posts
    fun postDonation( @Path("id") id: String,
                      @Body donationRequest:DonationRequest):Call<DonationRespone>
    @GET("rescue-team/donation-posts/{id}") ///relief-app/v1/rescue-team/donation-posts/{id}
    fun getDetailDonationPost( @Path("id") id: String): Call<DonationDetail>
    @PUT("rescue-team/donation-posts/{id}") ///relief-app/v1/rescue-team/donation-posts/{id}
    fun updateDoantionPost(@Path("id") id: String,
                           @Body updateRequest:UpdateDonation):Call<UpdateRespone>

    @GET("rescue-team/rescue-subscriptions/{id}/proofs/{id}") //relief-app/v1/rescue-team/rescue-subscriptions/{id}/proofs/{id}
    fun getProof(@Path("id") id: String):Call<ProofRescue>

    @POST("rescue-team/rescue-subscriptions/{id}/proofs")
     fun uploadImage(@Path("id") id: String,
                     @Body request :ProofRequest
     ):Call<ProofRespone>
    @GET("local-officers/rescue-action") //relief-app/v1/local-officers/rescue-action
    fun getListAction():Call<ListRescueAction>

    //Soponsor
    @GET("sponsor/donation-posts")
    fun getPostDonationSoponsor() : Call<ListSoponsor>
    @GET("sponsor/donation-posts/{id}")
    fun getDetailPostSoponsor(@Path("id") id: String):Call<DetailPost>
    @POST("sponsor/rescue-subscriptions/{id}/donations") //relief-app/v1/sponsor/rescue-subscriptions/{id}/donations
    fun postDonate(@Body request : PostDonate,
                   @Path("id") id: String):Call<DonateRespone>

    @GET("sponsor/donations") //relief-app/v1/sponsor/donations
    fun getInforDonate():Call<DetailDonate>
    @GET("sponsor/donations/{id}") //relief-app/v1/sponsor/donations/{id}
    fun getDetailDonateId(@Path("id") id: String):Call<IdDonateDetail>
    @PUT("sponsor/donations/{id}") //relief-app/v1/sponsor/donations/{id}
    fun upDateDonationInfor(@Body request:DonateInforRequest,
    @Path("id") id:String):Call<DonatiaInforRespone>
    @GET("local-officers/event-subscriptions/{id}/rescue-action") //relief-app/v1/
    fun getRescuAction(@Path("id") id:String):Call<ManagerRescueAction>
    @PATCH("rescue-team/donation-posts/{id}") //relief-app/v1/
    fun confirmFinishDonationPost(@Body request :FinishDonationPost,
    @Path("id") id:String):Call<FinishDonationPostRespone>

    @POST("sponsor/donations/{id}/proofs") //relief-app/v1/sponsor/donations/{id}/proofs
    fun postProof( @Path("id") id:String,
                   @Body request :ProofRequest): Call<ProofRespone>
    @PATCH("local-officers/event-subscriptions/{id}/rescue-action/{id}") //relief-app/v1/local-officers/event-subscriptions/{id}/rescue-action/{id}
    fun conFirmRescueActionOfLocalOfficer(
        @Body request :ConfirmRescueActionRequest,
        @Path("id") id:String
    ):Call<ConfirmRescueActionRespone>
    @PATCH("rescue-team/rescue-subscriptions/{id}") //relief-app/v1/rescue-team/rescue-subscriptions/{id}
    fun conFirmRescueSubscription(
        @Path("id") id:String,
        @Body request :RescueSubProofRequest
    ):Call<RescueSubProofRespone>
   @PATCH("sponsor/donations/{id}")
    fun confirmDonationFinish(  @Path("id") id:String,
                                @Body request :ConfirmFinish):Call<ConFirmRespone>
}