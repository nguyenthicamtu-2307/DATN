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
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DataItemPost
import com.example.foryou.Model.Soponsor.ListSoponsor
import com.example.foryou.View.Doicuutro.ListDonationRescue
import com.example.foryou.View.Donation.DetailPostDonation
import com.example.foryou.ViewModel.Adapter.OnPostDonation
import com.example.foryou.ViewModel.Adapter.SoponsorAdapter
import com.example.foryou.databinding.FragmentListDonationBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListDonationFragment : Fragment(),OnPostDonation {
    private lateinit var binding:FragmentListDonationBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentListDonationBinding.inflate(layoutInflater)
        getDonationPost()
        return binding.root

    }

    fun getDonationPost(){
        recyclerView = binding.rcvListDonation
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
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
        api.getPostDonationSoponsor().enqueue(object :Callback<ListSoponsor> {
            override fun onResponse(call: Call<ListSoponsor>, response: Response<ListSoponsor>) {
                if (response.isSuccessful) {
                    var dataList = response.body()
                    var adapter = SoponsorAdapter()
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    dataList?.data?.toMutableList()?.let { adapter.replacePost(it) }
                    adapter.addDonationPost(this@ListDonationFragment)
                } else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()

                }
            }


            override fun onFailure(call: Call<ListSoponsor>, t: Throwable) {
                Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()
            }


})
    }

    override fun onItemClick(dataItem: DataItemPost) {
        val sharedPref = context?.getSharedPreferences("DonationIdPost", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            with(sharedPref.edit()) {
                putString("DonationId", dataItem.id)
                apply()
            }
        }
        var intent = Intent(requireContext(), DetailPostDonation::class.java)
        startActivity(intent)
    }

}