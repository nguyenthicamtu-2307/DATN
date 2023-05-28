package com.example.foryou.Model.RescueTem

data class InforAid(val code: Int = 0,
                    val data: DataInfo,
                    val success: Boolean = false,
                    val timestamp: Long = 0)
data class DataInfo(val subscribeAt: String = "",
                val path: String = "",
                val eventName: String = "",
                val id: String = "",
                val closedAt: String,
                val isApproved: Boolean = false,
                val householdNumber: Int = 0,
                val householdsListUrl: String = "")