package com.example.foryou.View.Donation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DonateInforRequest
import com.example.foryou.Model.Soponsor.DonatiaInforRespone
import com.example.foryou.R
import com.example.foryou.View.Doicuutro.ListDonationRescue
import com.example.foryou.View.Donation.MainPage.Fragment.ManagerFragment
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.databinding.ActivityUpdateDonateInforBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateDonateInfor : AppCompatActivity() {
    private lateinit var binding :ActivityUpdateDonateInforBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDonateInforBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOn()
    }
    fun setOn(){
        binding.btnConfirm.setOnClickListener {
            getIdDetail()
        }
    }
    fun getIdDetail(){

        val sharedRes = getSharedPreferences("DonateID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("DonateID", "")
        Log.d("is",id.toString())
        updateDonateInfor(id.toString())
    }
    fun updateDonateInfor(id:String){
        var fromMobile:Boolean = true
        var moneyNeed = binding.edtMoneyDonatio.text.toString()
        var need = binding.edtNhuYeuPham.text.toString()
        var donateRequest = DonateInforRequest(moneyNeed.toInt(), need)
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
        api.upDateDonationInfor(donateRequest,id).enqueue(object :Callback<DonatiaInforRespone>{
            override fun onResponse(
                call: Call<DonatiaInforRespone>,
                response: Response<DonatiaInforRespone>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(this@UpdateDonateInfor,"Update data successfull", Toast.LENGTH_SHORT).show()
                    var intent =Intent(this@UpdateDonateInfor,DonateDetailSponsor::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@UpdateDonateInfor,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DonatiaInforRespone>, t: Throwable) {
                Toast.makeText(this@UpdateDonateInfor,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}