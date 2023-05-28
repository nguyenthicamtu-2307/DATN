package com.example.foryou.Model.Retrofit

import android.content.Context
import android.content.SharedPreferences
import com.example.foryou.View.Login.LoginActivity
import com.example.foryou.storage.SharePrefManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyInterceptors(private val sharedPreferences: SharedPreferences?) : Interceptor {

    private var accessToken: String? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = sharedPreferences?.getString("token", "")
        val requestBuilder = original.newBuilder()
            .header("accept:application/json", "Content-Type:application/json")
            .header("Authorization", "Bearer $token " )
        val request = requestBuilder.build()
        return  chain.proceed(request)

    }


}
