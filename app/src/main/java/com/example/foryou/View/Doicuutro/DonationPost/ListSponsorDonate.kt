package com.example.foryou.View.Doicuutro.DonationPost

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.foryou.Model.Donation.DonationDetail
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityListSponsorDonateBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListSponsorDonate : AppCompatActivity() {
    private lateinit var binding: ActivityListSponsorDonateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListSponsorDonateBinding.inflate(layoutInflater)
        getIdDonation()
        setContentView(binding.root)
    }

    fun getIdDonation() {
        val sharedId = getSharedPreferences("DonationId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DonationId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare", idDetail.toString())
        getSponsor(idDetail.toString())
    }

    fun getSponsor(id: String) {

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
        api.getDetailDonationPost(id).enqueue(object : Callback<DonationDetail> {
            override fun onResponse(
                call: Call<DonationDetail>,
                response: Response<DonationDetail>
            ) {
                if (response.isSuccessful) {
                    var dataDetail = response.body()
                    var itemlist = dataDetail?.data?.sponsors
                    if (dataDetail != null) {
                        if (itemlist != null) {
                            for (item in itemlist) {
                                var tableLayout = binding.tbLayout
                                val tableRow = TableRow(this@ListSponsorDonate)
                                var name = TextView(this@ListSponsorDonate)
                                name.text = item.name.toString()
                                tableRow.addView(name)
                                tableLayout.addView(tableRow)

                            }
                        } else {
                            Toast.makeText(
                                this@ListSponsorDonate,
                                "chưa có mạnh thường quân quyên góp",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                } else {
                    Toast.makeText(this@ListSponsorDonate, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<DonationDetail>, t: Throwable) {
                Toast.makeText(this@ListSponsorDonate, "${t}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}