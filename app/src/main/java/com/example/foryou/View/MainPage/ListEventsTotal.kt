package com.example.foryou.View.MainPage

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Event.DataItemResCue
import com.example.foryou.Model.Event.Event
import com.example.foryou.Model.Event.EventRescueTeam
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.View.Canbo.DetailPostRescueTeam
import com.example.foryou.View.Doicuutro.DetailEventRescue
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import com.example.foryou.ViewModel.Adapter.OnHomeItemClickListener
import com.example.foryou.ViewModel.Adapter.OnRescueOnClick
import com.example.foryou.ViewModel.Adapter.RescueAdapter
import com.example.foryou.databinding.ActivityListEventBinding
import com.example.foryou.databinding.ListEventBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListEventsTotal : AppCompatActivity() , OnHomeItemClickListener, OnRescueOnClick {
    private lateinit var binding:ActivityListEventBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var rescueAdapter: RescueAdapter
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEventBinding.inflate(layoutInflater)
        addEvent()
        setContentView(binding.root)
    }
    fun addEvent(){
        val getUser = getSharedPreferences("UserType", Context.MODE_PRIVATE)
        val userType = getUser?.getString("userType", "")
        if (userType == "local_officer"){
            addListEVent()
        }else{
            if (userType == "rescue_team"){
                addRescueListEVent()
            }
            else{
                addSponorListEVent()

            }
        }
    }
    fun addListEVent(){
        recyclerView = binding.rcvListEvent
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
        api.getListEvent().enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    var events = response.body()
                    var id = events?.data
                    if (id != null) {
                        for (events in id) {
                            eventId = events.id
                            Log.d("id", eventId)
                        }

                    }
                    homeAdapter = HomeAdapter()
//
                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@ListEventsTotal, LinearLayoutManager.VERTICAL, false)
                    events?.data?.toMutableList()?.let { homeAdapter?.replaceData(it) }
                    homeAdapter.addItemClickListener(this@ListEventsTotal)
                } else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })
    }
    fun addRescueListEVent() {
        recyclerView = binding.rcvListEvent
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
        api.getRescueEvent().enqueue(object : Callback<EventRescueTeam> {
            override fun onResponse(call: Call<EventRescueTeam>, response: Response<EventRescueTeam>) {
                if (response.isSuccessful) {
                    var events = response.body()
                    var id = events?.data
                    if (id != null) {
                        for (events in id) {
                            eventId = events.id
                            Log.d("id", eventId)
                        }

                    }
                    rescueAdapter = RescueAdapter()
//
                    recyclerView.adapter = rescueAdapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@ListEventsTotal, LinearLayoutManager.VERTICAL, false)
                    events?.data?.toMutableList()?.let { rescueAdapter?.replaceDataRescue(it) }
                    rescueAdapter.addRescueItem(this@ListEventsTotal)
                } else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventRescueTeam>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })

    }
    fun addSponorListEVent() {
        recyclerView = binding.rcvListEvent
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
        api.getSoponorEvent().enqueue(object : Callback<Event> {
            override fun onResponse(call: Call<Event>, response: Response<Event>) {
                if (response.isSuccessful) {
                    var events = response.body()
                    var id = events?.data
                    if (id != null) {
                        for (events in id) {
                            eventId = events.id
                            Log.d("id", eventId)
                        }

                    }
                    homeAdapter = HomeAdapter()
//
                    recyclerView.adapter = homeAdapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(this@ListEventsTotal, LinearLayoutManager.VERTICAL, false)
                    events?.data?.toMutableList()?.let { homeAdapter?.replaceData(it) }
                    homeAdapter.addItemClickListener(this@ListEventsTotal)
                } else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })

    }

    override fun onItemClick(dataItem: DataItem) {
        val sharedPref = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("id", dataItem.id)
                apply()
            }

        }

        if(dataItem.eventSubscriptionId != null){
            val sharedId = getSharedPreferences("Myis", Context.MODE_PRIVATE)
            if (sharedId != null) {
                with(sharedId.edit()) {
                    putString("idShare", dataItem.eventSubscriptionId)
                    apply()

                }
            }

        }



        var intent = Intent(this, DetailPostRescueTeam::class.java)
        startActivity(intent)
    }

    override fun onItemClick(dataItem: DataItemResCue) {
        val sharedPref = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("id", dataItem.id)
                apply()
            }
        }


        val sharedId = getSharedPreferences("RescueID", Context.MODE_PRIVATE)
        if (sharedId != null) {
            with(sharedId.edit()) {

                putString("idShare", dataItem.rtSubscriptionId)
                apply()

            }
        }else{
            Toast.makeText(this,"null", Toast.LENGTH_SHORT).show()
        }
        var intent = Intent(this, DetailEventRescue::class.java)
        startActivity(intent)

    }
}