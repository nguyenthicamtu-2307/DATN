package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DataItem
import com.example.foryou.Model.Soponsor.DetailDonate
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ListDonationRescue
import com.example.foryou.View.Donation.DonateDetailSponsor
import com.example.foryou.ViewModel.Adapter.ListDonateAdapter
import com.example.foryou.ViewModel.Adapter.OnDonates
import com.example.foryou.databinding.FragmentHistoryDonationBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HistoryDonationFragment : Fragment(),OnDonates {
    private lateinit var binding: FragmentHistoryDonationBinding
    private lateinit var recyclerView:RecyclerView
    private lateinit var adapterView : ListDonateAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryDonationBinding.inflate(inflater)
        // Inflate the layout for this fragment
        getListDonates()
        return binding.root
    }
    fun getListDonates(){
        recyclerView = binding.rcvListRelief
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
        api.getInforDonate().enqueue(object : Callback<DetailDonate> {
            override fun onResponse(call: Call<DetailDonate>, response: Response<DetailDonate>) {
                if(response.isSuccessful){
                    var data = response.body()
                    adapterView = ListDonateAdapter()
                    recyclerView.adapter = adapterView
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    data?.data?.toMutableList()?.let { adapterView.replaceDatDonate(it) }
                    adapterView.addClickDonate(this@HistoryDonationFragment)
                }else{
                    Toast.makeText(requireContext(),response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DetailDonate>, t: Throwable) {
                Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun onItemClick(dataItem: DataItem) {

        val sharedPref = context?.getSharedPreferences("DonateID", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("DonateID", dataItem.id)
                apply()
            }
        }
        var intent = Intent(requireContext(), DonateDetailSponsor::class.java)
        startActivity(intent)
    }

}


