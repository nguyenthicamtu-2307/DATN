package com.example.foryou.Model.Donation

data class DonationDetail(
    val code: Int = 0,
    val data: DataDonationDetail,
    val success: Boolean = false,
    val timestamp: Long = 0
)

data class DataDonationDetail(
    val bank: String = "",
    val necessariesList: String = "",
    val donatedMoney: Int = 0,
    val sponsors: List<SponsorsItem>?,
    val description: String = "",
    val bankAccountNumber: String = "",
    val id: String = "",
    val moneyNeed: Int = 0,
    val deadline: String = "",
    val status: String = "",
    val necessaries: List<NecessariesItem>?
)

data class NecessariesItem(
    val quantity: Int = 0,
    val donatedQuantity: Int = 0,
    val name: String = ""
)

data class SponsorsItem(
    val name: String = "",
    val id: String = ""
)