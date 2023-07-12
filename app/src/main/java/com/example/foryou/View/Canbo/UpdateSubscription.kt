package com.example.foryou.View.Canbo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.LocalOfficer.EditSubRespone
import com.example.foryou.Model.LocalOfficer.EditSubscription
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityUpdateSubscriptionBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateSubscription : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateSubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnLick()
    }
    fun setOnLick(){
        binding.btnConfirm.setOnClickListener {
            getIdSub()
        }
    }
    fun getIdSub(){
        val sharedId = getSharedPreferences("IDSUBSCRIPTION", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("id", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        updateSubscription(idDetail.toString())
    }
    fun updateSubscription(subId:String){
        var houseHold= binding.edtHoseholdNumber.text.toString()
        var urlHouse = binding.edtHouseholdUrl.text.toString()
        var money = binding.edtMoneyNeed.text.toString()
        var neccessary = binding.edtNeed.text.toString()
        var update = EditSubscription(neccessary,houseHold.toInt(),urlHouse,money.toInt())

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
        api.updateSubscription(subId,update).enqueue(object : Callback<EditSubRespone> {
            override fun onResponse(
                call: Call<EditSubRespone>,
                response: Response<EditSubRespone>
            ) {
                if(response.isSuccessful){
                    var data=response.body()
                    Toast.makeText(this@UpdateSubscription,"update successfull", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdateSubscription,DetailSubscription::class.java)
                    startActivity(intent)
                }else{
                    Log.e("API", "fail khi load dữ liệu: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<EditSubRespone>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)

            }

        })
    }
}