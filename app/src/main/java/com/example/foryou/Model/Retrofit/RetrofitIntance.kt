package com.example.foryou.Model.Retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyInterceptors() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("accept:application/json", "Content-Type:application/json")
            .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImxvY2FsX29mZmljZXJ8cGhpdGh1b25nbmd1eWVuQGdtYWlsLmNvbSIsInVzZXJUeXBlIjoiTE9DQUxfT0ZGSUNFUiIsImVtYWlsIjoicGhpdGh1b25nbmd1eWVuQGdtYWlsLmNvbSIsImlkIjoiODA3ZjUyNTEtYzBjMC00ZDg4LTkwYmUtZDJkZTIxZmYwZGMyIiwicGhvbmVOdW1iZXIiOiIwMzc1MTkxMzMyIiwiaWF0IjoxNjgzMzcxNTc2LCJleHAiOjE2ODMzNzE4NzZ9.9FcuZqm6EjPx2MbsSHUaZP5UyzceLGJT32MB6WYnavY")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }



}
class RetrofitIntance {
    companion object {

        var loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
//
    
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors() )
            .addInterceptor(loggingInterceptor)
            .build()

        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }


}