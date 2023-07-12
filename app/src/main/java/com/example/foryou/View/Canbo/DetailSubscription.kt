package com.example.foryou.View.Canbo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foryou.Model.LocalOfficer.DetailRegisSub
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.databinding.ActivityDetailSubscriptionBinding
import com.example.foryou.databinding.ActivityLocalOfficeInforRegisterBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailSubscription : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSubscriptionBinding
    private lateinit var idSub:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDetail()
        onClick()
    }

    fun onClick(){

        binding.btnEdt.setOnClickListener {
         var intent = Intent(this,UpdateSubscription::class.java)
            startActivity(intent)

        }
        binding.imBack.setOnClickListener {
            var intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
    fun getDetail(){
        val sharedId = getSharedPreferences("IdShare", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("idShare", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        getDetailbyID(idDetail.toString())
    }
    fun getDetailbyID(id:String){

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
        api.getDetailSubscriptions(id).enqueue(object : Callback<DetailRegisSub>{
            override fun onResponse(
                call: Call<DetailRegisSub>,
                response: Response<DetailRegisSub>
            ) {
                if (response.isSuccessful){
                    val data= response.body()
                    binding.txtNameSubscription.text = data?.data?.eventName
                    binding.txtAmountOfMoney.text =data?.data?.amountOfMoney
                    binding.txtHouseHoldNumber.text = data?.data?.householdsNumber.toString()
                    binding.txtUrl.text = data?.data?.householdsListUrl
                    binding.txtNameLocalOfficer.text= data?.data?.localOfficerName
                    binding.txtNessessary.text = data?.data?.neccessariesList
                    binding.txtStatusSub.text = data?.data?.isCompleted.toString()
                   idSub = data?.data?.id.toString()
                    val sharedPref = getSharedPreferences("IDSUBSCRIPTION", Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString("id", idSub)
                            apply()
                        }

                    }
                }else{
                    Log.e("API", "fail khi load dữ liệu: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<DetailRegisSub>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)


            }

        })
    }
}