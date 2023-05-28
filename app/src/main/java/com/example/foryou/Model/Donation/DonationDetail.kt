package com.example.foryou.Model.Donation

data class DonationDetail(val code: Int = 0,
                          val data: DataDonationDetail,
                          val success: Boolean = false,
                          val timestamp: Long = 0)

data class DataDonationDetail(val necessariesList: String = "",
                val donatedMoney: Int = 0,
                val description: String = "",
                val id: String = "",
                val deadline: String = "",
                val moneyNeed: Int = 0,
                val status: String = "")