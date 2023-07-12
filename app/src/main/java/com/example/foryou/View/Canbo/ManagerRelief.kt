package com.example.foryou.View.Canbo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.LocalOfficer.ManagerRescueAction
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityManagerReliefBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ManagerRelief : AppCompatActivity() {
    private lateinit var binding:ActivityManagerReliefBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerReliefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIdEventSubscription()
    }
    fun  getIdEventSubscription(){
        val sharedId = getSharedPreferences("Myis", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("idShare", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        getInforRescueAction(idDetail.toString())
    }
    fun getInforRescueAction(id:String){
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
        api.getRescuAction(id).enqueue(object : Callback<ManagerRescueAction> {
            override fun onResponse(
                call: Call<ManagerRescueAction>,
                response: Response<ManagerRescueAction>
            ) {
                if (response.isSuccessful){
                    var data = response.body()
                    binding.txtLocation.text= data?.data?.amountOfMoney
                    binding.txtEventName.text = data?.data?.neccessariesList
                    binding.txtStartAt.text = data?.data?.rescueTeamName
                    binding.txtEndAt.text = data?.data?.householdsListUrl
                    binding.txtMoneyDonate.text = data?.data?.reliefPlan?.aidPackage?.totalValue.toString()
                    binding.txtReliefNeccessary.text = data?.data?.reliefPlan?.aidPackage?.neccessariesList
                   var startTime = data?.data?.reliefPlan?.startAt
                    var endTime = data?.data?.reliefPlan?.endAt
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
                    try {
                        // Chuyển đổi chuỗi thành đối tượng Date
                        val date: Date = inputFormat.parse(startTime)
                        var enddate:Date = inputFormat.parse(endTime)
                        var formateEndTime = outputFormat.format(enddate)
                        val formattedDate = outputFormat.format(date)
                        binding.txtReliefStart.text = formattedDate.toString()
                        binding.txtReliefEnd.text = formateEndTime.toString()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else{
                    Toast.makeText(this@ManagerRelief,response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ManagerRescueAction>, t: Throwable) {
                Toast.makeText(this@ManagerRelief,"${t}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}