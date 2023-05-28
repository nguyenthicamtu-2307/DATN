package com.example.foryou.Model.Soponsor

data class DetailDonate(val totalRecords: Int = 0,
                        val data: List<DataItem>?,
                        val payloadSize: Int = 0,
                        val hasNext: Boolean = false)

data class DataItem(val createdAt: String = "",
                    val rescueTeam: RescueTeamInfo,
                    val money: Int = 0,
                    val id: String = "",
                    val deadline: String = "",
                    val event: Event,
                    val status: String = "",
                    val updatedAt: String = "")
data class RescueTeamInfo(val name: String = "",
                      val id: String = "")
data class Event(val eventName: String = "",
                 val id: String = "")