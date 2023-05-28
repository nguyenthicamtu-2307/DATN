package com.example.foryou.Model.LocalOfficer

data class ManagerRescueAction(val code: Int = 0,
                               val data: Data,
                               val success: Boolean = false,
                               val timestamp: Long = 0)
data class Data(val createdAt: String = "",
                val reliefPlan: ReliefPlan,
                val neccessariesList:String,
                val rescueTeamUserId: String = "",
                val id: String = "",
                val rescueTeamName: String,
                val householdsListUrl: String,
                val donationPost: DonationPost,
                val amountOfMoney: String)
data class ReliefPlan(val aidPackage: AidPackage,
                      val originalnoney: Int = 0,
                      val id: String = "",
                      val endAt: String = "",
                      val startAt: String = "")
data class DonationPost(val donatedMoney: Int = 0,
                        val id: String = "",
                        val deadline: String = "",
                        val moneyNeed: Int = 0,
                        val status: String = "")
data class AidPackage(val totalValue: Int = 0,
                      val neccessariesList: String = "",
                      val amountOfMoney: Int = 0)

