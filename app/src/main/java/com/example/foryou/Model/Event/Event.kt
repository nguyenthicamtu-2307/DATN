package com.example.foryou.Model.Event

data class Event(
    val totalRecords: Int = 0,
    val data: List<DataItem>?,
    val payloadSize: Int = 0,
    val hasNext: Boolean = false
)

data class DataItem(
    val createdAt: String = "",
    val eventSubscriptionId: String,
    val year: Int = 0,
    val name: String = "",
    val eventType: String = "",
    val id: String = "",
    val closedAt: String,
    val endAt: String = "",
    val startAt: String = "",
    val status: String = ""
)
