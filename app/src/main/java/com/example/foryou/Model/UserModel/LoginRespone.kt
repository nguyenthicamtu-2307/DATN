package com.example.foryou.Model.UserModel

data class LoginRespone(
    val code: Int = 0,
    val data: Data,
    val success: Boolean = false,
    val timestamp: Long = 0
)

data class Data(
    val phoneNumber: String = "",
    val fullName: String = "",
    val userType: String = "",
    val accessToken: String = "",
    val email: String = "",
    val username: String = "",
    val refreshToken: String = ""
)