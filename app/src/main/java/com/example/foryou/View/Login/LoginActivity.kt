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
import com.example.foryou.Model.Retrofit.RetrofitIntance
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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiService: getClient
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
        apiService = RetrofitIntance.getRetroInstance().create(getClient::class.java)
        apiService.login(loginRequest).enqueue(object: Callback<LoginRespone>{
            override fun onResponse(call: Call<LoginRespone>, response: Response<LoginRespone>) {
                if (response.isSuccessful){
                    val token = response.body()?.data?.accessToken
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
                    val intent = Intent(this@LoginActivity,HomeActivity::class.java)
                    intent.putExtra("token",token)
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
        val values = mutableListOf("rescue_team", "local_officer", "sponor")
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
                when(position){
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

}