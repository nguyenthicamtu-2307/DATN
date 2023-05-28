package com.example.foryou.Model.RescueTem

data class PlanRelief(
    val startAt: String = "",
    val endAt: String = "",

    val aidPackageMoney: Int = 0,
    val aidPackageTotalValue: Int = 0,
    val fromMobile: Boolean = false,

    val aidPackageNeccessariesList: String = ""
)

data class PlanRespone(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)