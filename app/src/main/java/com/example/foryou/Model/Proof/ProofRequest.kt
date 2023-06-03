package com.example.foryou.Model.Proof

data class ProofRequest(
    val proofUrls: String = "",

    val moneyTransferReceiptImgUrl: String = "",
    val donationDetailImages: List<DonationDetailImagesItem>?,
    val fromMobile: Boolean = false
)

data class DonationDetailImagesItem(
    val imageUrl: String = ""
)

data class ProofRespone(
    val code: Int,
    val success: Boolean,
    val timestamp: Long
)



