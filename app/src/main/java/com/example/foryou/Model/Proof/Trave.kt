package com.example.foryou.Model.Proof

data class Trave(val code: Int = 0,
                 val data: Data,
                 val success: Boolean = false,
                 val timestamp: Long = 0)
data class Data(val subscriptionIds: List<String>?)