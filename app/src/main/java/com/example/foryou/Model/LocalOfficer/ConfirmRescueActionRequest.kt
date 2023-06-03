package com.example.foryou.Model.LocalOfficer

data class ConfirmRescueActionRequest(val action: String = "")
data class ConfirmRescueActionRespone(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)