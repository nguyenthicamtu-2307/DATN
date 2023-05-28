package com.example.foryou.Model.Donation

data class UpdateDonation(
    val description: String = "",
    val moneyNeed: Int = 0,
    val necessariesList: String = "",
    val fromMobile: Boolean = false,
    val deadline: String = ""
)

data class UpdateRespone(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)