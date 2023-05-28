package com.example.foryou.View.Doicuutro

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.example.foryou.Model.Donation.UpdateDonation
import com.example.foryou.Model.Donation.UpdateRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityUpdateDonationPostBinding
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

class UpdateDonationPost : AppCompatActivity() {
    private lateinit var binding:ActivityUpdateDonationPostBinding
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDonationPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDeadlineFormat()
        onClickListen()
    }
    fun onClickListen(){
        binding.btnConfirm.setOnClickListener {
            getId()
        }
    }
    fun getId(){
        val sharedId = getSharedPreferences("DonationId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DonationId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        updateDonationPost(idDetail.toString())
    }
    fun setDeadlineFormat(){
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
    }
    fun updateDonationPost(id:String){
        var fromMobile:Boolean = true
        var moneyNeed = binding.edtMoneyDonatio.text.toString()
        var deadline = binding.edtStart.text.toString()
        var need = binding.edtNhuYeuPham.text.toString()
        var mota = binding.edtMota.text.toString()
        var updateRequest = UpdateDonation(mota,moneyNeed.toInt(),need,fromMobile,deadline)
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http://192.168.143.2:3000/relief-app/v1/"
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
        api.updateDoantionPost(id, updateRequest).enqueue(object :Callback<UpdateRespone>{
            override fun onResponse(call: Call<UpdateRespone>, response: Response<UpdateRespone>) {
                if (response.isSuccessful){
                    var data = response.body()
                    Toast.makeText(this@UpdateDonationPost,"Update data successfull",Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@UpdateDonationPost , ListDonationRescue::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@UpdateDonationPost,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<UpdateRespone>, t: Throwable) {
                Toast.makeText(this@UpdateDonationPost,"${t}", Toast.LENGTH_SHORT).show()

            }

        })
    }
}