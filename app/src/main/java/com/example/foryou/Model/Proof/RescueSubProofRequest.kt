package com.example.foryou.Model.Proof

data class RescueSubProofRequest(val action: String = "",
                                 val fromMobile: Boolean = false)
data class RescueSubProofRespone(val code: Int = 0,
                                 val success: Boolean = false,
                                 val timestamp: Long = 0)