package com.example.foryou.Model.RescueTem

data class DetailRelief(val code: Int = 0,
                        val data: DataDetail,
                        val success: Boolean = false,
                        val timestamp: Long = 0)

data class DataDetail(val createdAt: String = "",
                val reliefPlan: ReliefPlan,
                val neccessariesList: String = "",
                val rescueTeamUserId: String = "",
                val id: String = "",
                val rescueTeamName: String = "",
                val amountOfMoney: Int = 0)
data class ReliefPlan(val originalnoney: Int = 0,
                      val aidPackage: AidPackage,
                      val id: String = "",
                      val endAt: String = "",
                      val startAt: String = "")
data class AidPackage(val totalValue: Int = 0,
                      val neccessariesList: String = "",
                      val amountOfMoney: Int = 0)