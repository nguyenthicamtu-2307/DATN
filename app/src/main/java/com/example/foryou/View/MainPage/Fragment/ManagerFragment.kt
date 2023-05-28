package com.example.foryou.View.Donation.MainPage.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.foryou.Model.LocalOfficer.ManagerRescueAction
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.FragmentManagerActivityAidBinding
import com.example.foryou.databinding.FragmentManagerAidBinding
import com.example.foryou.databinding.FragmentSeconBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ManagerFragment : Fragment() {
    private lateinit var binding:FragmentSeconBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSeconBinding.inflate(inflater)
        getIdEventSubscription()
        return binding.root
    }
    fun  getIdEventSubscription(){
        val sharedId = context?.getSharedPreferences("Myis", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("idShare", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        getInforRescueAction(idDetail.toString())
    }
    fun getInforRescueAction(id:String){
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
        api.getRescuAction(id).enqueue(object :Callback<ManagerRescueAction>{
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
                    binding.txtReliefStart.text = data?.data?.reliefPlan?.startAt
                    binding.txtReliefEnd.text = data?.data?.reliefPlan?.endAt
                }
                else{
                    Toast.makeText(requireContext(),response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ManagerRescueAction>, t: Throwable) {
                Toast.makeText(requireContext(),"${t}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}