package com.example.foryou.View.Donation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.foryou.Model.Donation.DonateRespone
import com.example.foryou.Model.Donation.PostDonate
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.Fragment.ManagerFragment
import com.example.foryou.databinding.ActivityInforDonationBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InforDonation : AppCompatActivity() {
    private lateinit var binding:ActivityInforDonationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBankInfor()
        setOnClickRegist()
    }

    fun setOnClickRegist(){
        binding.btnDonate.setOnClickListener {
            getPostId()
        }
    }
    fun getBankInfor(){
        val bankName = getSharedPreferences("Bank", Context.MODE_PRIVATE)
        val bank = bankName?.getString("bank", "")
        val bankNumber = bankName?.getString("bankNumber","")
        val editableText = Editable.Factory.getInstance().newEditable(bankNumber)
        binding.edtSTK.text= editableText
        var tenNganHang = Editable.Factory.getInstance().newEditable(bank)
        binding.edtNameBank.text = tenNganHang

        Log.d("bank", bankNumber.toString())
        Log.d("name",bank.toString())
    }
 fun getPostId(){
     val sharedId = getSharedPreferences("DonationIdPost", Context.MODE_PRIVATE)
     val idDetail = sharedId?.getString("DonationId", "")

     regisDonate(idDetail.toString())
 }
    fun regisDonate(id:String){

        var money = binding.edtMoneyDonate.text.toString()
        var neccess =  binding.edtVatPham.text.toString()
        var donateRequest = PostDonate(money.toInt(),neccess)
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
        api.postDonate(donateRequest,id).enqueue(object : Callback<DonateRespone>{
            override fun onResponse(call: Call<DonateRespone>, response: Response<DonateRespone>) {
                if (response.isSuccessful){
                    var data = response.body()
                    Toast.makeText(this@InforDonation,"Đăng kí quyên góp thành công . Upload minh chứng để hoàn thành xác nhân bạn nhé", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@InforDonation,InforDonation2::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@InforDonation,response.message(), Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DonateRespone>, t: Throwable) {
                Toast.makeText(this@InforDonation,"${t}", Toast.LENGTH_SHORT).show()

            }
        })
    }
}