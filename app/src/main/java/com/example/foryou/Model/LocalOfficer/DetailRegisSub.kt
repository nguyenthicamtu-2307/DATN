package com.example.foryou.Model.LocalOfficer

data class DetailRegisSub(val code: Int = 0,
                          val data: DataDetail,
                          val success: Boolean = false,
                          val timestamp: Long = 0)
data class DataDetail(val eventId: String = "",
                val neccessariesList: String,
                val householdsNumber: Int = 0,
                val amountOfMoney: String,
                val createdAt: String = "",
                val localOfficerUserId: String = "",
                val eventName: String = "",
                val localOfficerName: String = "",
                val id: String = "",
                val householdsListUrl: String,
                val updatedAt: String = "",
                val isCompleted: Boolean = false)