package com.example.foryou.Model.Retrofit

import com.example.foryou.Model.Contents.ApiResponse
import com.example.foryou.Model.Contents.District
import com.example.foryou.Model.Contents.ProvincesRespon
import com.example.foryou.Model.Contents.WardsRespon
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Event.Event
import com.example.foryou.Model.UserModel.LoginRequest
import com.example.foryou.Model.UserModel.LoginRespone
import com.example.foryou.Model.UserModel.User
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

    //Local officer
    @GET("local-officers/events")
    fun getListEvent():Call<Event>
}