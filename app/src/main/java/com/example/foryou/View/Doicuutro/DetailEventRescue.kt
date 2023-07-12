package com.example.foryou.View.Doicuutro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.foryou.Model.Event.EventRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Canbo.LocalOfficeInforRegister
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.View.MainPage.ListEventsTotal
import com.example.foryou.databinding.ActivityDetailPostRescueTeamBinding
import com.example.foryou.databinding.FragmentDetailEventRescueBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailEventRescue : AppCompatActivity() {
    private lateinit var binding:FragmentDetailEventRescueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentDetailEventRescueBinding.inflate(layoutInflater)
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
        val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
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
        api.getListEventId(eventId).enqueue(object : Callback<EventRespone> {
            override fun onResponse(call: Call<EventRespone>, response: Response<EventRespone>) {
                if (response.isSuccessful){
                    val data= response.body()

                    binding.imgDetailPostRescue.resources
                    binding.txtTitleEvent.text=data?.data?.name
                    binding.txtDetailEvent.text= data?.data?.description
                    var startDate=data?.data?.startDate
                    var endDate=data?.data?.endDate
                    binding.txtStatus.text=data?.data?.status
                    binding.txtYear.text =data?.data?.year.toString()
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
                    try {
                        // Chuyển đổi chuỗi thành đối tượng Date
                        val date: Date = inputFormat.parse(startDate)
                        val endTime :Date =inputFormat.parse(endDate)
                        val formatEnd = outputFormat.format(endTime)
                        val formattedDate = outputFormat.format(date)
                        binding.txtStartTime.text = formattedDate.toString()
                        binding.txtEndTime.text = formatEnd.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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


            val intent = Intent(this, InforRegistrationRescue::class.java)
            startActivity(intent)

        }
        binding.imgBackTo.setOnClickListener {
            val intent = Intent(this, ListEventsTotal::class.java)
            startActivity(intent)
        }
    }
}