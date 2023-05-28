package com.example.foryou.View.Doicuutro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.foryou.Model.RescueTem.RegisSubRespone
import com.example.foryou.Model.RescueTem.SubScriptionRequest
import com.example.foryou.Model.RescueTem.Subscription
import com.example.foryou.Model.RescueTem.SubscriptionsItem
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityInforRegistrationRescueBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InforRegistrationRescue : AppCompatActivity() {
    private lateinit var binding:ActivityInforRegistrationRescueBinding
    private lateinit var location: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforRegistrationRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAddressLocal()
        setOnClick()
    }


    fun getAddressLocal(){
        val sharedPref = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("t",myString.toString())
        val shared = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        val productId = shared?.getString("id", "")
        Log.d("id",productId.toString())
        val sharedRes = getSharedPreferences("RescueID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("idShare", "")
        Log.d("is",id.toString())

        regisRescue(productId.toString())
    }

    fun regisRescue(subScriptionId:String){
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
        api.subScriptionId(subScriptionId).enqueue(object : Callback<Subscription>{
            override fun onResponse(call: Call<Subscription>, response: Response<Subscription>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data?.loEventSubscriptions
                    val listProvince = arrayListOf<String>()
                    if (data != null) {
                        for (i in data.indices) {
                            data[i].path.let {
                                listProvince.add(
                                    it
                                )
                            }
                        }
                    }

                    if (data != null) {
                        // Cập nhật Spinner với danh sách quận huyện
                        val districtNames = data.map { it.path }

                        val adapter = ArrayAdapter(
                            this@InforRegistrationRescue,
                            android.R.layout.simple_spinner_item,
                            districtNames
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinerAddress.adapter = adapter
                        binding.spinerAddress.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val LocationItem =
                                    data?.get(binding.spinerAddress.selectedItemPosition)
                                location = LocationItem?.id.toString()
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }

                        }

                    }
                }
            }

            override fun onFailure(call: Call<Subscription>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)

            }

        })
    }
    fun regisEvent(){
        var fromMobile:Boolean = true
        var location = location
        var money = binding.edtMoneyAid.text.toString()
        var subList : List<SubscriptionsItem> = listOf( SubscriptionsItem(location,money.toInt()))
        var subRequest = SubScriptionRequest(subList,fromMobile)
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
        api.regisSubscriptRescue(subRequest).enqueue(object : Callback<RegisSubRespone>{
            override fun onResponse(
                call: Call<RegisSubRespone>,
                response: Response<RegisSubRespone>
            ) {
                    if (response.isSuccessful){
                        var data = response.body()
                        Log.d("data",data.toString())
                        var dtaSub = data?.data?.subscriptionIds?.get(0).toString()
                        Log.d("ab",dtaSub.toString())

                        val sharedPref = getSharedPreferences("DataSub", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("data", dtaSub.toString())
                            apply()
                        }

                        Toast.makeText(this@InforRegistrationRescue,"đăng ký thành công",Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@InforRegistrationRescue,ReliefPlanRescue::class.java)
                        startActivity(intent)
                    }
                else{
                        Log.e("API", "Lỗi : ${response.message()}")

                    }
            }

            override fun onFailure(call: Call<RegisSubRespone>, t: Throwable) {
                Log.e("API", "Lỗi khi lấy dữ liệu", t)

            }

        })
    }
    fun setOnClick(){
        binding.btnConfirmRegis.setOnClickListener {
            regisEvent()
        }
    }
}