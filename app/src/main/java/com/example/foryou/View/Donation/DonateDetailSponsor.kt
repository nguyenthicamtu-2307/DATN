package com.example.foryou.View.Donation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.IdDonateDetail
import com.example.foryou.R
import com.example.foryou.databinding.ActivityDonateDetailSponsorBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DonateDetailSponsor : AppCompatActivity() {
    private lateinit var binding:ActivityDonateDetailSponsorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonateDetailSponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIdDetail()
        setOnLickListener()
    }
    fun setOnLickListener(){
        binding.btnEdt.setOnClickListener {
            var intent = Intent(this,UpdateDonateInfor::class.java)
            startActivity(intent)
        }
    }
    fun getIdDetail(){

        val sharedRes = getSharedPreferences("DonateID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("DonateID", "")
        Log.d("is",id.toString())
        getDetailDonateId(id.toString())
    }
    fun getDetailDonateId(id:String){
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
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
        api.getDetailDonateId(id).enqueue(object :Callback<IdDonateDetail>{
            override fun onResponse(
                call: Call<IdDonateDetail>,
                response: Response<IdDonateDetail>
            ) {
                if (response.isSuccessful){
                    var data = response.body()
                    binding.txtNameSubscription.text = data?.data?.event?.eventName
                    binding.txtAmountOfMoney.text = data?.data?.rescueTeam?.name
                    binding.txtHouseHoldNumber.text = data?.data?.status
                    binding.txtNessessary.text = data?.data?.neccessariesList
                    binding.txtStatusSub.text = data?.data?.moneyTransferReceiptImgUrl
                    binding.txtNameLocalOfficer.text = data?.data?.money.toString()
                    binding.txtUrl.text= data?.data?.deadline
                }else{
                    Toast.makeText(this@DonateDetailSponsor,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<IdDonateDetail>, t: Throwable) {
                Toast.makeText(this@DonateDetailSponsor,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}