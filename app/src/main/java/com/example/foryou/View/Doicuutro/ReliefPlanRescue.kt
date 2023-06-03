package com.example.foryou.View.Doicuutro

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.foryou.Model.RescueTem.PlanRelief
import com.example.foryou.Model.RescueTem.PlanRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityReliefPlanRescueBinding
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.*


class ReliefPlanRescue : AppCompatActivity() {
    private lateinit var binding:ActivityReliefPlanRescueBinding

    private lateinit var calendar:Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReliefPlanRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)
        pickCalendar()
        onClick()
    }

    fun onClick(){
        binding.btnDonation.setOnClickListener {
            idRelief()
        }


    }

    fun idRelief(){
        val sharedPref = getSharedPreferences("Myref", Context.MODE_PRIVATE)
        val myString = sharedPref?.getString("token", "")
        Log.d("t",myString.toString())
        val shared = getSharedPreferences("Myid", Context.MODE_PRIVATE)
        val productId = shared?.getString("id", "")
        Log.d("id",productId.toString())
        val sharedRes = getSharedPreferences("RescueID", Context.MODE_PRIVATE)
        val id = sharedRes?.getString("idShare", "")
        Log.d("is",id.toString())
        val dataSub = getSharedPreferences("DataSub", Context.MODE_PRIVATE)
        val data = dataSub?.getString("data", "")
        Log.d("is",data.toString())
        reliefPlan(data.toString())
    }



    fun pickCalendar() {
        calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.datePickStart.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                R.style.datePicker,
                DatePickerDialog.OnDateSetListener { view: DatePicker, myear: Int, mmonth: Int, mday: Int ->
                    val selectedDateTime = LocalDateTime.of(myear, month + 1, mday, 0, 0, 0, 0)
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val formattedDateTime = selectedDateTime.format(formatter)
                    binding.edtStart.text = formattedDateTime.toString()

                },
                year,
                month,
                day
            )
            dpd.show()

        }
        binding.datePickEnd.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                R.style.datePicker,
                DatePickerDialog.OnDateSetListener { view: DatePicker, myear: Int, mmonth: Int, mday: Int ->
                    val selectedDateTime = LocalDateTime.of(myear, month + 1, mday, 0, 0, 0, 0)
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    val formattedDateTime = selectedDateTime.format(formatter)
                    binding.edtEndAt.text = formattedDateTime.toString()


                },
                year,
                month,
                day
            )
            dpd.show()
        }

    }



    fun reliefPlan(planId:String){


       var pickDateStart = binding.edtStart.text
        var pickDateEnd = binding.edtEndAt.text
        var fromMobile:Boolean = true
        var aidoMoney = binding.edtPackageMoney.text.toString()
        var totalMoney = binding.edtTotalMoney.text.toString()
        var NeccList = binding.edtNeccessaryList.text.toString()
        var planRequest = PlanRelief(pickDateStart.toString(),pickDateEnd.toString(),aidoMoney.toInt(),totalMoney.toInt(),fromMobile,NeccList)

        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.1.4:3000/relief-app/v1/"
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
        api.ReliefPlan(planRequest,planId).enqueue(object : Callback<PlanRespone>{
            override fun onResponse(call: Call<PlanRespone>, response: Response<PlanRespone>) {
                if (response.isSuccessful){
                    var data= response.body()
                    Toast.makeText(this@ReliefPlanRescue,"cập nhật plan thành công",Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.e("API", "Lỗi khi update dữ liệu: ${response.message()}")

                }

            }

            override fun onFailure(call: Call<PlanRespone>, t: Throwable) {
                Log.e("API", "Fail update Plan", t)

            }

        })
    }



}