package com.example.foryou.View.Doicuutro

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.example.foryou.Model.RescueTem.RequestRelief
import com.example.foryou.Model.RescueTem.ResponeRelief
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Donation.DetailPostDonation
import com.example.foryou.databinding.ActivityUpdateDetailReliefBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class UpdateDetailRelief : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateDetailReliefBinding
    private lateinit var calendar: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDetailReliefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        calanderRelief()
        setOnClick()
    }
    fun calanderRelief(){
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
        binding.datePickerActionsEnd.setOnClickListener {
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
    fun getIdSUb(){
        val sharedId = getSharedPreferences("DetailId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DetailId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        updateReliefPlan(idDetail.toString())
    }
    fun updateReliefPlan(id:String){
        var fromMobile:Boolean = true
            var startAt = binding.edtStart.text
            var endAt = binding.edtEndAt.text
        var amoutOfMoney = binding.edtMoneyNeed.text.toString()
        var totalMoney = binding.edtTotalMn.text.toString()
        var neccessary = binding.edtNeccesary.text.toString()
        var request = RequestRelief(amoutOfMoney.toInt(),totalMoney.toInt(),neccessary,fromMobile,endAt.toString(),startAt.toString())
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
        api.updateRelief(id,request).enqueue(object :Callback<ResponeRelief>{
            override fun onResponse(call: Call<ResponeRelief>, response: Response<ResponeRelief>) {
                if (response.isSuccessful){
                    Toast.makeText(this@UpdateDetailRelief,"update successfull", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdateDetailRelief, ListReliefResue::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@UpdateDetailRelief,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponeRelief>, t: Throwable) {
                Toast.makeText(this@UpdateDetailRelief,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }

    fun setOnClick(){
        binding.btnConfirm.setOnClickListener {
            getIdSUb()
        }
    }
}