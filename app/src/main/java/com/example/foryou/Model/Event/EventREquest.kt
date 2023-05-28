package com.example.foryou.Model.Event

import java.net.IDN

data class evenRegisRespone(val code: Int = 0,
                        val data: Dataid,
                        val success: Boolean = false,
                        val timestamp: Long = 0)
data class eventRequest(
    var householdNumber:Int
)
data class Dataid(
    var id:String
)
