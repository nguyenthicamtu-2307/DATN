package com.example.foryou.View.Doicuutro.DonationPost

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.RescueTem.DataItemDonation
import com.example.foryou.Model.RescueTem.DonationList
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DataItemPost
import com.example.foryou.Model.Soponsor.ListSoponsor
import com.example.foryou.R
import com.example.foryou.View.Donation.DetailPostDonation
import com.example.foryou.ViewModel.Adapter.OnDonationClick
import com.example.foryou.ViewModel.Adapter.OnPostDonation
import com.example.foryou.ViewModel.Adapter.PostDonationAdapter
import com.example.foryou.ViewModel.Adapter.SoponsorAdapter
import com.example.foryou.databinding.ActivityListdonationPostRescueBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListdonationPostRescue : AppCompatActivity(), OnDonationClick {
    private lateinit var binding: ActivityListdonationPostRescueBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListdonationPostRescueBinding.inflate(layoutInflater)
        getDonationPost()
        setContentView(binding.root)
    }
    fun getDonationPost(){
        recyclerView = binding.rcvListDonation
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
        //
        val sharedPreferences =getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
        api.getDonationPost().enqueue(object : Callback<DonationList>{
            override fun onResponse(call: Call<DonationList>, response: Response<DonationList>) {
                if (response.isSuccessful){
                    var dataList = response.body()
                    var adapter = PostDonationAdapter()
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@ListdonationPostRescue, LinearLayoutManager.VERTICAL, false)
                    dataList?.data?.toMutableList()?.let { adapter.replaceList(it) }
                    adapter.addItemDonationClick(this@ListdonationPostRescue)
                }else{
                    Toast.makeText(this@ListdonationPostRescue,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DonationList>, t: Throwable) {
                Toast.makeText(this@ListdonationPostRescue,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }



    override fun onItemClick(dataItem: DataItemDonation) {
        val sharedPref = getSharedPreferences("DonationIdPost", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("DonationId", dataItem.id)
                apply()
            }
        }
        var intent = Intent(this, ListSponsorDonate::class.java)
        startActivity(intent)
    }

}