package com.example.foryou.Model.RescueTem

data class Subscription(val code: Int = 0,
                        val data: DataSub,
                        val success: Boolean = false,
                        val timestamp: Long = 0)
data class DataSub(val loEventSubscriptions: List<LoEventSubscriptionsItem>?)
data class LoEventSubscriptionsItem(val path: String = "",
                                    val id: String = "",
                                    val householdsNumber: Int = 0)