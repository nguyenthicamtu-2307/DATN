package com.example.foryou.Model.Contents


data class WardsItem(
    val nameWithType: String,
    val path: String,
    val code: String,
    val parentCode: String,
    val name: String,
    val pathWithType: String,
    val id: String
)

data class Wards(val wards: List<WardsItem>?)
data class WardsRespon(val data: Wards)