package com.example.foryou.View.Canbo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foryou.Model.Event.EventRespone
import com.example.foryou.Model.Retrofit.MyInterceptors

import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.View.Doicuutro.InforRegistrationRescue
import com.example.foryou.databinding.ActivityDetailPostRescueTeamBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailPostRescueTeam : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostRescueTeamBinding
    private lateinit var apiService: getClient


private lateinit var  productId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailPostRescueTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDetailEvent()
        setOnClick()
    }

    fun getDetailEvent(){
        val sharedPref = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("t",myString.toString())
        val shared = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        val productId = shared?.getString("id", "")
        Log.d("id",productId.toString())
        val sharedRes = getSharedPreferences("RescueID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("idShare", "")
        Log.d("is",id.toString())
        getEventById(productId.toString())

    }

    fun getEventById(eventId:String){
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
        //
        val sharedPreferences = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val client = OkHttpClient.Builder()
            .addInterceptor(MyInterceptors(sharedPreferences))
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(getClient::class.java)
        api.getListEventId(eventId).enqueue(object : Callback<EventRespone>{
            override fun onResponse(call: Call<EventRespone>, response: Response<EventRespone>) {
                if (response.isSuccessful){
                    val data= response.body()

                   binding.imgDetailPostRescue.resources
                    binding.txtTitleEvent.text=data?.data?.name
                    binding.txtDetailEvent.text= data?.data?.description
                    binding.txtStartTime.text=data?.data?.startDate
                    binding.txtEndTime.text=data?.data?.endDate
                    binding.txtStatus.text=data?.data?.status

                }
                else{
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<EventRespone>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)

            }

        })

    }
    fun setOnClick(){


            binding.btnDKCT.setOnClickListener {


                val intent = Intent(this,LocalOfficeInforRegister::class.java)
                startActivity(intent)

        }
    }
}