package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.ListEvent
import com.example.foryou.Model.ListItem
import com.example.foryou.Model.RescueTem.DataItemDonation
import com.example.foryou.Model.RescueTem.DonationList
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.DetailRelief
import com.example.foryou.View.Doicuutro.ListDonationRescue
import com.example.foryou.ViewModel.Adapter.HomeAdapter
import com.example.foryou.ViewModel.Adapter.OnDonationClick
import com.example.foryou.ViewModel.Adapter.PostDonationAdapter
import com.example.foryou.databinding.FragmentPlanAidBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PlanAidFragment : Fragment(),OnDonationClick {
    private lateinit var binding: FragmentPlanAidBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: PostDonationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanAidBinding.inflate(inflater)
        // Inflate the layout for this fragment
        postDonation()
        return binding.root
    }


    fun postDonation(){
        recyclerView = binding.rcvListPostAid
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
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
        api.getDonationPost().enqueue(object : Callback<DonationList>{
            override fun onResponse(call: Call<DonationList>, response: Response<DonationList>) {
                if (response.isSuccessful){
                    var dataList = response.body()
                    var adapter = PostDonationAdapter()
                    recyclerView.adapter=adapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    dataList?.data?.toMutableList()?.let { adapter.replaceList(it) }
                    adapter.addItemDonationClick(this@PlanAidFragment)
                }else{
                    Toast.makeText(requireContext(),response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DonationList>, t: Throwable) {
                Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onItemClick(dataItem: DataItemDonation) {
        val sharedPref = context?.getSharedPreferences("DonationId", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("DonationId", dataItem.id)
                apply()
            }
        }
        var intent = Intent(requireContext(), ListDonationRescue::class.java)
        startActivity(intent)
    }

}