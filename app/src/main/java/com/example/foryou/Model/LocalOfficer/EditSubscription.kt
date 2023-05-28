package com.example.foryou.Model.LocalOfficer

data class EditSubscription(
    val neccessariesList: String = "",
    val householdNumber: Int = 0,
    val householdsListUrl: String = "",
    val amountOfMoney: Int = 0
)
data class EditSubRespone(val code: Int = 0,
                          val success: Boolean = false,
                          val timestamp: Long = 0)