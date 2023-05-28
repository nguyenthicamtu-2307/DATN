package com.example.foryou.Model.RescueTem

data class RequestRelief(val aidPackageMoney: Int = 0,
                         val aidPackageTotalValue: Int = 0,
                         val aidPackageNeccessariesList: String = "",
                         val fromMobile: Boolean = false,
                         val endAt: String = "",
                         val startAt: String = "")
data class ResponeRelief(val code: Int = 0,
                         val success: Boolean = false,
                         val timestamp: Long = 0)
