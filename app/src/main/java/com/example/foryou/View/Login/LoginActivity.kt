package com.example.foryou.View.Login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foryou.Model.Retrofit.MyInterceptors
import com.example.foryou.Model.Retrofit.getClient
import com.example.foryou.Model.UserModel.LoginRequest
import com.example.foryou.Model.UserModel.LoginRespone
import com.example.foryou.Model.UserModel.UserReponsitory
import com.example.foryou.R
import com.example.foryou.View.Donation.MainPage.HomeActivity
import com.example.foryou.View.Register.RegisterActivity
import com.example.foryou.ViewModel.RegisterViewModel
import com.example.foryou.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: getClient
    private lateinit var   token:String
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClick()
        spinerUserType()
    }



    fun setOnClick() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            validationLogin()
            initViewModel()
        }
    }

    fun validationLogin() {
        val email = binding.txtUsername.text.toString().trim()
        var passWord = binding.txtPass.text.toString().trim()

        if (email.isEmpty()) {
            binding.txtUsername.error = "Email required"
            binding.txtUsername.requestFocus()

        }
        if (passWord.isEmpty()) {
            binding.txtPass.error = "Password required"
            binding.txtPass.requestFocus()
        }
    }

    fun initViewModel() {
        var username =binding.spnUserType.selectedItem.toString() + "|" + binding.txtUsername.text.toString()
        var password = binding.txtPass.text.toString()

        val loginRequest = LoginRequest(username, password)
        var loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
        val baseURL = "http:/192.168.143.2:3000/relief-app/v1/"
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
        api.login(loginRequest).enqueue(object: Callback<LoginRespone>{
            override fun onResponse(call: Call<LoginRespone>, response: Response<LoginRespone>) {
                if (response.isSuccessful){
                     token = response.body()?.data?.accessToken.toString()
                    if (token != null) {

                        // Lưu token vào SharedPreferences
                        val sharedPref = getSharedPreferences("Myref", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("token", token)
                            apply()
                        }
                        Log.d("tokern",token)
                    }else{
                        val errorMessage =response.code()
                        Log.e("errorTK",errorMessage.toString())
                    }

                    // Lưu trữ thông tin người dùng vào SharedPreferences
                    // Chuyển hướng đến màn hình chính của ứng dụng
                    val item = binding.spnUserType.selectedItem.toString()

                    val sharedPref = getSharedPreferences("UserType", Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString("userType", item)
                            apply()
                        }
                    }
                    val intent = Intent(this@LoginActivity,HomeActivity::class.java)

                    intent.putExtra("userType",item)
                    startActivity(intent)
                }else{

                    Toast.makeText(this@LoginActivity,"Login fail!! try again",Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<LoginRespone>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Login unsuccessful",Toast.LENGTH_SHORT).show()
                Log.e("abc",t.toString())
            }

        })

    }
    fun spinerUserType(){ //DỘi cứu trợ
        val values = mutableListOf("rescue_team", "local_officer", "sponsor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, values)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnUserType.adapter = adapter
        binding.spnUserType.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedValue = values[position]



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
    fun retrofit() {

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

    }


    }