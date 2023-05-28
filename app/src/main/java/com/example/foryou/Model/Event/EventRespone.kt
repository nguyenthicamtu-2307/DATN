package com.example.foryou.Model.Event

data class EventRespone(val code: Int = 0,
                        val data: Data,
                        val success: Boolean = false,
                        val timestamp: Long = 0)
data class Data(val closedBy: String,
                val createdAt: String = "",
                val endDate: String = "",
                val createdBy: CreatedBy,
                val year: Int = 0,
                val name: String = "",
                val description: String = "",
                val id: String = "",
                val type: String = "",
                val imageUrl:String="",
                val startDate: String = "",
                val status: String = "")
data class CreatedBy(val fullName: String = "",
                     val id: String = "")