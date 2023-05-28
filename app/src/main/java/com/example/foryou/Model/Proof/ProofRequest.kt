package com.example.foryou.Model.Proof

data class ProofRequest(
    val proofUrls: String = "",
    val moneyTransferReceiptImgUrl: String = "",
    val fromMobile: Boolean = false
)

data class ProofRespone(
    val code: Int ,
    val success: Boolean,
    val timestamp: Long
)

data class ApiResponse(
    val code: Int = 0,
    val success: Boolean = false,
    val timestamp: Long = 0
)
