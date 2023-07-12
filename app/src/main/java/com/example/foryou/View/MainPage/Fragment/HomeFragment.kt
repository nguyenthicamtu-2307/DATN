package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Event.DataItem
import com.example.foryou.Model.Event.DataItemResCue
import com.example.foryou.Model.Event.Event
import com.example.foryou.Model.Event.EventRescueTeam
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.Retrofit.MyInterceptors

import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DataItemPost
import com.example.foryou.Model.Soponsor.ListSoponsor
import com.example.foryou.R
import com.example.foryou.View.Canbo.DetailPostRescueTeam
import com.example.foryou.View.Doicuutro.DetailEventRescue
import com.example.foryou.View.MainPage.ListEventsTotal
import com.example.foryou.ViewModel.Adapter.*
import com.example.foryou.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnHomeItemClickListener, OnRescueOnClick , OnPostDonation {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var  rcv :RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var rescueAdapter: RescueAdapter
    private lateinit var apiService: getClient
    private lateinit var eventId: String
    private var dataList = mutableListOf<ListEvent>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        addEvent()
        setOnClick()
        val sharedPref = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("tokenhome", myString.toString())
        return binding.root
    }

    fun addEvent(){
        val getUser = context?.getSharedPreferences("UserType", Context.MODE_PRIVATE)
        val userType = getUser?.getString("userType", "")
       if (userType == "local_officer"){
           addListEVent()
       }else{
           if (userType == "rescue_team"){
               addRescueListEVent()
           }
           else{
               addSponorListEVent()
               addDonationPost()
           }
       }
    }
    fun addListEVent() {
        recyclerView = binding.rcvListEvent
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
        //
        val sharedPreferences = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    events?.data?.toMutableList()?.let { homeAdapter?.replaceData(it) }
                    homeAdapter.addItemClickListener(this@HomeFragment)
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
        val sharedPreferences = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    events?.data?.toMutableList()?.let { rescueAdapter?.replaceDataRescue(it) }
                    rescueAdapter.addRescueItem(this@HomeFragment)
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
        val sharedPreferences = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    events?.data?.toMutableList()?.let { homeAdapter?.replaceData(it) }
                    homeAdapter.addItemClickListener(this@HomeFragment)
                } else {
                    Log.e("API", "Lỗi khi lấy dữ liệu: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Event>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })

    }
   fun addDonationPost(){
       rcv = binding.rcvListPost
       var loggingInterceptor =
           HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
       val baseURL = "http://172.20.10.5:3000/relief-app/v1/"
       //
       val sharedPreferences = context?.getSharedPreferences("Myref", Context.MODE_PRIVATE)
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
       api.getPostDonationSoponsor().enqueue(object : Callback<ListSoponsor>{
           override fun onResponse(call: Call<ListSoponsor>, response: Response<ListSoponsor>) {
               if (response.isSuccessful) {
                   var dataList = response.body()
                   var adapter = SoponsorAdapter()
                   rcv.adapter = adapter
                   rcv.layoutManager =
                       LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                   dataList?.data?.toMutableList()?.let { adapter.replacePost(it) }
                   adapter.addDonationPost(this@HomeFragment)
               } else {
                   Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()

               }
           }

           override fun onFailure(call: Call<ListSoponsor>, t: Throwable) {
               Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()
           }

       })
   }

    override fun onItemClick(dataItem: DataItem) {
        val sharedPref = context?.getSharedPreferences("Myid", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("id", dataItem.id)
                apply()
            }

        }

        if(dataItem.eventSubscriptionId != null){
            val sharedId = context?.getSharedPreferences("Myis", Context.MODE_PRIVATE)
            if (sharedId != null) {
                with(sharedId.edit()) {
                    putString("idShare", dataItem.eventSubscriptionId)
                    apply()

                }
        }

        }



        var intent = Intent(requireContext(), DetailPostRescueTeam::class.java)
        startActivity(intent)
    }

   override fun onItemClick(dataItem: DataItemResCue) {
       val sharedPref = context?.getSharedPreferences("Myid", Context.MODE_PRIVATE)
       if (sharedPref != null) {
           with(sharedPref.edit()) {
               putString("id", dataItem.id)
               apply()
           }
       }


       val sharedId = context?.getSharedPreferences("RescueID", Context.MODE_PRIVATE)
       if (sharedId != null) {
           with(sharedId.edit()) {

               putString("idShare", dataItem.rtSubscriptionId)
               apply()

           }
       }else{
           Toast.makeText(requireContext(),"null",Toast.LENGTH_SHORT).show()
       }
       var intent = Intent(requireContext(),DetailEventRescue::class.java)
       startActivity(intent)



   }

fun setOnClick(){
    binding.txtSeeAll.setOnClickListener {
        var intent = Intent(requireContext(),ListEventsTotal::class.java)
        startActivity(intent)
    }
}

    override fun onItemClick(dataItem: DataItemPost) {
        TODO("Not yet implemented")
    }
}