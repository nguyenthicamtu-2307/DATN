package com.example.foryou.Model.LocalOfficer

data class ListRescueAction(
    val code: Int = 0,
    val data: DataAction,
    val success: Boolean = false,
    val timestamp: Long = 0
)

data class DataAction(
    val createdAt: String = "",
    val reliefPlan: ReliefPlanAction,
    val neccessariesList: String = "",
    val rescueTeamUserId: String = "",
    val proofs: List<ProofsItem>?,
    val id: String = "",
    val histories: List<HistoriesItem>?,
    val rescueTeamName: String = "",
    val householdsListUrl: String = "",
    val donationPost: DonationPostAction,
    val amountOfMoney: Int = 0
)

data class DonationPostAction(
    val neccessaries: List<NeccessariesItem>?,
    val donatedMoney: Int = 0,
    val id: String = "",
    val moneyNeed: Int = 0,
    val deadline: String = "",
    val status: String = ""
)

data class NeccessariesItem(
    val quantity: Int = 0,
    val donatedQuantity: Int = 0,
    val name: String = ""
)

data class ProofsItem(
    val createdAt: String = "",
    val imageUrl: String = "",
    val id: String = ""
)

data class AidPackageAction(
    val totalValue: Int = 0,
    val neccessaries: List<NeccessariesItem>?,
    val neccessariesList: String = "",
    val amountOfMoney: Int = 0
)

data class ReliefPlanAction(
    val originalnoney: Int = 0,
    val aidPackage: AidPackageAction,
    val id: String = "",
    val endAt: String = "",
    val startAt: String = ""
)

data class HistoriesItem(
    val createdAt: String = "",
    val rtSubscriptionId: String = "",
    val id: String = "",
    val type: String = "",
    val updatedAt: String = ""
)