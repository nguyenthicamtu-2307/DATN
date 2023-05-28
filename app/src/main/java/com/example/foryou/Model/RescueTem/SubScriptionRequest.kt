package com.example.foryou.Model.RescueTem

data class SubScriptionRequest(val subscriptions: List<SubscriptionsItem>?,
                               val fromMobile: Boolean = true)
data class SubscriptionsItem(val subscriptionID: String = "",
                             val originalMoney: Int)
data class RegisSubRespone(val code: Int = 0,
                           val data: DataRp,
                           val success: Boolean = false,
                           val timestamp: Long = 0)
data class DataRp(val subscriptionIds: List<String>?)

