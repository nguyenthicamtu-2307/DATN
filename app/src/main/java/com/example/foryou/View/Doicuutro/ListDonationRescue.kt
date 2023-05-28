package com.example.foryou.View.Doicuutro

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.foryou.Model.Donation.DonationDetail
import com.example.foryou.Model.RescueTem.FinishDonationPost
import com.example.foryou.Model.RescueTem.FinishDonationPostRespone
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.databinding.ActivityListDonationRescueBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListDonationRescue : AppCompatActivity() {
    private lateinit var binding : ActivityListDonationRescueBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDonationRescueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        OnClickListener()
        getID()
    }
    fun getID(){
        val sharedId = getSharedPreferences("DonationId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DonationId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        getDetailDonation(idDetail.toString())
    }
    fun getDetailDonation(id:String){
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
        api.getDetailDonationPost(id).enqueue(object : Callback<DonationDetail>{
            override fun onResponse(
                call: Call<DonationDetail>,
                response: Response<DonationDetail>
            ) {
                if (response.isSuccessful){
                    var dataDetail = response.body()
                    binding.txtDetailEvent.text= dataDetail?.data?.description
                    binding.txtStartTime.text = dataDetail?.data?.donatedMoney?.toString()

                    binding.txtYear.text = dataDetail?.data?.moneyNeed.toString()
                    binding.txtDeadline.text = dataDetail?.data?.deadline
                    binding.txtStatus.text = dataDetail?.data?.status

                }else{
                    Toast.makeText(this@ListDonationRescue,response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DonationDetail>, t: Throwable) {
                Toast.makeText(this@ListDonationRescue,"${t}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun  OnClickListener(){
        binding.btnDKCT.setOnClickListener {
            var intent = Intent(this,UpdateDonationPost::class.java)
            startActivity(intent)
        }
        binding.btnDone.setOnClickListener {
            idInforPost()
        }
    }
    fun idInforPost(){
        val sharedId = getSharedPreferences("DonationId", Context.MODE_PRIVATE)
        val idDetail = sharedId?.getString("DonationId", "")
//          val data_: String? = intent.getStringExtra("id")
//
        Log.d("idShare",idDetail.toString())
        showDialog(idDetail.toString())
    }
    private fun showDialog(id: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom)

        val textViewMessage = dialog.findViewById<TextView>(R.id.textViewMessage)
        val buttonYes = dialog.findViewById<Button>(R.id.buttonYes)
        val buttonNo = dialog.findViewById<Button>(R.id.buttonNo)

        textViewMessage.text = "Are you sure?"
        buttonYes.text = "finish"
        var requets = FinishDonationPost("finish")
        buttonYes.setOnClickListener {
            // Xử lý khi nút Yes được nhấn
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
            api.confirmFinishDonationPost(requets, id).enqueue(object : Callback<FinishDonationPostRespone>{
                override fun onResponse(
                    call: Call<FinishDonationPostRespone>,
                    response: Response<FinishDonationPostRespone>
                ) {
                    if (response.isSuccessful){
                        var dataPost = response.body()
                        Toast.makeText(this@ListDonationRescue,"Post successfull", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this@ListDonationRescue,response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<FinishDonationPostRespone>, t: Throwable) {
                    Toast.makeText(this@ListDonationRescue,"${t}", Toast.LENGTH_SHORT).show()

                }

            })
        }

        buttonNo.setOnClickListener {
            // Xử lý khi nút No được nhấn
            dialog.dismiss()
        }

        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
            // Xử lý khi hộp thoại được đóng
        })

        dialog.show()
    }
}
