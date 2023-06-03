package com.example.foryou.View.Doicuutro

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.foryou.Model.RescueTem.InforAid
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.Fragment.ManagerAidFragment
import com.example.foryou.databinding.ActivityInforAidBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InforAidRescue : AppCompatActivity() {
    private lateinit var binding: ActivityInforAidBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforAidBinding.inflate(layoutInflater)

        setContentView(binding.root)
        getId()

    }
    fun getId(){
        val sharedId = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("ReliefId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        detailInforAid(idDetail.toString())
    }
    fun detailInforAid(id:String){


        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
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
        api.getInforAidRescue(id).enqueue(object : Callback<InforAid>{
            override fun onResponse(call: Call<InforAid>, response: Response<InforAid>) {
                if (response.isSuccessful){
                    var data = response.body()
                    binding.txtLocation.text = data?.data?.path
                    binding.txtEventName.text = data?.data?.eventName
                    binding.txtStartAt.text =data?.data?.subscribeAt
                    binding.txtEndAt.text = data?.data?.closedAt
                    binding.txtHouseHoldNumber.text =data?.data?.householdNumber.toString()
                    binding.txtUrl.text = data?.data?.householdsListUrl
                    binding.txtAmountOfMoney.text = data?.data?.isApproved.toString()


                }else{
                    Toast.makeText(this@InforAidRescue,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<InforAid>, t: Throwable) {
                Toast.makeText(this@InforAidRescue,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }

}