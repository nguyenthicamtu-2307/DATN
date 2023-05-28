package com.example.foryou.View.Canbo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foryou.Model.Event.evenRegisRespone
import com.example.foryou.Model.Event.eventRequest
import com.example.foryou.Model.Retrofit.MyInterceptors

import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityLocalOfficeInforRegisterBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalOfficeInforRegister : AppCompatActivity() {
    private lateinit var binding:ActivityLocalOfficeInforRegisterBinding
    private lateinit var apiService: getClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalOfficeInforRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()
    }

    fun onClick(){
        binding.btnDK.setOnClickListener {
            registerEvent()
        }
    }
    fun registerEvent(){
        val shared = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        val productId = shared?.getString("id", "")
        Log.d("id",productId.toString())
        var houseHolds = binding.edtNumberOfHosehold.text.toString()
        var eventRequest = eventRequest(houseHolds.toInt())
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
        api.registerEvent(eventRequest,productId.toString()).enqueue(object : Callback<evenRegisRespone>{
            override fun onResponse(
                call: Call<evenRegisRespone>,
                response: Response<evenRegisRespone>
            ) {
                if (response.isSuccessful)
                {
                    val idSub = response.body()?.data?.id.toString()
                    val sharedPref = getSharedPreferences("IdShare", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("idShare", idSub)
                        apply()
                    }
                    Log.d("idSub",idSub.toString())
                    Toast.makeText(this@LocalOfficeInforRegister,"Register successfull",Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@LocalOfficeInforRegister,DetailSubscription::class.java)
                    intent.putExtra("id",idSub)
                    startActivity(intent)
                }else{
                    Log.e("API", "fail: ${response.message()}")
                }
            }


            override fun onFailure(call: Call<evenRegisRespone>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)
            }

        })




    }
}