package com.example.foryou.View.Doicuutro

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.example.foryou.Model.Donation.DonationRequest
import com.example.foryou.Model.Donation.DonationRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityPostRescueTeamBinding
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

class PostRescueTeam : AppCompatActivity() {
    private lateinit var binding : ActivityPostRescueTeamBinding
    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostRescueTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        formatCalendar()
        setOnClickPost()
    }
    fun setOnClickPost(){
        binding.btnConfirm.setOnClickListener {
            getId()
        }
    }
    fun getId(){
        val sharedId = getSharedPreferences("MyReliefId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("ReliefId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        postDonation(idDetail.toString())
    }
    fun formatCalendar(){
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
    fun postDonation(id:String){
        var decription = binding.edtDetail.text.toString()
        var fromMobile:Boolean =true
        var bank = binding.edtNameBank.text.toString()
        var bankNumber  = binding.edtNumberBank.text.toString()
        var necessaryList = binding.edtNeccesary.text.toString()
        var moneyNeed = binding.edtTotalMn.text.toString()
        var deadline = binding.edtStart.text.toString()
        var donationRequest =  DonationRequest(decription,moneyNeed.toInt(),necessaryList,fromMobile,deadline,bankNumber,bank)
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
        api.postDonation(id, donationRequest).enqueue(object : Callback<DonationRespone>{
            override fun onResponse(
                call: Call<DonationRespone>,
                response: Response<DonationRespone>
            ) {
                if (response.isSuccessful){
                    var dataPost = response.body()
                    Toast.makeText(this@PostRescueTeam,"Post successfull", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@PostRescueTeam,ListDonationRescue::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@PostRescueTeam,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DonationRespone>, t: Throwable) {
                Toast.makeText(this@PostRescueTeam,"${t}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}