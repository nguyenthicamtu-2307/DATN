package com.example.foryou.Model.UserModel

data class LoginRespone(
    val code: Int = 0,
    val data: Data,
    val success: Boolean = false,
    val timestamp: Long = 0
)

data class Data(
    val accessToken: String = "",
    val username: String = "",
    val refreshToken: String = "",
    val fullname :String,
    val userType:String,
    val email:String,
    val phoneNumber:String
)