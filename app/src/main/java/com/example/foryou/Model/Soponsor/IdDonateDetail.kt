package com.example.foryou.Model.Soponsor

data class IdDonateDetail(val code: Int = 0,
                          val data: DataIdDetail,
                          val success: Boolean = false,
                          val timestamp: Long = 0)
data class DataIdDetail(val donationPostId: String = "",
                val neccessariesList: String = "",
                val moneyTransferReceiptImgUrl: String,
                val createdAt: String = "",
                val rescueTeam: RescueTeamIdDetail,
                val money: Int = 0,
                val id: String = "",
                val deadline: String = "",
                val event: EventId,
                val updatedAt: String = "",
                val status: String = "")
data class EventId(val eventName: String = "",
                 val id: String = "")
data class RescueTeamIdDetail(val name: String = "",
                      val id: String = "")