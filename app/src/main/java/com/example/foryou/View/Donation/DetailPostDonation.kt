package com.example.foryou.View.Donation

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.Soponsor.DetailPost
import com.example.foryou.R
import com.example.foryou.databinding.ActivityDetailPostDonationBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailPostDonation : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostDonationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnCLickInfor()
        getId()
    }

    fun getId() {
        val sharedId = getSharedPreferences("DonationIdPost", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DonationId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare", idDetail.toString())
        getDetailPost(idDetail.toString())
    }


    fun getDetailPost(id: String) {
        var prercent = 100
        var title = binding.txtEvent
        var process = binding.progressBar
        var currentMoney = binding.txtDonationMoney
        var needMoney = binding.txtMoneyNeed
        var description = binding.txtDetail
        var neccess = binding.txtStarDate
        var endDate = binding.txtFinishDate
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
        api.getDetailPostSoponsor(id).enqueue(object : Callback<DetailPost> {
            override fun onResponse(call: Call<DetailPost>, response: Response<DetailPost>) {
                if (response.isSuccessful) {
                    var data = response.body()
                    description.text = data?.data?.description
                    endDate.text = data?.data?.deadline
                    currentMoney.text = data?.data?.donatedMoney?.toInt().toString()
                    neccess.text = data?.data?.necessariesList
                    needMoney.text = data?.data?.moneyNeed.toString()

                    var money1: Double = needMoney.text.toString().toDouble()
                    var money2: Double = currentMoney.text.toString().toDouble()
                    var progressItem = ((money2 / money1) * (prercent / 100))
                    process.setProgress(progressItem.toInt())
                    process.progressTintList = ColorStateList.valueOf(Color.RED)
                } else {
                    Toast.makeText(this@DetailPostDonation, response.message(), Toast.LENGTH_SHORT)
                        .show()

                }
            }

            override fun onFailure(call: Call<DetailPost>, t: Throwable) {
                Toast.makeText(this@DetailPostDonation, "${t}", Toast.LENGTH_SHORT).show()

            }

        })
//

    }
    fun setOnCLickInfor(){
        binding.btnDonation.setOnClickListener {
            var intent = Intent(this,InforDonation::class.java)
            startActivity(intent)
        }
    }
}