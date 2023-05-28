package com.example.foryou.Model.Donation

data class PostDonate(
    val money: Int = 0,
    val donationNeccessariesList: String = ""
)

data class DonateRespone(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)