package com.example.foryou.Model.RescueTem

data class FinishDonationPost(val action: String = "")
data class FinishDonationPostRespone(val code: Int = 0,
                                     val success: Boolean = false,
                                     val timestamp: Long = 0)