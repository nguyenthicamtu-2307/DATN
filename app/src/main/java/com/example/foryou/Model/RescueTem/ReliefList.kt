package com.example.foryou.Model.RescueTem

data class ReliefList(val totalRecords: Int = 0,
                      val data: List<DataItemRelief>?,
                      val payloadSize: Int = 0,
                      val hasNext: Boolean = false)

data class DataItemRelief(val subscribeAt: String = "",
                    val path: String = "",
                    val isCanceled: Boolean = false,
                    val eventName: String = "",
                    val id: String = "",
                    val closedAt: String,
                    val isApproved: Boolean = false,
                    val isDone: Boolean = false)