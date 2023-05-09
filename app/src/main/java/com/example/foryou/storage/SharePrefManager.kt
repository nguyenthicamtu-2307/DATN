package com.example.foryou.storage

import android.content.Context
import com.example.foryou.Model.UserModel.User

class SharePrefManager private constructor(private val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }




//    fun saveUser(user: User) {
//
//        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        editor.putString("email", user.email)
//        editor.putString("name", user.name)
//        editor.putString("school", user.school)
//
//        editor.apply()
//
//    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharePrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharePrefManager {
            if (mInstance == null) {
                mInstance = SharePrefManager(mCtx)
            }
            return mInstance as SharePrefManager
        }
    }

}