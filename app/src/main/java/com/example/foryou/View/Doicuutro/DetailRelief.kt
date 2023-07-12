package com.example.foryou.View.Doicuutro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.foryou.Model.RescueTem.DetailRelief
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.databinding.ActivityDetailReliefBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DetailRelief : AppCompatActivity() {
    private lateinit var binding:ActivityDetailReliefBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailReliefBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIdRelief()
        setOnClick()
    }

    fun getIdRelief(){
        val sharedPref = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("t",myString.toString())
        val shared = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        val productId = shared?.getString("id", "")
        Log.d("id",productId.toString())
        val sharedRes = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("ReliefId", "")
        Log.d("is",id.toString())
        detailRelief(id.toString())
    }
    fun detailRelief(id:String){
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
        api.detailReliefPlan(id).enqueue(object :Callback<DetailRelief>{
            override fun onResponse(call: Call<DetailRelief>, response: Response<DetailRelief>) {
                if (response.isSuccessful){
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy")
                    var dataBinding = response.body()
                    binding.txtNameSubscription.text = dataBinding?.data?.rescueTeamName
                    binding.txtAmountOfMoney.text = dataBinding?.data?.reliefPlan?.aidPackage?.amountOfMoney.toString()
                   var start = dataBinding?.data?.reliefPlan?.startAt
                    var end = dataBinding?.data?.reliefPlan?.endAt
                    binding.txtNameLocalOfficer.text = dataBinding?.data?.reliefPlan?.aidPackage?.totalValue.toString()
                    binding.txtNessessary.text = dataBinding?.data?.reliefPlan?.aidPackage?.neccessariesList
                    var create = dataBinding?.data?.createdAt
                    try {
                        // Chuyển đổi chuỗi thành đối tượng Date
                        val date: Date = inputFormat.parse(start)
                        var endat :Date = inputFormat.parse(end)
                        var createAt:Date = inputFormat.parse(create)
                        val formattedDate = outputFormat.format(date)
                        var finishAt = outputFormat.format(endat)
                        var createStart = outputFormat.format(createAt)
                        binding.txtStatusSub.text = formattedDate.toString()
                        binding.txtHouseHoldNumber.text = formattedDate.toString()
                        binding.txtUrl.text = finishAt.toString()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    val sharedPref = getSharedPreferences("DetailId", Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString("DetailId", dataBinding?.data?.reliefPlan?.id)
                            apply()
                        }
                    }
                }else{
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<DetailRelief>, t: Throwable) {
                Log.e("API", "Lỗi  dữ liệu", t)
            }

        })
    }
    fun setOnClick(){
        binding.btnEdt.setOnClickListener {
            var intent = Intent(this,UpdateDetailRelief::class.java)
            startActivity(intent);
        }
        binding.btnPost.setOnClickListener {
            var intent = Intent(this,PostRescueTeam::class.java)
            startActivity(intent)
        }
        binding.imgBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}