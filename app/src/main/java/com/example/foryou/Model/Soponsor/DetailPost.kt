package com.example.foryou.Model.Soponsor

data class DetailPost(val code: Int = 0,
                      val data: DataDetailPos,
                      val success: Boolean = false,
                      val timestamp: Long = 0)
data class DataDetailPos(val necessariesList: String = "",
                val donatedMoney: Int = 0,
                val description: String = "",
                val id: String = "",
                val deadline: String = "",
                val moneyNeed: Int = 0,
                val status: String = "")