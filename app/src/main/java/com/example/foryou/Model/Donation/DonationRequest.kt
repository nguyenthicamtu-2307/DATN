package com.example.foryou.Model.Donation

data class DonationRequest(
    val description: String = "",
    val moneyNeed: Int = 0,
    val necessariesList: String = "",
    val fromMobile: Boolean = false,
    val deadline: String = "",
    val bankAccountNumber:String,
    val bank:String
)

data class DonationRespone(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)
