package com.example.foryou.Model.Event

data class EventRescueTeam(
    val totalRecords: Int = 0,
    val data: List<DataItemResCue>?,
    val payloadSize: Int = 0,
    val hasNext: Boolean = false
)

data class DataItemResCue(
    val closedBy: String,
    val year: Int = 0,
    val description: String = "",
    val type: String = "",
    val endAt: String = "",
    val createdAt: String = "",
    val createdBy: CreatedBy,
    val rtSubscriptionId: String,
    val name: String = "",
    val id: String = "",
    val closedAt: String,
    val isApproved: String,
    val startAt: String = "",
    val status: String = ""
)

data class CreatedByRes(
    val fullName: String = "",
    val id: String = ""
)