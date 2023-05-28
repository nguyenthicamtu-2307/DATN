package com.example.foryou.Model.Soponsor

data class ListSoponsor(val totalRecords: Int = 0,
                        val data: List<DataItemPost>?,
                        val payloadSize: Int = 0,
                        val hasNext: Boolean = false)
data class DataItemPost(val createdAt: String = "",
                    val rescueTeam: RescueTeam,
                    val necessariesList: String = "",
                    val donatedMoney: Int = 0,
                    val description: String = "",
                    val eventName: String = "",
                    val id: String = "",
                    val deadline: String = "",
                    val moneyNeed: Int = 0,
                    val status: String = "")
data class RescueTeam(val name: String,
                      val id: String = "")