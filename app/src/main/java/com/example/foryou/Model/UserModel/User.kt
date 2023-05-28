package com.example.foryou.Model.UserModel

import android.text.Editable

data class  User(
   val userType:String,
   val email: String,
   val password:String,
   val firstName:String,
   val middleName:String,
   val lastName:String,
   val phoneNumber: String,
   val rescueTeamName:String,
   val provinceId:String,
   val districtId:String,
   val wardId: String
        )
