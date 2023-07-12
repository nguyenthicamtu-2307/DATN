package com.example.foryou.View.Doicuutro.ManagerAId

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.RescueTem.DataItemRelief
import com.example.foryou.Model.RescueTem.ReliefList
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ActivitySumaryImage
import com.example.foryou.ViewModel.Adapter.OnReliefClick
import com.example.foryou.ViewModel.Adapter.ReliefRescueAdapter
import com.example.foryou.databinding.ActivityImageSubScriptionBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageSubScription : AppCompatActivity(), OnReliefClick {
    private lateinit var binding: ActivityImageSubScriptionBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterView: ReliefRescueAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageSubScriptionBinding.inflate(layoutInflater)
        getListRelief()
        setContentView(binding.root)
    }

    fun getListRelief() {
        recyclerView = binding.rcvListImage
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
        api.getReliefList().enqueue(object : Callback<ReliefList> {
            override fun onResponse(call: Call<ReliefList>, response: Response<ReliefList>) {
                if (response.isSuccessful) {
                    var dataList = response.body()
                    adapterView = ReliefRescueAdapter()
                    recyclerView.adapter = adapterView
                    recyclerView.layoutManager =
                        LinearLayoutManager(
                            this@ImageSubScription,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    dataList?.data?.toMutableList()?.let { adapterView.replaceDatRelife(it) }
                    adapterView.addRelief(this@ImageSubScription)

                } else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<ReliefList>, t: Throwable) {
                Log.e("API", "Lỗi  dữ liệu", t)

            }

        })
    }

    override fun onItemClick(dataItem: DataItemRelief) {
        val sharedPref = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("ReliefId", dataItem.id)
                apply()
            }
        }
        var intent = Intent(this, ActivitySumaryImage::class.java)
        startActivity(intent)
    }
}