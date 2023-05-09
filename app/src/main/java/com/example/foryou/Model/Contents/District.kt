package com.example.foryou.Model.Contents

data class District(
    var id:String,
    var name:String,
    var code :String,
    var nameWithType:String,
    var parentCode:String,
    var path:String,
    var pathWithType:String
)

data class DistrictResponse(val districts: List<District>)

data class ApiResponse(val data: DistrictResponse)
