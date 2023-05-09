package com.example.foryou.Model.Contents

data class ProvincesItem(
    val nameWithType: String,
    val code: String,
    val name: String,
    val id: String
)
data class Provinces(val provinces: List<ProvincesItem>?)
data class ProvincesRespon(val data: Provinces)